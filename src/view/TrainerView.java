package view;

import controller.TrainerController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
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
        this.itemManagementView = new ItemManagementView(controller);
        this.pokemonManagementView = new PokemonManagementView(controller);
        setLayout(null);
        setOpaque(false);
        showTrainersWelcomePanel(onHome);
    }

    private void showTrainersWelcomePanel(Runnable onHome) {
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

        JButton viewBtn = GUIUtils.createButton1("View Trainers", 640, 345, 140, 35);
        trainersWelcomePanel.add(viewBtn);

        JButton selectBtn = GUIUtils.createButton1("Select Trainers", 493, 387, 140, 35);
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

        addBtn.addActionListener(evt -> {
            AddTrainerView addTrainerView = new AddTrainerView(controller);
            removeAll();
            addTrainerView.setBounds(0, 0, 901, 706);
            add(addTrainerView);
            addTrainerView.showAddTrainer(() -> showTrainersWelcomePanel(onHome));
            revalidate();
            repaint();
        });
        viewBtn.addActionListener(evt -> {
            ViewTrainersView viewTrainersView = new ViewTrainersView(controller);
            removeAll();
            viewTrainersView.setBounds(0, 0, 901, 706);
            add(viewTrainersView);
            viewTrainersView.showViewAllTrainers(() -> showTrainersWelcomePanel(onHome));
            revalidate();
            repaint();
        });
        selectBtn.addActionListener(evt -> showManageTrainers(onHome));
    }

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

        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        trainersMainPanel.add(btnHome);

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

        JButton managePokeBtn = GUIUtils.createButton1("Manage Pokémon", 640, 345, 140, 35);
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

    private void showManageItems(Trainer trainer, Runnable onHome) {
        removeAll();

        JPanel mainPanel = new JPanel(null);
        mainPanel.setOpaque(false);
        mainPanel.setBounds(0, 0, 901, 706);

        GUIUtils.addWelcomeLabel(mainPanel, "Manage Items", 35, 39, 353, 40);
        JLabel descLabel = new JLabel("<html><div style='width:320px; text-align:justify;'>"
                + "<b>Welcome to the Item Management Panel!</b><br><br>"
                + "Here you can buy, sell, use, or view your trainer's items."
                + "<br><br>Select an action below to manage your inventory." + "</div></html>");
        descLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        descLabel.setBounds(45, 100, 330, 100);
        descLabel.setVerticalAlignment(SwingConstants.TOP);
        descLabel.setHorizontalAlignment(SwingConstants.LEFT);
        mainPanel.add(descLabel);

        JButton buyBtn = GUIUtils.createButton1("Buy Item", 493, 345, 140, 35);
        JButton sellBtn = GUIUtils.createButton1("Sell Item", 640, 345, 140, 35);
        JButton useBtn = GUIUtils.createButton1("Use Item", 493, 387, 140, 35);
        JButton viewInvBtn = GUIUtils.createButton1("View Inventory", 640, 387, 140, 35);
        mainPanel.add(buyBtn);
        mainPanel.add(sellBtn);
        mainPanel.add(useBtn);
        mainPanel.add(viewInvBtn);

        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        mainPanel.add(btnHome);

        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> {
            showTrainerActionMenu(trainer, onHome);
        });
        mainPanel.add(backBtn);

        add(mainPanel);
        revalidate();
        repaint();

        buyBtn.addActionListener(evt -> showBuyItemPanel(trainer, onHome));
        sellBtn.addActionListener(evt -> showSellItemPanel(trainer, onHome));
        useBtn.addActionListener(evt -> {
            removeAll();
            if (itemManagementView == null) {
                itemManagementView = new ItemManagementView(controller);
            }
            itemManagementView.setBounds(0, 0, 901, 706);
            add(itemManagementView);
            itemManagementView.showUseItemDialog(trainer, () -> showManageItems(trainer, onHome));
            revalidate();
            repaint();
        });
        viewInvBtn.addActionListener(evt -> showTrainerInventoryDialog(trainer, onHome));
    }

    private ItemManagementView itemManagementView;
    private PokemonManagementView pokemonManagementView;

    private void showBuyItemPanel(Trainer trainer, Runnable onHome) {
        removeAll();
        if (itemManagementView == null) {
            itemManagementView = new ItemManagementView(controller);
        }
        itemManagementView.setBounds(0, 0, 901, 706);
        add(itemManagementView);
        itemManagementView.showBuyItemPanel(trainer, () -> showManageItems(trainer, onHome));
        revalidate();
        repaint();
    }

    private void showSellItemPanel(Trainer trainer, Runnable onHome) {
        removeAll();
        if (itemManagementView == null) {
            itemManagementView = new ItemManagementView(controller);
        }
        itemManagementView.setBounds(0, 0, 901, 706);
        add(itemManagementView);
        itemManagementView.showSellItemPanel(trainer, () -> showManageItems(trainer, onHome));
        revalidate();
        repaint();
    }

    private void showTrainerInventoryDialog(Trainer trainer, Runnable onHome) {
        removeAll();
        if (itemManagementView == null) {
            itemManagementView = new ItemManagementView(controller);
        }
        itemManagementView.setBounds(0, 0, 901, 706);
        add(itemManagementView);
        itemManagementView.showTrainerInventoryDialog(trainer, () -> showManageItems(trainer, onHome));
        revalidate();
        repaint();
    }

    private void showManagePokemons(Trainer trainer, Runnable onHome) {
        removeAll();

        JPanel pokePanel = new JPanel(null);
        pokePanel.setOpaque(false);
        pokePanel.setBounds(0, 0, 901, 706);

        GUIUtils.addWelcomeLabel(pokePanel, "Manage Pokémon", 35, 39, 353, 40);

        JPanel lineupListPanel = new JPanel();
        lineupListPanel.setLayout(new BoxLayout(lineupListPanel, BoxLayout.Y_AXIS));
        lineupListPanel.setOpaque(false);

        if (pokemonManagementView == null) {
            pokemonManagementView = new PokemonManagementView(controller);
        }

        // add the trainer's active pokemon lineup
        List<Pokemon> lineup = trainer.getPokemonLineup();
        if (lineup.isEmpty()) {
            JLabel emptyLabel = new JLabel("No Pokémon in active lineup");
            emptyLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
            lineupListPanel.add(emptyLabel);
        } else {
            for (Pokemon p : lineup) {
                lineupListPanel.add(pokemonManagementView.createPokemonDetailsLabel(p, true));
            }
        }

        JPanel storageListPanel = new JPanel();
        storageListPanel.setLayout(new BoxLayout(storageListPanel, BoxLayout.Y_AXIS));
        storageListPanel.setOpaque(false);

        // add the storage pokemon
        List<Pokemon> storage = trainer.getPokemonStorage();
        if (storage.isEmpty()) {
            JLabel emptyLabel = new JLabel("No Pokémon in storage");
            emptyLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
            storageListPanel.add(emptyLabel);
        } else {
            for (Pokemon p : storage) {
                storageListPanel.add(pokemonManagementView.createPokemonDetailsLabel(p, false));
            }
        }

        GUIUtils.createLabeledScrollPanel(
                pokePanel,
                "<html><span style='font-size:18px;'><b>Active Lineup (" + lineup.size() + "/6)</b></span></html>",
                34, 90, 356, 30,
                45, 125, 330, 140,
                lineupListPanel
        );

        GUIUtils.createLabeledScrollPanel(
                pokePanel,
                "<html><span style='font-size:18px;'><b>Storage (" + storage.size() + "/20)</b></span></html>",
                34, 270, 356, 30,
                45, 305, 330, 135,
                storageListPanel
        );

        JButton addPokemonBtn = GUIUtils.createButton1("Add Pokémon", 493, 345, 140, 35);
        JButton switchBtn = GUIUtils.createButton1("Switch to Storage", 640, 345, 140, 35);
        JButton releaseBtn = GUIUtils.createButton1("Release Pokémon", 493, 387, 140, 35);
        JButton teachMoveBtn = GUIUtils.createButton1("Teach Moves", 640, 387, 140, 35);

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

        addPokemonBtn.addActionListener(evt -> {
            removeAll();
            if (pokemonManagementView == null) {
                pokemonManagementView = new PokemonManagementView(controller);
            }
            pokemonManagementView.setBounds(0, 0, 901, 706);
            add(pokemonManagementView);
            pokemonManagementView.showAddPokemonDialog(trainer, () -> showManagePokemons(trainer, onHome));
            revalidate();
            repaint();
        });
        switchBtn.addActionListener(evt -> {
            removeAll();
            if (pokemonManagementView == null) {
                pokemonManagementView = new PokemonManagementView(controller);
            }
            pokemonManagementView.setBounds(0, 0, 901, 706);
            add(pokemonManagementView);
            pokemonManagementView.showSwitchPokemonDialog(trainer, () -> showManagePokemons(trainer, onHome));
            revalidate();
            repaint();
        });
        releaseBtn.addActionListener(evt -> {
            removeAll();
            if (pokemonManagementView == null) {
                pokemonManagementView = new PokemonManagementView(controller);
            }
            pokemonManagementView.setBounds(0, 0, 901, 706);
            add(pokemonManagementView);
            pokemonManagementView.showReleasePokemonDialog(trainer, () -> showManagePokemons(trainer, onHome));
            revalidate();
            repaint();
        });
        teachMoveBtn.addActionListener(evt -> {
            removeAll();
            if (pokemonManagementView == null) {
                pokemonManagementView = new PokemonManagementView(controller);
            }
            pokemonManagementView.setBounds(0, 0, 901, 706);
            add(pokemonManagementView);
            pokemonManagementView.showTeachMoveDialog(trainer, () -> showManagePokemons(trainer, onHome));
            revalidate();
            repaint();
        });
    }

}
