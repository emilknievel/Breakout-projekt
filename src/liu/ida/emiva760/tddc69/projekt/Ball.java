package liu.ida.emiva760.tddc69.projekt;

import javax.swing.ImageIcon;
import java.net.URL;

public class Ball extends GameObject implements SharedConstants {
	private int xDir;
	private int yDir;

    protected URL ball = Ball.class.getResource("/liu/ida/emiva760/tddc69/projekt/sprites/ball.png");

	public Ball() {

		xDir = 1;
		yDir = -1;

        ImageIcon icon = new ImageIcon(ball);
		image = icon.getImage();


		width = image.getWidth(null);
		height = image.getHeight(null);

		resetState();
	}


	public void move()
	{
		x += xDir;
		y += yDir;

		if (x == 0) {
			setXDir(1);
		}

		if (x == BALL_RIGHT) {
			setXDir(-1);
		}

		if (y == 0) {
			setYDir(1);
		}
	}

    /**
     * Place at starting position
     */
	public void resetState() 
	{
		x = SharedConstants.WIDTH / 2 - width;
		y = 350;
	}

	public void setXDir(int x)
	{
		xDir = x;
	}

	public void setYDir(int y)
	{
		yDir = y;
	}

	public int getYDir()
	{
		return yDir;
	}
}
