package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

import se.liu.ida.emiva760.tddc69.projekt.GameBoard;

public class GhostPower extends PowerUp
{
    public GhostPower(final double x, final double y, final GameBoard gameBoard) {
	super(x, y, "power_ghostball.png", gameBoard);
    }

    @Override
    public void usePowerUp() {
	double oldXDir = gameBoard.balls.get(0).getXDir();
	double oldYDir = gameBoard.balls.get(0).getYDir();
	gameBoard.balls.set(0, new GhostBall(gameBoard.balls.get(0).getX(), gameBoard.balls.get(0).getY()));
	gameBoard.balls.get(0).setXDir(oldXDir);
	gameBoard.balls.get(0).setYDir(oldYDir);
    }
}
