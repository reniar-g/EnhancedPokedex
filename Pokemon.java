
import java.util.ArrayList;
import java.util.List;

// Pokémon class represents a Pokémon with various attributes and methods to manage its state and actions.
public class Pokemon {

    private int pokedexNumber;
    private String pokemonName;
    private String pokemonType1;
    private String pokemonType2; // Optional, nullable
    private int baseLevel;
    private Integer evolvesFrom; // Pokédex Number, nullable
    private Integer evolvesTo;   // Pokédex Number, nullable
    private Integer evolutionLevel; // Nullable
    private int hp;
    private int attack;
    private int defense;
    private int speed;
    private List<Move> moveSet;
    private Item heldItem;

    // Constructor for Pokémon class
    public Pokemon(int pokedexNumber, String name, String type1, String type2, int baseLevel,
            Integer evolvesFrom, Integer evolvesTo, Integer evolutionLevel,
            int hp, int attack, int defense, int speed) {
        this.pokedexNumber = pokedexNumber;
        this.pokemonName = name;
        this.pokemonType1 = type1;
        this.pokemonType2 = type2;
        this.baseLevel = baseLevel;
        this.evolvesFrom = evolvesFrom;
        this.evolvesTo = evolvesTo;
        this.evolutionLevel = evolutionLevel;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.moveSet = new ArrayList<>();

        moveSet.add(new Move("Tackle", "A physical attack in which the user charges and slams into the target.", "TM", "Normal"));
        moveSet.add(new Move("Defend", "A defensive move that raises the user's Defense stat.", "TM", "Normal"));
        this.heldItem = null;
    }

    // Getters for Pokémon attributes
    public int getPokedexNumber() {
        return pokedexNumber;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public String getPokemonType1() {
        return pokemonType1;
    }

    public String getPokemonType2() {
        return pokemonType2;
    }

    public int getBaseLevel() {
        return baseLevel;
    }

    public Integer getEvolvesFrom() {
        return evolvesFrom;
    }

    public Integer getEvolvesTo() {
        return evolvesTo;
    }

    public Integer getEvolutionLevel() {
        return evolutionLevel;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public List<Move> getMoveSet() {
        return moveSet;
    }

    public Item getHeldItem() {
        return heldItem;
    }

    /**
     * Sets the Pokémon's held item.
     */
    public void setHeldItem(Item heldItem) {
        this.heldItem = heldItem;
    }

    /**
     * Simulates the Pokémon's cry.
     */
    public void cry() {
        System.out.println(pokemonName + " cries: " + pokemonName.toUpperCase() + "!");
    }

    /**
     * Helper method to convert null Integer to "N/A" string
     */
    private String formatNullableInteger(Integer value) {
        return value == null ? "N/A" : value.toString();
    }

    /**
     * Displays the header for Pokemon information table
     */
    public static void displayPokemonHeader() {
        System.out.printf("%-4s %-14s %-15s %-6s %-7s %-8s %-8s %-8s %-15s %-13s %-15s%n",
                "ID", "Name", "Type", "Level", "HP", "Atk", "Def", "Spd", "Evolves From", "Evolves To", "Evo Level");
        System.out.println("---------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Displays Pokemon information with N/A for null values in a formatted
     * table style with labels using printf
     */
    public void displayPokemon() {
        String type = pokemonType1 + (pokemonType2 != null && !pokemonType2.isEmpty() ? "/" + pokemonType2 : "");
        System.out.printf("%-4s %-14s %-15s %-6s %-7s %-8s %-8s %-8s %-15s %-13s %-15s%n",
                "#" + pokedexNumber,
                pokemonName,
                "(" + type + ")",
                "Lv." + baseLevel,
                "HP:" + hp,
                "Atk:" + attack,
                "Def:" + defense,
                "Spd:" + speed,
                "From:" + formatNullableInteger(evolvesFrom),
                "To:" + formatNullableInteger(evolvesTo),
                "EvoLv:" + formatNullableInteger(evolutionLevel));
    }
}
