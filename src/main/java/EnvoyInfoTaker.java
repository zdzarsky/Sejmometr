import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URL;

/**
 * Created by Wojciech Zdzarski on 14.12.2016.
 * Take infos about envoy by parsing Jsons
 */

public class EnvoyInfoTaker {

    private URLGenerator gen;

    public EnvoyInfoTaker(int cadence){
        this.gen = new URLGenerator(cadence);
    }

    public String getID(String Name, String Surname){
        String link = gen.generateEnvoyInfo(Name,Surname);
        String str = "";
        String name_and_surname = Name + " " + Surname;
        try{
            str = WebApiParser.readUrl(link);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        JSONObject obj = new JSONObject(str);
        JSONArray all = obj.getJSONArray(WebApiParser.ALL_ENV_TAB);
        for(int i = 0; i < all.length();i++){

        }
        return null;
    }

    public String getRepairsOf(int id){
        return null;
    }

}
