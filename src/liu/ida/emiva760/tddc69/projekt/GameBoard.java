package liu.ida.emiva760.tddc69.projekt;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import javax.swing.JPanel;

public class GameBoard extends JPanel implements SharedConstants {
    Timer gameTimer;
	String message = "Game Over! ";
	Ball ball;
	Paddle paddle;
	Brick bricks[][];
	PowerUp powers[][];
	
	boolean gameRunning = true;

	private int score = 0;
	private int lives = 2;

	private static Random randomNo = new Random();
	private int blockType;
    private int spawnInt;

	private static Random powerRand = new Random();
	private int powerType;
	private int localPowerType; // The type of the currently spawned powerUp

	private static Random destroyedBlockRand = new Random();
	private int randomSpawnPower;

	private String scoreString = "Score: " + Integer.toString(score);
	private String livesString = "Lives: " + Integer.toString(lives);

	public GameBoard() {
		addKeyListener(new TAdapter());
		setFocusable(true);

		bricks = new Brick[5][6];

        powers = new PowerUp[5][6];

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

		// Place the blocks on 5 rows and 6 columns
		// randomly place powerUps below blocks
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 6; j++) {
                spawnInt = randomNo.nextInt(5);
				blockType = randomNo.nextInt(3);
                powerType = powerRand.nextInt(3);
                if (spawnInt == 1) {
                    powers[i][j] = new PowerUp(j*50,i*30+50, powerType);
                }
				bricks[i][j] = new Brick(j*50, i*30+50, blockType);
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
			g2.drawString(scoreString,
                    SharedConstants.WIDTH-SharedConstants.WIDTH/2, 10);
            g2.drawString(livesString, 10, 10);

            g2.drawImage(ball.getImage(), ball.getX(), ball.getY(),
                    ball.getWidth(), ball.getHeight(), this);
            g2.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
                    paddle.getWidth(), paddle.getHeight(), this);

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 6; j++) {
                    if (powers[i][j] != null) {
                        g2.drawImage(
                                powers[i][j].getImage(),
                                powers[i][j].getX(), powers[i][j].getY(),
                                powers[i][j].getWidth(),
                                powers[i][j].getHeight(), this);
                    }
                    if (!bricks[i][j].isDestroyed()) {
                        g2.drawImage(
                                bricks[i][j].getImage(),
                                bricks[i][j].getX(), bricks[i][j].getY(),
                                bricks[i][j].getWidth(),
                                bricks[i][j].getHeight(), this);
                    }
                }
            }
        } else {
            Font font = new Font("Sans", Font.BOLD, 11);
            FontMetrics metrics = this.getFontMetrics(font);

            g2.setColor(Color.BLACK);
            g2.setFont(font);
            g2.drawString(
                    message + scoreString,
                    (SharedConstants.WIDTH -
                            metrics.stringWidth(message + scoreString))/2,
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
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 6; j++) {
                    if (powers[i][j] != null) {
                        powers[i][j].move();
                    }
                    if (pickedUpPower(powers[i][j])) {
                        usePowerUp(powers[i][j].getType());
                        powers[i][j] = null;
                        repaint();
                    }
                    if (powers[i][j] != null && powers[i][j].getY() > SharedConstants.BOTTOM) {
                        powers[i][j] = null;
                        repaint();
                    }
                }
            }

            checkCollision();
            repaint();
        }
    }

    public void stopGame() {
        gameRunning = false;
        gameTimer.cancel();
    }

    public void checkCollision() {
        ballMissed();

        int destroyedBlocksCounter = 0;
        checkWinCondition(destroyedBlocksCounter);

        ballPaddleCollision();

        ballBrickCollision();
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

    private void usePowerUp(int type) {
        if (type == 0) {
            useExtraPoints();
        }
        else if (type == 1) {
            useExtraLife();
        } else {
            useLoseLife();
        }
    }


    // Did the powerup collide with the paddle?
    private boolean pickedUpPower(PowerUp powerUp) {
        if (powerUp != null && powerUp.getY() == paddle.getY()) {
            if (powerUp.getX() >= paddle.getX()) {
                return powerUp.getX() <= paddle.getX() + paddle.getWidth();
            }
            return powerUp.getX() + powerUp.getWidth() >= paddle.getX();
        }
        return false;
    }

    /**
     * Destroy bricks neighboring to an explosive brick.
     */
    private void destroyNeighbors(Brick[][] array, int i, int j) {

        //TODO: Convert all these if occurences into calls to a single function
        if (powers[i][j] != null) {
            powers[i][j].triggerFall();
        }

        array[i][j].blowUp();

        if (isBlockAbove(array, i, j)) {
            if (array[i - 1][j].getType() == 2) {   // above explosive?
                destroyNeighbors(array, i - 1, j);
            }
            array[i - 1][j].blowUp();
            score += 100;
        }

        if (isBlockBelow(array, i, j)) {
            if (array[i + 1][j].getType() == 2) {
                destroyNeighbors(array, i + 1, j);
            }
            array[i + 1][j].blowUp();
            score += 100;
            if (powers[i + 1][j] != null) {
                powers[i + 1][j].triggerFall();
            }
        }

        if (isBlockLeft(array, i, j)) {
            if (array[i][j - 1].getType() == 2) {
                destroyNeighbors(array, i, j - 1);
            }
            array[i][j - 1].blowUp();
            score += 100;
            if (powers[i][j - 1] != null) {
                powers[i][j - 1].triggerFall();
            }
        }

        if (isBlockRight(array, i, j)) {
            if (array[i][j + 1].getType() == 2) {
                destroyNeighbors(array, i, j + 1);
            }
            array[i][j + 1].blowUp();
            score += 100;
            if (powers[i][j + 1] != null) {
                powers[i][j + 1].triggerFall();
            }
        }
    }

    private boolean isBlockAbove(Brick[][] array, int i, int j) {
        return i > 0 && (!array[i - 1][j].isDestroyed());
    }

    private boolean isBlockBelow(Brick[][] array, int i, int j) {
        return i < 4 && (!array[i + 1][j].isDestroyed());
    }

    private boolean isBlockLeft(Brick[][] array, int i, int j) {
        return j > 0 && (!array[i][j - 1].isDestroyed());
    }

    private boolean isBlockRight(Brick[][] array, int i, int j) {
        return j < 5 && (!array[i][j + 1].isDestroyed());
    }

    /**
     * Lose 1 life if the ball has reached the bottom
     */
    private void ballMissed() {
        if (ball.getRect().getMaxY() > SharedConstants.BOTTOM) {
            if (lives == 0) {
                stopGame();
            } else {
                nextLife();
                livesString = "Lives: " + Integer.toString(lives);
            }
        }
    }

    /**
     * Check if the game is won.
     */
    private void checkWinCondition(int blocksDestroyed) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if (bricks[i][j].isDestroyed()) {
                    blocksDestroyed++;
                }

                if (blocksDestroyed == 30) {
                    message = "You win! ";
                    stopGame();
                }
            }
        }
    }

    /**
     * Adjust the direction of the ball relating to where it hits the paddle.
     */
    private void ballPaddleCollision() {
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
    }

    /**
     * Adjust the direction of the ball relating to where it hits a brick.
     * Destroy the brick.
     */
    private void ballBrickCollision() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if ((ball.getRect()).intersects(bricks[i][j].getRect())) {

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

                    if (!bricks[i][j].isDestroyed()) {
                        if (bricks[i][j].getRect().contains(pointRight)) {
                            ball.setXDir(-1);
                        }

                        else if (bricks[i][j].getRect().contains(pointLeft)) {
                            ball.setXDir(1);
                        }

                        if (bricks[i][j].getRect().contains(pointTop)) {
                            ball.setYDir(1);
                        }

                        else if (bricks[i][j].getRect().contains(pointBottom)) {
                            ball.setYDir(-1);
                        }

                        bricks[i][j].setDestroyed(true);

                        if (bricks[i][j].getType() == 0) {
                            score += 100;


                            if (powers[i][j] != null) {
                                powers[i][j].triggerFall();
                            }
                        }
                        else if (bricks[i][j].getType() == 1 && bricks[i][j].getHealth() == 0) {
                            score += 50;
                        }
                        else if (bricks[i][j].getType() == 2) {
                            destroyNeighbors(bricks, i, j);


                            if (powers[i][j] != null) {
                                powers[i][j].triggerFall();
                            }


                            score += 100;
                        }
                        scoreString = "Score: " + Integer.toString(score);
                    }
                }
            }
        }
    }

}
