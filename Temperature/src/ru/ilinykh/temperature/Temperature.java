package ru.ilinykh.temperature;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Temperature {
    public static double convertTemperature(double temperature, int dimension1, int dimension2) {
        if (dimension1 < 1 || dimension1 > 3 || dimension2 < 1 || dimension2 > 3) {
            throw new IllegalArgumentException("Параметры dimension1 и dimension2 должны быть равны 1, 2 или 3.\n" +
                    "1 - Градусы Цельсия, 2 - Градусы Фаренгейта 3 - Градусы Кельвина.");
        }

        if (dimension1 == dimension2) {
            return temperature;
        }

        if (dimension1 == 1) {
            if (dimension2 == 2) {
                return temperature * 9 / 5 + 32;
            }

            return temperature + 273.15;
        } else if (dimension1 == 2) {
            if (dimension2 == 1) {
                return (temperature - 32) * 5 / 9;
            }

            return (temperature - 32) * 5 / 9 + 273.15;
        } else {
            if (dimension2 == 1) {
                return temperature - 273.15;
            }

            return (temperature - 273.15) * 9 / 5 + 32;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Temperature");

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setSize(screenSize.width - 1000, screenSize.height - 500);

            Dimension minSize = new Dimension();
            minSize.setSize(500, 350);
            frame.setMinimumSize(minSize);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            frame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            frame.setLocationRelativeTo(null);

            try {
                frame.setIconImage(ImageIO.read(new File("Temperature\\src\\ru\\ilinykh\\images\\1.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }

            JPanel panel = new JPanel();
            GridLayout layout = new GridLayout(5, 2, 10, 40);
            panel.setLayout(layout);

            panel.add(new JLabel());
            panel.add(new JLabel());

            panel.add(new JLabel("Введите значение:", JLabel.CENTER));
            panel.add(new JLabel("Результат:", JLabel.CENTER));

            JTextField enteredValue = new JTextField();
            panel.add(enteredValue);

            JTextField result = new JTextField();
            result.setEditable(false);
            panel.add(result);

            String[] dimensions = {"Гадусы Цельсия (°C)", "Градусы Фаренгейта (°F)", "Градусы Кельвина (К)"};
            JComboBox<String> inComboBox = new JComboBox<>(dimensions);
            panel.add(inComboBox);

            JComboBox<String> outComboBox = new JComboBox<>(dimensions);
            panel.add(outComboBox);

            frame.add(panel, BorderLayout.CENTER);
            frame.add(new JPanel(), BorderLayout.LINE_START);
            frame.add(new JPanel(), BorderLayout.LINE_END);

            ActionListener actionListener = e -> {
                int dimension1 = inComboBox.getSelectedIndex() + 1;
                int dimension2 = outComboBox.getSelectedIndex() + 1;
                double number = 0;

                try {
                    number = Double.parseDouble(enteredValue.getText().replace(",", "."));
                } catch (NumberFormatException n) {
                    JOptionPane.showMessageDialog(null, "Неверные данные, введите число.");
                }

                result.setText(Double.toString(convertTemperature(number, dimension1, dimension2)));
            };

            JButton okButton = new JButton("Ok");
            okButton.addActionListener(actionListener);

            JPanel pageEnd = new JPanel();
            pageEnd.add(okButton, BorderLayout.CENTER);
            frame.add(pageEnd, BorderLayout.PAGE_END);
        });
    }
}