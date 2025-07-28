package view;
import java.util.ArrayList;

import controller.TrainerController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Trainer;
import util.*;

public class TrainerView extends JPanel {

    private final TrainerController controller;
    private final List<Trainer> trainerList;

    private JPanel trainersWelcomePanel;
    private JPanel trainersMainPanel;
    private JLabel trainersWelcomeDesc;

    public TrainerView(TrainerController controller, Runnable onHome) {
        this.controller = controller;
        this.trainerList = controller.getTrainerList();
        setLayout(null);
        setOpaque(false);
        showTrainersWelcomePanel(onHome);
    }

    // Welcome panel with Add, View, Manage buttons
    private void showTrainersWelcomePanel(Runnable onHome) {
        // Remove existing panels first
        removeAll();
        
        trainersWelcomePanel = new JPanel(null);
        trainersWelcomePanel.setOpaque(false);
        trainersWelcomePanel.setBounds(0, 0, 901, 706);

        GUIUtils.addWelcomeLabel(trainersWelcomePanel, "Manage Trainers", 35, 39, 353, 40);

        trainersWelcomeDesc = new JLabel();
        trainersWelcomeDesc.setText(
                "<html><div style='text-align:justify;'>"
                + "<b>Welcome to the Trainers Management System!</b><br><br>"
                + "Here you can add, view, search, and manage trainers.<br><br>"
                + "Use the buttons to add, view, search, or manage Trainers."
                + "</div></html>"
        );
        trainersWelcomeDesc.setFont(new Font("Consolas", Font.PLAIN, 16));
        trainersWelcomeDesc.setBounds(49, 105, 334, 500);
        trainersWelcomeDesc.setVerticalAlignment(SwingConstants.TOP);
        trainersWelcomeDesc.setOpaque(false);
        trainersWelcomePanel.add(trainersWelcomeDesc);

        JButton addBtn = GUIUtils.createButton1("Add Trainer", 493, 345, 140, 35);
        trainersWelcomePanel.add(addBtn);

        JButton viewBtn = GUIUtils.createButton2("View Trainers", 640, 345, 140, 35);
        trainersWelcomePanel.add(viewBtn);

        JButton selectBtn = GUIUtils.createButton2("Select Trainers", 493, 387, 140, 35);
        trainersWelcomePanel.add(selectBtn);

        JButton btnHome = MainPokedexView.homeButton(e -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        trainersWelcomePanel.add(btnHome);

        add(trainersWelcomePanel);
        revalidate();
        repaint();

        addBtn.addActionListener(e -> showAddTrainer(onHome));
        viewBtn.addActionListener(e -> showViewAllTrainers(onHome));
        selectBtn.addActionListener(e -> showManageTrainers(onHome));
    }

    // View all trainers (list + search)
    private void showViewAllTrainers(Runnable onHome) {
        removeAll();
        
        trainersMainPanel = new JPanel(null);
        trainersMainPanel.setOpaque(false);
        trainersMainPanel.setBounds(0, 0, 901, 706);

        GUIUtils.addWelcomeLabel(trainersMainPanel, "All Trainers", 35, 39, 353, 40);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        int trainerNum = 1;
        for (Trainer t : trainerList) {
            JLabel trainerLabel = getJLabelWithPokemonAndInventory(t, trainerNum);
            listPanel.add(trainerLabel);
            trainerNum++;
        }

        GUIUtils.createLabeledScrollPanel(
                trainersMainPanel,
                "<html><span style='font-size:18px;'><b>List of Trainers</b></span></html>",
                34, 90, 356, 30,
                45, 125, 330, 320,
                listPanel
        );

        // Search field
        GUIUtils.SearchFieldComponents search = GUIUtils.createSearchField(
                trainersMainPanel,
                "Search Trainer:",
                490, 442, 307, 50,
                560, 500, 180, 30,
                645, 565, 62, 43,
                "Search"
        );

        search.button.addActionListener(e -> {
            String query = search.field.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a trainer name.");
                return;
            }
            Trainer found = null;
            for (Trainer t : trainerList) {
                if (t.getTrainerName().equalsIgnoreCase(query)) {
                    found = t;
                    break;
                }
            }
            if (found != null) {
                // Get active lineup and inventory
                StringBuilder lineup = new StringBuilder();
                if (found.getPokemonLineup() != null && !found.getPokemonLineup().isEmpty()) {
                    for (model.Pokemon p : found.getPokemonLineup()) {
                        lineup.append(p.getPokemonName()).append(", ");
                    }
                    if (lineup.length() > 2) lineup.setLength(lineup.length() - 2);
                } else {
                    lineup.append("None");
                }
                StringBuilder inventory = new StringBuilder();
                if (found.getInventory() != null && !found.getInventory().isEmpty()) {
                    for (model.Item item : found.getInventory()) {
                        inventory.append(item.getItemName()).append("<br>");
                    }
                } else {
                    inventory.append("None");
                }
                JOptionPane.showMessageDialog(this,
                        "<html><b>Trainer Searched</b><br><br>"
                        + "ID: <b>" + found.getTrainerId() + "</b><br>"
                        + "Name: " + found.getTrainerName() + "<br>"
                        + "Birthdate: " + found.getTrainerBirthdate() + "<br>"
                        + "Sex: " + found.getTrainerSex() + "<br>"
                        + "Hometown: " + found.getTrainerHometown() + "<br>"
                        + "Description: " + found.getTrainerDescription() + "<br>"
                        + "Money: " + found.getTrainerMoney() + "<br>"
                        + "<b>Active Lineup:</b> " + lineup + "<br>"
                        + "<b>Inventory:</b><br>" + inventory + "</html>"
                );
            } else {
                JOptionPane.showMessageDialog(this, "Trainer not found.");
            }
        });

        // Home button
        JButton btnHome = MainPokedexView.homeButton(e -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        trainersMainPanel.add(btnHome);

        // Back button
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, e -> showTrainersWelcomePanel(onHome));
        trainersMainPanel.add(backBtn);

        add(trainersMainPanel);
        revalidate();
        repaint();
    }

