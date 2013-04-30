package liu.ida.emiva760.tddc69.projekt;

import javax.swing.ImageIcon;
import java.net.URL;

public class Brick extends GameObject {
    protected URL brick;

	boolean destroyed;
    private int type;
    private int health;

    public Brick(int x, int y, int type) {
		this.x = x;
		this.y = y;
        this.type = type;

        selectImage(type);

        ImageIcon icon = new ImageIcon(brick);
		image = icon.getImage();

		width = image.getWidth(null);
		height = image.getHeight(null);

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

    private void selectImage(int type) {
        switch (type) {
            case 0:
                brick = Brick.class.getResource("/liu/ida/emiva760/tddc69/projekt/sprites/brick.png");
                break;
            case 1:
                makeSolid();
                break;
            case 2:
                brick = Brick.class.getResource("/liu/ida/emiva760/tddc69/projekt/sprites/explosivebrick.png");
                break;
            default:
                brick = Brick.class.getResource("/liu/ida/emiva760/tddc69/projekt/sprites/brick.png");
                break;
        }
    }

    private void makeSolid() {
        brick = Brick.class.getResource("/liu/ida/emiva760/tddc69/projekt/sprites/solidbrick.png");
        health = 3;
    }

    public int getHealth() {
        return health;
    }

    public int getType() {
        return type;
    }
}
