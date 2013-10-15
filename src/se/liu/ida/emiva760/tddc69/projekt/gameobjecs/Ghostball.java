package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

public class GhostBall extends Ball
{
    public GhostBall(double x, double y) {
	super(x, y, "ghostball.png");
    }

    public BallType getType() {
	return BallType.GHOST;
    }
}