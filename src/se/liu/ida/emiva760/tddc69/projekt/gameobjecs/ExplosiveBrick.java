package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

/**
 * A brick that blows up on impact.
 */
public class ExplosiveBrick extends Brick
{
    public ExplosiveBrick(double x, double y) {
	super(x, y, "explosivebrick.png", 1, BrickType.EXPLOSIVE, 100);
    }
}
