package ru.ilinykh.mine_sweeper.controller;

import ru.ilinykh.mine_sweeper.gui.Icons;
import ru.ilinykh.mine_sweeper.model.*;

import java.awt.event.ActionListener;
import java.util.*;

public class Controller {
    private GameField gameField;
    private final HighScores highScores;
    private final GameTimer gameTimer;
    private final int x;
    private final int y;
    private final int bombsCount;

    public Controller(int x, int y, int bombsCount) {
        this.x = x;
        this.y = y;
        this.bombsCount = bombsCount;
        gameField = null;
        highScores = new HighScores();
        gameTimer = new GameTimer();
    }

    public ArrayList<String> getLeaders() {
        return highScores.getLeaders();
    }

    public void setHighScores(String newLeader) {
        highScores.setLeaders(newLeader);
    }

    public void exit() {
        System.exit(0);
    }

    public String about() {
        return "Сапер. Правила и общие сведения: " +
                System.lineSeparator() + "Число в ячейке показывает, сколько мин скрыто вокруг данной ячейки; " +
                System.lineSeparator() + "Это число поможет понять вам, где находятся безопасные ячейки, а где находятся мины;" +
                System.lineSeparator() + "Если рядом с открытой ячейкой есть пустая ячейка, то она откроется автоматически;" +
                System.lineSeparator() + "Если вы открыли ячейку с миной, то игра проиграна;" +
                System.lineSeparator() + "Что бы пометить ячейку, в которой находится бомба, нажмите её правой кнопкой мыши;" +
                System.lineSeparator() + "Чтобы победить, необходимо открыть все ячейки, в которых нет мин.";
    }

    public Icons getCellParameter(int width, int height) {
        int parameter = gameField.getCellParameter(width, height);

        return switch (parameter) {
            case -1 -> Icons.BOMB;
            case 0 -> Icons.OPENED;
            case 1 -> Icons.ONE;
            case 2 -> Icons.TWO;
            case 3 -> Icons.THREE;
            case 4 -> Icons.FOUR;
            case 5 -> Icons.FIVE;
            case 6 -> Icons.SIX;
            case 7 -> Icons.SEVEN;
            case 8 -> Icons.EIGHT;
            case 9 -> Icons.FLAG;
            case 10 -> Icons.CLOCK;
            default -> Icons.CLOSED;
        };
    }

    public CellState getCellState(int width, int height) {
        if (gameField == null) {
            return null;
        }

        return gameField.getCellState(width, height);
    }

    public int getBombsLeft() {
        return gameField.getBombsLeft();
    }

    public CellsState leftMouseButtonClick(int width, int height) {
        if (gameField == null) {
            gameField = new GameField(x, y, bombsCount, width, height);
        }

        return gameField.leftMouseButtonClick(width, height);
    }

    public void rightMouseButtonClick(int width, int height) {
        gameField.rightMouseButtonClick(width, height);
    }

    public CellsState middleMouseButtonClick(int width, int height) {
        return gameField.middleMouseButtonClick(width, height);
    }

    public void timerStop() {
        gameTimer.timerStop();
    }

    public void timerStart() {
        gameTimer.timerStart();
    }

    public int getTime() {
        return gameTimer.getTime();
    }

    public void setTimerListener(ActionListener timerListener) {
        gameTimer.addTimerListener(timerListener);
    }
}