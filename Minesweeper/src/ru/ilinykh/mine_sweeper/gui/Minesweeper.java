package ru.ilinykh.mine_sweeper.gui;

import ru.ilinykh.mine_sweeper.controller.Controller;
import ru.ilinykh.mine_sweeper.model.CellState;
import ru.ilinykh.mine_sweeper.model.CellsState;
import ru.ilinykh.mine_sweeper.model.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Minesweeper {
    private final int width;
    private final int height;
    private final int bombsCount;
    private final Controller controller;
    private final JButton[][] buttons;
    private JPanel field;
    private JFrame window;
    private final HashMap<Icons, ImageIcon> icons;
    private final JLabel timePanel;
    private JLabel bombsLeftPanel;
    private boolean isAppointed;

    public Minesweeper() {
        this(9, 9, 10);
    }

    public Minesweeper(int width, int height, int bombsCount) {
        this.width = width;
        this.height = height;
        this.bombsCount = bombsCount;

        controller = new Controller(width, height, bombsCount);

        timePanel = new JLabel();
        timePanel.setText(Integer.toString(0));
        controller.setTimerListener(e -> timePanel.setText(Integer.toString(controller.getTime())));

        buttons = new JButton[height][width];
        field = new JPanel(new GridLayout(height, width, 0, 0));

        icons = new HashMap<>();
        icons.put(Icons.BOMB, new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/Bomb.jpg")));
        icons.put(Icons.OPENED, new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/Opened.jpg")));
        icons.put(Icons.ONE, new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/1.jpg")));
        icons.put(Icons.TWO, new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/2.jpg")));
        icons.put(Icons.THREE, new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/3.jpg")));
        icons.put(Icons.FOUR, new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/4.jpg")));
        icons.put(Icons.FIVE, new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/5.jpg")));
        icons.put(Icons.SIX, new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/6.jpg")));
        icons.put(Icons.SEVEN, new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/7.jpg")));
        icons.put(Icons.EIGHT, new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/8.jpg")));
        icons.put(Icons.FLAG, new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/Flag.jpg")));
        icons.put(Icons.CLOCK, new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/Clock.jpg")));
        icons.put(Icons.CLOSED, new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/Closed.jpg")));
    }

    private MouseListener getMouseListener(int width, int height) {
        return new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                controller.timerStart();

                CellsState cellsState = null;

                if (e.getButton() == MouseEvent.BUTTON1) {
                    cellsState = controller.leftMouseButtonClick(width, height);

                    if (!isAppointed) {
                        isAppointed = true;

                        for (int i = 0; i < Minesweeper.this.height; i++) {
                            for (int j = 0; j < Minesweeper.this.width; j++) {
                                buttons[i][j].setDisabledIcon(icons.get(controller.getCellParameter(j, i)));
                            }
                        }
                    }

                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    if (controller.getCellState(width, height) != null) {
                        controller.rightMouseButtonClick(width, height);

                        if (controller.getCellState(width, height) == CellState.CLOSED) {
                            buttons[height][width].setIcon(icons.get(Icons.CLOSED));
                        } else if (controller.getCellState(width, height) == CellState.FLAG) {
                            buttons[height][width].setIcon(icons.get(Icons.FLAG));
                        }

                        bombsLeftPanel.setText(Integer.toString(controller.getBombsLeft()));
                    }
                } else if (e.getButton() == MouseEvent.BUTTON2) {
                    cellsState = controller.middleMouseButtonClick(width, height);
                }

                if (cellsState != null) {
                    for (int i = 0; i < Minesweeper.this.height; i++) {
                        for (int j = 0; j < Minesweeper.this.width; j++) {
                            if (cellsState.getCellState(j, i) == CellState.OPENED) {
                                buttons[i][j].setEnabled(false);
                            }
                        }
                    }

                    if (cellsState.getLoseOrWin() == GameState.LOSE) {
                        loose();
                    } else if (cellsState.getLoseOrWin() == GameState.WIN) {
                        win();
                    }
                }
            }
        };
    }

    private void win() {
        controller.timerStop();
        StringBuilder stringBuilder = new StringBuilder();
        String stringTime = String.format("%3d", controller.getTime());

        stringBuilder.append("Время игры: ").append(stringTime).append(" секунд, количество бомб: ").append(bombsCount);

        String name = JOptionPane.showInputDialog(null, "Введите ваше имя:", "Вы победили!",
                JOptionPane.QUESTION_MESSAGE);
        stringBuilder.append(", имя: ").append(name);

        controller.setHighScores(stringBuilder.toString());

        int result = JOptionPane.showConfirmDialog(null, "Поздравляем, Вы победили! Хотите начать " +
                "новую игру?", "Вы выиграли!", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.NO_OPTION) {
            controller.exit();
        } else {
            window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            Minesweeper minesweeper = new Minesweeper(width, height, bombsCount);
            minesweeper.show();
        }
    }

    private void loose() {
        controller.timerStop();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (controller.getCellParameter(j, i) == Icons.BOMB) {
                    buttons[i][j].setEnabled(false);
                }
            }
        }

        int result = JOptionPane.showConfirmDialog(null, "Вы проиграли. Если" +
                        " хотите начать новую игру нажмите \"Да\", если хотите выйти нажмите \"Нет\".",
                "Игра проиграна!", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.NO_OPTION) {
            controller.exit();
        } else {
            Minesweeper minesweeper = new Minesweeper(width, height, bombsCount);
            minesweeper.show();

            window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void show() {
        SwingUtilities.invokeLater(() -> {
            UIManager.put("OptionPane.yesButtonText", "Да");
            UIManager.put("OptionPane.noButtonText", "Нет");
            UIManager.put("OptionPane.cancelButtonText", "Отмена");

            window = new JFrame("Сапер");
            window.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    controller.timerStop();
                }
            });

            int windowWidth = width * 30;
            int windowHeight = height * 40;

            if (height > 20) {
                windowHeight = height * 33;
            }

            window.setSize(400, 500);
            window.setMinimumSize(new Dimension(windowWidth, windowHeight));
            window.setVisible(true);
            window.setLocationRelativeTo(null);
            window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JMenuBar menu = new JMenuBar();

            JMenu game = new JMenu("Игра");
            game.setPreferredSize(new Dimension(60, 20));
            menu.add(game);

            JMenuItem newGame = new JMenuItem("Новая игра");
            newGame.setAccelerator(KeyStroke.getKeyStroke("F2"));
            newGame.addActionListener(e -> {
                int result = JOptionPane.showConfirmDialog(null, "Текущая игра не завершена. " +
                                "Начать новую игру?", "Новая игра",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
                    Minesweeper minesweeper = new Minesweeper(width, height, bombsCount);
                    minesweeper.show();
                }
            });

            game.add(newGame);

            JMenu about = new JMenu("Справка");
            about.setPreferredSize(new Dimension(60, 20));
            menu.add(about);

            JMenuItem info = new JMenuItem("Правила игры");
            info.setAccelerator(KeyStroke.getKeyStroke("F1"));
            info.addActionListener(e -> JOptionPane.showMessageDialog(null, controller.about(),
                    "Справка", JOptionPane.INFORMATION_MESSAGE));
            about.add(info);

            JMenuItem options = new JMenuItem("Настройки");
            options.setAccelerator(KeyStroke.getKeyStroke("F5"));
            options.addActionListener(e -> {
                Integer enteredX = null;
                Integer enteredY = null;
                Integer enteredBombsCount = null;

                JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
                JTextField fieldX = new JTextField();
                JTextField fieldY = new JTextField();
                JTextField fieldBombs = new JTextField();
                panel.add(new JLabel("Высота (от 9 до 24): "));
                panel.add(fieldY);
                panel.add(new JLabel("Ширина (от 9 до 30): "));
                panel.add(fieldX);
                panel.add(new JLabel("Число мин (от 10 до 85% поля): "));
                panel.add(fieldBombs);
                JOptionPane.showMessageDialog(null, panel, "Настройи", JOptionPane.QUESTION_MESSAGE);

                if (!fieldX.getText().equals("") || !fieldY.getText().equals("") || !fieldBombs.getText().equals("")) {
                    try {
                        enteredX = Integer.parseInt(fieldX.getText());
                        enteredY = Integer.parseInt(fieldY.getText());
                        enteredBombsCount = Integer.parseInt(fieldBombs.getText());

                    } catch (NumberFormatException n) {
                        JOptionPane.showMessageDialog(null, "Неверные данные, введите числа.");
                        options.doClick();
                    }
                }

                if ((enteredX != null && enteredY != null && enteredBombsCount != null) && (enteredX < 9 ||
                        enteredX > 30 || enteredY < 9 || enteredY > 24 || enteredBombsCount < 9 ||
                        enteredBombsCount > enteredX * enteredY * 0.85)) {
                    JOptionPane.showMessageDialog(null, "Неверные данные, ширина должна " +
                            "быть от 9 до 30, высота дожна быть от 9 до 24, количество бомб должно быть от 10 до "
                            + Math.round(enteredX * enteredY * 0.85) + ".");
                    options.doClick();
                } else if (enteredX != null && enteredY != null && enteredBombsCount != null) {
                    window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
                    Minesweeper minesweeper = new Minesweeper(enteredX, enteredY, enteredBombsCount);
                    minesweeper.show();
                }
            });

            game.add(options);

            JMenuItem highScores = new JMenuItem("Лучшие результаты");
            highScores.setAccelerator(KeyStroke.getKeyStroke("F4"));
            highScores.addActionListener(e -> {
                ArrayList<String> leaders = controller.getLeaders();

                if (leaders.size() < 1) {
                    JOptionPane.showMessageDialog(null, "Победителей пока нет.",
                            "Лучшие результаты", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, leaders.toArray(), "Лучшие результаты",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });

            about.add(highScores);

            JMenuItem exit = new JMenuItem("Выход");
            exit.setAccelerator(KeyStroke.getKeyStroke("F10"));
            exit.addActionListener(e -> controller.exit());
            game.add(exit);

            window.add(menu, BorderLayout.PAGE_START);

            field = new JPanel(new GridLayout(height, width, 0, 0));

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    buttons[i][j] = new JButton();
                    buttons[i][j].setBackground(Color.WHITE);
                    buttons[i][j].setIcon(icons.get(Icons.CLOSED));
                    buttons[i][j].addMouseListener(getMouseListener(j, i));
                    field.add(buttons[i][j]);
                }
            }

            window.add(field, BorderLayout.CENTER);

            JPanel lowerPanel = new JPanel(new GridLayout(1, 4, 0, 0));
            lowerPanel.setSize(200, 100);
            lowerPanel.add(new JLabel(icons.get(Icons.CLOCK)));
            bombsLeftPanel = new JLabel();
            bombsLeftPanel.setText(Integer.toString(bombsCount));
            bombsLeftPanel.setHorizontalAlignment(SwingConstants.RIGHT);

            lowerPanel.add(timePanel);
            lowerPanel.add(bombsLeftPanel);

            lowerPanel.add(new JLabel(icons.get(Icons.BOMB)));
            lowerPanel.setPreferredSize(new Dimension(300, 50));

            window.add(lowerPanel, BorderLayout.PAGE_END);

            if (Frame.getFrames().length < 1) {
                controller.exit();
            }
        });
    }
}