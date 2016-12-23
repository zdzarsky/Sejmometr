import com.sun.deploy.pings.Pings;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by Wojciech Zdzarski on 14.12.2016.
 * Take infos about envoy by parsing Jsons
 */

public class EnvoyInfoTaker {

    private URLGenerator gen;

    public EnvoyInfoTaker(int cadence) {
        this.gen = new URLGenerator(cadence);
    }

    public String getID(String Name, String Surname) {
        String link = gen.generateEnvoyInfo(Name, Surname);
        String url = "";
        String name_and_surname = Name + " " + Surname;
        try {
            url = WebApiParser.readUrl(link);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        JSONObject all_envoyes = new JSONObject(url);
        JSONObject links = all_envoyes.getJSONObject(WebApiParser.LINKS_TABLE);
        String self = links.getString("self");
        while(self != null){
            try {
                url = WebApiParser.readUrl(self);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
            JSONObject page = new JSONObject(url);
            JSONArray envoyes = page.getJSONArray(WebApiParser.ALL_ENV_TAB);
            for(int i = 0; i < envoyes.length();i++){
                JSONObject single_env = envoyes.getJSONObject(i);
                JSONObject data = single_env.getJSONObject(WebApiParser.DATA);
                String curr_name = (String)data.getString(WebApiParser.ENV_NAME);
                String curr_surname = (String) data.getString(WebApiParser.ENV_SURNAME);
                if(curr_name.equals(Name) && curr_surname.equals(Surname)){
                    return single_env.getString("id");
                }
            }
            links = page.getJSONObject(WebApiParser.LINKS_TABLE);
            self = (String) links.getString("next");
        }
        return null;
    } // działa

    public String getRepairsOf(int id) {
        return null;
    }

}
