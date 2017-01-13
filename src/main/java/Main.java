public class Main {
    public static void main(String args[]) {
        try {
            ArgumentParser a = new ArgumentParser(args);
            a.parseCLI();
            String cadence = a.getCadence();
            String name = a.getName();
            String surname = a.getSurname();
            URLGenerator gen = new URLGenerator(cadence);
            EnvoyInfoTaker taker = new EnvoyInfoTaker(gen,cadence);
            //taker.fillEnvoyList();
            taker.parallelListFill();
            StatsGenerator stats = new StatsGenerator(taker.getEnvoyList(),name,surname);
            System.out.println(stats);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

    }
}
