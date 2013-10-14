package se.liu.ida.emiva760.tddc69.projekt.gameobjects;

/**
 * A brick that explodes on impact. 
 */
public class ExplosiveBrick extends Brick
{
    public ExplosiveBrick(double x, double y) {
	super(x, y, "explosivebrick.png");
	health = 1;
	type = BrickType.EXPLOSIVE;
    }
}
