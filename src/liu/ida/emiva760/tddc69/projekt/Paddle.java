package liu.ida.emiva760.tddc69.projekt;

import java.awt.event.KeyEvent;
import java.net.URL;
import javax.swing.ImageIcon;

public class Paddle extends GameObject implements SharedConstants {
    URL paddle = Paddle.class.getResource("/liu/ida/emiva760/tddc69/projekt/sprites/paddle.png");

	int dx;

	public Paddle() {
        ImageIcon icon = new ImageIcon(paddle);
		image = icon.getImage();

		width = image.getWidth(null);
		height = image.getHeight(null);

        resetState();

    }

    public void move() {
        x += dx;
        if (x <= 2)
			x = 2;
		if (x >= SharedConstants.PADDLE_RIGHT)
			x = SharedConstants.PADDLE_RIGHT;
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = -2;

		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 2;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
		}
	}

    //TODO: Place the starting position in the middle

    /**
     * Place at starting position
     */
	public void resetState() {
		x = 200;
		y = 360;
	}
}
