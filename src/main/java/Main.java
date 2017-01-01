


public class Main {
    public static void main(String args[]){
        try {
            //URLGenerator gen = new URLGenerator(5);
            //EnvoyInfoTaker taker = new EnvoyInfoTaker(2);
            //String i = taker.findEnvoyeID("Piotr", "Serafin"); // throw Exception
            //taker.getRepairsOf(i);
            StatsGenerator stats = new StatsGenerator(8); // zostawić kadencje tylko w generatorze
            System.out.println(stats.countAvgOutgoings());

        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }

    }
}
