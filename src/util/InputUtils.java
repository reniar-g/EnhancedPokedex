package util;

import java.io.*;
import java.util.ArrayList;
import model.*;

public class InputUtils {

    /**
     * Loads Pokemon data from a CSV file
     *
     * @param csvPath Path to the CSV file
     * @return List of Pokemon objects, empty list if loading fails
     */
    public static ArrayList<Pokemon> loadPokemonsFromCSV(String csvPath) {
        ArrayList<Pokemon> pokemons = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) {
                    first = false;
                    continue;
                }
                String[] f = line.split(",");
                int id = Integer.parseInt(f[0]);
                String name = f[1];
                String type1 = f[2].isEmpty() ? null : f[2];
                String type2 = f[3].isEmpty() ? null : f[3];
                int gen = Integer.parseInt(f[4]);
                Integer pre = f[5].isEmpty() ? null : Integer.valueOf(f[5]);
                Integer next = f[6].isEmpty() ? null : Integer.valueOf(f[6]);
                Integer evoLvl = f[7].isEmpty() ? null : Integer.valueOf(f[7]);
                int hp = Integer.parseInt(f[8]);
                int atk = Integer.parseInt(f[9]);
                int def = Integer.parseInt(f[10]);
                int spd = Integer.parseInt(f[11]);
                pokemons.add(new Pokemon(id, name, type1, type2, gen, pre, next, evoLvl, hp, atk, def, spd));
            }
        } catch (IOException e) {
            // Return empty list on error, caller can check if list is empty
            return new ArrayList<>();
        }
        return pokemons;
    }

    /**
     * Loads Item data from a CSV file
     *
     * @param csvPath Path to the CSV file
     * @return List of Item objects, empty list if loading fails
     */
    public static ArrayList<Item> loadItemsFromCSV(String csvPath) {
        ArrayList<Item> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) {
                    first = false;
                    continue;
                }
                String[] f = line.split(",");
                int id = Integer.parseInt(f[0]);
                String name = f[1];
                String type = f[2];
                String desc = f[3];
                String effect = f[4];
                String buy = f[5];
                String sell = f[6];
                // Simple type-based instantiation
                if (type.equalsIgnoreCase("Vitamin")) {
                    items.add(new Vitamin(id, name, type, desc, effect, buy, sell));
                } else if (type.equalsIgnoreCase("Feather")) {
                    items.add(new Feather(id, name, type, desc, effect, buy, sell));
                } else if (type.equalsIgnoreCase("Leveling Item")) {
                    items.add(new Levelling(id, name, type, desc, effect, buy, sell));
                } else if (type.contains("Stone")) {
                    items.add(new EvolutionStone(id, name, type, desc, effect, buy, sell));
                } else {
                    items.add(new Item(id, name, type, desc, effect, buy, sell));
                }
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }
        return items;
    }

    /**
     * Loads Move data from a CSV file
     *
     * @param csvPath Path to the CSV file
     * @return List of Move objects, empty list if loading fails
     */
    public static ArrayList<Move> loadMovesFromCSV(String csvPath) {
        ArrayList<Move> moves = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) {
                    first = false;
                    continue;
                }
                String[] f = line.split(",");
                String name = f[0];
                String desc = f[1];
                String type = f[2];
                String category = f[3];
                String extra = f.length > 4 ? f[4] : null;
                moves.add(new Move(name, desc, type, category, extra));
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }
        return moves;
    }

    /**
     * Loads Trainer data from a CSV file
     *
     * @param csvPath Path to the CSV file
     * @return List of Trainer objects, empty list if loading fails
     */
    public static ArrayList<Trainer> loadTrainersFromCSV(String csvPath) {
        ArrayList<Trainer> trainers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) {
                    first = false;
                    continue;
                }
                String[] f = line.split(",");
                int id = Integer.parseInt(f[0]);
                String name = f[1];
                String birth = f[2];
                String gender = f[3];
                String loc = f[4];
                String desc = f[5];
                trainers.add(new Trainer(id, name, birth, gender, loc, desc));
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }
        return trainers;
    }

    // Valid Pokémon types
    private static final String[] VALID_TYPES = {
        "Normal", "Fire", "Water", "Grass", "Electric", "Psychic", "Ice", "Dragon",
        "Dark", "Fairy", "Fighting", "Flying", "Poison", "Ground", "Rock", "Bug", "Ghost", "Steel"};

    /**
     * Checks if a given type is valid
     */
    public static boolean isValidType(String type) {
        if (type == null || type.trim().isEmpty()) {
            return false;
        }
        String trimmedType = type.trim();
        for (String validType : VALID_TYPES) {
            if (validType.equalsIgnoreCase(trimmedType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Capitalizes the first letter of a string. This utility method is still
     * useful for GUI display formatting.
     */
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
