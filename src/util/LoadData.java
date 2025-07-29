package util;

import java.util.ArrayList;
import model.*;

public class LoadData {

    // Adds first 55 pokemon from the official pokemon franchise
    public static ArrayList<Pokemon> loadPokemons() {
        ArrayList<Pokemon> pokemons = new ArrayList<>();

        pokemons.add(new Pokemon(1, "Bulbasaur", "Grass", "Poison", 5, null, 2, 16, 45, 49, 49, 45));
        pokemons.add(new Pokemon(2, "Ivysaur", "Grass", "Poison", 5, 1, 3, 32, 60, 62, 63, 60));
        pokemons.add(new Pokemon(3, "Venusaur", "Grass", "Poison", 5, 2, null, null, 80, 82, 83, 80));
        pokemons.add(new Pokemon(4, "Charmander", "Fire", "", 5, null, 5, 16, 39, 52, 43, 65));
        pokemons.add(new Pokemon(5, "Charmeleon", "Fire", "", 5, 4, 6, 36, 58, 64, 58, 80));
        pokemons.add(new Pokemon(6, "Charizard", "Fire", "Flying", 5, 5, null, null, 78, 84, 78, 100));
        pokemons.add(new Pokemon(7, "Squirtle", "Water", "", 5, null, 8, 16, 44, 48, 65, 43));
        pokemons.add(new Pokemon(8, "Wartortle", "Water", "", 5, 7, 9, 36, 59, 63, 80, 58));
        pokemons.add(new Pokemon(9, "Blastoise", "Water", "", 5, 8, null, null, 79, 83, 100, 78));
        pokemons.add(new Pokemon(10, "Caterpie", "Bug", "", 5, null, 11, 7, 45, 30, 35, 45));
        pokemons.add(new Pokemon(11, "Metapod", "Bug", "", 5, 10, 12, 10, 50, 20, 25, 30));
        pokemons.add(new Pokemon(12, "Butterfree", "Bug", "Flying", 5, 11, null, null, 60, 45, 50, 70));
        pokemons.add(new Pokemon(13, "Weedle", "Bug", "Poison", 5, null, 14, 7, 40, 35, 30, 50));
        pokemons.add(new Pokemon(14, "Kakuna", "Bug", "Poison", 5, 13, 15, 10, 45, 25, 25, 35));
        pokemons.add(new Pokemon(15, "Beedrill", "Bug", "Poison", 5, 14, null, null, 65, 80, 40, 75));
        pokemons.add(new Pokemon(16, "Pidgey", "Normal", "Flying", 5, null, 17, 18, 40, 45, 40, 56));
        pokemons.add(new Pokemon(17, "Pidgeotto", "Normal", "Flying", 5, 16, 18, 36, 63, 60, 55, 71));
        pokemons.add(new Pokemon(18, "Pidgeot", "Normal", "Flying", 5, 17, null, null, 83, 80, 75, 91));
        pokemons.add(new Pokemon(19, "Rattata", "Normal", "", 5, null, 20, 20, 30, 56, 35, 72));
        pokemons.add(new Pokemon(20, "Raticate", "Normal", "", 5, 19, null, null, 65, 81, 60, 97));
        pokemons.add(new Pokemon(21, "Spearow", "Normal", "Flying", 5, null, 22, 20, 40, 60, 30, 70));
        pokemons.add(new Pokemon(22, "Fearow", "Normal", "Flying", 5, 21, null, null, 65, 90, 65, 100));
        pokemons.add(new Pokemon(23, "Ekans", "Poison", "", 5, null, 24, 22, 35, 60, 44, 55));
        pokemons.add(new Pokemon(24, "Arbok", "Poison", "", 5, 23, null, null, 60, 85, 69, 80));
        pokemons.add(new Pokemon(25, "Pikachu", "Electric", "", 5, null, 26, 22, 35, 55, 40, 90));
        pokemons.add(new Pokemon(26, "Raichu", "Electric", "", 5, 25, null, null, 60, 90, 55, 110));
        pokemons.add(new Pokemon(27, "Sandshrew", "Ground", "", 5, null, 28, 22, 50, 75, 85, 40));
        pokemons.add(new Pokemon(28, "Sandslash", "Ground", "", 5, 27, null, null, 75, 100, 110, 65));
        pokemons.add(new Pokemon(29, "Nidoran ♀", "Poison", "", 5, null, 30, 16, 55, 47, 52, 41));
        pokemons.add(new Pokemon(30, "Nidorina", "Poison", "", 5, 29, 31, 32, 70, 62, 67, 56));
        pokemons.add(new Pokemon(31, "Nidoqueen", "Poison", "Ground", 5, 30, null, null, 90, 82, 87, 76));
        pokemons.add(new Pokemon(32, "Nidoran ♂", "Poison", "", 5, null, 33, 16, 46, 57, 40, 50));
        pokemons.add(new Pokemon(33, "Nidorino", "Poison", "", 5, 32, 34, 36, 61, 72, 57, 65));
        pokemons.add(new Pokemon(34, "Nidoking", "Poison", "Ground", 5, 33, null, null, 81, 102, 77, 85));
        pokemons.add(new Pokemon(35, "Clefairy", "Fairy", "", 5, null, 36, 35, 70, 45, 48, 35));
        pokemons.add(new Pokemon(36, "Clefable", "Fairy", "", 5, 35, null, null, 95, 70, 73, 60));
        pokemons.add(new Pokemon(37, "Vulpix", "Fire", "", 5, null, 38, 29, 38, 41, 40, 65));
        pokemons.add(new Pokemon(38, "Ninetales", "Fire", "", 5, 37, null, null, 73, 76, 75, 100));
        pokemons.add(new Pokemon(39, "Jigglypuff", "Normal", "Fairy", 5, null, 40, 20, 115, 45, 20, 25));
        pokemons.add(new Pokemon(40, "Wigglytuff", "Normal", "Fairy", 5, 39, null, null, 140, 70, 45, 45));
        pokemons.add(new Pokemon(41, "Zubat", "Poison", "Flying", 5, null, 42, 22, 40, 45, 35, 55));
        pokemons.add(new Pokemon(42, "Golbat", "Poison", "Flying", 5, 41, null, null, 75, 80, 70, 90));
        pokemons.add(new Pokemon(43, "Oddish", "Grass", "Poison", 5, null, 44, 21, 45, 50, 55, 30));
        pokemons.add(new Pokemon(44, "Gloom", "Grass", "Poison", 5, 43, 45, 32, 60, 65, 70, 40));
        pokemons.add(new Pokemon(45, "Vileplume", "Grass", "Poison", 5, 44, null, null, 75, 80, 85, 50));
        pokemons.add(new Pokemon(46, "Paras", "Bug", "Grass", 5, null, 47, 24, 35, 70, 55, 25));
        pokemons.add(new Pokemon(47, "Parasect", "Bug", "Grass", 5, 46, null, null, 60, 95, 80, 30));
        pokemons.add(new Pokemon(48, "Venonat", "Bug", "Poison", 5, null, 49, 31, 60, 55, 50, 45));
        pokemons.add(new Pokemon(49, "Venomoth", "Bug", "Poison", 5, 48, null, null, 70, 65, 60, 90));
        pokemons.add(new Pokemon(50, "Diglett", "Ground", "", 5, null, 51, 26, 10, 55, 25, 95));
        pokemons.add(new Pokemon(51, "Dugtrio", "Ground", "", 5, 50, null, null, 35, 80, 50, 120));
        pokemons.add(new Pokemon(52, "Meowth", "Normal", "", 5, null, 53, 28, 40, 45, 35, 90));
        pokemons.add(new Pokemon(53, "Persian", "Normal", "", 5, 52, null, null, 65, 70, 60, 115));
        pokemons.add(new Pokemon(54, "Psyduck", "Water", "", 5, null, 55, 33, 50, 52, 48, 55));
        pokemons.add(new Pokemon(55, "Golduck", "Water", "", 5, 54, null, null, 80, 82, 78, 85));

        return pokemons;
    }

