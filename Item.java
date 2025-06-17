public class Item {

    private String itemName;
    private String itemCategory;
    private String itemDescription;
    private String itemEffect;
    private int buyingPrice;
    private int sellingPrice;

    public Item(String name, String category, String description,
            String effect, int buyingPrice, int sellingPrice) {
        this.itemName = name;
        this.itemCategory = category;
        this.itemDescription = description;
        this.itemEffect = effect;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemCategory() { return itemCategory; }

    public String getItemEffect() { return itemEffect; }

    public String displayItem() {
        return itemName + " (" + itemCategory + ") - " + itemDescription
                + " | Effect: " + itemEffect + " | Buy: ₱" + buyingPrice + ", Sell: ₱" + sellingPrice;
    }
}
