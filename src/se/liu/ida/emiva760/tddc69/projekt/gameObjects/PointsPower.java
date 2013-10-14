package se.liu.ida.emiva760.tddc69.projekt.gameobjects;

import se.liu.ida.emiva760.tddc69.projekt.GameBoard;

/**
 * Powerup that gives the player extrapoints on collision.
 */
public class PointsPower extends PowerUp
{

    public PointsPower(double x, double y, GameBoard gameBoard) {
	super(x, y, "power_points.png", gameBoard);
    }

    public void usePowerUp() {
	final int extraPoints = 300;
	gameBoard.score += extraPoints;
	gameBoard.scoreString = "Score: " + Integer.toString(gameBoard.score);
    }
}
