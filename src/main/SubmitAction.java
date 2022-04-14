/**
 * 
 */
package main;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
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
	private MainPanel mainPanel;

	public SubmitAction(MainPanel mainPanel, String name) {
		super(name);
		this.mainPanel = mainPanel;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void actionPerformed(ActionEvent e) {
		String selectedWord = "";
		LetterTextField[][] fieldGrid = mainPanel.getFieldGrid();
		for (int row = 0; row < fieldGrid.length; row++) {
			for (int col = 0; col < fieldGrid[row].length; col++) {
				if (fieldGrid[row][col].selected) {
					selectedWord += fieldGrid[row][col].getText();
				}
			}
		}

		WordLabel[] wordLabelList = mainPanel.getWordLabelList();
		for (int w = 0; w < Game.WORD_LIST.length; w++) {
			String word = Game.WORD_LIST[w];
			if (word.equalsIgnoreCase(selectedWord)) {
				Font font = wordLabelList[w].getFont();
				Map attributes = font.getAttributes();
				attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
				wordLabelList[w].setFont(font.deriveFont(attributes));
				
				ArrayList<Point> points = wordLabelList[w].getPoints();
				System.out.println(points.toString());
				for(int i = 0; i < points.size(); i ++) {
					Point p = points.get(i);
					fieldGrid[p.y][p.x].submitted = true;
					fieldGrid[p.y][p.x].selected = false;
					fieldGrid[p.y][p.x].setBackground(MainPanel.SOLVED_BG);
				}
			}
		}
	}
}