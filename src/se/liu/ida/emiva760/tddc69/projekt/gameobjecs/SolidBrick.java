package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

/**
 * A solid brick that can withstand multiple hits by the ball.
 */
public class SolidBrick extends Brick
{
    public SolidBrick(double x, double y) {
	super(x, y, "solidbrick.png");
	health = 3;
	type = BrickType.SOLID;
	score = 50; // the number of points added when the brick is destroyed.
    }
}
