
import org.apache.commons.cli.*;

import java.util.Arrays;

/**
 * Created by Wojciech Zdzarski on 14.12.2016.
 * Class used to Parse CLI with Apache Commons CLI
 */

public class ArgumentParser {
    private String name;
    private String surname;
    private String cadence;
    private String [] args;

    public ArgumentParser(String [] args) {
        this.args = args;
    }




    public void parseCLI() throws Exception{
            CommandLineParser parser = new DefaultParser();
            Options options = getOptions();
            CommandLine cmd = parser.parse(options,this.args);
            for(Option o : cmd.getOptions()){

                if(o.getOpt().equals("c") || o.getLongOpt().equals("cadence")){
                    setCadence(o);
                }
                if(o.getOpt().equals("n") || o.getLongOpt().equals("name")){
                    setNameAndSurname(o);
                }
            }
        }

    private void setNameAndSurname(Option o) throws Exception {
        String [] name_and_surname = o.getValues();
        if(name_and_surname.length != 2) throw new IllegalArgumentException("Niepoprawne Imię i Nazwisko");
        else {
            this.name = name_and_surname[0];
            this.surname = name_and_surname[1];
        }
    }

    private void setCadence(Option o) throws Exception {
        String c = o.getValue();
        if(!c.equals("7") && !c.equals("8"))
            throw new IllegalArgumentException("Niepoprawny numer kadencji");
        else
            this.cadence = c;
    }

    private Options getOptions() {
        Options options = new Options();
        Option name = Option.builder("n")
                .hasArgs()
                .longOpt("name")
                .required(false)
                .numberOfArgs(2)
                .build();

        Option cadence = Option.builder("c")
                .required(true)
                .longOpt("cadence")
                .numberOfArgs(1)
                .build();

        options.addOption(name);
        options.addOption(cadence);
        return options;
    }


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCadence() {
        return cadence;
    }

}
