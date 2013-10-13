package se.liu.ida.emiva760.tddc69.projekt.gameObjects;

import se.liu.ida.emiva760.tddc69.projekt.*;

public class Ball extends GameObject implements SharedConstants {
    private int xDir;
    private int yDir;
    private int startingHeight = SharedConstants.BALL_STARTY;
    private int startingX = SharedConstants.BALL_STARTX;

    public Ball(double x, double y) {
	super(x, y, "ball.png");
	xDir = 1;
	yDir = -1;
    }


    /**
     * Move the ball and bounce off the top and sides of the board.
     */
    public void move()
    {
	x += xDir;
	y += yDir;

	if (x == 0) {
	    setXDir(1);
	}

	if (x == BALL_RIGHT) {
	    setXDir(-1);
	}

	if (y == 0) {
	    setYDir(1);
	}
    }

    /**
     * Place at starting position
     */
    public void resetState()
    {
	x = startingX;
	y = startingHeight;
    }

    public void setXDir(int x)
    {
	xDir = x;
    }

    public void setYDir(int y)
    {
	yDir = y;
    }

    public int getYDir()
    {
	return yDir;
    }

    public int getStartingHeight() {
	return startingHeight;
    }

    public int getStartingX() {
	return startingX;
    }

    public void flipXDir() {
	xDir = -xDir;
    }

    public void flipYDir() {
    	yDir = -yDir;
    }
}
