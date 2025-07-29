package util;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import model.*;

public class GUIUtils {

    // color constants for the GUI
    public static final Color POKEDEX_RED = new Color(220, 50, 50);
    public static final Color POKEDEX_BLUE = new Color(10, 168, 255);
    public static final Color POKEDEX_GREEN = new Color(202, 213, 181);
    public static final Color BUTTON_SHADOW = new Color(68, 95, 146);
    public static final Border buttonShadowBorder = BorderFactory.createLineBorder(BUTTON_SHADOW, 1);

    // adds a welcome label to the given panel
    public static void addWelcomeLabel(JPanel panel, String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Consolas", Font.BOLD, 27));
        label.setForeground(Color.BLACK);
        label.setBounds(x, y, width, height);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
    }

    // creates a button for the UI.
    public static JButton createButton1(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Consolas", Font.BOLD, 14));
        button.setBounds(x, y, width, height);
        button.setBorder(buttonShadowBorder);
        button.setBackground(GUIUtils.POKEDEX_BLUE);
        button.setMargin(new Insets(0, 0, 0, 0));
        return button;
    }

    // creates a nav button for next and back in the pokemon list
    public static JButton createNavButton(String text, int x, int y, int width, int height, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Consolas", Font.BOLD, 14));
        button.setBounds(x, y, width, height);
        button.setBackground(POKEDEX_BLUE);
        button.setForeground(Color.BLACK);
        button.setOpaque(true);
        button.setBorder(buttonShadowBorder);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.addActionListener(action);
        return button;
    }

    // creates a labeled scroll panel with a label and a scroll pane containing a list panel.
    public static JScrollPane createLabeledScrollPanel(
            JPanel parent,
            String labelText,
            int labelX, int labelY, int labelW, int labelH,
            int scrollX, int scrollY, int scrollW, int scrollH,
            JPanel listPanel
    ) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Consolas", Font.BOLD, 14));
        label.setOpaque(true);
        label.setBackground(POKEDEX_GREEN);
        label.setBorder(buttonShadowBorder);
        label.setForeground(Color.BLACK);
        label.setBounds(labelX, labelY, labelW, labelH);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        parent.add(label);

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBounds(scrollX, scrollY, scrollW, scrollH);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        parent.add(scrollPane);

        return scrollPane;
    }

    // a class to hold the components of a search field and button.
    // basically this is used to return both the text field and button from a method.
    public static class SearchFieldComponents {

        public final JTextField field;
        public final JButton button;

        public SearchFieldComponents(JTextField field, JButton button) {
            this.field = field;
            this.button = button;
        }
    }

    // creates a search field with a label and a button.
    public static SearchFieldComponents createSearchField(
            JPanel parent,
            String labelText,
            int labelX, int labelY, int labelW, int labelH,
            int fieldX, int fieldY, int fieldW, int fieldH,
            int buttonX, int buttonY, int buttonW, int buttonH,
            String buttonText
    ) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Consolas", Font.BOLD, 25));
        label.setBounds(labelX, labelY, labelW, labelH);
        label.setBorder(buttonShadowBorder);
        label.setOpaque(true);
        label.setBackground(POKEDEX_GREEN);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        parent.add(label);

        JTextField field = new JTextField();
        field.setBounds(fieldX, fieldY, fieldW, fieldH);
        field.setHorizontalAlignment(SwingConstants.LEFT);
        field.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        parent.add(field);

        JButton button = new JButton(buttonText);
        button.setFont(new Font("Consolas", Font.BOLD, 14));
        button.setBounds(buttonX, buttonY, buttonW, buttonH);
        button.setOpaque(false);
        button.setBackground(POKEDEX_GREEN);
        button.setBorder(buttonShadowBorder);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        parent.add(button);

        return new SearchFieldComponents(field, button);
    }

    // creates a label for displaying information about a Pokemon.
    public static JLabel createPokemonInfoLabel(Pokemon p) {
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
        return pokeLabel;
    }

    // removes all components from a given panel and repaints it.
    public static void removeAllPanels(JPanel panel) {
        if (panel != null) {
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
        }
    }
}
