package view;

import controller.MoveController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Move;
import src.EnhancedPokedexMVC;
import util.*;

public class MoveView extends JPanel {

    private final MoveController controller;
    private final List<Move> moveList;

    private JPanel movesWelcomePanel, movesMainPanel;
    private JLabel movesWelcomeDesc;

    public MoveView(MoveController controller, Runnable onHome) {
        this.controller = controller;
        this.moveList = controller.getMoveList();
        setLayout(null);
        setOpaque(false);
        showMovesWelcomePanel(onHome);
    }

    private void showViewAllMoves(Runnable onHome) {
        if (movesMainPanel != null) {
            GUIUtils.removeAllPanels(movesMainPanel);
        }

        movesMainPanel = new JPanel(null);
        movesMainPanel.setOpaque(false);
        movesMainPanel.setBounds(0, 0, 901, 706);

        // Title
        GUIUtils.addWelcomeLabel(movesMainPanel, "All Moves", 35, 39, 353, 40);

        // Scrollable list of moves
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        int moveNum = 1;
        for (Move m : moveList) {
            JLabel moveLabel = getJLabel(m, moveNum);
            listPanel.add(moveLabel);
            moveNum++;
        }

        // Make the scrollable panel fill the big left area
        GUIUtils.createLabeledScrollPanel(
                movesMainPanel,
                "<html><span style='font-size:18px;'><b>List of Moves</b></span></html>",
                34, 90, 356, 30, // Label at top left
                45, 125, 310, 320, // Scroll panel fills most of left side
                listPanel
        );

        // Search field
        GUIUtils.SearchFieldComponents search = GUIUtils.createSearchField(
                movesMainPanel,
                "Search Move:",
                490, 442, 307, 50,
                560, 500, 180, 30,
                645, 565, 62, 43,
                "Search"
        );

        search.button.addActionListener(e -> {
            String query = search.field.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a move name.");
                return;
            }
            Move found = null;
            for (Move m : moveList) {
                if (m.getMoveName().equalsIgnoreCase(query)) {
                    found = m;
                    break;
                }
            }
            if (found != null) {
                String type2 = (found.getMoveType2() == null || found.getMoveType2().isEmpty()) ? "N/A" : found.getMoveType2();
                JOptionPane.showMessageDialog(this,
                        "<html><b>Move Searched</b><br><br>"
                        + "Move Name: <b>" + found.getMoveName() + "</b><br>"
                        + "Type 1: " + found.getMoveType1() + "<br>"
                        + "Type 2: " + type2 + "<br>"
                        + "Category: " + found.getMoveClassification() + "<br>"
                        + "Description: " + found.getMoveDescription() + "</html>"
                );
            } else {
                JOptionPane.showMessageDialog(this, "Move not found.");
            }
        });

        // Home button
        JButton btnHome = MainPokedexView.homeButton(e -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        movesMainPanel.add(btnHome);

        // Back button
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, e -> {
            remove(movesMainPanel);
            showMovesWelcomePanel(onHome);
            revalidate();
            repaint();
        });
        movesMainPanel.add(backBtn);

