package se.liu.ida.emiva760.tddc69.projekt;

import se.liu.ida.emiva760.tddc69.projekt.gameObjects.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import javax.swing.JPanel;

/**
 * The Game Panel as well as the class that controls the game logic.
 */
public class GameBoard extends JPanel implements SharedConstants {
    Timer gameTimer;
    String message = "Game Over! ";
    Ball ball;
    Paddle paddle;
    Brick[][] bricks;
    PowerUp[][] powers;

    boolean gameRunning = true;

    public int score = 0;
    public int lives = 2;
    public String scoreString = "Score: " + Integer.toString(score);
    public String livesString = "Lives: " + Integer.toString(lives);

    private static Random random = new Random();

    public GameBoard() {
	addKeyListener(new SteeringAdapter());
	setFocusable(true);

	bricks = new Brick[5][6];

	powers = new PowerUp[5][6];

	setDoubleBuffered(true);
	gameTimer = new Timer();
	gameTimer.scheduleAtFixedRate(new GameTask(), 1000, 10); // How fast the game loops

    }

    /**
     * Random enum selection method found at
     * http://stackoverflow.com/questions/1972392/java-pick-a-random-value-from-an-enum
     */
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
	int x = random.nextInt(clazz.getEnumConstants().length);
	return clazz.getEnumConstants()[x];
    }

    public void addNotify() {
	super.addNotify();
	gameInit();
    }

    public void gameInit() {
	ball = new Ball(SharedConstants.BALL_STARTX, SharedConstants.BALL_STARTY);
	paddle = new Paddle(SharedConstants.PADDLE_STARTX, SharedConstants.PADDLE_STARTY);

	// Place the blocks on 5 rows and 6 columns
	// randomly place powerUps below blocks
	for (int i = 0; i < 5; i++) {
	    for (int j = 0; j < 6; j++) {
		int spawnInt = random.nextInt(5); // Generates a random number to control whether a powerup should be created
		BrickType brickType = randomEnum(BrickType.class);
		PowerType powerType = randomEnum(PowerType.class);
		if (spawnInt == 1) {
		    // Place extra point powerup
		    if (powerType == PowerType.POINTS) {
			powers[i][j] = new PointsPower(j*50, i*30+50, this);
		    }
		    // Place extra life powerup
		    else if (powerType == PowerType.EXTRA_LIFE) {
			powers[i][j] = new ExtraLifePower(j*50, i*30+50, this);
		    }
		    // Place lose life powerup
		    else if (powerType == PowerType.LOSE_LIFE) {
			powers[i][j] = new LoseLifePower(j*50, i*30+50, this);
		    }
		}

		// Place normal brick
		if (brickType == BrickType.NORMAL) {
		    bricks[i][j] = new BlueBrick(j*50, i*30+50);
		}
		// Place explosive brick
		else if (brickType == BrickType.EXPLOSIVE) {
		    bricks[i][j] = new ExplosiveBrick(j*50, i*30+50);
		}
		// Place solid brick
		else if (brickType == BrickType.SOLID) {
		    bricks[i][j] = new SolidBrick(j*50, i*30+50);
		}

	    }
	}
    }

    /**
     * Place the paddle and the ball at their original positions when a life is lost
     */
    public void nextLife() {
	lives -= 1;
	ball = new Ball(SharedConstants.BALL_STARTX, SharedConstants.BALL_STARTY);
	paddle.resetState();
    }

    /**
     * The paint component. Draws the graphics of the entire game.
     * @param g is the graphics object
     */
    public void paint(Graphics g) {
	Graphics2D g2 = (Graphics2D) g;
	super.setBackground(Color.WHITE);
	super.paint(g2);

	// Only draw when the game is running
	if (gameRunning) {
	    //g2.setBackground(new Color(0, 0, 0));
	    // draws the text in the game
	    Font gameFont = new Font("Sans", Font.BOLD, 11);
	    g2.setFont(gameFont);
	    g2.drawString(scoreString,
			  SharedConstants.WIDTH-SharedConstants.WIDTH / 2, 10);
	    g2.drawString(livesString, 10, 10);

	    g2.drawImage(ball.getImage(), (int)ball.getX(), (int)ball.getY(),
			 ball.getWidth(), ball.getHeight(), this);
	    g2.drawImage(paddle.getImage(), (int)paddle.getX(), (int)paddle.getY(),
			 paddle.getWidth(), paddle.getHeight(), this);

	    // Check the different object arrays and draw the objects that exist in said arrays
	    for (int i = 0; i < 5; i++) {
		for (int j = 0; j < 6; j++) {
		    if (powers[i][j] != null) {
			g2.drawImage(
				powers[i][j].getImage(),
				(int)powers[i][j].getX(), (int)powers[i][j].getY(),
				powers[i][j].getWidth(),
				powers[i][j].getHeight(), this);
		    }
		    // Only draw bricks that haven't been destroyed
		    if (!bricks[i][j].isDestroyed()) {
			g2.drawImage(
				bricks[i][j].getImage(),
				(int)bricks[i][j].getX(), (int)bricks[i][j].getY(),
				bricks[i][j].getWidth(),
				bricks[i][j].getHeight(), this);
		    }
		}
	    }
	} else { // Tell the player that the game is over
	    Font endFont = new Font("Sans", Font.BOLD, 15);
	    FontMetrics endMetrics = this.getFontMetrics(endFont);

	    g2.setColor(Color.BLACK);
	    g2.setFont(endFont);
	    g2.drawString(
		    message + scoreString,
		    (SharedConstants.WIDTH -
		     endMetrics.stringWidth(message + scoreString))/2,
		    SharedConstants.WIDTH/2);
	}

	Toolkit.getDefaultToolkit().sync();
	g2.dispose();
    }

    /**
     * Controls the steering of the paddle
     */
    private class SteeringAdapter extends KeyAdapter {
	public void keyReleased(KeyEvent e) {
	    paddle.keyReleased(e);
	}

	public void keyPressed(KeyEvent e) {
	    paddle.keyPressed(e);
	}
    }

    class GameTask extends TimerTask {
	public void run() {
	    ball.move();
	    paddle.move();
	    for (int i = 0; i < 5; i++) {
		for (int j = 0; j < 6; j++) {
		    if (powers[i][j] != null) {
			powers[i][j].move();
		    }
		    if (pickedUpPower(powers[i][j])) {
			powers[i][j].usePowerUp();
			powers[i][j] = null;
			repaint();
		    }
		    if (powers[i][j] != null &&
			powers[i][j].getY() > SharedConstants.BOTTOM) {
			powers[i][j] = null;
			repaint();
		    }
		}
	    }

	    checkCollision();
	    repaint();
	}
    }

    /**
     * Stops the timer that controls the game speed and therefore stops the game
     */
    public void stopGame() {
	gameRunning = false;
	gameTimer.cancel();
    }

    /**
     * Controls collisions and whether all bricks are destroyed
     */
    public void checkCollision() {
	ballMissed();

	int destroyedBlocksCounter = 0;
	checkWinCondition(destroyedBlocksCounter);

	ballPaddleCollision();

	ballBrickCollision();
    }

    /**
     * Test whether the power up has collided with the paddle.
     */
    private boolean pickedUpPower(PowerUp powerUp) {
	if (powerUp != null && powerUp.intersects(paddle)) {
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
	triggerPower(powers[i][j]);

	array[i][j].blowUp();

	if (isBlockAbove(array, i, j)) {
	    if (array[i - 1][j].getType() == BrickType.EXPLOSIVE) {   // above explosive?
		destroyNeighbors(array, i - 1, j);
	    }
	    array[i - 1][j].blowUp();
	    score += 100;
	    triggerPower(powers[i - 1][j]);
	}

	if (isBlockBelow(array, i, j)) {
	    if (array[i + 1][j].getType() == BrickType.EXPLOSIVE) {
		destroyNeighbors(array, i + 1, j);
	    }
	    array[i + 1][j].blowUp();
	    score += 100;
	    triggerPower(powers[i + 1][j]);
	}

	if (isBlockLeft(array, i, j)) {
	    if (array[i][j - 1].getType() == BrickType.EXPLOSIVE) {
		destroyNeighbors(array, i, j - 1);
	    }
	    array[i][j - 1].blowUp();
	    score += 100;
	    triggerPower(powers[i][j - 1]);
	}

	if (isBlockRight(array, i, j)) {
	    if (array[i][j + 1].getType() == BrickType.EXPLOSIVE) {
		destroyNeighbors(array, i, j + 1);
	    }
	    array[i][j + 1].blowUp();
	    score += 100;
	    triggerPower(powers[i][j + 1]);
	}
    }

    // Functions to test whether there is a brick next to the currently checked brick ////////////////////
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
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

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
	if (ball.intersects(paddle)) {
	    if (ball.intersectsFromSide(paddle)) {
		ball.flipXDir();
	    } else {
		ball.flipYDir();
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
		if (ball.intersects(bricks[i][j]) && !bricks[i][j].isDestroyed()) {
		    if(ball.intersectsFromSide(bricks[i][j])) {
			ball.flipXDir();
		    } else {
			ball.flipYDir();
		    }

		    bricks[i][j].setDestroyed(true);

		    if (bricks[i][j].getType() == BrickType.NORMAL) {
			score += 100;
			triggerPower(powers[i][j]);
		    }
		    else if (bricks[i][j].getType() == BrickType.SOLID &&
			     bricks[i][j].getHealth() == 0) {
			bricks[i][j].setDestroyed(true);
			score += 50;
			triggerPower(powers[i][j]);
		    }
		    else if (bricks[i][j].getType() == BrickType.EXPLOSIVE) {
			destroyNeighbors(bricks, i, j);
			triggerPower(powers[i][j]);
			score += 100;
		    }
		    scoreString = "Score: " + Integer.toString(score);
		}
	    }
	}
    }

    /**
     * Make the powerup fall down.
     */
    private void triggerPower(PowerUp power) {
	if(power != null)
	    power.triggerFall();
    }

}
