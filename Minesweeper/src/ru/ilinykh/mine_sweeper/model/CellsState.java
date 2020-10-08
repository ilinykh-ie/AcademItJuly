package ru.ilinykh.mine_sweeper.model;

import java.util.Arrays;

public class CellsState {
    private final CellState[][] cellsState;
    private GameState loseOrWin;
    private int closedCells;
    private int bombsLeft;

    public CellsState(int width, int height, int bombsCount) {
        cellsState = new CellState[height][width];

        for (CellState[] e : cellsState) {
            Arrays.fill(e, CellState.CLOSED);
        }

        loseOrWin = GameState.CONTINUE;
        closedCells = width * height;
        bombsLeft = bombsCount;
    }

    public CellState getCellState(int width, int height) {
        return cellsState[height][width];
    }

    public void setCellState(int width, int height, CellState cellState) {
        cellsState[height][width] = cellState;
    }

    public int getClosedCells() {
        return closedCells;
    }

    public void setClosedCells(int closedCells) {
        this.closedCells = closedCells;
    }

    public int getBombsLeft() {
        return bombsLeft;
    }

    public void setBombsLeft(int bombsLeft) {
        this.bombsLeft = bombsLeft;
    }

    public GameState getLoseOrWin() {
        return loseOrWin;
    }

    public void setLoseOrWin(GameState gameState) {
        this.loseOrWin = gameState;
    }
}