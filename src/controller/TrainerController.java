package controller;

import java.util.ArrayList;
import java.util.List;
import model.*;

public class TrainerController {

    private List<Trainer> trainerList;
    private ItemController itemController;
    private PokemonController pokemonController;
    private MoveController moveController;

    public TrainerController(ArrayList<Trainer> trainerList) {
        this.trainerList = trainerList;
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

    /**
     * Adds a new trainer to the system.
     *
     * @return true if trainer was added successfully, false if ID already
     * exists
     */
    public boolean addTrainer(int trainerId, String name, String birthdate,
            String sex, String hometown, String description) {
        if (isTrainerIdExists(trainerId)) {
            return false;
        }

        Trainer trainer = new Trainer(trainerId, name, birthdate, sex, hometown, description);
        trainerList.add(trainer);
        return true;
    }

//        Item rareCandy = itemController.getItemByName("Rare Candy");
//
//        if (rareCandy != null) {
//            for (int i = 0; i < 5; i++) {
//                trainer.getInventory().add(rareCandy);
//            }
//        }
//
//        trainerList.add(trainer);
//        System.out.println("Trainer " + name + " added successfully!");
//    }
    /**
     * Searches trainers by keyword in name, hometown, or description
     *
     * @param keyword The search term
     * @return List of matching trainers
     */
    public ArrayList<Trainer> searchTrainers(String keyword) {
        ArrayList<Trainer> results = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            return results;
        }

        keyword = keyword.toLowerCase();
        for (Trainer trainer : trainerList) {
            if (trainer.getTrainerName().toLowerCase().contains(keyword)
                    || trainer.getTrainerHometown().toLowerCase().contains(keyword)
                    || trainer.getTrainerDescription().toLowerCase().contains(keyword)
                    || String.valueOf(trainer.getTrainerId()).equals(keyword)) {
                results.add(trainer);
            }
        }
        return results;
    }

