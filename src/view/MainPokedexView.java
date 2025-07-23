package view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import model.*;

public class MainPokedexView extends JFrame {

    public static final Color POKEDEX_RED = new Color(220, 50, 50);
    public static final Color POKEDEX_GREEN = new Color(180, 220, 180);
    public static final Color POKEDEX_BLUE = new Color(10, 168, 255);
    public static final Color BUTTON_SHADOW = new Color(68, 95, 146);
    Border buttonShadowBorder = BorderFactory.createLineBorder(BUTTON_SHADOW, 1);

    private List<Pokemon> pokedex;
    private List<Move> moveList;
    private List<Item> itemList;
    private List<Trainer> trainerList;

    private JTextArea outputArea;
    private JLabel backgroundLabel;
    private JLabel titleLabel;

    public MainPokedexView(List<Pokemon> pokedex, List<Move> moveList, List<Item> itemList, List<Trainer> trainerList) {
        super("Enhanced Pokédex");
        this.pokedex = pokedex;
        this.moveList = moveList;
        this.itemList = itemList;
        this.trainerList = trainerList;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        backgroundLabel = loadBackgroundImage();
        backgroundLabel.setLayout(null);

        homeScreen();

        setContentPane(backgroundLabel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JLabel loadBackgroundImage() {
        try {
            ImageIcon pokedexIcon = new ImageIcon("src/util/PokedexBase.png");
            JLabel backgroundLabel = new JLabel(pokedexIcon);
            backgroundLabel.setLayout(null);
            setSize(pokedexIcon.getIconWidth(), pokedexIcon.getIconHeight());
            return backgroundLabel;
        } catch (Exception e) {
            System.out.println("Error loading Pokedex icon: " + e.getMessage());
            JPanel backupBackground = new JPanel();
            backupBackground.setBackground(POKEDEX_RED);
            backupBackground.setPreferredSize(new Dimension(900, 700));
            setContentPane(backupBackground);
            setSize(900, 700);
            return new JLabel();
        }
    }

    private void homeScreen() {
        removeAllButtons();
        for (Component comp : backgroundLabel.getComponents()) {
            if (comp instanceof PokemonView) {
                backgroundLabel.remove(comp);
            }
        }
        // Output area
        if (outputArea == null) {
            outputArea = new JTextArea();
            outputArea.setFont(new Font("Consolas", Font.PLAIN, 15));
            outputArea.setOpaque(false);
            outputArea.setEditable(false);
            outputArea.setLineWrap(true);
            outputArea.setWrapStyleWord(true);
            outputArea.setBounds(40, 95, 305, 350);
        }
        backgroundLabel.add(outputArea);
        outputArea.setText("Welcome to Enhanced Pokédex!\n\n"
                + "Select an option using the blue buttons on the right.\n"
                + "1. Manage Pokémons\n"
                + "2. Manage Moves\n"
                + "3. Manage Items\n"
                + "4. Manage Trainers\n"
                + "5. Home\n"
                + "0. Exit\n");

        // Main Title
        if (titleLabel == null) {
            titleLabel = new JLabel("Enhanced Pokédex");
            titleLabel.setFont(new Font("Consolas", Font.BOLD, 27));
            titleLabel.setForeground(Color.BLACK);
            titleLabel.setBackground(Color.GREEN);
            titleLabel.setBounds(85, 36, 300, 45);
        }
        backgroundLabel.add(titleLabel);
        homeScreenButtons();
        backgroundLabel.repaint();
    }

    private void showMenuOutput(int choice) {
        switch (choice) {
            case 1 ->
                pokemonView();
            case 5 ->
                homeScreen(); // Home button pressed
            case 6 ->
                System.exit(0);
        }
    }

    private void pokemonView() {
        removeAllButtons();
        if (outputArea != null) {
            backgroundLabel.remove(outputArea);
            outputArea = null;
        }
        if (titleLabel != null) {
            backgroundLabel.remove(titleLabel);
        }
        for (Component comp : backgroundLabel.getComponents()) {
            if (comp instanceof PokemonView) {
                backgroundLabel.remove(comp);
            }
        }
        // Pass a callback to PokemonView for Home button
        PokemonView pokemonPanel = new PokemonView(pokedex, () -> showMenuOutput(5));
        pokemonPanel.setBounds(0, 0, getWidth(), getHeight());
        backgroundLabel.add(pokemonPanel);

        backgroundLabel.repaint();
    }

    public void homeScreenButtons() {
        // Button for Pokemon Management
        JButton btnPokemon = new JButton("Manage Pokémons");
        btnPokemon.setFont(new Font("Consolas", Font.BOLD, 14));
        btnPokemon.setBounds(493, 345, 140, 35);
        btnPokemon.setBorder(buttonShadowBorder);
        btnPokemon.setBackground(POKEDEX_BLUE);
        btnPokemon.setMargin(new Insets(0, 0, 0, 0));
        backgroundLabel.add(btnPokemon);
        btnPokemon.addActionListener(e -> showMenuOutput(1));

        // Button for Moves Management
        JButton btnMoves = new JButton("Manage Moves");
        btnMoves.setFont(new Font("Consolas", Font.BOLD, 14));
        btnMoves.setBounds(640, 345, 140, 35);
        btnMoves.setBorder(buttonShadowBorder);
        btnMoves.setBackground(POKEDEX_BLUE);
        btnMoves.setMargin(new Insets(0, 0, 0, 0));
        backgroundLabel.add(btnMoves);
        btnMoves.addActionListener(e -> showMenuOutput(2));

        // Button for Item Management
        JButton btnItems = new JButton("Manage Items");
        btnItems.setFont(new Font("Consolas", Font.BOLD, 14));
        btnItems.setBounds(493, 387, 140, 35);
        btnItems.setBorder(buttonShadowBorder);
        btnItems.setBackground(POKEDEX_BLUE);
        btnItems.setMargin(new Insets(0, 0, 0, 0));
        backgroundLabel.add(btnItems);
        btnItems.addActionListener(e -> showMenuOutput(3));

        // Button for Trainer Management
        JButton btnTrainers = new JButton("Manage Trainers");
        btnTrainers.setFont(new Font("Consolas", Font.BOLD, 14));
        btnTrainers.setBounds(640, 387, 140, 35);
        btnTrainers.setBorder(buttonShadowBorder);
        btnTrainers.setBackground(POKEDEX_BLUE);
        btnTrainers.setMargin(new Insets(0, 0, 0, 0));
        backgroundLabel.add(btnTrainers);
        btnTrainers.addActionListener(e -> showMenuOutput(4));

        JButton btnHome = new JButton("Home");
        btnHome.setFont(new Font("Consolas", Font.BOLD, 14));
        btnHome.setBounds(787, 345, 67, 35);
        btnHome.setBorder(buttonShadowBorder);
        btnHome.setBackground(POKEDEX_BLUE);
        btnHome.setMargin(new Insets(0, 0, 0, 0));
        backgroundLabel.add(btnHome);
        btnHome.addActionListener(e -> showMenuOutput(5));

        JButton btnExit = new JButton("Exit");
        btnExit.setFont(new Font("Consolas", Font.BOLD, 14));
        btnExit.setBounds(787, 387, 67, 35);
        btnExit.setBorder(buttonShadowBorder);
        btnExit.setBackground(POKEDEX_BLUE);
        btnExit.setMargin(new Insets(0, 0, 0, 0));
        backgroundLabel.add(btnExit);
        btnExit.addActionListener(e -> showMenuOutput(6));
    }

    private void removeAllButtons() {
        for (Component comp : backgroundLabel.getComponents()) {
            if (comp instanceof JButton) {
                backgroundLabel.remove(comp);
            }
        }
    }
}
