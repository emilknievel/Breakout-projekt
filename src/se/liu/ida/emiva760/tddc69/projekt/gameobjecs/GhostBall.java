package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

/**
 * A ball that can move through blocks.
 */
public class GhostBall extends Ball
{
    public GhostBall(double x, double y) {
	super(x, y, "ghostball.png", BallType.GHOST);
    }
}