package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

import se.liu.ida.emiva760.tddc69.projekt.*;

/**
 * Abstract class for the different kinds of balls.
 */
public abstract class Ball extends GameObject {
    private double xDir; // Positive value means move right, negative left.
    private double yDir; // Positive value means move down, negative up.

    protected BallType type = null; // The type of ball

    protected Ball(double x, double y, String ballType, BallType type) {
	super(x, y, ballType); // ballType is the name of the sprite png file
	this.type = type;
	// The ball should move up-right when spawned.
	xDir = 1;
	yDir = -1;
    }


    /**
     * Move the ball and bounce off the top and sides of the board.
     */
    public void move() {
	x += xDir;
	y += yDir;

	// The ball bounces off the left wall.
	if (x == 0) {
	    flipXDir();
	}

	// The ball bounces off the right wall.
	if ((int)x == GameBoard.BALL_RIGHT) {
	    flipXDir();
	}

	// The ball bounces off the top of the board.
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

    /**
     * Flip the ball's horizontal direction.
     */
    public void flipXDir() {
	xDir = -xDir;
    }

    /**
     * Flip the ball's vertical direction.
     */
    public void flipYDir() {
    	yDir = -yDir;
    }

    public BallType getType() {
	return type;
    }
}
