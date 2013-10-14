package se.liu.ida.emiva760.tddc69.projekt;

/**
 * Contains constants that can be used throughout the project.
 */
public interface SharedConstants {
    public static final int WIDTH = 300; // Width of the gameboard
    public static final int HEIGHT = 400; // Height of the gameboard
    public static final int BOTTOM = 390; // The bottom of the gameboard

    // How far to the right the paddle can reach
    public static final int PADDLE_RIGHT = 250;

    // How far to the left the ball can reach
    public static final int BALL_RIGHT = 290;

    public static final int BALL_STARTY = 350;
    public static final int BALL_STARTX = WIDTH / 2 - (WIDTH - BALL_RIGHT);

    public static final int PADDLE_STARTY = 360;
    public static final int PADDLE_STARTX = WIDTH / 2 - (WIDTH - PADDLE_RIGHT);
}
