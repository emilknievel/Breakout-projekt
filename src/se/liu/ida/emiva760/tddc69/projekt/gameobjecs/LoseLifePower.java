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

    @Override
    public void usePowerUp() {
	if (gameBoard.lives == 0) {
	    gameBoard.stopGame();
	} else {
	    gameBoard.lives -= 1;
	    gameBoard.livesString = "Lives: " + Integer.toString(gameBoard.lives);
	}
    }
}
