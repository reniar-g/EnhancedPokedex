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

    public static final String[] VALID_POKEMON_TYPES = {
        "Normal", "Fire", "Water", "Electric", "Grass", "Ice", "Fighting", "Poison",
        "Ground", "Flying", "Psychic", "Bug", "Rock", "Ghost", "Dragon", "Dark", "Steel", "Fairy"
    };

    // Load default data into the Array lists
    private static void loadDefaultData() {
        loadDefaultPokemons();
        loadDefaultItems();
        loadDefaultMoves();
        loadDefaultTrainers();
    }

    // Initialize controllers
    private static void initializeControllers() {
        moveController = new MoveController(moveList);
        pokemonController = new PokemonController(pokedex, moveController);
        itemController = new ItemController(itemList);
        trainerController = new TrainerController(trainerList);

        trainerController.setPokemonController(pokemonController);
        trainerController.setMoveController(moveController);
        trainerController.setItemController(itemController);
    }

    // Add default pokemons
    private static void loadDefaultPokemons() {
        pokedex.add(new Pokemon(1, "Bulbasaur", "Grass", "Poison", 1, null, 2, 16, 45, 49, 49, 45));
        pokedex.add(new Pokemon(2, "Ivysaur", "Grass", "Poison", 1, 1, 3, 32, 60, 62, 63, 60));
        pokedex.add(new Pokemon(3, "Venusaur", "Grass", "Poison", 1, 2, null, null, 80, 82, 83, 80));
        pokedex.add(new Pokemon(4, "Charmander", "Fire", null, 1, null, 5, 16, 39, 52, 43, 65));
        pokedex.add(new Pokemon(5, "Charmeleon", "Fire", null, 1, 4, 6, 36, 58, 64, 58, 80));
        pokedex.add(new Pokemon(6, "Charizard", "Fire", "Flying", 1, 5, null, null, 78, 84, 78, 100));
        pokedex.add(new Pokemon(7, "Squirtle", "Water", null, 1, null, 8, 16, 44, 48, 65, 43));
        pokedex.add(new Pokemon(8, "Wartortle", "Water", null, 1, 7, 9, 36, 59, 63, 80, 58));
        pokedex.add(new Pokemon(9, "Blastoise", "Water", null, 1, 8, null, null, 79, 83, 100, 78));
        pokedex.add(new Pokemon(10, "Caterpie", "Bug", null, 1, null, 11, 7, 45, 30, 35, 45));
        pokedex.add(new Pokemon(11, "Metapod", "Bug", null, 1, 10, 12, 10, 50, 20, 25, 30));
        pokedex.add(new Pokemon(12, "Butterfree", "Bug", "Flying", 1, 11, null, null, 60, 45, 50, 70));
        pokedex.add(new Pokemon(13, "Weedle", "Bug", "Poison", 1, null, 14, 7, 40, 35, 30, 50));
        pokedex.add(new Pokemon(14, "Kakuna", "Bug", "Poison", 1, 13, 15, 10, 45, 25, 25, 35));
        pokedex.add(new Pokemon(15, "Beedrill", "Bug", "Poison", 1, 14, null, null, 65, 80, 40, 75));
        pokedex.add(new Pokemon(16, "Pidgey", "Normal", "Flying", 1, null, 17, 18, 40, 45, 40, 56));
        pokedex.add(new Pokemon(17, "Pidgeotto", "Normal", "Flying", 1, 16, 18, 36, 63, 60, 55, 71));
        pokedex.add(new Pokemon(18, "Pidgeot", "Normal", "Flying", 1, 17, null, null, 83, 80, 75, 91));
        pokedex.add(new Pokemon(19, "Rattata", "Normal", null, 1, null, 20, 20, 30, 56, 35, 72));
        pokedex.add(new Pokemon(20, "Raticate", "Normal", null, 1, 19, null, null, 65, 81, 60, 97));
        pokedex.add(new Pokemon(21, "Spearow", "Normal", "Flying", 1, null, 22, 20, 40, 60, 30, 70));
        pokedex.add(new Pokemon(22, "Fearow", "Normal", "Flying", 1, 21, null, null, 65, 90, 65, 100));
        pokedex.add(new Pokemon(23, "Ekans", "Poison", null, 1, null, 24, 22, 35, 60, 44, 55));
        pokedex.add(new Pokemon(24, "Arbok", "Poison", null, 1, 23, null, null, 60, 85, 69, 80));
        pokedex.add(new Pokemon(25, "Pikachu", "Electric", null, 1, null, 26, 22, 35, 55, 40, 90));
        pokedex.add(new Pokemon(26, "Raichu", "Electric", null, 1, 25, null, null, 60, 90, 55, 110));
        pokedex.add(new Pokemon(27, "Sandshrew", "Ground", null, 1, null, 28, 22, 50, 75, 85, 40));
        pokedex.add(new Pokemon(28, "Sandslash", "Ground", null, 1, 27, null, null, 75, 100, 110, 65));
        pokedex.add(new Pokemon(29, "Nidoran ♀", "Poison", null, 1, null, 30, 16, 55, 47, 52, 41));
        pokedex.add(new Pokemon(30, "Nidorina", "Poison", null, 1, 29, 31, 32, 70, 62, 67, 56));
        pokedex.add(new Pokemon(31, "Nidoqueen", "Poison", "Ground", 1, 30, null, null, 90, 82, 87, 76));
        pokedex.add(new Pokemon(32, "Nidoran ♂", "Poison", null, 1, null, 33, 16, 46, 57, 40, 50));
        pokedex.add(new Pokemon(33, "Nidorino", "Poison", null, 1, 32, 34, 36, 61, 72, 57, 65));
        pokedex.add(new Pokemon(34, "Nidoking", "Poison", "Ground", 1, 33, null, null, 81, 102, 77, 85));
        pokedex.add(new Pokemon(35, "Clefairy", "Fairy", null, 1, null, 36, 35, 70, 45, 48, 35));
        pokedex.add(new Pokemon(36, "Clefable", "Fairy", null, 1, 35, null, null, 95, 70, 73, 60));
        pokedex.add(new Pokemon(37, "Vulpix", "Fire", null, 1, null, 38, 29, 38, 41, 40, 65));
        pokedex.add(new Pokemon(38, "Ninetales", "Fire", null, 1, 37, null, null, 73, 76, 75, 100));
        pokedex.add(new Pokemon(39, "Jigglypuff", "Normal", "Fairy", 1, null, 40, 20, 115, 45, 20, 25));
        pokedex.add(new Pokemon(40, "Wigglytuff", "Normal", "Fairy", 1, 39, null, null, 140, 70, 45, 45));
        pokedex.add(new Pokemon(41, "Zubat", "Poison", "Flying", 1, null, 42, 22, 40, 45, 35, 55));
        pokedex.add(new Pokemon(42, "Golbat", "Poison", "Flying", 1, 41, null, null, 75, 80, 70, 90));
        pokedex.add(new Pokemon(43, "Oddish", "Grass", "Poison", 1, null, 44, 21, 45, 50, 55, 30));
        pokedex.add(new Pokemon(44, "Gloom", "Grass", "Poison", 1, 43, 45, 32, 60, 65, 70, 40));
        pokedex.add(new Pokemon(45, "Vileplume", "Grass", "Poison", 1, 44, null, null, 75, 80, 85, 50));
        pokedex.add(new Pokemon(46, "Paras", "Bug", "Grass", 1, null, 47, 24, 35, 70, 55, 25));
        pokedex.add(new Pokemon(47, "Parasect", "Bug", "Grass", 1, 46, null, null, 60, 95, 80, 30));
        pokedex.add(new Pokemon(48, "Venonat", "Bug", "Poison", 1, null, 49, 31, 60, 55, 50, 45));
        pokedex.add(new Pokemon(49, "Venomoth", "Bug", "Poison", 1, 48, null, null, 70, 65, 60, 90));
        pokedex.add(new Pokemon(50, "Diglett", "Ground", null, 1, null, 51, 26, 10, 55, 25, 95));
        pokedex.add(new Pokemon(51, "Dugtrio", "Ground", null, 1, 50, null, null, 35, 80, 50, 120));
        pokedex.add(new Pokemon(52, "Meowth", "Normal", null, 1, null, 53, 28, 40, 45, 35, 90));
        pokedex.add(new Pokemon(53, "Persian", "Normal", null, 1, 52, null, null, 65, 70, 60, 115));
        pokedex.add(new Pokemon(54, "Psyduck", "Water", null, 1, null, 55, 33, 50, 52, 48, 55));
        pokedex.add(new Pokemon(55, "Golduck", "Water", null, 1, 54, null, null, 80, 82, 78, 85));
    }

    // Load default trainers into the trainer list.
    private static void loadDefaultTrainers() {
        trainerList.add(new Trainer(1, "Ash Ketchum", "1987-05-22", "M", "Pallet Town, Kanto", "The Show's Protagonist"));
        trainerList.add(new Trainer(2, "Rainer Gonzaga", "2005-12-23", "M", "Enhanced Pokedex Team", "The Creator of Enhanced Pokedex"));
        trainerList.add(new Trainer(3, "Joshua Del Mundo", "2001-01-01", "M", "Enhanced Pokedex Team", "The Co-Creator of Enhanced Pokedex"));
        trainerList.add(new Trainer(4, "Misty Mizuyawa", "1985-04-01", "F", "Cerulean City, Kanto", "Water-type Pokémon Trainer"));
        trainerList.add(new Trainer(5, "Brock Harrison", "1983-06-14", "M", "Pewter City, Kanto", "Rock-type Pokémon Trainer"));
    }

    // Add default moves to each Pokemon's moveSet (Requirement #3)
    private static void loadDefaultMoves() {
        // Default Moves (Requirement #3)
        moveList.add(new Move("Tackle", "A physical attack in which the user charges and slams into the target with its whole body.", "TM", "Normal", null));
        moveList.add(new Move("Defend", "The user hardens its body's surface like iron, sharply raising its Defense stat.", "TM", "Normal", null));

        // 5 Default HMs
        moveList.add(new Move("Cut", "Cuts down small trees.", "HM", "Normal", null));
        moveList.add(new Move("Fly", "Flies to a previously visited town.", "HM", "Flying", null));
        moveList.add(new Move("Surf", "Rides waves across water.", "HM", "Water", null));
        moveList.add(new Move("Strength", "Moves heavy boulders.", "HM", "Normal", null));
        moveList.add(new Move("Flash", "Lights up dark caves.", "HM", "Electric", null));

        // 10 Default TMs
        moveList.add(new Move("Thunderbolt", "A strong electric attack. May paralyze the target.", "TM", "Electric", null));
        moveList.add(new Move("Ice Beam", "A beam of ice is shot at the foe. May freeze the target.", "TM", "Ice", null));
        moveList.add(new Move("Flamethrower", "A powerful fire attack. May burn the target.", "TM", "Fire", null));
        moveList.add(new Move("Psychic", "A strong psychic attack.", "TM", "Psychic", null));
        moveList.add(new Move("Earthquake", "A ground-shaking attack.", "TM", "Ground", null));
        moveList.add(new Move("Hyper Beam", "A very powerful attack.", "TM", "Normal", null));
        moveList.add(new Move("Rock Slide", "Large rocks are thrown at the foe. May cause flinching.", "TM", "Rock", null));
        moveList.add(new Move("Quick Attack", "An extremely fast attack that always strikes first.", "TM", "Normal", null));
        moveList.add(new Move("Shadow Ball", "Hurls a shadowy blob at the foe. May lower the target's Special Defense.", "TM", "Ghost", null));
        moveList.add(new Move("Toxic", "Badly poisons the foe.", "TM", "Poison", null));

        for (Pokemon pokemon : pokedex) {
            pokemon.getMoveSet().add(moveList.get(0)); // Add Tackle
            pokemon.getMoveSet().add(moveList.get(1)); // Add Defend
        }
    }

    // Loads default items into the item list. (Requirement #5)
    private static void loadDefaultItems() {
        // Vitamins
        itemList.add(new Vitamin(1, "HP Up", "Vitamin", "A nutritious drink for Pokémon.", "+10 HP EVs", "10,000", "P5,000"));
        itemList.add(new Vitamin(2, "Protein", "Vitamin", "A nutritious drink for Pokémon.", "+10 Attack EVs", "P10,000", "P5,000"));
        itemList.add(new Vitamin(3, "Iron", "Vitamin", "A nutritious drink for Pokémon.", "+10 Defense EVs", "P10,000", "P5,000"));
        itemList.add(new Vitamin(4, "Carbos", "Vitamin", "A nutritious drink for Pokémon.", "+10 Speed EVs", "P10,000", "P5,000"));
        itemList.add(new Vitamin(5, "Zinc", "Vitamin", "A nutritious drink for Pokémon.", "+10 Special Defense EVs", "P10,000", "P5,000"));
        // Feathers
        itemList.add(new Feather(6, "Health Feather", "Feather", "A feather that slightly increases HP.", "+1 HP EV", "P300", "P150"));
        itemList.add(new Feather(7, "Muscle Feather", "Feather", "A feather that slightly increases Attack.", "+1 Attack EV", "P300", "P150"));
        itemList.add(new Feather(8, "Resist Feather", "Feather", "A feather that slightly increases Defense.", "+1 Defense EV", "P300", "P150"));
        itemList.add(new Feather(9, "Swift Feather", "Feather", "A feather that slightly increases Speed.", "+1 Speed EV", "P300", "P150"));
        // Leveling
        itemList.add(new Levelling(10, "Rare Candy", "Leveling Item", "A candy that is packed with energy.", "Increases level by 1", "Not sold", "P2,400"));
        // Evolution Stones
        itemList.add(new EvolutionStone(11, "Fire Stone", "Evolution Stone", "A stone that radiates heat.", "Evolves certain Pokémon.", "P3,000", "P1,500"));
        itemList.add(new EvolutionStone(12, "Water Stone", "Evolution Stone", "A stone with a blue, watery appearance.", "Evolves certain Pokémon.", "P3,000", "P1,500"));
        itemList.add(new EvolutionStone(13, "Thunder Stone", "Evolution Stone", "A stone that sparkles with electricity.", "Evolves certain Pokémon.", "P3,000", "P1,500"));
        itemList.add(new EvolutionStone(14, "Leaf Stone", "Evolution Stone", "A stone with a leaf pattern.", "Evolves certain Pokémon.", "P3,000", "P1,500"));
        itemList.add(new EvolutionStone(15, "Moon Stone", "Evolution Stone", "A stone that glows faintly in the moonlight.", "Evolves certain Pokémon.", "Not sold", "P1,500"));
        itemList.add(new EvolutionStone(16, "Sun Stone", "Evolution Stone", "A stone that glows like the sun.", "Evolves certain Pokémon.", "P3,000", "P1,500"));
        itemList.add(new EvolutionStone(17, "Shiny Stone", "Evolution Stone", "A stone that sparkles brightly.", "Evolves certain Pokémon.", "P3,000", "P1,500"));
        itemList.add(new EvolutionStone(18, "Dusk Stone", "Evolution Stone", "A dark, ominous stone.", "Evolves certain Pokémon.", "P3,000", "P1,500"));
        itemList.add(new EvolutionStone(19, "Dawn Stone", "Evolution Stone", "A stone that sparkles like the morning sky.", "Evolves certain Pokémon.", "P3,000", "P1,500"));
        itemList.add(new EvolutionStone(20, "Ice Stone", "Evolution Stone", "A stone that is cold to the touch.", "Evolves certain Pokémon.", "P3,000", "P1,500"));
    }

    public static void main(String[] args) {
        initializeControllers();
        loadDefaultData();
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