    // Add basic moves
    public static ArrayList<Move> loadMoves() {
        ArrayList<Move> moves = new ArrayList<>();

        moves.add(new Move("Tackle", "A physical attack in which the user charges and slams into the target with its whole body.", "TM", "Normal", ""));
        moves.add(new Move("Defend", "The user hardens its body's surface like iron and sharply raising its Defense stat.", "TM", "Normal", ""));
        moves.add(new Move("Cut", "Cuts down small trees.", "HM", "Normal", ""));
        moves.add(new Move("Fly", "Flies to a previously visited town.", "HM", "Flying", ""));
        moves.add(new Move("Surf", "Rides waves across water.", "HM", "Water", ""));
        moves.add(new Move("Strength", "Moves heavy boulders.", "HM", "Normal", ""));
        moves.add(new Move("Flash", "Lights up dark caves.", "HM", "Electric", ""));
        moves.add(new Move("Thunderbolt", "A strong electric attack. May paralyze the target.", "TM", "Electric", ""));
        moves.add(new Move("Ice Beam", "A beam of ice is shot at the foe. May freeze the target.", "TM", "Ice", ""));
        moves.add(new Move("Flamethrower", "A powerful fire attack. May burn the target.", "TM", "Fire", ""));
        moves.add(new Move("Psychic", "A strong psychic attack.", "TM", "Psychic", ""));
        moves.add(new Move("Earthquake", "A ground-shaking attack.", "TM", "Ground", ""));
        moves.add(new Move("Hyper Beam", "A very powerful attack.", "TM", "Normal", ""));
        moves.add(new Move("Rock Slide", "Large rocks are thrown at the foe. May cause flinching.", "TM", "Rock", ""));
        moves.add(new Move("Quick Attack", "An extremely fast attack that always strikes first.", "TM", "Normal", ""));
        moves.add(new Move("Shadow Ball", "Hurls a shadowy blob at the foe. May lower the target's Special Defense.", "TM", "Ghost", ""));
        moves.add(new Move("Toxic", "Badly poisons the foe.", "TM", "Poison", ""));

        return moves;
    }

