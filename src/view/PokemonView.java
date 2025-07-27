package view;

import controller.PokemonController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Pokemon;
import src.EnhancedPokedexMVC;
import util.*;

public class PokemonView extends JPanel {

    private PokemonController controller;
    private List<Pokemon> pokedex;
    private int currentPokemonIndex = 0;

    private JLabel pokemonImageLabel, pokemonNameLabel, pokemonLevelLabel, hpLabel, atkLabel, defLabel, spdLabel, typeLabel1, typeLabel2;
    private JPanel pokemonWelcomePanel, pokemonMainPanel, evolutionPanel, boxBase, boxSecond, boxThird;

    // Constructs a new PokemonView panel. Initializes the view and displays the welcome panel.
    public PokemonView(PokemonController controller, Runnable onHome) {
        this.controller = controller;
        this.pokedex = controller.getPokedex();
        setLayout(null);
        setOpaque(false);
        showPokemonWelcomePanel(onHome);
    }

    /**
     * Displays the welcome panel with options to add or view Pokémon.
     *
     * @param onHome Runnable callback to return to the home/main menu.
     */
    private void showPokemonWelcomePanel(Runnable onHome) {
        GUIUtils.removeAllPanels(pokemonMainPanel);

        pokemonWelcomePanel = new JPanel(null);
        pokemonWelcomePanel.setOpaque(false);
        pokemonWelcomePanel.setBounds(0, 0, 901, 706);

        // Welcome label and description
        GUIUtils.addWelcomeLabel(pokemonWelcomePanel, "Manage Pokémons", 35, 39, 353, 40);

        JLabel pokemonWelcomeDesc = new JLabel();
        pokemonWelcomeDesc.setText(
                "<html><div style='text-align:justify;'>"
                + "<b>Welcome to the Pokémon Management System!</b><br><br>"
                + "Here you can add, view, and manage your Pokémon collection.<br><br>"
                + "Use the buttons to add, view, or search for Pokémons."
                + "</div></html>"
        );
        pokemonWelcomeDesc.setFont(new Font("Consolas", Font.PLAIN, 16));
        pokemonWelcomeDesc.setBounds(49, 105, 334, 500);
        pokemonWelcomeDesc.setVerticalAlignment(SwingConstants.TOP);
        pokemonWelcomeDesc.setOpaque(false);
        pokemonWelcomePanel.add(pokemonWelcomeDesc);

        // Add buttons for adding and viewing Pokémon
        JButton pokemonAddBtn = GUIUtils.createButton1("Add New Pokémon", 493, 345, 140, 35);
        pokemonWelcomePanel.add(pokemonAddBtn);

        JButton pokemonViewBtn = GUIUtils.createButton2("View All Pokémon", 640, 345, 140, 35);
        pokemonWelcomePanel.add(pokemonViewBtn);

        JButton btnHome = MainPokedexView.homeButton(e -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        pokemonWelcomePanel.add(btnHome);
        add(pokemonWelcomePanel);

        // Only show main panel when "View All Pokémon" is pressed
        pokemonViewBtn.addActionListener(e -> {
            remove(pokemonWelcomePanel);
            showViewAllPokemons(onHome);
            revalidate();
            repaint();
        });

        // Only show add Pokémon panel when "Add New Pokémon" is pressed
        pokemonAddBtn.addActionListener(e -> {
            remove(pokemonWelcomePanel);
            showAddPokemon(onHome);
            revalidate();
            repaint();
        });
    }

    /**
     * Displays the main Pokémon view panel, showing details, evolution chain,
     * scrollable list, and search controls.
     *
     * @param onHome Runnable callback to return to the home/main menu.
     */
    private void showViewAllPokemons(Runnable onHome) {
        this.pokedex = controller.getPokedex();
        GUIUtils.removeAllPanels(pokemonMainPanel);

        // Create the main panel for viewing Pokémon
        pokemonMainPanel = new JPanel(null);
        pokemonMainPanel.setOpaque(false);
        pokemonMainPanel.setBounds(0, 0, 901, 706);

        // Pokemon details labels
        pokemonNameLabel = new JLabel();
        pokemonNameLabel.setFont(new Font("Consolas", Font.BOLD, 20));
        pokemonNameLabel.setBounds(40, 39, 300, 40);
        pokemonMainPanel.add(pokemonNameLabel);

        pokemonLevelLabel = new JLabel();
        pokemonLevelLabel.setFont(new Font("Consolas", Font.BOLD, 20));
        pokemonLevelLabel.setBounds(40, 39, 343, 40);
        pokemonLevelLabel.setHorizontalAlignment(JLabel.RIGHT);
        pokemonMainPanel.add(pokemonLevelLabel);

        hpLabel = new JLabel();
        hpLabel.setFont(new Font("Consolas", Font.BOLD, 17));
        hpLabel.setBounds(508, 35, 500, 30);
        pokemonMainPanel.add(hpLabel);

        atkLabel = new JLabel();
        atkLabel.setFont(new Font("Consolas", Font.BOLD, 17));
        atkLabel.setBounds(508, 60, 500, 30);
        pokemonMainPanel.add(atkLabel);

        defLabel = new JLabel();
        defLabel.setFont(new Font("Consolas", Font.BOLD, 17));
        defLabel.setBounds(508, 85, 500, 30);
        pokemonMainPanel.add(defLabel);

        spdLabel = new JLabel();
        spdLabel.setFont(new Font("Consolas", Font.BOLD, 17));
        spdLabel.setBounds(508, 110, 500, 30);
        pokemonMainPanel.add(spdLabel);

        typeLabel1 = new JLabel();
        typeLabel1.setFont(new Font("Consolas", Font.BOLD, 30));
        typeLabel1.setBounds(700, 75, 150, 40);
        typeLabel1.setHorizontalAlignment(JLabel.CENTER);
        pokemonMainPanel.add(typeLabel1);

        typeLabel2 = new JLabel();
        typeLabel2.setFont(new Font("Consolas", Font.BOLD, 30));
        typeLabel2.setBounds(700, 110, 150, 40);
        typeLabel2.setHorizontalAlignment(JLabel.CENTER);
        pokemonMainPanel.add(typeLabel2);

        pokemonImageLabel = new JLabel();
        pokemonImageLabel.setBounds(35, 90, 355, 355);
        pokemonImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pokemonImageLabel.setVerticalAlignment(SwingConstants.CENTER);
        pokemonMainPanel.add(pokemonImageLabel);

        // Evolution panel for displaying evolution chain
        evolutionPanel = new JPanel(null);
        evolutionPanel.setOpaque(false);
        evolutionPanel.setBounds(35, 180, 900, 170);

        boxBase = createEvolutionBox(null);
        boxBase.setBounds(410, 0, 500, 170);
        evolutionPanel.add(boxBase);

        boxSecond = createEvolutionBox(null);
        boxSecond.setBounds(535, 0, 500, 170);
        evolutionPanel.add(boxSecond);

        boxThird = createEvolutionBox(null);
        boxThird.setBounds(660, 0, 500, 170);
        evolutionPanel.add(boxThird);

        pokemonMainPanel.add(evolutionPanel);

        // Next and Previous buttons for navigating Pokémon
        JButton pokeNextBtn = GUIUtils.createNavButton("Next", 755, 559, 75, 75, e -> showNextPokemon());
        pokemonMainPanel.add(pokeNextBtn);

        JButton pokePrevBtn = GUIUtils.createNavButton("Previous", 520, 559, 75, 75, e -> showPreviousPokemon());
        pokemonMainPanel.add(pokePrevBtn);

        // Home button to return to the main menu
        JButton pokeMenuBtn = MainPokedexView.homeButton(e -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        pokemonMainPanel.add(pokeMenuBtn);

        // Add Back button to return to welcome panel
        JButton pokeBackBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, e -> {
            remove(pokemonMainPanel);
            showPokemonWelcomePanel(onHome);
            revalidate();
            repaint();
        });
        pokeBackBtn.setMargin(new Insets(0, 0, 0, 0));
        pokemonMainPanel.add(pokeBackBtn);

        // Scrollable panel for the list of Pokémon
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        updatePokemonLabels();

        GUIUtils.createLabeledScrollPanel(
                pokemonMainPanel,
                "List of Registered Pokémons",
                34, 507, 356, 20,
                37, 527, 347, 105,
                listPanel
        );

        GUIUtils.SearchFieldComponents search = GUIUtils.createSearchField(
                pokemonMainPanel,
                "Search Pokémon:",
                490, 442, 307, 50,
                560, 500, 180, 30,
                645, 565, 62, 43,
                "Search"
        );
        // Add each Pokémon's stats as a label
        updatePokemonListPanel(listPanel);

        // Add action listener for the search button
        search.button.addActionListener(e -> {
            String query = search.field.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a Pokémon name or number.");
                return;
            }
            Pokemon found = null;
            try {
                int num = Integer.parseInt(query);
                for (Pokemon p : pokedex) {
                    if (p.getPokedexNumber() == num) {
                        found = p;
                        break;
                    }
                }
            } catch (NumberFormatException ex) {
                for (Pokemon p : pokedex) {
                    if (p.getPokemonName().equalsIgnoreCase(query)) {
                        found = p;
                        break;
                    }
                }
            }
            if (found != null) {
                currentPokemonIndex = pokedex.indexOf(found);
                updatePokemonLabels();
                JOptionPane.showMessageDialog(this, "Pokémon found: " + found.getPokemonName());
            } else {
                JOptionPane.showMessageDialog(this, "Pokémon not found.");
            }
        });
        add(pokemonMainPanel);
    }

