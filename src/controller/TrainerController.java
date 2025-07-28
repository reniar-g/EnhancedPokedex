package controller;

import java.util.ArrayList;
import java.util.List;
import model.*;
import util.*;

public class TrainerController {

    private List<Trainer> trainerList;
    private ItemController itemController;
    private PokemonController pokemonController;
    private MoveController moveController;

    public TrainerController(ArrayList<Trainer> trainerList) {
        this.trainerList = trainerList;
    }

    public void setItemController(ItemController itemController) {
        this.itemController = itemController;
    }

    public void setPokemonController(PokemonController pokemonController) {
        this.pokemonController = pokemonController;
    }

    public void setMoveController(MoveController moveController) {
        this.moveController = moveController;
    }

    public ItemController getItemController() {
        return itemController;
    }

    public PokemonController getPokemonController() {
        return pokemonController;
    }

    public MoveController getMoveController() {
        return moveController;
    }

    /**
     * Trainer Management submenu
     */
    public void trainerManagement() {
        boolean running = true;
        while (running) {
            MenuArtUtils.trainersArt();
            MenuArtUtils.printTrainerMenu();
            int choice = InputUtils.getIntInput("Enter your choice: ");
            switch (choice) {
                case 1 ->
                    addTrainer();
                case 2 ->
                    viewAllTrainers();
                case 3 ->
                    searchTrainers();
                case 4 ->
                    manageTrainer();
                case 5 ->
                    handleItemTransaction(true);  // Buy Item
                case 6 ->
                    handleItemTransaction(false); // Sell Item
                case 7 ->
                    manageTrainerPokemons();
                case 0 ->
                    running = false;
                default ->
                    System.out.println("\u001B[31mInvalid choice. Please try again.\u001B[0m");
            }
        }
    }

    public List<Trainer> getTrainerList() {
        return trainerList;
    }

    /**
     * Adds a new trainer to the system.
     */
    private void addTrainer() {
        System.out.println("\n-- Add New Trainer --");
        int trainerId = InputUtils.getIntInput("Trainer ID: ");
        if (isTrainerIdExists(trainerId)) {
            System.out.println("Error: Trainer ID already exists.");
            return; // Exit if ID already exists
        }

        String name = InputUtils.getStringInput("Name: ");
        String birthdate = InputUtils.getStringInput("Birthdate (YYYY-MM-DD): ");
        String sex = getValidSex();
        String hometown = InputUtils.getStringInput("Hometown: ");
        String description = InputUtils.getStringInput("Description: ");

        Trainer trainer = new Trainer(trainerId, name, birthdate, sex, hometown, description);

        Item rareCandy = itemController.getItemByName("Rare Candy");

        if (rareCandy != null) {
            for (int i = 0; i < 5; i++) {
                trainer.getInventory().add(rareCandy);
            }
        }

        trainerList.add(trainer);
        System.out.println("Trainer " + name + " added successfully!");
    }

    /**
     * Displays all trainers in the system.
     */
    private void viewAllTrainers() {
        System.out.println("\n-- All Trainers --");
        Trainer.displayTrainerHeader();
        if (trainerList.isEmpty()) {
            System.out.println("No trainers in the database.");
            return; // Exit if no trainers are available
        }
        for (Trainer trainer : trainerList) {
            trainer.displayTrainer();
        }
    }

