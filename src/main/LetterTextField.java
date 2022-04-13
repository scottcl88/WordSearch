/**
 * 
 */
package main;

import javax.swing.JTextField;

/**
 * @author scott
 *
 */
public class LetterTextField extends JTextField {

	/**
	 * Default
	 */
	private static final long serialVersionUID = 2911170811732842628L;
	boolean selected = false;
	boolean submitted = false;
	int row, col;

	public LetterTextField(int row, int col) {
		super(2);
		this.row = row;
		this.col = col;
	}
}