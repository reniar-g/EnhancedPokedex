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

    // Color constants for the GUI
    public static final Color POKEDEX_RED = new Color(220, 50, 50);
    public static final Color POKEDEX_BLUE = new Color(10, 168, 255);
    public static final Color POKEDEX_GREEN = new Color(202, 213, 181);
    public static final Color BUTTON_SHADOW = new Color(68, 95, 146);
    public static final Border buttonShadowBorder = BorderFactory.createLineBorder(BUTTON_SHADOW, 1);

    /**
     * Adds a styled welcome label to the specified panel.
     *
     * @param panel The JPanel to add the label to.
     * @param text The text to display.
     * @param x The x position of the label.
     * @param y The y position of the label.
     * @param width The width of the label.
     * @param height The height of the label.
     */
    public static void addWelcomeLabel(JPanel panel, String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Consolas", Font.BOLD, 27));
        label.setForeground(Color.BLACK);
        label.setBounds(x, y, width, height);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
    }

    /**
     * Creates a styled button (variant 1) for the UI.
     *
     * @param text The button text.
     * @param x The x position.
     * @param y The y position.
     * @param width The button width.
     * @param height The button height.
     * @return The created JButton.
     */
    public static JButton createButton1(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Consolas", Font.BOLD, 14));
        button.setBounds(x, y, width, height);
        button.setBorder(buttonShadowBorder);
        button.setBackground(GUIUtils.POKEDEX_BLUE);
        button.setMargin(new Insets(0, 0, 0, 0));
        return button;
    }

    /**
     * Creates a styled button (variant 2) for the UI.
     *
     * @param text The button text.
     * @param x The x position.
     * @param y The y position.
     * @param width The button width.
     * @param height The button height.
     * @return The created JButton.
     */
    public static JButton createButton2(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Consolas", Font.BOLD, 14));
        button.setBounds(x, y, width, height);
        button.setBorder(buttonShadowBorder);
        button.setBackground(GUIUtils.POKEDEX_BLUE);
        button.setMargin(new Insets(0, 0, 0, 0));
        return button;
    }

    /**
     * Creates a navigation button with an action listener.
     *
     * @param text The button text.
     * @param x The x position.
     * @param y The y position.
     * @param width The button width.
     * @param height The button height.
     * @param action The ActionListener for the button.
     * @return The created JButton.
     */
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

    /**
     * Creates a labeled scroll panel and adds it to the parent panel.
     *
     * @param parent The parent JPanel.
     * @param labelText The label text.
     * @param labelX The label x position.
     * @param labelY The label y position.
     * @param labelW The label width.
     * @param labelH The label height.
     * @param scrollX The scroll pane x position.
     * @param scrollY The scroll pane y position.
     * @param scrollW The scroll pane width.
     * @param scrollH The scroll pane height.
     * @param listPanel The panel to be placed inside the scroll pane.
     * @return The created JScrollPane.
     */
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

    /**
     * Container for search field components (text field and button).
     */
    public static class SearchFieldComponents {

        public final JTextField field;
        public final JButton button;

        /**
         * Constructs a SearchFieldComponents object.
         *
         * @param field The JTextField component.
         * @param button The JButton component.
         */
        public SearchFieldComponents(JTextField field, JButton button) {
            this.field = field;
            this.button = button;
        }
    }

    /**
     * Creates a labeled search field and button, and adds them to the parent
     * panel.
     *
     * @param parent The parent JPanel.
     * @param labelText The label text.
     * @param labelX The label x position.
     * @param labelY The label y position.
     * @param labelW The label width.
     * @param labelH The label height.
     * @param fieldX The text field x position.
     * @param fieldY The text field y position.
     * @param fieldW The text field width.
     * @param fieldH The text field height.
     * @param buttonX The button x position.
     * @param buttonY The button y position.
     * @param buttonW The button width.
     * @param buttonH The button height.
     * @param buttonText The button text.
     * @return A SearchFieldComponents object containing the field and button.
     */
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

    /**
     * Creates a label displaying Pokémon information.
     *
     * @param p The Pokémon to display.
     * @return The created JLabel.
     */
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

    /**
     * Removes all components from the given JPanel, clearing its current state.
     * This is used to reset the view when switching between different panels.
     *
     * @param panel The JPanel to clear.
     */
    public static void removeAllPanels(JPanel panel) {
        if (panel != null) {
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
        }
    }
}
