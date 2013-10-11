package se.liu.ida.emiva760.tddc69.projekt.gameObjects;

import se.liu.ida.emiva760.tddc69.projekt.GameBoard;

public class ExtraLifePower extends PowerUp
{
    public ExtraLifePower(double x, double y, GameBoard gameBoard) {
	super(x, y, "power_extralife.png", gameBoard);
    }

    public void usePowerUp() {
	gameBoard.lives += 1;
	gameBoard.livesString = "Lives: " + Integer.toString(gameBoard.lives);
    }
}
