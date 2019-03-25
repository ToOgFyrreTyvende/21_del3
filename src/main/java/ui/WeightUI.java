package ui;

import functionality.IUserFunctionality;
import functionality.IWeightFunctionality;

import java.util.Scanner;

public class WeightUI implements IUI {
    private IWeightFunctionality wFunc;
    private IUserFunctionality uFunc; // Might be useful in later assignments
    private Scanner scan;

    // TODO: Will be VERY 'barebones' until func layer is fleshed out
    // TODO: implement check for users
    public WeightUI(IWeightFunctionality wFunc){
        this.wFunc = wFunc;
    }

    @Override
    public void showMenu(){
        while (true){
            scan = new Scanner(System.in);
            System.out.println("\nTrin 1:");
            System.out.println("Vægten beder om, at der indtastes operatørnummer.");
            String opNumStr = wFunc.requestInput("Indtast operatørnummer:");
            System.out.println("Trin 2:");
            System.out.println("Operatør indtaster brugernummer:");
            System.out.println("Indtastet data:" + opNumStr);
            int opNum = 0;
            try{
                opNum = Integer.parseInt(opNumStr);
            } catch (NumberFormatException e){
                System.out.println("Ugyldigt tal: " + opNumStr);
                System.out.println("Tryk enter for at starte forfra...");
                scan.nextLine();
                showMenu();
                break;
            }

            System.out.println("\nTrin 3:");
            System.out.println("Operatørens navn findes i databasen og vises på vægten:");
            if (opNum == 12){
                System.out.println("Bruger fundet: Anders And");
                System.out.println("\nTrin 4:");
                System.out.println("Operatøren kvitterer for at navnet er korrekt");
                if(!wFunc.getConfirmation("Bruger fundet: Anders And, tryk ok.")) continue;
            } else {
                System.out.println("Ingen bruger fundet med nummer: " + opNumStr);
                System.out.println("Tryk enter for at starte forfra...");
                scan.nextLine();
                showMenu();
                break;
            }

            System.out.println("\nTrin 5:");
            System.out.println("Vægten beder om, at der indtastes batch nummer:");
            String batchNumStr = wFunc.requestInput("Indtast batch nummer");
            System.out.println("Trin 6:");
            System.out.println("Operatør indtaster batch nummer:");
            System.out.println("Indtastet data:" + batchNumStr);
            int batchNum = 0;
            try{
                batchNum = Integer.parseInt(batchNumStr);
            } catch (NumberFormatException e){
            }
            if (batchNum == 1234){
                System.out.println("Batch genkendt.");
                System.out.println(batchNum + ": salt");
            } else {
                System.out.println("Batch ikke genkendt... Fortsætter");
            }

            System.out.println("\nTrin 7:");
            System.out.println("Operatøren instrueres om, at vægten skal være ubelastet:");
            if(!wFunc.getConfirmation("Sørg for vægt er ubelastet, tryk ok")) continue;
            System.out.println("Trin 8: Operatør kvitterer");

            System.out.println("Trin 9: Vægten tareres");
            wFunc.taraWeight();

            System.out.println("\nTrin 10:");
            System.out.println("Operatøren instrueres om, at placere tara på vægten:");
            if(!wFunc.getConfirmation("Placer tom beholder på vægt, tryk ok")) continue;
            System.out.println("Trin 11: Operatør kvitterer");

            System.out.println("Trin 12: Beholders vægt registreres");
            String taraWeightStr = wFunc.getWeight();
            double taraWeight = 0d;
            try{
                taraWeight = Double.parseDouble(taraWeightStr);
            } catch (NumberFormatException e){
                e.printStackTrace();
                System.out.println("wtf happened...?");
                System.out.println("... Noget gik meget galt... " +
                        "tryk enter for at starte forfra...");
                scan.nextLine();
            }
            System.out.println("Vægt registreret: " + taraWeight);

            System.out.println("Trin 13: Vægtens tareres");
            wFunc.taraWeight();

            System.out.println("\nTrin 14:");
            System.out.println("Operatør instrueres i at placere netto på vægten:");
            if(!wFunc.getConfirmation("Placer netto på vægt, tryk ok")) continue;
            System.out.println("Trin 15: Op kvitterer");

            System.out.println("Trin 16: Nettovægt registreres:");
            String nettoWeightStr = wFunc.getWeight();
            double nettoWeight = 0d;
            try{
                nettoWeight = Double.parseDouble(nettoWeightStr);
            } catch (NumberFormatException e){
                e.printStackTrace();
                System.out.println("Wtf happened...?");
                System.out.println("... Noget gik meget galt... " +
                        "tryk enter for at starte forfra...");
                scan.nextLine();
            }
            System.out.println("Vægt registreret: " + nettoWeight);

            System.out.println("Trin 17: Vægt tareres");
            wFunc.taraWeight();

            System.out.println("\nTrin 18: Op instrueres i at fjerne brutto fra vægt");
            if(!wFunc.getConfirmation("Fjern brutto fra vægt, tryk ok")) continue;
            System.out.println("Trin 19: Op: ok");

            System.out.println("Trin 20: Bruttovægt registreres (negativ)");
            String bruttoWeightStr = wFunc.getWeight();
            double bruttoWeight = 0d;
            try{
                bruttoWeight = Double.parseDouble(bruttoWeightStr);
            } catch (NumberFormatException e){
                e.printStackTrace();
                System.out.println("Wtf happened...?");
                System.out.println("... Noget gik meget galt... " +
                        "tryk enter for at starte forfra...");
                scan.nextLine();
            }
            System.out.println("Vægt registreret: " + bruttoWeight);

            System.out.println("Trin 21: Udskriv OK på vægt");
            wFunc.getConfirmation("Afvejning fuldført, fjern evt. vægt");
            System.out.println("Trin 22: Op: ok");

            System.out.println("Trin 23: tara vægt");
            wFunc.taraWeight();
            System.out.println("Vægt efter tara: " + wFunc.getWeight());

            System.out.println("Program fuldført... " +
                    "Tryk enter for at starte forfra");
            if (!"".equals(scan.nextLine())){
                break;
            }
        }
    }
}
