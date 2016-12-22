import org.json.*;

/**
 * Created by Wojciech Zdzarski on 14.12.2016.
 * This class will parse Jsons to get necessary stats.
 */
public class StatsGenerator {

    public double countAvgOutgoings(){
        try {
            JSONObject json = new JSONObject(WebApiParser.readUrl(""));
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }

        return 0;
    }

    public String findMostFrequentTraveller(){
        return null;
    }

    public String findMostExpensiveJourney(){
        return null;
    }

    public String [] getListOfItalyTravellers(){
        return null;
    }
}