    /**
     * Searches trainers by keyword.
     */
    private void searchTrainers() {
        System.out.println("\n-- Search Trainers --");
        String keyword = InputUtils.getStringInput("Enter keyword (name/hometown/description): ").toLowerCase();
        boolean found = false;
        for (Trainer trainer : trainerList) {
            if (trainer.getTrainerName().toLowerCase().contains(keyword)
                    || trainer.getTrainerHometown().toLowerCase().contains(keyword)
                    || trainer.getTrainerDescription().toLowerCase().contains(keyword)
                    || String.valueOf(trainer.getTrainerId()).equals(keyword)) {
                trainer.displayTrainer();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No trainers found matching the search criteria.");
        }
    }

    /**
     * Checks if a trainer ID already exists in the trainer list.
     */
    private boolean isTrainerIdExists(int trainerId) {
        for (Trainer trainer : trainerList) {
            if (trainer.getTrainerId() == trainerId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a valid sex input from the user for Trainer.
     */
    public static String getValidSex() {
        while (true) {
            String sex = InputUtils.getStringInput("Sex (M/F): ").toUpperCase();
            if (sex.equals("M") || sex.equals("F")) {
                return sex;
            }
            System.out.println("Please enter M or F only.");
        }
    }

    /**
     * Manages a selected trainer (placeholder implementation lang muna to).
     */
    private void manageTrainer() {
        System.out.println("\n-- Manage Trainer --");
        if (trainerList.isEmpty()) {
            System.out.println("No trainers available to manage.");
            return;
        }
        // List trainers
        for (int i = 0; i < trainerList.size(); i++) {
            Trainer trainer = trainerList.get(i);
            System.out.println((i + 1) + ". " + trainer.getTrainerName() + " (ID: " + trainer.getTrainerId() + ")");
        }
        int choice = InputUtils.getIntInput("Select trainer by number: ") - 1;
        if (choice < 0 || choice >= trainerList.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        Trainer selectedTrainer = trainerList.get(choice);
        System.out.println("Managing trainer: " + selectedTrainer.getTrainerName());

        // Show the trainer's items before allowing item transactions
        System.out.println("\nItems owned by " + selectedTrainer.getTrainerName() + ":");
        ArrayList<Item> inventory = selectedTrainer.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            Item.displayItemHeader();
            for (Item item : inventory) {
                item.displayItem();
            }
        }
    }

    /*
     * Handles item transactions (buying/selling) for a selected trainer.
     * This method allows the user to buy or sell items from/to the trainer's inventory.
     * If buying, it shows the available items and allows the user to select one.
     */
    public void handleItemTransaction(boolean isBuying) {
        Trainer trainer = selectTrainer();
        if (trainer == null) {
            return;
        }

        if (isBuying) { // Buying items
            itemController.viewAllItems();
            ArrayList<Item> items = itemController.getItemList();
            int itemIdx = InputUtils.getIntInput("Enter item number to buy: ") - 1;
            if (itemIdx < 0 || itemIdx >= items.size()) {
                System.out.println("Invalid item selection.");
                return;
            }
            buyItem(trainer, items.get(itemIdx));
        } else { // Selling items
            ArrayList<Item> inventory = trainer.getInventory();
            if (inventory.isEmpty()) {
                System.out.println("Inventory is empty.");
                return;
            }
            System.out.println("Items in inventory:");
            Item.displayItemHeader();
            for (int i = 0; i < inventory.size(); i++) {
                inventory.get(i).displayItem();
            }
            int itemIdx = InputUtils.getIntInput("Enter item number to sell: ") - 1;
            if (itemIdx < 0 || itemIdx >= inventory.size()) {
                System.out.println("Invalid item selection.");
                return;
            }
            sellItem(trainer, inventory.get(itemIdx));
        }
    }

    // Selects a trainer from the list for item transactions.
    private Trainer selectTrainer() {
        if (trainerList.isEmpty()) {
            System.out.println("No trainers available.");
            return null;
        }
        System.out.println("Select a trainer:");
        for (int i = 0; i < trainerList.size(); i++) {
            Trainer t = trainerList.get(i);
            System.out.println((i + 1) + ". " + t.getTrainerName() + " (ID: " + t.getTrainerId() + ")");
        }
        int idx = InputUtils.getIntInput("Enter trainer number: ") - 1;
        if (idx < 0 || idx >= trainerList.size()) {
            System.out.println("Invalid trainer selection.");
            return null;
        }
        return trainerList.get(idx);
    }

    // Handles buying and selling items for the selected trainer.
    // If buying, it checks if the trainer has enough money and adds the item to their inventory.
    private void buyItem(Trainer trainer, Item item) {
        if (trainer == null || item == null) {
            System.out.println("Invalid trainer or item.");
            return;
        }

        try {
            double price = Double.parseDouble(item.getItemPrice().replace("P", "").replace(",", ""));
            if (trainer.getTrainerMoney() >= price) {
                trainer.setTrainerMoney(trainer.getTrainerMoney() - price);
                trainer.getInventory().add(item);
                System.out.printf("%s bought %s. New money: P%.2f%n",
                        trainer.getTrainerName(), item.getItemName(), trainer.getTrainerMoney());
            } else {
                System.out.println("Not enough money to buy this item.");
            }
        } catch (NumberFormatException e) {
            System.out.println("This item cannot be bought.");
        }
    }

    // If selling, it removes the item from the inventory and adds half its value to the trainer's money.
    private void sellItem(Trainer trainer, Item item) {
        if (trainer == null || item == null) {
            System.out.println("Invalid trainer or item.");
            return;
        }

        try {
            double price = Double.parseDouble(item.getItemPrice().replace("P", "").replace(",", ""));
            double sellPrice = price * 0.5;
            if (trainer.getInventory().remove(item)) {
                trainer.setTrainerMoney(trainer.getTrainerMoney() + sellPrice);
                System.out.printf("%s sold %s. New money: P%.2f%n",
                        trainer.getTrainerName(), item.getItemName(), trainer.getTrainerMoney());
            } else {
                System.out.println("Item not found in inventory.");
            }
        } catch (NumberFormatException e) {
            System.out.println("This item cannot be sold.");
        }
    }

    public void useItem() {
        Trainer t = selectTrainer();
        if (t == null) {
            System.out.println("\u001B[31mNo trainer selected.\u001B[0m");
            return;
        }

        if (t.getInventory().isEmpty()) {
            System.out.println("\u001B[31mInventory is empty.\u001B[0m");
            return;
        }

        System.out.println("\n-- Trainer Inventory --");
        Item.displayItemHeader();
        for (int i = 0; i < t.getInventory().size(); i++) {
            System.out.printf("%-3d ", i + 1);
            t.getInventory().get(i).displayItem();
        }

        int itemChoice;

        do {
            itemChoice = InputUtils.getIntInput("\nSelect item to use (0 to cancel): ");

            if (itemChoice == 0) {
                return;
            }

            if (itemChoice < 1 || itemChoice > t.getInventory().size()) {
                System.out.println("Invalid Option. Please try again!");
            }

        } while (itemChoice == 0 || itemChoice < 1 || itemChoice > t.getInventory().size());

        Item selectedItem = t.getInventory().get(itemChoice - 1);

        System.out.println("\n-- Active Pokémon --");
        displayPokemonList(t.getPokemonLineup(), "Lineup");

        int pokemonChoice;

        do {
            pokemonChoice = InputUtils.getIntInput("Select Pokémon to use item on (0 to cancel): ");

            if (pokemonChoice == 0) {
                return;
            }

            if (pokemonChoice < 1 || pokemonChoice > t.getPokemonLineup().size()) {
                System.out.println("Invalid Option. Please try again!");
            }
        } while (pokemonChoice == 0 || pokemonChoice < 1 || pokemonChoice > t.getPokemonLineup().size());

        Pokemon targetPokemon = t.getPokemonLineup().get(pokemonChoice - 1);

        applyItemEffect(selectedItem, targetPokemon);

        if (!selectedItem.getItemCategory().equalsIgnoreCase("Held")) {
            t.getInventory().remove(selectedItem);
        }

        System.out.printf("\u001B[32mUsed %s on %s!\u001B[0m%n",
                selectedItem.getItemName(), targetPokemon.getPokemonName());

    }

    private void applyItemEffect(Item item, Pokemon pokemon) {
        try {

            if (item instanceof Vitamin) {
                Vitamin vitamin = (Vitamin) item;
                switch (item.getItemName()) {
                    case "HP Up":
                        vitamin.useHPUp(pokemon);
                        break;
                    case "Protein":
                        vitamin.useProtein(pokemon);
                        break;
                    case "Iron":
                        vitamin.useIron(pokemon);
                        break;
                    case "Carbos":
                        vitamin.useCarbos(pokemon);
                        break;
                    case "Zinc":
                        vitamin.useZinc(pokemon);
                        break;
                }
                return;
            }

            if (item instanceof Feather) {
                Feather feather = (Feather) item;
                if (item.getItemName().contains("Health")) {
                    feather.useHealthFeather(pokemon);
                } else if (item.getItemName().contains("Muscle")) {
                    feather.useMuscleFeather(pokemon);
                } else if (item.getItemName().contains("Resist")) {
                    feather.useResistFeather(pokemon);
                } else if (item.getItemName().contains("Swift")) {
                    feather.useSwiftFeather(pokemon);
                }
                return;
            }

            // Handle Levelling items
            if (item instanceof Levelling) {
                Levelling levelling = (Levelling) item;
                if (item.getItemName().equals("Rare Candy")) {
                    levelling.useRareCandy(pokemon);
                    System.out.printf("%s grew to level %d!%n",
                            pokemon.getPokemonName(), pokemon.getBaseLevel());
                }
                return;
            }

        } catch (Exception e) {
            System.out.println("\u001B[31mFailed to use item: " + e.getMessage() + "\u001B[0m");
        }
    }

    //Manages Trainer's pokemons
    private void manageTrainerPokemons() {
        System.out.println("\n-- Manage Trainer's Pokémon --");
        if (trainerList.isEmpty()) {
            System.out.println("No trainers available to manage.");
            return;
        }

        Trainer trainer = selectTrainer();
        if (trainer == null) {
            return;
        }

        boolean managing = true;
        while (managing) {
            System.out.println("\n=== Managing Trainer: " + trainer.getTrainerName() + " ===");
            System.out.println("Money: P" + trainer.getTrainerMoney());

            System.out.println("\nActive Pokémon (Max 6):");
            displayPokemonList(trainer.getPokemonLineup(), "Lineup");

            System.out.println("\nStored Pokémon:");
            displayPokemonList(trainer.getPokemonStorage(), "Storage");

            System.out.println("\nOptions:");
            System.out.println("1. Add Pokémon to Lineup");
            System.out.println("2. Switch Pokémon between Lineup and Storage");
            System.out.println("3. Release Pokémon");
            System.out.println("4. Teach Moves to Pokémon");
            System.out.println("5. Manage Held Items");
            System.out.println("6. Back to Trainer Menu");
            int choice = InputUtils.getIntInput("Enter your choice: ");

            switch (choice) {
                case 1 ->
                    addPokemonToTrainer(trainer);
                case 2 ->
                    switchPokemon(trainer);
                case 3 ->
                    releasePokemon(trainer);
                case 4 ->
                    teachPokemonMoves(trainer);
                case 5 ->
                    manageHeldItems(trainer);
                case 6 ->
                    managing = false;
                default ->
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //Adds a Pokemon to Lineup
    private void addPokemonToTrainer(Trainer trainer) {
        System.out.println("\n-- Add Pokémon --");

        ArrayList<Pokemon> pokedex = pokemonController.getPokedex();
        if (pokedex.isEmpty()) {
            System.out.println("No Pokémon available in the Pokédex.");
            return;
        }

        System.out.println("Available Pokémon:");
        Pokemon.displayPokemonHeader();
        for (int i = 0; i < pokedex.size(); i++) {
            pokedex.get(i).displayPokemon();
        }

        int addChoice;

        do {
            addChoice = InputUtils.getIntInput("Select Pokémon based on Option Number to Add (0 to cancel): ");

            if (addChoice == 0) {
                return;
            }

            if (addChoice < 1 || addChoice > pokedex.size()) {
                System.out.println("Invalid selection. Please try again!");
            }

        } while (addChoice == 0 || addChoice < 1 || addChoice > pokedex.size());

        Pokemon originalPokemon = pokedex.get(addChoice - 1);
        Pokemon copyPokemon = new Pokemon(
                originalPokemon.getPokedexNumber(),
                originalPokemon.getPokemonName(),
                originalPokemon.getPokemonType1(),
                originalPokemon.getPokemonType2(),
                originalPokemon.getBaseLevel(),
                originalPokemon.getEvolvesFrom(),
                originalPokemon.getEvolvesTo(),
                originalPokemon.getEvolutionLevel(),
                (int) originalPokemon.getHp(),
                (int) originalPokemon.getAttack(),
                (int) originalPokemon.getDefense(),
                (int) originalPokemon.getSpeed()
        );

        for (Move move : originalPokemon.getMoveSet()) {
            copyPokemon.getMoveSet().add(move);
        }

        if (trainer.getPokemonLineup().size() < 6) {
            trainer.getPokemonLineup().add(copyPokemon);
            System.out.println(copyPokemon.getPokemonName() + " is added to your lineup.");
        } else {
            if (trainer.getPokemonStorage().size() < 20) {
                trainer.getPokemonStorage().add(copyPokemon);
                System.out.println(copyPokemon.getPokemonName() + " is added to storage(Pokémon Lineup is full).");
            } else {
                System.out.println("Pokémon Lineup and Storage are full! Release a Pokémon First.");
            }
        }
    }

    //displays Pokemon's basic info
    private void displayPokemonList(ArrayList<Pokemon> pokemonList, String listType) {
        if (pokemonList.isEmpty()) {
            System.out.println("(" + listType + " is empty)");
            return;
        }

        System.out.printf("%-9s %-13s %-14s %-15s %-6s %-7s %-8s %-8s %-8s %-15s %-13s %-15s%n",
                "Option", "Pokédex #", "Name", "Type", "Level", "HP", "Atk", "Def", "Spd", "Evolves From", "Evolves To", "Evo Level");
        System.out.println("---------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < pokemonList.size(); i++) {
            Pokemon p = pokemonList.get(i);
            String type = p.getPokemonType1();
            if (p.getPokemonType2() != null && !p.getPokemonType2().isEmpty()) {
                type += "/" + p.getPokemonType2();
            }

            System.out.printf("%-9s %-13s %-14s %-15s %-6s %-7s %-8s %-8s %-8s %-15s %-13s %-15s%n",
                    "#" + p.getPokedexNumber(),
                    p.getPokemonName(),
                    "(" + type + ")",
                    "Lv." + p.getBaseLevel(),
                    "HP:" + p.getHp(),
                    "Atk:" + p.getAttack(),
                    "Def:" + p.getDefense(),
                    "Spd:" + p.getSpeed(),
                    "From:" + p.getEvolvesFrom(),
                    "To:" + p.getEvolvesTo(),
                    "EvoLv:" + p.getEvolutionLevel());
        }
    }

    //Switches pokemon
    private void switchPokemon(Trainer trainer) {
        System.out.println("\n-- Switch Pokémon --");
        System.out.println("1. Move from Storage to Lineup");
        System.out.println("2. Move from Lineup to Storage");
        System.out.println("3. Exit");

        int choice = InputUtils.getIntInput("Enter your Choice: ");
        if (choice == 3) {
            return;
        }

        if (choice == 1) {
            if (trainer.getPokemonStorage().isEmpty()) {
                System.out.println("No Pokémon in storage.");
                return;
            }

            if (trainer.getPokemonLineup().size() >= 6) {
                System.out.println("Lineup is full. Release a Pokémon first.");
                return;
            }

            System.out.println("Select Pokémon to Move to Lineup: ");
            displayPokemonList(trainer.getPokemonStorage(), "Storage");

            int pokemonChoice = InputUtils.getIntInput("Enter Pokémon Number(Press 0 to Cancel): ");
            if (pokemonChoice == 0) {
                return;
            }
            if (pokemonChoice < 1 || pokemonChoice > trainer.getPokemonStorage().size()) {
                System.out.println("Invalid Option. Please Try Again!");
                return;
            }

            Pokemon p = trainer.getPokemonStorage().remove(pokemonChoice - 1);
            trainer.getPokemonLineup().add(p);
            System.out.println(p.getPokemonName() + " is moved to Lineup!");
        } else if (choice == 2) {
            if (trainer.getPokemonLineup().isEmpty()) {
                System.out.println("No Pokemon in lineup.");
                return;
            }

            System.out.println("Select Pokémon to Move to Storage: ");
            displayPokemonList(trainer.getPokemonLineup(), "Lineup");

            int pokemonChoice = InputUtils.getIntInput("Enter Pokémon Number(Press 0 to Cancel): ");
            if (pokemonChoice == 0) {
                return;
            }
            if (pokemonChoice < 1 || pokemonChoice > trainer.getPokemonLineup().size()) {
                System.out.println("Invalid selection.");
                return;
            }

            Pokemon p = trainer.getPokemonLineup().remove(pokemonChoice - 1);
            trainer.getPokemonStorage().add(p);
            System.out.println(p.getPokemonName() + " is moved to Storage!");
        } else {
            System.out.println("Invalid Option. Please Try Again!");
        }
    }

    //RELEASES POKEMON
    private void releasePokemon(Trainer trainer) {
        System.out.println("\n-- Release Pokémon --");
        System.out.println("1. Release from Lineup");
        System.out.println("2. Release from Storage");
        System.out.println("3. Exit");

        int choice = InputUtils.getIntInput("Select source: ");
        if (choice == 3) {
            return;
        }

        ArrayList<Pokemon> sourceList = (choice == 1) ? trainer.getPokemonLineup() : trainer.getPokemonStorage();
        String sourceName = (choice == 1) ? "Lineup" : "Storage";

        if (sourceList.isEmpty()) {
            System.out.println("No Pokémon in " + sourceName);
            return;
        }

        System.out.println("Select Pokémon to release:");
        displayPokemonList(sourceList, sourceName);

        int pokemonChoice = InputUtils.getIntInput("Enter Pokémon Number (Press 0 to Cancel): ");
        if (pokemonChoice == 0) {
            return;
        }
        if (pokemonChoice < 1 || pokemonChoice > sourceList.size()) {
            System.out.println("Invalid Option. Please Try Again!");
            return;
        }

        Pokemon released = sourceList.remove(pokemonChoice - 1);
        System.out.println(released.getPokemonName() + " has been released.");
    }

    //Trainer teaches moves to a Pokemon
    private void teachPokemonMoves(Trainer trainer) {
        System.out.println("\n-- Teach Move to Pokémon --");

        if (trainer.getPokemonLineup().isEmpty()) {
            System.out.println("No Pokémon in your lineup.");
            return;
        }

        System.out.println("Select Pokémon to Teach:");
        displayPokemonList(trainer.getPokemonLineup(), "Lineup");

        int pokemonChoice = InputUtils.getIntInput("Enter a Pokémon Number(Press 0 to Cancel): ");
        if (pokemonChoice == 0) {
            return;
        }

        if (pokemonChoice < 1 || pokemonChoice > trainer.getPokemonLineup().size()) {
            System.out.println("Invalid Option. Please Try Again!");
            return;
        }

        Pokemon pokemon = trainer.getPokemonLineup().get(pokemonChoice - 1);
        moveTutorial(pokemon);
    }

    private void moveTutorial(Pokemon pokemon) {
        System.out.println("\n=== Teaching Move to " + pokemon.getPokemonName() + " ===");

        System.out.println("Current Moves: ");

        if (pokemon.getMoveSet().isEmpty()) {
            System.out.println("No Current Moves Being Taught!");
        } else {
            for (int i = 0; i < pokemon.getMoveSet().size(); i++) {
                Move move = pokemon.getMoveSet().get(i);
                System.out.printf("%d. %s (%s)%n", i + 1, move.getMoveName(), move.getMoveClassification());
            }

            ArrayList<Move> allMoves = moveController.getMoveList();
            System.out.println("\nAvailable Moves: ");
            Move.displayMoveHeader();
            for (int i = 0; i < allMoves.size(); i++) {
                System.out.printf("%-3d ", i + 1);
                allMoves.get(i).displayMove();
            }

            int moveChoice;

            do {
                moveChoice = InputUtils.getIntInput("\nSelect a Move Number(#) to Teach(Press 0 to Cancel): ");

                if (moveChoice == 0) {
                    return;
                }

                if (moveChoice < 1 || moveChoice > allMoves.size()) {
                    System.out.println("Invalid Option. Please Try Again!");
                }

            } while (moveChoice == 0 || moveChoice < 1 || moveChoice > allMoves.size());

            Move selectedMove = allMoves.get(moveChoice - 1);

            if (!pokemon.canLearnMove(selectedMove)) {
                System.out.println("\u001B[31m" + pokemon.getPokemonName()
                        + " can't learn " + selectedMove.getMoveName()
                        + " (type mismatch)!\u001B[0m");
                return;
            }

            if (pokemon.getMoveSet().size() >= 4) {
                System.out.println("\n" + pokemon.getPokemonName() + " already knows 4 moves!");

                System.out.println("Select a Move to Delete: ");
                for (int i = 0; i < pokemon.getMoveSet().size(); i++) {
                    Move m = pokemon.getMoveSet().get(i);
                    System.out.printf("%d. %s (%s)%n", i + 1, m.getMoveName(), m.getMoveClassification());
                }

                int deleteChoice;
                Move deleteMove;

                do {

                    deleteChoice = InputUtils.getIntInput("Enter a Move Number to Delete: ");
                    deleteMove = pokemon.getMoveSet().get(deleteChoice - 1);

                    if (deleteChoice < 1) {
                        System.out.println("Invalid option. Please try again");
                    }

                    if (deleteMove.getMoveClassification().equalsIgnoreCase("HM")) {
                        System.out.println("\u001B[31mCannot forget HM moves.\u001B[0m");
                    }

                } while (deleteChoice < 1 || deleteMove.getMoveClassification().equalsIgnoreCase("HM"));

                pokemon.getMoveSet().remove(deleteChoice - 1);
                pokemon.getMoveSet().add(selectedMove);
                System.out.println("\u001B[32m" + pokemon.getPokemonName()
                        + " removed " + deleteMove.getMoveName()
                        + " and learned " + selectedMove.getMoveName() + "!\u001B[0m");

            } else {
                if (pokemon.hasMove(selectedMove.getMoveName())) {
                    System.out.println(pokemon.getPokemonName() + " already knows "
                            + selectedMove.getMoveName() + ".");
                } else if (pokemon.learnMove(selectedMove)) {
                    System.out.println(pokemon.getPokemonName() + " learned "
                            + selectedMove.getMoveName() + "!");
                } else {
                    System.out.println("Failed to teach " + selectedMove.getMoveName() + ".");
                }
            }
        }
    }

    //Manages Held Items
    private void manageHeldItems(Trainer trainer) {
        System.out.println("\n-- Manage Held Items --");

        List<Pokemon> allPokemon = new ArrayList<>();
        allPokemon.addAll(trainer.getPokemonLineup());
        allPokemon.addAll(trainer.getPokemonStorage());

        if (allPokemon.isEmpty()) {
            System.out.println("No Pokémon available to Manage Items.");
            return;
        }

        System.out.println("Select a Pokémon to Manage Held Items:");
        for (int i = 0; i < allPokemon.size(); i++) {
            Pokemon p = allPokemon.get(i);
            String location = (i < trainer.getPokemonLineup().size()) ? "Lineup" : "Storage";
            String heldItem = p.hasHeldItem() ? p.getHeldItem().getItemName() : "None";
            System.out.printf("%d. %s (%s) - Holding: %s%n",
                    i + 1, p.getPokemonName(), location, heldItem);
        }

        int pokemonChoice = InputUtils.getIntInput("Enter Pokémon Number (0 to cancel): ");
        if (pokemonChoice == 0) {
            return;
        }
        if (pokemonChoice < 1 || pokemonChoice > allPokemon.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Pokemon selectedPokemon = allPokemon.get(pokemonChoice - 1);
        managePokemonHeldItem(trainer, selectedPokemon);
    }

    private void managePokemonHeldItem(Trainer trainer, Pokemon pokemon) {
        boolean managing = true;
        while (managing) {
            System.out.println("\nManaging held item for " + pokemon.getPokemonName());
            System.out.println("Currently holding: "
                    + (pokemon.hasHeldItem() ? pokemon.getHeldItem().getItemName() : "None"));

            System.out.println("\nOptions:");
            System.out.println("1. Set Held Item");
            System.out.println("2. Remove Held Item");
            System.out.println("3. Back");

            int choice = InputUtils.getIntInput("Enter your choice: ");
            switch (choice) {
                case 1 ->
                    setPokemonHeldItem(trainer, pokemon);
                case 2 ->
                    removePokemonHeldItem(pokemon);
                case 3 ->
                    managing = false;
                default ->
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void setPokemonHeldItem(Trainer trainer, Pokemon pokemon) {
        ArrayList<Item> itemInventory = trainer.getInventory();
        if (itemInventory.isEmpty()) {
            System.out.println("No Items in Inventory!");
            return;
        }

        System.out.println("\nAvailable Items: ");
        Item.displayItemOptions();
        for (int i = 0; i < itemInventory.size(); i++) {
            System.out.printf("%-6d ", i + 1);
            itemInventory.get(i).displayAddedItems();
        }

        int itemChoice = InputUtils.getIntInput("Select an Item based on Option Number(0 to Cancel): ");
        if (itemChoice == 0) {
            return;
        }
        if (itemChoice < 1 || itemChoice > itemInventory.size()) {
            System.out.println("Invalid Selection!");
            return;
        }

        Item selectedItem = itemInventory.get(itemChoice - 1);
        pokemon.setHeldItem(selectedItem);
        System.out.println(pokemon.getPokemonName() + " is holding " + selectedItem.getItemName());
    }

    private void removePokemonHeldItem(Pokemon pokemon) {
        if (!pokemon.hasHeldItem()) {
            System.out.println(pokemon.getPokemonName() + " is holding nothing!");
            return;
        }

        Item removed = pokemon.removeHeldItem();
        System.out.println(pokemon.getPokemonName() + " no longer holds " + removed.getItemName());
    }

    // For GUI: add a Pokemon to the trainer's lineup or storage
    public static boolean addPokemonToTrainerGUI(Trainer trainer, Pokemon pokemon) {
        if (trainer != null && pokemon != null) {
            return trainer.addPokemonToLineup(pokemon);
        }
        return false;
    }

    // For Terminal: add a Pokemon to the trainer's lineup or storage (can add CLI-specific logic if needed)
    public static boolean addPokemonToTrainerTerminal(Trainer trainer, Pokemon pokemon) {
        if (trainer != null && pokemon != null) {
            return trainer.addPokemonToLineup(pokemon);
        }
        return false;
    }

    // For GUI: add a new trainer to a list (static helper)
    public static void addTrainerToList(java.util.List<Trainer> trainerList, Trainer trainer) {
        if (trainerList != null && trainer != null) {
            trainerList.add(trainer);
        }
    }

}
