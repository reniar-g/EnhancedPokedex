package controller;

import java.util.ArrayList;
import java.util.List;
import model.*;

public class TrainerController {

    private final List<Trainer> trainerList;
    private ItemController itemController;
    private PokemonController pokemonController;
    private MoveController moveController;

    public TrainerController(ArrayList<Trainer> trainerList) {
        this.trainerList = trainerList;
    }

    // assigns default moves to a new pokemon when added to a trainer
    private List<Move> getDefaultMoves() {
        List<Move> defaultMoves = new ArrayList<>();
        if (moveController != null) {
            for (Move move : moveController.getMoveList()) {
                if (move.getMoveName().equalsIgnoreCase("Tackle")
                        || move.getMoveName().equalsIgnoreCase("Defend")) {
                    defaultMoves.add(move); // add default moves (Tackle and Defend)
                }
            }
        }
        return defaultMoves;
    }

    public ItemController getItemController() {
        return itemController;
    }

    public PokemonController getPokemonController() {
        return pokemonController;
    }

    public MoveController getMoveController() {
        return moveController;
    }

    public List<Trainer> getTrainerList() {
        return trainerList;
    }

    public void setItemController(ItemController itemController) {
        this.itemController = itemController;
    }

    public void setPokemonController(PokemonController pokemonController) {
        this.pokemonController = pokemonController;
    }

    public void setMoveController(MoveController moveController) {
        this.moveController = moveController;
    }

    // adds a new trainer to the list
    public boolean addTrainer(int trainerId, String name, String birthdate,
            String sex, String hometown, String description) {
        if (isTrainerIdExists(trainerId)) {
            return false;
        }
        Trainer trainer = new Trainer(trainerId, name, birthdate, sex, hometown, description);
        trainerList.add(trainer);
        return true;
    }

    // checks if a trainer ID already exists in the list
    private boolean isTrainerIdExists(int trainerId) {
        for (Trainer trainer : trainerList) {
            if (trainer.getTrainerId() == trainerId) {
                return true;
            }
        }
        return false;
    }

    // checks for valid sex values (M or F)
    public static boolean isValidSex(String sex) {
        if (sex == null) {
            return false;
        }
        String upperSex = sex.toUpperCase();
        return upperSex.equals("M") || upperSex.equals("F");
    }

    // uses an item on a Pokemon
    public ItemUseResult useItem(Trainer trainer, Item item, Pokemon pokemon) {
        if (trainer == null || item == null || pokemon == null) { // check for null values, if null return error
            return new ItemUseResult(false, 0, "Invalid trainer, item or Pokemon");
        }

        // check if item is in trainer's inventory
        if (!trainer.getInventory().contains(item)) {
            return new ItemUseResult(false, 0, "Item not in trainer's inventory");
        }

        // check if the pokemon is owned by the trainer
        if (!trainer.getPokemonLineup().contains(pokemon) && !trainer.getPokemonStorage().contains(pokemon)) {
            return new ItemUseResult(false, 0, "Pokemon not owned by trainer");
        }

        // apply the item effect on the Pokemon
        ItemUseResult result = applyItemEffect(item, pokemon);
        if (result.success && !item.getItemCategory().equalsIgnoreCase("Held")) {
            trainer.getInventory().remove(item);
        }
        return result;
    }

