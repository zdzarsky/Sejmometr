import org.json.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Wojciech Zdzarski on 14.12.2016.
 * This class will parse Jsons to get necessary stats.
 */
public class StatsGenerator {
    private List<Envoy> envoys;
    private String name;
    private String surname;

    StatsGenerator(List<Envoy> envoys, String name , String surname) {
       this.envoys = envoys;
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n ***************** STATYSTYKA ************* \n");
        sb.append(this.getInfoOf(this.name, this.surname));
        sb.append(this.countAvgOutGoings());
        sb.append(this.findMostFrequentTraveller());
        sb.append(this.findMostExpensiveJourney());
        sb.append(this.findLongestTripper());
        sb.append(this.getListOfItalyTravellers());
        return sb.toString();
    }

    private String getInfoOf(String name, String surname) {
        Envoy x = null;
        try{
            x = getEnvoyByName(name,surname);
        } catch (Exception e) {
            return e.getMessage() + "\n";
        }
        return "Poseł " + name + " " + surname + " wydał " + String.format("%.2f", x.getSumOfOutcomes()) + " złotych.\n" +
                "Poseł " + name + " " + surname + " wydał " + String.format("%.2f",x.getRepairsOf()) + " złotych na remonty biura poselskiego.\n";
    }

    private String countAvgOutGoings(){
        double denominator = (float)this.envoys.size();
        double sum = 0;
        for(Envoy e : envoys){
            sum+=e.getSumOfOutcomes();
        }
        double score = sum / denominator;
        return "Średnie wydatki wszystkich posłów: " + String.format("%.2f", score ) + " złotych.\n";
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
        return "Najwiecej podróży wykonał " + winner_name + " " + winner_surname + " z ilością " + Integer.toString(travels_amount) + " razy \n";
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
        return "Najdroższą podróż odbył " + winner_name + " " + winner_surname + " o sumarycznym koszcie " + String.format("%.2f", travels_cost) + " złotych. \n";
    }

    private String getListOfItalyTravellers() {
        StringBuilder sb = new StringBuilder();
        int formatter = 1;
        for(Envoy e: envoys){
            if(e.isItalyTraveller()){
                sb.append(e.getName()).append(" ").append(e.getSurname()).append(",");
                formatter ++;
                if(formatter%5 == 0) sb.append("\n");
            }

        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(".");
        return "Posłowie którzy odwiedzili włochy to : " + sb.toString() + "\n";
    }

    public Envoy getEnvoyByName(String Name, String Surname) throws Exception {

        for (Envoy e : this.envoys) {
            if (e.getName().equals(Name) && e.getSurname().equals(Surname))
                return e;
        }
        throw new Exception("Poseł o podanym imieniu i nazwisku nie istnieje");
    }
}
