
public class Item {

    private String itemName;
    private String itemCategory;
    private String itemDescription;
    private String itemEffect;
    private String itemPrice;
    private String itemSellPrice;

    public Item(String itemName, String itemCategory, String itemDescription, String itemEffect, String itemPrice, String itemSellPrice) {
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

    public String displayItem() {
        return itemName + " (" + itemCategory + ") - " + itemDescription
                + " | Effect: " + itemEffect + " | Buy: " + itemPrice + ", Sell: " + itemSellPrice;
    }
}