    // Label for each trainer in the list, with Pokémon and inventory
    private static JLabel getJLabelWithPokemonAndInventory(Trainer t, int trainerNum) {
        StringBuilder lineup = new StringBuilder();
        if (t.getPokemonLineup() != null && !t.getPokemonLineup().isEmpty()) {
            for (model.Pokemon p : t.getPokemonLineup()) {
                lineup.append(p.getPokemonName()).append(", ");
            }
            if (lineup.length() > 2) lineup.setLength(lineup.length() - 2);
        } else {
            lineup.append("None");
        }
        StringBuilder inventory = new StringBuilder();
        ArrayList<model.Item> invList = t.getInventory();
        ArrayList<Integer> shownIds = new ArrayList<>();
        if (invList != null && !invList.isEmpty()) {
            for (model.Item item : invList) {
                if (!shownIds.contains(item.getItemId())) {
                    int qty = t.getItemQuantity(item);
                    inventory.append(item.getItemName())
                            .append(" (x")
                            .append(qty)
                            .append(") ");
                    shownIds.add(item.getItemId());
                }
            }
        } else {
            inventory.append("None");
        }
        String trainerText = "<html>"
                + "<span style='font-size:13px;'><b>#" + trainerNum + "  " + t.getTrainerName() + "</span><br>"
                + "Birthdate: " + t.getTrainerBirthdate() + "<br>"
                + "Sex: " + t.getTrainerSex() + "<br>"
                + "Hometown: " + t.getTrainerHometown() + "<br>"
                + "<div style='width:250px; text-align:justify;'>Description: " + t.getTrainerDescription() + "</div><br>"
                + "Money: " + t.getTrainerMoney() + "<br>"
                + "<b>Active Lineup:</b> " + lineup + "<br>"
                + "<b>Inventory:</b> " + inventory
                + "<br><br>"
                + "</html>";

        JLabel trainerLabel = new JLabel(trainerText);
        trainerLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
        return trainerLabel;
    }

