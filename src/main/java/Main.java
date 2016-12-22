


public class Main {
    public static void main(String args[]){
        String b = "";
        String a = "https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions[poslowie.kadencja]=8";
        try {
            b = WebApiParser.readUrl(a);
            System.out.println(b);
        }
        catch(Exception e){
            e.getMessage();
        }

    }
}
