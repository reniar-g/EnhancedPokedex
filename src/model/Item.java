package model;

public class Item {

    private final int itemId;
    private final String itemName;
    private final String itemCategory;
    private final String itemDescription;
    private final String itemEffect;
    private final String itemPrice;
    private final String itemSellPrice;

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

    public String getItemInfo() {
        return String.format("%s - %s - %s", itemName, itemCategory, itemEffect);
    }

    public String getPriceInfo() {
        return String.format("Buy: %s | Sell: %s", itemPrice, itemSellPrice);
    }
}
