


public class Main {
    public static void main(String args[]){
        try {

            EnvoyInfoTaker taker = new EnvoyInfoTaker(2);
            taker.getID("Andrzej", "Duda");
        }
        catch(Exception e){
            e.getMessage();
        }

    }
}
