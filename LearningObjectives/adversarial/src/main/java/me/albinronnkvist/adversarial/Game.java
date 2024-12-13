package me.albinronnkvist.adversarial;

import java.util.Scanner;

import me.albinronnkvist.adversarial.agents.MiniMaxAlphaBetaPruningAgent;
import me.albinronnkvist.adversarial.exceptions.InvalidMoveException;
import me.albinronnkvist.adversarial.mappers.ActionMapper;
import me.albinronnkvist.adversarial.utils.GameTerminalHelper;

public class Game {
    private final Player max = new Player("MAX", Symbol.X, PlayerType.HUMAN);
    private final Player min = new Player("MIN", Symbol.O, PlayerType.AGENT);
    private final BoardState boardState = new BoardState();

    public void play() {
        try(var scanner = new Scanner(System.in)) {
            boolean isTerminal = false;
            var currentPlayer = max;

            while (!isTerminal) {
                boardState.print();
                
                makeMove(scanner, currentPlayer);

                isTerminal = GameTerminalHelper.isTerminal(boardState);
                if (!isTerminal) {
                    currentPlayer = changePlayer(currentPlayer);
                }
            }

            var utility = GameTerminalHelper.utility(boardState, max, min);
            System.out.println("\nGame over! Utility: " + utility);
            boardState.print();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void makeMove(Scanner scanner, Player currentPlayer) throws InvalidMoveException {
        if(currentPlayer.type() == PlayerType.HUMAN) {
            while (true) {
                try {
                    System.out.println("Human's turn (" + currentPlayer.symbol() + "). ");
                    System.out.print("Enter a number (0-8) for your move: ");
    
                    int move = Integer.parseInt(scanner.nextLine());
                    if (move < 0 || move > 8) {
                        throw new IllegalArgumentException("Invalid input. Please enter a number between 0 and 8.");
                    }
    
                    boardState.setCell(ActionMapper.convertToAction(move), currentPlayer.symbol());
                    break;
                } catch (InvalidMoveException | IllegalArgumentException e) {
                    System.out.println(e.getMessage() + "\n");
                }
            }
        }

        if(currentPlayer.type() == PlayerType.AGENT) {
            System.out.println("Agent's turn (" + currentPlayer.symbol() + ").");

            // Select your agent here
            // var action = new RandomAgent(boardState).getMove();
            // var action = new MiniMaxAgent(boardState, max, min, currentPlayer).getMove();
            var action = new MiniMaxAlphaBetaPruningAgent(boardState, max, min, currentPlayer).getMove();

            boardState.setCell(action, currentPlayer.symbol());
        }
    }

    private Player changePlayer(Player currentPlayer) {
        return (currentPlayer == max) ? min : max;
    }
}
