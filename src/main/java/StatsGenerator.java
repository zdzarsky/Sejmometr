import org.json.*;

import java.net.URL;
import java.util.Arrays;

/**
 * Created by Wojciech Zdzarski on 14.12.2016.
 * This class will parse Jsons to get necessary stats.
 */
public class StatsGenerator {
    private URLGenerator gen;

    StatsGenerator(int cadence) {
        this.gen = new URLGenerator(cadence);

    }

    public double countAvgOutgoings() throws Exception { // średnie roczne wydatki
        System.out.println("Counting average outgoings of all envoyes (may take a while) ...");
        String [] tab = getTabOfIds();
        double [] out = new double[tab.length];
        double acc = 0;
        int current_env = 1;
        for(int i=0;i<tab.length;i++) {
            System.out.println("Analizowanie posła " + current_env++ + " z " + tab.length );
            JSONObject page = new JSONObject(WebApiParser.readJSON(gen.generateLayerByID(tab[i], Layers.wydatki)));
            JSONObject layers = page.getJSONObject("layers");
            JSONObject outcomes = layers.getJSONObject("wydatki");
            JSONArray annuals = outcomes.getJSONArray("roczniki");
            int an_len = outcomes.getInt("liczba_rocznikow");
            for (int j = 0; j < an_len; j++) {
                JSONObject tmp = annuals.getJSONObject(j);
                JSONArray fields = tmp.getJSONArray("pola");
                for (int k = 0; k < fields.length(); k++) {
                    acc += fields.getDouble(k);
                }

            }
            acc /= an_len;
            out[i] = acc;
            acc = 0;
        }

        for(double x:out){
            acc+=x;
        }
        return (double)(acc/out.length);
    } // zoptymalizować

    private String[] getTabOfIds() {
        System.out.println("Processing table of all ids");
        String url = null;
        try {
            url = WebApiParser.readUrl(this.gen.generateAllEnvoyesInfo());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        JSONObject all_envoyes = new JSONObject(url);
        int size = all_envoyes.getInt("Count");
        String[] res = new String[size];
        int res_position = 0;
        JSONObject links = all_envoyes.getJSONObject(WebApiParser.LINKS_TABLE);

        String self = links.getString("self");
        String last = links.getString("last");

        while(self != null){
            try {
                url = WebApiParser.readUrl(self);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
            JSONObject page = new JSONObject(url);
            JSONArray envoyes = page.getJSONArray(WebApiParser.ALL_ENV_TAB);
            for (int i = 0; i < envoyes.length(); i++) {
                JSONObject single_env = envoyes.getJSONObject(i);
                String id = single_env.getString("id");
                res[res_position] = id;
                res_position++;
            }
            links = page.getJSONObject(WebApiParser.LINKS_TABLE);
            self = links.has("next") ? links.getString("next") : null;
        }
        return res;
    }

    public String findMostFrequentTraveller() {
        return null;
    }

    public String findMostExpensiveJourney() {
        return null;
    }

    public String[] getListOfItalyTravellers() {
        return null;
    }
}
