import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Wojciech Zdzarski on 14.12.2016.
 * Take infos about envoy by parsing Jsons
 */

public class EnvoyInfoTaker {

    private URLGenerator gen;
    private int outcomes_pointer;
    private List<Envoy> envoy_list;
    private String cadence;

    public EnvoyInfoTaker(URLGenerator gen, String cadence) {
        this.envoy_list = new LinkedList<Envoy>();
        this.outcomes_pointer = 13;
        this.gen = gen;
        this.cadence = cadence;
    }

    private List<String> getListOfIds() {
        System.out.println("Processing table of all ids");
        String url = WebApiParser.readJSON(this.gen.generateAllEnvoysInfo());
        JSONObject all_envoyes = new JSONObject(url);
        List<String> res = new LinkedList<String>();
        JSONObject links = all_envoyes.getJSONObject("Links");
        String self = links.getString("self");

        int a = 1;
        while (self != null) {
            System.out.print("\r +++ Processing " + a + " pages of 15");
            a++;
            try {
                url = WebApiParser.readUrl(self);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
            JSONObject page = new JSONObject(url);
            JSONArray envoyes = page.getJSONArray("Dataobject");

            for (int i = 0; i < envoyes.length(); i++) {
                JSONObject single_env = envoyes.getJSONObject(i);
                String id = single_env.getString("id");
                JSONArray w = single_env.getJSONObject("data").getJSONArray("poslowie.kadencja");
                for(int j = 0;j < w.length();j++){
                    String k = Integer.toString(w.getInt(j));
                    if(k.equals(this.cadence)) res.add(id);
                }
            }
            links = page.getJSONObject("Links");
            self = links.has("next") ? links.getString("next") : null;
        }
        System.out.println();
        return res;
    }
    
    @Deprecated
    public void fillEnvoyList() {
        List<String> id_list = getListOfIds();
        int counter = 1;
        System.out.println("\n Checking info about all envoys");
        for (String id : id_list) {
            System.out.print("\r Processed : " + counter++ + " of " + id_list.size());
            envoy_list.add(getEnvoy(id));
        }
    } // Metoda do imperatywnego przetworzenia posłów

    public Envoy getEnvoy(String id) {
        String url = gen.generateLayersByID(id);
        JSONObject json = new JSONObject(WebApiParser.readJSON(url));
        String Name = getName(json);
        String Surname = getSurname(json);
        double sumOutcomes = getSumOfOutcomes(json);
        int amountOfTravels = getAmountOfTravels(json);
        boolean isItalyVoyager = isItalyTraveller(json);
        double mostExpensiveJourney = getMostExpensiveJourney(json);
        double repairs = getRepairsCosts(json);
        int longestVoyage = getLongestTripTime(json);
        return new Envoy(
                id, Name, Surname,
                sumOutcomes, amountOfTravels,
                isItalyVoyager, mostExpensiveJourney,
                repairs, longestVoyage);
    }

    public void parallelListFill() { // Metoda wykorzystująca parallel stream do wypełnienia listy posłów
        List<String> idList = getListOfIds();
        System.out.println("+++ Parallel processing of envoy list (May take a while).");
        envoy_list = idList
                .parallelStream()
                .map(this::getEnvoy)
                .collect(Collectors.toList());
    }

    private double getMostExpensiveJourney(JSONObject json) {
        JSONObject layers = json.getJSONObject("layers");
        if (layers.optJSONObject("wyjazdy") != null) return 0;
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
        if (layers.optJSONObject("wyjazdy") != null) return false;
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
        if (layers.optJSONObject("wyjazdy") != null) return 0;
        JSONArray travels = layers.getJSONArray(Layers.wyjazdy.toString());
        return travels.length();
    }

    private int getLongestTripTime(JSONObject json) {
        JSONObject layers = json.getJSONObject("layers");
        if (layers.optJSONObject("wyjazdy") != null) return 0;
        JSONArray travels = layers.getJSONArray(Layers.wyjazdy.toString());
        int max = 0;
        for (int i = 0; i < travels.length(); i++) {
            JSONObject single_trip = travels.getJSONObject(i);
            int tmp = single_trip.getInt("liczba_dni");
            max = tmp > max ? tmp : max;
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

    private double getRepairsCosts(JSONObject json) {

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
