package ui;

import dto.UserDTO;
import functionality.IUserFunctionality;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TUI implements IUI {
    private IUserFunctionality func;
    private Scanner scan;
    private boolean keepGoing = true;

    public TUI(IUserFunctionality func){
        this.func = func;
    }

    @Override
    public void showMenu(){
        while (keepGoing){
            scan = new Scanner(System.in);
            int userChoice;

            System.out.println("-------------------");
            System.out.println("1. Opret bruger");
            System.out.println("2. Vis Enkelt Bruger");
            System.out.println("3. Vis brugere");
            System.out.println("4. Opdater bruger");
            System.out.println("5. Slet bruger");
            System.out.println("0. Afslut program");
            System.out.println("-------------------");
            System.out.println();
            System.out.println("Indtast valg: ");

            try{
                userChoice = scan.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Indtast venligst et tal!\n");
                showMenu();
                break;
            }

            System.out.println();

            switch (userChoice){
                case 1:
                    // Create new UserDTO
                    UserDTO user1 = new UserDTO();
                    System.out.println("|-- Opret bruger -- |");
                    System.out.println("Indtast ønskede userID:");
                    int userID1 = -1;

                    // Check if userID is taken or invalid input
                    while (true){
                        try{
                            userID1 = scan.nextInt();
                            UserDTO testTaken = func.getUser(userID1);
                            System.out.println(userID1 + " er desværre optaget, prøv et andet!");

                        } catch (IUserFunctionality.UserInputException e){
                            user1.setUserId(userID1);
                            break;
                        } catch (InputMismatchException e){
                            System.out.println("Indtast venligst userID i form af et tal!\n");
                        }
                    }
                    // Set user1 username
                    System.out.println("Indtast ønskede brugernavn:");
                    String username1 = scan.next();
                    user1.setUserName(username1);
                    // Set user1 ini
                    System.out.println("Indtast ønskede initialer:");
                    String ini1 = scan.next();
                    user1.setIni(ini1);
                    // Set user1 cpr
                    System.out.println("Indtast cpr-nummer:");
                    String cpr1 = scan.next();
                    user1.setCpr(cpr1);
                    // Set user1 password
                    System.out.println("Indtast ønskede password:");
                    String password1 = scan.next();
                    user1.setPassword(password1);
                    // set user1 role
                    user1.addRole("Operator");
                    System.out.println();
                    /*
                    // -------------------- Testing -------------------- //
                    System.out.println("|------- Testing -------|");
                    System.out.println("Er input = UserDTO info?");
                    System.out.println("Input |vs| UserDTO info");
                    System.out.println("userID:\t\t" + userID1 + "|vs|" + user1.getUserId());
                    System.out.println("username:\t" + username1 + "|vs|" + user1.getUserName());
                    System.out.println("ini:\t\t" + ini1 + "|vs|" + user1.getIni());
                    System.out.println("cpr:\t\t" + cpr1 + "|vs|" + user1.getCpr());
                    System.out.println("pass:\t\t" + password1 + "|vs|" + user1.getPassword());
                    // ---------------------- End ---------------------- //
                    */
                    // Forsøger at oprette brugeren
                    try{
                        func.createUser(user1);
                        System.out.println("Bruger succesfuldt oprettet!\n");
                    } catch (IUserFunctionality.UserInputException e){
                        System.out.println(e.getMessage() + "\n");
                    }
                    break;
                case 2:
                    System.out.println("|-- Vis enkelt bruger --|");
                    System.out.println("Indtast brugerens ID:");
                    UserDTO user2;
                    int userID2;

                    while (true){
                        userID2 = scan.nextInt();
                        try{
                            if (func.getUser(userID2) != null){
                                user2 = func.getUser(userID2);
                                break;
                            } else {
                                System.out.println("Der findes desværre ingen bruger med ID: "
                                        + userID2 + "\nPrøv igen!");
                            }
                        } catch (IUserFunctionality.UserInputException e){
                            System.out.println(e.getMessage() + "\n");
                        } catch (InputMismatchException e){
                            System.out.println("Indtast venligst userID i form af et tal!\n");
                        }
                    }

                    System.out.println(user2 + "\n");
                    break;
                case 3:
                    System.out.println("|-- Vis brugere --|");
                    try{
                        System.out.println(func.getUserList());
                    } catch (Exception e){
                        System.out.println(e.getMessage() + "\n");
                    }
                    break;
                case 4:
                    // Create new UserDTO
                    UserDTO user4 = new UserDTO();
                    System.out.println("|-- Opdater brugere --|");
                    System.out.println("Indtast userID af ønskede bruger:");
                    int userID3;

                    while (true){
                        userID3 = scan.nextInt();
                        try{
                            if (func.getUser(userID3) != null){
                                user4.setUserId(userID3);
                                break;
                            } else {
                                System.out.println("Der findes desværre ingen bruger med ID: "
                                        + userID3 + "\nPrøv igen!");
                            }
                        } catch (IUserFunctionality.UserInputException e){
                            System.out.println(e.getMessage() + "\n");
                        } catch (InputMismatchException e){
                            System.out.println("Indtast venligst userID i form af et tal!\n");
                        }
                    }

                    // Set user4 username
                    System.out.println("Indtast nye ønskede brugernavn:");
                    String username3 = scan.next();
                    user4.setUserName(username3);
                    // Set user4 ini
                    System.out.println("Indtast nye ønskede initialer:");
                    String ini3 = scan.next();
                    user4.setIni(ini3);
                    // Set user4 cpr
                    System.out.println("Indtast nye cpr-nummer:");
                    String cpr3 = scan.next();
                    user4.setCpr(cpr3);
                    // Set user4 password
                    System.out.println("Indtast nye ønskede password:");
                    String password3 = scan.next();
                    user4.setPassword(password3);
                    // Set user4 role
                    System.out.println("Indtast nye ønskede rolle:");
                    String role3 = scan.next();
                    user4.addRole(role3);

                    // -------------------- Testing -------------------- //
                    System.out.println("|------- Testing -------|");
                    System.out.println("userID:\t\t" + userID3);
                    System.out.println("username:\t" + username3);
                    System.out.println("ini:\t\t" + ini3);
                    System.out.println("cpr:\t\t" + cpr3);
                    System.out.println("pass:\t\t" + password3);
                    System.out.println("role:\t\t" + role3);
                    // ---------------------- End ---------------------- //

                    // Attempt to update user and print succes/failure msg
                    try{
                        func.updateUser(user4);
                        System.out.println("Bruger succesfuldt opdateret!\n");
                    } catch (IUserFunctionality.UserInputException e){
                        System.out.println(e.getMessage() + "\n");
                    }
                    break;
                case 5:
                    // Create new UserDTO
                    UserDTO user5;
                    System.out.println("|-- Slet bruger -- |");
                    System.out.println("Indtast userID af ønskede bruger");
                    int userID5;

                    // Check for valid userID or valid input
                    while (true){
                        try{
                            userID5 = scan.nextInt();
                            if (func.getUser(userID5) != null){
                                user5 = func.getUser(userID5);
                                System.out.println(user5);
                                System.out.println("Ønsker du at slette denne bruger? (Y/n)");
                                String yesNo = scan.next();
                                if ("Y".equals(yesNo)){
                                    func.deleteUser(userID5);
                                    System.out.println("Bruger succesfuldt slettet!\n");
                                    break;
                                } else {
                                    System.out.println("Slet bruger aflyst!\n");
                                    break;
                                }
                            }
                        } catch (IUserFunctionality.UserInputException e){
                            System.out.println(e.getMessage() + "\n");
                        } catch (InputMismatchException e){
                            System.out.println("Indtast venligst userID i form af et tal!\n");
                        }
                    }
                    // -------------------- Testing -------------------- //
                    System.out.println("|------- Testing -------|");
                    System.out.println("| Slettet bruger:");
                    System.out.println(user5);
                    // ---------------------- End ---------------------- //
                    break;
                case 0:
                    System.out.println("|-- Afslut program --|");
                    System.out.println("| Bye bye!");
                    keepGoing = false;
                    break;
                default:
                    System.out.println("|-- Indtast venligst en valid mulighed fra listen! --|\n");
                    break;
            }
        }
    }

}
