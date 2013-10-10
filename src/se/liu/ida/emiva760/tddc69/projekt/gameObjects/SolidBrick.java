package se.liu.ida.emiva760.tddc69.projekt.gameObjects;

/**
 * A solid brick that can withstand multiple hits by the ball.
 */
public class SolidBrick extends Brick
{
    public SolidBrick(double x, double y) {
	super(x, y, "solidbrick");
	health = 3;
    }
}
