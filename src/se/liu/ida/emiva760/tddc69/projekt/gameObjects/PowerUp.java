package se.liu.ida.emiva760.tddc69.projekt.gameObjects;

import se.liu.ida.emiva760.tddc69.projekt.GameBoard;

public class PowerUp extends GameObject {
    private int yDir;
    protected GameBoard gameBoard;

    public PowerUp(double x, double y, String powerType, GameBoard gameBoard) {
        super(x, y, powerType);
	this.gameBoard = gameBoard;
        yDir = 0;   //should be still until a brick above it is destroyed
    }

    public void move() {
        y += yDir;
    }

    public void triggerFall() {
        yDir = 1;
    }

    public void usePowerUp() {

    }
}
