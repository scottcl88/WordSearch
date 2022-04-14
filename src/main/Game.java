/**
 * 
 */
package main;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Scott Lewis
 *
 */
public class Game {

	public static final String[] WORD_LIST = { "benji", "macie", "betsy" };
	public static final int MAX_ROWS = 15;
	public static final int MAX_COLS = 15;
	public static final Color SOLVED_BG = Color.LIGHT_GRAY;
	public static final Color SELECTED_BG = Color.GREEN;
	public static final Color DEFAULT_BG = Color.WHITE;
	private MainPanel mainPanel;

	public enum Direction {
		Horizontal, Vertical, DiagonalR2L, // right to left; \
		DiagonalL2R// left to right; /
	}

	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public void fillSpacesWithLetters() {
		Random r = new Random();
		LetterTextField[][] fieldGrid = mainPanel.getFieldGrid();
		for (int row = 0; row < fieldGrid.length; row++) {
			for (int col = 0; col < fieldGrid[row].length; col++) {
				if (fieldGrid[row][col].getText().isBlank()) {
					int randomItem = r.nextInt(WORD_LIST.length);
					String randomWord = WORD_LIST[randomItem];
					randomItem = r.nextInt(randomWord.length());
					String randomChar = randomWord.charAt(randomItem) + "";
					fieldGrid[row][col].setText(randomChar.toUpperCase());
				}
			}
		}
	}

	public void generateWords() {
		mainPanel.clearLetters();
		LetterTextField[][] fieldGrid = mainPanel.getFieldGrid();
		ArrayList<Point> pointsUsed = new ArrayList<Point>();
		for (int w = 0; w < WORD_LIST.length; w++) {
			Direction direction = Direction.Horizontal;
			String word = WORD_LIST[w];
			word = word.toUpperCase();

			try {
				ArrayList<Point> wordPoints = getWordPoints(word, pointsUsed, direction);
				if (wordPoints == null || wordPoints.size() != word.length()) {
					System.out.println("Word eliminated due to no valid points found: " + word);
					continue;
				}
				for (int l = 0; l < wordPoints.size(); l++) {
					pointsUsed.add(wordPoints.get(l));
					fieldGrid[wordPoints.get(l).y][wordPoints.get(l).x].setText(word.charAt(l) + "");
				}
				WordLabel[] wordLabelList = mainPanel.getWordLabelList();
				for (int j = 0; j < wordLabelList.length; j++) {
					if (wordLabelList[j].getWord().equalsIgnoreCase(word)) {
						wordLabelList[j].setPoints(wordPoints);
					}
				}
			} catch (Exception ex) {
				throw ex;
			}
		}
	}

	private ArrayList<Point> getWordPoints(String word, ArrayList<Point> pointsUsed, Direction direction) {
		ArrayList<Point> wordPoints = new ArrayList<Point>();
		LetterTextField[][] fieldGrid = mainPanel.getFieldGrid();
		int maxRow = MAX_ROWS;
		int maxCol = MAX_COLS;

		switch (direction) {
		case Horizontal: {
			maxCol -= word.length();
			break;
		}
		case Vertical: {
			maxRow -= word.length();
			break;
		}
		case DiagonalL2R:
			break;
		case DiagonalR2L:
			break;
		default:
			break;
		}

		int randRow = 0;
		int randCol = 0;
		boolean valid = false;
		int tries = 0;
		do {
			randRow = (int) (Math.random() * (maxRow - 0 + 1) + 0);
			randCol = (int) (Math.random() * (maxCol - 0 + 1) + 0);
			wordPoints = new ArrayList<Point>();
			boolean conflict = false;
			int colCheck = randCol;
			int rowCheck = randRow;
			for (int l = 0; l < word.length(); l++) {
				final int testRow = rowCheck;
				final int testCol = colCheck;
				if (testRow >= MAX_ROWS || testCol >= MAX_COLS) {
					valid = false;
					conflict = true;
					break;
				}
				if (pointsUsed.stream().filter(p -> p.y == testRow && p.x == testCol).findFirst().isPresent()) {
					if (fieldGrid[rowCheck][colCheck].getText() != word.charAt(l) + "") {
						valid = false;
						conflict = true;
						break;
					}
				}
				wordPoints.add(new Point(testCol, testRow));

				switch (direction) {
				case Horizontal: {
					colCheck++;
					break;
				}
				case Vertical: {
					rowCheck++;
					break;
				}
				case DiagonalL2R:
					break;
				case DiagonalR2L:
					break;
				default:
					break;
				}
			}
			if (!conflict) {
				valid = true;
			}
			tries++;
		} while (!valid && tries < 100);

		return !valid ? null : wordPoints;
	}


	public void clear(boolean onlySelected) {
		mainPanel.clearSubmittedWords();
		LetterTextField[][] fieldGrid = mainPanel.getFieldGrid();
		for (int row = 0; row < fieldGrid.length; row++) {
			for (int col = 0; col < fieldGrid[row].length; col++) {
				if (onlySelected) {
					if (fieldGrid[row][col].isSelected()) {
						fieldGrid[row][col].setSelected(false);
						fieldGrid[row][col].setBackground(Color.WHITE);
					}
				} else {
					fieldGrid[row][col].setSelected(false);
					fieldGrid[row][col].setSubmitted(false);
					fieldGrid[row][col].setBackground(Color.WHITE);
				}

			}
		}
	}

	public void start() {
		generateWords();
		fillSpacesWithLetters();
	}
}
