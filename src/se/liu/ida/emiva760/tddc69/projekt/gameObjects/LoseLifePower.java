package se.liu.ida.emiva760.tddc69.projekt.gameObjects;

import se.liu.ida.emiva760.tddc69.projekt.GameBoard;

/**
 * Created with IntelliJ IDEA. User: Emil Date: 2013-10-09 Time: 20:11 To change this template use File | Settings | File
 * Templates.
 */
public class LoseLifePower extends PowerUp
{
    public LoseLifePower(double x, double y) {
	super(x, y, "power_loselife.png");
    }

    public void usePowerUp(GameBoard gameBoard) {
	if (gameBoard.lives == 0) {
	    gameBoard.stopGame();
	} else {
	    gameBoard.lives -= 1;
	    gameBoard.livesString = "Lives: " + Integer.toString(gameBoard.lives);
	}
    }
}
