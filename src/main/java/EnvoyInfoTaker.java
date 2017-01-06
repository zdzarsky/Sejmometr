import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Wojciech Zdzarski on 14.12.2016.
 * Take infos about envoy by parsing Jsons
 */

public class EnvoyInfoTaker {

    private URLGenerator gen;
    private int outcomes_pointer;
    private List<Envoy> envoy_list;

    public EnvoyInfoTaker(URLGenerator gen) {
        this.envoy_list = new LinkedList<Envoy>();
        this.outcomes_pointer = 13;
        this.gen = gen;
    }

    private String[] getTabOfIds() {
        System.out.println("Processing table of all ids");
        String url = WebApiParser.readJSON(this.gen.generateAllEnvoyesInfo());
        JSONObject all_envoyes = new JSONObject(url);
        int size = all_envoyes.getInt("Count");

        String[] res = new String[size];
        int res_position = 0;
        JSONObject links = all_envoyes.getJSONObject(WebApiParser.LINKS_TABLE);

        String self = links.getString("self");

        while (self != null) {
            try {
                url = WebApiParser.readUrl(self);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
            JSONObject page = new JSONObject(url);
            JSONArray envoyes = page.getJSONArray(Strings.ALL_ENV_TAB);

            for (int i = 0; i < envoyes.length(); i++) {
                JSONObject single_env = envoyes.getJSONObject(i);
                String id = single_env.getString("id");
                res[res_position] = id;
                res_position++;
            }
            links = page.getJSONObject(Strings.LINKS_TABLE);
            self = links.has("next") ? links.getString("next") : null;
        }
        return res;
    }

    public void fillEnvoyList() {
        String[] id_tab = getTabOfIds();
        int counter = 1;
        for(String id : id_tab) {
            System.out.print(counter + " z " + id_tab.length + "\r");
            String url = gen.generateLayersByID(id);
            JSONObject json = new JSONObject(WebApiParser.readJSON(url));
            String Name = getName(json);
            String Surname = getSurname(json);
            double sumOutcomes = getSumOfOutcomes(json);
            int amountOfTravels = getAmountOfTravels(json);
            boolean isItalyVoyager = isItalyTraveller(json);
            double mostExpensiveJourney = getMostExpensiveJourney(json);
            double repairs = getRepairsOf(json);
            int longestVoyage = getLongestTripTime(json);
            this.envoy_list.add(new Envoy(id, Name, Surname, sumOutcomes, amountOfTravels, isItalyVoyager, mostExpensiveJourney, repairs, longestVoyage));
            counter ++;
        }
    }

    private double getMostExpensiveJourney(JSONObject json) {
        JSONObject layers = json.getJSONObject("layers");
        if(layers.optJSONObject("wyjazdy") != null) return 0;
        JSONArray travels = layers.getJSONArray(Layers.wyjazdy.toString());
        double max = 0;
        for (int i = 0; i < travels.length(); i++) {
            JSONObject single_trip = travels.getJSONObject(i);
            double current_cost = single_trip.getDouble("koszt_suma");
            max = current_cost > max ? current_cost : max;
        }
        return max;
    }

    private boolean isItalyTraveller(JSONObject json) {
        JSONObject layers = json.getJSONObject("layers");
        if(layers.optJSONObject("wyjazdy") != null) return false;
        JSONArray travels = layers.getJSONArray(Layers.wyjazdy.toString());
        for (int i = 0; i < travels.length(); i++) {
            JSONObject single_trip = travels.getJSONObject(i);
            String country = single_trip.getString("kraj");
            if (country.equals("Włochy")) return true;
        }
        return false;
    }

    private int getAmountOfTravels(JSONObject json) {
        JSONObject layers = json.getJSONObject("layers");
        if(layers.optJSONObject("wyjazdy") != null) return 0;
        JSONArray travels = layers.getJSONArray(Layers.wyjazdy.toString());
        return travels.length();
    }

    private int getLongestTripTime(JSONObject json){
        JSONObject layers = json.getJSONObject("layers");
        if(layers.optJSONObject("wyjazdy") != null) return 0;
        JSONArray travels = layers.getJSONArray(Layers.wyjazdy.toString());
        int max = 0;
        for (int i = 0; i < travels.length(); i++) {
            JSONObject single_trip = travels.getJSONObject(i);
            int tmp = single_trip.getInt("liczba_dni");
            max = tmp > max ? tmp:max;
        }

        return max;
    }

    private double getSumOfOutcomes(JSONObject json) {
        JSONObject layers = json.getJSONObject("layers");
        JSONObject outcomes = layers.getJSONObject(Layers.wydatki.toString());
        JSONArray annuals = outcomes.getJSONArray("roczniki");
        double acc = 0;
        for (int j = 0; j < annuals.length(); j++) {
            JSONObject tmp = annuals.getJSONObject(j);
            JSONArray fields = tmp.getJSONArray("pola");
            for (int k = 0; k < fields.length(); k++) {
                acc += fields.getDouble(k);
            }
        }
        return acc;
    }

    private String getName(JSONObject json) {
        JSONObject data = json.getJSONObject("data");
        return data.getString("poslowie.imie_pierwsze");
    }

    private String getSurname(JSONObject json) {
        JSONObject data = json.getJSONObject("data");
        return data.getString("poslowie.nazwisko");
    }

    public Envoy getEnvoyByName(String Name, String Surname) throws Exception {
        for (Envoy e : this.envoy_list) {
            if (e.getName().equals(Name) && e.getSurname().equals(Surname))
                return e;
        }
        throw new Exception("Poseł o podanym imieniu i nazwisku nie istnieje");
    }

    private double getRepairsOf(JSONObject json) {

        JSONObject layers = json.getJSONObject("layers");
        JSONObject outcomes = layers.getJSONObject(Layers.wydatki.toString());
        JSONArray annuals = outcomes.getJSONArray("roczniki");
        double acc = 0;
        for (int i = 0; i < annuals.length(); i++) {
            JSONObject tmp = annuals.getJSONObject(i);
            JSONArray fields = tmp.getJSONArray("pola");
            acc += fields.getDouble(this.outcomes_pointer - 1);
        }
        return acc;
    }

    public List<Envoy> getEnvoyList() {
        return envoy_list;
    }
}
