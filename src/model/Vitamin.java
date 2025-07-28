package model;

public class Vitamin extends Item
{
    public Vitamin(int itemId, String itemName, String itemCategory, String itemDescription,
    String itemEffect, String itemPrice, String itemSellPrice)
    {
        super(itemId, itemName, itemCategory, itemDescription, itemEffect, itemPrice, itemSellPrice);
    }

    public void useHPUp(Pokemon p)
    {
        p.addHealth(10);
        System.out.println(p.getPokemonName() + " uses " + getItemName() + "!");
    }

    public void useProtein(Pokemon p)
    {
        p.addAttack(10);
        System.out.println(p.getPokemonName() + " uses " + getItemName() + "!");
    }

    public void useIron(Pokemon p)
    {
        p.addDefense(10);
        System.out.println(p.getPokemonName() + " uses " + getItemName() + "!");
    }

    public void useCarbos(Pokemon p)
    {
        p.addSpeed(10);
        System.out.println(p.getPokemonName() + " uses " + getItemName() + "!");
    }

    public void useZinc(Pokemon p)
    {
        p.addDefense(10);
        System.out.println(p.getPokemonName() + " uses " + getItemName() + "!");
    }
}
