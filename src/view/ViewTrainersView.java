package view;

import controller.TrainerController;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import model.Item;
import model.Pokemon;
import model.Trainer;
import util.GUIUtils;

public class ViewTrainersView extends JPanel {

    private final TrainerController controller;
    private final List<Trainer> trainerList;
    private JPanel mainPanel;

    public ViewTrainersView(TrainerController controller) {
        this.controller = controller;
        this.trainerList = controller.getTrainerList();
        setLayout(null);
        setOpaque(false);
    }

    public void showViewAllTrainers(Runnable onHome) {
        removeAll();

        mainPanel = new JPanel(null);
        mainPanel.setOpaque(false);
        mainPanel.setBounds(0, 0, 901, 706);
        add(mainPanel);

        GUIUtils.addWelcomeLabel(mainPanel, "All Trainers", 35, 39, 353, 40);

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
                mainPanel,
                "<html><span style='font-size:18px;'><b>List of Trainers</b></span></html>",
                34, 90, 356, 30,
                45, 125, 330, 320,
                listPanel
        );

        // Search field
        GUIUtils.SearchFieldComponents search = GUIUtils.createSearchField(
                mainPanel,
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
        JButton btnHome = MainPokedexView.homeButton(e -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        mainPanel.add(btnHome);

        // Back button
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, e -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        mainPanel.add(backBtn);

        revalidate();
        repaint();
    }

    private static JLabel getJLabelWithPokemonAndInventory(Trainer t, int trainerNum) {
        StringBuilder lineup = new StringBuilder();
        if (t.getPokemonLineup() != null && !t.getPokemonLineup().isEmpty()) {
            for (Pokemon p : t.getPokemonLineup()) {
                lineup.append(p.getPokemonName()).append(", ");
            }
            if (lineup.length() > 2) {
                lineup.setLength(lineup.length() - 2);
            }
        } else {
            lineup.append("None");
        }

        StringBuilder inventory = new StringBuilder();
        ArrayList<Item> invList = t.getInventory();
        ArrayList<Integer> shownIds = new ArrayList<>();
        if (invList != null && !invList.isEmpty()) {
            for (Item item : invList) {
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
}
