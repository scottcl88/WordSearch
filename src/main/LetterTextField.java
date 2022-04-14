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
	private boolean selected = false;
	private boolean submitted = false;

	public LetterTextField() {
		super(2);
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * @return the submitted
	 */
	public boolean isSubmitted() {
		return submitted;
	}

	/**
	 * @param submitted the submitted to set
	 */
	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}
}