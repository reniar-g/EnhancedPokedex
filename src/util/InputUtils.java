package util;

import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import model.*;

public class InputUtils {

    public static ArrayList<Pokemon> loadPokemonsFromCSV(String csvPath) {
        ArrayList<Pokemon> pokemons = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) {
                    first = false;
                    continue;
                } // skip header
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
        } catch (Exception e) {
            System.out.println("Error loading Pokemons from CSV: " + e.getMessage());
        }
        return pokemons;
    }

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
        } catch (Exception e) {
            System.out.println("Error loading Items from CSV: " + e.getMessage());
        }
        return items;
    }

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
        } catch (Exception e) {
            System.out.println("Error loading Moves from CSV: " + e.getMessage());
        }
        return moves;
    }

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
        } catch (Exception e) {
            System.out.println("Error loading Trainers from CSV: " + e.getMessage());
        }
        return trainers;
    }

    private static final Scanner scanner = new Scanner(System.in);

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

    /*
     * Method to get an integer input from the user
     */
    public static int getIntInput(String prompt) {
        int value = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print(prompt);
                value = Integer.parseInt(scanner.nextLine().trim());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        return value;
    }

    /**
     * Gets a non-empty string input from the user.
     */
    public static String getStringInput(String prompt) {
        String input;
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
    public static Integer getOptionalIntInput(String prompt) {
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
    public static String getValidTypeInput(String prompt, boolean optional) {
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
     * Displays all valid Pokémon types
     */
    public static void displayValidTypes() {
        System.out.println("\n        Valid Pokémon Types:");
        System.out.println("Normal, Fire, Water, Grass, Electric,");
        System.out.println("  Psychic, Ice, Dragon, Dark, Fairy,");
        System.out.println("Fighting, Flying, Poison, Ground,");
        System.out.println("      Rock, Bug, Ghost, Steel.");
        System.out.println();
    }

    /**
     * Capitalizes the first letter of a string.
     */
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str; // Return the original string if it's null or empty
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * Closes the Scanner when the application is done with input operations.
     */
    public static void closeScanner() {
        scanner.close();
    }
}
