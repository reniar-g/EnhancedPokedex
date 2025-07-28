package model;

import java.util.ArrayList;

public class Trainer {

    private int trainerId;
    private String trainerName;
    private String trainerBirthdate;
    private String trainerSex;
    private String trainerHometown;
    private String trainerDescription;
    private double trainerMoney;
    private ArrayList<Item> inventory = new ArrayList<>(); // Inventory of items owned by the trainer
    private ArrayList<Pokemon> pokemonList = new ArrayList<>(); // List of Pokémon owned by the trainer

    private ArrayList<Pokemon> pokemonLineup = new ArrayList<>();
    private ArrayList<Pokemon> pokemonStorage = new ArrayList<>();

    public Trainer(int trainerId, String trainerName, String trainerBirthdate,
            String trainerSex, String trainerHometown, String trainerDescription) {
        this.trainerId = trainerId;
        this.trainerName = trainerName;
        this.trainerBirthdate = trainerBirthdate;
        this.trainerSex = trainerSex;
        this.trainerHometown = trainerHometown;
        this.trainerDescription = trainerDescription;
        this.trainerMoney = 1000000.00; // Initial money for the trainer (Requirement #4)
    }

    // Getters for trainer attributes
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

    // Getters for Pokémon list of the trainer
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public ArrayList<Pokemon> getPokemonLineup() {
        return pokemonLineup;
    }

    public ArrayList<Pokemon> getPokemonStorage() {
        return pokemonStorage;
    }

    /**
     * Adds an item to the trainer's inventory, enforcing the 10 unique/50 total
     * item limits. Returns true if added, false if limits would be exceeded.
     */
    public boolean addItem(Item item) {
        int totalItems = getTotalItemCount();
        int uniqueItems = getUniqueItemCount();
        boolean hasItem = hasItem(item);
        if (!hasItem && uniqueItems >= 10) {
            // Cannot add more unique items
            return false;
        }
        if (totalItems >= 50) {
            // Cannot add more total items
            return false;
        }
        inventory.add(item);
        return true;
    }

    /**
     * Returns true if the trainer must discard items (over 50 total).
     */
    public boolean mustDiscardItems() {
        return getTotalItemCount() > 50;
    }

    /**
     * Discards one instance of the given item from inventory. Returns true if
     * successful.
     */
    public boolean discardItem(Item item) {
        return removeItem(item);
    }

    // Removes one instance of an item from the trainer's inventory
    // Returns true if the item was successfully removed, false if it was not found
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

    // Gets the quantity of a specific item
    public int getItemQuantity(Item item) {
        int count = 0;
        for (Item i : inventory) {
            if (i.getItemId() == item.getItemId()) {
                count++;
            }
        }
        return count;
    }

    // Gets the number of unique items
    public int getUniqueItemCount() {
        ArrayList<Integer> uniqueIds = new ArrayList<>();
        for (Item i : inventory) {
            if (!uniqueIds.contains(i.getItemId())) {
                uniqueIds.add(i.getItemId());
            }
        }
        return uniqueIds.size();
    }

    // Gets the total number of items
    public int getTotalItemCount() {
        return inventory.size();
    }

    // Displays the trainer's inventory with quantities
    public void displayInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        System.out.printf("%-6s %-16s %-17s %-8s\n", "ID", "Item Name", "Category", "Qty");
        System.out.println("----------------------------------------------------------");
        ArrayList<Integer> shownIds = new ArrayList<>();
        for (Item item : inventory) {
            if (!shownIds.contains(item.getItemId())) {
                int qty = getItemQuantity(item);
                System.out.printf("%-6d %-16s %-17s %-8d\n", item.getItemId(), item.getItemName(), item.getItemCategory(), qty);
                shownIds.add(item.getItemId());
            }
        }
    }

    public boolean addPokemonToLineup(Pokemon pokemon) {
        if (pokemonLineup.size() < 6) {
            pokemonLineup.add(pokemon);
            return true;
        } else {
            pokemonStorage.add(pokemon);
            return false;
        }
    }

    public boolean switchPokemonToLineup(int storageIndex) {
        if (pokemonLineup.size() > 6) {
            return false;
        }

        if (storageIndex >= 0 && storageIndex < pokemonStorage.size()) {
            pokemonLineup.add(pokemonStorage.remove(storageIndex));
            return true;
        }

        return false;
    }

    public Pokemon releasePokemon(boolean isLineup, int index) {
        if (isLineup) {
            if (index >= 0 && index < pokemonLineup.size()) {
                return pokemonLineup.remove(index);
            }
        } else {
            if (index >= 0 && index < pokemonStorage.size()) {
                return pokemonStorage.remove(index);
            }
        }

        return null;
    }

    //SETTERS
    public void setTrainerMoney(double money) {
        this.trainerMoney = money;
    }

    /**
     * Displays the header for Trainer information table
     */
    public static void displayTrainerHeader() {
        System.out.printf("%-10s %-20s %-12s %-6s %-21s %-30s %-12s%n",
                "ID", "Name", "Birthdate", "Sex", "Hometown", "Description", "Money");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Displays trainer details in a formatted table style using printf
     */
    public void displayTrainer() {
        System.out.printf("%-10d %-20s %-12s %-6s %-21s %-30s P%-12.2f%n",
                trainerId,
                trainerName,
                trainerBirthdate,
                trainerSex,
                trainerHometown,
                trainerDescription,
                trainerMoney);
    }

    public Item getItemByName(String itemName) {
        for (Item i : inventory) {
            if (i.getItemName().equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        return null;
    }
}
