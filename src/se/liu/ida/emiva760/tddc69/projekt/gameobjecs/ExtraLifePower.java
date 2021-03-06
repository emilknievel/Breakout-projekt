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

    /**
     * Give the player one extra life and update the score string.
     */
    @Override
    public void usePowerUp() {
	gameBoard.incrementLives();
	gameBoard.setLivesString("Lives: " + Integer.toString(gameBoard.getLives())); // Update livesstring
    }
}
