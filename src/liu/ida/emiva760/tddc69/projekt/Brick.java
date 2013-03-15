package liu.ida.emiva760.tddc69.projekt;

//TODO: Change this class so that it only contains what's common for the different brick types
//TODO: Perhaps I should make a BrickMaker type class that creates different brick types

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
		this.destroyed = destroyed;
	}

    private void selectImage(int type) {
        switch (type) {
            case 0:
                brick = Brick.class.getResource("/liu/ida/emiva760/tddc69/projekt/sprites/brick.png");
                break;
            case 1:
                brick = Brick.class.getResource("/liu/ida/emiva760/tddc69/projekt/sprites/solidbrick.png");
                break;
            case 2:
                brick = Brick.class.getResource("/liu/ida/emiva760/tddc69/projekt/sprites/explosivebrick.png");
                break;
            default:
                brick = Brick.class.getResource("/liu/ida/emiva760/tddc69/projekt/sprites/brick.png");
                break;
        }
    }
}
