import org.json.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Wojciech Zdzarski on 14.12.2016.
 * This class will parse Jsons to get necessary stats.
 */
public class StatsGenerator {
    private List<Envoy> envoys;

    StatsGenerator(List<Envoy> envoys) {
       this.envoys = envoys;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.countAvgOutGoings());
        sb.append(this.findMostFrequentTraveller());
        sb.append(this.findMostExpensiveJourney());
        sb.append(this.findLongestTripper());
        sb.append(this.getListOfItalyTravellers());
        return sb.toString();
    }

    private String countAvgOutGoings(){
        double denominator = (double) this.envoys.size();
        double sum = 0.0;
        for(Envoy e : envoys){
            sum+=e.getSumOfOutcomes();
        }
        return "Średnie wydatki wszystkich posłów: " + Double.toString(sum / denominator) + " złotych.\n";
    }

    private String findMostFrequentTraveller() {
        String winner_name = "";
        String winner_surname = "";
        int travels_amount = 0;
        for(Envoy e: envoys){
            int tmp_amount = e.getAmountOfTravels();
            if(tmp_amount > travels_amount){
                travels_amount = tmp_amount;
                winner_name = e.getName();
                winner_surname = e.getSurname();
            }
        }
        return "Najwiecej podróży wykonał: " + winner_name + " " + winner_surname + " z ilością " + Integer.toString(travels_amount) + "razy \n";
    }

    private String findLongestTripper() {
        String winner_name = "";
        String winner_surname = "";
        int travels_length = 0;
        for(Envoy e: envoys){
            int tmp_amount = e.getLongestVoyage();
            if(tmp_amount > travels_length){
                travels_length = tmp_amount;
                winner_name = e.getName();
                winner_surname = e.getSurname();
            }
        }
        return "Najdłuższą podróż odbył " + winner_name + " " + winner_surname + " o długości " + Integer.toString(travels_length) + " dni.\n";
    }

    private String findMostExpensiveJourney() {
        String winner_name = "";
        String winner_surname = "";
        double travels_cost = 0;
        for(Envoy e: envoys){
            double tmp_amount = e.getMostExpensiveJourney();
            if(tmp_amount > travels_cost){
                travels_cost = tmp_amount;
                winner_name = e.getName();
                winner_surname = e.getSurname();
            }
        }
        return "Najdroższą podróż odbył " + winner_name + " " + winner_surname + " o sumarycznym koszcie " + Double.toString(travels_cost) + " złotych.";
    }

    private String getListOfItalyTravellers() {
        StringBuilder sb = new StringBuilder();
        int formatter = 0;
        for(Envoy e: envoys){
            if(e.isItalyTraveller()){
                sb.append(e.getName()).append(" ").append(e.getSurname()).append(",");
            }
            if(formatter%10 == 0) sb.append("\n");
        }
        sb.deleteCharAt(sb.capacity()-1);
        sb.append(".");
        return sb.toString() + "\n";
    }

}
