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
public class ClearAction extends AbstractAction {
	/**
	 * Default
	 */
	private static final long serialVersionUID = 2359467297726922666L;
	private Game game;

	public ClearAction(Game game, String name) {
		super(name);
		this.game = game;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		game.clear(true);
	}
}
