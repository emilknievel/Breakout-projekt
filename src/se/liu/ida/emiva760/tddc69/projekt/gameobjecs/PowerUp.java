package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

import se.liu.ida.emiva760.tddc69.projekt.GameBoard;

/**
 * Superclass for powerup objects
 */
public abstract class PowerUp extends GameObject
{
    private int yDir;
    protected GameBoard gameBoard;

    protected PowerUp(double x, double y, String powerType, GameBoard gameBoard) {
        super(x, y, powerType);
	this.gameBoard = gameBoard;
        yDir = 0;   //should be still until a brick over it is destroyed
    }

    public void move() {
        y += yDir;
    }

    public void triggerFall() {
        yDir = 1;
    }

    public abstract void usePowerUp();
}
