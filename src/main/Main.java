package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.*;

public class Main extends JPanel {

	/**
	 * Default
	 */
	private static final long serialVersionUID = -7478413237523422826L;
	private static final int CLUSTER = 3;
	private static final float FIELD_PTS = 32f;
	private static final int GAP = 3;
	private static final Color BG = Color.BLACK;
	public static final Color SOLVED_BG = Color.LIGHT_GRAY;
	public static final int TIMER_DELAY = 2 * 1000;
	private WordLabel[] wordLabelList = new WordLabel[Game.WORD_LIST.length];
	private LetterTextField[][] fieldGrid = new LetterTextField[Game.MAX_ROWS][Game.MAX_ROWS];

	private JPanel[][] panels;
	private Game game;

	public LetterTextField[][] getFieldGrid() {
		return fieldGrid;
	}
	public WordLabel[] getWordLabelList() {
		return wordLabelList;
	}

	public Main(Game game) {
		JPanel mainPanel = new JPanel(new GridLayout(CLUSTER, CLUSTER));
		JPanel mainPanel2 = new JPanel(new GridLayout(CLUSTER, CLUSTER));
		mainPanel.setBackground(BG);
		panels = new JPanel[CLUSTER][CLUSTER];
		for (int i = 0; i < panels.length; i++) {
			for (int j = 0; j < panels[i].length; j++) {
				panels[i][j] = new JPanel(new GridLayout(CLUSTER, CLUSTER, 0, 0));
				panels[i][j].setBackground(BG);
				mainPanel.add(panels[i][j]);
			}
		}

		CreatePanels();

		JPanel mainPanel3 = new JPanel();
		mainPanel3.setBorder(BorderFactory.createEmptyBorder(GAP, 15, GAP, 15));
		mainPanel3.setLayout(new BoxLayout(mainPanel3, BoxLayout.Y_AXIS));
		JLabel wordsLabel = new JLabel("Words");
		Font font = wordsLabel.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		attributes.put(TextAttribute.SIZE, 18);
		wordsLabel.setFont(font.deriveFont(attributes));
		mainPanel3.add(wordsLabel);
		for (int w = 0; w < Game.WORD_LIST.length; w++) {
			wordLabelList[w] = new WordLabel(Game.WORD_LIST[w].toUpperCase());
			mainPanel3.add(wordLabelList[w]);
		}
		mainPanel3.add(new JButton(new SubmitAction(this, "Submit")), BorderLayout.PAGE_END);
		mainPanel3.add(new JButton(new ClearAction(game, "Clear")), BorderLayout.PAGE_END);
		mainPanel3.add(new JButton(new ScrambleAction(game, "Scramble")), BorderLayout.PAGE_END);
		mainPanel2.add(mainPanel3);

		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.WEST);
		add(mainPanel2, BorderLayout.EAST);
	}

	private void CreatePanels() {
		for (int row = 0; row < fieldGrid.length; row++) {
			for (int col = 0; col < fieldGrid[row].length; col++) {
				fieldGrid[row][col] = createField(row, col);
				int i = row / 3;
				int j = col / 3;
				panels[i][j].add(fieldGrid[row][col]);
			}
		}
	}


	private LetterTextField createField(int row, int col) {
		LetterTextField letterTextField = new LetterTextField(row, col);
//	        JTextField field = new JTextField(2);
//	    	letterTextField.setText("A");
		letterTextField.setEnabled(false);
		letterTextField.setHorizontalAlignment(JTextField.CENTER);
		letterTextField.setFont(letterTextField.getFont().deriveFont(Font.BOLD, FIELD_PTS));
		letterTextField.addMouseListener(new SelectAction(row, col));
		return letterTextField;
	}

	private class SelectAction implements MouseListener {

		int row, col;

		public SelectAction(int row, int col) {
			this.row = row;
			this.col = col;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
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


	private static void createAndShowGui() {
		Game game = new Game();
		Main mainPanel = new Main(game);
		game.setMainPanel(mainPanel);
		game.GenerateWords();
		game.FillSpacesWithLetters();

		JFrame frame = new JFrame("Word Search");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			createAndShowGui();
		});
	}

}
