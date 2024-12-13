package me.albinronnkvist.adversarial;

import java.util.Arrays;

import me.albinronnkvist.adversarial.exceptions.InvalidPlyException;
import me.albinronnkvist.adversarial.validators.BoardValidator;

public class BoardState {
    private final Symbol[][] grid;
    public static final int SIZE = 3;

    public BoardState() {
        this.grid = new Symbol[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            Arrays.fill(grid[i], Symbol.EMPTY);
        }
    }

    public Symbol getCell(Action action) {
        BoardValidator.validateCell(grid, action);
        return grid[action.row()][action.col()];
    }

    public void setCell(Action action, Symbol symbol) throws InvalidPlyException {
        BoardValidator.validateCellOnSet(grid, action);
        grid[action.row()][action.col()] = symbol;
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
}
