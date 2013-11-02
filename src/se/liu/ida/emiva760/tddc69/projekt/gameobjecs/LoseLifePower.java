package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

import se.liu.ida.emiva760.tddc69.projekt.GameBoard;

/**
 * Powerup that takes one life off the player.
 */
public class LoseLifePower extends PowerUp
{
    public LoseLifePower(double x, double y, GameBoard gameBoard) {
	super(x, y, "power_loselife.png", gameBoard);
    }

    /**
     * Make the player lose one life, or if its at 0 stop the game.
     */
    @Override
    public void usePowerUp() {
	if (gameBoard.getLives() == 0) {
	    gameBoard.stopGame();
	} else {
	    gameBoard.decrementLives();
	    gameBoard.setLivesString("Lives: " + Integer.toString(gameBoard.getLives()));
	}
    }
}
