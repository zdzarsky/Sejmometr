


public class Main {
    public static void main(String args[]){
        try {
            URLGenerator gen = new URLGenerator(5);
            EnvoyInfoTaker taker = new EnvoyInfoTaker(2);
            String i = taker.getID("Andrzej", "Czerwiński");
            taker.getRepairsOf(i);
        }
        catch(Exception e){
            e.getMessage();
        }

    }
}
