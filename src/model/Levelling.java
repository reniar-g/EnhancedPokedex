package model;

import java.util.ArrayList;

public class Levelling extends Item {

    public Levelling(int itemId, String itemName, String itemCategory, String itemDescription,
            String itemEffect, String itemPrice, String itemSellPrice) {
        super(itemId, itemName, itemCategory, itemDescription, itemEffect, itemPrice, itemSellPrice);
    }

    public void useRareCandy(Pokemon p) {
        if (p == null) {
            System.out.println("\u001B[31mError: No Pokémon selected!\u001B[0m");
            return;
        }

        int oldLevel = p.getBaseLevel();
        p.setBaseLevel(oldLevel + 1);

        String originalName = p.getPokemonName();
        Item heldItem = p.getHeldItem();
        ArrayList<Move> moves = new ArrayList<>(p.getMoveSet());

        // increase stats by 10%
        p.setHP(Math.round(p.getHp() * 1.1));
        p.setAttack(Math.round(p.getAttack() * 1.1));
        p.setDefense(Math.round(p.getDefense() * 1.1));
        p.setSpeed(Math.round(p.getSpeed() * 1.1));

        System.out.printf("\u001B[32m%s grew to level %d!\u001B[0m%n",
                p.getPokemonName(), p.getBaseLevel());
        System.out.printf("New stats: HP:%.0f ATK:%.0f DEF:%.0f SPD:%.0f%n",
                p.getHp(), p.getAttack(), p.getDefense(), p.getSpeed());

        // check for evolution
        if (p.getEvolutionLevel() != null && p.getBaseLevel() >= p.getEvolutionLevel()) {
            evolvePokemon(p, originalName, heldItem, moves);
        }
    }

    private void evolvePokemon(Pokemon p, String originalName,
            Item heldItem, ArrayList<Move> moves) {
        System.out.printf("\u001B[36m%s is evolving!\u001B[0m%n", p.getPokemonName());

        String evolvedName = getEvolvedForm(originalName);
        String[] evolvedTypes = getEvolvedTypes(originalName);
        double[] evolvedBaseStats = getEvolvedBaseStats(originalName);

        p.setPokemonName(evolvedName);
        p.setPokemonType1(evolvedTypes[0]);
        p.setPokemonType2(evolvedTypes.length > 1 ? evolvedTypes[1] : "");

        // keep the higher of current or evolved base stats
        p.setHP(Math.max(p.getHp(), evolvedBaseStats[0]));
        p.setAttack(Math.max(p.getAttack(), evolvedBaseStats[1]));
        p.setDefense(Math.max(p.getDefense(), evolvedBaseStats[2]));
        p.setSpeed(Math.max(p.getSpeed(), evolvedBaseStats[3]));

        // restore held item and move set
        p.setHeldItem(heldItem);
        p.setMoveSet(moves);

        System.out.printf("\u001B[32m%s evolved into %s!%n\u001B[0m",
                originalName, evolvedName);
        System.out.printf("New type: %s%s%n",
                evolvedTypes[0],
                evolvedTypes.length > 1 ? "/" + evolvedTypes[1] : "");
    }

    private String getEvolvedForm(String originalName) {
        switch (originalName.toLowerCase()) {
            case "charmander":
                return "Charmeleon";
            case "charmeleon":
                return "Charizard";
            case "squirtle":
                return "Wartortle";
            case "wartortle":
                return "Blastoise";
            default:
                return originalName;
        }
    }

    private String[] getEvolvedTypes(String originalName) {
        switch (originalName.toLowerCase()) {
            case "charmander":
            case "charmeleon":
                return new String[]{"Fire"};
            case "charizard":
                return new String[]{"Fire", "Flying"};
            case "squirtle":
            case "wartortle":
            case "blastoise":
                return new String[]{"Water"};
            default:
                return new String[]{""};
        }
    }

    private double[] getEvolvedBaseStats(String originalName) {
        switch (originalName.toLowerCase()) {
            case "charmander":
                return new double[]{39, 52, 43, 65};
            case "charmeleon":
                return new double[]{58, 64, 58, 80};
            case "charizard":
                return new double[]{78, 84, 78, 100};
            case "squirtle":
                return new double[]{44, 48, 65, 43};
            case "wartortle":
                return new double[]{59, 63, 80, 58};
            case "blastoise":
                return new double[]{79, 83, 100, 78};
            default:
                return new double[]{0, 0, 0, 0};
        }
    }
}
