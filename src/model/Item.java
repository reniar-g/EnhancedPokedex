package model;

public class Item {

    private int itemId;
    private String itemName;
    private String itemCategory;
    private String itemDescription;
    private String itemEffect;
    private String itemPrice;
    private String itemSellPrice;

    // Update constructor
    public Item(int itemId, String itemName, String itemCategory, String itemDescription,
            String itemEffect, String itemPrice, String itemSellPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemDescription = itemDescription;
        this.itemEffect = itemEffect;
        this.itemPrice = itemPrice;
        this.itemSellPrice = itemSellPrice;
    }

    // Getters for Item attributes
    public String getItemName() {
        return itemName;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public String getItemEffect() {
        return itemEffect;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemSellPrice() {
        return itemSellPrice;
    }

    /**
     * Displays the header for Item information table
     */
    public static void displayItemHeader() {
        System.out.printf("%-6s %-16s %-17s %-45s %-26s %-15s %-15s%n",
                "ID", "Item Name", "Category", "Description", "Effect", "Buy Price", "Sell Price");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Displays item details in a formatted table style using printf
     */
    public void displayItem() {
        System.out.printf("%-6d %-16s %-17s %-45s %-26s %-15s %-15s%n",
                itemId,
                itemName,
                itemCategory,
                itemDescription,
                itemEffect,
                itemPrice,
                itemSellPrice);
    }
}
