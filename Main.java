
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<Pokemon> pokedex = new ArrayList<>();
    private static ArrayList<Move> moveList = new ArrayList<>();
    private static ArrayList<Item> itemList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n==== Enhanced Pokedex ====");
            System.out.println("1. Add New Pokemon");
            System.out.println("2. View All Pokemon");
            System.out.println("3. Search Pokemon");
            System.out.println("4. Add Move");
            System.out.println("5. View Moves");
            System.out.println("6. Search Moves");
            System.out.println("7. View Items");
            System.out.println("8. Search Items");
            System.out.println("0. Exit");
            System.out.print("Select option: ");
            choice = Integer.valueOf(scanner.nextLine());

            switch (choice) {
                case 1:
                    addPokemon();
                    break;
                case 2:
                    viewAllPokemon();
                    break;
                case 3:
                    //searchPokemon();
                    break;
                case 4:
                    //addMove();
                    break;
                case 5:
                    //viewMoves();
                    break;
                case 6:
                    //searchMoves();
                    break;
                case 7:
                    //viewItems();
                    break;
                case 8:
                    //searchItems();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }

        } while (choice != 0);

    }

    // not final since wala pang error handling
    private static void addPokemon() {
        System.out.println("\n=== Add New Pokemon ===");

        System.out.print("Pokedex Number: ");
        int number = Integer.parseInt(scanner.nextLine());

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Type 1: ");
        String type1 = scanner.nextLine();

        System.out.print("Type 2 (press Enter to skip): ");
        String type2 = scanner.nextLine();
        if (type2.isBlank()) {
            type2 = null;
        }

        System.out.print("Base Level: ");
        int baseLevel = Integer.parseInt(scanner.nextLine());

        System.out.print("Evolves From (enter -1 if none): ");
        int evolvesFrom = Integer.parseInt(scanner.nextLine());

        System.out.print("Evolves To (enter -1 if none): ");
        int evolvesTo = Integer.parseInt(scanner.nextLine());

        System.out.print("Evolution Level: ");
        int evoLevel = Integer.parseInt(scanner.nextLine());

        System.out.print("HP: ");
        int hp = Integer.parseInt(scanner.nextLine());

        System.out.print("Attack: ");
        int atk = Integer.parseInt(scanner.nextLine());

        System.out.print("Defense: ");
        int def = Integer.parseInt(scanner.nextLine());

        System.out.print("Speed: ");
        int spd = Integer.parseInt(scanner.nextLine());

        Pokemon p = new Pokemon(number, name, type1, type2, baseLevel,
                evolvesFrom, evolvesTo, evoLevel, hp, atk, def, spd);
        pokedex.add(p);

        System.out.println("Pokemon added successfully!");
    }

    private static void viewAllPokemon() {
        System.out.println("\n=== All Pokemon ===");
        if (pokedex.isEmpty()) {
            System.out.println("No Pokémon available.");
            return;
        }
        for (Pokemon p : pokedex) {
            System.out.println(p.displayPokemon());
        }
    }
}
