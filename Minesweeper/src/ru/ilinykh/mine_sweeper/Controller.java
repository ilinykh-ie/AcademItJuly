package ru.ilinykh.mine_sweeper;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.*;

public class Controller {
    private final ActionListener exit;
    private final ActionListener about;
    private final ActionListener highScores;
    private final Model model;
    private final ArrayList<String> leaders;

    public Controller(int x, int y, int bombsCount) {
        this.exit = e -> System.exit(0);

        model = new Model(x, y, bombsCount);

        this.about = e -> JOptionPane.showMessageDialog(null, "Сапер: правила и общие сведения: " +
                        System.lineSeparator() + "Число в ячейке показывает, сколько мин скрыто вокруг данной ячейки. " +
                        System.lineSeparator() + "Это число поможет понять вам, где находятся безопасные ячейки, а где находятся мины." +
                        System.lineSeparator() + "Если рядом с открытой ячейкой есть пустая ячейка, то она откроется автоматически." +
                        System.lineSeparator() + "Если вы открыли ячейку с миной, то игра проиграна." +
                        System.lineSeparator() + "Что бы пометить ячейку, в которой находится бомба, нажмите её правой кнопкой мыши." +
                        System.lineSeparator() + "Чтобы победить, необходимо открыть все ячейки, в которых нет мин.",
                "Справка", JOptionPane.INFORMATION_MESSAGE);

        leaders = new ArrayList<>(10);

        try (Scanner scanner = new Scanner(getClass().getResourceAsStream("/ru/ilinykh/mine_sweeper/resources/Leaders.txt"),
                "windows-1251")) {
            while (scanner.hasNextLine()) {
                leaders.add(scanner.nextLine());
            }
        }

        this.highScores = e -> {
            if (leaders.size() < 1) {
                JOptionPane.showMessageDialog(null, "Победителей пока нет.");
            } else {
                JOptionPane.showMessageDialog(null, leaders.toArray());
            }
        };
    }

    public void setHighScores(String string) {
        if (leaders.size() == 10) {
            leaders.remove(9);
        }

        leaders.add(string);
        Collections.sort(leaders);

        try (PrintWriter writer = new PrintWriter(new File(getClass().getResource
                ("/ru/ilinykh/mine_sweeper/resources/Leaders.txt").getPath()), "windows-1251")) {
            for (String s : leaders) {
                writer.println(s);
            }
        } catch (FileNotFoundException | UnsupportedEncodingException ignored) {
        }
    }

    public ActionListener exit() {
        return exit;
    }

    public ActionListener highScores() {
        return highScores;
    }

    public ActionListener about() {
        return about;
    }

    public MouseListener mouseListener(int x, int y, JButton[][] buttons) {
        ImageIcon closed = new ImageIcon("Minesweeper\\src\\ru\\ilinykh\\mine_sweeper\\resources\\Closed.jpg");
        ImageIcon bomb = new ImageIcon("Minesweeper\\src\\ru\\ilinykh\\mine_sweeper\\resources\\Bomb.jpg");
        ImageIcon opened = new ImageIcon("Minesweeper\\src\\ru\\ilinykh\\mine_sweeper\\resources\\Opened.jpg");
        ImageIcon one = new ImageIcon("Minesweeper\\src\\ru\\ilinykh\\mine_sweeper\\resources\\1.jpg");
        ImageIcon two = new ImageIcon("Minesweeper\\src\\ru\\ilinykh\\mine_sweeper\\resources\\2.jpg");
        ImageIcon three = new ImageIcon("Minesweeper\\src\\ru\\ilinykh\\mine_sweeper\\resources\\3.jpg");
        ImageIcon four = new ImageIcon("Minesweeper\\src\\ru\\ilinykh\\mine_sweeper\\resources\\4.jpg");
        ImageIcon five = new ImageIcon("Minesweeper\\src\\ru\\ilinykh\\mine_sweeper\\resources\\5.jpg");
        ImageIcon six = new ImageIcon("Minesweeper\\src\\ru\\ilinykh\\mine_sweeper\\resources\\6.jpg");
        ImageIcon seven = new ImageIcon("Minesweeper\\src\\ru\\ilinykh\\mine_sweeper\\resources\\7.jpg");
        ImageIcon eight = new ImageIcon("Minesweeper\\src\\ru\\ilinykh\\mine_sweeper\\resources\\8.jpg");
        ImageIcon flag = new ImageIcon("Minesweeper\\src\\ru\\ilinykh\\mine_sweeper\\resources\\Flag.jpg");

        int[][] modelField = model.getField();

        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (modelField[x][y] == -1) {
                        buttons[x][y].setIcon(bomb);
                    } else if (modelField[x][y] == 0) {
                        buttons[x][y].setIcon(opened);
                    } else if (modelField[x][y] == 1) {
                        buttons[x][y].setIcon(one);
                    } else if (modelField[x][y] == 2) {
                        buttons[x][y].setIcon(two);
                    } else if (modelField[x][y] == 3) {
                        buttons[x][y].setIcon(three);
                    } else if (modelField[x][y] == 4) {
                        buttons[x][y].setIcon(four);
                    } else if (modelField[x][y] == 5) {
                        buttons[x][y].setIcon(five);
                    } else if (modelField[x][y] == 6) {
                        buttons[x][y].setIcon(six);
                    } else if (modelField[x][y] == 7) {
                        buttons[x][y].setIcon(seven);
                    } else {
                        buttons[x][y].setIcon(eight);
                    }


                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    if (buttons[x][y].getSelectedIcon().equals(closed)) {
                        buttons[x][y].setIcon(flag);

                    } else {
                        buttons[x][y].setIcon(closed);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

    public int getCondition(int x, int y) {
        return model.getField()[x][y];
    }
}