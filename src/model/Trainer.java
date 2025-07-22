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

    public void addItem(Item item) {
        inventory.add(item);
    }

    // Removes an item from the trainer's inventory
    // Returns true if the item was successfully removed, false if it was not found
    public boolean removeItem(Item item) {
        return inventory.remove(item);
    }

    // Checks if the trainer has a specific item in their inventory
    public boolean hasItem(Item item) {
        return inventory.contains(item);
    }

    // Displays the trainer's inventory
    public void displayInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        Item.displayItemHeader();
        for (Item item : inventory) {
            item.displayItem();
        }
    }

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
}
