package se.liu.ida.emiva760.tddc69.projekt;

/**
 * Contains player name and the players score along with the position of the highscore
 */
public class Score
{
    int position;
    int score;
    String name;

    public Score(final int position, final String name, final int score) {
	this.position = position;
	this.score = score;
	this.name = name;
    }
}
