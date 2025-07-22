package controller;

import java.util.ArrayList;
import model.*;
import util.*;

public class PokemonController {

    private final ArrayList<Pokemon> pokedex;

    public PokemonController(ArrayList<Pokemon> pokedex) {
        this.pokedex = pokedex;
    }

    /**
     * Pokemon Management submenu
     */
    public void pokemonManagement() {
        boolean running = true;
        while (running) {
            MenuArtUtils.pokemonArt();
            MenuArtUtils.printPokemonMenu();
            int choice = InputUtils.getIntInput("Enter your choice: ");
            switch (choice) {
                case 1 ->
                    addPokemon();
                case 2 ->
                    viewAllPokemon();
                case 3 ->
                    viewPokemonDetails();
                case 4 ->
                    searchPokemon();
                case 0 ->
                    running = false;
                default ->
                    System.out.println("\u001B[31mInvalid choice. Please try again.\u001B[0m");
            }
        }
    }

    /**
     * Adds a new Pokémon to the Pokédex after validation.
     */
    private void addPokemon() {
        System.out.println("\n-- Add New Pokémon --");
        int pokedexNumber = InputUtils.getIntInput("Pokédex Number: ");
        if (isPokedexNumberExists(pokedexNumber)) {
            System.out.println("\u001B[31mError: Pokédex Number already exists.\u001B[0m");
            return; // Exit if number already exists
        }
        String name = InputUtils.getStringInput("Name: ");
        if (isPokemonNameExists(name)) {
            System.out.println("\u001B[31mError: Pokémon Name already exists.\u001B[0m");
            return; // Exit if name already exists
        }

        String type1 = InputUtils.getValidTypeInput("Type 1: ", false);
        String type2 = InputUtils.getValidTypeInput("Type 2 (press Enter if none): ", true);

        Integer evolvesFrom = InputUtils.getOptionalIntInput("Evolves From (Pokédex Number, press Enter if none): ");
        Integer evolvesTo = InputUtils.getOptionalIntInput("Evolves To (Pokédex Number, press Enter if none): ");
        Integer evolutionLevel = InputUtils.getOptionalIntInput("Evolution Level (press Enter if none): ");
        int hp = InputUtils.getIntInput("Base HP: ");
        int attack = InputUtils.getIntInput("Base Attack: ");
        int defense = InputUtils.getIntInput("Base Defense: ");
        int speed = InputUtils.getIntInput("Base Speed: ");

        Pokemon p = new Pokemon(pokedexNumber, name, type1, type2, 1, evolvesFrom, evolvesTo, evolutionLevel, hp, attack, defense, speed);
        pokedex.add(p);
        System.out.println("\n\u001B[32mPokémon added successfully!\u001B[0m");
    }

    /**
     * Displays all Pokémon in the Pokédex.
     */
    private void viewAllPokemon() {
        MenuArtUtils.allPokemon();
        if (pokedex.isEmpty()) {
            System.out.println("\u001B[31mNo Pokémon in the database.\u001B[0m");
            return; // Exit if no Pokémon are available
        }
        Pokemon.displayPokemonHeader();
        for (Pokemon p : pokedex) { // Display each Pokémon in the Pokédex
            p.displayPokemon();
        }
        System.out.println();
    }

    /**
     * Searches Pokémon by name, type, or other attributes.
     */
    private void searchPokemon() {
        System.out.println("\n-- Search Pokémon --");
        String keyword = InputUtils.getStringInput("Enter keyword (name/type/attribute): ").toLowerCase();
        boolean found = false;
        boolean headerDisplayed = false;
        for (Pokemon p : pokedex) {
            if (p.getPokemonName().toLowerCase().contains(keyword)
                    || p.getPokemonType1().toLowerCase().contains(keyword)
                    || (p.getPokemonType2() != null && p.getPokemonType2().toLowerCase().contains(keyword))
                    || String.valueOf(p.getPokedexNumber()).equals(keyword)) {
                if (!headerDisplayed) {
                    Pokemon.displayPokemonHeader();
                    headerDisplayed = true;
                }
                p.displayPokemon();
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
    private void viewPokemonDetails() {
        System.out.println("\n-- View Pokémon Details --");
        if (pokedex.isEmpty()) {
            System.out.println("\u001B[31mNo Pokémon in the database.\u001B[0m");
            return;
        }

        // Show available Pokémon
        System.out.println("Available Pokémon:");
        for (int i = 0; i < pokedex.size(); i++) {
            Pokemon p = pokedex.get(i);
            // Zero-based index
            System.out.println((i + 1) + ". " + p.getPokemonName() + " (#" + p.getPokedexNumber() + ")");
        }

        int choice = InputUtils.getIntInput("Select Pokémon (enter number): ") - 1;
        if (choice < 0 || choice >= pokedex.size()) {
            System.out.println("\u001B[31mInvalid selection.\u001B[0m");
            return;
        }

        Pokemon selectedPokemon = pokedex.get(choice);
        System.out.println();
        Pokemon.displayPokemonHeader();
        selectedPokemon.displayPokemon();

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
     * Checks if a Pokédex number already exists. (Requirement #1)
     */
    private boolean isPokedexNumberExists(int number) {
        for (Pokemon p : pokedex) {
            if (p.getPokedexNumber() == number) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a Pokémon name already exists in the Pokédex. (Requirement #2)
     */
    private boolean isPokemonNameExists(String name) {
        for (Pokemon p : pokedex) {
            if (p.getPokemonName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
