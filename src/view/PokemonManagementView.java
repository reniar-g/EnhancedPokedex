package view;

import controller.TrainerController;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import model.Move;
import model.Pokemon;
import model.Trainer;
import util.GUIUtils;

public class PokemonManagementView extends JPanel {

    private final TrainerController controller;

    public PokemonManagementView(TrainerController controller) {
        this.controller = controller;
        setLayout(null);
        setOpaque(false);
    }

    public void showAddPokemonDialog(Trainer trainer, Runnable onHome) {
        removeAll();

        JPanel addPanel = new JPanel(null);
        addPanel.setOpaque(false);
        addPanel.setBounds(0, 0, 901, 706);
        add(addPanel);

        GUIUtils.addWelcomeLabel(addPanel, "Add Pokémon to Trainer", 35, 39, 353, 40);

        JLabel pokemonToAdd = new JLabel("Select Pokémon to Add:");
        pokemonToAdd.setFont(new Font("Consolas", Font.BOLD, 17));
        pokemonToAdd.setBounds(45, 105, 334, 20);
        addPanel.add(pokemonToAdd);

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
        scrollPane.setBounds(45, 125, 330, 320);
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
            removeAll();
            revalidate();
            repaint();
            if (onHome != null) {
                onHome.run();
            }
        });
        addPanel.add(backBtn);

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
                if (onHome != null) {
                    onHome.run();
                }
            }
        });
    }

    /**
     * Creates a formatted label with Pokemon details including moves
     */
    public JLabel createPokemonDetailsLabel(Pokemon p, boolean isLineup) {
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

    public void showSwitchPokemonDialog(Trainer trainer, Runnable onHome) {
        removeAll();

        JPanel switchPanel = new JPanel(null);
        switchPanel.setOpaque(false);
        switchPanel.setBounds(0, 0, 901, 706);
        add(switchPanel);

        GUIUtils.addWelcomeLabel(switchPanel, "Switch Pokémon", 35, 39, 353, 40);

        // Create lineup panel
        JPanel lineupPanel = new JPanel();
        lineupPanel.setLayout(new BoxLayout(lineupPanel, BoxLayout.Y_AXIS));
        lineupPanel.setOpaque(false);

        List<Pokemon> lineup = trainer.getPokemonLineup();
        ButtonGroup lineupGroup = new ButtonGroup();
        for (Pokemon p : lineup) {
            String pokemonDetails = String.format("#%d %s (Lvl %d). %s%s",
                    p.getPokedexNumber(),
                    p.getPokemonName(),
                    p.getBaseLevel(),
                    p.getPokemonType1(),
                    p.getPokemonType2() != null && !p.getPokemonType2().isEmpty() ? " / " + p.getPokemonType2() : "");
            JRadioButton radio = new JRadioButton(pokemonDetails);
            radio.setOpaque(false);
            radio.setActionCommand("lineup:" + lineup.indexOf(p));
            lineupGroup.add(radio);
            lineupPanel.add(radio);
            radio.setFont(new Font("Consolas", Font.PLAIN, 12));
        }

        // Create storage panel
        JPanel storagePanel = new JPanel();
        storagePanel.setLayout(new BoxLayout(storagePanel, BoxLayout.Y_AXIS));
        storagePanel.setOpaque(false);

        List<Pokemon> storage = trainer.getPokemonStorage();
        ButtonGroup storageGroup = new ButtonGroup();
        for (Pokemon p : storage) {
            String pokemonDetails = String.format("#%d %s (Lvl %d). %s%s",
                    p.getPokedexNumber(),
                    p.getPokemonName(),
                    p.getBaseLevel(),
                    p.getPokemonType1(),
                    p.getPokemonType2() != null && !p.getPokemonType2().isEmpty() ? " / " + p.getPokemonType2() : "");
            JRadioButton radio = new JRadioButton(pokemonDetails);
            radio.setOpaque(false);
            radio.setActionCommand("storage:" + storage.indexOf(p));
            storageGroup.add(radio);
            storagePanel.add(radio);
            radio.setFont(new Font("Consolas", Font.PLAIN, 12));
        }

        // Add scroll panes
        GUIUtils.createLabeledScrollPanel(
                switchPanel,
                "<html><span style='font-size:18px;'><b>Active Lineup (" + lineup.size() + "/6)</b></span></html>",
                34, 90, 356, 30,
                45, 125, 330, 140,
                lineupPanel
        );

        JScrollPane storageScrollPane = GUIUtils.createLabeledScrollPanel(
                switchPanel,
                "<html><span style='font-size:18px;'><b>Storage (" + storage.size() + "/20)</b></span></html>",
                34, 270, 356, 30,
                45, 305, 330, 135,
                storagePanel
        );
        storageScrollPane.setOpaque(false);
        storageScrollPane.getViewport().setOpaque(false);

        // Add switch button
        JButton switchBtn = GUIUtils.createButton1("Switch", 493, 345, 140, 35);
        switchPanel.add(switchBtn);

        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        switchPanel.add(btnHome);

        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> {
            removeAll();
            revalidate();
            repaint();
            if (onHome != null) {
                onHome.run();
            }
        });
        switchPanel.add(backBtn);

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
                    if (onHome != null) {
                        onHome.run();
                    }
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
                    if (onHome != null) {
                        onHome.run();
                    }
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
                if (onHome != null) {
                    onHome.run();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Select Pokémon from lineup or storage to switch",
                        "Selection Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void showReleasePokemonDialog(Trainer trainer, Runnable onHome) {
        removeAll();

        JPanel releasePanel = new JPanel(null);
        releasePanel.setOpaque(false);
        releasePanel.setBounds(0, 0, 901, 706);
        add(releasePanel);

        GUIUtils.addWelcomeLabel(releasePanel, "Release Pokémon", 35, 39, 353, 40);

        // Create lineup panel
        JPanel lineupPanel = new JPanel();
        lineupPanel.setLayout(new BoxLayout(lineupPanel, BoxLayout.Y_AXIS));
        lineupPanel.setOpaque(false);

        List<Pokemon> lineup = trainer.getPokemonLineup();
        ButtonGroup lineupGroup = new ButtonGroup();
        for (Pokemon p : lineup) {
            String pokemonDetails = String.format("#%d %s (Lvl %d). %s%s",
                    p.getPokedexNumber(),
                    p.getPokemonName(),
                    p.getBaseLevel(),
                    p.getPokemonType1(),
                    p.getPokemonType2() != null && !p.getPokemonType2().isEmpty() ? " / " + p.getPokemonType2() : "");
            JRadioButton radio = new JRadioButton(pokemonDetails);
            radio.setOpaque(false);
            radio.setActionCommand("lineup:" + lineup.indexOf(p));
            lineupGroup.add(radio);
            lineupPanel.add(radio);
            radio.setFont(new Font("Consolas", Font.PLAIN, 12));
        }

        // Create storage panel
        JPanel storagePanel = new JPanel();
        storagePanel.setLayout(new BoxLayout(storagePanel, BoxLayout.Y_AXIS));
        storagePanel.setOpaque(false);

        List<Pokemon> storage = trainer.getPokemonStorage();
        ButtonGroup storageGroup = new ButtonGroup();
        for (Pokemon p : storage) {
            String pokemonDetails = String.format("#%d %s (Lvl %d). %s%s",
                    p.getPokedexNumber(),
                    p.getPokemonName(),
                    p.getBaseLevel(),
                    p.getPokemonType1(),
                    p.getPokemonType2() != null && !p.getPokemonType2().isEmpty() ? " / " + p.getPokemonType2() : "");
            JRadioButton radio = new JRadioButton(pokemonDetails);
            radio.setOpaque(false);
            radio.setActionCommand("storage:" + storage.indexOf(p));
            storageGroup.add(radio);
            storagePanel.add(radio);
            radio.setFont(new Font("Consolas", Font.PLAIN, 12));
        }

        // Add scroll panes
        GUIUtils.createLabeledScrollPanel(
                releasePanel,
                "<html><span style='font-size:18px;'><b>Active Lineup (" + lineup.size() + "/6)</b></span></html>",
                34, 90, 356, 30,
                45, 125, 330, 140,
                lineupPanel
        );

        JScrollPane storageScrollPane = GUIUtils.createLabeledScrollPanel(
                releasePanel,
                "<html><span style='font-size:18px;'><b>Storage (" + storage.size() + "/20)</b></span></html>",
                34, 270, 356, 30,
                45, 305, 330, 135,
                storagePanel
        );
        storageScrollPane.setOpaque(false);
        storageScrollPane.getViewport().setOpaque(false);

        // Add release button
        JButton releaseBtn = GUIUtils.createButton1("Release", 493, 345, 140, 35);
        releasePanel.add(releaseBtn);

        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        releasePanel.add(btnHome);

        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> {
            removeAll();
            revalidate();
            repaint();
            if (onHome != null) {
                onHome.run();
            }
        });
        releasePanel.add(backBtn);

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
                    if (onHome != null) {
                        onHome.run();
                    }
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
                    if (onHome != null) {
                        onHome.run();
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Failed to release Pokémon.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void showTeachMoveDialog(Trainer trainer, Runnable onHome) {
        removeAll();

        JPanel teachMovePanel = new JPanel(null);
        teachMovePanel.setOpaque(false);
        teachMovePanel.setBounds(0, 0, 901, 706);
        add(teachMovePanel);

        GUIUtils.addWelcomeLabel(teachMovePanel, "Teach Moves", 35, 39, 353, 40);

        //Select Pokémon
        JPanel selectPokemonPanel = new JPanel(null);
        selectPokemonPanel.setOpaque(false);
        selectPokemonPanel.setBounds(0, 0, 901, 706);
        teachMovePanel.add(selectPokemonPanel);

        JLabel selectPokemonLabel = new JLabel("Select Pokémon to Teach:");
        selectPokemonLabel.setFont(new Font("Consolas", Font.BOLD, 17));
        selectPokemonLabel.setBounds(45, 105, 334, 20);
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
                    "#" + p.getPokedexNumber() + " - " + p.getPokemonName()
                    + " (Lv." + p.getBaseLevel() + ", " + location + ")"
            );
            radio.setFont(new Font("Consolas", Font.PLAIN, 14));
            radio.setOpaque(false);
            radio.setActionCommand(String.valueOf(allPokemon.indexOf(p)));
            pokemonGroup.add(radio);
            pokemonListPanel.add(radio);
        }

        JScrollPane pokemonScroll = new JScrollPane(pokemonListPanel);
        pokemonScroll.setBounds(45, 125, 330, 320);
        pokemonScroll.setOpaque(false);
        pokemonScroll.getVerticalScrollBar().setUnitIncrement(14);
        pokemonScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pokemonScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pokemonScroll.setBorder(null);
        pokemonScroll.getViewport().setOpaque(false);
        selectPokemonPanel.add(pokemonScroll);

        // Next button (only enabled when a Pokémon is selected)
        JButton nextBtn = GUIUtils.createButton1("Next", 493, 345, 140, 35);
        nextBtn.setEnabled(false);
        selectPokemonPanel.add(nextBtn);

        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        selectPokemonPanel.add(btnHome);

        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> {
            removeAll();
            revalidate();
            repaint();
            if (onHome != null) {
                onHome.run();
            }
        });
        selectPokemonPanel.add(backBtn);

        // Enable Next button when a Pokémon is selected
        pokemonGroup.getElements().asIterator().forEachRemaining(button -> {
            button.addActionListener(e -> nextBtn.setEnabled(true));
        });

        // Add teach move panel (initially invisible)
        JPanel teachPanel = new JPanel(null);
        teachPanel.setOpaque(false);
        teachPanel.setBounds(0, 0, 901, 706);
        teachPanel.setVisible(false);
        teachMovePanel.add(teachPanel);

        // Next button action
        nextBtn.addActionListener(evt -> {
            // Get selected Pokémon
            ButtonModel selected = pokemonGroup.getSelection();
            if (selected == null) {
                return;
            }

            int index = Integer.parseInt(selected.getActionCommand());
            Pokemon pokemon = allPokemon.get(index);

            // Clear the teach panel before adding new components
            teachPanel.removeAll();
            teachPanel.revalidate();
            teachPanel.repaint();

            // Show teach move panel
            selectPokemonPanel.setVisible(false);
            teachPanel.setVisible(true);

            // Current moves panel
            JPanel currentMovesPanel = new JPanel();
            currentMovesPanel.setLayout(new BoxLayout(currentMovesPanel, BoxLayout.Y_AXIS));
            currentMovesPanel.setOpaque(false);

            List<Move> currentMoves = pokemon.getMoveSet();
            ButtonGroup currentMovesGroup = new ButtonGroup();
            for (Move m : currentMoves) {
                JRadioButton radio = new JRadioButton(m.getMoveName() + " - " + m.getMoveClassification());
                radio.setFont(new Font("Consolas", Font.PLAIN, 14));
                radio.setOpaque(false);
                radio.setActionCommand(String.valueOf(currentMoves.indexOf(m)));
                if (m.getMoveClassification().equals("HM")) {
                    radio.setEnabled(false);
                    radio.setToolTipText("HM moves cannot be replaced");
                }
                currentMovesGroup.add(radio);
                currentMovesPanel.add(radio);
            }

            // Available moves panel
            JPanel availableMovesPanel = new JPanel();
            availableMovesPanel.setLayout(new BoxLayout(availableMovesPanel, BoxLayout.Y_AXIS));
            availableMovesPanel.setOpaque(false);

            List<Move> availableMoves = controller.getMoveController().getMoveList();
            ButtonGroup availableMovesGroup = new ButtonGroup();
            for (Move m : availableMoves) {
                if (!currentMoves.contains(m)) {
                    JRadioButton radio = new JRadioButton(
                            m.getMoveName() + " - " + m.getMoveClassification()
                            + " (Type: " + m.getMoveType1()
                            + (m.getMoveType2() != null && !m.getMoveType2().isEmpty() ? "/" + m.getMoveType2() : "") + ")"
                    );
                    radio.setFont(new Font("Consolas", Font.PLAIN, 14));
                    radio.setOpaque(false);
                    radio.setActionCommand(String.valueOf(availableMoves.indexOf(m)));
                    availableMovesGroup.add(radio);
                    availableMovesPanel.add(radio);
                }
            }

            // Create scroll panes for both panels
            GUIUtils.createLabeledScrollPanel(
                    teachPanel,
                    "<html><span style='font-size:18px;'><b>Current Moves</b></span></html>",
                    34, 90, 356, 30,
                    45, 125, 330, 140,
                    currentMovesPanel
            );

            GUIUtils.createLabeledScrollPanel(
                    teachPanel,
                    "<html><span style='font-size:18px;'><b>Available Moves</b></span></html>",
                    34, 270, 356, 30,
                    45, 305, 330, 135,
                    availableMovesPanel
            );

            // Teach button
            JButton teachBtn = GUIUtils.createButton1("Teach Move", 493, 345, 140, 35);
            teachPanel.add(teachBtn);

            teachBtn.addActionListener(e -> {
                ButtonModel selectedCurrentMove = currentMovesGroup.getSelection();
                ButtonModel selectedNewMove = availableMovesGroup.getSelection();

                if (selectedCurrentMove == null) {
                    // Check if pokemon has space for a new move
                    if (currentMoves.size() >= 4) {
                        JOptionPane.showMessageDialog(this,
                                "Please select a move to replace.",
                                "Move Slot Required",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                if (selectedNewMove == null) {
                    JOptionPane.showMessageDialog(this,
                            "Please select a move to teach.",
                            "Move Required",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Get selected moves
                Move newMove = availableMoves.get(
                        Integer.parseInt(selectedNewMove.getActionCommand())
                );

                Move oldMove = null;
                if (selectedCurrentMove != null) {
                    oldMove = currentMoves.get(
                            Integer.parseInt(selectedCurrentMove.getActionCommand())
                    );
                }

                // Use TrainerController's teachMove method which includes type compatibility check
                TrainerController.TeachMoveResult result = controller.teachMove(pokemon, newMove, oldMove);

                if (result.success) {
                    if (result.replacedMove != null) {
                        JOptionPane.showMessageDialog(this,
                                result.replacedMove.getMoveName() + " was replaced with " + result.newMove.getMoveName() + "!");
                    } else {
                        JOptionPane.showMessageDialog(this,
                                pokemon.getPokemonName() + " learned " + result.newMove.getMoveName() + "!");
                    }
                    if (onHome != null) {
                        onHome.run();
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            result.errorMessage,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            // Back to pokemon selection button
            JButton backToPokemonBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, e -> {
                teachPanel.setVisible(false);
                selectPokemonPanel.setVisible(true);
            });
            teachPanel.add(backToPokemonBtn);

            // Home button for teach panel
            JButton teachHomebtn = MainPokedexView.homeButton(e -> {
                if (onHome != null) {
                    onHome.run();
                }
            });
            teachPanel.add(teachHomebtn);
        });

        revalidate();
        repaint();
    }
}
