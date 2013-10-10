package se.liu.ida.emiva760.tddc69.projekt.gameObjects;

import se.liu.ida.emiva760.tddc69.projekt.GameBoard;

public class ExtraLifePower extends PowerUp
{
    public ExtraLifePower(double x, double y) {
	super(x, y, "power_extralife.png");
    }

    public void usePowerUp(GameBoard gameBoard) {
	gameBoard.lives += 1;
	gameBoard.livesString = "Lives: " + Integer.toString(gameBoard.lives);
    }
}
