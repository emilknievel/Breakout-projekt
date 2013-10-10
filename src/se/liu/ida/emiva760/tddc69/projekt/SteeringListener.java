package se.liu.ida.emiva760.tddc69.projekt;

import se.liu.ida.emiva760.tddc69.projekt.gameObjects.Paddle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SteeringListener implements KeyListener
{
    private Paddle paddle;
    private GameBoard gameBoard;

    public SteeringListener(GameBoard gameBoard, Paddle paddle) {
	this.gameBoard = gameBoard;
	this.paddle = paddle;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
	int key = keyEvent.getKeyCode();
	if (key == KeyEvent.VK_LEFT) {
	    paddle.setdX(-2); // Go left
	}
	if (key == KeyEvent.VK_RIGHT) {
	    paddle.setdX(2); // Go right
	}
    }

    @Override
    public void keyReleased(KeyEvent keyEvent)
    {
	int key = keyEvent.getKeyCode();
	if(key == KeyEvent.VK_LEFT) {
	    if(paddle.getdX() == -2) {
		paddle.setdX(0);
	    }
	}
	if(key == KeyEvent.VK_RIGHT) {
	    if(paddle.getdX() == 2) {
		paddle.setdX(0);
	    }
	}
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }
}
