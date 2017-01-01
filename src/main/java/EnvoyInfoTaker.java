import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by Wojciech Zdzarski on 14.12.2016.
 * Take infos about envoy by parsing Jsons
 */

public class EnvoyInfoTaker {

    private URLGenerator gen;
    private int outcomes_pointer;
    private String outcomes_str;


    public EnvoyInfoTaker(int cadence) {
        this.outcomes_pointer = 13;
        this.outcomes_str = Layers.toString(Layers.wydatki);
        this.gen = new URLGenerator(cadence);
    }


    public String findEnvoyeID(String Name, String Surname) throws Exception {
        String link = gen.generateAllEnvoyesInfo();
        String url = WebApiParser.readJSON(link);
        JSONObject all_envoyes = new JSONObject(url);
        JSONObject links = all_envoyes.getJSONObject(WebApiParser.LINKS_TABLE);
        String self = links.getString("self");
        String id = null;
        while (self != null) {
            url = WebApiParser.readJSON(self);
            JSONObject page = new JSONObject(url);
            JSONArray envoyes = page.getJSONArray(WebApiParser.ALL_ENV_TAB);
            for (int i = 0; i < envoyes.length(); i++) {
                JSONObject single_env = envoyes.getJSONObject(i);
                JSONObject data = single_env.getJSONObject(WebApiParser.DATA);
                String curr_name = data.getString(WebApiParser.ENV_NAME);
                String curr_surname = data.getString(WebApiParser.ENV_SURNAME);
                if (curr_name.equals(Name) && curr_surname.equals(Surname)) {
                    id = single_env.getString("id");
                }
            }
            links = page.getJSONObject(WebApiParser.LINKS_TABLE);
            self = links.has("next") ? links.getString("next") : null;
        }
        if(id == null) throw new Exception("Envoy not found");
        return id ;
    }

    public String getRepairsOf(String id) throws Exception {
        String url = gen.generateLayerByID(id, Layers.wydatki);
        String rawJSON = WebApiParser.readJSON(url);
        JSONObject page = new JSONObject(rawJSON);
        JSONObject layers = page.getJSONObject("layers");
        JSONObject outcomes = layers.getJSONObject(this.outcomes_str);
        JSONArray annuals = outcomes.getJSONArray("roczniki");
        double acc = 0;
        for(int i = 0 ; i < outcomes.getInt("liczba_rocznikow"); i++){
            JSONObject tmp = annuals.getJSONObject(i);
            JSONArray fields = tmp.getJSONArray("pola");
            acc += fields.getDouble(this.outcomes_pointer-1);
        }
        return Double.toString(acc);
    }

}
