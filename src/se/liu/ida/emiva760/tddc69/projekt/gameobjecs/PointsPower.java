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

    /**
     * Give the player extrapoints
     */
    @Override
    public void usePowerUp() {
	gameBoard.setScore(gameBoard.getScore() + EXTRAPOINTS);
	gameBoard.setScoreString("Score: " + Integer.toString(gameBoard.getScore()));
    }
}
