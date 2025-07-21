package controller;

import java.util.ArrayList;
import model.*;
import util.*;

public class ItemController {

    private final ArrayList<Item> itemList;

    public ItemController(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    /**
     * Item Management submenu
     */
    public void itemManagement() {
        boolean running = true;
        while (running) {
            MenuArtUtils.itemsArt();
            MenuArtUtils.printItemMenu();
            int choice = InputUtils.getIntInput("Enter your choice: ");
            switch (choice) {
                case 1 ->
                    viewAllItems();
                case 2 ->
                    searchItems();
                case 0 ->
                    running = false;
                default ->
                    System.out.println("\u001B[31mInvalid choice. Please try again.\u001B[0m");
            }
        }
    }

    /**
     * Loads the official item list into the itemList.
     */
    public void loadDefaultItems() {
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
    private void viewAllItems() {
        MenuArtUtils.allItems();
        if (itemList.isEmpty()) {
            System.out.println("\u001B[31mNo items in the database.\u001B[0m");
            return;
        }
        Item.displayItemHeader();
        for (Item item : itemList) {
            item.displayItem();
        }
        System.out.println();
    }

    /**
     * Searches items by keyword.
     */
    private void searchItems() {
        System.out.println("\n-- Search Items --");
        String keyword = InputUtils.getStringInput("Enter keyword (name/category/effect): ").toLowerCase();
        boolean found = false;
        boolean headerDisplayed = false;
        for (Item i : itemList) {
            if (i.getItemName().toLowerCase().contains(keyword)
                    || i.getItemCategory().toLowerCase().contains(keyword)
                    || i.getItemEffect().toLowerCase().contains(keyword)) {
                if (!headerDisplayed) {
                    Item.displayItemHeader();
                    headerDisplayed = true;
                }
                i.displayItem();
                found = true;
            }
        }
        if (!found) {
            System.out.println("\u001B[31mNo items found matching the search criteria.\u001B[0m");
        }
    }

}