    // Adds the default items from the specs
    public static ArrayList<Item> loadItems() {
        ArrayList<Item> items = new ArrayList<>();

        // Vitamins
        items.add(new Vitamin(1, "HP Up", "Vitamin",
                "A nutritious drink for Pokémon.",
                "+10 HP EVs", "P10000", "P5000"));

        items.add(new Vitamin(2, "Protein", "Vitamin",
                "A nutritious drink for Pokémon.",
                "+10 Attack EVs", "P10000", "P5000"));

        items.add(new Vitamin(3, "Iron", "Vitamin",
                "A nutritious drink for Pokémon.",
                "+10 Defense EVs", "P10000", "P5000"));

        items.add(new Vitamin(4, "Carbos", "Vitamin",
                "A nutritious drink for Pokémon.",
                "+10 Speed EVs", "P10000", "P5000"));

        items.add(new Vitamin(5, "Zinc", "Vitamin",
                "A nutritious drink for Pokémon.",
                "+10 Special Defense EVs", "P10000", "P5000"));

        // Feathers
        items.add(new Feather(6, "Health Feather", "Feather",
                "A feather that slightly increases HP.",
                "+1 HP EV", "P300", "P150"));

        items.add(new Feather(7, "Muscle Feather", "Feather",
                "A feather that slightly increases Attack.",
                "+1 Attack EV", "P300", "P150"));

        items.add(new Feather(8, "Resist Feather", "Feather",
                "A feather that slightly increases Defense.",
                "+1 Defense EV", "P300", "P150"));

        items.add(new Feather(9, "Swift Feather", "Feather",
                "A feather that slightly increases Speed.",
                "+1 Speed EV", "P300", "P150"));

        // Levelling Item
        items.add(new Levelling(10, "Rare Candy", "Leveling Item",
                "A candy that is packed with energy.",
                "Increases level by 1", "P4800", "P2400"));

        // Evolution Stones
        items.add(new EvolutionStone(11, "Fire Stone", "Evolution Stone",
                "A stone that radiates heat.",
                "Evolves certain Pokémon.", "P3000", "P1500"));

        items.add(new EvolutionStone(12, "Water Stone", "Evolution Stone",
                "A stone with a blue and watery appearance.",
                "Evolves certain Pokémon.", "P3000", "P1500"));

        items.add(new EvolutionStone(13, "Thunder Stone", "Evolution Stone",
                "A stone that sparkles with electricity.",
                "Evolves certain Pokémon.", "P3000", "P1500"));

        items.add(new EvolutionStone(14, "Leaf Stone", "Evolution Stone",
                "A stone with a leaf pattern.",
                "Evolves certain Pokémon.", "P3000", "P1500"));

        items.add(new EvolutionStone(15, "Moon Stone", "Evolution Stone",
                "A stone that glows faintly in the moonlight.",
                "Evolves certain Pokémon.", "P3000", "P1500"));

        items.add(new EvolutionStone(16, "Sun Stone", "Evolution Stone",
                "A stone that glows like the sun.",
                "Evolves certain Pokémon.", "P3000", "P1500"));

        items.add(new EvolutionStone(17, "Shiny Stone", "Evolution Stone",
                "A stone that sparkles brightly.",
                "Evolves certain Pokémon.", "P3000", "P1500"));

        items.add(new EvolutionStone(18, "Dusk Stone", "Evolution Stone",
                "A dark and ominous stone.",
                "Evolves certain Pokémon.", "P3000", "P1500"));

        items.add(new EvolutionStone(19, "Dawn Stone", "Evolution Stone",
                "A stone that sparkles like the morning sky.",
                "Evolves certain Pokémon.", "P3000", "P1500"));

        items.add(new EvolutionStone(20, "Ice Stone", "Evolution Stone",
                "A stone that is cold to the touch.",
                "Evolves certain Pokémon.", "P3000", "P1500"));

        return items;
    }

