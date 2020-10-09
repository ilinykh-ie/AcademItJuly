package ru.ilinykh.mine_sweeper.model;

import java.util.Random;

public class GameField {
    private final int[][] field;
    private final CellsState cellsState;
    private final int bombsCount;
    private final int width;
    private final int height;

    public GameField(int width, int height, int bombsCount, int firstOpenedWidth, int firstOpenedHeight) {
        this.width = width;
        this.height = height;
        field = new int[height][width];
        cellsState = new CellsState(width, height, bombsCount);
        this.bombsCount = bombsCount;

        Random random = new Random();

        for (int i = 0; i < bombsCount; i++) {
            Integer bombWidthCoordinate = null;
            Integer bombHeightCoordinate = null;

            while (bombWidthCoordinate == null || (bombWidthCoordinate == firstOpenedWidth && bombHeightCoordinate == firstOpenedHeight)) {
                bombWidthCoordinate = random.nextInt(width);
                bombHeightCoordinate = random.nextInt(height);
            }

            field[bombHeightCoordinate][bombWidthCoordinate] = -1;
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

    public CellState getCellState(int width, int height) {
        return cellsState.getCellState(width, height);
    }

    public CellsState leftMouseButtonClick(int width, int height) {
        if (cellsState.getCellState(width, height) == CellState.FLAG) {
            return null;
        }

        if (cellsState.getCellState(width, height) == CellState.CLOSED) {
            cellsState.setCellState(width, height, CellState.OPENED);
            cellsState.setClosedCells(cellsState.getClosedCells() - 1);
        }

        if (field[height][width] == -1) {
            cellsState.setLoseOrWin(GameState.LOSE);
        } else if (bombsCount == cellsState.getClosedCells()) {
            cellsState.setLoseOrWin(GameState.WIN);
        } else if (field[height][width] == 0) {
            openAdjacentCells(width, height);
        }

        return cellsState;
    }

    public CellsState middleMouseButtonClick(int width, int height) {
        if (cellsState.getCellState(width, height) != CellState.OPENED) {
            return null;
        }

        int flagsAround = 0;

        for (int i = height - 1; i < height + 2; i++) {
            for (int j = width - 1; j < width + 2; j++) {
                if (i < 0 || i == this.height || j < 0 || j == this.width) {
                    continue;
                }

                if (cellsState.getCellState(j, i) == CellState.FLAG) {
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

                leftMouseButtonClick(j, i);
            }
        }

        return cellsState;
    }

    public void rightMouseButtonClick(int width, int height) {
        if (cellsState.getCellState(width, height) == CellState.FLAG) {
            cellsState.setCellState(width, height, CellState.CLOSED);
            cellsState.setBombsLeft(cellsState.getBombsLeft() + 1);
        } else if (cellsState.getCellState(width, height) == CellState.CLOSED) {
            cellsState.setCellState(width, height, CellState.FLAG);
            cellsState.setBombsLeft(cellsState.getBombsLeft() - 1);
        }
    }

    private void openAdjacentCells(int width, int height) {
        for (int i = height - 1; i < height + 2; i++) {
            for (int j = width - 1; j < width + 2; j++) {
                if (i < 0 || i == this.height || j < 0 || j == this.width || cellsState.getCellState(j, i) == CellState.FLAG) {
                    continue;
                }

                open(j, i);
            }
        }
    }

    private void open(int width, int height) {
        if (cellsState.getCellState(width, height) == CellState.CLOSED) {
            cellsState.setCellState(width, height, CellState.OPENED);
            cellsState.setClosedCells(cellsState.getClosedCells() - 1);

            if (field[height][width] == 0) {
                openAdjacentCells(width, height);
            }

            if (bombsCount == cellsState.getClosedCells()) {
                cellsState.setLoseOrWin(GameState.WIN);
            }
        }
    }

    public int getBombsLeft() {
        return cellsState.getBombsLeft();
    }
}