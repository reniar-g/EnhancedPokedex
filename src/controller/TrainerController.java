package controller;

import java.util.ArrayList;
import model.*;
import util.*;

public class TrainerController {

    private final ArrayList<Trainer> trainerList;
    private ItemController itemController;

    public TrainerController(ArrayList<Trainer> trainerList) {
        this.trainerList = trainerList;
    }

    public void setItemController(ItemController itemController) {
        this.itemController = itemController;
    }

    /**
     * Trainer Management submenu
     */
    public void trainerManagement() {
        boolean running = true;
        while (running) {
            MenuArtUtils.trainersArt();
            MenuArtUtils.printTrainerMenu();
            int choice = InputUtils.getIntInput("Enter your choice: ");
            switch (choice) {
                case 1 ->
                    addTrainer();
                case 2 ->
                    viewAllTrainers();
                case 3 ->
                    searchTrainers();
                case 4 ->
                    manageTrainer();
                case 5 ->
                    handleItemTransaction(true);  // Buy Item
                case 6 ->
                    handleItemTransaction(false); // Sell Item
                case 0 ->
                    running = false;
                default ->
                    System.out.println("\u001B[31mInvalid choice. Please try again.\u001B[0m");
            }
        }
    }

    /**
     * Adds a new trainer to the system.
     */
    private void addTrainer() {
        System.out.println("\n-- Add New Trainer --");
        int trainerId = InputUtils.getIntInput("Trainer ID: ");
        if (isTrainerIdExists(trainerId)) {
            System.out.println("Error: Trainer ID already exists.");
            return; // Exit if ID already exists
        }

        String name = InputUtils.getStringInput("Name: ");
        String birthdate = InputUtils.getStringInput("Birthdate (YYYY-MM-DD): ");
        String sex = getValidSex();
        String hometown = InputUtils.getStringInput("Hometown: ");
        String description = InputUtils.getStringInput("Description: ");

        Trainer trainer = new Trainer(trainerId, name, birthdate, sex, hometown, description);
        trainerList.add(trainer);
        System.out.println("Trainer " + name + " added successfully!");
    }

    /**
     * Displays all trainers in the system.
     */
    private void viewAllTrainers() {
        System.out.println("\n-- All Trainers --");
        Trainer.displayTrainerHeader();
        if (trainerList.isEmpty()) {
            System.out.println("No trainers in the database.");
            return; // Exit if no trainers are available
        }
        for (Trainer trainer : trainerList) {
            trainer.displayTrainer();
        }
    }