    // Add trainer form (step-by-step, like add moves)
    private void showAddTrainer(Runnable onHome) {
        removeAll();

        JPanel addPanel = new JPanel(null);
        addPanel.setOpaque(false);
        addPanel.setBounds(0, 0, 901, 706);

        GUIUtils.addWelcomeLabel(addPanel, "Add New Trainer", 35, 39, 353, 40);

        JLabel descLabel = new JLabel("<html><div style='width:250px; text-align:justify;'>Fill out all the fields below to register a new Trainer. Initial funds are set to <b>₱1,000,000.00 (PkD)</b> for item purchases.</div></html>");
        descLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
        descLabel.setBounds(45, 100, 340, 60);
        descLabel.setVerticalAlignment(SwingConstants.TOP);
        descLabel.setHorizontalAlignment(SwingConstants.LEFT);
        addPanel.add(descLabel);

        JLabel idLabel = new JLabel("Trainer ID:");
        idLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        idLabel.setBounds(45, 160, 120, 20);
        addPanel.add(idLabel);
        JTextField idField = new JTextField();
        idField.setBounds(145, 155, 225, 25);
        addPanel.add(idField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        nameLabel.setBounds(45, 195, 120, 20);
        addPanel.add(nameLabel);
        JTextField nameField = new JTextField();
        nameField.setBounds(145, 190, 225, 25);
        addPanel.add(nameField);

        JLabel birthLabel = new JLabel("Birthdate (YYYY-MM-DD):");
        birthLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        birthLabel.setBounds(45, 230, 200, 20);
        addPanel.add(birthLabel);
        JTextField birthField = new JTextField();
        birthField.setBounds(245, 225, 125, 25);
        addPanel.add(birthField);

        JLabel sexLabel = new JLabel("Sex (M/F):");
        sexLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        sexLabel.setBounds(45, 265, 120, 20);
        addPanel.add(sexLabel);
        JTextField sexField = new JTextField();
        sexField.setBounds(145, 260, 50, 25);
        addPanel.add(sexField);

        JLabel hometownLabel = new JLabel("Hometown:");
        hometownLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        hometownLabel.setBounds(45, 300, 120, 20);
        addPanel.add(hometownLabel);
        JTextField hometownField = new JTextField();
        hometownField.setBounds(145, 295, 225, 25);
        addPanel.add(hometownField);

        JLabel desc2Label = new JLabel("Description:");
        desc2Label.setFont(new Font("Consolas", Font.PLAIN, 15));
        desc2Label.setBounds(45, 335, 120, 20);
        addPanel.add(desc2Label);
        JTextField desc2Field = new JTextField();
        desc2Field.setBounds(145, 330, 225, 25);
        addPanel.add(desc2Field);

        JLabel moneyLabel = new JLabel("Initial Funds:");
        moneyLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        moneyLabel.setBounds(45, 370, 120, 20);
        addPanel.add(moneyLabel);
        JLabel moneyValueLabel = new JLabel("₱1,000,000.00 (PkD)");
        moneyValueLabel.setFont(new Font("Consolas", Font.BOLD, 15));
        moneyValueLabel.setBounds(170, 370, 225, 20);
        addPanel.add(moneyValueLabel);

        JButton submitBtn = GUIUtils.createNavButton("Submit", 787, 345, 67, 35, null);
        addPanel.add(submitBtn);

        submitBtn.addActionListener(e -> {
            // Validate all fields
            String idText = idField.getText().trim();
            String nameText = nameField.getText().trim();
            String birthText = birthField.getText().trim();
            String sexText = sexField.getText().trim().toUpperCase();
            String hometownText = hometownField.getText().trim();
            String descText = desc2Field.getText().trim();
            if (idText.isEmpty() || nameText.isEmpty() || birthText.isEmpty() || sexText.isEmpty() || hometownText.isEmpty() || descText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.");
                return;
            }
            int idVal;
            try {
                idVal = Integer.parseInt(idText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Trainer ID must be a number.");
                return;
            }
            // Check for duplicate ID or Name
            for (Trainer t : controller.getTrainerList()) {
                if (t.getTrainerId() == idVal) {
                    JOptionPane.showMessageDialog(this, "A trainer with this ID already exists.");
                    return;
                }
                if (t.getTrainerName().equalsIgnoreCase(nameText)) {
                    JOptionPane.showMessageDialog(this, "A trainer with this name already exists.");
                    return;
                }
            }
            if (!(sexText.equals("M") || sexText.equals("F"))) {
                JOptionPane.showMessageDialog(this, "Sex must be 'M' or 'F'.");
                return;
            }
            // Add trainer to controller (GUI)
            Trainer newTrainer = new Trainer(
                    idVal,
                    nameText,
                    birthText,
                    sexText,
                    hometownText,
                    descText
            );
            // Money is set by default in Trainer constructor
            controller.getTrainerList().add(newTrainer);
            JOptionPane.showMessageDialog(this, "Trainer added! Initial funds: ₱1,000,000.00 (PkD)");
            showTrainersWelcomePanel(onHome);
        });

        // Home button
        JButton btnHome = MainPokedexView.homeButton(e -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        addPanel.add(btnHome);

        // Back button
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, e -> {
            showTrainersWelcomePanel(onHome);
        });
        addPanel.add(backBtn);

        add(addPanel);
        revalidate();
        repaint();
    }

    // Manage trainers panel with Buy, Sell, Manage Pokémons buttons
    private void showManageTrainers(Runnable onHome) {
        removeAll();

        trainersMainPanel = new JPanel(null);
        trainersMainPanel.setOpaque(false);
        trainersMainPanel.setBounds(0, 0, 901, 706);

        GUIUtils.addWelcomeLabel(trainersMainPanel, "Select Trainer", 35, 39, 353, 40);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        ButtonGroup group = new ButtonGroup();
        int trainerNum = 1;
        for (Trainer t : trainerList) {
            JRadioButton radio = new JRadioButton("#" + trainerNum + " - " + t.getTrainerName() + " (ID: " + t.getTrainerId() + ")");
            radio.setFont(new Font("Consolas", Font.PLAIN, 17));
            radio.setOpaque(false);
            radio.setActionCommand(String.valueOf(trainerNum - 1));
            group.add(radio);
            listPanel.add(radio);
            trainerNum++;
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBounds(45, 105, 330, 320);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setOpaque(false);
        trainersMainPanel.add(scrollPane);

        JButton selectBtn = GUIUtils.createButton1("Select", 493, 345, 140, 35);
        trainersMainPanel.add(selectBtn);

        // Home button
        JButton btnHome = MainPokedexView.homeButton(e -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        trainersMainPanel.add(btnHome);

        // Back button
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, e -> {
            showTrainersWelcomePanel(onHome);
        });
        trainersMainPanel.add(backBtn);

        add(trainersMainPanel);
        revalidate();
        repaint();

        selectBtn.addActionListener(e -> {
            ButtonModel selected = group.getSelection();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Please select a trainer.");
                return;
            }
            int idx = Integer.parseInt(selected.getActionCommand());
            Trainer selectedTrainer = trainerList.get(idx);
            showTrainerActionMenu(selectedTrainer, onHome);
        });
    }

    // After selecting a trainer, show manage items or pokemons
    private void showTrainerActionMenu(Trainer trainer, Runnable onHome) {
        removeAll();

        JPanel actionPanel = new JPanel(null);
        actionPanel.setOpaque(false);
        actionPanel.setBounds(0, 0, 901, 706);

        GUIUtils.addWelcomeLabel(actionPanel, "Trainer Action Menu", 35, 39, 353, 40);

        JLabel greetLabel = new JLabel("<html><div style='text-align:justify;'>How do you want to manage <b>Trainer " 
         + trainer.getTrainerName() + "?</b></div></html>");
        greetLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
        greetLabel.setBounds(49, 105, 334, 500);
        greetLabel.setHorizontalAlignment(SwingConstants.LEFT);
        greetLabel.setVerticalAlignment(SwingConstants.TOP);
        actionPanel.add(greetLabel);

        JButton manageItemsBtn = GUIUtils.createButton1("Manage Items", 493, 345, 140, 35);
        actionPanel.add(manageItemsBtn);

        JButton managePokeBtn = GUIUtils.createButton2("Manage Pokémon", 640, 345, 140, 35);
        actionPanel.add(managePokeBtn);

        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, e -> {
            showManageTrainers(onHome);
        });
        actionPanel.add(backBtn);

        JButton btnHome = MainPokedexView.homeButton(e -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        actionPanel.add(btnHome);

        add(actionPanel);
        revalidate();
        repaint();

        manageItemsBtn.addActionListener(e -> showManageItems(trainer, onHome));
        managePokeBtn.addActionListener(e -> showManagePokemons(trainer, onHome));
    }

    // Manage Items: Buy, Sell, Use, View Inventory
    private void showManageItems(Trainer trainer, Runnable onHome) {
        removeAll();

        JPanel mainPanel = new JPanel(null);
        mainPanel.setOpaque(false);
        mainPanel.setBounds(0, 0, 901, 706);

        GUIUtils.addWelcomeLabel(mainPanel, "Manage Items", 35, 39, 353, 40);

        // Buttons for actions
        JButton buyBtn = GUIUtils.createButton1("Buy Item", 493, 345, 140, 35);
        JButton sellBtn = GUIUtils.createButton2("Sell Item", 640, 345, 140, 35);
        JButton useBtn = GUIUtils.createButton2("Use Item", 493, 387, 140, 35);
        JButton viewInvBtn = GUIUtils.createButton2("View Inventory", 640, 387, 140, 35);
        mainPanel.add(buyBtn);
        mainPanel.add(sellBtn);
        mainPanel.add(useBtn);
        mainPanel.add(viewInvBtn);

        // Home button
        JButton btnHome = MainPokedexView.homeButton(e -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        mainPanel.add(btnHome);

        // Back button
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, e -> {
            showTrainerActionMenu(trainer, onHome);
        });
        mainPanel.add(backBtn);

        add(mainPanel);
        revalidate();
        repaint();

        // --- Action handlers ---
        buyBtn.addActionListener(e -> showBuyItemPanel(trainer, onHome));
        sellBtn.addActionListener(e -> showSellItemPanel(trainer, onHome));
        useBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Use Item: Not yet implemented."));
        viewInvBtn.addActionListener(e -> showTrainerInventoryDialog(trainer));
    }

    // Buy Item panel (modern layout)
    private void showBuyItemPanel(Trainer trainer, Runnable onHome) {
        removeAll();
        JPanel buyPanel = new JPanel(null);
        buyPanel.setOpaque(false);
        buyPanel.setBounds(0, 0, 901, 706);
        GUIUtils.addWelcomeLabel(buyPanel, "Buy Items", 35, 39, 353, 40);
        java.util.List<model.Item> itemList = controller.getItemController().getItemList();
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);
        int itemNum = 1;
        for (model.Item i : itemList) {
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
        GUIUtils.SearchFieldComponents buy = GUIUtils.createSearchField(
            buyPanel,
            "Buy Item:",
            490, 442, 307, 50,
            560, 500, 180, 30,
            645, 565, 62, 43,
            "Buy"
        );
        buy.button.addActionListener(e -> {
            String query = buy.field.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter an item name to buy.");
                return;
            }
            model.Item found = null;
            for (model.Item i : itemList) {
                if (i.getItemName().equalsIgnoreCase(query)) {
                    found = i;
                    break;
                }
            }
            if (found == null) {
                JOptionPane.showMessageDialog(this, "Item not found.");
                return;
            }
            double price = 0;
            try {
                // Remove all non-digit characters (except dot for decimals)
                String priceStr = found.getItemPrice().replaceAll("[^0-9.]", "");
                price = Double.parseDouble(priceStr);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid item price.");
                return;
            }
            if (trainer.getTrainerMoney() < price) {
                JOptionPane.showMessageDialog(this, "Not enough money.");
                return;
            }
            boolean added = trainer.addItem(found);
            if (!added) {
                JOptionPane.showMessageDialog(this, "Cannot add item: inventory full or unique item limit reached.");
                return;
            }
            trainer.setTrainerMoney(trainer.getTrainerMoney() - price);
            JOptionPane.showMessageDialog(this, "Bought " + found.getItemName() + " for ₱" + price + ".");
            buy.field.setText("");
        });
        JButton btnHome = MainPokedexView.homeButton(ev -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        buyPanel.add(btnHome);
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, ev -> {
            showManageItems(trainer, onHome);
        });
        buyPanel.add(backBtn);
        add(buyPanel);
        revalidate();
        repaint();
    }

    // Sell Item panel (modern layout)
    private void showSellItemPanel(Trainer trainer, Runnable onHome) {
        removeAll();
        JPanel sellPanel = new JPanel(null);
        sellPanel.setOpaque(false);
        sellPanel.setBounds(0, 0, 901, 706);
        GUIUtils.addWelcomeLabel(sellPanel, "Sell Items", 35, 39, 353, 40);
        java.util.List<model.Item> inv = trainer.getInventory();
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);
        java.util.List<Integer> shownIds = new java.util.ArrayList<>();
        int itemNum = 1;
        for (model.Item item : inv) {
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
                itemLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
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
        GUIUtils.SearchFieldComponents sell = GUIUtils.createSearchField(
            sellPanel,
            "Sell Item:",
            490, 442, 307, 50,
            560, 500, 180, 30,
            645, 565, 62, 43,
            "Sell"
        );
        sell.button.addActionListener(e -> {
            String query = sell.field.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter an item name to sell.");
                return;
            }
            model.Item found = null;
            for (model.Item item : inv) {
                if (item.getItemName().equalsIgnoreCase(query)) {
                    found = item;
                    break;
                }
            }
            if (found == null) {
                JOptionPane.showMessageDialog(this, "Item not found in inventory.");
                return;
            }
            double price = 0;
            try {
                // Remove all non-digit characters (except dot for decimals)
                String priceStr = found.getItemPrice().replaceAll("[^0-9.]", "");
                price = Double.parseDouble(priceStr);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid item price.");
                return;
            }
            boolean removed = trainer.removeItem(found);
            if (!removed) {
                JOptionPane.showMessageDialog(this, "Could not remove item.");
                return;
            }
            double gain = price * 0.5;
            trainer.setTrainerMoney(trainer.getTrainerMoney() + gain);
            JOptionPane.showMessageDialog(this, "Sold " + found.getItemName() + " for ₱" + gain + ".");
            sell.field.setText("");
        });
        JButton btnHome = MainPokedexView.homeButton(ev -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        sellPanel.add(btnHome);
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, ev -> {
            showManageItems(trainer, onHome);
        });
        sellPanel.add(backBtn);
        add(sellPanel);
        revalidate();
        repaint();
    }

