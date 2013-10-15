package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

import se.liu.ida.emiva760.tddc69.projekt.GameBoard;

/**
 * Powerup that gives one life to the player when a collision occurs.
 */
public class ExtraLifePower extends PowerUp
{
    public ExtraLifePower(double x, double y, GameBoard gameBoard) {
	super(x, y, "power_extralife.png", gameBoard);
    }

    public void usePowerUp() {
	gameBoard.lives += 1;
	gameBoard.livesString = "Lives: " + Integer.toString(gameBoard.lives);
    }
}
