

/**
 * Created by Wojciech Zdzarski on 14.12.2016.
 * Layers distinguishable by API
 */

public enum Layers {
    krs, biura, wyjazdy, wydatki;
    public static String toString(Layers layer) {
        switch(layer){
            case krs : return "krs";
            case biura: return "biura";
            case wyjazdy: return "wyjazdy";
            case wydatki :return "wydatki";
            default: return "ERROR_ON_MAP_ELEMENT_TO_STRING";
        }
    }
}