    // View Inventory dialog
    private void showTrainerInventoryDialog(Trainer trainer) {
        // Show inventory in a full panel, similar to buy/sell
        removeAll();
        JPanel invPanel = new JPanel(null);
        invPanel.setOpaque(false);
        invPanel.setBounds(0, 0, 901, 706);
        GUIUtils.addWelcomeLabel(invPanel, "Trainer Inventory", 35, 39, 353, 40);
        java.util.List<model.Item> inv = trainer.getInventory();
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);
        java.util.List<Integer> shownIds = new java.util.ArrayList<>();
        int itemNum = 1;
        for (model.Item item : inv) {
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
        JButton btnHome = MainPokedexView.homeButton(ev -> {
            if (trainer != null) showTrainersWelcomePanel(null);
        });
        invPanel.add(btnHome);
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, ev -> {
            showManageItems(trainer, null);
        });
        invPanel.add(backBtn);
        add(invPanel);
        revalidate();
        repaint();
    }

    // Manage Pokémons: Teach Move, View Lineup, View Storage
    private void showManagePokemons(Trainer trainer, Runnable onHome) {
        removeAll();

        JPanel pokePanel = new JPanel(null);
        pokePanel.setOpaque(false);
        pokePanel.setBounds(0, 0, 901, 706);

        GUIUtils.addWelcomeLabel(pokePanel, "Manage Pokémon", 35, 39, 353, 40);

        JLabel greetLabel = new JLabel("Pokémon Management for " + trainer.getTrainerName());
        greetLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        greetLabel.setBounds(49, 105, 334, 500);
        greetLabel.setHorizontalAlignment(SwingConstants.LEFT);
        greetLabel.setVerticalAlignment(SwingConstants.TOP);
        pokePanel.add(greetLabel);

        JButton teachMoveBtn = GUIUtils.createButton1("Teach Move", 493, 345, 140, 35);
        JButton viewLineupBtn = GUIUtils.createButton2("View Lineup", 640, 345, 140, 35);
        JButton viewStorageBtn = GUIUtils.createButton2("View Storage", 493, 387, 140, 35);

        pokePanel.add(teachMoveBtn);
        pokePanel.add(viewLineupBtn);
        pokePanel.add(viewStorageBtn);

        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, e -> {
            showTrainerActionMenu(trainer, onHome);
        });
        pokePanel.add(backBtn);

        JButton btnHome = MainPokedexView.homeButton(e -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        pokePanel.add(btnHome);

        add(pokePanel);
        revalidate();
        repaint();

        // Action handlers for Pokemon management
        teachMoveBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Teach Move: Not yet implemented."));
        viewLineupBtn.addActionListener(e -> showPokemonLineupDialog(trainer));
        viewStorageBtn.addActionListener(e -> showPokemonStorageDialog(trainer));
    }

    // Show Pokemon lineup dialog
    private void showPokemonLineupDialog(Trainer trainer) {
        StringBuilder sb = new StringBuilder();
        java.util.List<model.Pokemon> lineup = trainer.getPokemonLineup();
        if (lineup.isEmpty()) {
            sb.append("No Pokémon in lineup.");
        } else {
            sb.append("Active Lineup (").append(lineup.size()).append("/6):\n\n");
            int num = 1;
            for (model.Pokemon p : lineup) {
                sb.append(num).append(". ").append(p.getPokemonName())
                  .append(" (Level ").append(p.getBaseLevel()).append(")\n")
                  .append("   Type: ").append(p.getPokemonType1());
                if (p.getPokemonType2() != null && !p.getPokemonType2().isEmpty()) {
                    sb.append("/").append(p.getPokemonType2());
                }
                sb.append("\n   HP: ").append((int)p.getHp())
                  .append(" | ATK: ").append((int)p.getAttack())
                  .append(" | DEF: ").append((int)p.getDefense())
                  .append(" | SPD: ").append((int)p.getSpeed()).append("\n\n");
                num++;
            }
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Pokémon Lineup", JOptionPane.INFORMATION_MESSAGE);
    }

    // Show Pokemon storage dialog
    private void showPokemonStorageDialog(Trainer trainer) {
        StringBuilder sb = new StringBuilder();
        java.util.List<model.Pokemon> storage = trainer.getPokemonStorage();
        if (storage.isEmpty()) {
            sb.append("No Pokémon in storage.");
        } else {
            sb.append("Storage (").append(storage.size()).append("/20):\n\n");
            int num = 1;
            for (model.Pokemon p : storage) {
                sb.append(num).append(". ").append(p.getPokemonName())
                  .append(" (Level ").append(p.getBaseLevel()).append(")\n")
                  .append("   Type: ").append(p.getPokemonType1());
                if (p.getPokemonType2() != null && !p.getPokemonType2().isEmpty()) {
                    sb.append("/").append(p.getPokemonType2());
                }
                sb.append("\n   HP: ").append((int)p.getHp())
                  .append(" | ATK: ").append((int)p.getAttack())
                  .append(" | DEF: ").append((int)p.getDefense())
                  .append(" | SPD: ").append((int)p.getSpeed()).append("\n\n");
                num++;
            }
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Pokémon Storage", JOptionPane.INFORMATION_MESSAGE);
    }
}