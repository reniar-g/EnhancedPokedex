
import java.util.ArrayList;
import java.util.Scanner;

// EnhancedPokedex class is the main class for managing Pokémon, moves, items, and trainers.
public class EnhancedPokedex {

    private static ArrayList<Pokemon> pokedex = new ArrayList<>();
    private static ArrayList<Move> moveList = new ArrayList<>();
    private static ArrayList<Item> itemList = new ArrayList<>();
    private static ArrayList<Trainer> trainerList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    private static final String[] VALID_TYPES = {
        "Normal", "Fire", "Water", "Grass", "Electric", "Psychic", "Ice", "Dragon",
        "Dark", "Fairy", "Fighting", "Flying", "Poison", "Ground", "Rock", "Bug", "Ghost", "Steel", "Stellar"};

    private static void loadDefaultPokemons() {
        pokedex.add(new Pokemon(1, "Bulbasaur", "Grass", "Poison", 1, null, 2, 16, 45, 49, 49, 45));
        pokedex.add(new Pokemon(2, "Ivysaur", "Grass", "Poison", 1, 1, 3, 32, 60, 62, 63, 60));
        pokedex.add(new Pokemon(3, "Ivysaur", "Grass", "Poison", 1, 2, null, null, 80, 82, 83, 80));
        pokedex.add(new Pokemon(4, "Charmander", "Fire", null, 1, null, 5, 16, 39, 52, 43, 65));
        pokedex.add(new Pokemon(5, "Charmeleon", "Fire", null, 1, 4, 6, 36, 58, 64, 58, 80));
        pokedex.add(new Pokemon(6, "Charizard", "Fire", "Flying", 1, 5, null, null, 78, 84, 78, 100));
    }

