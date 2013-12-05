package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

/**
 * A solid brick that can withstand multiple hits by the ball.
 */
public class SolidBrick extends Brick
{
    public SolidBrick(double x, double y) {
	super(x, y, "solidbrick.png", 3, BrickType.SOLID, 50);
    }
}
