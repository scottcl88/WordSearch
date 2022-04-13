/**
 * 
 */
package main;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.AbstractAction;

/**
 * @author scott
 *
 */
public class SubmitAction extends AbstractAction {
	/**
	 * Default
	 */
	private static final long serialVersionUID = 3759121696154037200L;
	private Main main;

	public SubmitAction(Main main, String name) {
		super(name);
		this.main = main;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String selectedWord = "";
		LetterTextField[][] fieldGrid = main.getFieldGrid();
		for (int row = 0; row < fieldGrid.length; row++) {
			for (int col = 0; col < fieldGrid[row].length; col++) {
				if (fieldGrid[row][col].selected) {
					selectedWord += fieldGrid[row][col].getText();
				}
			}
		}

		WordLabel[] wordLabelList = main.getWordLabelList();
		for (int w = 0; w < Game.WORD_LIST.length; w++) {
			String word = Game.WORD_LIST[w];
			if (word.equalsIgnoreCase(selectedWord)) {
				Font font = wordLabelList[w].getFont();
				Map attributes = font.getAttributes();
				attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
				wordLabelList[w].setFont(font.deriveFont(attributes));
			}
		}

		for (int row = 0; row < fieldGrid.length; row++) {
			for (int col = 0; col < fieldGrid[row].length; col++) {
				if (fieldGrid[row][col].selected) {
					fieldGrid[row][col].submitted = true;
					fieldGrid[row][col].selected = false;
					fieldGrid[row][col].setBackground(Main.SOLVED_BG);
				}
			}
		}
	}
}