    /**
     * Searches trainers by keyword.
     */
    private void searchTrainers() {
        System.out.println("\n-- Search Trainers --");
        String keyword = InputUtils.getStringInput("Enter keyword (name/hometown/description): ").toLowerCase();
        boolean found = false;
        for (Trainer trainer : trainerList) {
            if (trainer.getTrainerName().toLowerCase().contains(keyword)
                    || trainer.getTrainerHometown().toLowerCase().contains(keyword)
                    || trainer.getTrainerDescription().toLowerCase().contains(keyword)
                    || String.valueOf(trainer.getTrainerId()).equals(keyword)) {
                trainer.displayTrainer();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No trainers found matching the search criteria.");
        }
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
     * Gets a valid sex input from the user for Trainer.
     */
    public static String getValidSex() {
        while (true) {
            String sex = InputUtils.getStringInput("Sex (M/F): ").toUpperCase();
            if (sex.equals("M") || sex.equals("F")) {
                return sex;
            }
            System.out.println("Please enter M or F only.");
        }
    }

    /**
     * Manages a selected trainer (placeholder implementation lang muna to).
     */
    private void manageTrainer() {
        System.out.println("\n-- Manage Trainer --");
        if (trainerList.isEmpty()) {
            System.out.println("No trainers available to manage.");
            return;
        }
        // List trainers
        for (int i = 0; i < trainerList.size(); i++) {
            Trainer trainer = trainerList.get(i);
            System.out.println((i + 1) + ". " + trainer.getTrainerName() + " (ID: " + trainer.getTrainerId() + ")");
        }
        int choice = InputUtils.getIntInput("Select trainer by number: ") - 1;
        if (choice < 0 || choice >= trainerList.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        Trainer selectedTrainer = trainerList.get(choice);
        System.out.println("Managing trainer: " + selectedTrainer.getTrainerName());

        // Show the trainer's items before allowing item transactions
        System.out.println("\nItems owned by " + selectedTrainer.getTrainerName() + ":");
        ArrayList<Item> inventory = selectedTrainer.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            Item.displayItemHeader();
            for (Item item : inventory) {
                item.displayItem();
            }
        }
    }

    /*
     * Handles item transactions (buying/selling) for a selected trainer.
     * This method allows the user to buy or sell items from/to the trainer's inventory.
     * If buying, it shows the available items and allows the user to select one.
     */
    public void handleItemTransaction(boolean isBuying) {
        Trainer trainer = selectTrainer();
        if (trainer == null) {
            return;
        }

        if (isBuying) { // Buying items
            itemController.viewAllItems();
            ArrayList<Item> items = itemController.getItemList();
            int itemIdx = InputUtils.getIntInput("Enter item number to buy: ") - 1;
            if (itemIdx < 0 || itemIdx >= items.size()) {
                System.out.println("Invalid item selection.");
                return;
            }
            buyItem(trainer, items.get(itemIdx));
        } else { // Selling items
            ArrayList<Item> inventory = trainer.getInventory();
            if (inventory.isEmpty()) {
                System.out.println("Inventory is empty.");
                return;
            }
            System.out.println("Items in inventory:");
            Item.displayItemHeader();
            for (int i = 0; i < inventory.size(); i++) {
                inventory.get(i).displayItem();
            }
            int itemIdx = InputUtils.getIntInput("Enter item number to sell: ") - 1;
            if (itemIdx < 0 || itemIdx >= inventory.size()) {
                System.out.println("Invalid item selection.");
                return;
            }
            sellItem(trainer, inventory.get(itemIdx));
        }
    }

    // Selects a trainer from the list for item transactions.
    private Trainer selectTrainer() {
        if (trainerList.isEmpty()) {
            System.out.println("No trainers available.");
            return null;
        }
        System.out.println("Select a trainer:");
        for (int i = 0; i < trainerList.size(); i++) {
            Trainer t = trainerList.get(i);
            System.out.println((i + 1) + ". " + t.getTrainerName() + " (ID: " + t.getTrainerId() + ")");
        }
        int idx = InputUtils.getIntInput("Enter trainer number: ") - 1;
        if (idx < 0 || idx >= trainerList.size()) {
            System.out.println("Invalid trainer selection.");
            return null;
        }
        return trainerList.get(idx);
    }

    // Handles buying and selling items for the selected trainer.
    // If buying, it checks if the trainer has enough money and adds the item to their inventory.
    private void buyItem(Trainer trainer, Item item) {
        if (trainer == null || item == null) {
            System.out.println("Invalid trainer or item.");
            return;
        }

        try {
            double price = Double.parseDouble(item.getItemPrice().replace("P", "").replace(",", ""));
            if (trainer.getTrainerMoney() >= price) {
                trainer.setTrainerMoney(trainer.getTrainerMoney() - price);
                trainer.getInventory().add(item);
                System.out.printf("%s bought %s. New money: P%.2f%n",
                        trainer.getTrainerName(), item.getItemName(), trainer.getTrainerMoney());
            } else {
                System.out.println("Not enough money to buy this item.");
            }
        } catch (NumberFormatException e) {
            System.out.println("This item cannot be bought.");
        }
    }

    // If selling, it removes the item from the inventory and adds half its value to the trainer's money.
    private void sellItem(Trainer trainer, Item item) {
        if (trainer == null || item == null) {
            System.out.println("Invalid trainer or item.");
            return;
        }

        try {
            double price = Double.parseDouble(item.getItemPrice().replace("P", "").replace(",", ""));
            double sellPrice = price * 0.5;
            if (trainer.getInventory().remove(item)) {
                trainer.setTrainerMoney(trainer.getTrainerMoney() + sellPrice);
                System.out.printf("%s sold %s. New money: P%.2f%n",
                        trainer.getTrainerName(), item.getItemName(), trainer.getTrainerMoney());
            } else {
                System.out.println("Item not found in inventory.");
            }
        } catch (NumberFormatException e) {
            System.out.println("This item cannot be sold.");
        }
    }
}
