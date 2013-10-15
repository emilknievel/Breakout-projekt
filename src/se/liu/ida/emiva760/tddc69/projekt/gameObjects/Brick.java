package se.liu.ida.emiva760.tddc69.projekt.gameObjects;

/**
 * Superclass for brick objects
 */
public abstract class Brick extends GameObject {
    boolean destroyed;
    protected int health;
    protected BrickType type = null;

    protected Brick(double x, double y, String brickType) {
	super(x, y, brickType);

	destroyed = false;
    }

    public boolean isDestroyed()
    {
	return destroyed;
    }

    public void setDestroyed(boolean destroyed)
    {
	if (health == 0) {
	    this.destroyed = destroyed;
	} else {
	    health--;
	}
    }

    public void blowUp() {
	this.destroyed = true;
    }

    public int getHealth() {
	return health;
    }

    public BrickType getType() {
	return type;
    }
}
