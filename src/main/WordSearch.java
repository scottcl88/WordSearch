/**
 * 
 */
package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * @author scott
 *
 */
public class WordSearch {


	private static void createAndShowGui() {
		Game game = new Game();
		MainPanel mainPanel = new MainPanel(game);
		game.setMainPanel(mainPanel);
		game.generateWords();
		game.fillSpacesWithLetters();

		JFrame frame = new JFrame("Word Search");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			createAndShowGui();
		});
	}
}