    // applies the effect of the item on the Pokemon
    public ItemUseResult applyItemEffect(Item item, Pokemon pokemon) {
        try {
            if (item instanceof Vitamin vitamin) {
                switch (item.getItemName()) {
                    case "HP Up" ->
                        vitamin.useHPUp(pokemon);
                    case "Protein" ->
                        vitamin.useProtein(pokemon);
                    case "Iron" ->
                        vitamin.useIron(pokemon);
                    case "Carbos" ->
                        vitamin.useCarbos(pokemon);
                    case "Zinc" ->
                        vitamin.useZinc(pokemon);
                }
                return new ItemUseResult(true, pokemon.getBaseLevel(), "Vitamin applied successfully");
            }

            // check if item is a feather and apply its effect
            if (item instanceof Feather feather) {
                if (item.getItemName().contains("Health")) {
                    feather.useHealthFeather(pokemon);
                } else if (item.getItemName().contains("Muscle")) {
                    feather.useMuscleFeather(pokemon);
                } else if (item.getItemName().contains("Resist")) {
                    feather.useResistFeather(pokemon);
                } else if (item.getItemName().contains("Swift")) {
                    feather.useSwiftFeather(pokemon);
                }
                return new ItemUseResult(true, pokemon.getBaseLevel(), "Feather applied successfully");
            }

            // check if item is a levelling item or evolution stone
            if (item instanceof Levelling levelling) {
                if (item.getItemName().equals("Rare Candy")) {
                    String oldName = pokemon.getPokemonName();
                    levelling.useRareCandy(pokemon);

                    // if pokemon can evolve through level up
                    if (pokemon.getEvolvesTo() != null && pokemon.getEvolutionLevel() != null) {
                        if (pokemon.getBaseLevel() >= pokemon.getEvolutionLevel()) {
                            // pokemon should evolve
                            evolvePokemonByLevel(pokemon);
                            return new ItemUseResult(true, pokemon.getBaseLevel(),
                                    oldName + " grew to level " + pokemon.getBaseLevel() + " and evolved into " + pokemon.getPokemonName() + "!");
                        }
                    }
                    // if pokemon just leveled up, return level up message
                    return new ItemUseResult(true, pokemon.getBaseLevel(),
                            pokemon.getPokemonName() + " grew to level " + pokemon.getBaseLevel());
                }
            }

            // check if item is an evolution stone
            if (item instanceof EvolutionStone stone) {
                if (stone.canEvolveWithStone(pokemon)) {
                    stone.useStone(pokemon); // apply the stone effect if it can evolve w the stones
                    return new ItemUseResult(true, pokemon.getBaseLevel(),
                            pokemon.getPokemonName() + " evolved using " + stone.getItemName() + "!");
                } else {
                    return new ItemUseResult(false, pokemon.getBaseLevel(),
                            pokemon.getPokemonName() + " cannot evolve with " + stone.getItemName());
                }
            }
            return new ItemUseResult(false, pokemon.getBaseLevel(), "Unknown item type");
        } catch (Exception e) {
            return new ItemUseResult(false, pokemon.getBaseLevel(), e.getMessage());
        }
    }

    // evolves a Pokemon through level up
    private void evolvePokemonByLevel(Pokemon pokemon) {
        if (pokemon.getEvolvesTo() == null) {
            return;
        }

        // find the evolution target in the Pokemon list
        if (pokemonController != null) {
            for (Pokemon evolutionCandidate : pokemonController.getPokedex()) {
                if (evolutionCandidate.getPokedexNumber() == pokemon.getEvolvesTo().intValue()) {
                    // store current stats before evolution
                    double currentHp = pokemon.getHp();
                    double currentAttack = pokemon.getAttack();
                    double currentDefense = pokemon.getDefense();
                    double currentSpeed = pokemon.getSpeed();

                    // update the current pokemon to become the evolved form
                    pokemon.setPokedexNumber(evolutionCandidate.getPokedexNumber());
                    pokemon.setPokemonName(evolutionCandidate.getPokemonName());
                    pokemon.setPokemonType1(evolutionCandidate.getPokemonType1());
                    pokemon.setPokemonType2(evolutionCandidate.getPokemonType2());
                    pokemon.setEvolvesFrom(evolutionCandidate.getEvolvesFrom());
                    pokemon.setEvolvesTo(evolutionCandidate.getEvolvesTo());
                    pokemon.setEvolutionLevel(evolutionCandidate.getEvolutionLevel());

                    // set stats to the higher value between current stats and evolved form's base stats
                    pokemon.setHP(Math.max(currentHp, evolutionCandidate.getHp()));
                    pokemon.setAttack(Math.max(currentAttack, evolutionCandidate.getAttack()));
                    pokemon.setDefense(Math.max(currentDefense, evolutionCandidate.getDefense()));
                    pokemon.setSpeed(Math.max(currentSpeed, evolutionCandidate.getSpeed()));
                    break;
                }
            }
        }
    }

    public static class ItemUseResult {

        public final boolean success;
        public final int newLevel;
        public final String message;

        public ItemUseResult(boolean success, int newLevel, String message) {
            this.success = success;
            this.newLevel = newLevel;
            this.message = message;
        }
    }

    // get pokemon list details for displaying in trainer view
    public List<String> getPokemonListDetails(List<Pokemon> pokemonList) {
        List<String> details = new ArrayList<>();
        for (Pokemon p : pokemonList) {
            String type = p.getPokemonType1();
            if (p.getPokemonType2() != null && !p.getPokemonType2().isEmpty()) {
                type += "/" + p.getPokemonType2();
            }

            details.add(String.format("#%d %s (%s) Lv.%d | HP:%d ATK:%d DEF:%d SPD:%d",
                    p.getPokedexNumber(),
                    p.getPokemonName(),
                    type,
                    p.getBaseLevel(),
                    (int) p.getHp(),
                    (int) p.getAttack(),
                    (int) p.getDefense(),
                    (int) p.getSpeed()));
        }
        return details;
    }