    /**
     * Checks if a trainer ID already exists in the trainer list.
     */
    private boolean isTrainerIdExists(int trainerId) {
        for (Trainer trainer : trainerList) {
            if (trainer.getTrainerId() == trainerId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates if a given sex value is valid
     *
     * @return true if sex is valid (M/F), false otherwise
     */
    public static boolean isValidSex(String sex) {
        if (sex == null) {
            return false;
        }
        String upperSex = sex.toUpperCase();
        return upperSex.equals("M") || upperSex.equals("F");
    }

    /**
     * Get trainer details including inventory and Pokemon
     *
     * @param trainerId The ID of the trainer to get details for
     * @return Trainer object if found, null if not found
     */
    public Trainer getTrainerDetails(int trainerId) {
        for (Trainer trainer : trainerList) {
            if (trainer.getTrainerId() == trainerId) {
                return trainer;
            }
        }
        return null;
    }

    // These methods have been moved to ItemController
    // Keeping the method signatures here for reference during the transition
    /*
    public BuyItemResult buyItem(Trainer trainer, Item item)
    public SellItemResult sellItem(Trainer trainer, Item item)
     */
    /**
     * Attempts to buy an item for a trainer
     *
     * @return A Result object containing success status and new money balance
     */
    public BuyItemResult buyItem(Trainer trainer, Item item) {
        if (trainer == null || item == null) {
            return new BuyItemResult(false, trainer.getTrainerMoney(), "Invalid trainer or item");
        }

        try {
            double price = Double.parseDouble(item.getItemPrice().replace("P", "").replace(",", ""));
            if (trainer.getTrainerMoney() >= price) {
                trainer.setTrainerMoney(trainer.getTrainerMoney() - price);
                trainer.getInventory().add(item);
                return new BuyItemResult(true, trainer.getTrainerMoney(), null);
            } else {
                return new BuyItemResult(false, trainer.getTrainerMoney(), "Not enough money");
            }
        } catch (NumberFormatException e) {
            return new BuyItemResult(false, trainer.getTrainerMoney(), "Invalid price format");
        }
    }

    /**
     * Attempts to sell an item for a trainer
     *
     * @return A Result object containing success status and new money balance
     */
    public SellItemResult sellItem(Trainer trainer, Item item) {
        if (trainer == null || item == null) {
            return new SellItemResult(false, trainer.getTrainerMoney(), "Invalid trainer or item");
        }

        try {
            double price = Double.parseDouble(item.getItemPrice().replace("P", "").replace(",", ""));
            double sellPrice = price * 0.5;
            if (trainer.getInventory().remove(item)) {
                trainer.setTrainerMoney(trainer.getTrainerMoney() + sellPrice);
                return new SellItemResult(true, trainer.getTrainerMoney(), null);
            } else {
                return new SellItemResult(false, trainer.getTrainerMoney(), "Item not found in inventory");
            }
        } catch (NumberFormatException e) {
            return new SellItemResult(false, trainer.getTrainerMoney(), "Invalid price format");
        }
    }

    // Result classes for GUI operations
    public static class BuyItemResult {

        public final boolean success;
        public final double newBalance;
        public final String errorMessage;

        public BuyItemResult(boolean success, double newBalance, String errorMessage) {
            this.success = success;
            this.newBalance = newBalance;
            this.errorMessage = errorMessage;
        }
    }

    public static class SellItemResult {

        public final boolean success;
        public final double newBalance;
        public final String errorMessage;

        public SellItemResult(boolean success, double newBalance, String errorMessage) {
            this.success = success;
            this.newBalance = newBalance;
            this.errorMessage = errorMessage;
        }
    }

    /**
     * Use an item on a Pokemon
     *
     * @return ItemUseResult containing the result of using the item
     */
    public ItemUseResult useItem(Trainer trainer, Item item, Pokemon pokemon) {
        if (trainer == null || item == null || pokemon == null) {
            return new ItemUseResult(false, 0, "Invalid trainer, item or Pokemon");
        }

        if (!trainer.getInventory().contains(item)) {
            return new ItemUseResult(false, 0, "Item not in trainer's inventory");
        }

        if (!trainer.getPokemonLineup().contains(pokemon) && !trainer.getPokemonStorage().contains(pokemon)) {
            return new ItemUseResult(false, 0, "Pokemon not owned by trainer");
        }

        ItemUseResult result = applyItemEffect(item, pokemon);
        if (result.success && !item.getItemCategory().equalsIgnoreCase("Held")) {
            trainer.getInventory().remove(item);
        }
        return result;
    }

    /**
     * Attempts to apply an item effect to a Pokemon
     *
     * @return ItemUseResult containing success status and effect details
     */
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

            if (item instanceof Levelling levelling) {
                if (item.getItemName().equals("Rare Candy")) {
                    levelling.useRareCandy(pokemon);
                    return new ItemUseResult(true, pokemon.getBaseLevel(),
                            pokemon.getPokemonName() + " grew to level " + pokemon.getBaseLevel());
                }
            }

            return new ItemUseResult(false, pokemon.getBaseLevel(), "Unknown item type");
        } catch (Exception e) {
            return new ItemUseResult(false, pokemon.getBaseLevel(), e.getMessage());
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

    /**
     * Gets a Pokemon's moves in a formatted way for GUI display
     */
    public List<String> getPokemonMoveDetails(Pokemon pokemon) {
        List<String> moveDetails = new ArrayList<>();
        for (Move move : pokemon.getMoveSet()) {
            moveDetails.add(String.format("%s (%s)",
                    move.getMoveName(),
                    move.getMoveClassification()));
        }
        return moveDetails;
    }

    /**
     * Gets a formatted list of Pokemon for display
     */
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

    /**
     * Gets a summary of a trainer's Pokemon management state
     *
     * @return TrainerPokemonSummary containing lineup and storage details
     */
    public TrainerPokemonSummary getTrainerPokemonSummary(Trainer trainer) {
        if (trainer == null) {
            return new TrainerPokemonSummary(new ArrayList<>(), new ArrayList<>());
        }

        List<String> lineupDetails = getPokemonListDetails(trainer.getPokemonLineup());
        List<String> storageDetails = getPokemonListDetails(trainer.getPokemonStorage());

        return new TrainerPokemonSummary(lineupDetails, storageDetails);
    }

    public static class TrainerPokemonSummary {

        public final List<String> lineupDetails;
        public final List<String> storageDetails;

        public TrainerPokemonSummary(List<String> lineupDetails, List<String> storageDetails) {
            this.lineupDetails = lineupDetails;
            this.storageDetails = storageDetails;
        }
    }

    //Adds a Pokemon to Lineup
    /**
     * Adds a Pokemon to a trainer's team
     *
     * @return AddPokemonResult containing success status and placement location
     */
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

        // Copy moves
        for (Move move : originalPokemon.getMoveSet()) {
            copyPokemon.getMoveSet().add(move);
        }

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

    // Pokemon display functionality has been moved to PokemonView
    //Switches pokemon
    /**
     * Switch a Pokemon between lineup and storage
     *
     * @return SwitchResult containing success status and move details
     */
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

    /**
     * Releases a Pokemon from either lineup or storage
     *
     * @return The released Pokemon if successful, null if not
     */
    public Pokemon releasePokemon(Trainer trainer, Pokemon pokemon, boolean fromLineup) {
        if (trainer == null || pokemon == null) {
            return null;
        }

        ArrayList<Pokemon> source = fromLineup ? trainer.getPokemonLineup() : trainer.getPokemonStorage();
        if (source.contains(pokemon)) {
            source.remove(pokemon);
            return pokemon;
        }
        return null;
    }

    //Trainer teaches moves to a Pokemon
    /**
     * Attempt to teach a move to a Pokemon
     *
     * @return TeachMoveResult containing success status and details
     */
    public TeachMoveResult teachMove(Pokemon pokemon, Move newMove, Move oldMove) {
        if (pokemon == null || newMove == null) {
            return new TeachMoveResult(false, null, null, "Invalid Pokemon or move");
        }

        if (!pokemon.canLearnMove(newMove)) {
            return new TeachMoveResult(false, null, null, "Type mismatch - cannot learn move");
        }

        if (pokemon.hasMove(newMove.getMoveName())) {
            return new TeachMoveResult(false, null, null, "Already knows this move");
        }

        if (pokemon.getMoveSet().size() >= 4) {
            if (oldMove == null) {
                return new TeachMoveResult(false, null, null, "Must specify move to forget");
            }
            if (oldMove.getMoveClassification().equalsIgnoreCase("HM")) {
                return new TeachMoveResult(false, null, null, "Cannot forget HM moves");
            }
            pokemon.getMoveSet().remove(oldMove);
            pokemon.getMoveSet().add(newMove);
            return new TeachMoveResult(true, oldMove, newMove, null);
        } else {
            pokemon.getMoveSet().add(newMove);
            return new TeachMoveResult(true, null, newMove, null);
        }
    }

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

    /**
     * Adds a new trainer to the list
     *
     * @return true if successful, false if trainer is null
     */
    public boolean addTrainerToList(Trainer trainer) {
        if (trainer == null) {
            return false;
        }
        trainerList.add(trainer);
        return true;
    }

}
