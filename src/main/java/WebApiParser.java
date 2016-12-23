import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by Wojciech Zdzarski on 16.12.2016.
 * Technical stuff and constants
 */

public class WebApiParser {

    public static final String ALL_ENV_TAB = "Dataobject";
    public static final String ENV_NAME = "poslowie.imie_pierwsze";
    public static final String ENV_SURNAME = "poslowie.nazwisko";
    public static final String DATA = "data";
    public static final String LINKS_TABLE = "Links";

    public static String readUrl(String url) throws Exception {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);
        in.close();

        return response.toString();
    }


}
