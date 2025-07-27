package view;

import controller.ItemController;
import model.Item;
import java.awt.*;
import java.util.List;
import javax.swing.*;

import util.*;
import src.EnhancedPokedexMVC;

public class ItemView extends JPanel {

    private final ItemController controller;
    private final List<Item> itemList;

    private JPanel itemsWelcomePanel, itemsMainPanel;
    private JLabel itemsWelcomeDesc;

    public ItemView(ItemController controller, Runnable onHome) {
        this.controller = controller;
        this.itemList = controller.getItemList();
        setLayout(null);
        setOpaque(false);
        showItemsWelcomePanel(onHome);
    }

    private void showViewAllItems(Runnable onHome) {
        GUIUtils.removeAllPanels(itemsMainPanel);

        itemsMainPanel = new JPanel(null);
        itemsMainPanel.setOpaque(false);
        itemsMainPanel.setBounds(0, 0, 901, 706);

        // Title
        GUIUtils.addWelcomeLabel(itemsMainPanel, "All Items", 35, 39, 353, 40);

        // Scrollable list of items
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        int itemNum = 1;
        for (Item i : itemList) {
            JLabel itemLabel = getJLabel(i, itemNum);
            listPanel.add(itemLabel);
            itemNum++;
        }

        // Make the scrollable panel fill the big left area
        GUIUtils.createLabeledScrollPanel(
                itemsMainPanel,
                "<html><span style='font-size:18px;'><b>List of Items</b></span></html>",
                34, 90, 356, 30, // Label at top left
                45, 125, 310, 320, // Scroll panel fills most of left side
                listPanel
        );

        // Search field
        GUIUtils.SearchFieldComponents search = GUIUtils.createSearchField(
                itemsMainPanel,
                "Search Item:",
                490, 442, 307, 50,
                560, 500, 180, 30,
                645, 565, 62, 43,
                "Search"
        );

        search.button.addActionListener(e -> {
            String query = search.field.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter an item name.");
                return;
            }
            Item found = null;
            for (Item i : itemList) {
                if (i.getItemName().equalsIgnoreCase(query)) {
                    found = i;
                    break;
                }
            }
            if (found != null) {
                JOptionPane.showMessageDialog(this,
                        "<html><b>Item Searched</b><br><br>"
                        + "Name: <b>" + found.getItemName() + "</b><br>"
                        + "Category: " + found.getItemCategory() + "<br>"
                        + "Description: " + found.getItemDescription() + "<br>"
                        + "Effect: " + found.getItemEffect() + "<br>"
                        + "Buy Price: " + found.getItemPrice() + "<br>"
                        + "Sell Price: " + found.getItemSellPrice() + "</html>"
                );
            } else {
                JOptionPane.showMessageDialog(this, "Item not found.");
            }
        });

        // Home button
        JButton btnHome = MainPokedexView.homeButton(e -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        itemsMainPanel.add(btnHome);

        // Back button
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, e -> {
            remove(itemsMainPanel);
            showItemsWelcomePanel(onHome);
            revalidate();
            repaint();
        });
        itemsMainPanel.add(backBtn);

        add(itemsMainPanel);
        revalidate();
        repaint();
    }

    private static JLabel getJLabel(Item i, int itemNum) {
        String itemText = "<html>"
                + "<span style='font-size:14px;'><b>#" + itemNum + "  " + i.getItemName() + "</b> | " + i.getItemCategory() + "</span><br>"
                + "Description: " + i.getItemDescription() + "<br>"
                + "Effect: " + i.getItemEffect() + "<br>"
                + "Buy Price: " + i.getItemPrice() + " | Sell Price: " + i.getItemSellPrice()
                + "<br><br>" // Space between items
                + "</html>";

        JLabel itemLabel = new JLabel(itemText);
        itemLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
        return itemLabel;
    }

    // Show the welcome panel
    private void showItemsWelcomePanel(Runnable onHome) {
        if (itemsWelcomePanel != null) {
            remove(itemsWelcomePanel);
        }

        itemsWelcomePanel = new JPanel(null);
        itemsWelcomePanel.setOpaque(false);
        itemsWelcomePanel.setBounds(0, 0, 901, 706);

        GUIUtils.addWelcomeLabel(itemsWelcomePanel, "Manage Items", 35, 39, 353, 40);

        itemsWelcomeDesc = new JLabel();
        itemsWelcomeDesc.setText(
                "<html><div style='text-align:justify;'>"
                + "<b>Welcome to the Items Management System!</b><br><br>"
                + "Here you can view and search for items.<br><br>"
                + "Use the buttons to view or search for Items."
                + "</div></html>"
        );
        itemsWelcomeDesc.setFont(new Font("Consolas", Font.PLAIN, 16));
        itemsWelcomeDesc.setBounds(49, 105, 334, 500);
        itemsWelcomeDesc.setVerticalAlignment(SwingConstants.TOP);
        itemsWelcomeDesc.setOpaque(false);
        itemsWelcomePanel.add(itemsWelcomeDesc);

        JButton itemsViewBtn = GUIUtils.createButton2("View All Items", 640, 345, 140, 35);
        itemsWelcomePanel.add(itemsViewBtn);

        JButton btnHome = MainPokedexView.homeButton(e -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        itemsWelcomePanel.add(btnHome);

        // Add button actions
        itemsViewBtn.addActionListener(e -> {
            remove(itemsWelcomePanel);
            showViewAllItems(onHome);
            revalidate();
            repaint();
        });

        add(itemsWelcomePanel);
        revalidate();
        repaint();
    }
}