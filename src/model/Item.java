package model;

// Item class representing an item in the game
public abstract class Item {

    protected String itemName;
    protected String itemCategory;
    protected String itemDescription;
    protected double itemEffect;
    protected double itemPrice;
    protected double itemSellPrice;

    // Constructor for Item class
    public Item(String itemName, String itemCategory, String itemDescription,
            double itemEffect, double itemPrice, double itemSellPrice) {
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

    public double getItemEffect() {
        return itemEffect;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public double getItemSellPrice() {
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

class Vitamin extends Item
{
    Vitamin (String itemName, String itemCategory, String itemDescription,
             double itemEffect, double itemPrice, double itemSellPrice)
    {
        super(itemName, itemCategory, itemDescription, itemEffect, itemPrice, itemSellPrice);
    }

    //USE ITEMS
    public void useHPUp(Pokemon pokemon)
    {
        pokemon.addHP(10);
    }

    public void useProtein(Pokemon pokemon)
    {
        pokemon.addAttack(10);
    }

    public void useIron(Pokemon pokemon)
    {
        pokemon.addDefense(10);
    }

    public void useCarbos(Pokemon pokemon)
    {
        pokemon.addSpeed(10);
    }

    public void useZinc(Pokemon pokemon)
    {
        pokemon.addDefense(10);
    }
}

class Levelling extends Item
{
    Levelling(String itemName, String itemCategory, String itemDescription,
              double itemEffect, double itemPrice, double itemSellPrice)
    {
        super(itemName, itemCategory, itemDescription, itemEffect, itemPrice, itemSellPrice);
    }

    public void useRareCandy(Pokemon pokemon)
    {
        pokemon.baseLevelUp();
    }
}

class Feather extends Item
{
    Feather(String itemName, String itemCategory, String itemDescription,
            double itemEffect, double itemPrice, double itemSellPrice)
    {
        super(itemName, itemCategory, itemDescription, itemEffect, itemPrice, itemSellPrice);
    }

    //USE ITEMS
    public void useHealthFeather(Pokemon pokemon)
    {
        pokemon.addHP(1);
    }

    public void useMuscleFeather(Pokemon pokemon)
    {
        pokemon.addAttack(1);
    }

    public void useResistFeather(Pokemon pokemon)
    {
        pokemon.addDefense(1);
    }

    public void useSwiftFeather(Pokemon pokemon)
    {
        pokemon.addSpeed(1);
    }
}

class Stone extends Item
{
    Stone(String itemName, String itemCategory, String itemDescription,
          double itemEffect, double itemPrice, double itemSellPrice)
    {
        super(itemName, itemCategory, itemDescription, itemEffect, itemPrice, itemSellPrice);
    }
}

