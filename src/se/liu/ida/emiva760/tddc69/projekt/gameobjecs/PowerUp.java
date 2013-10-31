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

    /**
     * Tell the powerup to fall down.
     */
    public void triggerFall() {
        yDir = 1;
    }

    /**
     * What the powerup should do when picked up. Specified by the different powerups
     */
    public abstract void usePowerUp();
}
