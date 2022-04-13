package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.*;

public class Main extends JPanel {

	private static final char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	private static final String[] words = { "benji", "macie", "betsy" };
	private static final int CLUSTER = 3;
	private static final int MAX_ROWS = 9;
	private static final int MAX_COLS = 9;
	private static final float FIELD_PTS = 32f;
	private static final int GAP = 3;
	private static final Color BG = Color.BLACK;
	private static final Color SOLVED_BG = Color.LIGHT_GRAY;
	public static final int TIMER_DELAY = 2 * 1000;
	private WordLabel[] wordLabelList = new WordLabel[words.length];
	private LetterTextField[][] fieldGrid = new LetterTextField[MAX_ROWS][MAX_ROWS];

	private JPanel[][] panels;

	public Main() {
		JPanel mainPanel = new JPanel(new GridLayout(CLUSTER, CLUSTER));
		JPanel mainPanel2 = new JPanel(new GridLayout(CLUSTER, CLUSTER));
//	        mainPanel.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
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
		GenerateWords();
		FillSpacesWithLetters();

		JPanel mainPanel3 = new JPanel();
		mainPanel3.setBorder(BorderFactory.createEmptyBorder(GAP, 15, GAP, 15));
//	        mainPanel3.setLayout(new GridLayout(1, 5, 1, 1));
		mainPanel3.setLayout(new BoxLayout(mainPanel3, BoxLayout.Y_AXIS));
		JLabel wordsLabel = new JLabel("Words");
		Font font = wordsLabel.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		attributes.put(TextAttribute.SIZE, 18);
		wordsLabel.setFont(font.deriveFont(attributes));
		mainPanel3.add(wordsLabel);
		for (int w = 0; w < words.length; w++) {
			wordLabelList[w] = new WordLabel(words[w].toUpperCase());
			mainPanel3.add(wordLabelList[w]);
		}
		mainPanel3.add(new JButton(new SubmitAction("Submit")), BorderLayout.PAGE_END);
		mainPanel3.add(new JButton(new ClearAction("Clear")), BorderLayout.PAGE_END);
		mainPanel3.add(new JButton(new ScrambleAction("Scramble")), BorderLayout.PAGE_END);
		mainPanel2.add(mainPanel3);

//	        mainPanel.add(mainPanel2, BorderLayout.WEST);
		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.WEST);
		add(mainPanel2, BorderLayout.EAST);
//	        add(new JButton(new SolveAction("Solve")), BorderLayout.PAGE_END);
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

	private void FillSpacesWithLetters() {
		Random r = new Random();

		
		for (int row = 0; row < fieldGrid.length; row++) {
			for (int col = 0; col < fieldGrid[row].length; col++) {
				if(fieldGrid[row][col].getText().isBlank()) {
					int randomItem = r.nextInt(words.length);
					String randomWord = words[randomItem];		
					randomItem = r.nextInt(randomWord.length());
					String randomChar = randomWord.charAt(randomItem)+"";
					fieldGrid[row][col].setText(randomChar.toUpperCase());					
				}
			}
		}
	}

