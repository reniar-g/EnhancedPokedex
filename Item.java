
public class Item {

    private String name;
    private String category;
    private String description;
    private String effect;
    private int buyingPrice;
    private int sellingPrice;

    public Item(String name, String category, String description,
            String effect, int buyingPrice, int sellingPrice) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.effect = effect;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
    }

    public String getName() {
        return name;
    }

    public String displayItem() {
        return name + " (" + category + ") - " + description
                + " | Effect: " + effect + " | Buy: ₱" + buyingPrice + ", Sell: ₱" + sellingPrice;
    }
}
