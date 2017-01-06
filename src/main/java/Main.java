import java.util.Arrays;

public class Main {
    public static void main(String args[]){
        try {
            URLGenerator generator = new URLGenerator(8);
//            System.out.println(generator.generateLayersByID("77"));
            //StatsGenerator stats = new StatsGenerator(generator); // zostawić kadencje tylko w generatorze
            EnvoyInfoTaker taker = new EnvoyInfoTaker(generator);
            taker.fillEnvoyList();
            StatsGenerator gen = new StatsGenerator(taker.getEnvoyList());
            System.out.println(gen);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }

    }
}
