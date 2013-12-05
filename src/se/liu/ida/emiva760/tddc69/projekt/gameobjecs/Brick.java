package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

/**
 * Superclass for brick objects
 */
public abstract class Brick extends GameObject {
    boolean destroyed; // Whether the brick is destroyed
    protected int health; // Specifies the number of hits the brick can withstand
    protected int score; // The amount of points given to the player when the brick is destroyed
    protected BrickType type = null; // The type of brick

    protected Brick(double x, double y, String brickType, int health) {
	super(x, y, brickType);
	this.health = health;
	destroyed = false;
    }

    public boolean isDestroyed()
    {
	return destroyed;
    }

    /**
     * Make the brick lose one point of health if it has health left.
     * If not, set the brick's destroyed value to the one specified in the parameter.
     * @param destroyed sets the chosen boolean value
     */
    public void setDestroyed(boolean destroyed)
    {
	if (health == 0) {
	    this.destroyed = destroyed;
	} else {
	    health--;
	}
    }

    /**
     * Immediately destroy the brick, regardless of its health.
     */
    public void blowUp() {
	this.destroyed = true;
    }

    public int getHealth() {
	return health;
    }

    public int getScore() {
	return score;
    }

    public BrickType getType() {
	return type;
    }
}