    // adds a pokemon to a trainer's lineup or storage
    public AddPokemonResult addPokemonToTrainer(Trainer trainer, Pokemon originalPokemon) {
        if (trainer == null || originalPokemon == null) {
            return new AddPokemonResult(false, null, "Invalid trainer or Pokemon");
        }

        Pokemon copyPokemon = new Pokemon(
                originalPokemon.getPokedexNumber(),
                originalPokemon.getPokemonName(),
                originalPokemon.getPokemonType1(),
                originalPokemon.getPokemonType2(),
                originalPokemon.getBaseLevel(),
                originalPokemon.getEvolvesFrom(),
                originalPokemon.getEvolvesTo(),
                originalPokemon.getEvolutionLevel(),
                (int) originalPokemon.getHp(),
                (int) originalPokemon.getAttack(),
                (int) originalPokemon.getDefense(),
                (int) originalPokemon.getSpeed()
        );

        // Add default moves first (Tackle and Defend)
        List<Move> defaultMoves = getDefaultMoves();
        if (defaultMoves.size() < 2) {
            return new AddPokemonResult(false, null, "Default moves (Tackle and Defend) not found in move list");
        }
        copyPokemon.getMoveSet().addAll(defaultMoves);

        // Copy additional moves if any (up to maximum of 4 total)
        for (Move move : originalPokemon.getMoveSet()) {
            if (!move.getMoveName().equalsIgnoreCase("Tackle")
                    && !move.getMoveName().equalsIgnoreCase("Defend")) {
                if (copyPokemon.getMoveSet().size() < 4) {
                    copyPokemon.getMoveSet().add(move);
                }
            }
        }

        // if the trainer's lineup is not full, add to lineup
        // otherwise, add to storage if storage is not full
        if (trainer.getPokemonLineup().size() < 6) {
            trainer.getPokemonLineup().add(copyPokemon);
            return new AddPokemonResult(true, "lineup", null);
        } else if (trainer.getPokemonStorage().size() < 20) {
            trainer.getPokemonStorage().add(copyPokemon);
            return new AddPokemonResult(true, "storage", null);
        } else {
            return new AddPokemonResult(false, null, "Both lineup and storage are full");
        }
    }

    public static class AddPokemonResult {

        public final boolean success;
        public final String location; // "lineup" or "storage"
        public final String errorMessage;

        public AddPokemonResult(boolean success, String location, String errorMessage) {
            this.success = success;
            this.location = location;
            this.errorMessage = errorMessage;
        }
    }

    // switches a pokemon between lineup and storage
    public SwitchResult switchPokemon(Trainer trainer, Pokemon pokemon, boolean toLineup) {
        if (trainer == null || pokemon == null) {
            return new SwitchResult(false, "Invalid trainer or Pokemon");
        }

        if (toLineup) {
            if (!trainer.getPokemonStorage().contains(pokemon)) {
                return new SwitchResult(false, "Pokemon not in storage");
            }
            if (trainer.getPokemonLineup().size() >= 6) {
                return new SwitchResult(false, "Lineup is full");
            }
            trainer.getPokemonStorage().remove(pokemon);
            trainer.getPokemonLineup().add(pokemon);
            return new SwitchResult(true, "Moved to lineup");
        } else {
            if (!trainer.getPokemonLineup().contains(pokemon)) {
                return new SwitchResult(false, "Pokemon not in lineup");
            }
            trainer.getPokemonLineup().remove(pokemon);
            trainer.getPokemonStorage().add(pokemon);
            return new SwitchResult(true, "Moved to storage");
        }
    }

    public static class SwitchResult {

        public final boolean success;
        public final String message;

        public SwitchResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }

    // releases a pokemon from a trainer's lineup or storage
    public Pokemon releasePokemon(Trainer trainer, Pokemon pokemon, boolean fromLineup) {
        if (trainer == null || pokemon == null) {
            return null;
        }
        // ternary operator to determine source list
        // if fromLineup is true, use lineup; otherwise use storage
        ArrayList<Pokemon> source = fromLineup ? trainer.getPokemonLineup() : trainer.getPokemonStorage();
        if (source.contains(pokemon)) {
            source.remove(pokemon);
            return pokemon;
        }
        return null;
    }

