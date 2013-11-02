package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

import se.liu.ida.emiva760.tddc69.projekt.GameBoard;

import java.awt.event.KeyEvent;

/**
 * The paddle that the player controls
 */
public class Paddle extends GameObject
{
    private int xDir;

    public Paddle(double x, double y) {
	super(x, y, "paddle.png");
    }

    /**
     * Move the paddle with regards to keys pressed and whether the paddle
     * reaches an edge.
     */
    public void move() {
	x += xDir;
	if (x <= 2)
	    x = 2;
	if (x >= GameBoard.PADDLE_RIGHT)
	    x = GameBoard.PADDLE_RIGHT;
    }

    /**
     * Move the paddle while the left or right key is pressed.
     */
    public void keyPressed(KeyEvent keyEvent) {

	int key = keyEvent.getKeyCode();

	if (key == KeyEvent.VK_LEFT) {
	    xDir = -2;
	}

	if (key == KeyEvent.VK_RIGHT) {
	    xDir = 2;
	}
    }

    /**
     * Stop moving the paddle when letting go of the keys.
     */
    public void keyReleased(KeyEvent keyEvent) {
	int key = keyEvent.getKeyCode();

	if (key == KeyEvent.VK_LEFT) {
	    xDir = 0;
	}

	if (key == KeyEvent.VK_RIGHT) {
	    xDir = 0;
	}
    }

    /**
     * Place at starting position
     */
    public void resetState() {
	x = GameBoard.PADDLE_STARTX;
	y = GameBoard.PADDLE_STARTY;
    }
}
