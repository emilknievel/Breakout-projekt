package liu.ida.emiva760.tddc69.projekt;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

import javax.swing.JPanel;

public class GameBoard extends JPanel implements SharedConstants {
	//Image sprite;
	Timer gameTimer;
	String message = "Game Over! ";
	Ball ball;
	Paddle paddle;
	Brick bricks[];
    PowerUp powerUp;
	
	boolean gameRunning = true;

	private int score = 0;
    private int lives = 3;

    private static Random randomNo = new Random();
    private int blockType;

    private static Random powerRand = new Random();
    private int powerType;

    private static Random destroyedBlockRand = new Random();
    private int randomSpawnPower;

	private String scoreString = "Score: " + Integer.toString(score);
    private String livesString = "Lives: " + Integer.toString(lives);
	//int gameTimerID;

	public GameBoard() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		
		bricks = new Brick[30];
		setDoubleBuffered(true);
		gameTimer = new Timer();
		gameTimer.scheduleAtFixedRate(new ScheduleTask(), 1000, 10);
	}
	
	public void addNotify() {
		super.addNotify();
		gameInit();
	}
	
	public void gameInit() {
		ball = new Ball();
		paddle = new Paddle();
		
		int brickIndex = 0;
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 6; j++) {
                blockType = randomNo.nextInt(3);
				bricks[brickIndex] = new Brick(j*40+30, i*10+50, blockType);
				brickIndex++;
			}
		}
	}

    public void nextLife() {
        lives -= 1;
        ball = new Ball();
        paddle = new Paddle();
    }
	
	public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
		super.paint(g2);

		if (gameRunning) {
			// draws the text in the game
			g2.drawString(scoreString, SharedConstants.WIDTH-SharedConstants.WIDTH/2, 10);
            g2.drawString(livesString, 10, 10);
			
			g2.drawImage(ball.getImage(), ball.getX(), ball.getY(),
					ball.getWidth(), ball.getHeight(), this);
			g2.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
					paddle.getWidth(), paddle.getHeight(), this);

			for (int i = 0; i < 30; i++) {
				if (!bricks[i].isDestroyed()) {
                    g2.drawImage(bricks[i].getImage(), bricks[i].getX(),
                            bricks[i].getY(), bricks[i].getWidth(),
                            bricks[i].getHeight(), this);
                } else {
                    if (bricks[i].getType() != 1 && randomSpawnPower == 1) {
                        g2.drawImage(powerUp.getImage(), powerUp.getX(), powerUp.getY(),
                                powerUp.getWidth(), powerUp.getHeight(), this);
                    }
                }
			}
		} else {
			Font font = new Font("Sans", Font.BOLD, 11);
			FontMetrics metrics = this.getFontMetrics(font);

			g2.setColor(Color.BLACK);
			g2.setFont(font);
			g2.drawString(message + scoreString,
					(SharedConstants.WIDTH - metrics.stringWidth(message + scoreString))/2,
					SharedConstants.WIDTH/2);
		}

		Toolkit.getDefaultToolkit().sync();
		g2.dispose();
	}
	
	private class TAdapter extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			paddle.keyReleased(e);
		}
		
		public void keyPressed(KeyEvent e) {
			paddle.keyPressed(e);
		}
	}
	
	class ScheduleTask extends TimerTask {
		public void run() {
			ball.move();
			paddle.move();
            powerType = powerRand.nextInt(3);
            randomSpawnPower = destroyedBlockRand.nextInt(10);
            if (powerUp != null) {
                powerUp.move();
            }

			checkCollision();
			repaint();
		}
	}
	
	public void stopGame() {
		gameRunning = false;
		gameTimer.cancel();
	}
	//TODO: Look through collision detection. Should probably divide it into subroutines
	public void checkCollision() {
		if (ball.getRect().getMaxY() > SharedConstants.BOTTOM) {
            if (lives == 0) {
                stopGame();
            } else {
                nextLife();
                livesString = "Lives: " + Integer.toString(lives);
            }
		}
		
		for (int i = 0, j = 0; i < 30; i++) {
			if (bricks[i].isDestroyed()) {
				j++;
			}
			
			if (j == 30) {
				message = "You win! ";
				stopGame();
			}
		}
		
		if ((ball.getRect()).intersects(paddle.getRect())) {

			int paddleLPos = (int)paddle.getRect().getMinX();
			int ballLPos = (int)ball.getRect().getMinX();

			int first = paddleLPos + 8;
			int second = paddleLPos + 16;
			int third = paddleLPos + 24;
			int fourth = paddleLPos + 32;

			if (ballLPos < first) {
				ball.setXDir(-1);
				ball.setYDir(-1);
			}

			if (ballLPos >= first && ballLPos < second) {
				ball.setXDir(-1);
				ball.setYDir(-1 * ball.getYDir());
			}

			if (ballLPos >= second && ballLPos < third) {
				ball.setXDir(0);
				ball.setYDir(-1);
			}

			if (ballLPos >= third && ballLPos < fourth) {
				ball.setXDir(1);
				ball.setYDir(-1 * ball.getYDir());
			}

			if (ballLPos > fourth) {
				ball.setXDir(1);
				ball.setYDir(-1);
			}
		}
		
		for (int i = 0; i < 30; i++) {
			if ((ball.getRect()).intersects(bricks[i].getRect())) {

				int ballLeft = (int)ball.getRect().getMinX();
				int ballHeight = (int)ball.getRect().getHeight();
				int ballWidth = (int)ball.getRect().getWidth();
				int ballTop = (int)ball.getRect().getMinY();

				Point pointRight =
						new Point(ballLeft + ballWidth + 1, ballTop);
				Point pointLeft = new Point(ballLeft - 1, ballTop);
				Point pointTop = new Point(ballLeft, ballTop - 1);
				Point pointBottom =
						new Point(ballLeft, ballTop + ballHeight + 1);

				if (!bricks[i].isDestroyed()) {
					if (bricks[i].getRect().contains(pointRight)) {
						ball.setXDir(-1);
					}

					else if (bricks[i].getRect().contains(pointLeft)) {
						ball.setXDir(1);
					}

					if (bricks[i].getRect().contains(pointTop)) {
						ball.setYDir(1);
					}

					else if (bricks[i].getRect().contains(pointBottom)) {
						ball.setYDir(-1);
					}

					bricks[i].setDestroyed(true);

                    if (bricks[i].getType() == 0) {
                        score += 100;
                    }
                    //TODO: Fix so that it gives the points only when destroyed
                    //Currently it gives points the strike before and when it's destroyed
                    else if (bricks[i].getType() == 1 && bricks[i].getHealth() == 0) {
                        score += 50;
                    }
                    else if (bricks[i].getType() == 2) {
                        //TODO: Add the explosive effect here. Think about points
                        score += 200;
                    }

                    //TODO: I can probably place the powerup making methods around here (use rands)
                    if (bricks[i].getType() != 1 && randomSpawnPower == 1) {
                        createPowerUp(bricks[i].getX(), bricks[i].getY(), powerType);
                    }

					scoreString = "Score: " + Integer.toString(score);
				}
			}
		}
	}

    private void useExtraLife() {
        lives += 1;
        livesString = "Lives: " + Integer.toString(lives);
    }

    private void useLoseLife() {
        if (lives == 0) {
            stopGame();
        } else {
            lives -= 1;
            livesString = "Lives: " + Integer.toString(lives);
        }
    }

    private void useExtraPoints() {
        score += 300;
        scoreString = "Score: " + Integer.toString(score);
    }

    private void createPowerUp(int x, int y, int type) {
        powerUp = new PowerUp(x, y, type);
    }


    /*
    private void explode() {
        if blockAbove()
    }

    private void totalDestruction() {

    }

    private boolean blockAbove() {
        return
    }

    private boolean blockBelow() {

    }

    private boolean blockLeft() {

    }

    private boolean blockRight() {

    }
    */
}
