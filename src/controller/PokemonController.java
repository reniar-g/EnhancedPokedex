package controller;

import java.util.ArrayList;
import java.util.List;
import model.*;

public class PokemonController {

    private final ArrayList<Pokemon> pokedex;
    private final MoveController moveController;

    public PokemonController(ArrayList<Pokemon> pokedex, MoveController moveController) {
        this.pokedex = pokedex;
        this.moveController = moveController;
    }

    public ArrayList<Pokemon> getPokedex() {
        return pokedex;
    }

    /**
     * Checks if a Pokédex number already exists. (Requirement #1)
     */
    public boolean isPokedexNumberExists(int number) {
        for (Pokemon p : pokedex) {
            if (p.getPokedexNumber() == number) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a Pokémon name already exists in the Pokédex. (Requirement #2)
     */
    public boolean isPokemonNameExists(String name) {
        for (Pokemon p : pokedex) {
            if (p.getPokemonName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    // add pokemon for gui
    public void addPokemon(int pokedexNumber, String name, String type1, String type2, int baseLevel,
            Integer evolvesFrom, Integer evolvesTo, Integer evolutionLevel,
            double hp, double attack, double defense, double speed) {

        Pokemon p = new Pokemon(pokedexNumber, name, type1, type2, baseLevel, evolvesFrom, evolvesTo, evolutionLevel,
                (int) hp, (int) attack, (int) defense, (int) speed);

        p.addDefaultMoves(moveController.getMoveList()); // Add default moves to the new Pokémon
        pokedex.add(p);
    }

    /**
     * Returns the evolution chain (base, second, third) for the given Pokémon.
     *
     * @param p The Pokémon to find the chain for.
     * @return An array: [base, secondEvo, thirdEvo]
     */
    public Pokemon[] getEvolutionChain(Pokemon p) {
        List<Pokemon> pokedex = getPokedex();
        Pokemon base = null;
        Pokemon secondEvo = null;
        Pokemon thirdEvo = null;

        // Find base evolution
        Pokemon current = p;
        while (current.getEvolvesFrom() != null && current.getEvolvesFrom() != 0) {
            for (Pokemon poke : pokedex) {
                if (poke.getPokedexNumber() == current.getEvolvesFrom()) {
                    current = poke;
                    break;
                }
            }
        }
        base = current;

        // Find second and third evolutions
        if (base.getEvolvesTo() != null && base.getEvolvesTo() != 0) {
            for (Pokemon poke : pokedex) {
                if (poke.getPokedexNumber() == base.getEvolvesTo()) {
                    secondEvo = poke;
                    break;
                }
            }
            if (secondEvo != null && secondEvo.getEvolvesTo() != null && secondEvo.getEvolvesTo() != 0) {
                for (Pokemon poke : pokedex) {
                    if (poke.getPokedexNumber() == secondEvo.getEvolvesTo()) {
                        thirdEvo = poke;
                        break;
                    }
                }
            }
        }
        return new Pokemon[]{base, secondEvo, thirdEvo};
    }

    public String getCanonicalType(String input, String[] validTypes) {
        for (String t : validTypes) {
            if (t.equalsIgnoreCase(input)) {
                return t;
            }
        }
        return null;
    }

    public boolean isValidStat(double value) {
        return value > 0;
    }
}
