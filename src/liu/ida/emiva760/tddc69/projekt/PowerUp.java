package liu.ida.emiva760.tddc69.projekt;

import javax.swing.*;
import java.net.URL;

public class PowerUp extends GameObject implements SharedConstants {
    protected URL powerup;
    private int yDir;
    private int type;

    public PowerUp(int x, int y, int type) {
        this.x = x;
        this.y = y;
        yDir = 0;   //should be still until a brick above it is destroyed
        this.type = type;

        selectImage(type);
        ImageIcon icon = new ImageIcon(powerup);
        image = icon.getImage();

        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    private void selectImage(int type) {
        switch (type) {
            case 0:
                powerup = PowerUp.class.getResource("/liu/ida/emiva760/tddc69/projekt/sprites/power_points.png");
                break;
            case 1:
                powerup = PowerUp.class.getResource("/liu/ida/emiva760/tddc69/projekt/sprites/power_extralife.png");
                break;
            case 2:
                powerup = PowerUp.class.getResource("/liu/ida/emiva760/tddc69/projekt/sprites/power_loselife.png");
                break;
            default:
                powerup = PowerUp.class.getResource("/liu/ida/emiva760/tddc69/projekt/sprites/power_points.png");
                break;
        }
    }

    public int getType() {
        return type;
    }

    public void move() {
        y += yDir;
    }

    public void triggerFall() {
        yDir = 1;
    }
}
