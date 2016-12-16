

import org.json.*;
/**
 * Created by Wojciech Zdzarski on 14.12.2016.
 * This class will generate necessary info urls;
 * TODO - Differencing Cadences
 */

public class URLGenerator {
    private String BaseString;
    private int cadence;

    URLGenerator(int cadence){
        this.BaseString = "https://api-v3.mojepanstwo.pl/dane/poslowie/";
        this.cadence = cadence;
    }

    public String generateEnvoyInfo(String Name, String Surname){
        return null;
    }

    public String generateAllEnvoyesInfo(){
        return null;
    }

    public String generateTravelsInfo(){
        return null;
    }


}
