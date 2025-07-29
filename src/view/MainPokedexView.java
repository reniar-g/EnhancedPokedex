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
import model.*;
import util.GUIUtils;

public class MainPokedexView extends JFrame {

    private JLabel titleLabel, outputArea;
    private final JLabel backgroundLabel1, backgroundLabel2;
    private final JButton nextButton, prevButton;

    private PokemonView pokemonView;
    private MoveView movesView;
    private ItemView itemView;
    private TrainerView trainerView;

    private final PokemonController pokemonController;
    private final MoveController movesController;
    private final ItemController itemController;
    private final TrainerController trainerController;

    private boolean pokemonViewActive = false;

    public MainPokedexView(ArrayList<Pokemon> pokedex, ArrayList<Move> moveList, ArrayList<Item> itemList, ArrayList<Trainer> trainerList) {
        super("Enhanced Pokédex");
        movesController = new MoveController(moveList);
        pokemonController = new PokemonController(pokedex, movesController);
        itemController = new ItemController(itemList);
        trainerController = new TrainerController(trainerList);
        trainerController.setPokemonController(pokemonController);
        trainerController.setItemController(itemController);
        trainerController.setMoveController(movesController);

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

    // creates the next button for pokemon view
    private JButton nextButton() {
        JButton btnNext = new JButton("Next");
        btnNext.setBounds(755, 559, 75, 75);
        btnNext.setBackground(GUIUtils.POKEDEX_BLUE);
        btnNext.setForeground(Color.BLACK);
        btnNext.setFont(new Font("Consolas", Font.BOLD, 14));
        btnNext.setBorder(GUIUtils.buttonShadowBorder);
        btnNext.setOpaque(true);

        btnNext.addActionListener(e -> {
            if (pokemonViewActive && pokemonView != null) {
                pokemonView.showNextPokemon();
            }
        });
        return btnNext;
    }

    // creates the back button for pokemon view
    private JButton backButton() {
        JButton btnBack = new JButton("Back");
        btnBack.setBounds(520, 559, 75, 75);
        btnBack.setBackground(GUIUtils.POKEDEX_BLUE);
        btnBack.setFont(new Font("Consolas", Font.BOLD, 14));
        btnBack.setForeground(Color.BLACK);
        btnBack.setOpaque(true);
        btnBack.setBorder(GUIUtils.buttonShadowBorder);
        btnBack.addActionListener(e -> {
            if (pokemonViewActive && pokemonView != null) {
                pokemonView.showPreviousPokemon();
            }
        });
        return btnBack;
    }

    // creates the home button for navigation
    public static JButton homeButton(ActionListener action) {
        JButton btnHome = new JButton("Home");
        btnHome.setFont(new Font("Consolas", Font.BOLD, 14));
        btnHome.setBounds(787, 345, 67, 35);
        btnHome.setBorder(BorderFactory.createLineBorder(GUIUtils.BUTTON_SHADOW, 1));
        btnHome.setBackground(GUIUtils.POKEDEX_BLUE);
        btnHome.setMargin(new Insets(0, 0, 0, 0));
        if (action != null) {
            btnHome.addActionListener(action);
        }
        return btnHome;
    }

    // loads and returns the default bg img for the pokemon view.
    private JLabel loadDefaultBackground() {
        try {
            ImageIcon pokedexIcon = new ImageIcon("src/util/MainPokedexBase.png");
            JLabel backgroundLabel = new JLabel(pokedexIcon);
            backgroundLabel.setLayout(null);
            setSize(pokedexIcon.getIconWidth(), pokedexIcon.getIconHeight());
            return backgroundLabel;
        } catch (Exception e) { // create a backup background if the image fails to load
            System.out.println("Error loading Pokedex icon: " + e.getMessage());
            JPanel backupBackground = new JPanel();
            backupBackground.setBackground(GUIUtils.POKEDEX_RED);
            backupBackground.setPreferredSize(new Dimension(900, 700));
            setContentPane(backupBackground);
            setSize(900, 700);
            return new JLabel();
        }
    }

    // loads and returns the alt bg img for the pokemon view.
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
            backupBackground.setBackground(GUIUtils.POKEDEX_RED);
            backupBackground.setPreferredSize(new Dimension(900, 700));
            setContentPane(backupBackground);
            setSize(900, 700);
            return new JLabel();
        }
    }

    // shows the home screen with welcome message and buttons
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

    // displays the pokemon management screen
    private void showPokemonView() {
        setContentPane(backgroundLabel2);
        backgroundLabel2.removeAll();
        pokemonViewActive = true;
        nextButton.setEnabled(true);
        prevButton.setEnabled(true);
        backgroundLabel2.add(nextButton);
        backgroundLabel2.add(prevButton);

        // pass the pokemonController to the PokemonView
        pokemonView = new PokemonView(pokemonController, () -> showMenuOutput(5));
        pokemonView.setBounds(0, 0, getWidth(), getHeight());
        backgroundLabel2.add(pokemonView);

        backgroundLabel2.repaint();
        revalidate();
        repaint();
    }

    // displays the moves management screen
    private void showMovesView() {
        setContentPane(backgroundLabel1);
        backgroundLabel1.removeAll();
        nextButton.setEnabled(false);
        prevButton.setEnabled(false);
        backgroundLabel1.add(nextButton);
        backgroundLabel1.add(prevButton);

        movesView = new MoveView(movesController, () -> showMenuOutput(5));
        movesView.setBounds(0, 0, getWidth(), getHeight());
        backgroundLabel1.add(movesView);

        backgroundLabel1.repaint();
        revalidate();
        repaint();
    }

    // displays the item management screen
    private void showItemView() {
        setContentPane(backgroundLabel1);
        backgroundLabel1.removeAll();
        nextButton.setEnabled(false);
        prevButton.setEnabled(false);
        backgroundLabel1.add(nextButton);
        backgroundLabel1.add(prevButton);

        itemView = new ItemView(itemController, () -> showMenuOutput(5));
        itemView.setBounds(0, 0, getWidth(), getHeight());
        backgroundLabel1.add(itemView);

        backgroundLabel1.repaint();
        revalidate();
        repaint();
    }

    // displays the trainer management screen
    private void showTrainerView() {
        setContentPane(backgroundLabel1);
        backgroundLabel1.removeAll();
        nextButton.setEnabled(false);
        prevButton.setEnabled(false);
        backgroundLabel1.add(nextButton);
        backgroundLabel1.add(prevButton);

        trainerView = new TrainerView(trainerController, () -> showMenuOutput(5));
        trainerView.setBounds(0, 0, getWidth(), getHeight());
        backgroundLabel1.add(trainerView);

        backgroundLabel1.repaint();
        revalidate();
        repaint();
    }

    private void showMenuOutput(int choice) {
        switch (choice) {
            case 1 ->
                showPokemonView(); // Pokemon Management
            case 2 ->
                showMovesView(); // Moves Management
            case 3 ->
                showItemView(); // Item Management
            case 4 ->
                showTrainerView(); // Trainer Management
            case 5 ->
                showHomeScreen();  // Home Screen
            case 6 ->
                System.exit(0);
        }
    }

    // creates the buttons for the home screen
    public void homeScreenButtons() {
        JButton btnPokemon = new JButton("Manage Pokémons");
        btnPokemon.setFont(new Font("Consolas", Font.BOLD, 14));
        btnPokemon.setBounds(493, 345, 140, 35);
        btnPokemon.setBorder(GUIUtils.buttonShadowBorder);
        btnPokemon.setBackground(GUIUtils.POKEDEX_BLUE);
        btnPokemon.setMargin(new Insets(0, 0, 0, 0));
        backgroundLabel1.add(btnPokemon);
        btnPokemon.addActionListener(e -> showMenuOutput(1));

        JButton btnMoves = new JButton("Manage Moves");
        btnMoves.setFont(new Font("Consolas", Font.BOLD, 14));
        btnMoves.setBounds(640, 345, 140, 35);
        btnMoves.setBorder(GUIUtils.buttonShadowBorder);
        btnMoves.setBackground(GUIUtils.POKEDEX_BLUE);
        btnMoves.setMargin(new Insets(0, 0, 0, 0));
        backgroundLabel1.add(btnMoves);
        btnMoves.addActionListener(e -> showMenuOutput(2));

        JButton btnItems = new JButton("Manage Items");
        btnItems.setFont(new Font("Consolas", Font.BOLD, 14));
        btnItems.setBounds(493, 387, 140, 35);
        btnItems.setBorder(GUIUtils.buttonShadowBorder);
        btnItems.setBackground(GUIUtils.POKEDEX_BLUE);
        btnItems.setMargin(new Insets(0, 0, 0, 0));
        backgroundLabel1.add(btnItems);
        btnItems.addActionListener(e -> showMenuOutput(3));

        JButton btnTrainers = new JButton("Manage Trainers");
        btnTrainers.setFont(new Font("Consolas", Font.BOLD, 14));
        btnTrainers.setBounds(640, 387, 140, 35);
        btnTrainers.setBorder(GUIUtils.buttonShadowBorder);
        btnTrainers.setBackground(GUIUtils.POKEDEX_BLUE);
        btnTrainers.setMargin(new Insets(0, 0, 0, 0));
        backgroundLabel1.add(btnTrainers);
        btnTrainers.addActionListener(e -> showMenuOutput(4));

        JButton btnHome = MainPokedexView.homeButton(e -> showMenuOutput(5));
        backgroundLabel1.add(btnHome);

        JButton btnExit = new JButton("Exit");
        btnExit.setFont(new Font("Consolas", Font.BOLD, 14));
        btnExit.setBounds(787, 387, 67, 35);
        btnExit.setBorder(GUIUtils.buttonShadowBorder);
        btnExit.setBackground(GUIUtils.POKEDEX_BLUE);
        btnExit.setMargin(new Insets(0, 0, 0, 0));
        backgroundLabel1.add(btnExit);
        btnExit.addActionListener(e -> showMenuOutput(6));
    }

    // plays the pokemon song in the bg
    private void playPokemonSong() {
        new Thread(() -> {
            try {
                File audioFile = new File("src/util/PokemonSong.wav");
                if (audioFile.exists()) {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();

                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    float range = gainControl.getMaximum() - gainControl.getMinimum();
                    float gain = (range * 0.8f) + gainControl.getMinimum(); // for volume control
                    gainControl.setValue(gain);

                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } else { // if the audio file is not found, print an error message
                    System.out.println("Audio file not found: " + audioFile.getPath());
                }
            } catch (Exception e) { // if there is an error playing the song, print the error message
                System.out.println("Error playing song: " + e.getMessage());
            }
        }).start();
    }
}
