package se.liu.ida.emiva760.tddc69.projekt.gameobjecs;

import se.liu.ida.emiva760.tddc69.projekt.SpriteNotFoundException;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.net.URL;

/** Base class for all the game's objects. Contains methods common to all objects. */
public abstract class GameObject extends Point2D.Double
{
    private int width;
    private int height;

    private Image sprite;

    protected GameObject(double x, double y, String spriteFileName) {
	super(x, y);

	// Loads sprite URL from the spriteFileName
	URL spriteUrl = getClass().getResource("/se/liu/ida/emiva760/tddc69/projekt/resources/" + spriteFileName);

	ImageIcon icon;
	if (spriteUrl != null) {
	    icon = new ImageIcon(spriteUrl);
	} else {
	    // Throws and exception if the sprite image file is not found
	    throw new SpriteNotFoundException("Sprite not found with name: " + spriteFileName);
	}

	sprite = icon.getImage();

	width = sprite.getWidth(null);
	height = sprite.getHeight(null);
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public Image getImage() {
	return sprite;
    }

    /**
     * The bounding rectangle of the gameObject
     * @return the rectangle
     */
    public Rectangle getRect() {
	return new Rectangle((int) x, (int) y, sprite.getWidth(null), sprite.getHeight(null));
    }

    /**
     * Checks if two objects intersects by comparing their bounding rectangles.
     * @param gameObject is the object to test against.
     * @return whether the rectangles intersect.
     */
    public boolean intersects(GameObject gameObject) {
	return getRect().intersects(gameObject.getRect());
    }

    /**
     * Returns true if the current object is intersects between the highest and lowest part of the other object.
     * @param gameObject is the object to test against.
     * @return if the object intersects from the side.
     */
    public boolean intersectsFromSide(GameObject gameObject) {
	return intersects(gameObject) && (((y + height) < (gameObject.y + gameObject.height)) && (y > gameObject.y));
    }
}
