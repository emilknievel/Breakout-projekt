package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

/**
 * Ball that bounces normally off of bricks.
 */
public class NormalBall extends Ball
{
    public NormalBall(double x, double y) {
	super(x, y, "normalball.png", BallType.NORMAL);
    }
}
