package view;

import controller.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import javax.swing.border.Border;
import model.*;

public class MainPokedexView extends JFrame {

    public static final Color POKEDEX_RED = new Color(220, 50, 50);
    public static final Color POKEDEX_BLUE = new Color(10, 168, 255);
    public static final Color POKEDEX_GREEN = new Color(202, 213, 181);
    public static final Color BUTTON_SHADOW = new Color(68, 95, 146);
    Border buttonShadowBorder = BorderFactory.createLineBorder(BUTTON_SHADOW, 1);
    Border buttonShadowBorderThick = BorderFactory.createLineBorder(BUTTON_SHADOW, 3);

    private List<Pokemon> pokedex;
    private List<Move> moveList;
    private List<Item> itemList;
    private List<Trainer> trainerList;

    private JLabel outputArea;
    private final JLabel backgroundLabel1;
    private final JLabel backgroundLabel2;
    private JLabel titleLabel;

    private final JButton nextButton;
    private final JButton prevButton;
    private PokemonView pokemonView;
    private boolean pokemonViewActive = false;
    private PokemonController pokemonController;


    /*
     * Constructor for MainPokedexView
     * Initializes the main window, sets up the background, and adds buttons.
     */
    public MainPokedexView(List<Pokemon> pokedex, List<Move> moveList, List<Item> itemList, List<Trainer> trainerList) {
        super("Enhanced Pokédex");
        this.pokedex = pokedex;
        this.moveList = moveList;
        this.itemList = itemList;
        this.trainerList = trainerList;

        // Instantiate the controller
        pokemonController = new PokemonController((ArrayList<Pokemon>) pokedex, (ArrayList<Move>) moveList);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        backgroundLabel1 = loadBackgroundImage1();
        backgroundLabel1.setLayout(null);

        backgroundLabel2 = loadBackgroundImage2();
        backgroundLabel2.setLayout(null);

        nextButton = nextButton();
        prevButton = backButton();
        nextButton.setEnabled(false);
        prevButton.setEnabled(false);

        backgroundLabel1.add(nextButton);
        backgroundLabel1.add(prevButton);

        showHomeScreen();
        playPokemonSong();

        setContentPane(backgroundLabel1);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // method for the next button
    private JButton nextButton() {
        JButton btnNext = new JButton("Next");
        btnNext.setBounds(755, 559, 75, 75);
        btnNext.setBackground(POKEDEX_BLUE);
        btnNext.setForeground(Color.BLACK);
        btnNext.setFont(new Font("Consolas", Font.BOLD, 14));
        btnNext.setBorder(buttonShadowBorder);
        btnNext.setOpaque(true);

        btnNext.addActionListener(e -> {
            if (pokemonViewActive && pokemonView != null) {
                pokemonView.showNextPokemon();
            }
        });
        return btnNext;
    }

    // method for back button
    private JButton backButton() {
        JButton btnBack = new JButton("Back");
        btnBack.setBounds(520, 559, 75, 75);
        btnBack.setBackground(POKEDEX_BLUE);
        btnBack.setFont(new Font("Consolas", Font.BOLD, 14));
        btnBack.setForeground(Color.BLACK);
        btnBack.setOpaque(true);
        btnBack.setBorder(buttonShadowBorder);
        btnBack.addActionListener(e -> {
            if (pokemonViewActive && pokemonView != null) {
                pokemonView.showPreviousPokemon();
            }
        });
        return btnBack;
    }

    // method for default background
    private JLabel loadBackgroundImage1() {
        try {
            ImageIcon pokedexIcon = new ImageIcon("src/util/MainPokedexBase.png");
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

    // method for default background
    private JLabel loadBackgroundImage2() {
        try {
            ImageIcon pokedexIcon = new ImageIcon("src/util/AltPokedexBase.png");
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

    // method for homescreen
    private void showHomeScreen() {
        setContentPane(backgroundLabel1);
        // Remove all components except the background from backgroundLabel1
        backgroundLabel1.removeAll();
        pokemonViewActive = false;
        nextButton.setEnabled(false);
        prevButton.setEnabled(false);
        backgroundLabel1.add(nextButton);
        backgroundLabel1.add(prevButton);

        if (outputArea == null) {
            outputArea = new JLabel();
            outputArea.setFont(new Font("Consolas", Font.PLAIN, 16));
            outputArea.setOpaque(false);
            outputArea.setBounds(49, 105, 334, 500);
            outputArea.setVerticalAlignment(SwingConstants.TOP);
        }
        backgroundLabel1.add(outputArea);
        outputArea.setText(
                "<html><div style='text-align:justify;'> <b>Welcome to Enhanced Pokédex!</b><br><br>"
                + "Select an option using the blue buttons on the right.<br><br>"
                + "1. Manage Pokémons<br>"
                + "2. Manage Moves<br>"
                + "3. Manage Items<br>"
                + "4. Manage Trainers<br>"
                + "5. Home<br>"
                + "0. Exit"
                + "</div></html>"
        );

        if (titleLabel == null) {
            titleLabel = new JLabel("Enhanced Pokédex");
            titleLabel.setFont(new Font("Consolas", Font.BOLD, 27));
            titleLabel.setForeground(Color.BLACK);
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            titleLabel.setBounds(35, 39, 353, 40);
        }
        backgroundLabel1.add(titleLabel);
        homeScreenButtons();
        backgroundLabel1.repaint();
        revalidate();
        repaint();
    }

    private void showMenuOutput(int choice) {
        switch (choice) {
            case 1 ->
                showPokemonView(); // Pokemon Management
            case 5 ->
                showHomeScreen();  // Home Screen
            case 6 ->
                System.exit(0);
        }
    }

    private void showPokemonView() {
        setContentPane(backgroundLabel2);
        // Remove all components except the background from backgroundLabel2
        backgroundLabel2.removeAll();
        pokemonViewActive = true;
        nextButton.setEnabled(true);
        prevButton.setEnabled(true);
        backgroundLabel2.add(nextButton);
        backgroundLabel2.add(prevButton);

        // Pass the controller to PokemonView
        pokemonView = new PokemonView(pokemonController, () -> showMenuOutput(5));
        pokemonView.setBounds(0, 0, getWidth(), getHeight());
        backgroundLabel2.add(pokemonView);

        backgroundLabel2.repaint();
        revalidate();
        repaint();
    }

    public void homeScreenButtons() {
        // Button for Pokemon Management
        JButton btnPokemon = new JButton("Manage Pokémons");
        btnPokemon.setFont(new Font("Consolas", Font.BOLD, 14));
        btnPokemon.setBounds(493, 345, 140, 35);
        btnPokemon.setBorder(buttonShadowBorder);
        btnPokemon.setBackground(POKEDEX_BLUE);
        btnPokemon.setMargin(new Insets(0, 0, 0, 0));
        backgroundLabel1.add(btnPokemon);
        btnPokemon.addActionListener(e -> showMenuOutput(1));

        // Button for Moves Management
        JButton btnMoves = new JButton("Manage Moves");
        btnMoves.setFont(new Font("Consolas", Font.BOLD, 14));
        btnMoves.setBounds(640, 345, 140, 35);
        btnMoves.setBorder(buttonShadowBorder);
        btnMoves.setBackground(POKEDEX_BLUE);
        btnMoves.setMargin(new Insets(0, 0, 0, 0));
        backgroundLabel1.add(btnMoves);
        btnMoves.addActionListener(e -> showMenuOutput(2));

        // Button for Item Management
        JButton btnItems = new JButton("Manage Items");
        btnItems.setFont(new Font("Consolas", Font.BOLD, 14));
        btnItems.setBounds(493, 387, 140, 35);
        btnItems.setBorder(buttonShadowBorder);
        btnItems.setBackground(POKEDEX_BLUE);
        btnItems.setMargin(new Insets(0, 0, 0, 0));
        backgroundLabel1.add(btnItems);
        btnItems.addActionListener(e -> showMenuOutput(3));

        // Button for Trainer Management
        JButton btnTrainers = new JButton("Manage Trainers");
        btnTrainers.setFont(new Font("Consolas", Font.BOLD, 14));
        btnTrainers.setBounds(640, 387, 140, 35);
        btnTrainers.setBorder(buttonShadowBorder);
        btnTrainers.setBackground(POKEDEX_BLUE);
        btnTrainers.setMargin(new Insets(0, 0, 0, 0));
        backgroundLabel1.add(btnTrainers);
        btnTrainers.addActionListener(e -> showMenuOutput(4));

        JButton btnHome = MainPokedexView.createHomeButton(e -> showMenuOutput(5));
        backgroundLabel1.add(btnHome);

        JButton btnExit = new JButton("Exit");
        btnExit.setFont(new Font("Consolas", Font.BOLD, 14));
        btnExit.setBounds(787, 387, 67, 35);
        btnExit.setBorder(buttonShadowBorder);
        btnExit.setBackground(POKEDEX_BLUE);
        btnExit.setMargin(new Insets(0, 0, 0, 0));
        backgroundLabel1.add(btnExit);
        btnExit.addActionListener(e -> showMenuOutput(6));
    }

    private void removeAllButtons(JLabel backgroundLabel) {
        for (Component comp : backgroundLabel.getComponents()) {
            if (comp instanceof JButton) {
                backgroundLabel.remove(comp);
            }
        }
    }

    private void playPokemonSong() {
        new Thread(() -> {
            try {
                File audioFile = new File("src/util/PokemonSong.wav");
                if (audioFile.exists()) {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();

                    // Set volume to 50%
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    float range = gainControl.getMaximum() - gainControl.getMinimum();
                    float gain = (range * 0.15f) + gainControl.getMinimum(); // 50% volume
                    gainControl.setValue(gain);

                    // Keep the clip running
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } else {
                    System.out.println("Audio file not found: " + audioFile.getPath());
                }
            } catch (Exception e) {
                System.out.println("Error playing song: " + e.getMessage());
            }
        }).start();
    }

    public static JButton createHomeButton(ActionListener action) {
        JButton btnHome = new JButton("Home");
        btnHome.setFont(new Font("Consolas", Font.BOLD, 14));
        btnHome.setBounds(787, 345, 67, 35);
        btnHome.setBorder(BorderFactory.createLineBorder(BUTTON_SHADOW, 1));
        btnHome.setBackground(POKEDEX_BLUE);
        btnHome.setMargin(new Insets(0, 0, 0, 0));
        if (action != null) {
            btnHome.addActionListener(action);
        }
        return btnHome;
    }
}
