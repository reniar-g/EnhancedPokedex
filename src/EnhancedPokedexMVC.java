package src;

import controller.*;
import java.util.ArrayList;
import model.*;
import util.LoadData;
import view.MainPokedexView;

public class EnhancedPokedexMVC {

    private static final ArrayList<Pokemon> pokedex = new ArrayList<>();
    private static final ArrayList<Move> moveList = new ArrayList<>();
    private static final ArrayList<Item> itemList = new ArrayList<>();
    private static final ArrayList<Trainer> trainerList = new ArrayList<>();

    private static PokemonController pokemonController;
    private static MoveController moveController;
    private static ItemController itemController;
    private static TrainerController trainerController;

    private static void loadData() {
        // clears the data and adds the new data
        pokedex.clear();
        itemList.clear();
        moveList.clear();
        trainerList.clear();

        pokedex.addAll(LoadData.loadPokemons());
        moveList.addAll(LoadData.loadMoves());
        itemList.addAll(LoadData.loadItems());
        trainerList.addAll(LoadData.loadTrainers());

        // assign default moves to each Pokemon
        if (moveList.size() >= 2) {
            for (Pokemon p : pokedex) {
                p.getMoveSet().clear();
                // If Pokemon doesn't have 2 moves yet, add basic moves
                while (p.getMoveSet().size() < 2) {
                    for (Move move : moveList) {
                        if (move.getMoveType1().equals("Normal")) {
                            p.getMoveSet().add(move);
                            if (p.getMoveSet().size() >= 2) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private static void initializeControllers() {
        moveController = new MoveController(moveList);
        pokemonController = new PokemonController(pokedex, moveController);
        itemController = new ItemController(itemList);
        trainerController = new TrainerController(trainerList);

        trainerController.setPokemonController(pokemonController);
        trainerController.setMoveController(moveController);
        trainerController.setItemController(itemController);
    }

    public static void main(String[] args) {
        loadData();
        initializeControllers();
        new MainPokedexView(pokedex, moveList, itemList, trainerList);
    }
}