	private void GenerateWords() {
		for (int row = 0; row < fieldGrid.length; row++) {
			for (int col = 0; col < fieldGrid[row].length; col++) {
				fieldGrid[row][col].setText("");
			}
		}
		// int[] rowsUsed = new int[MAX_ROWS];
		List<Point> pointsUsed = new ArrayList<Point>();
		for (int w = 0; w < words.length; w++) {
			int randDirection = (int) (Math.random() * (2 - 1 + 1) + 1);
			boolean horizontal = randDirection == 1;

			String word = words[w];
			word = word.toUpperCase();

			int maxRow = MAX_ROWS - 1;
			int maxCol = MAX_COLS - 1;// word.length();

			if (horizontal) {
				maxCol -= word.length();
			} else {
				maxRow -= word.length();
			}

			int randRow = 0;// (int) (Math.random() * (maxRow - 0 + 1) + 0);
			int randCol = 0;// (int) (Math.random() * (maxCol - 0 + 1) + 0);
//			System.out.println("MAX_ROWS: " + MAX_ROWS + " - " + word.length());
//			System.out.println("Randrow: " + randRow + " - " + randCol);	
			boolean valid = false;
			int tries = 0;
			do {
				randRow = (int) (Math.random() * (maxRow - 0 + 1) + 0);
				randCol = (int) (Math.random() * (maxCol - 0 + 1) + 0);
				System.out.println("RanCol selected: " + randCol);

				boolean conflict = false;
				int colCheck = randCol;
				int rowCheck = randRow;
				for (int l = 0; l < word.length(); l++) {
					final int testRow = rowCheck;
					final int testCol = colCheck;
					if (pointsUsed.stream().filter(p -> p.y == testRow && p.x == testCol).findFirst().isPresent()) {
						if (fieldGrid[rowCheck][colCheck].getText() != word.charAt(l) + "") {
							valid = false;
							conflict = true;
							System.out.println("Point conflict found");
							break;
						}
					}
					if (horizontal) {
						colCheck++;
					} else {
						rowCheck++;
					}
//						if(randCol > maxCol) {
//							randCol = maxCol;
//						}
				}
				if (!conflict) {
					valid = true;
				}

				tries++;
				if (tries >= 99) {
					System.out.println("Tries is more than 100");
				}
			} while (!valid && tries < 100);
			try {
				for (int l = 0; l < word.length(); l++) {
					pointsUsed.add(new Point(randCol, randRow));
					fieldGrid[randRow][randCol].setText(word.charAt(l) + "");
					if (horizontal) {
						randCol++;
					} else {
						randRow++;
					}
				}
			} catch (Exception ex) {
				System.out.println("RandRow = " + randRow);
				System.out.println("RandCol = " + randCol);
				throw ex;
			}
		}
	}

	private class LetterTextField extends JTextField {

		boolean selected = false;
		boolean submitted = false;
		int row, col;

		public LetterTextField(int row, int col) {
			super(2);
			this.row = row;
			this.col = col;
		}
	}

	private class WordLabel extends JLabel {

		String name = "";

		public WordLabel(String name) {
			super(name);
			this.name = name;
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

	private class SubmitAction extends AbstractAction {
		public SubmitAction(String name) {
			super(name);
		}

		public SubmitAction() {
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Submitting");

			String selectedWord = "";
			for (int row = 0; row < fieldGrid.length; row++) {
				for (int col = 0; col < fieldGrid[row].length; col++) {
					if (fieldGrid[row][col].selected) {
						selectedWord += fieldGrid[row][col].getText();
					}
				}
			}

			System.out.println("selectedWord = " + selectedWord);

			for (int w = 0; w < words.length; w++) {
				String word = words[w];
				if (word.equalsIgnoreCase(selectedWord)) {
					Font font = wordLabelList[w].getFont();
					Map attributes = font.getAttributes();
					attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
					wordLabelList[w].setFont(font.deriveFont(attributes));
				}
			}

			for (int row = 0; row < fieldGrid.length; row++) {
				for (int col = 0; col < fieldGrid[row].length; col++) {
					if (fieldGrid[row][col].selected) {
						fieldGrid[row][col].submitted = true;
						fieldGrid[row][col].selected = false;
						fieldGrid[row][col].setBackground(Color.GRAY);
					}
				}
			}
		}
	}

	private class ClearAction extends AbstractAction {
		public ClearAction(String name) {
			super(name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Clear");
			for (int row = 0; row < fieldGrid.length; row++) {
				for (int col = 0; col < fieldGrid[row].length; col++) {
					fieldGrid[row][col].selected = false;
					fieldGrid[row][col].setBackground(Color.WHITE);
				}
			}
		}
	}

	private class ScrambleAction extends AbstractAction {
		public ScrambleAction(String name) {
			super(name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Scramble words");
			GenerateWords();

		}
	}

	private static void createAndShowGui() {
		Main mainPanel = new Main();

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
