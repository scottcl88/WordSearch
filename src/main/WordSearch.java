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
		JFrame frame = new JFrame("Word Search");
		
		Game game = new Game();
		MainPanel mainPanel = new MainPanel(frame, game);
		game.loadWords();
		mainPanel.setup();
		game.setMainPanel(mainPanel);
		game.start();

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
