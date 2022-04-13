/**
 * 
 */
package main;

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
	String name = "";

	public WordLabel(String name) {
		super(name);
		this.name = name;
	}
}
