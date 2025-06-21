
import java.util.ArrayList;
import java.util.Scanner;

public class EnhancedPokedex {

    private static ArrayList<Pokemon> pokedex = new ArrayList<>();
    private static ArrayList<Move> moveList = new ArrayList<>();
    private static ArrayList<Item> itemList = new ArrayList<>();
    private static ArrayList<Trainer> trainerList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        loadDefaultItems();

        boolean running = true;
        while (running) {
            pokedexArt();
            printMainMenu();
            int choice = getIntInput("Enter your choice: ");
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
                    viewAllMoves();
                    break;
                case 6:
                    searchMoves();
                    break;
                case 7:
                    viewAllItems();
                    break;
                case 8:
                    searchItems();
                    break;
                case 9:
                    addTrainer();
                    break;
                case 10:
                    viewAllTrainers();
                    break;
                case 11:
                    searchTrainers();
                    break;
                case 12:
                    manageTrainer();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting. Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // --- Pokemon Management ---
    /**
     * Adds a new Pokémon to the Pokédex after validation.
     */
    private static void addPokemon() {
        System.out.println("\n-- Add New Pokémon --");
        int pokedexNumber = getIntInput("Pokédex Number: ");
        if (isPokedexNumberExists(pokedexNumber)) {
            System.out.println("Error: Pokédex Number already exists.");
            return;
        }
        String name = getStringInput("Name: ");
        if (isPokemonNameExists(name)) {
            System.out.println("Error: Pokémon Name already exists.");
            return;
        }
        String type1 = getStringInput("Type 1: ");
        String type2 = getOptionalStringInput("Type 2 (press Enter if none): ");
        int baseLevel = getIntInput("Base Level: ");
        Integer evolvesFrom = getOptionalIntInput("Evolves From (Pokédex Number, press Enter if none): ");
        Integer evolvesTo = getOptionalIntInput("Evolves To (Pokédex Number, press Enter if none): ");
        Integer evolutionLevel = getOptionalIntInput("Evolution Level (press Enter if none): ");
        int hp = getIntInput("Base HP: ");
        int attack = getIntInput("Base Attack: ");
        int defense = getIntInput("Base Defense: ");
        int speed = getIntInput("Base Speed: ");

        Pokemon p = new Pokemon(pokedexNumber, name, type1, type2, baseLevel, evolvesFrom, evolvesTo, evolutionLevel, hp, attack, defense, speed);
        pokedex.add(p);
        System.out.println("Pokémon added successfully! Default moves assigned: Tackle, Defend.");
    }

    /**
     * Displays all Pokémon in the Pokédex.
     */
    private static void viewAllPokemon() {
        System.out.println("\n-- All Pokémon --");
        if (pokedex.isEmpty()) {
            System.out.println("No Pokémon in the database.");
            return;
        }
        for (Pokemon p : pokedex) {
            System.out.println(p);
        }
    }

    /**
     * Searches Pokémon by name, type, or other attributes.
     */
    private static void searchPokemon() {
        System.out.println("\n-- Search Pokémon --");
        String keyword = getStringInput("Enter keyword (name/type/attribute): ").toLowerCase();
        boolean found = false;
        for (Pokemon p : pokedex) {
            if (p.getPokemonName().toLowerCase().contains(keyword)
                    || p.getPokemonType1().toLowerCase().contains(keyword)
                    || (p.getPokemonType2() != null && p.getPokemonType2().toLowerCase().contains(keyword))
                    || String.valueOf(p.getPokedexNumber()).equals(keyword)) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No Pokémon found matching the search criteria.");
        }
    }

    private static boolean isPokedexNumberExists(int number) {
        for (Pokemon p : pokedex) {
            if (p.getPokedexNumber() == number) {
                return true;
            }
        }
        return false;
    }

    private static boolean isPokemonNameExists(String name) {
        for (Pokemon p : pokedex) {
            if (p.getPokemonName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    // --- Moves Management ---
    /**
     * Adds a new move to the move list after validation.
     */
    private static void addMove() {
        System.out.println("\n-- Add New Move --");
        String name = getStringInput("Move Name: ");
        if (isMoveNameExists(name)) {
            System.out.println("Error: Move name already exists.");
            return;
        }
        String description = getStringInput("Description: ");
        String classification = "";
        while (true) {
            classification = getStringInput("Classification (HM/TM): ").toUpperCase();
            if (classification.equals("HM") || classification.equals("TM")) {
                break;
            }
            System.out.println("Invalid classification. Please enter HM or TM.");
        }
        String type1 = getStringInput("Type 1: ");
        String type2 = getOptionalStringInput("Type 2 (press Enter if none): ");

        Move m = new Move(name, description, classification, type1, type2);
        moveList.add(m);
        System.out.println("Move added successfully!");
    }

    /**
     * Displays all moves in the move list.
     */
    private static void viewAllMoves() {
        System.out.println("\n-- All Moves --");
        if (moveList.isEmpty()) {
            System.out.println("No moves in the database.");
            return;
        }
        for (Move m : moveList) {
            System.out.println(m);
        }
    }

    /**
     * Searches moves by keyword or filter.
     */
    private static void searchMoves() {
        System.out.println("\n-- Search Moves --");
        String keyword = getStringInput("Enter keyword (name/type/classification): ").toLowerCase();
        boolean found = false;
        for (Move m : moveList) {
            if (m.getMoveName().toLowerCase().contains(keyword)
                    || m.getMoveType1().toLowerCase().contains(keyword)
                    || (m.getMoveType2() != null && m.getMoveType2().toLowerCase().contains(keyword))
                    || m.getMoveClassification().toLowerCase().contains(keyword)) {
                System.out.println(m);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No moves found matching the search criteria.");
        }
    }

    private static boolean isMoveNameExists(String name) {
        for (Move m : moveList) {
            if (m.getMoveName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    // --- Items Management ---
    /**
     * Loads the official item list into the itemList.
     */
    private static void loadDefaultItems() {
        // Vitamins
        itemList.add(new Item("HP Up", "Vitamin", "A nutritious drink for Pokémon.", "+10 HP EVs", "P10,000", "P5,000"));
        itemList.add(new Item("Protein", "Vitamin", "A nutritious drink for Pokémon.", "+10 Attack EVs", "P10,000", "P5,000"));
        itemList.add(new Item("Iron", "Vitamin", "A nutritious drink for Pokémon.", "+10 Defense EVs", "P10,000", "P5,000"));
        itemList.add(new Item("Carbos", "Vitamin", "A nutritious drink for Pokémon.", "+10 Speed EVs", "P10,000", "P5,000"));
        itemList.add(new Item("Zinc", "Vitamin", "A nutritious drink for Pokémon.", "+10 Special Defense EVs", "P10,000", "P5,000"));
        // Feathers
        itemList.add(new Item("Health Feather", "Feather", "A feather that slightly increases HP.", "+1 HP EV", "P300", "P150"));
        itemList.add(new Item("Muscle Feather", "Feather", "A feather that slightly increases Attack.", "+1 Attack EV", "P300", "P150"));
        itemList.add(new Item("Resist Feather", "Feather", "A feather that slightly increases Defense.", "+1 Defense EV", "P300", "P150"));
        itemList.add(new Item("Swift Feather", "Feather", "A feather that slightly increases Speed.", "+1 Speed EV", "P300", "P150"));
        // Leveling
        itemList.add(new Item("Rare Candy", "Leveling Item", "A candy that is packed with energy.", "Increases level by 1", "Not sold", "P2,400"));
        // Evolution Stones
        itemList.add(new Item("Fire Stone", "Evolution Stone", "A stone that radiates heat.", "Evolves certain Pokémon.", "P3,000–P5,000", "P1,500"));
        itemList.add(new Item("Water Stone", "Evolution Stone", "A stone with a blue, watery appearance.", "Evolves certain Pokémon.", "P3,000–P5,000", "P1,500"));
        itemList.add(new Item("Thunder Stone", "Evolution Stone", "A stone that sparkles with electricity.", "Evolves certain Pokémon.", "P3,000–P5,000", "P1,500"));
        itemList.add(new Item("Leaf Stone", "Evolution Stone", "A stone with a leaf pattern.", "Evolves certain Pokémon.", "P3,000–P5,000", "P1,500"));
        itemList.add(new Item("Moon Stone", "Evolution Stone", "A stone that glows faintly in the moonlight.", "Evolves certain Pokémon.", "Not sold", "P1,500"));
        itemList.add(new Item("Sun Stone", "Evolution Stone", "A stone that glows like the sun.", "Evolves certain Pokémon.", "P3,000–P5,000", "P1,500"));
        itemList.add(new Item("Shiny Stone", "Evolution Stone", "A stone that sparkles brightly.", "Evolves certain Pokémon.", "P3,000–P5,000", "P1,500"));
        itemList.add(new Item("Dusk Stone", "Evolution Stone", "A dark, ominous stone.", "Evolves certain Pokémon.", "P3,000–P5,000", "P1,500"));
        itemList.add(new Item("Dawn Stone", "Evolution Stone", "A stone that sparkles like the morning sky.", "Evolves certain Pokémon.", "P3,000–P5,000", "P1,500"));
        itemList.add(new Item("Ice Stone", "Evolution Stone", "A stone that is cold to the touch.", "Evolves certain Pokémon.", "P3,000–P5,000", "P1,500"));
    }

    /**
     * Displays all items in the database.
     */
    private static void viewAllItems() {
        System.out.println("\n-- All Items --");
        if (itemList.isEmpty()) {
            System.out.println("No items in the database.");
            return;
        }
        for (Item item : itemList) {
            System.out.println(item.displayItem()); // Call displayItem() method instead of printing item directly
        }
    }

    /**
     * Searches items by keyword.
     */
    private static void searchItems() {
        System.out.println("\n-- Search Items --");
        String keyword = getStringInput("Enter keyword (name/category/effect): ").toLowerCase();
        boolean found = false;
        for (Item i : itemList) {
            if (i.getItemName().toLowerCase().contains(keyword)
                    || i.getItemCategory().toLowerCase().contains(keyword)
                    || i.getItemEffect().toLowerCase().contains(keyword)) {
                System.out.println(i);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No items found matching the search criteria.");
        }
    }

    // --- Error Handling Methods ---
    /**
     * Gets a validated integer input from the user.
     */
    private static int getIntInput(String prompt) {
        int input = -1;
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                input = Integer.parseInt(line);
                if (input < 0) {
                    System.out.println("Please enter a non-negative integer.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return input;
    }

    /**
     * Gets a non-empty string input from the user.
     */
    private static String getStringInput(String prompt) {
        String input = "";
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            } else {
                break;
            }
        }
        return input;
    }

    /**
     * Gets an optional string input from the user.
     */
    private static String getOptionalStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Gets an optional integer input from the user.
     */
    private static Integer getOptionalIntInput(String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
            return null;
        }
        try {
            int value = Integer.parseInt(line);
            if (value < 0) {
                System.out.println("Negative values are not allowed. Skipping.");
                return null;
            }
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Skipping.");
            return null;
        }
    }

    // --- Trainer Management ---
    /**
     * Adds a new trainer to the system.
     */
    private static void addTrainer() {
        System.out.println("\n-- Add New Trainer --");
        int trainerId = getIntInput("Trainer ID: ");
        if (isTrainerIdExists(trainerId)) {
            System.out.println("Error: Trainer ID already exists.");
            return;
        }

        String name = getStringInput("Name: ");
        String birthdate = getStringInput("Birthdate (YYYY-MM-DD): ");
        String sex = getStringInput("Sex (M/F): ");
        String hometown = getStringInput("Hometown: ");
        String description = getStringInput("Description: ");

        Trainer trainer = new Trainer(trainerId, name, birthdate, sex, hometown, description);
        trainerList.add(trainer);
        System.out.println("Trainer " + name + " added successfully with P1,000,000 starting money!");
    }

    /**
     * Displays all trainers in the system.
     */
    private static void viewAllTrainers() {
        System.out.println("\n-- All Trainers --");
        if (trainerList.isEmpty()) {
            System.out.println("No trainers in the database.");
            return;
        }
        for (Trainer trainer : trainerList) {
            System.out.println(trainer.displayTrainer());
        }
    }

    /**
     * Searches trainers by keyword.
     */
    private static void searchTrainers() {
        System.out.println("\n-- Search Trainers --");
        String keyword = getStringInput("Enter keyword (name/hometown/description): ").toLowerCase();
        boolean found = false;
        for (Trainer trainer : trainerList) {
            if (trainer.getTrainerName().toLowerCase().contains(keyword)
                    || trainer.getTrainerHometown().toLowerCase().contains(keyword)
                    || trainer.getTrainerDescription().toLowerCase().contains(keyword)
                    || String.valueOf(trainer.getTrainerId()).equals(keyword)) {
                System.out.println(trainer.displayTrainer());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No trainers found matching the search criteria.");
        }
    }

    /**
     * Manages a specific trainer's activities.
     */
    private static void manageTrainer() {
        System.out.println("\n-- Manage Trainer --");
        if (trainerList.isEmpty()) {
            System.out.println("No trainers in the database.");
            return;
        }

        // Display trainers for selection
        System.out.println("Available Trainers:");
        for (int i = 0; i < trainerList.size(); i++) {
            System.out.println((i + 1) + ". " + trainerList.get(i).getTrainerName() + " (ID: " + trainerList.get(i).getTrainerId() + ")");
        }

        int trainerIndex = getIntInput("Select trainer (enter number): ") - 1;
        if (trainerIndex < 0 || trainerIndex >= trainerList.size()) {
            System.out.println("Invalid trainer selection.");
            return;
        }

        Trainer selectedTrainer = trainerList.get(trainerIndex);
        //manageTrainerMenu(selectedTrainer); para to sa next method tinatamad na ko
    }

    private static boolean isTrainerIdExists(int trainerId) {
        for (Trainer trainer : trainerList) {
            if (trainer.getTrainerId() == trainerId) {
                return true;
            }
        }
        return false;
    }

    // --- Menus and ASCII Arts ---
    private static void printMainMenu() {
        System.out.println("\n                       === Enhanced Pokédex Menu ===");
        System.out.println("                            1. Add New Pokémon");
        System.out.println("                            2. View All Pokémon");
        System.out.println("                            3. Search Pokémon");
        System.out.println("                            4. Add New Move");
        System.out.println("                            5. View All Moves");
        System.out.println("                            6. Search Moves");
        System.out.println("                            7. View All Items");
        System.out.println("                            8. Search Items");
        System.out.println("                            9. Add New Trainer");
        System.out.println("                           10. View All Trainers");
        System.out.println("                           11. Search Trainers");
        System.out.println("                           12. Manage Trainer");
        System.out.println("                            0. Exit");
    }

    private static void pokedexArt() {
        System.out.println("===========================================================================");
        System.out.println("                 ______ _____ _   __ ___________ _______   __");
        System.out.println("                 | ___ \\  _  | | / /|  ___|  _  \\  ___\\ \\ / /");
        System.out.println("                 | |_/ / | | | |/ / | |__ | | | | |__  \\ V / ");
        System.out.println("                 |  __/| | | |    \\ |  __|| | | |  __| /   \\ ");
        System.out.println("                 | |   \\ \\_/ / |\\  \\| |___| |/ /| |___/ /^\\ \\");
        System.out.println("                 \\_|    \\___/\\_| \\_/\\____/|___/ \\____/\\/   \\/ \n");
        System.out.println("===========================================================================");
    }
}
