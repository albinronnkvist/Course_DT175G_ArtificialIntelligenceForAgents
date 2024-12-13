package me.albinronnkvist.adversarial.utils;

import me.albinronnkvist.adversarial.Action;
import me.albinronnkvist.adversarial.BoardState;

public class ActionMapper {
    public static Action convertToAction(int move) {
        int row = (move) / BoardState.SIZE;
        int col = (move) % BoardState.SIZE;
        
        return new Action(row, col);
    }
}
