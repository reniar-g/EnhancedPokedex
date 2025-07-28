package controller;

import java.util.ArrayList;
import model.*;

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
     * Searches items by keyword and returns matching items
     *
     * @param keyword The search term to look for in name/category/effect
     * @return List of matching items
     */
    public ArrayList<Item> searchItems(String keyword) {
        ArrayList<Item> results = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            return results;
        }

        keyword = keyword.toLowerCase();
        for (Item item : itemList) {
            if (item.getItemName().toLowerCase().contains(keyword)
                    || item.getItemCategory().toLowerCase().contains(keyword)
                    || item.getItemEffect().toLowerCase().contains(keyword)) {
                results.add(item);
            }
        }
        return results;
    }

    /**
     * Gets an item by its ID
     *
     * @param id The item ID to look for
     * @return The item if found, null otherwise
     */
    public Item getItemById(int id) {
        for (Item item : itemList) {
            if (item.getItemId() == id) {
                return item;
            }
        }
        return null;
    }
}
