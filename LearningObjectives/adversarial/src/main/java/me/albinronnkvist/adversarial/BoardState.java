package me.albinronnkvist.adversarial;

import java.util.Arrays;

import me.albinronnkvist.adversarial.exceptions.InvalidMoveException;
import me.albinronnkvist.adversarial.validators.BoardStateValidator;

public class BoardState {
    private final Symbol[][] grid;
    public static final int SIZE = 3;

    public BoardState() {
        this.grid = new Symbol[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            Arrays.fill(grid[i], Symbol.EMPTY);
        }
    }

    private BoardState(Symbol[][] grid) {
        this.grid = grid;
    }

    public Symbol getCell(Action action) {
        BoardStateValidator.validateCell(grid, action);
        return grid[action.row()][action.col()];
    }

    public void setCell(Action action, Symbol symbol) throws InvalidMoveException {
        BoardStateValidator.validateCellOnSet(grid, action);
        grid[action.row()][action.col()] = symbol;
    }

    public Symbol[] getRow(int row) {
        BoardStateValidator.validateGetRowOrCol(row);
        return grid[row];
    }

    public Symbol[] getColumn(int col) {
        BoardStateValidator.validateGetRowOrCol(col);

        var column = new Symbol[SIZE];
        for (int i = 0; i < SIZE; i++) {
            column[i] = grid[i][col];
        }
        return column;
    }

    public Symbol[] getDiagonalFromTopLeft() {
        var diagonal = new Symbol[SIZE];
        for (int i = 0; i < SIZE; i++) {
            diagonal[i] = grid[i][i];
        }
        return diagonal;
    }

    public Symbol[] getDiagonalFromTopRight() {
        var diagonal = new Symbol[SIZE];
        for (int i = 0; i < SIZE; i++) {
            diagonal[i] = grid[i][SIZE - 1 - i];
        }
        return diagonal;
    }

    public void print() {
        int size = grid.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j] == Symbol.EMPTY ? "-" : grid[i][j]);
                if (j < size - 1) System.out.print(" | ");
            }
            System.out.println();
            if (i < size - 1) System.out.println("---------");
        }
        System.out.println();
    }

    @Override
    public BoardState clone() {
        var clonedGrid = new Symbol[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            clonedGrid[i] = grid[i].clone();
        }

        return new BoardState(clonedGrid);
    }
}