    // Add default trainers
    public static ArrayList<Trainer> loadTrainers() {
        ArrayList<Trainer> trainers = new ArrayList<>();
        ArrayList<Pokemon> allPokemon = loadPokemons();
        ArrayList<Item> allItems = loadItems();

        Trainer ash = new Trainer(1, "Ash Ketchum", "22/05/1987", "M", "Pallet Town",
                "The Show's Protagonist");

        Trainer rainer = new Trainer(2, "Rainer Gonzaga", "23/12/2005", "M", "Quezon City",
                "The Creator of Enhanced Pokedex");

        Trainer joshua = new Trainer(3, "Joshua Del Mundo", "01/01/2001", "M", "Manila City",
                "The Co-Creator of Enhanced Pokedex");

        Trainer misty = new Trainer(4, "Misty Mizuyawa", "01/04/1985", "F", "Cerulean City",
                "Water-type Pokémon Trainer");

        Trainer brock = new Trainer(5, "Brock Harrison", "14/06/1983", "M", "Pewter City",
                "Rock-type Pokémon Trainer");

        // Default pokemons and items of trainer 1
        ash.addPokemonToLineup(allPokemon.get(0));  // Bulbasaur
        ash.addPokemonToLineup(allPokemon.get(5));  // Charizard
        ash.addPokemonToLineup(allPokemon.get(6));  // Squirtle
        ash.addPokemonToLineup(allPokemon.get(24)); // Pikachu
        ash.addPokemonToLineup(allPokemon.get(36)); // Vulpix

        // Add Ash's 5 storage Pokemon
        ash.getPokemonStorage().add(allPokemon.get(15)); // Pidgey
        ash.getPokemonStorage().add(allPokemon.get(18)); // Rattata
        ash.getPokemonStorage().add(allPokemon.get(38)); // Jigglypuff
        ash.getPokemonStorage().add(allPokemon.get(51)); // Meowth
        ash.getPokemonStorage().add(allPokemon.get(52)); // Persian

        // Add Ash's default items (3 of each)
        for (int i = 0; i < 3; i++) {
            // 3 Vitamins
            ash.getInventory().add(allItems.get(0));  // HP Up
            ash.getInventory().add(allItems.get(1));  // Protein
            ash.getInventory().add(allItems.get(2));  // Iron

            // 3 Feathers
            ash.getInventory().add(allItems.get(5));  // Health Feather
            ash.getInventory().add(allItems.get(6));  // Muscle Feather
            ash.getInventory().add(allItems.get(7));  // Resist Feather

            // 3 Evolution Stones
            ash.getInventory().add(allItems.get(11)); // Water Stone
            ash.getInventory().add(allItems.get(13)); // Leaf Stone
            ash.getInventory().add(allItems.get(15)); // Moon Stone
        }
        trainers.add(ash);

        // trainer 2 default pokemons and items


        rainer.addPokemonToLineup(allPokemon.get(0));  // Bulbasaur
        rainer.addPokemonToLineup(allPokemon.get(24)); // Pikachu
        rainer.getPokemonStorage().add(allPokemon.get(15)); // Pidgey

        for (int i = 0; i < 3; i++) {
            rainer.getInventory().add(allItems.get(0));  // HP Up
            rainer.getInventory().add(allItems.get(5));  // Health Feather
        }
        trainers.add(rainer);

        // trainer 3 default pokemons and items
        joshua.addPokemonToLineup(allPokemon.get(5));  // Charizard
        joshua.getPokemonStorage().add(allPokemon.get(18)); // Rattata
        joshua.getPokemonStorage().add(allPokemon.get(38)); // Jigglypuff

        for (int i = 0; i < 3; i++) {
            joshua.getInventory().add(allItems.get(1));  // Protein
            joshua.getInventory().add(allItems.get(6));  // Muscle Feather
        }
        trainers.add(joshua);

        // trainer 4 default pokemons and items
        misty.addPokemonToLineup(allPokemon.get(6));  // Squirtle
        misty.addPokemonToLineup(allPokemon.get(36)); // Vulpix
        misty.getPokemonStorage().add(allPokemon.get(51)); // Meowth

        for (int i = 0; i < 3; i++) {
            misty.getInventory().add(allItems.get(2));  // Iron
            misty.getInventory().add(allItems.get(7));  // Resist Feather
        }
        trainers.add(misty);

        // trainer 5 default pokemons and items
        brock.addPokemonToLineup(allPokemon.get(5));  // Charizard
        brock.getPokemonStorage().add(allPokemon.get(52)); // Persian

        for (int i = 0; i < 3; i++) {
            brock.getInventory().add(allItems.get(11));  // Water Stone
            brock.getInventory().add(allItems.get(13));  // Leaf Stone
        }
        trainers.add(brock);

        return trainers;
    }
}
