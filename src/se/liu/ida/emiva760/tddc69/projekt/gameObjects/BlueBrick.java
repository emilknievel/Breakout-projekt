package se.liu.ida.emiva760.tddc69.projekt.gameobjects;

/**
 * A normal brick
 */
public class BlueBrick extends Brick
{
    public BlueBrick(double x, double y) {
	super(x, y, "bluebrick.png");
	health = 0;
	type = BrickType.NORMAL;
    }
}
