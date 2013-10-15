package se.liu.ida.emiva760.tddc69.projekt;

import se.liu.ida.emiva760.tddc69.projekt.gameobjecs.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import javax.swing.JPanel;

/**
 * The Game Panel as well as the class that controls the game logic.
 */
public class GameBoard extends JPanel
{
    /**
     * Width of the GameBoard
     */
    public static final int WIDTH = 300;

    /**
     * Height of the GameBoard
     */
    public static final int HEIGHT = 400;

    /**
     * How far to the right the paddle can reach
     */
    public static final int PADDLE_RIGHT = 250;

    /**
     * How far to the right the ball can reach
     * */
    public static final int BALL_RIGHT = 290;

    /**
     * The y position of the ball at game start
     */
    public static final int BALL_STARTY = 350;

    /**
     * The x position of the ball at game start
     */
    public static final int BALL_STARTX = WIDTH / 2 - (WIDTH - BALL_RIGHT);

    /**
     * The y position of the paddle at game start
     */
    public static final int PADDLE_STARTY = 360;

    /**
     * The x position of the paddle at game start
     */
    public static final int PADDLE_STARTX = WIDTH / 2 - (WIDTH - PADDLE_RIGHT);

    /**
     * The number of rows of bricks
     */
    private static final int ROWS = 5;

    /**
     * The number of columns of bricks
     */
    private static final int COLUMNS = 6;

    Timer gameTimer;
    String message = "Game Over! ";
    //Ball ball = null;
    ArrayList<Ball> balls;

    int numberOfBalls = 0;

    Paddle paddle = null;
    //TODO: Maybe change the arrays to ArrayLists
    Brick[][] bricks;
    PowerUp[][] powers;

    boolean gameRunning = true;

    /**
     * The player's score
     */
    public int score = 0;

    /**
     * The player's lives
     */
    public int lives = 2;

    public String scoreString = "Score: " + Integer.toString(score);
    public String livesString = "Lives: " + Integer.toString(lives);

    private static Random random = new Random();

    public GameBoard() {
	addKeyListener(new SteeringAdapter());
	setFocusable(true);

	balls = new ArrayList<>();
	bricks = new Brick[ROWS][COLUMNS];
	powers = new PowerUp[ROWS][COLUMNS];

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
	balls.add(new NormalBall(BALL_STARTX, BALL_STARTY));
	numberOfBalls = 1;
	paddle = new Paddle(PADDLE_STARTX, PADDLE_STARTY);

	// Place the blocks
	// randomly place powerUps below blocks
	for (int i = 0; i < ROWS; i++) {
	    for (int j = 0; j < COLUMNS; j++) {
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
	if (lives >= 0) {
	    balls.clear();
	    numberOfBalls = 1;
	    balls.add(new NormalBall(BALL_STARTX, BALL_STARTY));
	    paddle.resetState();
	    return;
	}
	stopGame();
    }

    /**
     * The paint component. Draws the graphics of the entire game.
     * @param g is the graphics object
     */
    public void paint(Graphics g) {
	Graphics2D g2 = (Graphics2D) g;
	setBackground(Color.WHITE);
	super.paint(g2);

	// Only draw when the game is running
	if (gameRunning) {
	    // draws the text in the game
	    Font gameFont = new Font("Sans", Font.BOLD, 11);
	    g2.setFont(gameFont);
	    g2.drawString(scoreString,
			  WIDTH - WIDTH / 2, 10);
	    g2.drawString(livesString, 10, 10);

	    for (int ballIndex = 0; ballIndex < numberOfBalls; ballIndex++) {
		g2.drawImage(balls.get(ballIndex).getImage(), (int) balls.get(ballIndex).getX(),
			     (int) balls.get(ballIndex).getY(), balls.get(ballIndex).getWidth(),
			     balls.get(ballIndex).getHeight(), this);
	    }

	    g2.drawImage(paddle.getImage(), (int)paddle.getX(), (int)paddle.getY(),
			 paddle.getWidth(), paddle.getHeight(), this);

	    // Check the different object arrays and draw the objects that exist in said arrays
	    for (int i = 0; i < ROWS; i++) {
		for (int j = 0; j < COLUMNS; j++) {
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
		    (WIDTH -
		     endMetrics.stringWidth(message + scoreString))/2,
		    WIDTH /2);
	}

	Toolkit.getDefaultToolkit().sync();
	g2.dispose();
    }

    /**
     * Controls the steering of the paddle
     */
    private class SteeringAdapter extends KeyAdapter {
	public void keyReleased(KeyEvent keyEvent) {
	    paddle.keyReleased(keyEvent);
	}

	public void keyPressed(KeyEvent keyEvent) {
	    paddle.keyPressed(keyEvent);
	}
    }

    class GameTask extends TimerTask {
	public void run() {
	    for (int ballIndex = 0; ballIndex < numberOfBalls; ballIndex++) {
		balls.get(ballIndex).move();
	    }
	    paddle.move();
	    for (int i = 0; i < ROWS; i++) {
		for (int j = 0; j < COLUMNS; j++) {
		    if (powers[i][j] != null) {
			powers[i][j].move();
		    }
		    if (pickedUpPower(powers[i][j])) {
			powers[i][j].usePowerUp();
			powers[i][j] = null;
			repaint();
		    }
		    if (powers[i][j] != null &&
			powers[i][j].getY() > HEIGHT) {
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
	for (int ballIndex = 0; ballIndex < numberOfBalls; ballIndex++) {
	    if (balls.get(ballIndex).getRect().getMaxY() > HEIGHT) {
		numberOfBalls--;
		if (numberOfBalls == 0) {
		    nextLife();
		    livesString = "Lives: " + Integer.toString(lives);
		}
	    }
	}
    }

    /**
     * Check if the game is won.
     */
    private void checkWinCondition(int blocksDestroyed) {
	for (int i = 0; i < ROWS; i++) {
	    for (int j = 0; j < COLUMNS; j++) {
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
	for (int ballIndex = 0; ballIndex < numberOfBalls; ballIndex++) {
	    if (balls.get(ballIndex).intersects(paddle)) {
		if (balls.get(ballIndex).intersectsFromSide(paddle)) {
		    balls.get(ballIndex).flipXDir();
		} else {
		    balls.get(ballIndex).flipYDir();
		}
	    }
	}
    }

    /**
     * Adjust the direction of the ball relating to where it hits a brick.
     * Destroy the brick.
     */
    private void ballBrickCollision() {
	for (int i = 0; i < ROWS; i++) {
	    for (int j = 0; j < COLUMNS; j++) {
		for (int ballIndex = 0; ballIndex < numberOfBalls; ballIndex++) {
		    if (balls.get(ballIndex).intersects(bricks[i][j]) && !bricks[i][j].isDestroyed()) {
			if (balls.get(ballIndex).getType() != BallType.GHOST) {
			    if(balls.get(ballIndex).intersectsFromSide(bricks[i][j])) {
				balls.get(ballIndex).flipXDir();
			    } else {
				balls.get(ballIndex).flipYDir();
			    }
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
    }

    /**
     * Make the powerup fall down.
     */
    private void triggerPower(PowerUp power) {
	if(power != null)
	    power.triggerFall();
    }

}
