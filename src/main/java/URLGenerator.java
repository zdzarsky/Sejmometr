

import org.json.*;

/**
 * Created by Wojciech Zdzarski on 14.12.2016.
 * This class will generate necessary info urls;
 * TODO - Differencing Cadences
 */

public class URLGenerator {
    private String BaseString;
    private int cadence;
    private String format;
    private String layer_init;
    private String cadence_string;

    URLGenerator(int cadence){
        this.BaseString = "https://api-v3.mojepanstwo.pl/dane/poslowie/";
        this.format = ".json?";
        this.layer_init = "layers[]=";
        this.cadence_string = "&kadencja=";
        this.cadence = cadence;
    }

    public String generateEnvoyInfo(String Name, String Surname){
        return this.BaseString + ""; // example
    }
    public String generateLayerByID(String id, Layers layer){
        return this.BaseString + id + this.format + this.layer_init + Layers.toString(layer) + this.cadence_string + this.cadence;
    }
    public String generateAllEnvoyesInfo(){
        return null;
    }

    public String generateTravelsInfo(){
        return null;
    }


}
