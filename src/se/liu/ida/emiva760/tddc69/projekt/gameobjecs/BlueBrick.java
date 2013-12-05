package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

/**
 * A normal brick
 */
public class BlueBrick extends Brick
{
    public BlueBrick(double x, double y) {
	super(x, y, "bluebrick.png", 0);
	type = BrickType.NORMAL;
	score = 100;
    }
}
