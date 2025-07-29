package view;

import controller.TrainerController;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.*;
import model.Move;
import model.Pokemon;
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

        addBtn.addActionListener(evt -> showAddTrainer(onHome));
        viewBtn.addActionListener(evt -> showViewAllTrainers(onHome));
        selectBtn.addActionListener(evt -> showManageTrainers(onHome));
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

        search.button.addActionListener(evt -> {
            String query = search.field.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a trainer name.");
                return;
            }
            Trainer found = null;
            int foundNum = 1;
            for (Trainer t : trainerList) {
                if (t.getTrainerName().equalsIgnoreCase(query)) {
                    found = t;
                    break;
                }
                foundNum++;
            }
            if (found != null) {
                JLabel trainerLabel = getJLabelWithPokemonAndInventory(found, foundNum);
                JOptionPane.showMessageDialog(this, trainerLabel, "Trainer Searched", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Trainer not found.");
            }
        });

        // Home button
        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        trainersMainPanel.add(btnHome);

        // Back button
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> showTrainersWelcomePanel(onHome));
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
            if (lineup.length() > 2) {
                lineup.setLength(lineup.length() - 2);
            }
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
                + "<b>Active Lineup:</b> <div style='width:250px; text-align:justify; display:inline;'>" + lineup + "</div><br>"
                + "<b>Inventory:</b> <div style='width:250px; text-align:justify; display:inline;'>" + inventory + "</div>"
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

        submitBtn.addActionListener(evt -> {
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
        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        addPanel.add(btnHome);

        // Back button
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> {
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
        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        trainersMainPanel.add(btnHome);

        // Back button
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> {
            showTrainersWelcomePanel(onHome);
        });
        trainersMainPanel.add(backBtn);

        add(trainersMainPanel);
        revalidate();
        repaint();

        selectBtn.addActionListener(evt -> {
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

        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> {
            showManageTrainers(onHome);
        });
        actionPanel.add(backBtn);

        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        actionPanel.add(btnHome);

        add(actionPanel);
        revalidate();
        repaint();

        manageItemsBtn.addActionListener(evt -> showManageItems(trainer, onHome));
        managePokeBtn.addActionListener(evt -> showManagePokemons(trainer, onHome));
    }

    // Manage Items: Buy, Sell, Use, View Inventory
    private void showManageItems(Trainer trainer, Runnable onHome) {
        removeAll();

        JPanel mainPanel = new JPanel(null);
        mainPanel.setOpaque(false);
        mainPanel.setBounds(0, 0, 901, 706);

        GUIUtils.addWelcomeLabel(mainPanel, "Manage Items", 35, 39, 353, 40);
        // Description label
        JLabel descLabel = new JLabel("<html><div style='width:320px; text-align:justify;'>"
                + "<b>Welcome to the Item Management Panel!</b><br><br>"
                + "Here you can buy, sell, use, or view your trainer's items."
                + "<br><br>Select an action below to manage your inventory." + "</div></html>");
        descLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        descLabel.setBounds(45, 100, 330, 100);
        descLabel.setVerticalAlignment(SwingConstants.TOP);
        descLabel.setHorizontalAlignment(SwingConstants.LEFT);
        mainPanel.add(descLabel);

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
        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        mainPanel.add(btnHome);

        // Back button
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> {
            showTrainerActionMenu(trainer, onHome);
        });
        mainPanel.add(backBtn);

        add(mainPanel);
        revalidate();
        repaint();

        // --- Action handlers ---
        buyBtn.addActionListener(evt -> showBuyItemPanel(trainer, onHome));
        sellBtn.addActionListener(evt -> showSellItemPanel(trainer, onHome));
        useBtn.addActionListener(evt -> JOptionPane.showMessageDialog(this, "Use Item: Not yet implemented."));
        viewInvBtn.addActionListener(evt -> showTrainerInventoryDialog(trainer, onHome));
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
        // Item name input
        JLabel nameLabel = new JLabel("Item Name:");
        nameLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        nameLabel.setBounds(510, 475, 100, 30);
        nameLabel.setForeground(Color.BLACK);
        buyPanel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(510, 500, 180, 30);
        buyPanel.add(nameField);

        // Quantity input
        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        qtyLabel.setBounds(710, 475, 100, 30);
        qtyLabel.setForeground(Color.BLACK);
        buyPanel.add(qtyLabel);

        JTextField qtyField = new JTextField("1");
        qtyField.setBounds(710, 500, 60, 30);
        buyPanel.add(qtyField);

        // Buy button
        JButton buyButton = new JButton("Buy");
        buyButton.setFont(new Font("Consolas", Font.BOLD, 14));
        buyButton.setMargin(new Insets(0, 0, 0, 0));
        buyButton.setBounds(645, 565, 62, 43);
        buyButton.setBackground(GUIUtils.POKEDEX_GREEN);
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

            model.Item found = null;
            for (model.Item i : itemList) {
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
                // Remove all non-digit characters (except dot for decimals)
                String priceStr = found.getItemPrice().replaceAll("[^0-9.]", "");
                unitPrice = Double.parseDouble(priceStr);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid item price.");
                return;
            }

            double totalPrice = unitPrice * quantity;
            if (trainer.getTrainerMoney() < totalPrice) {
                JOptionPane.showMessageDialog(this, "Not enough money to buy " + quantity + " " + found.getItemName() + "(s).");
                return;
            }

            for (int i = 0; i < quantity; i++) {
                boolean added = trainer.addItem(found);
                if (!added) {
                    JOptionPane.showMessageDialog(this, "Could only add " + i + " item(s): inventory full or unique item limit reached.");
                    // Refund any partially added items
                    trainer.setTrainerMoney(trainer.getTrainerMoney() - (unitPrice * i));
                    return;
                }
            }

            trainer.setTrainerMoney(trainer.getTrainerMoney() - totalPrice);
            JOptionPane.showMessageDialog(this, "Bought " + quantity + " " + found.getItemName() + "(s) for ₱" + totalPrice + ".");
            nameField.setText("");
        });
        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        buyPanel.add(btnHome);
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> {
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

        JLabel detailsLabel = new JLabel("Enter Details:");
        detailsLabel.setFont(new Font("Consolas", Font.BOLD, 20));
        detailsLabel.setBounds(490, 442, 307, 30);
        detailsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailsLabel.setVerticalAlignment(SwingConstants.CENTER);
        detailsLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        detailsLabel.setBackground(GUIUtils.POKEDEX_GREEN);
        detailsLabel.setOpaque(true);
        sellPanel.add(detailsLabel);

        // Item name input
        JLabel nameLabel = new JLabel("Item Name:");
        nameLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        nameLabel.setBounds(510, 475, 100, 30);
        nameLabel.setForeground(Color.BLACK);
        sellPanel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(510, 500, 180, 30);
        sellPanel.add(nameField);

        // Quantity input
        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        qtyLabel.setBounds(710, 475, 100, 30);
        qtyLabel.setForeground(Color.BLACK);
        sellPanel.add(qtyLabel);

        JTextField qtyField = new JTextField("1");
        qtyField.setBounds(710, 500, 60, 30);
        sellPanel.add(qtyField);

        // Sell button
        JButton sellButton = new JButton("Sell");
        sellButton.setFont(new Font("Consolas", Font.BOLD, 14));
        sellButton.setMargin(new Insets(0, 0, 0, 0));
        sellButton.setBounds(645, 565, 62, 43);
        sellButton.setBackground(GUIUtils.POKEDEX_GREEN);
        sellPanel.add(sellButton);

        sellButton.addActionListener(e -> {
            String itemName = nameField.getText().trim();
            String qtyStr = qtyField.getText().trim();

            if (itemName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter an item name to sell.");
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

            model.Item found = null;
            for (model.Item item : inv) {
                if (item.getItemName().equalsIgnoreCase(itemName)) {
                    found = item;
                    break;
                }
            }
            if (found == null) {
                JOptionPane.showMessageDialog(this, "Item not found in inventory.");
                return;
            }

            // Check if trainer has enough of the item
            int available = trainer.getItemQuantity(found);
            if (available < quantity) {
                JOptionPane.showMessageDialog(this, "You only have " + available + " " + found.getItemName() + "(s).");
                return;
            }

            double unitPrice = 0;
            try {
                // Remove all non-digit characters (except dot for decimals)
                String priceStr = found.getItemSellPrice().replaceAll("[^0-9.]", "");
                unitPrice = Double.parseDouble(priceStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid item sell price format.");
                return;
            }

            // Try to sell all items
            for (int i = 0; i < quantity; i++) {
                boolean removed = trainer.removeItem(found);
                if (!removed) {
                    if (i > 0) {
                        JOptionPane.showMessageDialog(this, "Could only sell " + i + " item(s).");
                        // Add money for partial sale
                        trainer.setTrainerMoney(trainer.getTrainerMoney() + (unitPrice * i));
                    } else {
                        JOptionPane.showMessageDialog(this, "Could not sell item.");
                    }
                    return;
                }
            }

            double totalGain = unitPrice * quantity;
            trainer.setTrainerMoney(trainer.getTrainerMoney() + totalGain);
            JOptionPane.showMessageDialog(this, "Sold " + quantity + " " + found.getItemName() + "(s) for ₱" + totalGain + ".");
            nameField.setText("");
            qtyField.setText("1");
        });
        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        sellPanel.add(btnHome);
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> {
            showManageItems(trainer, onHome);
        });
        sellPanel.add(backBtn);
        add(sellPanel);
        revalidate();
        repaint();
    }

    // View Inventory dialog
    private void showTrainerInventoryDialog(Trainer trainer, Runnable onHome) {
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
        // Use onHome callback for Home button, matching ItemView
        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) {
                onHome.run();
            } else {
                // fallback: just remove panel
                removeAll();
                revalidate();
                repaint();
            }
        });
        invPanel.add(btnHome);
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> {
            showManageItems(trainer, onHome);
        });
        invPanel.add(backBtn);
        add(invPanel);
        revalidate();
        repaint();
    }

    // Manage Pokémons: Display both lineups and allow actions
    private void showManagePokemons(Trainer trainer, Runnable onHome) {
        removeAll();

        JPanel pokePanel = new JPanel(null);
        pokePanel.setOpaque(false);
        pokePanel.setBounds(0, 0, 901, 706);

        GUIUtils.addWelcomeLabel(pokePanel, "Manage Pokémon", 35, 39, 353, 40);

        // Create lineup panel
        JPanel lineupListPanel = new JPanel();
        lineupListPanel.setLayout(new BoxLayout(lineupListPanel, BoxLayout.Y_AXIS));
        lineupListPanel.setOpaque(false);

        // Add lineup Pokémon
        List<Pokemon> lineup = trainer.getPokemonLineup();
        if (lineup.isEmpty()) {
            JLabel emptyLabel = new JLabel("No Pokémon in active lineup");
            emptyLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
            lineupListPanel.add(emptyLabel);
        } else {
            for (Pokemon p : lineup) {
                lineupListPanel.add(createPokemonDetailsLabel(p, true));
            }
        }

        // Create storage panel
        JPanel storageListPanel = new JPanel();
        storageListPanel.setLayout(new BoxLayout(storageListPanel, BoxLayout.Y_AXIS));
        storageListPanel.setOpaque(false);

        // Add storage Pokémon
        List<Pokemon> storage = trainer.getPokemonStorage();
        if (storage.isEmpty()) {
            JLabel emptyLabel = new JLabel("No Pokémon in storage");
            emptyLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
            storageListPanel.add(emptyLabel);
        } else {
            for (Pokemon p : storage) {
                storageListPanel.add(createPokemonDetailsLabel(p, false));
            }
        }

        // Add lineup scroll panel
        GUIUtils.createLabeledScrollPanel(
                pokePanel,
                "<html><span style='font-size:18px;'><b>Active Lineup (" + lineup.size() + "/6)</b></span></html>",
                34, 90, 356, 30,
                45, 125, 330, 140,
                lineupListPanel
        );

        // Add storage scroll panel
        GUIUtils.createLabeledScrollPanel(
                pokePanel,
                "<html><span style='font-size:18px;'><b>Storage (" + storage.size() + "/20)</b></span></html>",
                34, 270, 356, 30,
                45, 305, 330, 135,
                storageListPanel
        );

        // Action buttons
        JButton addPokemonBtn = GUIUtils.createButton1("Add Pokémon", 493, 345, 140, 35);
        JButton switchBtn = GUIUtils.createButton2("Switch to Storage", 640, 345, 140, 35);
        JButton releaseBtn = GUIUtils.createButton2("Release Pokémon", 493, 387, 140, 35);
        JButton teachMoveBtn = GUIUtils.createButton2("Teach Moves", 640, 387, 140, 35);

        pokePanel.add(addPokemonBtn);
        pokePanel.add(switchBtn);
        pokePanel.add(releaseBtn);
        pokePanel.add(teachMoveBtn);

        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> {
            showTrainerActionMenu(trainer, onHome);
        });
        pokePanel.add(backBtn);

        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        pokePanel.add(btnHome);

        add(pokePanel);
        revalidate();
        repaint();

        // Action handlers for Pokémon management
        addPokemonBtn.addActionListener(evt -> showAddPokemonDialog(trainer, onHome));
        switchBtn.addActionListener(evt -> showSwitchPokemonDialog(trainer, onHome));
        releaseBtn.addActionListener(evt -> showReleasePokemonDialog(trainer, onHome));
        teachMoveBtn.addActionListener(evt -> showTeachMoveDialog(trainer, onHome));
    }

    // Show Pokemon lineup dialog
    private void showPokemonLineupDialog(Trainer trainer) {
        showManagePokemons(trainer);
    }

    private void showManagePokemons(Trainer trainer) {
        java.util.List<model.Pokemon> lineup = controller.getActiveLineup(trainer);
        java.util.List<model.Pokemon> storage = trainer.getPokemonStorage();

        // Create main panel with BoxLayout vertically
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Create lineup panel
        JPanel lineupPanel = new JPanel();
        lineupPanel.setBorder(BorderFactory.createTitledBorder("Active Lineup (" + lineup.size() + "/6)"));
        lineupPanel.setLayout(new BoxLayout(lineupPanel, BoxLayout.Y_AXIS));

        // Add lineup Pokemon
        for (Pokemon pokemon : lineup) {
            JLabel pokemonLabel = createPokemonDetailsLabel(pokemon, true);
            JPanel pokemonPanel = new JPanel(new BorderLayout());
            pokemonPanel.add(pokemonLabel, BorderLayout.CENTER);
            lineupPanel.add(pokemonPanel);
            lineupPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        // Create lineup scroll pane
        JScrollPane lineupScroll = new JScrollPane(lineupPanel);
        lineupScroll.setPreferredSize(new Dimension(400, 200));

        // Create storage panel
        JPanel storagePanel = new JPanel();
        storagePanel.setBorder(BorderFactory.createTitledBorder("Storage (" + storage.size() + "/20)"));
        storagePanel.setLayout(new BoxLayout(storagePanel, BoxLayout.Y_AXIS));

        // Add storage Pokemon
        for (Pokemon pokemon : storage) {
            JLabel pokemonLabel = createPokemonDetailsLabel(pokemon, false);
            JPanel pokemonPanel = new JPanel(new BorderLayout());
            pokemonPanel.add(pokemonLabel, BorderLayout.CENTER);
            storagePanel.add(pokemonPanel);
            storagePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        // Create storage scroll pane
        JScrollPane storageScroll = new JScrollPane(storagePanel);
        storageScroll.setPreferredSize(new Dimension(400, 200));

        // Add buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton switchButton = new JButton("Switch Pokemon");
        JButton moveButton = new JButton("Manage Moves");
        JButton closeButton = new JButton("Close");

        switchButton.addActionListener(e -> {
            // Will be implemented in the next update
            JOptionPane.showMessageDialog(mainPanel,
                    "Select Pokemon from lineup and storage to switch positions.",
                    "Switch Pokemon",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        moveButton.addActionListener(e -> {
            // Will be implemented in the next update
            JOptionPane.showMessageDialog(mainPanel,
                    "Select a Pokemon to manage its moves.",
                    "Manage Moves",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        closeButton.addActionListener(e -> {
            Window win = SwingUtilities.getWindowAncestor(mainPanel);
            win.dispose();
        });

        buttonsPanel.add(switchButton);
        buttonsPanel.add(moveButton);
        buttonsPanel.add(closeButton);

        // Add components to main panel
        mainPanel.add(lineupScroll);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
        mainPanel.add(storageScroll);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
        mainPanel.add(buttonsPanel);

        // Show dialog
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Manage Pokemon", true);
        dialog.setContentPane(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showAddPokemonDialog(Trainer trainer, Runnable onHome) {
        removeAll();

        JPanel addPanel = new JPanel(null);
        addPanel.setOpaque(false);
        addPanel.setBounds(0, 0, 901, 706);

        GUIUtils.addWelcomeLabel(addPanel, "Add Pokémon to Trainer", 35, 39, 353, 40);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        // Get all Pokémon from the Pokédex
        List<Pokemon> allPokemon = controller.getPokemonController().getPokedex();

        ButtonGroup group = new ButtonGroup();
        for (Pokemon pokemon : allPokemon) {
            JRadioButton radio = new JRadioButton(
                    "#" + pokemon.getPokedexNumber() + " - " + pokemon.getPokemonName()
                    + " (" + pokemon.getPokemonType1()
                    + (pokemon.getPokemonType2() != null && !pokemon.getPokemonType2().isEmpty()
                    ? "/" + pokemon.getPokemonType2() : "") + ")"
            );
            radio.setFont(new Font("Consolas", Font.PLAIN, 14));
            radio.setOpaque(false);
            radio.setActionCommand(String.valueOf(pokemon.getPokedexNumber()));
            group.add(radio);
            listPanel.add(radio);
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBounds(45, 105, 330, 320);
        scrollPane.setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setOpaque(false);
        addPanel.add(scrollPane);

        JButton selectBtn = GUIUtils.createButton1("Add Pokémon", 493, 345, 140, 35);
        addPanel.add(selectBtn);

        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        addPanel.add(btnHome);

        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> {
            showManagePokemons(trainer, onHome);
        });
        addPanel.add(backBtn);

        add(addPanel);
        revalidate();
        repaint();

        selectBtn.addActionListener(evt -> {
            ButtonModel selected = group.getSelection();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Please select a Pokémon.");
                return;
            }

            int pokedexNumber = Integer.parseInt(selected.getActionCommand());
            Pokemon selectedPokemon = null;
            for (Pokemon p : allPokemon) {
                if (p.getPokedexNumber() == pokedexNumber) {
                    selectedPokemon = p;
                    break;
                }
            }

            if (selectedPokemon != null) {
                TrainerController.AddPokemonResult result = controller.addPokemonToTrainer(trainer, selectedPokemon);
                if (result.success) {
                    JOptionPane.showMessageDialog(this,
                            "Pokémon added to " + result.location + "!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            result.errorMessage,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                showManagePokemons(trainer, onHome);
            }
        });
    }

    private String extractTextFromLabel(JLabel label) {
        // Extract the HTML content from the label and convert it to plain text
        String htmlText = label.getText();
        // Remove HTML tags and convert to plain text
        return htmlText.replaceAll("<[^>]*>", "").replace("&nbsp;", " ").trim();
    }

    /**
     * Creates a formatted label with Pokemon details including moves
     */
    private JLabel createPokemonDetailsLabel(Pokemon p, boolean isLineup) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><div style='width:250px;'>");

        // Pokemon basic info
        sb.append("<span style='font-size:13px;'><b>")
                .append(p.getPokemonName())
                .append(" (Level ").append(p.getBaseLevel()).append(")</b></span><br>");

        // Type
        sb.append("Type: ").append(p.getPokemonType1());
        if (p.getPokemonType2() != null && !p.getPokemonType2().isEmpty()) {
            sb.append("/").append(p.getPokemonType2());
        }
        sb.append("<br>");

        // Stats
        sb.append("HP: ").append((int) p.getHp())
                .append(" | ATK: ").append((int) p.getAttack())
                .append(" | DEF: ").append((int) p.getDefense())
                .append(" | SPD: ").append((int) p.getSpeed())
                .append("<br>");

        // Location
        sb.append("<b>Location: ").append(isLineup ? "Active Lineup" : "Storage").append("</b><br>");

        // Moves
        sb.append("<b>Moves:</b><br>");
        List<Move> moves = p.getMoveSet();
        if (moves.isEmpty()) {
            sb.append("No moves learned<br>");
        } else {
            for (Move m : moves) {
                sb.append("• ").append(m.getMoveName())
                        .append(" - ")
                        .append(m.getMoveClassification())
                        .append("<br>");
            }
        }

        // Evolution info if available
        if (p.getEvolvesTo() != null && !p.getEvolvesTo().toString().isEmpty()) {
            sb.append("<br>Evolves to: ").append(p.getEvolvesTo());
            if (p.getEvolutionLevel() > 0) {
                sb.append(" at level ").append(p.getEvolutionLevel());
            }
            sb.append("<br>");
        }

        sb.append("<br></div></html>");

        JLabel label = new JLabel(sb.toString());
        label.setFont(new Font("Consolas", Font.PLAIN, 12));
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        return label;
    }

    private void showSwitchPokemonDialog(Trainer trainer, Runnable onHome) {
        removeAll();

        JPanel switchPanel = new JPanel(null);
        switchPanel.setOpaque(false);
        switchPanel.setBounds(0, 0, 901, 706);

        GUIUtils.addWelcomeLabel(switchPanel, "Switch Pokémon", 35, 39, 353, 40);

        // Create lineup panel
        JPanel lineupPanel = new JPanel();
        lineupPanel.setLayout(new BoxLayout(lineupPanel, BoxLayout.Y_AXIS));
        lineupPanel.setOpaque(false);

        List<Pokemon> lineup = trainer.getPokemonLineup();
        ButtonGroup lineupGroup = new ButtonGroup();
        for (Pokemon p : lineup) {
            String pokemonDetails = extractTextFromLabel(createPokemonDetailsLabel(p, true));
            JRadioButton radio = new JRadioButton(pokemonDetails);
            radio.setOpaque(false);
            radio.setActionCommand("lineup:" + lineup.indexOf(p));
            lineupGroup.add(radio);
            lineupPanel.add(radio);
        }

        // Create storage panel
        JPanel storagePanel = new JPanel();
        storagePanel.setLayout(new BoxLayout(storagePanel, BoxLayout.Y_AXIS));
        storagePanel.setOpaque(false);

        List<Pokemon> storage = trainer.getPokemonStorage();
        ButtonGroup storageGroup = new ButtonGroup();
        for (Pokemon p : storage) {
            String pokemonDetails = extractTextFromLabel(createPokemonDetailsLabel(p, false));
            JRadioButton radio = new JRadioButton(pokemonDetails);
            radio.setOpaque(false);
            radio.setActionCommand("storage:" + storage.indexOf(p));
            storageGroup.add(radio);
            storagePanel.add(radio);
        }

        // Add scroll panes
        GUIUtils.createLabeledScrollPanel(
                switchPanel,
                "<html><span style='font-size:18px;'><b>Active Lineup (" + lineup.size() + "/6)</b></span></html>",
                34, 90, 356, 30,
                45, 125, 330, 140,
                lineupPanel
        );

        GUIUtils.createLabeledScrollPanel(
                switchPanel,
                "<html><span style='font-size:18px;'><b>Storage (" + storage.size() + "/20)</b></span></html>",
                34, 270, 356, 30,
                45, 305, 330, 135,
                storagePanel
        );

        // Add switch button
        JButton switchBtn = GUIUtils.createButton1("Switch", 493, 345, 140, 35);
        switchPanel.add(switchBtn);

        // Add home button
        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        switchPanel.add(btnHome);

        // Add back button
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> {
            showManagePokemons(trainer, onHome);
        });
        switchPanel.add(backBtn);

        add(switchPanel);
        revalidate();
        repaint();

        switchBtn.addActionListener(evt -> {
            // Get selected Pokémon from lineup or storage
            ButtonModel lineupSelected = lineupGroup.getSelection();
            ButtonModel storageSelected = storageGroup.getSelection();

            if (lineupSelected != null && storageSelected == null) {
                // Moving from lineup to storage
                int index = Integer.parseInt(lineupSelected.getActionCommand().split(":")[1]);
                Pokemon pokemon = lineup.get(index);
                TrainerController.SwitchResult result = controller.switchPokemon(trainer, pokemon, false);
                if (result.success) {
                    JOptionPane.showMessageDialog(this, result.message);
                    showManagePokemons(trainer, onHome);
                } else {
                    JOptionPane.showMessageDialog(this, result.message, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (storageSelected != null && lineupSelected == null) {
                // Moving from storage to lineup
                int index = Integer.parseInt(storageSelected.getActionCommand().split(":")[1]);
                Pokemon pokemon = storage.get(index);
                TrainerController.SwitchResult result = controller.switchPokemon(trainer, pokemon, true);
                if (result.success) {
                    JOptionPane.showMessageDialog(this, result.message);
                    showManagePokemons(trainer, onHome);
                } else {
                    JOptionPane.showMessageDialog(this, result.message, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (lineupSelected != null && storageSelected != null) {
                // Swap between lineup and storage
                int lineupIndex = Integer.parseInt(lineupSelected.getActionCommand().split(":")[1]);
                int storageIndex = Integer.parseInt(storageSelected.getActionCommand().split(":")[1]);
                Pokemon lineupPokemon = lineup.get(lineupIndex);
                Pokemon storagePokemon = storage.get(storageIndex);

                // First move lineup pokemon to storage
                TrainerController.SwitchResult result1 = controller.switchPokemon(trainer, lineupPokemon, false);
                if (!result1.success) {
                    JOptionPane.showMessageDialog(this, result1.message, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Then move storage pokemon to lineup
                TrainerController.SwitchResult result2 = controller.switchPokemon(trainer, storagePokemon, true);
                if (!result2.success) {
                    // If failed, try to move the first pokemon back
                    controller.switchPokemon(trainer, lineupPokemon, true);
                    JOptionPane.showMessageDialog(this, result2.message, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(this, "Pokémon swapped successfully!");
                showManagePokemons(trainer, onHome);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Select Pokémon from lineup or storage to switch",
                        "Selection Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void showReleasePokemonDialog(Trainer trainer, Runnable onHome) {
        removeAll();

        JPanel releasePanel = new JPanel(null);
        releasePanel.setOpaque(false);
        releasePanel.setBounds(0, 0, 901, 706);

        GUIUtils.addWelcomeLabel(releasePanel, "Release Pokémon", 35, 39, 353, 40);

        // Create lineup panel
        JPanel lineupPanel = new JPanel();
        lineupPanel.setLayout(new BoxLayout(lineupPanel, BoxLayout.Y_AXIS));
        lineupPanel.setOpaque(false);

        List<Pokemon> lineup = trainer.getPokemonLineup();
        ButtonGroup lineupGroup = new ButtonGroup();
        for (Pokemon p : lineup) {
            String pokemonDetails = extractTextFromLabel(createPokemonDetailsLabel(p, true));
            JRadioButton radio = new JRadioButton(pokemonDetails);
            radio.setOpaque(false);
            radio.setActionCommand("lineup:" + lineup.indexOf(p));
            lineupGroup.add(radio);
            lineupPanel.add(radio);
        }

        // Create storage panel
        JPanel storagePanel = new JPanel();
        storagePanel.setLayout(new BoxLayout(storagePanel, BoxLayout.Y_AXIS));
        storagePanel.setOpaque(false);

        List<Pokemon> storage = trainer.getPokemonStorage();
        ButtonGroup storageGroup = new ButtonGroup();
        for (Pokemon p : storage) {
            String pokemonDetails = extractTextFromLabel(createPokemonDetailsLabel(p, false));
            JRadioButton radio = new JRadioButton(pokemonDetails);
            radio.setOpaque(false);
            radio.setActionCommand("storage:" + storage.indexOf(p));
            storageGroup.add(radio);
            storagePanel.add(radio);
        }

        // Add scroll panes
        GUIUtils.createLabeledScrollPanel(
                releasePanel,
                "<html><span style='font-size:18px;'><b>Active Lineup (" + lineup.size() + "/6)</b></span></html>",
                34, 90, 356, 30,
                45, 125, 330, 140,
                lineupPanel
        );

        GUIUtils.createLabeledScrollPanel(
                releasePanel,
                "<html><span style='font-size:18px;'><b>Storage (" + storage.size() + "/20)</b></span></html>",
                34, 270, 356, 30,
                45, 305, 330, 135,
                storagePanel
        );

        // Add release button
        JButton releaseBtn = GUIUtils.createButton1("Release", 493, 345, 140, 35);
        releasePanel.add(releaseBtn);

        // Add home button
        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        releasePanel.add(btnHome);

        // Add back button
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> {
            showManagePokemons(trainer, onHome);
        });
        releasePanel.add(backBtn);

        add(releasePanel);
        revalidate();
        repaint();

        releaseBtn.addActionListener(evt -> {
            // Get selected Pokémon from lineup or storage
            ButtonModel lineupSelected = lineupGroup.getSelection();
            ButtonModel storageSelected = storageGroup.getSelection();

            if (lineupSelected == null && storageSelected == null) {
                JOptionPane.showMessageDialog(this, "Please select a Pokémon to release.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to release this Pokémon?\nThis action cannot be undone.",
                    "Confirm Release",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            if (lineupSelected != null) {
                int index = Integer.parseInt(lineupSelected.getActionCommand().split(":")[1]);
                Pokemon pokemon = lineup.get(index);
                Pokemon released = controller.releasePokemon(trainer, pokemon, true);
                if (released != null) {
                    JOptionPane.showMessageDialog(this,
                            "Released " + released.getPokemonName() + " from lineup.",
                            "Pokémon Released",
                            JOptionPane.INFORMATION_MESSAGE);
                    showManagePokemons(trainer, onHome);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Failed to release Pokémon.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if (storageSelected != null) {
                int index = Integer.parseInt(storageSelected.getActionCommand().split(":")[1]);
                Pokemon pokemon = storage.get(index);
                Pokemon released = controller.releasePokemon(trainer, pokemon, false);
                if (released != null) {
                    JOptionPane.showMessageDialog(this,
                            "Released " + released.getPokemonName() + " from storage.",
                            "Pokémon Released",
                            JOptionPane.INFORMATION_MESSAGE);
                    showManagePokemons(trainer, onHome);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Failed to release Pokémon.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void showTeachMoveDialog(Trainer trainer, Runnable onHome) {
        removeAll();

        JPanel teachMovePanel = new JPanel(null);
        teachMovePanel.setOpaque(false);
        teachMovePanel.setBounds(0, 0, 901, 706);

        GUIUtils.addWelcomeLabel(teachMovePanel, "Teach Moves", 35, 39, 353, 40);

        //Select Pokémon
        JPanel selectPokemonPanel = new JPanel(null);
        selectPokemonPanel.setOpaque(false);
        selectPokemonPanel.setBounds(0, 0, 901, 706);
        teachMovePanel.add(selectPokemonPanel);

        JLabel selectPokemonLabel = new JLabel("Select Pokémon to Teach:");
        selectPokemonLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        selectPokemonLabel.setBounds(45, 80, 300, 20);
        selectPokemonPanel.add(selectPokemonLabel);

        // Combine lineup and storage for selection
        List<Pokemon> allPokemon = new ArrayList<>();
        allPokemon.addAll(trainer.getPokemonLineup());
        allPokemon.addAll(trainer.getPokemonStorage());

        JPanel pokemonListPanel = new JPanel();
        pokemonListPanel.setLayout(new BoxLayout(pokemonListPanel, BoxLayout.Y_AXIS));
        pokemonListPanel.setOpaque(false);

        ButtonGroup pokemonGroup = new ButtonGroup();
        for (Pokemon p : allPokemon) {
            String location = trainer.getPokemonLineup().contains(p) ? "Lineup" : "Storage";
            JRadioButton radio = new JRadioButton(
                    "#" + p.getPokedexNumber() + " - " + p.getPokemonName() +
                            " (Lv." + p.getBaseLevel() + ", " + location + ")"
            );
            radio.setFont(new Font("Consolas", Font.PLAIN, 14));
            radio.setOpaque(false);
            radio.setActionCommand(String.valueOf(allPokemon.indexOf(p)));
            pokemonGroup.add(radio);
            pokemonListPanel.add(radio);
        }

        JScrollPane pokemonScroll = new JScrollPane(pokemonListPanel);
        pokemonScroll.setBounds(45, 105, 330, 400);
        pokemonScroll.setOpaque(false);
        pokemonScroll.setBorder(null);
        pokemonScroll.getViewport().setOpaque(false);
        selectPokemonPanel.add(pokemonScroll);

        // Next button (only enabled when a Pokémon is selected)
        JButton nextBtn = GUIUtils.createButton1("Next", 493, 345, 140, 35);
        nextBtn.setEnabled(false);
        selectPokemonPanel.add(nextBtn);

        // Select Move (initially hidden)
        JPanel selectMovePanel = new JPanel(null);
        selectMovePanel.setOpaque(false);
        selectMovePanel.setBounds(0, 0, 901, 706);
        selectMovePanel.setVisible(false);
        teachMovePanel.add(selectMovePanel);

        JLabel selectMoveLabel = new JLabel("Select Move to Teach:");
        selectMoveLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        selectMoveLabel.setBounds(45, 80, 300, 20);
        selectMovePanel.add(selectMoveLabel);

        // List of all available moves
        List<Move> allMoves = controller.getMoveController().getMoveList();
        JPanel moveListPanel = new JPanel();
        moveListPanel.setLayout(new BoxLayout(moveListPanel, BoxLayout.Y_AXIS));
        moveListPanel.setOpaque(false);

        ButtonGroup moveGroup = new ButtonGroup();
        for (Move m : allMoves) {
            JRadioButton radio = new JRadioButton(
                    m.getMoveName() + " (" + m.getMoveClassification() + ")"
            );
            radio.setFont(new Font("Consolas", Font.PLAIN, 14));
            radio.setOpaque(false);
            radio.setActionCommand(String.valueOf(allMoves.indexOf(m)));
            moveGroup.add(radio);
            moveListPanel.add(radio);
        }

        JScrollPane moveScroll = new JScrollPane(moveListPanel);
        moveScroll.setBounds(45, 105, 330, 400);
        moveScroll.setOpaque(false);
        moveScroll.setBorder(null);
        moveScroll.getViewport().setOpaque(false);
        selectMovePanel.add(moveScroll);

        // Panel for move replacement (shown when Pokémon has 4 moves)
        JPanel replacePanel = new JPanel();
        replacePanel.setLayout(new BoxLayout(replacePanel, BoxLayout.Y_AXIS));
        replacePanel.setOpaque(false);
        replacePanel.setBounds(490, 200, 330, 200);
        replacePanel.setVisible(false);
        selectMovePanel.add(replacePanel);

        JLabel replaceLabel = new JLabel("Select Move to Replace:");
        replaceLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        replaceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        replacePanel.add(replaceLabel);

        ButtonGroup replaceGroup = new ButtonGroup();
        JRadioButton[] replaceButtons = new JRadioButton[4];
        for (int i = 0; i < 4; i++) {
            replaceButtons[i] = new JRadioButton();
            replaceButtons[i].setFont(new Font("Consolas", Font.PLAIN, 14));
            replaceButtons[i].setOpaque(false);
            replaceButtons[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            replaceGroup.add(replaceButtons[i]);
            replacePanel.add(replaceButtons[i]);
        }

        // Teach button
        JButton teachBtn = GUIUtils.createButton1("Teach Move", 493, 345, 140, 35);
        selectMovePanel.add(teachBtn);

        // Back button (to return to Pokémon selection)
        JButton backToPokemonBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> {
            selectMovePanel.setVisible(false);
            selectPokemonPanel.setVisible(true);
        });
        selectMovePanel.add(backToPokemonBtn);

        // Home button
        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) onHome.run();
        });
        teachMovePanel.add(btnHome);

        // Back button (to return to manage Pokémon screen)
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> {
            showManagePokemons(trainer, onHome);
        });
        teachMovePanel.add(backBtn);

        add(teachMovePanel);
        revalidate();
        repaint();

        // Enable Next button only when a Pokémon is selected
        for (Enumeration<AbstractButton> buttons = pokemonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            button.addItemListener(e -> {
                nextBtn.setEnabled(pokemonGroup.getSelection() != null);
            });
        }

        // Next button action - show move selection
        nextBtn.addActionListener(evt -> {
            selectPokemonPanel.setVisible(false);
            selectMovePanel.setVisible(true);
        });

        // Teach button action
        teachBtn.addActionListener(evt -> {
            // Get selected Pokémon
            ButtonModel pokemonSelected = pokemonGroup.getSelection();
            Pokemon selectedPokemon = allPokemon.get(Integer.parseInt(pokemonSelected.getActionCommand()));

            // Get selected move
            ButtonModel moveSelected = moveGroup.getSelection();
            if (moveSelected == null) {
                JOptionPane.showMessageDialog(this, "Please select a move to teach.");
                return;
            }
            Move selectedMove = allMoves.get(Integer.parseInt(moveSelected.getActionCommand()));

            // Check if Pokémon already knows the move
            if (selectedPokemon.hasMove(selectedMove.getMoveName())) {
                JOptionPane.showMessageDialog(this,
                        selectedPokemon.getPokemonName() + " already knows " + selectedMove.getMoveName() + ".",
                        "Move Known",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Check if Pokémon can learn the move (type compatibility)
            if (!selectedPokemon.canLearnMove(selectedMove)) {
                JOptionPane.showMessageDialog(this,
                        selectedPokemon.getPokemonName() + " can't learn " + selectedMove.getMoveName() +
                                " due to type incompatibility.",
                        "Cannot Learn Move",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Handle move teaching based on current moves
            if (selectedPokemon.getMoveSet().size() < 4) {
                // Directly teach the move if there's space
                TrainerController.TeachMoveResult result = controller.teachMove(
                        selectedPokemon, selectedMove, null);
                if (result.success) {
                    JOptionPane.showMessageDialog(this,
                            selectedPokemon.getPokemonName() + " learned " + selectedMove.getMoveName() + "!",
                            "Move Learned",
                            JOptionPane.INFORMATION_MESSAGE);
                    showManagePokemons(trainer, onHome);
                } else {
                    JOptionPane.showMessageDialog(this,
                            result.errorMessage,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Show move replacement panel
                replacePanel.setVisible(true);

                // Populate current moves
                List<Move> currentMoves = selectedPokemon.getMoveSet();
                for (int i = 0; i < 4; i++) {
                    replaceButtons[i].setText(currentMoves.get(i).getMoveName() +
                            " (" + currentMoves.get(i).getMoveClassification() + ")");
                    replaceButtons[i].setActionCommand(String.valueOf(i));
                    replaceButtons[i].setSelected(false);
                }

                // Add confirm button
                JButton confirmBtn = GUIUtils.createButton1("Confirm", 493, 420, 140, 35);
                selectMovePanel.add(confirmBtn);

                confirmBtn.addActionListener(e -> {
                    ButtonModel replaceSelected = replaceGroup.getSelection();
                    if (replaceSelected == null) {
                        JOptionPane.showMessageDialog(this, "Please select a move to replace.");
                        return;
                    }

                    int replaceIndex = Integer.parseInt(replaceSelected.getActionCommand());
                    Move oldMove = currentMoves.get(replaceIndex);

                    // Check if trying to replace an HM move
                    if (oldMove.getMoveClassification().equalsIgnoreCase("HM")) {
                        JOptionPane.showMessageDialog(this,
                                "Cannot replace HM moves.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Teach the new move
                    TrainerController.TeachMoveResult result = controller.teachMove(
                            selectedPokemon, selectedMove, oldMove);

                    if (result.success) {
                        JOptionPane.showMessageDialog(this,
                                selectedPokemon.getPokemonName() + " forgot " + oldMove.getMoveName() +
                                        " and learned " + selectedMove.getMoveName() + "!",
                                "Move Replaced",
                                JOptionPane.INFORMATION_MESSAGE);
                        showManagePokemons(trainer, onHome);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                result.errorMessage,
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });

                revalidate();
                repaint();
            }
        });
    }
}
