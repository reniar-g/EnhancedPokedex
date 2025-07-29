package view;

import controller.TrainerController;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import model.Item;
import model.Trainer;
import util.GUIUtils;
import model.Pokemon;

public class ItemManagementView extends JPanel {

    private final TrainerController controller;

    public ItemManagementView(TrainerController controller) {
        this.controller = controller;
        setLayout(null);
        setOpaque(false);
    }

    // shows the buy item panel where trainer can buy items from the shop
    public void showBuyItemPanel(Trainer trainer, Runnable onHome) {
        removeAll();

        // get list of items from controller
        List<Item> itemList = controller.getItemController().getItemList();

        JPanel buyPanel = new JPanel(null);
        buyPanel.setOpaque(false);
        buyPanel.setBounds(0, 0, 901, 706);
        add(buyPanel);

        GUIUtils.addWelcomeLabel(buyPanel, "Buy Items", 35, 39, 353, 40);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        int itemNum = 1;
        for (Item i : itemList) {
            JLabel itemLabel = new JLabel(
                    "<html>"
                    + "<span style='font-size:13px;'><b>#" + itemNum + "  " + i.getItemName() + "</b> | " + i.getItemCategory() + "</span><br>"
                    + "<div style='width:250px; text-align:justify;'>Description: " + i.getItemDescription() + "</div><br>"
                    + "Effect: " + i.getItemEffect() + "<br>"
                    + "Buy Price: " + i.getItemPrice() + " | Sell Price: " + i.getItemSellPrice()
                    + "<br><br>"
                    + "</html>");
            itemLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
            listPanel.add(itemLabel);
            itemNum++;
        }

        GUIUtils.createLabeledScrollPanel(
                buyPanel,
                "<html><span style='font-size:18px;'><b>List of Items</b></span></html>",
                34, 90, 356, 30,
                45, 125, 330, 320,
                listPanel
        );

        JLabel nameLabel = new JLabel("Item Name:");
        nameLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        nameLabel.setBounds(510, 475, 100, 30);
        nameLabel.setForeground(Color.BLACK);
        buyPanel.add(nameLabel);
        JTextField nameField = new JTextField();
        nameField.setBounds(510, 500, 180, 30);
        buyPanel.add(nameField);

        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        qtyLabel.setBounds(710, 475, 100, 30);
        qtyLabel.setForeground(Color.BLACK);
        buyPanel.add(qtyLabel);
        JTextField qtyField = new JTextField("1");
        qtyField.setBounds(710, 500, 60, 30);
        buyPanel.add(qtyField);

        JButton buyButton = new JButton("Buy");
        buyButton.setFont(new Font("Consolas", Font.BOLD, 14));
        buyButton.setMargin(new Insets(0, 0, 0, 0));
        buyButton.setBounds(645, 565, 62, 43);
        buyButton.setBackground(GUIUtils.POKEDEX_GREEN);
        buyPanel.add(buyButton);

        JLabel detailsLabel = new JLabel("Enter Details:");
        detailsLabel.setFont(new Font("Consolas", Font.BOLD, 20));
        detailsLabel.setBounds(490, 442, 307, 30);
        detailsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailsLabel.setVerticalAlignment(SwingConstants.CENTER);
        detailsLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        detailsLabel.setBackground(GUIUtils.POKEDEX_GREEN);
        detailsLabel.setOpaque(true);
        buyPanel.add(detailsLabel);

        // validate and process buy action
        buyButton.addActionListener(e -> {
            String itemName = nameField.getText().trim();
            String qtyStr = qtyField.getText().trim();

            if (itemName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter an item name to buy.");
                return;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(qtyStr);
                if (quantity <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid quantity (greater than 0).");
                return;
            }

            Item found = null;
            for (Item i : itemList) {
                if (i.getItemName().equalsIgnoreCase(itemName)) {
                    found = i;
                    break;
                }
            }
            if (found == null) {
                JOptionPane.showMessageDialog(this, "Item not found.");
                return;
            }
            double unitPrice = 0;
            try {
                // since the data is naka P100 format, parse it to get the numeric value
                String priceStr = found.getItemPrice().replaceAll("[^0-9.]", "");
                unitPrice = Double.parseDouble(priceStr);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid item price.");
                return;
            }

            // check if trainer has enough money
            double totalPrice = unitPrice * quantity;
            if (trainer.getTrainerMoney() < totalPrice) {
                JOptionPane.showMessageDialog(this, "Not enough money to buy " + quantity + " " + found.getItemName() + "(s).");
                return;
            }

            // check if item is unique and already exists in inventory
            for (int i = 0; i < quantity; i++) {
                boolean added = trainer.addItem(found);
                if (!added) {
                    JOptionPane.showMessageDialog(this, "Could only add " + i + " item(s): inventory full or unique item limit reached.");
                    // deduct the money for the items that were successfully added requirement 6
                    trainer.setTrainerMoney(trainer.getTrainerMoney() - (unitPrice * i));
                    return;
                }
            }
            trainer.setTrainerMoney(trainer.getTrainerMoney() - totalPrice);
            JOptionPane.showMessageDialog(this, "Bought " + quantity + " " + found.getItemName() + "(s) for ₱" + totalPrice + ".");
            nameField.setText("");
        });

        JButton btnHome = MainPokedexView.homeButton(e -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        buyPanel.add(btnHome);

        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, e -> {
            removeAll();
            revalidate();
            repaint();
            if (onHome != null) {
                onHome.run();
            }
        });
        buyPanel.add(backBtn);

        revalidate();
        repaint();
    }

    // shows the sell item panel where trainer can sell items from their inventory
    public void showSellItemPanel(Trainer trainer, Runnable onHome) {
        removeAll();
        // get trainer's inventory
        List<Item> inv = trainer.getInventory();

        JPanel sellPanel = new JPanel(null);
        sellPanel.setOpaque(false);
        sellPanel.setBounds(0, 0, 901, 706);
        add(sellPanel);

        GUIUtils.addWelcomeLabel(sellPanel, "Sell Items", 35, 39, 353, 40);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        // new list to track shown item IDs
        List<Integer> shownIds = new java.util.ArrayList<>();
        int itemNum = 1;
        for (Item item : inv) {
            if (!shownIds.contains(item.getItemId())) {
                int qty = trainer.getItemQuantity(item);
                JLabel itemLabel = new JLabel(
                        "<html>"
                        + "<span style='font-size:13px;'><b>#" + itemNum + "  " + item.getItemName() + "</b> | " + item.getItemCategory() + " (x" + qty + ")</span><br>"
                        + "<div style='width:250px; text-align:justify;'>Description: " + item.getItemDescription() + "</div><br>"
                        + "Effect: " + item.getItemEffect() + "<br>"
                        + "Sell Price: " + item.getItemSellPrice()
                        + "<br><br>"
                        + "</html>");
                itemLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
                listPanel.add(itemLabel);
                shownIds.add(item.getItemId());
                itemNum++;
            }
        }

        GUIUtils.createLabeledScrollPanel(
                sellPanel,
                "<html><span style='font-size:18px;'><b>Trainer Inventory</b></span></html>",
                34, 90, 356, 30,
                45, 125, 330, 320,
                listPanel
        );

        JLabel detailsLabel = new JLabel("Enter Details:");
        detailsLabel.setFont(new Font("Consolas", Font.BOLD, 20));
        detailsLabel.setBounds(490, 442, 307, 30);
        detailsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailsLabel.setVerticalAlignment(SwingConstants.CENTER);
        detailsLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        detailsLabel.setBackground(GUIUtils.POKEDEX_GREEN);
        detailsLabel.setOpaque(true);
        sellPanel.add(detailsLabel);

        JLabel nameLabel = new JLabel("Item Name:");
        nameLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        nameLabel.setBounds(510, 475, 100, 30);
        nameLabel.setForeground(Color.BLACK);
        sellPanel.add(nameLabel);
        JTextField nameField = new JTextField();
        nameField.setBounds(510, 500, 180, 30);
        sellPanel.add(nameField);

        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        qtyLabel.setBounds(710, 475, 100, 30);
        qtyLabel.setForeground(Color.BLACK);
        sellPanel.add(qtyLabel);
        JTextField qtyField = new JTextField("1");
        qtyField.setBounds(710, 500, 60, 30);
        sellPanel.add(qtyField);

        JButton sellButton = new JButton("Sell");
        sellButton.setFont(new Font("Consolas", Font.BOLD, 14));
        sellButton.setMargin(new Insets(0, 0, 0, 0));
        sellButton.setBounds(645, 565, 62, 43);
        sellButton.setBackground(GUIUtils.POKEDEX_GREEN);
        sellPanel.add(sellButton);

        // validate and process sell action
        sellButton.addActionListener(e -> {
            String itemName = nameField.getText().trim();
            String qtyText = qtyField.getText().trim();

            // if item name or quantity is empty, show error popup
            if (itemName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an item name.");
                return;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(qtyText);
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Quantity must be positive.");
                    return;
                }
            } catch (NumberFormatException ex) { // if quantity is not a valid number or is negative, show error popup
                JOptionPane.showMessageDialog(this, "Invalid quantity.");
                return;
            }

            // find the item in inventory to sell
            Item itemToSell = null;
            for (Item i : inv) {
                if (i.getItemName().equalsIgnoreCase(itemName)) {
                    itemToSell = i;
                    break;
                }
            }

            if (itemToSell == null) {
                JOptionPane.showMessageDialog(this, "Item not found in inventory.");
                return;
            }

            // check if trainer has enough quantity of the item to sell
            int available = trainer.getItemQuantity(itemToSell);
            if (available < quantity) {
                JOptionPane.showMessageDialog(this,
                        "Insufficient quantity.\nYou only have " + available + " " + itemToSell.getItemName() + "(s).",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // calculate the sell value (50% of the buy price) requirement 6
            double sellValue;
            try {
                String priceStr = itemToSell.getItemPrice().replaceAll("[^0-9.]", "");
                double basePrice = Double.parseDouble(priceStr);
                sellValue = basePrice * 0.5 * quantity;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid item price format.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Sell " + quantity + " " + itemToSell.getItemName() + "(s) for ₱"
                    + String.format("%,.2f", sellValue) + " PkD?",
                    "Confirm Sale",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                for (int i = 0; i < quantity; i++) {
                    trainer.removeItem(itemToSell);
                }
                trainer.setTrainerMoney(trainer.getTrainerMoney() + sellValue);
                JOptionPane.showMessageDialog(this,
                        "Sale successful!\nNew balance: ₱"
                        + String.format("%,.2f", trainer.getTrainerMoney()) + " PkD");
                removeAll();
                revalidate();
                repaint();
                if (onHome != null) {
                    onHome.run();
                }
            }
        });

        JButton btnHome = MainPokedexView.homeButton(e -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        sellPanel.add(btnHome);

        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, e -> {
            removeAll();
            revalidate();
            repaint();
            if (onHome != null) {
                onHome.run();
            }
        });
        sellPanel.add(backBtn);

        revalidate();
        repaint();
    }

    public void showUseItemDialog(Trainer trainer, Runnable onHome) {
        removeAll();

        JPanel usePanel = new JPanel(null);
        usePanel.setOpaque(false);
        usePanel.setBounds(0, 0, 901, 706);
        add(usePanel);

        GUIUtils.addWelcomeLabel(usePanel, "Use Items", 35, 39, 353, 40);

        // Create inventory panel
        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.Y_AXIS));
        inventoryPanel.setOpaque(false);

        List<Item> inventory = trainer.getInventory();
        ButtonGroup inventoryGroup = new ButtonGroup();

        // Track shown item IDs to avoid duplicates
        List<Integer> shownIds = new ArrayList<>();
        int itemNum = 1;
        for (Item item : inventory) {
            if (!shownIds.contains(item.getItemId())) {
                int qty = trainer.getItemQuantity(item);
                JRadioButton radio = new JRadioButton(
                        "#" + itemNum + " " + item.getItemName() +
                                " (" + item.getItemCategory() + ") x" + qty
                );
                radio.setFont(new Font("Consolas", Font.PLAIN, 14));
                radio.setOpaque(false);
                radio.setActionCommand(String.valueOf(item.getItemId()));
                inventoryGroup.add(radio);
                inventoryPanel.add(radio);
                shownIds.add(item.getItemId());
                itemNum++;
            }
        }

        // Create Pokémon panel
        JPanel pokemonPanel = new JPanel();
        pokemonPanel.setLayout(new BoxLayout(pokemonPanel, BoxLayout.Y_AXIS));
        pokemonPanel.setOpaque(false);

        // Combine lineup and storage for selection
        List<Pokemon> allPokemon = new ArrayList<>();
        if (trainer.getPokemonLineup() != null) {
            allPokemon.addAll(trainer.getPokemonLineup());
        }
        if (trainer.getPokemonStorage() != null) {
            allPokemon.addAll(trainer.getPokemonStorage());
        }

        ButtonGroup pokemonGroup = new ButtonGroup();
        for (Pokemon p : allPokemon) {
            String location = trainer.getPokemonLineup() != null &&
                    trainer.getPokemonLineup().contains(p) ? "Lineup" : "Storage";
            JRadioButton radio = new JRadioButton(
                    "#" + p.getPokedexNumber() + " " + p.getPokemonName() +
                            " (Lv." + p.getBaseLevel() + ", " + location + ")"
            );
            radio.setFont(new Font("Consolas", Font.PLAIN, 14));
            radio.setOpaque(false);
            radio.setActionCommand(String.valueOf(allPokemon.indexOf(p)));
            pokemonGroup.add(radio);
            pokemonPanel.add(radio);
        }

        // Add scroll panes
        GUIUtils.createLabeledScrollPanel(
                usePanel,
                "<html><span style='font-size:18px;'><b>Inventory (" + shownIds.size() + " items)</b></span></html>",
                34, 90, 356, 30,
                45, 125, 330, 140,
                inventoryPanel
        );

        GUIUtils.createLabeledScrollPanel(
                usePanel,
                "<html><span style='font-size:18px;'><b>Your Pokémon (" + allPokemon.size() + ")</b></span></html>",
                34, 270, 356, 30,
                45, 305, 330, 135,
                pokemonPanel
        );

        // Add use button
        JButton useBtn = GUIUtils.createButton1("Use Item", 493, 345, 140, 35);
        usePanel.add(useBtn);

        // Home button
        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        usePanel.add(btnHome);

        // Back button
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> {
            removeAll();
            revalidate();
            repaint();
            if (onHome != null) {
                onHome.run();
            }
        });
        usePanel.add(backBtn);

