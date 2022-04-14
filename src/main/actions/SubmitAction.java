/**
 * 
 */
package main.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import main.MainPanel;

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

	@Override
	public void actionPerformed(ActionEvent e) {
		mainPanel.submitWord();
	}
}