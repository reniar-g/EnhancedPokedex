import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static ArrayList<Pokemon> pokedex = new ArrayList<>();
    private static ArrayList<Move> moveList = new ArrayList<>();
    private static ArrayList<Item> itemList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Item item1 = new Item("HP Up", "Vitamin",
                    "A nutritious drink for Pokemon",
                        "+10 HP EVs", 10000, 5000);
        Item item2 = new Item("Protein", "Vitamin",
                    "A nutritious drink for Pokemon",
                        "+10 Attack EVs", 10000, 5000);
        Item item3 = new Item("Iron", "Vitamin",
                    "A nutritious drink for Pokemon",
                        "+10 Defense EVs", 10000, 5000);
        Item item4 = new Item("Carbos", "Vitamin",
                    "A nutritious drink for Pokemon",
                        "+10 Speed EVs", 10000, 5000);
        Item item5 = new Item("Rare Candy", "Leveling Item",
                    "A candy that is packed with energy",
                        "Increases level by 1 (Stat gain depends on Pokemon's base stats and EVs",
                    0, 2400);
        Item item6 = new Item("Health Feather", "Feather",
                    "A feather slightly increases HP", "+1 HP EV",
                    300, 150);
        Item item7 = new Item("Muscle Feather", "Feather",
                    "A feather slightly increases Attack", "+1 Attack EV",
                    300, 150);
        Item item8 = new Item("Resist Feather", "Feather",
                    "A feather slightly increases Defense", "+1 Defense EV",
                    300, 150);
        Item item9 = new Item("Swift Feather", "Feather",
                    "A feather slightly increases Speed", "+1 Speed EV",
                    300, 150);
        Item item10 = new Item("Zinc", "Vitamin",
                     "A nutritious drink for Pokemon",
                         "+10 Special Defense EVs", 10000, 5000);

        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);
        itemList.add(item4);
        itemList.add(item5);
        itemList.add(item6);
        itemList.add(item7);
        itemList.add(item8);
        itemList.add(item9);
        itemList.add(item10);

        Item stone1 = new Item("Fire Stone", "Evolution Stone",
                    "A stone that radiates heat.",
                        "Evolves Pokemon like Vulpix, Growlithe, Eevee(into Flareon), etc.",
                        3000, 1500);
        Item stone2 = new Item("Water Stone", "Evolution Stone",
                    "A stone with a blue, watery appearance.",
                        "Evolves Pokemon like Poliwhirl, Shellder, Eevee(into Vapereon), etc.",
                        3000, 1500);
        Item stone3 = new Item("Thunder Stone", "Evolution Stone",
                    "A stone that sparkles with electricity.",
                        "Evolves Pokemon like Pikachu, Eevee(into Jolteon), Eelektrik, etc.",
                            3000, 1500);
        Item stone4 = new Item("Leaf Stone", "Evolution Stone",
                            "A stone with a leaf pattern.",
                    "Evolves Pokemon like Gloom, Weepinbell, Exeggcute, etc.",
                                3000, 1500);
        Item stone5 = new Item("Moon Stone","Evolution Stone",
                    "A stone that glows faintly in the moonlight.",
                            "Evolves Pokemon like Nidorina, Clefairy, Jigglypuff, etc.",
                                0, 1500);
        Item stone6 = new Item("Sun Stone", "Evolution Stone",
                                "A stone that glows like the sun.",
                        "Evolves Pokemon like Gloom(into Bellossom), Sunkern, Cottonee, etc.",
                                3000, 1500);
        Item stone7 = new Item("Shiny Stone","Evolution Stone",
                     "A stone that sparkles brighlty",
                        "Evolves Pokemon like Togetic, Roselia, Minccino, etc.",
                                3000, 1500);
        Item stone8 = new Item("Dusk Stone","Evolution Stone",
                                "A dark stone that is ominous in appearance.",
                                "Evolves Pokemon like Murkrow, Misdreavus, Doublade, etc.",
                                3000, 1500);
        Item stone9 = new Item("Dawn Stone", "Evolution Stone",
                                "A stone sparkles like the morning sky.",
                                "Evolves male Kirlia into Gallade, female Snorunt into Froslass.",
                                3000, 1500);
        Item stone10 = new Item("Ice Stone", "Evolution Stone",
                                "A stone that is cold to the touch",
                                "Evolves Pokemon like Alolan, Vulpix, Galarian Darumaka, Eevee(into Glaceon).",
                                3000, 1500);

        itemList.add(stone1);
        itemList.add(stone2);
        itemList.add(stone3);
        itemList.add(stone4);
        itemList.add(stone5);
        itemList.add(stone6);
        itemList.add(stone7);
        itemList.add(stone8);
        itemList.add(stone9);
        itemList.add(stone10);
        
        int choice;

        do {
            System.out.println("\n====ENHANCED POKEDEX====");
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
                    searchPokemon();
                    break;
                case 4:
                    addMove();
                    break;
                case 5:
                    viewMoves();
                    break;
                case 6:
                    searchMoves();
                    break;
                case 7:
                    viewItems();
                    break;
                case 8:
                    searchItems();
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
        System.out.println("\n====ADD NEW POKEMON====");

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

        Pokemon pokemon = new Pokemon(number, name, type1, type2, baseLevel,
                evolvesFrom, evolvesTo, evoLevel, hp, atk, def, spd);
        pokedex.add(pokemon);

        System.out.println("Pokemon ADDED successfully!");
    }

    private static void viewAllPokemon() {
        System.out.println("\n====LIST OF ALL POKEMONS====");
        if (pokedex.isEmpty()) {
            System.out.println("No Pokémon available.");
            return;
        }
        for (Pokemon pokemon : pokedex) {
            System.out.println(pokemon.displayPokemon());
        }
    }

    private static void searchPokemon()
    {
        System.out.println("\n====SEARCH POKEMON====");

        if(pokedex.isEmpty())
        {
            System.out.println("No Pokemons Available");
            return;
        }

        System.out.print("Search Pokemon by Name, Type, or Other Attributes: ");
        String keyword = scanner.nextLine().toLowerCase();

        boolean found = false;

        System.out.println("====POKEMON LIBRARY====");

        for(Pokemon pokemon : pokedex)
        {
            if(pokemon != null)
            {
                if(pokemon.getPokemonName().toLowerCase().contains(keyword)
                        || pokemon.getPokemonType1().toLowerCase().contains(keyword)
                        || pokemon.getPokemonType2().toLowerCase().contains(keyword))
                {
                    System.out.println(pokemon.displayPokemon());
                    found = true;
                }
            }
        }

    }

    private static void addMove()
    {
        System.out.print("Name of Move: ");
        String moveName = scanner.nextLine();

        System.out.print("Move Description: ");
        String moveDescription = scanner.nextLine();

        System.out.print("Move Classification: ");
        String moveClassification = scanner.nextLine();

        System.out.print("Enter Type 1 Move: ");
        String moveType1 = scanner.nextLine();

        System.out.print("Enter Type 2 Move: ");
        String moveType2 = scanner.nextLine();
        if (moveType2.isBlank()) {
            moveType2 = null;
        }

        Move move = new Move(moveName, moveDescription, moveClassification, moveType1, moveType2);
        moveList.add(move);

        System.out.println("Move ADDED Successfully!");
    }

    private static void viewMoves()
    {
        System.out.println("\n====ALL POKEMON MOVES====");

        if(moveList.isEmpty())
        {
            System.out.println("There no moves that have been added. Please add a move!");
        }

        for(Move moves : moveList)
        {
            System.out.println(moves.displayMove());
        }
    }

    private static void searchMoves()
    {
        System.out.println("\n====SEARCH POKEMON MOVES====");

        if(moveList.isEmpty())
        {
            System.out.println("No Pokemon Moves Available");
            return;
        }

        System.out.print("Search for a move: ");
        String keyword = scanner.nextLine().toLowerCase();

        boolean found = false;

        System.out.println("====POKEMON MOVES====");

        for(Move move : moveList)
        {
            if(move != null)
            {
                if(move.getMoveName().toLowerCase().contains(keyword)
                        || move.getMoveType1().toLowerCase().contains(keyword)
                        || move.getMoveType2().toLowerCase().contains(keyword)
                        || move.getMoveClassification().toLowerCase().contains(keyword))
                {
                    System.out.println(move.displayMove());
                    found = true;
                }
            }
        }
    }

    private static void viewItems()
    {
        System.out.println("\n====ALL POKEMON ITEMS====");

        for(Item items : itemList)
        {
            System.out.println(items.displayItem());
        }
    }

    private static void searchItems()
    {
        System.out.println("====SEARCH POKEMON ITEMS====");

        System.out.print("Search for an item: ");
        String keyword = scanner.nextLine().toLowerCase();

        boolean found = false;

        System.out.println("====POKEMON ITEMS====");

        for(Item item : itemList)
        {
            if(item != null)
            {
                if(item.getItemName().toLowerCase().contains(keyword)
                    || item.getItemCategory().toLowerCase().contains(keyword)
                    || item.getItemEffect().toLowerCase().contains(keyword))
                {
                    System.out.println(item.displayItem());
                    found = true;
                }
            }
        }
    }
}
