/**
 * 
 */
package main;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
		fieldGrid[row][col].selected = !fieldGrid[row][col].selected;
		if (fieldGrid[row][col].selected) {
			fieldGrid[row][col].setBackground(Color.GREEN);
		} else {
			fieldGrid[row][col].setBackground(Color.WHITE);
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