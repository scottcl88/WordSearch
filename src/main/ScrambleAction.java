/**
 * 
 */
package main;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * @author scott
 *
 */
public class ScrambleAction extends AbstractAction {
	/**
	 * Default
	 */
	private static final long serialVersionUID = 3858345266187730976L;
	private Game game;

	public ScrambleAction(Game game, String name) {
		super(name);
		this.game = game;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		game.clear(false);
		game.generateWords();
		game.fillSpacesWithLetters();
		game.resetWordList();
	}
}
