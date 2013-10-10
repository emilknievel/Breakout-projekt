package se.liu.ida.emiva760.tddc69.projekt.gameObjects;

import se.liu.ida.emiva760.tddc69.projekt.GameBoard;

public class PointsPower extends PowerUp
{

    public PointsPower(double x, double y) {
	super(x, y, "power_points.png");
    }

    public void usePowerUp(GameBoard gameBoard) {
	final int extraPoints = 300;
	gameBoard.score += extraPoints;
	gameBoard.scoreString = "Score: " + Integer.toString(gameBoard.score);
    }
}