        add(movesMainPanel);
        revalidate();
        repaint();
    }

    private static JLabel getJLabel(Move m, int moveNum) {
        String type2 = (m.getMoveType2() != null && !m.getMoveType2().isEmpty()) ? m.getMoveType2() : "N/A";
        String moveText = "<html>"
                + "<span style='font-size:14px;'><b>#" + moveNum + "  " + m.getMoveName() + "</b> | " + m.getMoveClassification() + "</span><br>"
                + "Type 1: " + m.getMoveType1() + " | Type 2: " + type2 + "<br>"
                + "<div style='width:250px; text-align:justify;'>" + m.getMoveDescription() + "</div>"
                + "<br><br>"
                + "</html>";

        JLabel moveLabel = new JLabel(moveText);
        moveLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
        return moveLabel;
    }

    // Show the add move form
    private void showAddMove(Runnable onHome) {
        GUIUtils.removeAllPanels(movesMainPanel);

        JPanel addPanel = new JPanel(null);
        addPanel.setOpaque(false);
        addPanel.setBounds(0, 0, 901, 706);

        GUIUtils.addWelcomeLabel(addPanel, "Add New Move", 35, 39, 353, 40);

        // Move Name
        JLabel moveNameLabel = new JLabel("Move Name:");
        moveNameLabel.setFont(new Font("Consolas", Font.BOLD, 15));
        moveNameLabel.setBounds(50, 120, 120, 20);
        addPanel.add(moveNameLabel);

        JTextField moveNameField = new JTextField();
        moveNameField.setBounds(50, 145, 320, 30);
        moveNameField.setMargin(new Insets(0, 0, 0, 0));
        addPanel.add(moveNameField);

        // Type 1 and Type 2
        JLabel moveType1Label = new JLabel("Type 1:");
        moveType1Label.setFont(new Font("Consolas", Font.BOLD, 15));
        moveType1Label.setBounds(50, 180, 120, 20);
        addPanel.add(moveType1Label);

        JTextField moveType1Field = new JTextField();
        moveType1Field.setBounds(50, 205, 150, 30);
        moveType1Field.setMargin(new Insets(0, 0, 0, 0));
        addPanel.add(moveType1Field);

        JLabel moveType2Label = new JLabel("Type 2 (optional):");
        moveType2Label.setFont(new Font("Consolas", Font.BOLD, 15));
        moveType2Label.setBounds(220, 180, 160, 20);
        addPanel.add(moveType2Label);

        JTextField moveType2Field = new JTextField();
        moveType2Field.setBounds(220, 205, 150, 30);
        moveType2Field.setMargin(new Insets(0, 0, 0, 0));
        addPanel.add(moveType2Field);

        // Category
        JLabel moveCategoryLabel = new JLabel("Category (TM or HM):");
        moveCategoryLabel.setFont(new Font("Consolas", Font.BOLD, 15));
        moveCategoryLabel.setBounds(50, 240, 180, 20);
        addPanel.add(moveCategoryLabel);

        JTextField moveCategoryField = new JTextField();
        moveCategoryField.setBounds(50, 265, 320, 30);
        moveCategoryField.setMargin(new Insets(0, 0, 0, 0));
        addPanel.add(moveCategoryField);

        // Description
        JLabel moveDescLabel = new JLabel("Description:");
        moveDescLabel.setFont(new Font("Consolas", Font.BOLD, 15));
        moveDescLabel.setBounds(50, 300, 120, 20);
        addPanel.add(moveDescLabel);

        JTextField moveDescField = new JTextField();
        moveDescField.setBounds(50, 325, 320, 30);
        moveDescField.setMargin(new Insets(0, 0, 0, 0));
        addPanel.add(moveDescField);

        // Valid Types below description
        JLabel validTypesLabel = new JLabel();
        validTypesLabel.setFont(new Font("Consolas", Font.PLAIN, 10));
        validTypesLabel.setBounds(50, 360, 325, 70);
        validTypesLabel.setText("<html><div style='text-align:justify; font-size:10px;'><b>Valid Types:</b> " + String.join(", ", EnhancedPokedexMVC.VALID_POKEMON_TYPES) + "</div></html>");
        validTypesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        validTypesLabel.setVerticalAlignment(SwingConstants.TOP);
        addPanel.add(validTypesLabel);

        // Enter button
        JButton enterBtn = GUIUtils.createNavButton("Enter", 787, 345, 67, 35, null);
        addPanel.add(enterBtn);

        enterBtn.addActionListener(e -> {
            String name = moveNameField.getText().trim();
            String type1 = moveType1Field.getText().trim();
            String type2 = moveType2Field.getText().trim();
            String category = moveCategoryField.getText().trim();
            String desc = moveDescField.getText().trim();
            String[] validTypes = EnhancedPokedexMVC.VALID_POKEMON_TYPES;
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Move name cannot be empty.");
                return;
            }
            if (type1.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Type 1 cannot be empty.");
                return;
            }
            boolean validType1 = false, validType2 = false;
            for (String vt : validTypes) {
                if (vt.equalsIgnoreCase(type1)) {
                    validType1 = true;
                }
                if (!type2.isEmpty() && vt.equalsIgnoreCase(type2)) {
                    validType2 = true;
                }
            }
            if (!validType1) {
                JOptionPane.showMessageDialog(this, "Invalid Type 1. Allowed types are:\n" + String.join(", ", validTypes));
                return;
            }
            if (!type2.isEmpty() && !validType2) {
                JOptionPane.showMessageDialog(this, "Invalid Type 2. Allowed types are:\n" + String.join(", ", validTypes));
                return;
            }
            if (category.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Category cannot be empty.");
                return;
            }
            String catUpper = category.trim().toUpperCase();
            if (!catUpper.equals("TM") && !catUpper.equals("HM")) {
                JOptionPane.showMessageDialog(this, "Category must be TM or HM.");
                return;
            }
            if (desc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Description cannot be empty.");
                return;
            }
            boolean success = controller.addMove(name, type1, type2, catUpper, desc);
            if (success) {
                JOptionPane.showMessageDialog(this, "Move added!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add move. Check for duplicate name or invalid input.");
            }
            remove(addPanel);
            showMovesWelcomePanel(onHome);
        });

        add(addPanel);
        revalidate();
        repaint();
    }

    // Update welcome panel to use new screens
    private void showMovesWelcomePanel(Runnable onHome) {
        if (movesWelcomePanel != null) {
            remove(movesWelcomePanel);
        }

        movesWelcomePanel = new JPanel(null);
        movesWelcomePanel.setOpaque(false);
        movesWelcomePanel.setBounds(0, 0, 901, 706);

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

        // Add button actions
        movesViewBtn.addActionListener(e -> {
            remove(movesWelcomePanel);
            showViewAllMoves(onHome);
            revalidate();
            repaint();
        });

        movesAddBtn.addActionListener(e -> {
            remove(movesWelcomePanel);
            showAddMove(onHome);
            revalidate();
            repaint();
        });

        add(movesWelcomePanel);
        revalidate();
        repaint();
    }
}
