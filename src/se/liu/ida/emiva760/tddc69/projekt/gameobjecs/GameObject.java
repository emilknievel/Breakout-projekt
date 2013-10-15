package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.net.URL;

/**
 * Base class for all the game's objects. Contains methods common to all objects.
 */
public abstract class GameObject extends Point2D.Double
{
    // The name of the sprite to be drawn
    private String spriteFileName;

    protected double x;
    protected double y;

    private int width;
    private int height;

    private Image sprite;

    protected GameObject(double x, double y, String spriteFileName) {
	super(x, y);
	this.x = x;
	this.y = y;
	this.spriteFileName = spriteFileName;

	// Loads sprite URL from the sprite name variable
	URL spriteUrl = getClass().getResource("/se/liu/ida/emiva760/tddc69/projekt/resources/" + spriteFileName);

	ImageIcon icon = null;
	if (spriteUrl != null) {
	    icon =  new ImageIcon(spriteUrl);
	} else {
	    System.err.println("Couldn't find file: " + spriteFileName);
	}

	sprite = icon.getImage();

	width = sprite.getWidth(null);
	height = sprite.getHeight(null);
    }

    @Override
    public double getX() {
	return x;
    }

    @Override
    public double getY() {
	return y;
    }

    public void setX(final double x) {
	this.x = x;
    }

    public void setY(final double y) {
	this.y = y;
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public Image getImage()
    {
	return sprite;
    }

    /**
     * The bounding rectangle of the gameObject
     * @return the rectangle
     */
    public Rectangle getRect()
    {
	return new Rectangle((int)x, (int)y,
			     sprite.getWidth(null), sprite.getHeight(null));
    }

    public boolean intersects(GameObject gameObject) {
	return getRect().intersects(gameObject.getRect());
    }

    public boolean intersectsFromSide(GameObject gameObject) {
	return intersects(gameObject) && (((x + width) < gameObject.x) || (x > gameObject.x + gameObject.width));
    }
}
