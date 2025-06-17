import java.util.ArrayList;

public class Pokemon {

    private int pokedexNumber;
    private String pokemonName;
    private String pokemonType1;
    private String pokemonType2;
    private int baseLevel;
    private int evolvesFrom;
    private int evolvesTo;
    private int evolutionLevel;
    private int hp, attack, defense, speed;
    private ArrayList<Move> moves;
    private Item heldItem;

    // constructor
    public Pokemon(int pokedexNumber, String name, String type1, String type2,
            int baseLevel, int evolvesFrom, int evolvesTo, int evolutionLevel,
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
        this.moves = new ArrayList<>();
        this.heldItem = null; // null kasi wala pang hawak

        // default moves
        this.moves.add(new Move("Tackle", "A basic attack", "TM", type1, null));
        this.moves.add(new Move("Defend", "Raises defense", "TM", type1, null));
    }

    public void cry() {
        System.out.println(pokemonName + " cries out!");
    }

    //setter for held item kasi nagbabago value
    public void setHeldItem(Item item) {
        this.heldItem = item;
    }

    public boolean hasMove(String moveName) {
        for (Move move : moves) {
            if (move.getMoveName().equalsIgnoreCase(moveName)) {
                return true;
            }
        }
        return false;
    }

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

    public int getEvolvesFrom() {
        return evolvesFrom;
    }

    public int getEvolvesTo() {
        return evolvesTo;
    }

    public int getEvolutionLevel() {
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

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public Item getHeldItem() {
        return heldItem;
    }

    public String displayPokemon() {
        String typeInfo = pokemonType1 + (pokemonType2 != null ? "/" + pokemonType2 : "");
        String evolvesFromInfo = evolvesFrom != -1 ? String.valueOf(evolvesFrom) : "None";
        String evolvesToInfo = evolvesTo != -1 ? String.valueOf(evolvesTo) : "None";
        String heldItemInfo = heldItem != null ? heldItem.getItemName() : "None";

        return "[" + pokedexNumber + "] " + pokemonName
                + " | Type: " + typeInfo
                + " | HP: " + hp + " ATK: " + attack + " DEF: " + defense + " SPD: " + speed
                + " | Evolves From: " + evolvesFromInfo
                + " | Evolves To: " + evolvesToInfo
                + " | Evo Level: " + evolutionLevel
                + " | Held Item: " + heldItemInfo;
    }

}