    /**
     * Displays the add Pokémon form, prompting for each field step-by-step with
     * validation.
     *
     * @param onHome Runnable callback to return to the home/main menu.
     */
    private void showAddPokemon(Runnable onHome) {

        JPanel addPanel = new JPanel(null);
        addPanel.setOpaque(false);
        addPanel.setBounds(0, 0, 901, 706);

        // Vertical layout for base stats fields
        JLabel baseHpLabel = new JLabel("Base HP:");
        baseHpLabel.setFont(new Font("Consolas", Font.BOLD, 15));
        baseHpLabel.setBounds(50, 180, 120, 20);
        baseHpLabel.setVisible(false);
        addPanel.add(baseHpLabel);

        JTextField baseHpField = new JTextField();
        baseHpField.setBounds(50, 205, 320, 30);
        baseHpField.setVisible(false);
        addPanel.add(baseHpField);

        JLabel baseAtkLabel = new JLabel("Base Attack:");
        baseAtkLabel.setFont(new Font("Consolas", Font.BOLD, 15));
        baseAtkLabel.setBounds(50, 240, 120, 20);
        baseAtkLabel.setVisible(false);
        addPanel.add(baseAtkLabel);

        JTextField baseAtkField = new JTextField();
        baseAtkField.setBounds(50, 265, 320, 30);
        baseAtkField.setVisible(false);
        addPanel.add(baseAtkField);

        JLabel baseDefLabel = new JLabel("Base Defense:");
        baseDefLabel.setFont(new Font("Consolas", Font.BOLD, 15));
        baseDefLabel.setBounds(50, 300, 120, 20);
        baseDefLabel.setVisible(false);
        addPanel.add(baseDefLabel);

        JTextField baseDefField = new JTextField();
        baseDefField.setBounds(50, 325, 320, 30);
        baseDefField.setVisible(false);
        addPanel.add(baseDefField);

        JLabel baseSpdLabel = new JLabel("Base Speed:");
        baseSpdLabel.setFont(new Font("Consolas", Font.BOLD, 15));
        baseSpdLabel.setBounds(50, 360, 120, 20);
        baseSpdLabel.setVisible(false);
        addPanel.add(baseSpdLabel);

        JTextField baseSpdField = new JTextField();
        baseSpdField.setBounds(50, 385, 320, 30);
        baseSpdField.setVisible(false);
        addPanel.add(baseSpdField);

        // Vertical layout for evolution fields
        JLabel evolvesFromLabel = new JLabel("Evolves From:");
        evolvesFromLabel.setFont(new Font("Consolas", Font.BOLD, 15));
        evolvesFromLabel.setBounds(50, 200, 120, 20);
        evolvesFromLabel.setVisible(false);
        addPanel.add(evolvesFromLabel);

        JTextField evolvesFromField = new JTextField();
        evolvesFromField.setBounds(50, 225, 320, 30);
        evolvesFromField.setVisible(false);
        addPanel.add(evolvesFromField);

        JLabel evolvesToLabel = new JLabel("Evolves To:");
        evolvesToLabel.setFont(new Font("Consolas", Font.BOLD, 15));
        evolvesToLabel.setBounds(50, 260, 120, 20);
        evolvesToLabel.setVisible(false);
        addPanel.add(evolvesToLabel);

        JTextField evolvesToField = new JTextField();
        evolvesToField.setBounds(50, 285, 320, 30);
        evolvesToField.setVisible(false);
        addPanel.add(evolvesToField);

        JLabel evoLevelLabel = new JLabel("Evo Level:");
        evoLevelLabel.setFont(new Font("Consolas", Font.BOLD, 15));
        evoLevelLabel.setBounds(50, 320, 120, 20);
        evoLevelLabel.setVisible(false);
        addPanel.add(evoLevelLabel);

        JTextField evoLevelField = new JTextField();
        evoLevelField.setBounds(50, 345, 320, 30);
        evoLevelField.setVisible(false);
        addPanel.add(evoLevelField);
        GUIUtils.removeAllPanels(pokemonMainPanel);

        String[] validTypes = EnhancedPokedexMVC.VALID_POKEMON_TYPES;

        // Title label
        GUIUtils.addWelcomeLabel(addPanel, "Add New Pokémon", 35, 39, 353, 40);

        // Question label and answer field
        JLabel pokeQuestionLabel = new JLabel();
        pokeQuestionLabel.setFont(new Font("Consolas", Font.PLAIN, 16));
        pokeQuestionLabel.setBounds(49, 160, 334, 500);
        pokeQuestionLabel.setVerticalAlignment(SwingConstants.TOP);
        pokeQuestionLabel.setHorizontalAlignment(SwingConstants.LEFT);
        addPanel.add(pokeQuestionLabel);

        JTextField pokeAnswerField = new JTextField();
        pokeAnswerField.setBounds(50, 180, 300, 30);
        addPanel.add(pokeAnswerField);

        // Type fields for simultaneous input
        JTextField type1Field = new JTextField();
        type1Field.setBounds(50, 180, 140, 30);
        type1Field.setVisible(false);
        addPanel.add(type1Field);

        JTextField type2Field = new JTextField();
        type2Field.setBounds(210, 180, 140, 30);
        type2Field.setVisible(false);
        addPanel.add(type2Field);

        JLabel type2Label = new JLabel("Type 2 (optional):");
        type2Label.setFont(new Font("Consolas", Font.BOLD, 15));
        type2Label.setBounds(210, 160, 190, 20);
        type2Label.setVisible(false);
        addPanel.add(type2Label);

        // Enter button to submit answers
        JButton pokeEnterButton = GUIUtils.createNavButton("Enter", 787, 345, 67, 35, null);
        addPanel.add(pokeEnterButton);

        // Valid types label (bold only the label)
        JLabel validTypesLabel = new JLabel();
        validTypesLabel.setFont(new Font("Consolas", Font.BOLD, 15));
        validTypesLabel.setBounds(49, 230, 334, 500);
        validTypesLabel.setVisible(false);
        validTypesLabel.setHorizontalAlignment(SwingConstants.LEFT);
        validTypesLabel.setVerticalAlignment(SwingConstants.TOP);
        addPanel.add(validTypesLabel);
        add(addPanel);

        // Instruction label
        JLabel pokeInstrucLabel = new JLabel(
                "<html>Enter the following variables and press the <b>\"enter\"</b> button on the right side.</html>"
        );
        pokeInstrucLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        pokeInstrucLabel.setBounds(49, 105, 334, 500);
        pokeInstrucLabel.setVerticalAlignment(SwingConstants.TOP);
        pokeInstrucLabel.setHorizontalAlignment(SwingConstants.LEFT);
        addPanel.add(pokeInstrucLabel);

        String[] questions = {
            "<html><b>Pokédex Number:</b></html>",
            "<html><b>Name:</b></html>",
            "<html><b>Pokémon Types:</b></html>",
            "<html><b>Evolution Questions (Enter if none):</b></html>",
            "<html><b>Evolves To (Enter if none):</b></html>",
            "<html><b>Evolution Level (Enter if none):</b></html>",
            "<html><b>Base Stats (Must be greater than 0):</b></html>",
            "<html><b>Base Attack:</b></html>",
            "<html><b>Base Defense:</b></html>",
            "<html><b>Base Speed:</b></html>"};

        pokeQuestionLabel.setText(questions[0]);
        Object[] answers = new Object[questions.length + 1]; // answers[2]=type1, answers[3]=type2
        int[] current = {0};

        updateValidTypesLabel(validTypesLabel, current[0], validTypes);

        pokeEnterButton.addActionListener(e -> {
            if (current[0] == 2) {
                // Show both type fields
                String type1Input = type1Field.getText().trim();
                String type2Input = type2Field.getText().trim();
                String canonicalType1 = controller.getCanonicalType(type1Input, validTypes);
                if (canonicalType1 == null) {
                    JOptionPane.showMessageDialog(this, "Invalid Type 1. Allowed types are:\n" + String.join(", ", validTypes));
                    return;
                }
                answers[2] = canonicalType1;
                if (type2Input.isEmpty()) {
                    answers[3] = "";
                } else {
                    String canonicalType2 = controller.getCanonicalType(type2Input, validTypes);
                    if (canonicalType2 == null) {
                        JOptionPane.showMessageDialog(this, "Invalid Type 2. Allowed types are:\n" + String.join(", ", validTypes));
                        return;
                    }
                    answers[3] = canonicalType2;
                }
                current[0] += 1;
                type1Field.setText("");
                type2Field.setText("");
                type1Field.setVisible(false);
                type2Field.setVisible(false);
                type2Label.setVisible(false);
                pokeAnswerField.setVisible(true);
            } else if (current[0] == 3) {
                // Show all evolves fields together vertically, hide valid types label
                validTypesLabel.setVisible(false);
                String evolvesFromInput = evolvesFromField.getText().trim();
                String evolvesToInput = evolvesToField.getText().trim();
                String evoLevelInput = evoLevelField.getText().trim();
                Integer evolvesFrom = null, evolvesTo = null, evoLevel = null;
                if (!evolvesFromInput.isEmpty()) {
                    try {
                        evolvesFrom = Integer.parseInt(evolvesFromInput);
                        if (!controller.isPokedexNumberExists(evolvesFrom)) {
                            JOptionPane.showMessageDialog(this, "Referenced Pokédex number for Evolves From does not exist.");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid number for Evolves From.");
                        return;
                    }
                }
                if (!evolvesToInput.isEmpty()) {
                    try {
                        evolvesTo = Integer.parseInt(evolvesToInput);
                        // No existence check for Evolves To (can be new)
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid number for Evolves To.");
                        return;
                    }
                }
                if (!evoLevelInput.isEmpty()) {
                    try {
                        evoLevel = Integer.parseInt(evoLevelInput);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid number for Evo Level.");
                        return;
                    }
                }
                answers[4] = evolvesFrom;
                answers[5] = evolvesTo;
                answers[6] = evoLevel;
                current[0] += 3;
                evolvesFromField.setText("");
                evolvesToField.setText("");
                evoLevelField.setText("");
                evolvesFromField.setVisible(false);
                evolvesToField.setVisible(false);
                evoLevelField.setVisible(false);
                evolvesFromLabel.setVisible(false);
                evolvesToLabel.setVisible(false);
                evoLevelLabel.setVisible(false);
                // Show base stats fields next
                baseHpLabel.setVisible(true);
                baseHpField.setVisible(true);
                baseAtkLabel.setVisible(true);
                baseAtkField.setVisible(true);
                baseDefLabel.setVisible(true);
                baseDefField.setVisible(true);
                baseSpdLabel.setVisible(true);
                baseSpdField.setVisible(true);
            } else if (current[0] == 6) {
                // Collect base stats from vertical fields
                String hpInput = baseHpField.getText().trim();
                String atkInput = baseAtkField.getText().trim();
                String defInput = baseDefField.getText().trim();
                String spdInput = baseSpdField.getText().trim();
                double hp, atk, def, spd;
                try {
                    if (hpInput.isEmpty()) {
                        throw new NumberFormatException();
                    }
                    hp = Double.parseDouble(hpInput);
                    if (!controller.isValidStat(hp)) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Base HP. Must be a number > 0.");
                    return;
                }
                try {
                    atk = Double.parseDouble(atkInput);
                    if (!controller.isValidStat(atk)) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Base Attack. Must be a number > 0.");
                    return;
                }
                try {
                    def = Double.parseDouble(defInput);
                    if (!controller.isValidStat(def)) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Base Defense. Must be a number > 0.");
                    return;
                }
                try {
                    spd = Double.parseDouble(spdInput);
                    if (!controller.isValidStat(spd)) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Base Speed. Must be a number > 0.");
                    return;
                }
                answers[7] = hp;
                answers[8] = atk;
                answers[9] = def;
                answers[10] = spd;
                baseHpField.setText("");
                baseAtkField.setText("");
                baseDefField.setText("");
                baseSpdField.setText("");
                baseHpLabel.setVisible(false);
                baseHpField.setVisible(false);
                baseAtkLabel.setVisible(false);
                baseAtkField.setVisible(false);
                baseDefLabel.setVisible(false);
                baseDefField.setVisible(false);
                baseSpdLabel.setVisible(false);
                baseSpdField.setVisible(false);
                // Skip the individual stat questions and go to the end
                current[0] = 11;
            } else {
                String input = pokeAnswerField.getText().trim();
                switch (current[0]) {
                    case 0 -> { // Pokédex Number
                        try {
                            int num = Integer.parseInt(input);
                            if (controller.isPokedexNumberExists(num)) {
                                JOptionPane.showMessageDialog(this, "Pokédex Number already exists!");
                                return;
                            }
                            answers[0] = num;
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Invalid Pokédex Number.");
                            return;
                        }
                    }
                    case 1 -> { // Name
                        if (input.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Name cannot be empty.");
                            return;
                        }
                        if (controller.isPokemonNameExists(input)) {
                            JOptionPane.showMessageDialog(this, "Pokémon Name already exists!");
                            return;
                        }
                        answers[1] = formatPokemonName(input);
                    }
                }
                if (current[0] == 7) {
                    // Skip base stats single field input, go to next
                    current[0]++;
                } else {
                    current[0]++;
                }
                pokeAnswerField.setText("");
            }
            if (current[0] < questions.length) {
                pokeQuestionLabel.setText(questions[current[0]]);
                updateValidTypesLabel(validTypesLabel, current[0], validTypes);
                if (current[0] == 2) {
                    pokeAnswerField.setVisible(false);
                    type1Field.setVisible(true);
                    type2Field.setVisible(true);
                    type2Label.setVisible(true);
                } else if (current[0] == 3) {
                    pokeAnswerField.setVisible(false);
                    type1Field.setVisible(false);
                    type2Field.setVisible(false);
                    type2Label.setVisible(false);
                    evolvesFromField.setVisible(true);
                    evolvesToField.setVisible(true);
                    evoLevelField.setVisible(true);
                    evolvesFromLabel.setVisible(true);
                    evolvesToLabel.setVisible(true);
                    evoLevelLabel.setVisible(true);
                    validTypesLabel.setVisible(false);
                } else if (current[0] == 6) {
                    // Show base stats fields vertically
                    pokeAnswerField.setVisible(false);
                    baseHpLabel.setVisible(true);
                    baseHpField.setVisible(true);
                    baseAtkLabel.setVisible(true);
                    baseAtkField.setVisible(true);
                    baseDefLabel.setVisible(true);
                    baseDefField.setVisible(true);
                    baseSpdLabel.setVisible(true);
                    baseSpdField.setVisible(true);
                } else {
                    pokeAnswerField.setVisible(true);
                    type1Field.setVisible(false);
                    type2Field.setVisible(false);
                    type2Label.setVisible(false);
                    evolvesFromField.setVisible(false);
                    evolvesToField.setVisible(false);
                    evoLevelField.setVisible(false);
                    evolvesFromLabel.setVisible(false);
                    evolvesToLabel.setVisible(false);
                    evoLevelLabel.setVisible(false);
                    baseHpLabel.setVisible(false);
                    baseHpField.setVisible(false);
                    baseAtkLabel.setVisible(false);
                    baseAtkField.setVisible(false);
                    baseDefLabel.setVisible(false);
                    baseDefField.setVisible(false);
                    baseSpdLabel.setVisible(false);
                    baseSpdField.setVisible(false);
                }
            } else {
                controller.addPokemon(
                        (int) answers[0], // pokedexNumber
                        (String) answers[1], // name
                        (String) answers[2], // type1
                        (String) answers[3], // type2
                        1, // baseLevel
                        (Integer) answers[4], // evolvesFrom
                        (Integer) answers[5], // evolvesTo
                        (Integer) answers[6], // evolutionLevel
                        (double) answers[7], // hp
                        (double) answers[8], // attack
                        (double) answers[9], // defense
                        (double) answers[10] // speed
                );
                JOptionPane.showMessageDialog(this, "Pokémon added!");
                remove(addPanel);
                showPokemonWelcomePanel(onHome);
            }
        });
    }

    /**
     * Advances to the next Pokémon in the list and updates the view.
     */
    public void showNextPokemon() {
        if (pokedex.isEmpty()) {
            return;
        }
        currentPokemonIndex = (currentPokemonIndex + 1) % pokedex.size();
        updatePokemonLabels();
    }

    /**
     * Goes to the previous Pokémon in the list and updates the view.
     */
    public void showPreviousPokemon() {
        if (pokedex.isEmpty()) {
            return;
        }
        currentPokemonIndex = (currentPokemonIndex - 1 + pokedex.size()) % pokedex.size();
        updatePokemonLabels();
    }

    /**
     * Updates the labels and evolution chain display for the currently selected
     * Pokémon. Handles image, stats, and evolution chain logic.
     */
    private void updatePokemonLabels() {
        if (pokedex.isEmpty()) {
            pokemonNameLabel.setText("No Pokémon");
            pokemonLevelLabel.setText("Lv. N/A");
            hpLabel.setText("HP.......... N/A");
            atkLabel.setText("Atk......... N/A");
            defLabel.setText("Def......... N/A");
            spdLabel.setText("Spd......... N/A");
            typeLabel1.setText("Type 1..... N/A");
            typeLabel2.setText("Type 2..... N/A");
            updateEvolutionBoxes(null, null, null);
        } else {
            Pokemon p = pokedex.get(currentPokemonIndex);
            pokemonNameLabel.setText(p.getPokemonName());
            pokemonLevelLabel.setText("No. " + p.getPokedexNumber());
            hpLabel.setText("HP.......... " + p.getHp());
            atkLabel.setText("Atk......... " + p.getAttack());
            defLabel.setText("Def......... " + p.getDefense());
            spdLabel.setText("Spd......... " + p.getSpeed());
            typeLabel1.setText(p.getPokemonType1());
            if (p.getPokemonType2() == null || p.getPokemonType2().isEmpty()) {
                typeLabel2.setText("N/A");
            } else {
                typeLabel2.setText(p.getPokemonType2());
            }

            int num = p.getPokedexNumber();
            String imgPath;

            if (num >= 1 && num <= 55) {
                imgPath = "src/util/PokemonSprites/" + num + ".png";
            } else {
                imgPath = "src/util/PokemonSprites/noData.png";
            }

            ImageIcon icon = new ImageIcon(imgPath);
            Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            pokemonImageLabel.setIcon(new ImageIcon(img));

            // Get the evolution chain for the current Pokémon
            Pokemon base = null;
            Pokemon secondEvo = null;
            Pokemon thirdEvo = null;

            Pokemon[] chain = controller.getEvolutionChain(p);
            base = chain[0];
            secondEvo = chain[1];
            thirdEvo = chain[2];
            updateEvolutionBoxes(base, secondEvo, thirdEvo);
        }
    }

    /**
     * Updates the evolution chain display with the given base, second, and
     * third evolution Pokémon.
     *
     * @param base The base form Pokémon.
     * @param secondEvo The first evolution Pokémon.
     * @param thirdEvo The second evolution Pokémon.
     */
    private void updateEvolutionBoxes(Pokemon base, Pokemon secondEvo, Pokemon thirdEvo) {
        // Clear existing components in the evolution boxes
        boxBase.removeAll();
        boxSecond.removeAll();
        boxThird.removeAll();

        // Create new evolution boxes for each Pokémon
        JPanel newBase = createEvolutionBox(base);
        JPanel newSecond = createEvolutionBox(secondEvo);
        JPanel newThird = createEvolutionBox(thirdEvo);

        // Copy components from new panels to the existing ones
        for (Component c : newBase.getComponents()) {
            boxBase.add(c);
        }
        for (Component c : newSecond.getComponents()) {
            boxSecond.add(c);
        }
        for (Component c : newThird.getComponents()) {
            boxThird.add(c);
        }

        // Revalidate and repaint the boxes to reflect changes
        boxBase.revalidate();
        boxBase.repaint();
        boxSecond.revalidate();
        boxSecond.repaint();
        boxThird.revalidate();
        boxThird.repaint();
    }

    /**
     * Creates a JPanel representing a single evolution box with image and name.
     *
     * @param p The Pokémon to display in the box, or null for "No Data".
     * @return JPanel containing the evolution box.
     */
    private JPanel createEvolutionBox(Pokemon p) {
        JPanel box = new JPanel(null);
        box.setOpaque(false);
        box.setBounds(0, 0, 220, 170);

        // Create image and name labels for the Pokémon
        JLabel imgLabel = new JLabel();
        imgLabel.setBounds(10, 10, 200, 120);
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setVerticalAlignment(SwingConstants.CENTER);

        JLabel nameLabel = new JLabel();
        nameLabel.setFont(new Font("Consolas", Font.BOLD, 13));
        nameLabel.setBounds(7, 127, 200, 30);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setVerticalAlignment(SwingConstants.CENTER);

        /**
         * Sets the image and name for the evolution box. If the Pokémon is
         * null, displays "No Data" and the default image. Otherwise, shows the
         * Pokémon's image and name.
         */
        if (p != null) {
            int num = p.getPokedexNumber();
            String imgPath = (num >= 1 && num <= 55)
                    ? "src/util/PokemonSprites/" + num + ".png"
                    : "src/util/PokemonSprites/noData.png";
            ImageIcon icon = new ImageIcon(imgPath);
            Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(img));
            nameLabel.setText("#" + num + " " + p.getPokemonName());
        } else {
            imgLabel.setIcon(new ImageIcon(new ImageIcon("src/util/PokemonSprites/noData.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
            nameLabel.setText("No Data");
        }

        box.add(imgLabel);
        box.add(nameLabel);
        return box;
    }

    // Helper method for name formatting (title case for multi-word names)
    private String formatPokemonName(String input) {
        String[] words = input.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    sb.append(word.substring(1).toLowerCase());
                }
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    private void updateValidTypesLabel(JLabel validTypesLabel, int currentIndex, String[] validTypes) {
        if (currentIndex == 2 || currentIndex == 3) {
            validTypesLabel.setText("<html>Valid types: <b>" + String.join(", ", validTypes) + "</b></html>");
            validTypesLabel.setVisible(true);
        } else {
            validTypesLabel.setVisible(false);
        }
    }

    private void updatePokemonListPanel(JPanel listPanel) {
        listPanel.removeAll();
        for (Pokemon p : pokedex) {
            JLabel pokeLabel = GUIUtils.createPokemonInfoLabel(p);
            listPanel.add(pokeLabel);
        }
        listPanel.revalidate();
        listPanel.repaint();
    }
}
