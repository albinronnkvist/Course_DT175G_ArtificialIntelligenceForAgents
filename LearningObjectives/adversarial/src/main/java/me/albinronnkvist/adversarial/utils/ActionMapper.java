package me.albinronnkvist.adversarial.utils;

import me.albinronnkvist.adversarial.Action;

public class ActionMapper {
    public static Action convertToAction(int move) {
        int row = (move) / 3;
        int col = (move) % 3;
        
        return new Action(row, col);
    }
}