        revalidate();
        repaint();

        useBtn.addActionListener(evt -> {
            // Get selected item and Pokémon
            ButtonModel selectedItem = inventoryGroup.getSelection();
            ButtonModel selectedPokemon = pokemonGroup.getSelection();

            if (selectedItem == null || selectedPokemon == null) {
                JOptionPane.showMessageDialog(this,
                        "Please select both an item and a Pokémon.",
                        "Selection Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int itemId = Integer.parseInt(selectedItem.getActionCommand());
            int pokemonIndex = Integer.parseInt(selectedPokemon.getActionCommand());

            // Find the actual item instance
            Item itemToUse = null;
            for (Item item : inventory) {
                if (item.getItemId() == itemId) {
                    itemToUse = item;
                    break;
                }
            }

            if (itemToUse == null) {
                JOptionPane.showMessageDialog(this,
                        "Selected item not found in inventory.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (pokemonIndex < 0 || pokemonIndex >= allPokemon.size()) {
                JOptionPane.showMessageDialog(this,
                        "Selected Pokémon not found.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Pokemon pokemon = allPokemon.get(pokemonIndex);

            // Use the item through the controller
            TrainerController.ItemUseResult result = controller.useItem(trainer, itemToUse, pokemon);

            if (result.success) {
                JOptionPane.showMessageDialog(this,
                        result.message,
                        "Item Used",
                        JOptionPane.INFORMATION_MESSAGE);

                // Refresh the view if the item was consumed
                if (!itemToUse.getItemCategory().equalsIgnoreCase("Held")) {
                    showUseItemDialog(trainer, onHome);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        result.message,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // shows the trainer's inventory where trainer can view their items
    public void showTrainerInventoryDialog(Trainer trainer, Runnable onHome) {
        removeAll();

        JPanel invPanel = new JPanel(null);
        invPanel.setOpaque(false);
        invPanel.setBounds(0, 0, 901, 706);
        add(invPanel);

        GUIUtils.addWelcomeLabel(invPanel, "Trainer Inventory", 35, 39, 353, 40);

        List<Item> inv = trainer.getInventory();
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        List<Integer> shownIds = new ArrayList<>();
        int itemNum = 1;
        for (Item item : inv) {
            if (!shownIds.contains(item.getItemId())) {
                int qty = trainer.getItemQuantity(item);
                JLabel itemLabel = new JLabel(
                        "<html>"
                        + "<span style='font-size:13px;'><b>#" + itemNum + "  " + item.getItemName() + "</b> (x" + qty + ") | " + item.getItemCategory() + "</span><br>"
                        + "<div style='width:250px; text-align:justify;'>Description: " + item.getItemDescription() + "</div><br>"
                        + "Effect: " + item.getItemEffect() + "<br>"
                        + "Buy Price: " + item.getItemPrice() + " | Sell Price: " + item.getItemSellPrice()
                        + "<br><br>"
                        + "</html>");
                itemLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
                listPanel.add(itemLabel);
                shownIds.add(item.getItemId());
                itemNum++;
            }
        }
        if (listPanel.getComponentCount() == 0) {
            JLabel emptyLabel = new JLabel("Inventory is empty.");
            emptyLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
            listPanel.add(emptyLabel);
        }
        GUIUtils.createLabeledScrollPanel(
                invPanel,
                "<html><span style='font-size:18px;'><b>Trainer Inventory</b></span></html>",
                34, 90, 356, 30,
                45, 125, 330, 320,
                listPanel
        );

        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        invPanel.add(btnHome);

        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> {
            removeAll();
            revalidate();
            repaint();
            if (onHome != null) {
                onHome.run();
            }
        });
        invPanel.add(backBtn);

        revalidate();
        repaint();
    }
}
