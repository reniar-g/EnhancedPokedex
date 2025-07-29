package model;

import java.util.ArrayList;

public class EvolutionStone extends Item
{
    private final String stoneType;

    public EvolutionStone(int itemId, String itemName, String itemCategory,
                              String itemDescription, String itemEffect,
                              String itemPrice, String itemSellPrice, String stoneType) {
        super(itemId, itemName, itemCategory, itemDescription, itemEffect, itemPrice, itemSellPrice);
        this.stoneType = stoneType;
    }

    public String getStoneType() {
        return stoneType;
    }

    public boolean canEvolveWithStone(Pokemon pokemon) {
        String pokemonName = pokemon.getPokemonName().toLowerCase();

        switch (stoneType.toLowerCase()) {
            case "fire":
                return pokemonName.equals("vulpix") ||
                        pokemonName.equals("growlithe") ||
                        pokemonName.equals("eevee");
            case "water":
                return pokemonName.equals("poliwhirl") ||
                        pokemonName.equals("shellder") ||
                        pokemonName.equals("eevee") ||
                        pokemonName.equals("staryu");
            case "thunder":
                return pokemonName.equals("pikachu") ||
                        pokemonName.equals("eevee") ||
                        pokemonName.equals("eelektrik");
            case "leaf":
                return pokemonName.equals("gloom") ||
                        pokemonName.equals("weepinbell") ||
                        pokemonName.equals("exeggcute");
            case "moon":
                return pokemonName.equals("nidorina") ||
                        pokemonName.equals("clefairy") ||
                        pokemonName.equals("jigglypuff");
            case "sun":
                return pokemonName.equals("gloom") ||
                        pokemonName.equals("sunkern") ||
                        pokemonName.equals("cottonee");
            case "shiny":
                return pokemonName.equals("togetic") ||
                        pokemonName.equals("roselia") ||
                        pokemonName.equals("minccino");
            case "dusk":
                return pokemonName.equals("murkrow") ||
                        pokemonName.equals("misdreavus") ||
                        pokemonName.equals("doublade");
            case "dawn":
                return pokemonName.equals("kirlia") ||
                        pokemonName.equals("snorunt");
            case "ice":
                return pokemonName.equals("vulpix") ||
                        pokemonName.equals("darumaka") ||
                        pokemonName.equals("eevee");
            default:
                return false;
        }
    }

    public void useStone(Pokemon pokemon) {
        if (!canEvolveWithStone(pokemon)) {
            throw new IllegalStateException(pokemon.getPokemonName() + " cannot evolve with this stone");
        }

        // Store current state
        String originalName = pokemon.getPokemonName();
        ArrayList<Move> moves = new ArrayList<>(pokemon.getMoveSet());
        Item heldItem = pokemon.getHeldItem();

        // Evolve based on stone type
        String evolvedName = getEvolvedForm(originalName, stoneType);
        String[] evolvedTypes = getEvolvedTypes(evolvedName);
        double[] evolvedStats = getEvolvedStats(evolvedName);

        // Apply evolution changes
        pokemon.setPokemonName(evolvedName);
        pokemon.setPokemonType1(evolvedTypes[0]);
        pokemon.setPokemonType2(evolvedTypes.length > 1 ? evolvedTypes[1] : "");

        // Keep the higher of current or evolved stats
        pokemon.setHP(Math.max(pokemon.getHp(), evolvedStats[0]));
        pokemon.setAttack(Math.max(pokemon.getAttack(), evolvedStats[1]));
        pokemon.setDefense(Math.max(pokemon.getDefense(), evolvedStats[2]));
        pokemon.setSpeed(Math.max(pokemon.getSpeed(), evolvedStats[3]));

        // Restore original properties
        pokemon.setMoveSet(moves);
        pokemon.setHeldItem(heldItem);
    }

    private String getEvolvedForm(String originalName, String stoneType) {
        String lowerName = originalName.toLowerCase();

        switch (stoneType.toLowerCase()) {
            case "fire":
                if (lowerName.equals("eevee")) return "Flareon";
                if (lowerName.equals("vulpix")) return "Ninetales";
                if (lowerName.equals("growlithe")) return "Arcanine";
                break;
            case "water":
                if (lowerName.equals("eevee")) return "Vaporeon";
                if (lowerName.equals("poliwhirl")) return "Poliwrath";
                if (lowerName.equals("shellder")) return "Cloyster";
                if (lowerName.equals("staryu")) return "Starmie";
                break;
            case "thunder":
                if (lowerName.equals("eevee")) return "Jolteon";
                if (lowerName.equals("pikachu")) return "Raichu";
                if (lowerName.equals("eelektrik")) return "Eelektross";
                break;
            case "leaf":
                if (lowerName.equals("gloom")) return "Vileplume";
                if (lowerName.equals("weepinbell")) return "Victreebel";
                if (lowerName.equals("exeggcute")) return "Exeggutor";
                break;
            case "moon":
                if (lowerName.equals("nidorina")) return "Nidoqueen";
                if (lowerName.equals("nidorino")) return "Nidoking";
                if (lowerName.equals("clefairy")) return "Clefable";
                if (lowerName.equals("jigglypuff")) return "Wigglytuff";
                break;
            case "sun":
                if (lowerName.equals("gloom")) return "Bellossom";
                if (lowerName.equals("sunkern")) return "Sunflora";
                if (lowerName.equals("cottonee")) return "Whimsicott";
                break;
            case "shiny":
                if (lowerName.equals("togetic")) return "Togekiss";
                if (lowerName.equals("roselia")) return "Roserade";
                if (lowerName.equals("minccino")) return "Cinccino";
                break;
            case "dusk":
                if (lowerName.equals("murkrow")) return "Honchkrow";
                if (lowerName.equals("misdreavus")) return "Mismagius";
                if (lowerName.equals("doublade")) return "Aegislash";
                break;
            case "dawn":
                if (lowerName.equals("kirlia")) {
                    // Gender-based evolution
                    return "Gallade"; // Assuming male Kirlia
                }
                if (lowerName.equals("snorunt")) {
                    // Gender-based evolution
                    return "Froslass"; // Assuming female Snorunt
                }
                break;
            case "ice":
                if (lowerName.equals("eevee")) return "Glaceon";
                if (lowerName.equals("vulpix")) return "Alolan Ninetales";
                if (lowerName.equals("darumaka")) return "Galarian Darmanitan";
                break;
        }
        return originalName;
    }

