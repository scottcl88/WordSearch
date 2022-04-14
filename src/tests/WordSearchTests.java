/**
 * 
 */
package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Game;
import main.LetterTextField;
import main.MainPanel;

/**
 * @author scott
 *
 */
class WordSearchTests {

	private Game game;
	private MainPanel mainPanel;

	@BeforeEach
	void setUp() {
		game = new Game();
		mainPanel = new MainPanel(null, game);
	}

	/**
	 * Verify words load from text file
	 */
	@Test
	void wordsLoadFromFile() {
		game.loadWords();
		assertEquals(6, game.getWordList().size());
		assertTrue(game.getWordList().contains("benji"));
	}
	
	/**
	 * Words generate and are added to labels
	 */
	@Test
	void wordLabelListCreated() {
		game.loadWords();
		mainPanel.setup();
		game.setMainPanel(mainPanel);
		game.start();
		assertEquals(6, mainPanel.getWordLabelList().length);
	}
	
	/**
	 * Letters are filled
	 */
	@Test
	void lettersAreFilled() {
		game.loadWords();
		mainPanel.setup();
		game.setMainPanel(mainPanel);
		game.start();
		int totalLetters = Game.MAX_COLS * Game.MAX_ROWS;
		LetterTextField[][] fieldGrid = mainPanel.getFieldGrid();
		assertEquals(totalLetters, fieldGrid.length);
		for(int i = 0; i < fieldGrid.length; i ++) {
			for(int j = 0; j < fieldGrid[i].length; j ++) {
				if(fieldGrid[i][j].getText().isBlank()) {
					fail("Letter is not populated");
				}
			}
		}
	}

}
