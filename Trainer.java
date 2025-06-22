
import java.util.ArrayList;
import java.util.List;

public class Trainer {

    private int trainerId;
    private String trainerName;
    private String trainerBirthdate;
    private String trainerSex;
    private String trainerHometown;
    private String trainerDescription;
    private double trainerMoney;
    private List<Pokemon> trainerActiveLineup;
    private List<Pokemon> trainerStorage;
    private List<Item> trainerInventory;
    private List<Integer> trainerInventoryQuantities; // Parallel list to track quantities

    public Trainer(int trainerId, String trainerName, String trainerBirthdate, String trainerSex, String trainerHometown, String trainerDescription) {
        this.trainerId = trainerId;
        this.trainerName = trainerName;
        this.trainerBirthdate = trainerBirthdate;
        this.trainerSex = trainerSex;
        this.trainerHometown = trainerHometown;
        this.trainerDescription = trainerDescription;
        this.trainerMoney = 1000000.00;
        this.trainerActiveLineup = new ArrayList<>();
        this.trainerStorage = new ArrayList<>();
        this.trainerInventory = new ArrayList<>();
        this.trainerInventoryQuantities = new ArrayList<>();
    }

    // Getters
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

    public List<Pokemon> getTrainerActiveLineup() {
        return trainerActiveLineup;
    }

    public List<Pokemon> getTrainerStorage() {
        return trainerStorage;
    }

    public List<Item> getTrainerInventory() {
        return trainerInventory;
    }

    /**
     * Buys an item and adds it to inventory if conditions are met.
     */
    public boolean buyItem(Item item) {
        // Parse price from string format (PX,XXX)
        double price = parsePrice(item.getItemPrice());
        if (price == -1) {
            System.out.println("Item is not for sale.");
            return false;
        }

        if (trainerMoney < price) {
            System.out.println("Insufficient funds.");
            return false;
        }

        // Check total item count limit (50)
        int totalItems = 0;
        for (int quantity : trainerInventoryQuantities) {
            totalItems += quantity;
        }

        if (totalItems >= 50) {
            System.out.println("Inventory full! Cannot carry more than 50 items.");
            return false;
        }

        // Check unique item limit (10)
        int itemIndex = findItemIndex(item.getItemName());
        if (itemIndex == -1) {
            if (trainerInventory.size() >= 10) {
                System.out.println("Cannot carry more than 10 unique items.");
                return false;
            }
            trainerInventory.add(item);
            trainerInventoryQuantities.add(1);
        } else {
            trainerInventoryQuantities.set(itemIndex, trainerInventoryQuantities.get(itemIndex) + 1);
        }

        trainerMoney -= price;
        System.out.println("Successfully purchased " + item.getItemName() + " for P" + price);
        return true;
    }

    /**
     * Sells an item from inventory for 50% of its price.
     */
    public boolean sellItem(String itemName) {
        int itemIndex = findItemIndex(itemName);
        if (itemIndex == -1) {
            System.out.println("Item not found in inventory.");
            return false;
        }

        Item item = trainerInventory.get(itemIndex);
        double sellPrice = parsePrice(item.getItemSellPrice());

        // Remove one quantity
        int currentQuantity = trainerInventoryQuantities.get(itemIndex);
        if (currentQuantity > 1) {
            trainerInventoryQuantities.set(itemIndex, currentQuantity - 1);
        } else {
            trainerInventory.remove(itemIndex);
            trainerInventoryQuantities.remove(itemIndex);
        }

        trainerMoney += sellPrice;
        System.out.println("Sold " + itemName + " for P" + sellPrice);
        return true;
    }

    private int findItemIndex(String itemName) {
        for (int i = 0; i < trainerInventory.size(); i++) {
            if (trainerInventory.get(i).getItemName().equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        return -1;
    }

    private double parsePrice(String priceString) {
        if (priceString.equals("Not sold")) {
            return -1;
        }

        // Remove currency symbol and commas, then parse
        String cleanPrice = priceString.replaceAll("[₱P,]", "");
        if (cleanPrice.contains("–") || cleanPrice.contains("-")) {
            // Take the lower price for buying
            if (cleanPrice.contains("–")) {
                cleanPrice = cleanPrice.split("–")[0];
            } else if (cleanPrice.contains("-")) {
                cleanPrice = cleanPrice.split("-")[0];
            }
        }

        try {
            return Double.parseDouble(cleanPrice);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String displayTrainer() {
        return "Trainer ID: " + trainerId + " | " + trainerName + " (" + trainerSex + ") from " + trainerHometown
                + " | Money: P" + String.format("%.2f", trainerMoney)
                + " | Active Pokémon: " + trainerActiveLineup.size()
                + " | Stored Pokémon: " + trainerStorage.size()
                + " | Items: " + trainerInventory.size(); //di pa tapos
    }

}
