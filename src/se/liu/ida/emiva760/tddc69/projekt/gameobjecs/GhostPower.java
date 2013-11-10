package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

import se.liu.ida.emiva760.tddc69.projekt.GameBoard;

/**
 * Converts the first ball into a ghostball.
 */
public class GhostPower extends PowerUp
{
    public GhostPower(final double x, final double y, final GameBoard gameBoard) {
	super(x, y, "power_ghostball.png", gameBoard);
    }

    /**
     * Convert the first ball into a ghostball.
     */
    @Override
    public void usePowerUp() {
	double oldXDir = gameBoard.getBalls().get(0).getXDir();
	double oldYDir = gameBoard.getBalls().get(0).getYDir();

	// Convert the ball
	gameBoard.getBalls().set(0, new GhostBall(gameBoard.getBalls().get(0).getX(), gameBoard.getBalls().get(0).getY()));

	// Make sure that the ball moves in the same direction as before
	gameBoard.getBalls().get(0).setXDir(oldXDir);
	gameBoard.getBalls().get(0).setYDir(oldYDir);
    }
}
