
import controller.*;
import java.util.ArrayList;
import model.*;
import util.*;

public class EnhancedPokedexMVC {

    private static final ArrayList<Pokemon> pokedex = new ArrayList<>();
    private static final ArrayList<Move> moveList = new ArrayList<>();
    private static final ArrayList<Item> itemList = new ArrayList<>();
    private static final ArrayList<Trainer> trainerList = new ArrayList<>();

    // Controllers
    private static PokemonController pokemonController;
    private static MoveController moveController;
    private static ItemController itemController;
    private static TrainerController trainerController;

    private static void loadDefaultData() {
        loadDefaultPokemons();
        loadDefaultItems();
        loadDefaultMoves();
    }

    private static void initializeControllers() {
        pokemonController = new PokemonController(pokedex, moveList);
        moveController = new MoveController(moveList);
        itemController = new ItemController(itemList);
        trainerController = new TrainerController(trainerList);
    }

    // add default pokemons
    private static void loadDefaultPokemons() {
        pokedex.add(new Pokemon(1, "Bulbasaur", "Grass", "Poison", 1, null, 2, 16, 45, 49, 49, 45));
        pokedex.add(new Pokemon(2, "Ivysaur", "Grass", "Poison", 1, 1, 3, 32, 60, 62, 63, 60));
        pokedex.add(new Pokemon(3, "Venusaur", "Grass", "Poison", 1, 2, null, null, 80, 82, 83, 80));
        pokedex.add(new Pokemon(4, "Charmander", "Fire", null, 1, null, 5, 16, 39, 52, 43, 65));
        pokedex.add(new Pokemon(5, "Charmeleon", "Fire", null, 1, 4, 6, 36, 58, 64, 58, 80));
        pokedex.add(new Pokemon(6, "Charizard", "Fire", "Flying", 1, 5, null, null, 78, 84, 78, 100));
    }

    private static void loadDefaultMoves() {
        moveList.add(new Move("Tackle", "A physical attack in which the user charges and slams into the target with its whole body.", "TM", "Normal", null));
        moveList.add(new Move("Defend", "The user hardens its body's surface like iron, sharply raising its Defense stat.", "TM", "Normal", null));

        // Add default moves to each Pokemon's moveSet (Requirement #3)
        for (Pokemon pokemon : pokedex) {
            pokemon.getMoveSet().add(moveList.get(0)); // Add Tackle
            pokemon.getMoveSet().add(moveList.get(1)); // Add Defend
        }
    }

    // Loads default items into the item list. (Requirement #5)
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

    public static void main(String[] args) {
        initializeControllers(); // Initialize controllers first
        loadDefaultData(); // Load default data into the Array lists
        runPokedex(); // Run the Pokedex

        // Close scanner
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
