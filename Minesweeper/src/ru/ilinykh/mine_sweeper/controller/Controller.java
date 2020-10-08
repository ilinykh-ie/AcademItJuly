package ru.ilinykh.mine_sweeper.controller;

import ru.ilinykh.mine_sweeper.model.*;

import java.awt.event.ActionListener;
import java.util.*;

public class Controller {
    private final GameField gameField;
    private final HighScores highScores;
    private final GameTimer gameTimer;

    public Controller(int x, int y, int bombsCount) {
        gameField = new GameField(x, y, bombsCount);
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

    public int getCellParameter(int width, int height) {
        return gameField.getCellParameter(width, height);
    }

    public CellState getCellState(int width, int height) {
        return gameField.getCellState(width, height);
    }

    public int getBombsLeft() {
        return gameField.getBombsLeft();
    }

    public CellsState leftMouseButtonClick(int width, int height) {
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