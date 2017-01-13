

/**
 * Created by Wojciech Zdzarski on 14.12.2016.
 * This class will generate necessary info urls;
 */

public class URLGenerator {
    private String BaseString;
    private String cadence;
    private String format;
    private String layer_init;
    private String cadence_string;

    URLGenerator(String cadence){
        this.BaseString = "https://api-v3.mojepanstwo.pl/dane/poslowie/";
        this.format = ".json?";
        this.layer_init = "layers[]=";
        this.cadence_string = "&[poslowie.kadencja]=";
        this.cadence = cadence;
    }
    public String generateLayersByID(String id){
        return this.BaseString +
                id + this.format +
                this.layer_init +
                Layers.wydatki.toString() +
                "&" +
                this.layer_init +
                Layers.wyjazdy.toString() +
                this.cadence_string + this.cadence;
    }
    public String generateAllEnvoysInfo(){
        return this.BaseString + this.format + this.cadence_string + this.cadence;
    }



}
