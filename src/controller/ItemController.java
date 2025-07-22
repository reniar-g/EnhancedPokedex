package controller;

import java.util.ArrayList;
import model.*;
import util.*;

public class ItemController {

    private final ArrayList<Item> itemList;

    public ItemController(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    // Getters for item attributes used by TrainerController
    public ArrayList<Item> getItemList() {
        return itemList;
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
     * Displays all items in the database. Public so that it can be accessed by
     * TrainerController for buying/selling items.
     */
    public void viewAllItems() {
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
