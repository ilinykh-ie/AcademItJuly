package ru.ilinykh.temperature;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class Temperature {
   TemperatureConverter temperatureConverter;

   public Temperature(){
       temperatureConverter = new TemperatureConverter();
   }

    public  void show() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }


        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Конвертер температур");

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
            GridLayout layout = new GridLayout(3, 2, 10, 40);
            panel.setLayout(layout);

            panel.add(new JLabel("Введите значение:", JLabel.CENTER));
            panel.add(new JLabel("Результат:", JLabel.CENTER));

            JTextField enteredValue = new JTextField();
            panel.add(enteredValue);

            JTextField result = new JTextField();
            result.setEditable(false);
            panel.add(result);

            String[] dimensions = Arrays.stream(Dimensions.values())
                    .map(Dimensions::getRussianName)
                    .toArray(String[]::new);

            JComboBox<String> inComboBox = new JComboBox<>(dimensions);
            panel.add(inComboBox);

            JComboBox<String> outComboBox = new JComboBox<>(dimensions);
            panel.add(outComboBox);

            frame.add(panel, BorderLayout.CENTER);
            frame.add(new JPanel(), BorderLayout.LINE_START);
            frame.add(new JPanel(), BorderLayout.LINE_END);

            ActionListener actionListener = e -> {
                double number = 0;

                try {
                    number = Double.parseDouble(enteredValue.getText().replace(",", "."));
                } catch (NumberFormatException n) {
                    JOptionPane.showMessageDialog(null, "Неверные данные, введите число.");
                }

                result.setText(Double.toString(temperatureConverter.convert(number,
                        Dimensions.getEnum((String) Objects.requireNonNull(inComboBox.getSelectedItem())),
                        Dimensions.getEnum((String) Objects.requireNonNull(outComboBox.getSelectedItem())))));
            };

            JButton okButton = new JButton("Перевести");
            okButton.setPreferredSize(new Dimension(200,70));
            okButton.addActionListener(actionListener);

            JPanel pageEnd = new JPanel();
            pageEnd.add(okButton, BorderLayout.CENTER);
            frame.add(pageEnd, BorderLayout.PAGE_END);
        });
    }
}