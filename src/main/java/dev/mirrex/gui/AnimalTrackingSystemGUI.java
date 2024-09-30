package dev.mirrex.gui;

import dev.mirrex.engine.AnimalTrackingSystem;
import dev.mirrex.model.Animal;
import dev.mirrex.model.Rule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AnimalTrackingSystemGUI extends JFrame {

    private final AnimalTrackingSystem system;

    private JTextArea outputArea;

    private JTextField animalsFileField, propertiesFileField, rulesFileField;

    public AnimalTrackingSystemGUI() {
        system = new AnimalTrackingSystem();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Animal Tracking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        animalsFileField = new JTextField("animals.txt");
        propertiesFileField = new JTextField("properties.txt");
        rulesFileField = new JTextField("rules.txt");

        inputPanel.add(new JLabel("Animals File:"));
        inputPanel.add(animalsFileField);
        inputPanel.add(new JLabel("Properties File:"));
        inputPanel.add(propertiesFileField);
        inputPanel.add(new JLabel("Rules File:"));
        inputPanel.add(rulesFileField);

        JButton loadButton = new JButton("Load and Process");
        loadButton.addActionListener(this::loadAndProcessFiles);
        inputPanel.add(loadButton);

        JButton clearButton = new JButton("Clear Results");
        clearButton.addActionListener(this::clearAction);
        inputPanel.add(clearButton);

        add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadAndProcessFiles(ActionEvent e) {
        String animalsFile = animalsFileField.getText();
        String propertiesFile = propertiesFileField.getText();
        String rulesFile = rulesFileField.getText();

        try {
            system.run(animalsFile, propertiesFile, rulesFile);
            displayResults();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " +
                    ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayResults() {
        StringBuilder output = new StringBuilder();
        List<Animal> animals = system.getAnimals();
        List<Rule> rules = system.getRules();

        output.append("Loaded animals:\n");
        animals.forEach(animal -> output.append(animal).append("\n"));

        output.append("\nLoaded rules:\n");
        rules.forEach(rule -> output.append(rule).append("\n"));

        rules.forEach(rule -> {
            long count = animals.stream().filter(rule.getPredicate()).count();
            output.append(String.format("\n%s: %d\n", rule.getName(), count));
            output.append("  Matching animals:\n");
            animals.stream().filter(rule.getPredicate())
                    .forEach(animal -> output.append("    ").append(animal).append("\n"));
        });

        outputArea.setText(output.toString());
    }

    private void clearResults() {
        outputArea.setText("");
    }

    public static void launch() {
        SwingUtilities.invokeLater(() -> {
            AnimalTrackingSystemGUI gui = new AnimalTrackingSystemGUI();
            gui.setVisible(true);
        });
    }

    private void clearAction(ActionEvent e) {
        clearResults();
    }
}
