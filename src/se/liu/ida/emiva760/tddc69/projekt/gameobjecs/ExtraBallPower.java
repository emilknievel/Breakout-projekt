package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

import se.liu.ida.emiva760.tddc69.projekt.GameBoard;

/**
 * Powerup that adds an extra ball to the game, spawning where the powerup was picked up.
 */
public class ExtraBallPower extends PowerUp
{
    // So that the ball doesn't spawn on the first ball.
    private int yOffset, xOffset = 20;

    public ExtraBallPower(final double x, final double y, final GameBoard gameBoard) {
    	super(x, y, "power_extraball.png", gameBoard);
    }

    @Override
    public void usePowerUp() {
	gameBoard.balls.add(new NormalBall(gameBoard.balls.get(0).getX() + xOffset, gameBoard.balls.get(0).getY() + yOffset));
	gameBoard.numberOfBalls += 1;
    }
}
