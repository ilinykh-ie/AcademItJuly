package ru.ilinykh.mine_sweeper.gui;

import ru.ilinykh.mine_sweeper.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Minesweeper {
    private final int x;
    private final int y;
    private final int bombsCount;
    private final Controller controller;
    private final JButton[][] buttons;
    private JPanel field;
    private JFrame window;
    private int closedCells;
    private final ImageIcon closed = new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/Closed.jpg"));
    private final ImageIcon bomb = new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/Bomb.jpg"));
    private final ImageIcon opened = new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/Opened.jpg"));
    private final ImageIcon one = new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/1.jpg"));
    private final ImageIcon two = new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/2.jpg"));
    private final ImageIcon three = new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/3.jpg"));
    private final ImageIcon four = new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/4.jpg"));
    private final ImageIcon five = new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/5.jpg"));
    private final ImageIcon six = new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/6.jpg"));
    private final ImageIcon seven = new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/7.jpg"));
    private final ImageIcon eight = new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/8.jpg"));
    private final ImageIcon flag = new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/Flag.jpg"));
    private final ImageIcon clock = new ImageIcon(getClass().getResource("/ru/ilinykh/mine_sweeper/resources/Clock.jpg"));
    private final Timer timer;
    private int time;
    private final JLabel timePanel;
    private int bombsLeft;
    private JLabel bombsLeftPanel;

    public Minesweeper() {
        this(9, 9, 10);
    }

    public Minesweeper(int x, int y, int bombsCount) {
        this.x = x;
        this.y = y;
        this.bombsCount = bombsCount;
        bombsLeft = bombsCount;

        timePanel = new JLabel();
        timer = new Timer(1000, e -> {
            time++;
            timePanel.setText(Integer.toString(time));
        });

        closedCells = x * y;
        buttons = new JButton[y][x];
        controller = new Controller(x, y, bombsCount);

        field = new JPanel(new GridLayout(y, x, 0, 0));

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].setIcon(closed);
                buttons[i][j].addMouseListener(controller.mouseListener(i, j, buttons));

                field.add(buttons[i][j]);
            }
        }
    }

    private MouseListener getMouseListener(int i, int j) {
        return new MouseAdapter() {
            boolean isPressed = false;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && !isPressed) {
                    buttons[i][j].setEnabled(false);
                    closedCells--;

                    if (controller.getCondition(i, j) == -1) {
                        loose();
                    }else if (closedCells == bombsCount) {
                        win();
                    }

                    if (controller.getCondition(i, j) == 0) {
                        openAdjacentCells(i, j);
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    if (!isPressed) {
                        buttons[i][j].setIcon(flag);
                        isPressed = true;

                        bombsLeft--;
                    } else {
                        buttons[i][j].setIcon(closed);
                        isPressed = false;
                        bombsLeft++;
                    }

                    bombsLeftPanel.setText(Integer.toString(bombsLeft));
                }
            }
        };
    }

    private void openAdjacentCells(int i, int j) {
        if (i == 0 && j == 0) {
            for (int k = i; k < i + 2; k++) {
                for (int l = j; l < j + 2; l++) {
                    open(k, l);
                }
            }
        } else if (i == 0 && j == x - 1) {
            for (int k = i; k < i + 2; k++) {
                for (int l = j - 1; l < j + 1; l++) {
                    if (k == i && j == l) {
                        continue;
                    }

                    open(k, l);
                }
            }
        } else if (i == y - 1 && j == 0) {
            for (int k = i - 1; k < i + 1; k++) {
                for (int l = j; l < j + 2; l++) {
                    if (k == i && j == l) {
                        continue;
                    }

                    open(k, l);
                }
            }
        } else if (i == y - 1 && j == x - 1) {
            for (int k = i - 1; k < i + 1; k++) {
                for (int l = j - 1; l < j + 1; l++) {
                    if (k == i && j == l) {
                        continue;
                    }

                    open(k, l);
                }
            }
        } else if (i == 0) {
            for (int k = i; k < i + 2; k++) {
                for (int l = j - 1; l < j + 2; l++) {
                    if (k == i && j == l) {
                        continue;
                    }

                    open(k, l);
                }
            }
        } else if (i == y - 1) {
            for (int k = i - 1; k < i + 1; k++) {
                for (int l = j - 1; l < j + 2; l++) {
                    if (k == i && j == l) {
                        continue;
                    }

                    open(k, l);
                }
            }
        } else if (j == 0) {
            for (int k = i - 1; k < i + 2; k++) {
                for (int l = j; l < j + 2; l++) {
                    if (k == i && j == l) {
                        continue;
                    }

                    open(k, l);
                }
            }
        } else if (j == x - 1) {
            for (int k = i - 1; k < i + 2; k++) {
                for (int l = j - 1; l < j + 1; l++) {
                    if (k == i && j == l) {
                        continue;
                    }

                    open(k, l);
                }
            }
        } else {
            for (int k = i - 1; k < i + 2; k++) {
                for (int l = j - 1; l < j + 2; l++) {
                    if (k == i && j == l) {
                        continue;
                    }

                    open(k, l);
                }
            }
        }
    }

    private void open(int i, int j) {
        if (buttons[i][j].isEnabled()) {
            buttons[i][j].setEnabled(false);
            closedCells--;

            if (closedCells == bombsCount) {
                win();
            }

            if (controller.getCondition(i, j) == 0) {
                openAdjacentCells(i, j);
            }
        }
    }

    private void win() {
        timer.stop();
        StringBuilder stringBuilder = new StringBuilder();
        String stringTime = String.format("%8d", time);

        stringBuilder.append("Время игры: ").append(stringTime).append(" секунд, количество бомб: ").append(bombsCount);

        String name = JOptionPane.showInputDialog(null, "Введите ваше имя:", "Вы победили!",
                JOptionPane.QUESTION_MESSAGE);
        stringBuilder.append(", имя: ").append(name);

        controller.setHighScores(stringBuilder.toString());

        int result = JOptionPane.showConfirmDialog(null, "Поздравляем, Вы победили! Хотите начать " +
                "новую игру?", "Вы выиграли!", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.NO_OPTION) {
            System.exit(0);
        } else {
            window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            Minesweeper minesweeper = new Minesweeper(x, y, bombsCount);
            minesweeper.show();
        }
    }

    private void loose() {
        timer.stop();

        for (int l = 0; l < y; l++) {
            for (int k = 0; k < x; k++) {
                if (controller.getCondition(l, k) == -1) {
                    buttons[l][k].setEnabled(false);
                }
            }
        }

        int result = JOptionPane.showConfirmDialog(null, "Вы проиграли. Если" +
                        " хотите начать новую игру нажимте YES, если хотите выйти нажмите NO.",
                "Игра проиграна!", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.NO_OPTION) {
            System.exit(0);
        } else {
            Minesweeper minesweeper = new Minesweeper(x, y, bombsCount);
            minesweeper.show();

            window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        }
    }


    public void show() {
        timer.start();

        SwingUtilities.invokeLater(() -> {
            window = new JFrame("Сапер");
            window.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    timer.stop();
                }
            });

            window.setSize(600, 700);
            window.setMinimumSize(new Dimension(300, 350));

            window.setVisible(true);
            window.setLocationRelativeTo(null);

            window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel menu = new JPanel(new GridBagLayout());
            JButton newGame = new JButton("Новая ирга");
            newGame.addActionListener(e -> {
                int result = JOptionPane.showConfirmDialog(null, "Текущая игра не завершена. " +
                                "Начать новую игру?", "Новая игра",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
                    Minesweeper minesweeper = new Minesweeper(x, y, bombsCount);
                    minesweeper.show();
                }
            });

            menu.add(newGame);

            JButton about = new JButton("Справка");
            about.addActionListener(controller.about());
            menu.add(about);

            JButton options = new JButton("Настройки");
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

                if (!(fieldX.getText().equals("") && fieldY.getText().equals("") && fieldBombs.getText().equals(""))) {
                    try {
                        enteredX = Integer.parseInt(fieldX.getText());
                        enteredY = Integer.parseInt(fieldY.getText());
                        enteredBombsCount = Integer.parseInt(fieldBombs.getText());

                    } catch (NumberFormatException n) {
                        JOptionPane.showMessageDialog(null, "Неверные данные, введите числа.");
                        options.doClick();
                    }
                }

                if (!(enteredX == null || enteredY == null || enteredBombsCount == null) && !(enteredX > 8 &&
                        enteredX < 31 && enteredY > 8 && enteredY < 25 && enteredBombsCount > 9 &&
                        enteredBombsCount < enteredX * enteredY * 0.85)) {
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

            menu.add(options);

            JButton highScores = new JButton("Лучшие результаты");
            highScores.addActionListener(controller.highScores());
            menu.add(highScores);

            JButton exit = new JButton("Выход");
            exit.addActionListener(controller.exit());
            menu.add(exit);

            window.add(menu, BorderLayout.PAGE_START);

            field = new JPanel(new GridLayout(y, x, 0, 0));

            for (int i = 0; i < y; i++) {
                for (int j = 0; j < x; j++) {
                    buttons[i][j] = new JButton();
                    buttons[i][j].setBackground(Color.WHITE);
                    buttons[i][j].setIcon(closed);

                    final int conditions = controller.getCondition(i, j);

                    if (conditions == -1) {
                        buttons[i][j].setDisabledIcon(bomb);
                    } else if (conditions == 0) {
                        buttons[i][j].setDisabledIcon(opened);
                    } else if (conditions == 1) {
                        buttons[i][j].setDisabledIcon(one);
                    } else if (conditions == 2) {
                        buttons[i][j].setDisabledIcon(two);
                    } else if (conditions == 3) {
                        buttons[i][j].setDisabledIcon(three);
                    } else if (conditions == 4) {
                        buttons[i][j].setDisabledIcon(four);
                    } else if (conditions == 5) {
                        buttons[i][j].setDisabledIcon(five);
                    } else if (conditions == 6) {
                        buttons[i][j].setDisabledIcon(six);
                    } else if (conditions == 7) {
                        buttons[i][j].setDisabledIcon(seven);
                    } else {
                        buttons[i][j].setDisabledIcon(eight);
                    }

                    buttons[i][j].addMouseListener(getMouseListener(i, j));
                    field.add(buttons[i][j]);
                }
            }

            window.add(field, BorderLayout.CENTER);

            JPanel lowerPanel = new JPanel(new GridLayout(1, 4, 0, 0));
            lowerPanel.setSize(200, 100);
            lowerPanel.add(new JLabel(clock));
            bombsLeftPanel = new JLabel();
            bombsLeftPanel.setText(Integer.toString(bombsCount));
            bombsLeftPanel.setHorizontalAlignment(SwingConstants.RIGHT);
            lowerPanel.add(timePanel);
            lowerPanel.add(bombsLeftPanel);

            lowerPanel.add(new JLabel(bomb));
            lowerPanel.setPreferredSize(new Dimension(300, 50));

            window.add(lowerPanel, BorderLayout.PAGE_END);

            if (Frame.getFrames().length < 1) {
                System.exit(0);
            }
        });
    }
}