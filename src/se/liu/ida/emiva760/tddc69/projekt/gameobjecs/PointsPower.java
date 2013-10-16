package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

import se.liu.ida.emiva760.tddc69.projekt.GameBoard;

/**
 * Powerup that gives the player extrapoints on collision.
 */
public class PointsPower extends PowerUp
{
    private static final int EXTRAPOINTS = 300;

    public PointsPower(double x, double y, GameBoard gameBoard) {
	super(x, y, "power_points.png", gameBoard);
    }

    @Override
    public void usePowerUp() {

	gameBoard.score += EXTRAPOINTS;
	gameBoard.scoreString = "Score: " + Integer.toString(gameBoard.score);
    }
}
