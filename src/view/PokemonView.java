package view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import model.Pokemon;

public class PokemonView extends JPanel {

    public static final Color POKEDEX_BLUE = new Color(10, 168, 255);
    public static final Color BUTTON_SHADOW = new Color(68, 95, 146);
    Border buttonShadowBorder = BorderFactory.createLineBorder(BUTTON_SHADOW, 1);

    private List<Pokemon> pokedex;
    private int currentPokemonIndex = 0;
    private JLabel nameLabel, levelLabel, hpLabel, atkLabel, defLabel, spdLabel, typeLabel1, typeLabel2;

    public PokemonView(List<Pokemon> pokedex, Runnable onHome) {
        this.pokedex = pokedex;
        setLayout(null);
        setOpaque(false);

        nameLabel = new JLabel();
        levelLabel = new JLabel();
        hpLabel = new JLabel();
        atkLabel = new JLabel();
        defLabel = new JLabel();
        spdLabel = new JLabel();
        typeLabel1 = new JLabel();
        typeLabel2 = new JLabel();

        nameLabel.setFont(new Font("Consolas", Font.BOLD, 20));
        levelLabel.setFont(new Font("Consolas", Font.BOLD, 20));
        hpLabel.setFont(new Font("Consolas", Font.BOLD, 17));
        atkLabel.setFont(new Font("Consolas", Font.BOLD, 17));
        defLabel.setFont(new Font("Consolas", Font.BOLD, 17));
        spdLabel.setFont(new Font("Consolas", Font.BOLD, 17));
        typeLabel1.setFont(new Font("Consolas", Font.BOLD, 30));
        typeLabel2.setFont(new Font("Consolas", Font.BOLD, 30));

        nameLabel.setBounds(40, 39, 300, 40);
        levelLabel.setBounds(315, 39, 300, 40);
        hpLabel.setBounds(508, 35, 500, 30);
        atkLabel.setBounds(508, 60, 500, 30);
        defLabel.setBounds(508, 85, 500, 30);
        spdLabel.setBounds(508, 110, 500, 30);
        typeLabel1.setBounds(700, 75, 150, 40);
        typeLabel2.setBounds(700, 110, 150, 40);

        typeLabel1.setHorizontalAlignment(JLabel.CENTER);
        typeLabel2.setHorizontalAlignment(JLabel.CENTER);

        add(nameLabel);
        add(levelLabel);
        add(hpLabel);
        add(atkLabel);
        add(defLabel);
        add(spdLabel);
        add(typeLabel1);
        add(typeLabel2);

        updatePokemonLabels(); // Only update text, do not add new labels

        JButton btnAdd = new JButton("Add New Pokémon");
        btnAdd.setFont(new Font("Consolas", Font.BOLD, 14));
        btnAdd.setBounds(493, 345, 140, 35);
        btnAdd.setBorder(buttonShadowBorder);
        btnAdd.setBackground(POKEDEX_BLUE);
        btnAdd.setMargin(new Insets(0, 0, 0, 0));
        add(btnAdd);

        JButton btnViewAll = new JButton("View All Pokémon");
        btnViewAll.setFont(new Font("Consolas", Font.BOLD, 14));
        btnViewAll.setBounds(640, 345, 140, 35);

        btnViewAll.setBorder(buttonShadowBorder);
        btnViewAll.setBackground(POKEDEX_BLUE);
        btnViewAll.setMargin(new Insets(0, 0, 0, 0));
        add(btnViewAll);

        JButton btnSearch = new JButton("Search Pokémon");
        btnSearch.setFont(new Font("Consolas", Font.BOLD, 14));
        btnSearch.setBounds(493, 387, 140, 35);
        btnSearch.setBorder(buttonShadowBorder);
        btnSearch.setBackground(POKEDEX_BLUE);
        btnSearch.setMargin(new Insets(0, 0, 0, 0));
        add(btnSearch);

        JButton btnHome = new JButton("Home");
        btnHome.setFont(new Font("Consolas", Font.BOLD, 14));
        btnHome.setBounds(787, 345, 67, 35);
        btnHome.setBorder(buttonShadowBorder);
        btnHome.setBackground(POKEDEX_BLUE);
        btnHome.setMargin(new Insets(0, 0, 0, 0));
        add(btnHome);
        btnHome.addActionListener(e -> {
            if (onHome != null) {
                onHome.run();
            }
        });

        JButton btnExit = new JButton("Exit");
        btnExit.setFont(new Font("Consolas", Font.BOLD, 14));
        btnExit.setBounds(787, 387, 67, 35);
        btnExit.setBorder(buttonShadowBorder);
        btnExit.setBackground(POKEDEX_BLUE);
        btnExit.setMargin(new Insets(0, 0, 0, 0));
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
}
