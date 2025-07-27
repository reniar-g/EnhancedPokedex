package src;

import controller.*;
import java.util.ArrayList;
import model.*;
import util.*;
import view.*;

public class EnhancedPokedexMVC {

    // Array lists to hold data
    private static final ArrayList<Pokemon> pokedex = new ArrayList<>();
    private static final ArrayList<Move> moveList = new ArrayList<>();
    private static final ArrayList<Item> itemList = new ArrayList<>();
    private static final ArrayList<Trainer> trainerList = new ArrayList<>();

    private static PokemonController pokemonController;
    private static MoveController moveController;
    private static ItemController itemController;
    private static TrainerController trainerController;

    private static final String POKEMON_CSV = "src/data/Pokemons.csv";
    private static final String MOVE_CSV = "src/data/Moves.csv";
    private static final String ITEM_CSV = "src/data/Items.csv";
    private static final String TRAINER_CSV = "src/data/Trainers.csv";

    public static final String[] VALID_POKEMON_TYPES = {
        "Normal", "Fire", "Water", "Electric", "Grass", "Ice", "Fighting", "Poison",
        "Ground", "Flying", "Psychic", "Bug", "Rock", "Ghost", "Dragon", "Dark", "Steel", "Fairy"
    };

    // Load data from CSV files into the Array lists
    private static void loadDataFromFiles() {
        pokedex.clear();
        itemList.clear();
        moveList.clear();
        trainerList.clear();

        pokedex.addAll(InputUtils.loadPokemonsFromCSV(POKEMON_CSV));
        itemList.addAll(InputUtils.loadItemsFromCSV(ITEM_CSV));
        moveList.addAll(InputUtils.loadMovesFromCSV(MOVE_CSV));
        trainerList.addAll(InputUtils.loadTrainersFromCSV(TRAINER_CSV));

        // Assign first two moves as default moves for each Pokémon
        if (moveList.size() >= 2) {
            for (Pokemon p : pokedex) {
                p.getMoveSet().clear();
                p.getMoveSet().add(moveList.get(0));
                p.getMoveSet().add(moveList.get(1));
            }
        }
    }

    // Initialize controllers
    private static void initializeControllers() {
        moveController = new MoveController(moveList);
        pokemonController = new PokemonController(pokedex, moveController);
        itemController = new ItemController(itemList);
        trainerController = new TrainerController(trainerList);

        // Ensure all controllers use the same moveList instance
        trainerController.setPokemonController(pokemonController);
        trainerController.setMoveController(moveController);
        trainerController.setItemController(itemController);
    }

    public static void main(String[] args) {
        initializeControllers();
        loadDataFromFiles();
        MainPokedexView gui = new MainPokedexView(pokedex, moveList, itemList, trainerList); // Initialize GUI
        runPokedex(); // Run the terminal-based Pokédex

        InputUtils.closeScanner();
        System.out.println("Thank you for using Enhanced Pokédex!");
    }

    private static void runPokedex() {
        boolean running = true;
        while (running) {
            MenuArtUtils.pokedexArt();
            MenuArtUtils.printMainMenu();
            int choice = InputUtils.getIntInput("Enter your choice: ");

            switch (choice) {
                case 1 ->
                    pokemonController.pokemonManagement(); // Manage Pokémon
                case 2 ->
                    moveController.moveManagement(); // Manage Moves
                case 3 ->
                    itemController.itemManagement(); // Manage Items
                case 4 ->
                    trainerController.trainerManagement(); // Manage Trainers
                case 0 ->
                    running = false; // Exit the application
                default ->
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}