    private String[] getEvolvedTypes(String evolvedName) {
        switch (evolvedName.toLowerCase()) {
            // Fire stone evolutions
            case "flareon": return new String[]{"Fire"};
            case "ninetales": return new String[]{"Fire"};
            case "arcanine": return new String[]{"Fire"};

            // Water stone evolutions
            case "vaporeon": return new String[]{"Water"};
            case "poliwrath": return new String[]{"Water", "Fighting"};
            case "cloyster": return new String[]{"Water", "Ice"};
            case "starmie": return new String[]{"Water", "Psychic"};

            // Thunder stone evolutions
            case "jolteon": return new String[]{"Electric"};
            case "raichu": return new String[]{"Electric"};
            case "eelektross": return new String[]{"Electric"};

            // Leaf stone evolutions
            case "vileplume": return new String[]{"Grass", "Poison"};
            case "victreebel": return new String[]{"Grass", "Poison"};
            case "exeggutor": return new String[]{"Grass", "Psychic"};

            // Moon stone evolutions
            case "nidoqueen": return new String[]{"Poison", "Ground"};
            case "nidoking": return new String[]{"Poison", "Ground"};
            case "clefable": return new String[]{"Fairy"};
            case "wigglytuff": return new String[]{"Normal", "Fairy"};

            // Sun stone evolutions
            case "bellossom": return new String[]{"Grass"};
            case "sunflora": return new String[]{"Grass"};
            case "whimsicott": return new String[]{"Grass", "Fairy"};

            // Shiny stone evolutions
            case "togekiss": return new String[]{"Fairy", "Flying"};
            case "roserade": return new String[]{"Grass", "Poison"};
            case "cinccino": return new String[]{"Normal"};

            // Dusk stone evolutions
            case "honchkrow": return new String[]{"Dark", "Flying"};
            case "mismagius": return new String[]{"Ghost"};
            case "aegislash": return new String[]{"Steel", "Ghost"};

            // Dawn stone evolutions
            case "gallade": return new String[]{"Psychic", "Fighting"};
            case "froslass": return new String[]{"Ice", "Ghost"};

            // Ice stone evolutions
            case "glaceon": return new String[]{"Ice"};
            case "alolan ninetales": return new String[]{"Ice", "Fairy"};
            case "galarian darmanitan": return new String[]{"Ice"};

            default: return new String[]{""};
        }
    }

    private double[] getEvolvedStats(String evolvedName) {
        // Base stats for evolved forms (HP, Attack, Defense, Speed)
        switch (evolvedName.toLowerCase()) {
            // Fire stone evolutions
            case "flareon": return new double[]{65, 130, 60, 65};
            case "ninetales": return new double[]{73, 76, 75, 100};
            case "arcanine": return new double[]{90, 110, 80, 95};

            // Water stone evolutions
            case "vaporeon": return new double[]{130, 65, 60, 65};
            case "poliwrath": return new double[]{90, 85, 95, 70};
            case "cloyster": return new double[]{50, 95, 180, 70};
            case "starmie": return new double[]{60, 75, 85, 115};

            // Thunder stone evolutions
            case "jolteon": return new double[]{65, 65, 60, 130};
            case "raichu": return new double[]{60, 90, 55, 110};
            case "eelektross": return new double[]{85, 115, 80, 50};

            // Leaf stone evolutions
            case "vileplume": return new double[]{75, 80, 85, 50};
            case "victreebel": return new double[]{80, 105, 65, 70};
            case "exeggutor": return new double[]{95, 95, 85, 55};

            // Moon stone evolutions
            case "nidoqueen": return new double[]{90, 82, 87, 76};
            case "nidoking": return new double[]{81, 92, 77, 85};
            case "clefable": return new double[]{95, 70, 73, 60};
            case "wigglytuff": return new double[]{140, 70, 45, 45};

            // Sun stone evolutions
            case "bellossom": return new double[]{75, 80, 85, 50};
            case "sunflora": return new double[]{75, 75, 55, 30};
            case "whimsicott": return new double[]{60, 67, 85, 116};

            // Shiny stone evolutions
            case "togekiss": return new double[]{85, 50, 95, 80};
            case "roserade": return new double[]{60, 70, 55, 90};
            case "cinccino": return new double[]{75, 95, 60, 115};

            // Dusk stone evolutions
            case "honchkrow": return new double[]{100, 125, 52, 71};
            case "mismagius": return new double[]{60, 60, 60, 105};
            case "aegislash": return new double[]{60, 50, 150, 60}; // Shield Forme

            // Dawn stone evolutions
            case "gallade": return new double[]{68, 125, 65, 80};
            case "froslass": return new double[]{70, 80, 70, 110};

            // Ice stone evolutions
            case "glaceon": return new double[]{65, 60, 110, 65};
            case "alolan ninetales": return new double[]{73, 67, 75, 109};
            case "galarian darmanitan": return new double[]{105, 140, 55, 95};

            default: return new double[]{0, 0, 0, 0};
        }
    }
}
