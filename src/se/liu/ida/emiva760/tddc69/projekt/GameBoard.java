package se.liu.ida.emiva760.tddc69.projekt;

import se.liu.ida.emiva760.tddc69.projekt.gameobjecs.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.regex.Pattern;
import javax.swing.*;

/**
 * The Game Panel as well as the class that controls the game logic.
 */
public class GameBoard extends JPanel
{
    /**
     * The number of rows of bricks
     */
    private static final int ROWS = 5;

    /**
     * The number of columns of bricks
     */
    private static final int COLUMNS = 6;

    /**
     * The total number of bricks in the game
     */
    private static final int NUMBEROFBRICKS = ROWS * COLUMNS;

    /**
     * The height of a brick. Used in gameInit to make sure that the bricks spawn right above eachother
     */
    private static final int BRICKHEIGHT = 30;

    /**
     * The width of a brick. Used in gameInit to make sure that the bricks spawn right next to eachother
     */
    private static final int BRICKWIDTH = 50;

    /**
     * Width of the GameBoard
     */
    public static final int WIDTH = BRICKWIDTH * COLUMNS;

    /**
     * Height of the GameBoard
     */
    public static final int HEIGHT = 400;

    /**
     * How far to the right the paddle can reach
     */
    public static final int PADDLE_RIGHT = WIDTH - 50;

    /**
     * How far to the right the ball can reach
     */
    public static final int BALL_RIGHT = WIDTH - 10;

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
    public static final int PADDLE_STARTX = (WIDTH / 2) - (WIDTH - PADDLE_RIGHT);



    /**
     * Offset for brick spawning so that there is space above the bricks
     */
    private static final int TOPSPACING = 30;

    /**
     * The different parts of every line in the highscore file will be separated by a comma. See readHighScores()
     */
    private static final Pattern COMPILE = Pattern.compile(",");

    /**
     * Used to control the game speed.
     */
    Timer gameTimer;

    String message = "Game Over! ";

    private List<Ball> balls;

    /**
     * The amount of balls in the game.
     */
    private int numberOfBalls = 0;

    Paddle paddle = null;
    Brick[][] bricks;
    PowerUp[][] powers;

    boolean gameRunning = true;

    /**
     * The player's score
     */
    private int score = 0;

    /**
     * The player's lives
     */
    private int lives = 2;

    /**
     * String representation of the score.
     */
    private String scoreString = "Score: " + Integer.toString(score);

    /**
     * String representation of lives.
     */
    private String livesString = "Lives: " + Integer.toString(lives);

    /**
     * The list that contains the highscores
     */
    LinkedList<Score> scoreList = new LinkedList<>();

    private static Random random = new Random();

    public GameBoard() {
	addKeyListener(new SteeringAdapter()); // Adds a listener that listens for key events
	setFocusable(true);
	/**
	 * Contains all of the balls in the game. Useful for spawning of multiple balls.
	 */
	balls = new ArrayList<>();
	bricks = new Brick[ROWS][COLUMNS];
	powers = new PowerUp[ROWS][COLUMNS];

	setDoubleBuffered(true);
	gameTimer = new Timer(); // Will control the game loop
	// Delays the GameTask for a short while and specifies how fast the task updates.
	gameTimer.scheduleAtFixedRate(new GameTask(), 500, 5); // 500 ms delay. 5 ms between ticks.
	gameInit();

	// Read the highscores from highscores.txt
	try {
	    readHighScores(scoreList);
	} catch (IOException e) {
	    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
	}
    }

