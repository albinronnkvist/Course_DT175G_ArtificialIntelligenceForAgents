package me.albinronnkvist.adversarial;

import java.util.Scanner;

import me.albinronnkvist.adversarial.exceptions.InvalidPlyException;
import me.albinronnkvist.adversarial.utils.ActionMapper;
import me.albinronnkvist.adversarial.utils.GameHelper;

public class Game {
    private final Player player1 = new Player("MAX", Symbol.X, PlayerType.HUMAN);
    private final Player player2 = new Player("MIN", Symbol.O, PlayerType.HUMAN);
    private final Board board = new Board();

    public void play() {
        try(var scanner = new Scanner(System.in)) {
            boolean isTerminal = false;
            var currentPlayer = player1;

            while (!isTerminal) {
                board.printBoard();
                
                makeMove(scanner, currentPlayer);

                isTerminal = GameHelper.isTerminal(board);
                if (!isTerminal) {
                    currentPlayer = toMove(currentPlayer);
                }
            }

            var utility = GameHelper.utility(board, player1, player2);
            System.out.println("\nGame over! Utility: " + utility);
            board.printBoard();
        }
    }

    public void makeMove(Scanner scanner, Player currentPlayer) {
        if(currentPlayer.type() == PlayerType.HUMAN) {
            while (true) {
                try {
                    System.out.println("Human's turn (" + currentPlayer.symbol() + "). ");
                    System.out.print("Enter a number (0-8) for your move: ");
    
                    int move = Integer.parseInt(scanner.nextLine());
                    if (move < 0 || move > 8) {
                        throw new IllegalArgumentException("Invalid input. Please enter a number between 0 and 8.");
                    }
    
                    board.setCell(ActionMapper.convertToAction(move), currentPlayer.symbol());
                    break;
                } catch (InvalidPlyException | IllegalArgumentException e) {
                    System.out.println(e.getMessage() + "\n");
                }
            }
        }

        if(currentPlayer.type() == PlayerType.AGENT) {
            System.out.println("Agent's turn (" + currentPlayer.symbol() + ").");
            // TODO: 
        }
    }

    private Player toMove(Player currentPlayer) {
        return (currentPlayer == player1) ? player2 : player1;
    }
}
