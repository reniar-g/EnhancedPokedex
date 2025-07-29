package view;

import controller.TrainerController;
import java.awt.*;
import javax.swing.*;
import util.GUIUtils;

public class AddTrainerView extends JPanel {

    private final TrainerController controller;
    private JPanel addPanel;

    public AddTrainerView(TrainerController controller) {
        this.controller = controller;
        setLayout(null);
        setOpaque(false);
    }

    // method to show the add trainer screen on the main view
    public void showAddTrainer(Runnable onHome) {
        removeAll();

        addPanel = new JPanel(null);
        addPanel.setOpaque(false);
        addPanel.setBounds(0, 0, 901, 706);
        add(addPanel);

        GUIUtils.addWelcomeLabel(addPanel, "Add New Trainer", 35, 39, 353, 40);

        JLabel descLabel = new JLabel("<html><div style='width:250px; text-align:justify;'>Fill out all the fields below to register a new Trainer. Initial funds are set to <b>₱1,000,000.00 (PkD)</b> for item purchases.</div></html>");
        descLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
        descLabel.setBounds(45, 100, 340, 60);
        descLabel.setVerticalAlignment(SwingConstants.TOP);
        descLabel.setHorizontalAlignment(SwingConstants.LEFT);
        addPanel.add(descLabel);

        JLabel idLabel = new JLabel("Trainer ID:");
        idLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        idLabel.setBounds(45, 160, 120, 20);
        addPanel.add(idLabel);
        JTextField idField = new JTextField();
        idField.setBounds(145, 155, 225, 25);
        addPanel.add(idField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        nameLabel.setBounds(45, 195, 120, 20);
        addPanel.add(nameLabel);
        JTextField nameField = new JTextField();
        nameField.setBounds(145, 190, 225, 25);
        addPanel.add(nameField);

        JLabel birthLabel = new JLabel("Birthdate (YYYY-MM-DD):");
        birthLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        birthLabel.setBounds(45, 230, 200, 20);
        addPanel.add(birthLabel);
        JTextField birthField = new JTextField();
        birthField.setBounds(245, 225, 125, 25);
        addPanel.add(birthField);

        JLabel sexLabel = new JLabel("Sex (M/F):");
        sexLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        sexLabel.setBounds(45, 265, 120, 20);
        addPanel.add(sexLabel);
        JTextField sexField = new JTextField();
        sexField.setBounds(145, 260, 50, 25);
        addPanel.add(sexField);

        JLabel hometownLabel = new JLabel("Hometown:");
        hometownLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        hometownLabel.setBounds(45, 300, 120, 20);
        addPanel.add(hometownLabel);
        JTextField hometownField = new JTextField();
        hometownField.setBounds(145, 295, 225, 25);
        addPanel.add(hometownField);

        JLabel desc2Label = new JLabel("Description:");
        desc2Label.setFont(new Font("Consolas", Font.PLAIN, 15));
        desc2Label.setBounds(45, 335, 120, 20);
        addPanel.add(desc2Label);
        JTextField desc2Field = new JTextField();
        desc2Field.setBounds(145, 330, 225, 25);
        addPanel.add(desc2Field);

        JLabel moneyLabel = new JLabel("Initial Funds:");
        moneyLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        moneyLabel.setBounds(45, 370, 120, 20);
        addPanel.add(moneyLabel);
        JLabel moneyValueLabel = new JLabel("₱1,000,000.00 (PkD)");
        moneyValueLabel.setFont(new Font("Consolas", Font.BOLD, 15));
        moneyValueLabel.setBounds(170, 370, 225, 20);
        addPanel.add(moneyValueLabel);

        // submit button
        JButton submitBtn = GUIUtils.createNavButton("Submit", 787, 345, 67, 35, null);
        addPanel.add(submitBtn);

        // action listener for submit button
        // validates the input and adds the trainer
        submitBtn.addActionListener(evt -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                String birth = birthField.getText().trim();
                String sex = sexField.getText().trim();
                String hometown = hometownField.getText().trim();
                String description = desc2Field.getText().trim();

                // if there is an empty fields, show error message
                if (name.isEmpty() || birth.isEmpty() || sex.isEmpty() || hometown.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required.");
                    return;
                }

                // must follow the format
                if (!birth.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    JOptionPane.showMessageDialog(this, "Invalid birthdate format. Use YYYY-MM-DD.");
                    return;
                }

                // accepts only m or f
                if (!TrainerController.isValidSex(sex)) {
                    JOptionPane.showMessageDialog(this, "Sex must be 'M' or 'F'.");
                    return;
                }

                boolean success = controller.addTrainer(id, name, birth, sex.toUpperCase(), hometown, description);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Trainer added successfully!");
                    if (onHome != null) {
                        onHome.run();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Trainer ID already exists.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Trainer ID. Please enter a number.");
            }
        });

        // home button
        JButton btnHome = MainPokedexView.homeButton(evt -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        addPanel.add(btnHome);

        // back button
        JButton backBtn = GUIUtils.createNavButton("Back", 787, 387, 67, 35, evt -> {
            if (onHome != null) {
                onHome.run();
            }
        });
        addPanel.add(backBtn);

        add(addPanel);
        revalidate();
        repaint();
    }
}