    public static void main(String[] args) {

        loadDefaultPokemons();
        loadDefaultItems();
        loadDefaultMoves();

        boolean running = true;
        while (running) {
            MenuArts.pokedexArt();
            MenuArts.printMainMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    pokemonManagement();
                    break;
                case 2:
                    movesManagement();
                    break;
                case 3:
                    itemManagement();
                    break;
                case 4:
                    trainerManagement();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting. Thank you!");
                    break;
                default:
                    System.out.println("\u001B[31mInvalid choice. Please try again.\u001B[0m");
            }
        }
    }

    // ---------------------------------------------------- Sub-Menu Methods ----------------------------------------------------
    /**
     * Pokemon Management submenu
     */
    private static void pokemonManagement() {
        boolean running = true;
        while (running) {
            MenuArts.pokemonArt();
            MenuArts.printPokemonMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    addPokemon();
                    break;
                case 2:
                    viewAllPokemon();
                    break;
                case 3:
                    viewPokemonDetails();
                    break;
                case 4:
                    searchPokemon();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("\u001B[31mInvalid choice. Please try again.\u001B[0m");
            }
        }
    }

    /**
     * Moves Management submenu
     */
    private static void movesManagement() {
        boolean running = true;
        while (running) {
            MenuArts.movesArt();
            MenuArts.printMovesMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    addMove();
                    break;
                case 2:
                    viewAllMoves();
                    break;
                case 3:
                    searchMoves();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("\u001B[31mInvalid choice. Please try again.\u001B[0m");
            }
        }
    }

    /**
     * Item Management submenu
     */
    private static void itemManagement() {
        boolean running = true;
        while (running) {
            MenuArts.itemsArt();
            MenuArts.printItemMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    viewAllItems();
                    break;
                case 2:
                    searchItems();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("\u001B[31mInvalid choice. Please try again.\u001B[0m");
            }
        }
    }

    /**
     * Trainer Management submenu
     */
    private static void trainerManagement() {
        boolean running = true;
        while (running) {
            MenuArts.trainersArt();
            MenuArts.printTrainerMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    addTrainer();
                    break;
                case 2:
                    viewAllTrainers();
                    break;
                case 3:
                    searchTrainers();
                    break;
                case 4:
                    manageTrainer();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("\u001B[31mInvalid choice. Please try again.\u001B[0m");
            }
        }
    }

    // ---------------------------------------------------- Pokemon Management ----------------------------------------------------
    /**
     * Adds a new Pokémon to the Pokédex after validation.
     */
    private static void addPokemon() {
        System.out.println("\n-- Add New Pokémon --");
        int pokedexNumber = getIntInput("Pokédex Number: ");
        if (isPokedexNumberExists(pokedexNumber)) {
            System.out.println("\u001B[31mError: Pokédex Number already exists.\u001B[0m");
            return;
        }
        String name = getStringInput("Name: ");
        if (isPokemonNameExists(name)) {
            System.out.println("\u001B[31mError: Pokémon Name already exists.\u001B[0m");
            return;
        }

        String type1 = getValidTypeInput("Type 1: ", false);
        String type2 = getValidTypeInput("Type 2 (press Enter if none): ", true);

        Integer evolvesFrom = getOptionalIntInput("Evolves From (Pokédex Number, press Enter if none): ");
        Integer evolvesTo = getOptionalIntInput("Evolves To (Pokédex Number, press Enter if none): ");
        Integer evolutionLevel = getOptionalIntInput("Evolution Level (press Enter if none): ");
        int hp = getIntInput("Base HP: ");
        int attack = getIntInput("Base Attack: ");
        int defense = getIntInput("Base Defense: ");
        int speed = getIntInput("Base Speed: ");

        Pokemon p = new Pokemon(pokedexNumber, name, type1, type2, 1, evolvesFrom, evolvesTo, evolutionLevel, hp, attack, defense, speed);
        pokedex.add(p);
        System.out.println("\n\u001B[32mPokémon added successfully!\u001B[0m\n");
    }

    /**
     * Displays all Pokémon in the Pokédex.
     */
    private static void viewAllPokemon() {
        System.out.println("\n-- All Pokémon --");
        if (pokedex.isEmpty()) {
            System.out.println("\u001B[31mNo Pokémon in the database.\u001B[0m");
            return;
        }
        for (Pokemon p : pokedex) {
            System.out.println(p.displayPokemon());
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
                System.out.println(p.displayPokemon());
                found = true;
            }
        }
        if (!found) {
            System.out.println("\u001B[31mNo Pokémon found matching the search criteria.\u001B[0m");
        }
    }

    /**
     * Views detailed information about a specific Pokémon, including its moves.
     * If no Pokémon are available, it informs the user.
     */
    private static void viewPokemonDetails() {
        System.out.println("\n-- View Pokémon Details --");
        if (pokedex.isEmpty()) {
            System.out.println("\u001B[31mNo Pokémon in the database.\u001B[0m");
            return;
        }

        // Show available Pokémon
        System.out.println("Available Pokémon:");
        for (int i = 0; i < pokedex.size(); i++) {
            Pokemon p = pokedex.get(i);
            System.out.println((i + 1) + ". " + p.getPokemonName() + " (#" + p.getPokedexNumber() + ")");
        }

        int choice = getIntInput("Select Pokémon (enter number): ") - 1;
        if (choice < 0 || choice >= pokedex.size()) {
            System.out.println("\u001B[31mInvalid selection.\u001B[0m");
            return;
        }

        Pokemon selectedPokemon = pokedex.get(choice);
        System.out.println("\n" + selectedPokemon.displayPokemon());

        // Display actual moves from the Pokémon's moveSet
        System.out.println("\n=== Moves ===");
        if (selectedPokemon.getMoveSet().isEmpty()) {
            System.out.println("\u001B[31mThis Pokémon has no moves.\u001B[0m");
        } else {
            for (Move move : selectedPokemon.getMoveSet()) {
                System.out.println("- " + move.getMoveName() + " (" + move.getMoveType1() + ")");
            }
        }

        // Display held item if any
        System.out.println("\n=== Held Item ===");
        if (selectedPokemon.getHeldItem() == null) {
            System.out.println("\u001B[31mNo held item.\u001B[0m");
        } else {
            System.out.println("- " + selectedPokemon.getHeldItem().getItemName());
        }
    }

    /**
     * Checks if a Pokédex number already exists in the Pokédex.
     */
    private static boolean isPokedexNumberExists(int number) {
        for (Pokemon p : pokedex) {
            if (p.getPokedexNumber() == number) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a Pokémon name already exists in the Pokédex.
     */
    private static boolean isPokemonNameExists(String name) {
        for (Pokemon p : pokedex) {
            if (p.getPokemonName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    // ---------------------------------------------------- Moves Management ----------------------------------------------------
    /**
     * Adds a new move to the move list after validation.
     */
    private static void addMove() {
        System.out.println("\n-- Add New Move --");
        String name = getStringInput("Move Name: ");
        if (isMoveNameExists(name)) {
            System.out.println("\u001B[31mError: Move name already exists.\u001B[0m");
            return;
        }
        String description = getStringInput("Description: ");
        String classification = "";
        while (true) {
            classification = getStringInput("Classification (HM/TM): ").toUpperCase();
            if (classification.equals("HM") || classification.equals("TM")) {
                break;
            }
            System.out.println("\u001B[31mInvalid classification. Please enter HM or TM.\u001B[0m");
        }
        String type1 = getValidTypeInput("Type: ", false);
        Move m = new Move(name, description, classification, type1);
        moveList.add(m);
        System.out.println("\n\u001B[32mMove added successfully!\u001B[0m\n");
    }

    /**
     * Displays all moves in the move list.
     */
    private static void viewAllMoves() {
        System.out.println("\n-- All Moves --");
        if (moveList.isEmpty()) {
            System.out.println("\u001B[31mNo moves in the database.\u001B[0m");
            return;
        }
        for (Move m : moveList) {
            System.out.println(m.displayMove());
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
                    || m.getMoveClassification().toLowerCase().contains(keyword)
                    || m.getMoveDescription().toLowerCase().contains(keyword)) {
                System.out.println(m.displayMove());
                found = true;
            }
        }
        if (!found) {
            System.out.println("\u001B[31mNo moves found matching the search criteria.\u001B[0m");
        }
    }

    /**
     * Checks if a move name already exists in the move list.
     */
    private static boolean isMoveNameExists(String name) {
        for (Move m : moveList) {
            if (m.getMoveName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Loads default moves into the move list.
     */
    private static void loadDefaultMoves() {
        // Add default moves that all Pokémon start with
        if (!isMoveNameExists("Tackle")) {
            moveList.add(new Move("Tackle", "A physical attack in which the user charges and slams into the target with its whole body.", "TM", "Normal"));
        }
        if (!isMoveNameExists("Defend")) {
            moveList.add(new Move("Defend", "The user hardens its body's surface like iron, sharply raising its Defense stat.", "TM", "Normal"));
        }
    }

    // ---------------------------------------------------- Items Management ----------------------------------------------------
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
        itemList.add(new Item("Fire Stone", "Evolution Stone", "A stone that radiates heat.", "Evolves certain Pokémon.", "P3,000-P5,000", "P1,500"));
        itemList.add(new Item("Water Stone", "Evolution Stone", "A stone with a blue, watery appearance.", "Evolves certain Pokémon.", "P3,000-P5,000", "P1,500"));
        itemList.add(new Item("Thunder Stone", "Evolution Stone", "A stone that sparkles with electricity.", "Evolves certain Pokémon.", "P3,000-P5,000", "P1,500"));
        itemList.add(new Item("Leaf Stone", "Evolution Stone", "A stone with a leaf pattern.", "Evolves certain Pokémon.", "P3,000-P5,000", "P1,500"));
        itemList.add(new Item("Moon Stone", "Evolution Stone", "A stone that glows faintly in the moonlight.", "Evolves certain Pokémon.", "Not sold", "P1,500"));
        itemList.add(new Item("Sun Stone", "Evolution Stone", "A stone that glows like the sun.", "Evolves certain Pokémon.", "P3,000-P5,000", "P1,500"));
        itemList.add(new Item("Shiny Stone", "Evolution Stone", "A stone that sparkles brightly.", "Evolves certain Pokémon.", "P3,000-P5,000", "P1,500"));
        itemList.add(new Item("Dusk Stone", "Evolution Stone", "A dark, ominous stone.", "Evolves certain Pokémon.", "P3,000-P5,000", "P1,500"));
        itemList.add(new Item("Dawn Stone", "Evolution Stone", "A stone that sparkles like the morning sky.", "Evolves certain Pokémon.", "P3,000-P5,000", "P1,500"));
        itemList.add(new Item("Ice Stone", "Evolution Stone", "A stone that is cold to the touch.", "Evolves certain Pokémon.", "P3,000-P5,000", "P1,500"));
    }

    /**
     * Displays all items in the database.
     */
    private static void viewAllItems() {
        System.out.println("\n-- All Items --");
        if (itemList.isEmpty()) {
            System.out.println("\u001B[31mNo items in the database.\u001B[0m");
            return;
        }
        for (Item item : itemList) {
            System.out.println(item.displayItem());
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
                System.out.println(i.displayItem());
                found = true;
            }
        }
        if (!found) {
            System.out.println("\u001B[31mNo items found matching the search criteria.\u001B[0m");
        }
    }

    // ---------------------------------------------------- Error Handling Methods ----------------------------------------------------
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
                    System.out.println("\u001B[31mPlease enter a non-negative integer.\u001B[0m");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31mInvalid input. Please enter a valid integer.\u001B[0m");
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
                System.out.println("\u001B[31mInput cannot be empty. Please try again.\u001B[0m");
            } else {
                break;
            }
        }
        return input;
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
                System.out.println("\u001B[31mNegative values are not allowed. Skipping.\u001B[0m");
                return null;
            }
            return value;
        } catch (NumberFormatException e) {
            System.out.println("\u001B[31mInvalid input. Skipping.\u001B[0m");
            return null;
        }
    }

    /**
     * Gets a valid Pokémon type input from the user. If optional is true,
     * allows empty input.
     */
    private static String getValidTypeInput(String prompt, boolean optional) {
        while (true) {
            displayValidTypes();
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            // Handle optional input
            if (optional && input.isEmpty()) {
                return null;
            }

            // Check if input is empty for required fields
            if (!optional && input.isEmpty()) {
                System.out.println("\u001B[31mType cannot be empty. Please try again.\u001B[0m");
                continue;
            }

            // Validate type
            if (isValidType(input)) {
                return capitalizeFirstLetter(input);
            } else {
                System.out.println("\u001B[31mInvalid type. Please choose from the list above.\u001B[0m");
            }
        }
    }

    /**
     * Gets a valid sex input from the user.
     */
    private static String getValidSex() {
        while (true) {
            String sex = getStringInput("Sex (M/F): ").toUpperCase();
            if (sex.equals("M") || sex.equals("F")) {
                return sex;
            }
            System.out.println("Please enter M or F only.");
        }
    }

    /**
     * Displays all valid Pokémon types
     */
    private static void displayValidTypes() {
        System.out.println("\n        Valid Pokémon Types:");
        System.out.println("Normal, Fire, Water, Grass, Electric,");
        System.out.println("  Psychic, Ice, Dragon, Dark, Fairy,");
        System.out.println("Fighting, Flying, Poison, Ground, Rock,");
        System.out.println("     Bug, Ghost, Steel, Stellar.");
        System.out.println();
    }

    /**
     * Checks if a type is valid.
     */
    private static boolean isValidType(String type) {
        if (type == null || type.trim().isEmpty()) {
            return false;
        }

        for (String validType : VALID_TYPES) {
            if (validType.equalsIgnoreCase(type.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Capitalizes the first letter of a string.
     */
    private static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    // ---------------------------------------------------- Trainer Management ----------------------------------------------------
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
        String sex = getValidSex(); // Use the validation method instead of getStringInput
        String hometown = getStringInput("Hometown: ");
        String description = getStringInput("Description: ");

        Trainer trainer = new Trainer(trainerId, name, birthdate, sex, hometown, description);
        trainerList.add(trainer);
        System.out.println("Trainer " + name + " added successfully!");
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
    }

    /**
     * Checks if a trainer ID already exists in the trainer list.
     */
    private static boolean isTrainerIdExists(int trainerId) {
        for (Trainer trainer : trainerList) {
            if (trainer.getTrainerId() == trainerId) {
                return true;
            }
        }
        return false;
    }
}
