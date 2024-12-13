package me.albinronnkvist.adversarial.utils;

import me.albinronnkvist.adversarial.Action;
import me.albinronnkvist.adversarial.BoardState;
import me.albinronnkvist.adversarial.Player;
import me.albinronnkvist.adversarial.Symbol;

public class GameTerminalHelper {
    
    /**
     * Check if the game has terminated.
     * The game is terminal if either player has won or the board is full.
     * @param board The game board.
     * @return true if the game has terminated, false otherwise.
     */
    public static boolean isTerminal(BoardState board) {
        if (isWin(board, Symbol.X) 
            || isWin(board, Symbol.O) 
            || isFull(board)) {
            return true;
        }

        return false;
    }

    /**
     * Determines if the search should be cut off based on the depth or terminal state.
     * The search is cut off if the specified depth is zero or if the game is in a terminal state.
     * 
     * @param state The current board state.
     * @param depth The current depth level in the search tree.
     * @return true if the search should be cut off, false otherwise.
     */
    public static boolean isCutoff(BoardState state, int depth) {
        return depth == 0 || GameTerminalHelper.isTerminal(state);
    }

    /**
     * Calculates the utility of a game board.
     * The utility is 1 if MAX has won, -1 if MIN has won, and 0 otherwise.
     * @param board The game board.
     * @param Max The first player.
     * @param Min The second player.
     * @return The utility of the board.
     */
    public static int utility(BoardState board, Player Max, Player Min) {
        if (isWin(board, Max.symbol())) {
            return 1;
        }

        if (isWin(board, Min.symbol())) {
            return -1;
        }

        return 0;
    }

    private static boolean isWin(BoardState board, Symbol symbol) {
        for (int i = 0; i < BoardState.SIZE; i++) {
            if (isRowWin(board, i, symbol) || isColumnWin(board, i, symbol)) {
                return true;
            }
        }
        return isDiagonalWin(board, symbol) || isAntiDiagonalWin(board, symbol);
    }

    private static boolean isFull(BoardState board) {
        for (int i = 0; i < BoardState.SIZE; i++) {
            for (int j = 0; j < BoardState.SIZE; j++) {
                if (board.getCell(new Action(i, j)) == Symbol.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isRowWin(BoardState board, int row, Symbol symbol) {
        for (int col = 0; col < BoardState.SIZE; col++) {
            if (board.getCell(new Action(row, col)) != symbol) {
                return false;
            }
        }
        return true;
    }

    private static boolean isColumnWin(BoardState board, int col, Symbol symbol) {
        for (int row = 0; row < BoardState.SIZE; row++) {
            if (board.getCell(new Action(row, col)) != symbol) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDiagonalWin(BoardState board, Symbol symbol) {
        for (int i = 0; i < BoardState.SIZE; i++) {
            if (board.getCell(new Action(i, i)) != symbol) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAntiDiagonalWin(BoardState board, Symbol symbol) {
        for (int i = 0; i < BoardState.SIZE; i++) {
            if (board.getCell(new Action(i, BoardState.SIZE - 1 - i)) != symbol) {
                return false;
            }
        }
        return true;
    }
}
