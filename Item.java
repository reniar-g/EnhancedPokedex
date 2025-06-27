// Item class representing an item in the game

public class Item {

    private String itemName;
    private String itemCategory;
    private String itemDescription;
    private String itemEffect;
    private String itemPrice;
    private String itemSellPrice;

    // Constructor for Item class
    public Item(String itemName, String itemCategory, String itemDescription,
            String itemEffect, String itemPrice, String itemSellPrice) {
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
        System.out.printf("%-16s %-17s %-45s %-26s %-15s %-15s%n",
                "Item Name", "Category", "Description", "Effect", "Buy Price", "Sell Price");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Displays item details in a formatted table style using printf
     */
    public void displayItem() {
        System.out.printf("%-16s %-17s %-45s %-26s %-15s %-15s%n",
                itemName,
                itemCategory,
                itemDescription,
                itemEffect,
                itemPrice,
                itemSellPrice);
    }
}
