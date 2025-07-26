package view;

import controller.MoveController;
import model.Move;
import java.awt.*;
import java.util.List;
import javax.swing.*;

import util.*;

public class MoveView extends JPanel {

    private final MoveController controller;
    private final List<Move> moveList;

    private JPanel movesWelcomePanel;
    private JLabel movesWelcomeDesc;

    public MoveView(MoveController controller, Runnable onHome) {
        this.controller = controller;
        this.moveList = controller.getMoveList();
        setLayout(null);
        setOpaque(false);
        showMovesWelcomePanel(onHome);
    }

    private void showMovesWelcomePanel(Runnable onHome) {
        if (movesWelcomePanel != null) {
            remove(movesWelcomePanel);
        }

        movesWelcomePanel = new JPanel(null);
        movesWelcomePanel.setOpaque(false);
        movesWelcomePanel.setBounds(0, 0, 901, 706);

        // Welcome label and description
        GUIUtils.addWelcomeLabel(movesWelcomePanel, "Manage Moves", 35, 39, 353, 40);

        movesWelcomeDesc = new JLabel();
        movesWelcomeDesc.setText(
                "<html><div style='text-align:justify;'>"
                + "<b>Welcome to the Moves Management System!</b><br><br>"
                + "Here you can add and view the Pokémon's Moves.<br><br>"
                + "Use the buttons to add, view, or search for Moves."
                + "</div></html>"
        );
        movesWelcomeDesc.setFont(new Font("Consolas", Font.PLAIN, 16));
        movesWelcomeDesc.setBounds(49, 105, 334, 500);
        movesWelcomeDesc.setVerticalAlignment(SwingConstants.TOP);
        movesWelcomeDesc.setOpaque(false);
        movesWelcomePanel.add(movesWelcomeDesc);

        // Add buttons for adding and viewing Moves
        JButton movesAddBtn = GUIUtils.createButton1("Add New Move", 493, 345, 140, 35);
        movesWelcomePanel.add(movesAddBtn);

        JButton movesViewBtn = GUIUtils.createButton2("View All Moves", 640, 345, 140, 35);
        movesWelcomePanel.add(movesViewBtn);

        JButton btnHome = MainPokedexView.homeButton(e -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        movesWelcomePanel.add(btnHome);

        add(movesWelcomePanel);
        revalidate();
        repaint();
    }

}
