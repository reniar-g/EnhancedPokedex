package view;

import controller.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
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

    private JLabel titleLabel; // Title label for the main window
    private JLabel outputArea; // Output area for displaying messages in main menu
    private final JLabel backgroundLabel1; // Background label for the main window
    private final JLabel backgroundLabel2; // Background label for the Pokémon view

    private final JButton nextButton; // Button to navigate to the next Pokémon
    private final JButton prevButton; // Button to navigate to the previous Pokémon
    private PokemonView pokemonView; // Instance of the Pokémon view to display Pokémon details
    private boolean pokemonViewActive = false;
    private PokemonController pokemonController; // Instance of the Pokémon controller to manage Pokémon data

    /**
     * Constructor for MainPokedexView. Initializes the main window, sets up the
     * backgrounds, buttons, and starts the home screen and music.
     *
     * @param pokedex List of Pokémon to manage.
     * @param moveList List of moves to manage.
     * @param itemList List of items to manage.
     * @param trainerList List of trainers to manage.
     */
    public MainPokedexView(ArrayList<Pokemon> pokedex, ArrayList<Move> moveList, ArrayList<Item> itemList, ArrayList<Trainer> trainerList) {
        super("Enhanced Pokédex");
        pokemonController = new PokemonController(pokedex, moveList);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        backgroundLabel1 = loadDefaultBackground();
        backgroundLabel1.setLayout(null);

        backgroundLabel2 = loadAltBackground();
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

    /**
     * Creates and returns the "Next" button for navigating to the next Pokémon
     * in the view. The button is only functional when the Pokémon view is
     * active.
     *
     * @return JButton configured as the "Next" navigation button.
     */
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

    /**
     * Creates and returns the "Back" button for navigating to the previous
     * Pokémon in the view. The button is only functional when the Pokémon view
     * is active.
     *
     * @return JButton configured as the "Back" navigation button.
     */
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

    /**
     * Creates and returns a styled "Home" button with the provided action
     * listener.
     *
     * @param action The ActionListener to attach to the Home button.
     * @return JButton configured as the "Home" navigation button.
     */
    public static JButton homeButton(ActionListener action) {
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

    /**
     * Loads and returns the default background image for the main window. If
     * the image cannot be loaded, returns a backup background panel.
     *
     * @return JLabel containing the main background image or backup panel.
     */
    private JLabel loadDefaultBackground() {
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

    /**
     * Loads and returns the alternate background image for the Pokémon view. If
     * the image cannot be loaded, returns a backup background panel.
     *
     * @return JLabel containing the alternate background image or backup panel.
     */
    private JLabel loadAltBackground() {
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

    /**
     * Displays the home screen with main menu options and disables navigation
     * buttons. Sets up the main background and menu buttons.
     */
    private void showHomeScreen() {
        setContentPane(backgroundLabel1);
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

    /**
     * Displays the Pokémon management view, enables navigation buttons, and
     * shows the PokémonView panel.
     */
    private void showPokemonView() {
        setContentPane(backgroundLabel2);
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

    /**
     * Handles menu output and navigation based on the selected choice.
     *
     * @param choice The menu option selected by the user.
     */
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

    /**
     * Adds the main menu buttons (Pokémon, Moves, Items, Trainers, Home, Exit)
     * to the home screen. Sets up their event listeners for navigation.
     */
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

        JButton btnHome = MainPokedexView.homeButton(e -> showMenuOutput(5));
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

    /**
     * Plays the Pokémon theme song in a separate thread. Sets the volume to 80%
     * and loops the song continuously.
     */
    private void playPokemonSong() {
        new Thread(() -> {
            try {
                File audioFile = new File("src/util/PokemonSong.wav");
                if (audioFile.exists()) {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();

                    // Set volume to 80%
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    float range = gainControl.getMaximum() - gainControl.getMinimum();
                    float gain = (range * 0.1f) + gainControl.getMinimum(); // 80% volume
                    gainControl.setValue(gain);

                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } else {
                    System.out.println("Audio file not found: " + audioFile.getPath());
                }
            } catch (Exception e) {
                System.out.println("Error playing song: " + e.getMessage());
            }
        }).start();
    }
}
