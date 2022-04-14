/**
 * 
 */
package main;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JLabel;

/**
 * @author scott
 *
 */
public class WordLabel extends JLabel {

	/**
	 * Default
	 */
	private static final long serialVersionUID = -218613347643472006L;
	private String word;
	private ArrayList<Point> points;

	public WordLabel(String word) {
		super(word); 
		this.setWord(word);
		this.setPoints(new ArrayList<Point>(word.length()));
	}

	/**
	 * @return the points
	 */
	public ArrayList<Point> getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

	/**
	 * @return the word
	 */
	public String getWord() {
		return word;
	}

	/**
	 * @param word the word to set
	 */
	public void setWord(String word) {
		this.word = word;
	}
}
