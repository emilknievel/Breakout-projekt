package se.liu.ida.emiva760.tddc69.projekt.gameObjects;

import se.liu.ida.emiva760.tddc69.projekt.*;
//TODO: Add more types of balls. Ex: ghost ball, small ball, big ball, extra balls
public class Ball extends GameObject {
    private double xDir;
    private double yDir;
    private int startingHeight = GameBoard.BALL_STARTY;
    private int startingX = GameBoard.BALL_STARTX;

    public Ball(double x, double y) {
	super(x, y, "ball.png");
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

    /**
     * Place at starting position
     */
    public void resetState() {
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

    public double getYDir()
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
