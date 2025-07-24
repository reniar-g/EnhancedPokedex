package view;

import controller.PokemonController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import model.Pokemon;
import src.EnhancedPokedexMVC;

public class PokemonView extends JPanel {

    public static final Color POKEDEX_BLUE = new Color(10, 168, 255);
    public static final Color BUTTON_SHADOW = new Color(68, 95, 146);
    public static final Color POKEDEX_GREEN = new Color(202, 213, 181);
    Border buttonShadowBorder = BorderFactory.createLineBorder(BUTTON_SHADOW, 1);

    private List<Pokemon> pokedex;
    private int currentPokemonIndex = 0;
    private JLabel nameLabel, levelLabel, hpLabel, atkLabel, defLabel, spdLabel, typeLabel1, typeLabel2;

    private JPanel welcomePanel;
    private JPanel mainPanel;
    private JLabel pokemonImageLabel;
    private JPanel evolutionPanel;
    private JPanel boxBase, boxSecond, boxThird;

    private PokemonController controller;

    public PokemonView(PokemonController controller, Runnable onHome) {
        this.controller = controller;
        this.pokedex = controller.getPokedex();
        setLayout(null);
        setOpaque(false);
        showWelcomePanel(onHome);
    }

    private void showWelcomePanel(Runnable onHome) {
        welcomePanel = new JPanel(null);
        welcomePanel.setOpaque(false);
        welcomePanel.setBounds(0, 0, 901, 706);

        JLabel welcomeLabel = new JLabel("Manage Pokémons");
        welcomeLabel.setFont(new Font("Consolas", Font.BOLD, 27));
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setBounds(35, 39, 353, 40);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomePanel.add(welcomeLabel);

        JLabel welcomeDesc = new JLabel();
        welcomeDesc.setText(
                "<html><div style='text-align:justify;'>"
                + "<b>Welcome to the Pokémon Management System!</b><br><br>"
                + "Here you can add, view, and manage your Pokémon collection.<br><br>"
                + "Use the buttons to add, view, or search for Pokémons."
                + "</div></html>"
        );
        welcomeDesc.setFont(new Font("Consolas", Font.PLAIN, 16));
        welcomeDesc.setBounds(49, 105, 334, 500);
        welcomeDesc.setVerticalAlignment(SwingConstants.TOP);
        welcomeDesc.setOpaque(false);
        welcomePanel.add(welcomeDesc);

        // Add buttons
        JButton btnAdd = new JButton("Add New Pokémon");
        btnAdd.setFont(new Font("Consolas", Font.BOLD, 14));
        btnAdd.setBounds(493, 345, 140, 35);
        btnAdd.setBorder(buttonShadowBorder);
        btnAdd.setBackground(POKEDEX_BLUE);
        btnAdd.setMargin(new Insets(0, 0, 0, 0));
        welcomePanel.add(btnAdd);

        JButton btnViewAll = new JButton("View All Pokémon");
        btnViewAll.setFont(new Font("Consolas", Font.BOLD, 14));
        btnViewAll.setBounds(640, 345, 140, 35);
        btnViewAll.setBorder(buttonShadowBorder);
        btnViewAll.setBackground(POKEDEX_BLUE);
        btnViewAll.setMargin(new Insets(0, 0, 0, 0));
        welcomePanel.add(btnViewAll);

        JButton btnHome = MainPokedexView.createHomeButton(e -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        welcomePanel.add(btnHome);

        add(welcomePanel);

        // Only show main panel when "View All Pokémon" is pressed
        btnViewAll.addActionListener(e -> {
            remove(welcomePanel);
            showViewAllPokemons(onHome);
            revalidate();
            repaint();
        });

        btnAdd.addActionListener(e -> {
            remove(welcomePanel);
            showAddPokemon(onHome);
            revalidate();
            repaint();
        });
    }

    private void showViewAllPokemons(Runnable onHome) {
        mainPanel = new JPanel(null);
        mainPanel.setOpaque(false);
        mainPanel.setBounds(0, 0, 901, 706);

        // Create and add labels for Pokémon info
        nameLabel = new JLabel();
        nameLabel.setFont(new Font("Consolas", Font.BOLD, 20));
        nameLabel.setBounds(40, 39, 300, 40);
        mainPanel.add(nameLabel);

        levelLabel = new JLabel();
        levelLabel.setFont(new Font("Consolas", Font.BOLD, 20));
        levelLabel.setBounds(40, 39, 343, 40);
        levelLabel.setHorizontalAlignment(JLabel.RIGHT);
        mainPanel.add(levelLabel);

        hpLabel = new JLabel();
        hpLabel.setFont(new Font("Consolas", Font.BOLD, 17));
        hpLabel.setBounds(508, 35, 500, 30);
        mainPanel.add(hpLabel);

        atkLabel = new JLabel();
        atkLabel.setFont(new Font("Consolas", Font.BOLD, 17));
        atkLabel.setBounds(508, 60, 500, 30);
        mainPanel.add(atkLabel);

        defLabel = new JLabel();
        defLabel.setFont(new Font("Consolas", Font.BOLD, 17));
        defLabel.setBounds(508, 85, 500, 30);
        mainPanel.add(defLabel);

        spdLabel = new JLabel();
        spdLabel.setFont(new Font("Consolas", Font.BOLD, 17));
        spdLabel.setBounds(508, 110, 500, 30);
        mainPanel.add(spdLabel);

        typeLabel1 = new JLabel();
        typeLabel1.setFont(new Font("Consolas", Font.BOLD, 30));
        typeLabel1.setBounds(700, 75, 150, 40);
        typeLabel1.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(typeLabel1);

        typeLabel2 = new JLabel();
        typeLabel2.setFont(new Font("Consolas", Font.BOLD, 30));
        typeLabel2.setBounds(700, 110, 150, 40);
        typeLabel2.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(typeLabel2);

        pokemonImageLabel = new JLabel();
        pokemonImageLabel.setBounds(35, 90, 355, 355);
        pokemonImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pokemonImageLabel.setVerticalAlignment(SwingConstants.CENTER);
        mainPanel.add(pokemonImageLabel);

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

        mainPanel.add(evolutionPanel);

        updatePokemonLabels();

        // --- Evolution Chain Panel ---
        JPanel evolutionPanel = new JPanel(null);
        evolutionPanel.setOpaque(false);
        evolutionPanel.setBounds(35, 180, 800, 170);

        // Next button
        JButton btnNext = new JButton("Next");
        btnNext.setFont(new Font("Consolas", Font.BOLD, 14));
        btnNext.setBounds(755, 559, 75, 75);
        btnNext.setBackground(POKEDEX_BLUE);
        btnNext.setForeground(Color.BLACK);
        btnNext.setBorder(buttonShadowBorder);
        btnNext.setOpaque(true);
        btnNext.addActionListener(e -> showNextPokemon());
        mainPanel.add(btnNext);

        // Back Button
        JButton btnBack = new JButton("Previous");
        btnBack.setFont(new Font("Consolas", Font.BOLD, 14));
        btnBack.setBounds(520, 559, 75, 75);
        btnBack.setBackground(POKEDEX_BLUE);
        btnBack.setForeground(Color.BLACK);
        btnBack.setOpaque(true);
        btnBack.setBorder(buttonShadowBorder);
        btnBack.addActionListener(e -> showPreviousPokemon());
        mainPanel.add(btnBack);

        // Add Home button
        JButton btnHome = new JButton("Home");
        btnHome.setFont(new Font("Consolas", Font.BOLD, 14));
        btnHome.setBounds(787, 387, 67, 35);
        btnHome.setBorder(buttonShadowBorder);
        btnHome.setBackground(POKEDEX_BLUE);
        btnHome.setMargin(new Insets(0, 0, 0, 0));
        btnHome.addActionListener(e -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        mainPanel.add(btnHome);

        // Add Back button to return to welcome panel
        JButton btnBackToMain = new JButton("Back");
        btnBackToMain.setFont(new Font("Consolas", Font.BOLD, 14));
        btnBackToMain.setBounds(787, 345, 67, 35);
        btnBackToMain.setBorder(buttonShadowBorder);
        btnBackToMain.setBackground(POKEDEX_BLUE);
        btnBackToMain.setMargin(new Insets(0, 0, 0, 0));
        btnBackToMain.addActionListener(e -> {
            remove(mainPanel);
            showWelcomePanel(onHome);
            revalidate();
            repaint();
        });
        mainPanel.add(btnBackToMain);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

// Add each Pokémon's stats as a label
        for (Pokemon p : pokedex) {
            JLabel pokeLabel = new JLabel(
                    "<html><div>"
                    + "<b>" + "#" + p.getPokedexNumber() + " " + p.getPokemonName() + "</b><br>"
                    + "Level: " + p.getBaseLevel() + "<br>"
                    + "HP: " + p.getHp()
                    + " | Atk: " + p.getAttack()
                    + " | Def: " + p.getDefense()
                    + " | Spd: " + p.getSpeed() + "<br>"
                    + "Type 1: " + p.getPokemonType1()
                    + (p.getPokemonType2() != null && !p.getPokemonType2().isEmpty()
                    ? " | Type 2: " + p.getPokemonType2()
                    : "")
                    + "<br><br>"
                    + "</div></html>"
            );
            pokeLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
            pokeLabel.setHorizontalAlignment(SwingConstants.LEFT);
            listPanel.add(pokeLabel);
        }

        JLabel registeredLabel = new JLabel("List of Registered Pokémons");
        registeredLabel.setFont(new Font("Consolas", Font.BOLD, 14));
        registeredLabel.setOpaque(true);
        registeredLabel.setBackground(POKEDEX_GREEN);
        registeredLabel.setBorder(buttonShadowBorder);
        registeredLabel.setForeground(Color.BLACK);
        registeredLabel.setBounds(34, 507, 356, 20);
        registeredLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registeredLabel.setVerticalAlignment(SwingConstants.CENTER);
        mainPanel.add(registeredLabel);

        // Put the list panel in a scroll pane
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBounds(37, 527, 347, 105);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        mainPanel.add(scrollPane);
        add(mainPanel);

        JLabel searchLabel = new JLabel("Search Pokémon:");
        searchLabel.setFont(new Font("Consolas", Font.BOLD, 25));
        searchLabel.setBounds(490, 442, 307, 50);
        searchLabel.setBorder(buttonShadowBorder);
        searchLabel.setOpaque(true);
        searchLabel.setBackground(POKEDEX_GREEN);
        searchLabel.setHorizontalAlignment(SwingConstants.CENTER);
        searchLabel.setVerticalAlignment(SwingConstants.CENTER);
        mainPanel.add(searchLabel);

        JTextField searchField = new JTextField();
        searchField.setBounds(560, 500, 180, 30);
        searchField.setHorizontalAlignment(SwingConstants.LEFT);
        searchField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        mainPanel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Consolas", Font.BOLD, 14));
        searchButton.setBounds(645, 565, 62, 43);
        searchButton.setOpaque(false);
        searchButton.setBackground(POKEDEX_GREEN);
        searchButton.setBorder(buttonShadowBorder);
        searchButton.setHorizontalAlignment(SwingConstants.CENTER);
        searchButton.setVerticalAlignment(SwingConstants.CENTER);
        mainPanel.add(searchButton);

        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a Pokémon name or number.");
                return;
            }
            Pokemon found = null;
            // Try to search by number first
            try {
                int num = Integer.parseInt(query);
                for (Pokemon p : pokedex) {
                    if (p.getPokedexNumber() == num) {
                        found = p;
                        break;
                    }
                }
            } catch (NumberFormatException ex) {
                // Not a number, search by name
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

        add(mainPanel);

    }

    private void updatePokemonLabels() {
        if (pokedex.isEmpty()) {
            nameLabel.setText("No Pokémon");
            levelLabel.setText("Lv. N/A");
            hpLabel.setText("HP.......... N/A");
            atkLabel.setText("Atk......... N/A");
            defLabel.setText("Def......... N/A");
            spdLabel.setText("Spd......... N/A");
            typeLabel1.setText("Type 1..... N/A");
            typeLabel2.setText("Type 2..... N/A");
            // Update evolution boxes to show "No Data"
            updateEvolutionBoxes(null, null, null);
        } else {
            Pokemon p = pokedex.get(currentPokemonIndex);
            nameLabel.setText(p.getPokemonName());
            levelLabel.setText("No. " + p.getPokedexNumber());
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
            // Set Pokémon image
            int num = p.getPokedexNumber();
            String imgPath;
            if (num >= 1 && num <= 28) {
                imgPath = "src/util/PokemonSprites/" + num + ".png";
            } else {
                imgPath = "src/util/PokemonSprites/noData.png";
            }
            ImageIcon icon = new ImageIcon(imgPath);
            Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            pokemonImageLabel.setIcon(new ImageIcon(img));

            // Evolution logic
            Pokemon base = null;
            Pokemon secondEvo = null;
            Pokemon thirdEvo = null;

            // Find base form by tracing evolvesFrom
            Pokemon current = p;
            while (current.getEvolvesFrom() != null && current.getEvolvesFrom() != 0) {
                for (Pokemon poke : pokedex) {
                    if (poke.getPokedexNumber() == current.getEvolvesFrom()) {
                        current = poke;
                        break;
                    }
                }
            }
            base = current;

            // Find second and third evolutions from base
            if (base != null && base.getEvolvesTo() != null && base.getEvolvesTo() != 0) {
                for (Pokemon poke : pokedex) {
                    if (poke.getPokedexNumber() == base.getEvolvesTo()) {
                        secondEvo = poke;
                        break;
                    }
                }
                if (secondEvo != null && secondEvo.getEvolvesTo() != null && secondEvo.getEvolvesTo() != 0) {
                    for (Pokemon poke : pokedex) {
                        if (poke.getPokedexNumber() == secondEvo.getEvolvesTo()) {
                            thirdEvo = poke;
                            break;
                        }
                    }
                }
            }

            updateEvolutionBoxes(base, secondEvo, thirdEvo);
        }
    }

    public void showNextPokemon() {
        if (pokedex.isEmpty()) {
            return;
        }
        currentPokemonIndex = (currentPokemonIndex + 1) % pokedex.size();
        updatePokemonLabels();
    }

    public void showPreviousPokemon() {
        if (pokedex.isEmpty()) {
            return;
        }
        currentPokemonIndex = (currentPokemonIndex - 1 + pokedex.size()) % pokedex.size();
        updatePokemonLabels();
    }

    private void showAddPokemon(Runnable onHome) {
        String[] validTypes = EnhancedPokedexMVC.VALID_POKEMON_TYPES;

        removeAllPanels();
        JPanel addPanel = new JPanel(null);
        addPanel.setOpaque(false);
        addPanel.setBounds(0, 0, 901, 706);

        JLabel titleLabel = new JLabel("Add New Pokémon");
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 20));
        titleLabel.setBounds(40, 39, 300, 40);
        addPanel.add(titleLabel);

        JLabel questionLabel = new JLabel();
        questionLabel.setFont(new Font("Consolas", Font.PLAIN, 16));
        questionLabel.setBounds(49, 160, 334, 500);
        questionLabel.setVerticalAlignment(SwingConstants.TOP);
        questionLabel.setHorizontalAlignment(SwingConstants.LEFT);
        addPanel.add(questionLabel);

        JTextField answerField = new JTextField();
        answerField.setBounds(50, 180, 300, 30);
        addPanel.add(answerField);

        JButton enterButton = new JButton("Enter");
        enterButton.setFont(new Font("Consolas", Font.BOLD, 14));
        enterButton.setBounds(787, 345, 67, 35);
        enterButton.setMargin(new Insets(0, 0, 0, 0));
        enterButton.setBorder(buttonShadowBorder);
        enterButton.setBackground(POKEDEX_BLUE);
        addPanel.add(enterButton);

        JLabel instructionLabel = new JLabel(
                "<html>Enter the following variables and press the <b>\"enter\"</b> button on the right side.</html>"
        );
        instructionLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        instructionLabel.setBounds(49, 105, 334, 500);
        instructionLabel.setVerticalAlignment(SwingConstants.TOP);
        instructionLabel.setHorizontalAlignment(SwingConstants.LEFT);
        addPanel.add(instructionLabel);

        JLabel validTypesLabel = new JLabel();
        validTypesLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        validTypesLabel.setBounds(49, 230, 334, 500);
        validTypesLabel.setVisible(false);
        validTypesLabel.setHorizontalAlignment(SwingConstants.LEFT);
        validTypesLabel.setVerticalAlignment(SwingConstants.TOP);
        addPanel.add(validTypesLabel);
        add(addPanel);

        String[] questions = {
            "<html><b>Pokédex Number:</b></html>",
            "<html><b>Name:</b></html>",
            "<html><b>Pokémon Type 1:</b></html>",
            "<html><b>Pokémon Type 2 (Enter if none):</b></html>",
            "<html><b>Evolves From (Enter if none):</b></html>",
            "<html><b>Evolves To (Enter if none):</b></html>",
            "<html><b>Evolution Level (Enter if none):</b></html>",
            "<html><b>Base HP:</b></html>",
            "<html><b>Base Attack:</b></html>",
            "<html><b>Base Defense:</b></html>",
            "<html><b>Base Speed:</b></html>"
        };

        Object[] answers = new Object[questions.length];
        int[] current = {0};

        questionLabel.setText(questions[current[0]]);
        answerField.setText("");
        if (current[0] == 2 || current[0] == 3) {
            validTypesLabel.setText("<html>Valid types: <b>" + String.join(", ", validTypes) + "</b></html>");
            validTypesLabel.setVisible(true);
        } else {
            validTypesLabel.setVisible(false);
        }

        enterButton.addActionListener(e -> {
            String input = answerField.getText().trim();
            switch (current[0]) {
                case 0: // Pokédex Number
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
                    break;
                case 1: // Name
                    if (input.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Name cannot be empty.");
                        return;
                    }
                    if (controller.isPokemonNameExists(input)) {
                        JOptionPane.showMessageDialog(this, "Pokémon Name already exists!");
                        return;
                    }
                    answers[1] = input;
                    break;
                case 2: // Type 1
                    boolean validType1 = false;
                    for (String t : validTypes) {
                        if (t.equalsIgnoreCase(input)) {
                            validType1 = true;
                            break;
                        }
                    }
                    if (!validType1) {
                        JOptionPane.showMessageDialog(this, "Invalid Type 1. Allowed types are:\n" + String.join(", ", validTypes));
                        return;
                    }
                    answers[2] = input;
                    break;
                case 3: // Type 2
                    if (input.isEmpty()) {
                        answers[3] = "";
                    } else {
                        boolean validType2 = false;
                        for (String t : validTypes) {
                            if (t.equalsIgnoreCase(input)) {
                                validType2 = true;
                                break;
                            }
                        }
                        if (!validType2) {
                            JOptionPane.showMessageDialog(this, "Invalid Type 2. Allowed types are:\n" + String.join(", ", validTypes));
                            return;
                        }
                        answers[3] = input;
                    }
                    break;
                case 4: // Evolves From
                case 5: // Evolves To
                case 6: // Evolution Level
                    if (input.isEmpty()) {
                        answers[current[0]] = null;
                    } else {
                        try {
                            answers[current[0]] = Integer.parseInt(input);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Invalid number.");
                            return;
                        }
                    }
                    break;
                case 7: // Base HP
                case 8: // Base Attack
                case 9: // Base Defense
                case 10: // Base Speed
                    try {
                        answers[current[0]] = Double.parseDouble(input);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Invalid number.");
                        return;
                    }
                    break;
            }
            current[0]++;
            if (current[0] < questions.length) {
                questionLabel.setText(questions[current[0]]);
                answerField.setText("");
                // Show valid types only for type questions
                if (current[0] == 2 || current[0] == 3) {
                    validTypesLabel.setText("<html>Valid types: <b>" + String.join(", ", validTypes) + "</b></html>");
                    validTypesLabel.setVisible(true);
                } else {
                    validTypesLabel.setVisible(false);
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
                showWelcomePanel(onHome);
            }
        });
    }

    private void removeAllPanels() {
        if (welcomePanel != null) {
            remove(welcomePanel);
        }
        if (mainPanel != null) {
            remove(mainPanel);
        }
        revalidate();
        repaint();
    }

    private JPanel createEvolutionBox(Pokemon p) {
        JPanel box = new JPanel(null);
        box.setOpaque(false);
        box.setBounds(0, 0, 220, 170);

        JLabel imgLabel = new JLabel();
        imgLabel.setBounds(10, 10, 200, 120);
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setVerticalAlignment(SwingConstants.CENTER);

        JLabel nameLabel = new JLabel();
        nameLabel.setFont(new Font("Consolas", Font.BOLD, 13));
        nameLabel.setBounds(7, 127, 200, 30);
        nameLabel.setBorder(buttonShadowBorder);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setVerticalAlignment(SwingConstants.CENTER);

        if (p != null) {
            int num = p.getPokedexNumber();
            String imgPath = (num >= 1 && num <= 28)
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

    private void updateEvolutionBoxes(Pokemon base, Pokemon secondEvo, Pokemon thirdEvo) {
        boxBase.removeAll();
        boxSecond.removeAll();
        boxThird.removeAll();

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

        boxBase.revalidate();
        boxBase.repaint();
        boxSecond.revalidate();
        boxSecond.repaint();
        boxThird.revalidate();
        boxThird.repaint();
    }
}
