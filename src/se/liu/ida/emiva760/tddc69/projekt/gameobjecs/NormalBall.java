package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

public class NormalBall extends Ball
{
    public NormalBall(double x, double y) {
	super(x, y, "normalball.png");
    }

    public BallType getType() {
	return BallType.NORMAL;
    }
}
