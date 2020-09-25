package ru.ilinykh.mine_sweeper.model;

public class CellsState {
    private final int[][] cellState; //0 - ячейка закрыта, -1 - флаг, 1 - открыта.
    private int looseOrWin;          //0 - игра не закончена. -1 - поражение, 1 - победа
    private int closedCells;
    private int bombsLeft;

    public CellsState(int width, int height, int bombsCount) {
        cellState = new int[height][width];
        closedCells = width * height;
        bombsLeft = bombsCount;
    }

    public int getCellState(int width, int height) {
        return cellState[height][width];
    }

    public void setCellState(int width, int height, int state) {
        cellState[height][width] = state;
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

    public int getLooseOrWin() {
        return looseOrWin;
    }

    public void setLooseOrWin(int looseOrWin) {
        this.looseOrWin = looseOrWin;
    }
}