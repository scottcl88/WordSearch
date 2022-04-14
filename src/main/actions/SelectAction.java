/**
 * 
 */
package main.actions;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import main.Game;
import main.LetterTextField;
import main.MainPanel;

/**
 * @author scott
 *
 */
public class SelectAction implements MouseListener {

	private MainPanel mainPanel;
	int row, col;

	public SelectAction(MainPanel mainPanel, int row, int col) {
		this.mainPanel = mainPanel;
		this.row = row;
		this.col = col;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		LetterTextField[][] fieldGrid = mainPanel.getFieldGrid();
		fieldGrid[row][col].setSelected(!fieldGrid[row][col].isSelected());
		if (fieldGrid[row][col].isSelected()) {
			fieldGrid[row][col].setBackground(Game.SELECTED_BG);
		} else {
			fieldGrid[row][col].setBackground(Game.DEFAULT_BG);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}