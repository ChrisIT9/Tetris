package com.company;

public class Tetris {
    private boolean[][] grid;
    private int elementRow, elementColumn;
    private GameState gameState;
    private int score;

    private final int ROWS = 10;
    private final int COLUMNS = 7;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private boolean checkRow(int row) {
        if (row < 0 || row >= ROWS) return false;

        for (int column = 0; column < COLUMNS; column++) if (!this.grid[row][column])
            return false;

        return true;
    }

    private void clearRow(int row) {
        if (row < 0 || row >= ROWS) return;

        for (int column = 0; column < COLUMNS; column++)
            this.grid[row][column] = false;

        if (row >= 1) {
            for (int currentRow = row - 1; currentRow >= 0; currentRow--) {
                for (int column = 0; column < COLUMNS; column++)
                    this.grid[currentRow + 1][column] = this.grid[currentRow][column];
            }
        }

    }

    private boolean isFull(int row, int column) {
        return this.grid[row][column];
    }

    private void moveDown() {
        this.grid[elementRow][elementColumn] = false;
        this.grid[++elementRow][elementColumn] = true;
    }

    private void moveLeft() {
        this.grid[elementRow][elementColumn] = false;
        this.grid[elementRow][--elementColumn] = true;
    }

    private void moveRight() {
        this.grid[elementRow][elementColumn] = false;
        this.grid[elementRow][++elementColumn] = true;
    }

    private void resetElement() {
        this.elementRow = 0;
        this.elementColumn = COLUMNS / 2;
        this.grid[this.elementRow][this.elementColumn] = true;
    }

    public Tetris() {
        this.score = 0;
        this.gameState = GameState.IN_PROGRESS;
        this.grid = new boolean[ROWS][COLUMNS];

        this.elementRow = 0;
        this.elementColumn = COLUMNS / 2;

        for (int row = 0; row < ROWS; row++)
            for (int column = 0; column < COLUMNS; column++) this.grid[row][column] = false;

        this.grid[elementRow][elementColumn] = true;
    }

    public void play(char move) {
        if (this.gameState == GameState.OVER) return;

        boolean needsResetting;

        switch(move) {
            case 'A':
                if (this.elementRow + 1 < ROWS) {
                    if (this.elementColumn - 1 >= 0 && !isFull(elementRow + 1, elementColumn - 1)) {
                        moveLeft();
                        moveDown();
                    } else if (!isFull(elementRow + 1, elementColumn))
                        moveDown();
                }
                break;

            case 'D':
                if (this.elementRow + 1 < ROWS) {
                    if (this.elementColumn + 1 < COLUMNS && !isFull(elementRow + 1, elementColumn + 1)) {
                        moveRight();
                        moveDown();
                    } else if (!isFull(elementRow + 1, elementColumn))
                        moveDown();
                }
                break;

            case 'S':
                if (this.elementRow + 1 < ROWS) {
                    if (!isFull(elementRow + 1, elementColumn)) moveDown();
                }
                break;

            case 'X':
                for (int row = elementRow + 1; row < ROWS; row++) {
                    if (!this.grid[row][elementColumn]) {
                        this.grid[elementRow][elementColumn] = false;
                        this.grid[row][elementColumn] = true;
                        this.elementRow++;
                    }
                }
                break;

            default:
                return;
        }

        needsResetting = elementRow == ROWS - 1 || isFull(elementRow + 1, elementColumn);

        if (checkRow(this.elementRow)) {
            clearRow(this.elementRow);
            score += COLUMNS;
        }

        if (isFull(0, COLUMNS / 2)) gameState = GameState.OVER;

        if (needsResetting && this.gameState != GameState.OVER) resetElement();

    }

    public String toString() {
        String s = "";

        s += ANSI_BLUE + "Punteggio: " + score + ANSI_RESET + "\n";
        for (int row = 0; row < ROWS; row++) {
            s += "[";
            for (int column = 0; column < COLUMNS; column++) {
                s += (row == elementRow && column == elementColumn && this.gameState != GameState.OVER) ? ANSI_RED : ANSI_GREEN;
                s += this.grid[row][column] ? "X" : " ";
                s += ANSI_RESET;
            }
            s += "]\n";
        }
        return s;
    }

    public GameState getGameState() {
        return gameState;
    }
}
