# Octi AI Agent

## Overview

This agent implements a _Monte Carlo Tree Search (MCTS)_ approach for optimal play in the game of __Octi__. When selecting a move, it first checks if any immediately winning action is available. If not, it generates a search tree by repeatedly applying the four MCTS steps: __selection__, __expansion__, __simulation__, and __backpropagation__.
- During __selection__, the agent applies the _UCT_ policy, leveraging the _UCB1_ formula to identify the most promising child node for exploration.
- In __expansion__, if there are any unvisited children, one is selected to generate a new child node.
- During __simulation__, the agent runs a playout with a mix of random moves and heuristic-guided “best moves” until a terminal state is reached or time runs out. A utility function is then used to return the final score of the playout from the perspective of the agent.
- Finally, in __backpropagation__, the score from the simulation is propagated back up the tree to update node rewards and visits.

As time runs out, the agent selects the move from the most-visited child node, which most likely has the highest win rate due to the diminishing exploration term in _UCB1_.

### Utility Function

If a state is terminal, it returns +1 if the agent’s player has won, -1 if lost, or 0 otherwise. 
For non-terminal states, it combines three weighted heuristics: __Material__ (counting pods and prongs), __Positional Pod__ (closer to enemy base is better, based on manhattan distance), and __Positional Prong__ (facing the opponent’s base and having opposing prongs is better). These weighted scores are normalized to ensure they remain within a range of 1 to -1, so they are not deemed better/worse than a win or a loss.

## Build & Run

### 1. Build the Project

Run the following command in the project root to clean and build the project:  
```sh
mvn clean verify
```

### 2. Start the Game

You can start the game using one of the following methods:

#### Option 1: Using the Start Script (Recommended)

Run the provided script and follow the console instructions:
```sh
cd runner
./StartGame.sh
```

#### Option 2: Manual Setup

Alternatively, you can start the game manually with the following steps:

1. **Start the Octi Monitor:** Open a terminal and run:
```java -jar octi-monitor-0.0.4.jar```
    - This will launch the GUI.
    - Manually click _Start Server_ and then _New MultiGame_ before proceeding.

2. **Launch the AI Agent:** Open a new terminal window and run: `java -jar target/agent_ai25_alrn1700.jar -n MyAgent -c red`
    - This starts the custom AI agent named _MyAgent_, playing as the red team.

3. **Launch the opponent Random Agent:** Open another terminal window and run: `java -jar runner/random-playing-agent-0.0.3.jar -n RandomAgent -c black`
    - This starts a simple random-playing agent named RandomAgent, playing as the black team.

The game should now be running—sit back and watch the agents battle it out! 🥊🔥