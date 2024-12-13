package me.albinronnkvist.adversarial.utils;

import me.albinronnkvist.adversarial.Action;
import me.albinronnkvist.adversarial.Board;
import me.albinronnkvist.adversarial.Symbol;

public class BoardHelper {
    public static boolean isWin(Board board, Symbol symbol) {
        for (int i = 0; i < Board.SIZE; i++) {
            if (isRowWin(board, i, symbol) || isColumnWin(board, i, symbol)) {
                return true;
            }
        }
        return isDiagonalWin(board, symbol) || isAntiDiagonalWin(board, symbol);
    }

    public static boolean isFull(Board board) {
        for (int i = 0; i < Board.SIZE; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                if (board.getCell(new Action(i, j)) == Symbol.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isRowWin(Board board, int row, Symbol symbol) {
        for (int col = 0; col < Board.SIZE; col++) {
            if (board.getCell(new Action(row, col)) != symbol) {
                return false;
            }
        }
        return true;
    }

    private static boolean isColumnWin(Board board, int col, Symbol symbol) {
        for (int row = 0; row < Board.SIZE; row++) {
            if (board.getCell(new Action(row, col)) != symbol) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDiagonalWin(Board board, Symbol symbol) {
        for (int i = 0; i < Board.SIZE; i++) {
            if (board.getCell(new Action(i, i)) != symbol) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAntiDiagonalWin(Board board, Symbol symbol) {
        for (int i = 0; i < Board.SIZE; i++) {
            if (board.getCell(new Action(i, Board.SIZE - 1 - i)) != symbol) {
                return false;
            }
        }
        return true;
    }
}
