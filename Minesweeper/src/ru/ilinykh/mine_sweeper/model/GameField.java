package ru.ilinykh.mine_sweeper.model;

import java.util.Random;

public class GameField {
    private final int[][] field;
    private final CellsState cellsState;
    private final int bombsCount;
    private final int width;
    private final int height;

    public GameField(int width, int height, int bombsCount) {
        this.width = width;
        this.height = height;
        field = new int[height][width];
        cellsState = new CellsState(width, height, bombsCount);
        this.bombsCount = bombsCount;

        Random random = new Random();

        for (int i = 0; i < bombsCount; i++) {
            field[random.nextInt(height)][random.nextInt(width)] = -1;
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (field[i][j] == -1) {
                    continue;
                }

                int bombsAroundCount = 0;

                for (int k = i - 1; k < i + 2; k++) {
                    for (int m = j - 1; m < j + 2; m++) {
                        if (k < 0 || k == height || m < 0 || m == width) {
                            continue;
                        }

                        if (field[k][m] == -1) {
                            bombsAroundCount++;
                        }
                    }
                }

                field[i][j] = bombsAroundCount;
            }
        }
    }

    public int getCellParameter(int width, int height) {
        return field[height][width];
    }

    public int getCellState(int width, int height) {
        return cellsState.getCellState(width, height);
    }

    public CellsState leftMouseButtonClick(int width, int height) {
        if (cellsState.getCellState(width, height) == -1) {
            return null;
        }

        if (cellsState.getCellState(width, height) == 0) {
            cellsState.setCellState(width, height, 1);
            cellsState.setClosedCells(cellsState.getClosedCells() - 1);
        }

        if (field[height][width] == -1) {
            cellsState.setLooseOrWin(-1);
        } else if (bombsCount == cellsState.getClosedCells()) {
            cellsState.setLooseOrWin(1);
        } else if (field[height][width] == 0) {
            openAdjacentCells(width, height);
        }

        return cellsState;
    }

    public void rightMouseButtonClick(int width, int height) {
        if (cellsState.getCellState(width, height) == -1) {
            cellsState.setCellState(width, height, 0);
            cellsState.setBombsLeft(cellsState.getBombsLeft() + 1);
        } else if (cellsState.getCellState(width, height) == 0) {
            cellsState.setCellState(width, height, -1);
            cellsState.setBombsLeft(cellsState.getBombsLeft() - 1);
        }
    }

    private void openAdjacentCells(int width, int height) {
        for (int i = height - 1; i < height + 2; i++) {
            for (int j = width - 1; j < width + 2; j++) {
                if (i < 0 || i == this.height || j < 0 || j == this.width || cellsState.getCellState(j, i) == -1) {
                    continue;
                }

                open(j, i);
            }
        }
    }

    private void open(int width, int height) {
        if (cellsState.getCellState(width, height) == 0) {
            cellsState.setCellState(width, height, 1);
            cellsState.setClosedCells(cellsState.getClosedCells() - 1);

            if (field[height][width] == 0) {
                openAdjacentCells(width, height);
            }

            if (bombsCount == cellsState.getClosedCells()) {
                cellsState.setLooseOrWin(1);
            }
        }
    }

    public int getBombsLeft() {
        return cellsState.getBombsLeft();
    }

    public CellsState middleMouseButtonClick(int width, int height) {
        if (cellsState.getCellState(width, height) != 1) {
            return null;
        }

        int flagsAround = 0;

        for (int i = height - 1; i < height + 2; i++) {
            for (int j = width - 1; j < width + 2; j++) {
                if (i < 0 || i == this.height || j < 0 || j == this.width) {
                    continue;
                }

                if (cellsState.getCellState(j, i) == -1) {
                    flagsAround++;
                }
            }
        }

        if (field[height][width] != flagsAround) {
            return null;
        }

        for (int i = height - 1; i < height + 2; i++) {
            for (int j = width - 1; j < width + 2; j++) {
                if (i < 0 || i == this.height || j < 0 || j == this.width) {
                    continue;
                }

                if (cellsState.getCellState(j, i) == 0) {
                    cellsState.setCellState(j, i, 1);
                    cellsState.setClosedCells(cellsState.getClosedCells() - 1);
                }

                if (field[i][j] == -1 && cellsState.getCellState(j, i) != -1) {
                    cellsState.setLooseOrWin(-1);
                } else if (bombsCount == cellsState.getClosedCells()) {
                    cellsState.setLooseOrWin(1);
                }
            }
        }

        return cellsState;
    }
}