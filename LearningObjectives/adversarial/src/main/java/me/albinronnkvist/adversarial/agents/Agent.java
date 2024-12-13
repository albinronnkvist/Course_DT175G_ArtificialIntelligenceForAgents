package me.albinronnkvist.adversarial.agents;

import me.albinronnkvist.adversarial.Action;
import me.albinronnkvist.adversarial.exceptions.InvalidMoveException;

public interface Agent {
    Action getMove() throws InvalidMoveException;
}
