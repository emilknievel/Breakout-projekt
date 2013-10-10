package se.liu.ida.emiva760.tddc69.projekt.gameObjects;

import se.liu.ida.emiva760.tddc69.projekt.GameBoard;

public class PowerUp extends GameObject {
    private int yDir;

    public PowerUp(double x, double y, String powerType) {
        super(x, y, powerType);
        yDir = 0;   //should be still until a brick above it is destroyed
    }

    public void move() {
        y += yDir;
    }

    public void triggerFall() {
        yDir = 1;
    }

    public void usePowerUp(GameBoard gameBoard) {

    }
}
