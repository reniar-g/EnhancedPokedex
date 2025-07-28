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
     * Gets a formatted string representation of the item for display
     */
    public String getItemInfo() {
        return String.format("%s - %s - %s", itemName, itemCategory, itemEffect);
    }

    /**
     * Gets formatted price string
     */
    public String getPriceInfo() {
        return String.format("Buy: %s | Sell: %s", itemPrice, itemSellPrice);
    }
}
