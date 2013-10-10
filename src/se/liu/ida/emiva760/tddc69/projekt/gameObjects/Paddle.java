package se.liu.ida.emiva760.tddc69.projekt.gameObjects;

import se.liu.ida.emiva760.tddc69.projekt.SharedConstants;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject implements SharedConstants
{
    private int dX;

    public Paddle(double x, double y) {
	super(x, y, "paddle");
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
    /*public void keyPressed(KeyEvent keyEvent) {

	int key = keyEvent.getKeyCode();

	if (key == KeyEvent.VK_LEFT) {
	    xDir = -2;

	}

	if (key == KeyEvent.VK_RIGHT) {
	    xDir = 2;
	}
    }*/

    /**
     * Stop moving the paddle when letting go of the keys.
     */
    /*public void keyReleased(KeyEvent keyEvent) {
	int key = keyEvent.getKeyCode();

	if (key == KeyEvent.VK_LEFT) {
	    xDir = 0;
	}

	if (key == KeyEvent.VK_RIGHT) {
	    xDir = 0;
	}
    }*/

    /**
     * Place at starting position
     */
    public void resetState() {
	x = SharedConstants.WIDTH / 2 - getWidth();
	y = SharedConstants.BOTTOM - getHeight();
    }

    public void setdX(final int dX) {
	this.dX = dX;
    }

    public int getdX() {
	return dX;
    }
}
