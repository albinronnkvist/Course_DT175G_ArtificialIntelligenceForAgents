package me.albinronnkvist.adversarial.utils;

import me.albinronnkvist.adversarial.Board;
import me.albinronnkvist.adversarial.Player;
import me.albinronnkvist.adversarial.Symbol;

public class GameHelper {
    public static boolean isTerminal(Board board) {
        if (BoardHelper.isWin(board, Symbol.X) 
            || BoardHelper.isWin(board, Symbol.O) 
            || BoardHelper.isFull(board)) {
            return true;
        }

        return false;
    }

    public static int utility(Board board, Player player1, Player player2) {
        if (BoardHelper.isWin(board, player1.symbol())) {
            return 1;
        }

        if (BoardHelper.isWin(board, player2.symbol())) {
            return -1;
        }

        return 0;
    }
}
