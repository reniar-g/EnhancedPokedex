package util;

import java.util.Scanner;

public class InputUtils {

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
