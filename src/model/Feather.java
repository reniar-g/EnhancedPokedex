package model;

public class Feather extends Item
{
    public Feather(int itemId, String itemName, String itemCategory, String itemDescription,
                   String itemEffect, String itemPrice, String itemSellPrice)
    {
        super(itemId, itemName, itemCategory, itemDescription, itemEffect, itemPrice, itemSellPrice);
    }

    public void useHealthFeather(Pokemon p)
    {
        p.addHealth(1);
        System.out.println(p.getPokemonName() + " uses " + getItemName() + "!");
    }

    public void useMuscleFeather(Pokemon p)
    {
        p.addAttack(1);
        System.out.println(p.getPokemonName() + " uses " + getItemName() + "!");
    }

    public void useResistFeather(Pokemon p)
    {
        p.addDefense(1);
        System.out.println(p.getPokemonName() + " uses " + getItemName() + "!");
    }

    public void useSwiftFeather(Pokemon p)
    {
        p.addSpeed(1);
        System.out.println(p.getPokemonName() + " uses " + getItemName() + "!");
    }
}
