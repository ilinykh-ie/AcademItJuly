package ru.ilinykh.mine_sweeper;

import java.util.Random;

class Model {
    private final int[][] field;

    public Model(int x, int y, int bombsCount) {
        field = new int[y][x];

        Random random = new Random();

        for (int i = 0; i < bombsCount; i++) {
            field[random.nextInt(y)][random.nextInt(x)] = -1;
        }

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (field[i][j] == -1) {
                    continue;
                }

                int bombsAroundCount = 0;

                if (i == 0 && j == 0) {
                    for (int k = i; k < i + 2; k++) {
                        for (int l = j; l < j + 2; l++) {
                            if (field[k][l] == -1) {
                                bombsAroundCount++;
                            }
                        }
                    }
                } else if (i == 0 && j == x - 1) {
                    for (int k = i; k < i + 2; k++) {
                        for (int l = j - 1; l < j + 1; l++) {
                            if (field[k][l] == -1) {
                                bombsAroundCount++;
                            }
                        }
                    }
                } else if (i == y - 1 && j == 0) {
                    for (int k = i - 1; k < i + 1; k++) {
                        for (int l = j; l < j + 2; l++) {
                            if (field[k][l] == -1) {
                                bombsAroundCount++;
                            }
                        }
                    }
                } else if (i == y - 1 && j == x - 1) {
                    for (int k = i - 1; k < i + 1; k++) {
                        for (int l = j - 1; l < j + 1; l++) {
                            if (field[k][l] == -1) {
                                bombsAroundCount++;
                            }
                        }
                    }
                } else if (i == 0) {
                    for (int k = i; k < i + 2; k++) {
                        for (int l = j - 1; l < j + 2; l++) {
                            if (field[k][l] == -1) {
                                bombsAroundCount++;
                            }
                        }
                    }
                } else if (i == y - 1) {
                    for (int k = i - 1; k < i + 1; k++) {
                        for (int l = j - 1; l < j + 2; l++) {
                            if (field[k][l] == -1) {
                                bombsAroundCount++;
                            }
                        }
                    }
                } else if (j == 0) {
                    for (int k = i - 1; k < i + 2; k++) {
                        for (int l = j; l < j + 2; l++) {
                            if (field[k][l] == -1) {
                                bombsAroundCount++;
                            }
                        }
                    }
                } else if (j == x - 1) {
                    for (int k = i - 1; k < i + 2; k++) {
                        for (int l = j - 1; l < j + 1; l++) {
                            if (field[k][l] == -1) {
                                bombsAroundCount++;
                            }
                        }
                    }
                } else {
                    for (int k = i - 1; k < i + 2; k++) {
                        for (int l = j - 1; l < j + 2; l++) {
                            if (field[k][l] == -1) {
                                bombsAroundCount++;
                            }
                        }
                    }
                }

                field[i][j] = bombsAroundCount;
            }
        }
    }

    public int[][] getField() {
        return field;
    }
}