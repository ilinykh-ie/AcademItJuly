package ru.ilinykh.temperature;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class TemperatureWindow {
    private final TemperatureConverter temperatureConverter;
    private final TemperatureScale[] scales;

    public TemperatureWindow(TemperatureScale... scales) {
        temperatureConverter = new TemperatureConverter();

        this.scales = scales;
    }

    public void show() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            JFrame frame = new JFrame("Конвертер температур");

            frame.setSize(550, 300);
            frame.setResizable(false);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);

            try {
                frame.setIconImage(ImageIO.read(new File("Temperature\\src\\ru\\ilinykh\\images\\1.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();

            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.ipady = 5;
            constraints.insets = new Insets(50, 30, 0, 25);
            panel.add(new JLabel("Введите значение:", JLabel.CENTER), constraints);

            constraints.gridx = 1;
            panel.add(new JLabel("Результат:", JLabel.CENTER), constraints);

            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.ipadx = 150;
            constraints.insets = new Insets(0, 30, 10, 30);
            JTextField enteredValue = new JTextField();
            panel.add(enteredValue, constraints);

            constraints.gridx = 1;
            JTextField result = new JTextField();
            result.setEditable(false);
            panel.add(result, constraints);

            String[] scaleNames = new String[scales.length];

            for (int i = 0; i < scales.length; i++) {
                scaleNames[i] = scales[i].getName();
            }

            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.ipadx = 0;
            JComboBox<String> inComboBox = new JComboBox<>(scaleNames);
            panel.add(inComboBox, constraints);

            constraints.gridx = 1;

            JComboBox<String> outComboBox = new JComboBox<>(scaleNames);
            panel.add(outComboBox, constraints);

            constraints.gridwidth = 2;
            constraints.gridx = 0;
            constraints.gridy = 3;
            constraints.ipadx = 50;
            constraints.ipady = 15;
            constraints.insets = new Insets(50, 0, 0, 0);
            JButton okButton = new JButton("Перевести");
            okButton.addActionListener(e -> {
                try {
                    double number = Double.parseDouble(enteredValue.getText().replace(",", "."));

                    TemperatureScale from = Arrays.stream(scales)
                            .filter(x -> Objects.equals(inComboBox.getSelectedItem(), x.getName()))
                            .findAny()
                            .orElse(null);

                    TemperatureScale to = Arrays.stream(scales)
                            .filter(x -> Objects.equals(outComboBox.getSelectedItem(), x.getName()))
                            .findAny()
                            .orElse(null);

                    if (from != null && to != null) {
                        result.setText(Double.toString(temperatureConverter.convert(number, from, to)));
                    }
                } catch (NumberFormatException n) {
                    JOptionPane.showMessageDialog(null, "Неверные данные, введите число.");
                }
            });

            panel.add(okButton, constraints);

            frame.add(panel, BorderLayout.CENTER);
            frame.add(new JPanel(), BorderLayout.LINE_START);
            frame.add(new JPanel(), BorderLayout.LINE_END);
        });
    }
}