package me.albinronnkvist.adversarial.validators;

import me.albinronnkvist.adversarial.Action;
import me.albinronnkvist.adversarial.Symbol;
import me.albinronnkvist.adversarial.exceptions.InvalidMoveException;

public class BoardStateValidator {
    public static void validateCell(Symbol[][] grid, Action action) {
        if (action.row() < 0 
            || action.row() >= grid.length 
            || action.col() < 0 
            || action.col() >= grid.length) {
            throw new IndexOutOfBoundsException("Invalid cell position.");
        }
    }

    public static void validateCellOnSet(Symbol[][] grid, Action action) throws InvalidMoveException {
        validateCell(grid, action);

        if (grid[action.row()][action.col()] != Symbol.EMPTY) {
            throw new InvalidMoveException("Cell is already occupied.");
        }
    }
}