    /**
     * Random enum selection method found at
     * http://stackoverflow.com/questions/1972392/java-pick-a-random-value-from-an-enum
     * Randomizes an enum from the specified class.
     */
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
	int x = random.nextInt(clazz.getEnumConstants().length);
	return clazz.getEnumConstants()[x];
    }

    /**
     * Places the game objects at their initial positions.
     */
    public void gameInit() {
	balls.add(new NormalBall(BALL_STARTX, BALL_STARTY));
	numberOfBalls = 1;
	paddle = new Paddle(PADDLE_STARTX, PADDLE_STARTY);

	// Place the blocks
	// randomly place powerUps below blocks
	for (int i = 0; i < ROWS; i++) {
	    for (int j = 0; j < COLUMNS; j++) {
		int spawnInt = random.nextInt(5); // Generates a random number to control whether a powerup should be created
		BrickType brickType = randomEnum(BrickType.class); // Random BrickType
		PowerType powerType = randomEnum(PowerType.class);
		if (spawnInt == 1) {
		    // Place extra point powerup. i corresponds to y and j to x.
		    if (powerType == PowerType.POINTS) {
			powers[i][j] = new PointsPower(j*BRICKWIDTH, i*BRICKHEIGHT+TOPSPACING, this);
		    }
		    // Place extra life powerup
		    else if (powerType == PowerType.EXTRA_LIFE) {
			powers[i][j] = new ExtraLifePower(j*BRICKWIDTH, i*BRICKHEIGHT+TOPSPACING, this);
		    }
		    // Place lose life powerup
		    else if (powerType == PowerType.LOSE_LIFE) {
			powers[i][j] = new LoseLifePower(j*BRICKWIDTH, i*BRICKHEIGHT+TOPSPACING, this);
		    }
		    // Place ghost ball powerup
		    else if (powerType == PowerType.GHOSTBALL) {
			powers[i][j] = new GhostPower(j*BRICKWIDTH, i*BRICKHEIGHT+TOPSPACING, this);
		    }
		    else if (powerType == PowerType.EXTRA_BALL) {
			powers[i][j] = new ExtraBallPower(j*BRICKWIDTH, i*BRICKHEIGHT+TOPSPACING, this);
		    }
		}

		// Place normal brick
		if (brickType == BrickType.NORMAL) {
		    bricks[i][j] = new BlueBrick(j*BRICKWIDTH, i*BRICKHEIGHT+TOPSPACING);
		}
		// Place explosive brick
		else if (brickType == BrickType.EXPLOSIVE) {
		    bricks[i][j] = new ExplosiveBrick(j*BRICKWIDTH, i*BRICKHEIGHT+TOPSPACING);
		}
		// Place solid brick
		else if (brickType == BrickType.SOLID) {
		    bricks[i][j] = new SolidBrick(j*BRICKWIDTH, i*BRICKHEIGHT+TOPSPACING);
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
	    Font gameFont = new Font("Sans", Font.BOLD, 11); // 11 is the size of the font
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
	    Font endFont = new Font("Sans", Font.BOLD, 15); // 15 is the size of the font
	    FontMetrics endMetrics = this.getFontMetrics(endFont);

	    g2.setColor(Color.BLACK);
	    g2.setFont(endFont);
	    g2.drawString(message + scoreString, (WIDTH - endMetrics.stringWidth(message + scoreString)) / 2, WIDTH / 2);
	}

	g2.dispose();
    }

    /**
     * Returns all of the balls in the game. Useful for spawning of multiple balls.
     */
    public List<Ball> getBalls() {
	return balls;
    }

    /**
     * Controls the steering of the paddle. Extends KeyAdapter that is an abstract class for receiving keyboard commands.
     * KeyAdapter implements KeyListener
     */
    private class SteeringAdapter extends KeyAdapter {
	@Override public void keyReleased(KeyEvent keyEvent) {
	    super.keyReleased(keyEvent);
	    paddle.keyReleased(keyEvent);
	}

	@Override public void keyPressed(KeyEvent keyEvent) {
	    super.keyPressed(keyEvent);
	    paddle.keyPressed(keyEvent);
	}
    }

    /**
     * Controls the game loop. Five milliseconds between gameticks.
     */
    class GameTask extends TimerTask {
	public void run() {
	    for (int ballIndex = 0; ballIndex < numberOfBalls; ballIndex++) {
		getBalls().get(ballIndex).move();
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
		    }
		    if (powers[i][j] != null &&
			powers[i][j].getY() > HEIGHT) {
			powers[i][j] = null;
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
	try {
	    checkHighScores();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Controls collisions and whether all bricks are destroyed
     */
    public void checkCollision() {
	ballMissed();

	int destroyedBricksCounter = 0;
	checkWinCondition(destroyedBricksCounter);

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
    private void destroyNeighbors(Brick[][] array, int y, int x) {
	triggerPower(powers[y][x]);

	array[y][x].blowUp();

	if (isBlockAbove(array, y, x)) {
	    if (array[y - 1][x].getType() == BrickType.EXPLOSIVE) {   // above explosive?
		destroyNeighbors(array, y - 1, x);
	    }
	    array[y - 1][x].blowUp();
	    score += 100;
	    triggerPower(powers[y - 1][x]);
	}

	if (isBlockBelow(array, y, x)) {
	    if (array[y + 1][x].getType() == BrickType.EXPLOSIVE) {
		destroyNeighbors(array, y + 1, x);
	    }
	    array[y + 1][x].blowUp();
	    score += 100;
	    triggerPower(powers[y + 1][x]);
	}

	if (isBlockLeft(array, y, x)) {
	    if (array[y][x - 1].getType() == BrickType.EXPLOSIVE) {
		destroyNeighbors(array, y, x - 1);
	    }
	    array[y][x - 1].blowUp();
	    score += 100;
	    triggerPower(powers[y][x - 1]);
	}

	if (isBlockRight(array, y, x)) {
	    if (array[y][x + 1].getType() == BrickType.EXPLOSIVE) {
		destroyNeighbors(array, y, x + 1);
	    }
	    array[y][x + 1].blowUp();
	    score += 100;
	    triggerPower(powers[y][x + 1]);
	}
    }

    // Functions to test whether there exists a brick next to the currently checked brick ////////////////////
    private boolean isBlockAbove(Brick[][] array, int y, int x) {
	return y > 0 && (!array[y - 1][x].isDestroyed());
    }

    private boolean isBlockBelow(Brick[][] array, int y, int x) {
	return y < ROWS - 1 && (!array[y + 1][x].isDestroyed());
    }

    private boolean isBlockLeft(Brick[][] array, int y, int x) {
	return x > 0 && (!array[y][x - 1].isDestroyed());
    }

    private boolean isBlockRight(Brick[][] array, int y, int x) {
	return x < COLUMNS - 1 && (!array[y][x + 1].isDestroyed());
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Lose 1 life if the ball has reached the bottom
     */
    private void ballMissed() {
	for (int ballIndex = 0; ballIndex < numberOfBalls; ballIndex++) {
	    if (balls.get(ballIndex).getRect().getMaxY() > HEIGHT) {
		balls.remove(ballIndex);
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

		if (blocksDestroyed == NUMBEROFBRICKS) {
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
			    score += bricks[i][j].getScore();
			    triggerPower(powers[i][j]);
			}
			else if (bricks[i][j].getType() == BrickType.SOLID &&
				 bricks[i][j].getHealth() == 0) {
			    bricks[i][j].setDestroyed(true);
			    score += bricks[i][j].getScore();
			    triggerPower(powers[i][j]);
			}
			else if (bricks[i][j].getType() == BrickType.EXPLOSIVE) {
			    destroyNeighbors(bricks, i, j);
			    triggerPower(powers[i][j]);
			    score += bricks[i][j].getScore();
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

    public int getNumberOfBalls() {
	return numberOfBalls;
    }

    public void incrementNumberOfBalls() {
	numberOfBalls++;
    }

    public int getScore() {
	return score;
    }

    public void setScore(final int score) {
	this.score = score;
    }

    public void setScoreString(final String scoreString) {
	this.scoreString = scoreString;
    }

    public void setLivesString(final String livesString) {
	this.livesString = livesString;
    }

    public int getLives() {
	return lives;
    }

    public void incrementLives() {
	lives++;
    }

    public void decrementLives() {
	lives--;
    }

    /**
     * Writes the highscores from the score list to a separate file. The maximum amount of scores is 10.
     * @param scores is the score list
     * @param playerScore is the current player's score
     * @param playerName is the current player's name
     * @throws IOException
     */
    public void writeHighScores(LinkedList<Score> scores, int playerScore, String playerName) throws IOException {
	try (BufferedWriter out = new BufferedWriter(new FileWriter("highscore.txt"))) {

	    if (!scores.isEmpty()) {
		for (int i = 0; i < scores.size(); i++) {
		    if (playerScore > scores.get(i).score) {
			int playerRank = i + 1;
			if (scores.size() < 10) { // there is free space in the list for a new highscore
			    scores.add(i, new Score(playerRank, playerName, playerScore));
			    break;
			} else { // if there already are 10 highscores, replace the last one with the current player's
			    scores.removeLast();
			    scores.add(i, new Score(playerRank, playerName, playerScore));
			    break;
			}
		    }
		}
		// this is to make sure that there is always a new score added to the list if there is room for it
		if (scores.size() < 10 && score < scores.peekLast().score) {
		    scores.addLast(new Score((scores.peekLast().position + 1), playerName, playerScore));
		}
		// Update the position of the scores
		for (int i = 0; i < scores.size(); i++) {
		    Score temp = scores.get(i);
		    temp.position = i + 1;
		    scores.set(i, temp);
		}

		for (int i = 0; i < scores.size() - 1; i++) {
		    out.write(scores.get(i).position + "," + scores.get(i).name + "," + scores.get(i).score);
		    out.newLine();
		}
	    } else { // add the new score if the file is empty
		scores.add(new Score(1, playerName, playerScore));
	    }

	    out.write(scores.get(scores.size() - 1).position + "," + scores.get(scores.size() - 1).name + "," +
		      scores.get(scores.size() - 1).score);

	    out.close();
	}
    }

    /**
     * Reads highscores from the highscore file and stores them in a list.
     * @param scores is the list of scores
     */
    public void readHighScores(List<Score> scores) throws IOException, FileNotFoundException {
	try (BufferedReader br = new BufferedReader(new FileReader("highscore.txt"))) {

	    String line = br.readLine();

	    int i = 0;
	    while (line != null) { // For each line in the file
		String[] parts = COMPILE.split(line); // Divides the different parts of the line

		// Put a new Score object into the array
		scores.add(i, new Score(Integer.parseInt(parts[0]), parts[1], Integer.parseInt(parts[2])));
		line = br.readLine();
		i += 1;
	    }

	    br.close();
	}
    }

    /**
     * Builds a string from the score list
     * @return the String containing the score info
     */
    public String showHighscores() {
	StringBuilder stringBuilder = new StringBuilder("Highscores\n\nRank:\tName:\t\tScore:\n");

	for (int i = 0; i < scoreList.size(); i++) {
	    stringBuilder.append(scoreList.get(i).position);
	    stringBuilder.append(".\t");
	    stringBuilder.append(scoreList.get(i).name);
	    stringBuilder.append("\t\t");
	    stringBuilder.append(scoreList.get(i).score);
	    stringBuilder.append("\n");
	}
	return stringBuilder.toString();
    }

    /**
     * Show the player the highscore list, using the score string
     * @throws IOException
     */
    public void checkHighScores() throws IOException {
	// Tell the player to enter his/her name if getting a place on the highscore list
	if (scoreList.size() < 10 || score > scoreList.peekLast().score) {
	    String name = JOptionPane.showInputDialog(null, "New highscore! Please enter your name.");
	    writeHighScores(scoreList, score, name);
	}
	String highScoreString = showHighscores();
	JOptionPane.showMessageDialog(null, new JTextArea(highScoreString)); // in a JTextArea to fix alignment
    }
}
