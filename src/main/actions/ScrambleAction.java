/**
 * 
 */
package main.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import main.Game;

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
		game.start();
	}
}
