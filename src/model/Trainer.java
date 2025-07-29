package model;

import java.util.ArrayList;

public class Trainer {

    private final int trainerId;
    private final String trainerName;
    private final String trainerBirthdate;
    private final String trainerSex;
    private final String trainerHometown;
    private final String trainerDescription;
    private double trainerMoney = 1000000.00; // initial money for the trainer (Requirement #4)
    private final ArrayList<Item> inventory = new ArrayList<>();
    private final ArrayList<Pokemon> pokemonLineup = new ArrayList<>();
    private final ArrayList<Pokemon> pokemonStorage = new ArrayList<>();

    public Trainer(int trainerId, String trainerName, String trainerBirthdate,
            String trainerSex, String trainerHometown, String trainerDescription) {
        this.trainerId = trainerId;
        this.trainerName = trainerName;
        this.trainerBirthdate = trainerBirthdate;
        this.trainerSex = trainerSex;
        this.trainerHometown = trainerHometown;
        this.trainerDescription = trainerDescription;

    }

    public int getTrainerId() {
        return trainerId;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public String getTrainerBirthdate() {
        return trainerBirthdate;
    }

    public String getTrainerSex() {
        return trainerSex;
    }

    public String getTrainerHometown() {
        return trainerHometown;
    }

    public String getTrainerDescription() {
        return trainerDescription;
    }

    public double getTrainerMoney() {
        return trainerMoney;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public ArrayList<Pokemon> getPokemonLineup() {
        return pokemonLineup;
    }

    public ArrayList<Pokemon> getPokemonStorage() {
        return pokemonStorage;
    }

    public void setTrainerMoney(double money) {
        this.trainerMoney = money;
    }

    // adds an item to the trainer's inventory
    public boolean addItem(Item item) {
        int totalItems = getTotalItemCount();
        int uniqueItems = getUniqueItemCount();
        boolean hasItem = hasItem(item);
        // if the item is already in inventory and the quantity is less than 10,
        if (!hasItem && uniqueItems >= 10) {
            // cannot add more unique items
            return false;
        }
        if (totalItems >= 50) { // if total items are already 50
            // cannot add more total items
            return false;
        }
        inventory.add(item);
        return true;
    }

    // removes an item from the trainer's inventory
    public boolean removeItem(Item item) {
        return inventory.remove(item);
    }

    // Checks if the trainer has a specific item in their inventory
    public boolean hasItem(Item item) {
        for (Item i : inventory) {
            if (i.getItemId() == item.getItemId()) {
                return true;
            }
        }
        return false;
    }

    // gets the quantity of a specific item in the trainer's inventory
    public int getItemQuantity(Item item) {
        int count = 0;
        for (Item i : inventory) {
            if (i.getItemId() == item.getItemId()) {
                count++;
            }
        }
        return count;
    }

    // gets the quantity of a specific item by its ID
    public int getUniqueItemCount() {
        ArrayList<Integer> uniqueIds = new ArrayList<>();
        for (Item i : inventory) {
            if (!uniqueIds.contains(i.getItemId())) {
                uniqueIds.add(i.getItemId());
            }
        }
        return uniqueIds.size();
    }

    // gets the total number of items in the trainer's inventory
    public int getTotalItemCount() {
        return inventory.size();
    }

    // adds a pokemon to the trainer's lineup or storage
    public boolean addPokemonToLineup(Pokemon pokemon) {
        if (pokemonLineup.size() < 6) {
            pokemonLineup.add(pokemon);
            return true;
        } else {
            pokemonStorage.add(pokemon);
            return false;
        }
    }
}
