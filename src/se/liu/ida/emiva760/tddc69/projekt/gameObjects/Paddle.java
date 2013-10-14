package se.liu.ida.emiva760.tddc69.projekt.gameObjects;

import se.liu.ida.emiva760.tddc69.projekt.SharedConstants;

import java.awt.event.KeyEvent;

/**
 * The paddle that the player controls
 */
public class Paddle extends GameObject
{
    private int dX;

    public Paddle(double x, double y) {
	super(x, y, "paddle.png");
    }

    /**
     * Move the paddle with regards to keys pressed and whether the paddle
     * reaches an edge.
     */
    public void move() {
	x += dX;
	if (x <= 2)
	    x = 2;
	if (x >= SharedConstants.PADDLE_RIGHT)
	    x = SharedConstants.PADDLE_RIGHT;
    }

    /**
     * Move the paddle while the left or right key is pressed.
     */
    public void keyPressed(KeyEvent keyEvent) {

	int key = keyEvent.getKeyCode();

	if (key == KeyEvent.VK_LEFT) {
	    dX = -2;

	}

	if (key == KeyEvent.VK_RIGHT) {
	    dX = 2;
	}
    }

    /**
     * Stop moving the paddle when letting go of the keys.
     */
    public void keyReleased(KeyEvent keyEvent) {
	int key = keyEvent.getKeyCode();

	if (key == KeyEvent.VK_LEFT) {
	    dX = 0;
	}

	if (key == KeyEvent.VK_RIGHT) {
	    dX = 0;
	}
    }

    /**
     * Place at starting position
     */
    public void resetState() {
	x = SharedConstants.PADDLE_STARTX;
	y = SharedConstants.PADDLE_STARTY;
    }

    public void setdX(final int dX) {
	this.dX = dX;
    }

    public int getdX() {
	return dX;
    }
}
