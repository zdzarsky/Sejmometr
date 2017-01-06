/**
 * Created by Wojciech Zdzarski on 06.01.2017.
    This class is keeping envoyes info.
 */

public class Envoy {
    private String ID;
    private String Name;
    private String Surname;
    private double SumOfOutcomes;
    private int AmountOfTravels;
    private boolean isItalyTraveller;
    private double MostExpensiveJourney;
    private int longestVoyage;
    private double RepairsOf;


    public Envoy(String ID, String name, String surname, double sumOfOutcomes, int amountOfTravels,
                 boolean isItalyTraveller, double mostExpensiveJourney, double repairs, int longestVoyage) {
        this.ID = ID;
        this.Name = name;
        this.Surname = surname;
        this.SumOfOutcomes = sumOfOutcomes;
        this.AmountOfTravels = amountOfTravels;
        this.isItalyTraveller = isItalyTraveller;
        this.MostExpensiveJourney = mostExpensiveJourney;
        this.RepairsOf = repairs;
        this.longestVoyage = longestVoyage;

    }

    @Override

    public String toString(){
        return  this.Name + " " + this.Surname;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getSurname() {
        return Surname;
    }

    public double getSumOfOutcomes() {
        return SumOfOutcomes;
    }

    public int getAmountOfTravels() {
        return AmountOfTravels;
    }

    public boolean isItalyTraveller() {
        return isItalyTraveller;
    }

    public double getMostExpensiveJourney() {
        return MostExpensiveJourney;
    }

    public int getLongestVoyage() {
        return longestVoyage;
    }
}
