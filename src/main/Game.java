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
	public static final int MAX_ROWS = 9;
	public static final int MAX_COLS = 9;
	private MainPanel mainPanel;
	
	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
	
	public void resetWordList() {
		for (int w = 0; w < Game.WORD_LIST.length; w++) {
			mainPanel.getWordLabelList()[w] = new WordLabel(Game.WORD_LIST[w].toUpperCase());
		}
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
		LetterTextField[][] fieldGrid = mainPanel.getFieldGrid();
		for (int row = 0; row < fieldGrid.length; row++) {
			for (int col = 0; col < fieldGrid[row].length; col++) {
				fieldGrid[row][col].setText("");
			}
		}
		ArrayList<Point> pointsUsed = new ArrayList<Point>();
		for (int w = 0; w < WORD_LIST.length; w++) {
			int randDirection = (int) (Math.random() * (2 - 1 + 1) + 1);
			boolean horizontal = randDirection == 1;

			String word = WORD_LIST[w];
			word = word.toUpperCase();

			int maxRow = MAX_ROWS - 1;
			int maxCol = MAX_COLS - 1;

			if (horizontal) {
				maxCol -= word.length();
			} else {
				maxRow -= word.length();
			}

			int randRow = 0;
			int randCol = 0;
			boolean valid = false;
			int tries = 0;
			do {
				randRow = (int) (Math.random() * (maxRow - 0 + 1) + 0);
				randCol = (int) (Math.random() * (maxCol - 0 + 1) + 0);

				boolean conflict = false;
				int colCheck = randCol;
				int rowCheck = randRow;
				for (int l = 0; l < word.length(); l++) {
					final int testRow = rowCheck;
					final int testCol = colCheck;
					if (pointsUsed.stream().filter(p -> p.y == testRow && p.x == testCol).findFirst().isPresent()) {
						if (fieldGrid[rowCheck][colCheck].getText() != word.charAt(l) + "") {
							valid = false;
							conflict = true;
							break;
						}
					}
					if (horizontal) {
						colCheck++;
					} else {
						rowCheck++;
					}
				}
				if (!conflict) {
					valid = true;
				}

				tries++;
			} while (!valid && tries < 100);
			try {
				ArrayList<Point> wordPoints = new ArrayList<Point>();
				for (int l = 0; l < word.length(); l++) {
					pointsUsed.add(new Point(randCol, randRow));
					wordPoints.add(new Point(randCol, randRow));
					fieldGrid[randRow][randCol].setText(word.charAt(l) + "");
					if (horizontal) {
						randCol++;
					} else {
						randRow++;
					}
				}
				WordLabel[] wordLabelList = mainPanel.getWordLabelList();
				for (int j = 0; j < wordLabelList.length; j++) {
					if(wordLabelList[j].getWord().equalsIgnoreCase(word)) {
						wordLabelList[j].setPoints(wordPoints);						
					}
				}
			} catch (Exception ex) {
				System.out.println("RandRow = " + randRow);
				System.out.println("RandCol = " + randCol);
				throw ex;
			}
		}
	}
	public void clear(boolean onlySelected) {
		LetterTextField[][] fieldGrid = mainPanel.getFieldGrid();
		for (int row = 0; row < fieldGrid.length; row++) {
			for (int col = 0; col < fieldGrid[row].length; col++) {
				if (onlySelected) {
					if (fieldGrid[row][col].selected) {
						fieldGrid[row][col].selected = false;
						fieldGrid[row][col].setBackground(Color.WHITE);
					}
				} else {
					fieldGrid[row][col].selected = false;
					fieldGrid[row][col].submitted = false;
					fieldGrid[row][col].setBackground(Color.WHITE);
				}

			}
		}
	}
}