    // teaches a move to a pokemon, replacing an old move if needed
    public TeachMoveResult teachMove(Pokemon pokemon, Move newMove, Move oldMove) {
        if (pokemon == null || newMove == null) {
            return new TeachMoveResult(false, null, null, "Invalid Pokemon or move");
        }

        // check if the pokemon already knows the move
        if (pokemon.hasMove(newMove.getMoveName())) {
            return new TeachMoveResult(false, null, null,
                    pokemon.getPokemonName() + " already knows " + newMove.getMoveName());
        }

        // check for type compatibility
        boolean typeMatches = false;
        String pokemonType1 = pokemon.getPokemonType1();
        String pokemonType2 = pokemon.getPokemonType2();
        String moveType1 = newMove.getMoveType1();
        String moveType2 = newMove.getMoveType2();

        // check if move's primary type matches either of pokemon's types
        if (moveType1 != null && !moveType1.isEmpty()) {
            if (moveType1.equalsIgnoreCase(pokemonType1)) {
                typeMatches = true;
            }
            if (!typeMatches && pokemonType2 != null && !pokemonType2.isEmpty()
                    && moveType1.equalsIgnoreCase(pokemonType2)) {
                typeMatches = true;
            }
        }

        // if no match yet, check if move's secondary type matches either of pokemon's types
        if (!typeMatches && moveType2 != null && !moveType2.isEmpty()) {
            if (moveType2.equalsIgnoreCase(pokemonType1)) {
                typeMatches = true;
            }
            if (!typeMatches && pokemonType2 != null && !pokemonType2.isEmpty()
                    && moveType2.equalsIgnoreCase(pokemonType2)) {
                typeMatches = true;
            }
        }

        // if no type match, return error
        if (!typeMatches) {
            return new TeachMoveResult(false, null, null,
                    pokemon.getPokemonName() + " cannot learn " + newMove.getMoveName()
                    + " due to type incompatibility. Move types: " + moveType1
                    + (moveType2 != null && !moveType2.isEmpty() ? "/" + moveType2 : "")
                    + ", Pokemon types: " + pokemonType1
                    + (pokemonType2 != null && !pokemonType2.isEmpty() ? "/" + pokemonType2 : ""));
        }

        // if the move is an HM, allow it to be added regardless of current moves
        if (newMove.getMoveClassification().equalsIgnoreCase("HM")) {
            // add the HM move directly without checking for existing moves
            pokemon.getMoveSet().add(newMove);
            return new TeachMoveResult(true, null, newMove, null);
        }

        // if the pokemon already knows 4 moves, replace one
        if (pokemon.getMoveSet().size() >= 4) {
            // if no old move is specified, return error
            if (oldMove == null) {
                return new TeachMoveResult(false, null, null,
                        "Pokemon already knows 4 moves. Select a move to forget.");
            }

            // verify that the old move exists in the pokemon's moveset
            boolean foundMove = false;
            for (Move m : pokemon.getMoveSet()) {
                if (m.getMoveName().equals(oldMove.getMoveName())) {
                    if (m.getMoveClassification().equalsIgnoreCase("HM")) {
                        return new TeachMoveResult(false, null, null,
                                "Cannot replace HM move " + oldMove.getMoveName());
                    }
                    foundMove = true;
                    break;
                }
            }

            // if the old move is not found, return error
            if (!foundMove) {
                return new TeachMoveResult(false, null, null,
                        "Selected move to forget not found in Pokemon's moveset");
            }

            // replace the old move with the new one
            pokemon.getMoveSet().remove(oldMove);
            pokemon.getMoveSet().add(newMove);
            return new TeachMoveResult(true, oldMove, newMove, null);
        } else {
            // if the pokemon has less than 4 moves, just add
            pokemon.getMoveSet().add(newMove);
            return new TeachMoveResult(true, null, newMove, null);
        }
    }

    // TeachMoveResult class to encapsulate the result of a teach move operation
    public static class TeachMoveResult {

        public final boolean success;
        public final Move replacedMove;
        public final Move newMove;
        public final String errorMessage;

        public TeachMoveResult(boolean success, Move replacedMove, Move newMove, String errorMessage) {
            this.success = success;
            this.replacedMove = replacedMove;
            this.newMove = newMove;
            this.errorMessage = errorMessage;
        }
    }
}
