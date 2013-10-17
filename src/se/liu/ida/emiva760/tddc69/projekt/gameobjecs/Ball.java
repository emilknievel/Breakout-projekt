package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

import se.liu.ida.emiva760.tddc69.projekt.*;

/**
 * Abstract class for the different kinds of balls.
 */
public abstract class Ball extends GameObject {
    private double xDir;
    private double yDir;
    private int startingHeight = GameBoard.BALL_STARTY;
    private int startingX = GameBoard.BALL_STARTX;

    protected BallType type = null;

    protected Ball(double x, double y, String ballType) {
	super(x, y, ballType);
	xDir = 1;
	yDir = -1;
    }


    /**
     * Move the ball and bounce off the top and sides of the board.
     */
    public void move() {
	x += xDir;
	y += yDir;

	if (x == 0) {
	    flipXDir();
	}

	if ((int)x == GameBoard.BALL_RIGHT) {
	    flipXDir();
	}

	if (y == 0) {
	    flipYDir();
	}
    }

    public void setXDir(double x)
    {
	xDir = x;
    }

    public void setYDir(double y)
    {
	yDir = y;
    }

    public double getXDir()
        {
    	return xDir;
        }

    public double getYDir()
    {
	return yDir;
    }

    public void flipXDir() {
	xDir = -xDir;
    }

    public void flipYDir() {
    	yDir = -yDir;
    }

    public BallType getType() {
	return type;
    }
}
