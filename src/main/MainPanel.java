package main;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.*;

import main.actions.ClearAction;
import main.actions.ScrambleAction;
import main.actions.SelectAction;
import main.actions.SubmitAction;

public class MainPanel extends JPanel {

	/**
	 * Default
	 */
	private static final long serialVersionUID = -7478413237523422826L;
	private static final float FIELD_PTS = 32f;
	private WordLabel[] wordLabelList;
	private LetterTextField[][] fieldGrid = new LetterTextField[Game.MAX_ROWS][Game.MAX_COLS];

	private Game game;
	private JFrame frame;

	private JPanel[][] panels;
	private JPanel letterPanel = new JPanel(new GridLayout(Game.MAX_ROWS, Game.MAX_COLS));
	private JPanel rightPanel = new JPanel();

	public LetterTextField[][] getFieldGrid() {
		return fieldGrid;
	}

	public WordLabel[] getWordLabelList() {
		return wordLabelList;
	}

	public MainPanel(JFrame frame, Game game) {
		this.frame = frame;
		this.game = game;
	}
	
	public void setup() {
		wordLabelList = new WordLabel[game.getWordList().size()];

		createLetterPanels();
		createTextFields();

		setLayout(new BorderLayout());
		add(letterPanel, BorderLayout.WEST);

		createWordList();

		createButtons();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void submitWord() {
		String selectedWord = "";
		for (int row = 0; row < fieldGrid.length; row++) {
			for (int col = 0; col < fieldGrid[row].length; col++) {
				if (fieldGrid[row][col].isSelected()) {
					selectedWord += fieldGrid[row][col].getText();
				}
			}
		}

		boolean foundWord = false;
		for (int w = 0; w < game.getWordList().size(); w++) {
			String word = game.getWordList().get(w);
			if (word.equalsIgnoreCase(selectedWord)) {
				foundWord = true;

				Font font = wordLabelList[w].getFont();
				Map attributes = font.getAttributes();
				attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
				wordLabelList[w].setFont(font.deriveFont(attributes));

				ArrayList<Point> points = wordLabelList[w].getPoints();
				for (int i = 0; i < points.size(); i++) {
					Point p = points.get(i);
					fieldGrid[p.y][p.x].setSubmitted(true);
					fieldGrid[p.y][p.x].setSelected(false);
					fieldGrid[p.y][p.x].setBackground(Game.SOLVED_BG);
				}
			}
		}
		if (!foundWord) {
			JOptionPane.showMessageDialog(frame, "No word found with selected letters");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void clearSubmittedWords() {
		for (int w = 0; w < game.getWordList().size(); w++) {
			Font font = wordLabelList[w].getFont();
			Map attributes = font.getAttributes();
			attributes.clear();
			attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH);
			wordLabelList[w].setFont(font.deriveFont(attributes));
		}
	}

	public void clearLetters() {
		for (int row = 0; row < fieldGrid.length; row++) {
			for (int col = 0; col < fieldGrid[row].length; col++) {
				fieldGrid[row][col].setText("");
			}
		}
	}

	private void createLetterPanels() {
		panels = new JPanel[Game.MAX_ROWS][Game.MAX_COLS];
		for (int i = 0; i < panels.length; i++) {
			for (int j = 0; j < panels[i].length; j++) {
				GridBagLayout layout = new GridBagLayout();
				panels[i][j] = new JPanel();
				panels[i][j].setLayout(layout);
				letterPanel.add(panels[i][j]);
			}
		}
	}

	private void createButtons() {
		JLabel emptyLabel = new JLabel();
		emptyLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		rightPanel.add(emptyLabel, BorderLayout.PAGE_END);
		rightPanel.add(new JButton(new SubmitAction(this, "Submit")), BorderLayout.PAGE_END);
		JLabel emptyLabel2 = new JLabel();
		emptyLabel2.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		rightPanel.add(emptyLabel2, BorderLayout.PAGE_END);
		rightPanel.add(new JButton(new ClearAction(game, "Clear")), BorderLayout.PAGE_END);
		JLabel emptyLabel3 = new JLabel();
		emptyLabel3.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		rightPanel.add(emptyLabel3, BorderLayout.PAGE_END);
		rightPanel.add(new JButton(new ScrambleAction(game, "Scramble")), BorderLayout.PAGE_END);
		add(rightPanel, BorderLayout.EAST);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createWordList() {
		rightPanel.setBorder(BorderFactory.createEmptyBorder(3, 15, 3, 15));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		JLabel wordsLabel = new JLabel("Words");
		Font font = wordsLabel.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		attributes.put(TextAttribute.SIZE, 18);
		wordsLabel.setFont(font.deriveFont(attributes));
		rightPanel.add(wordsLabel);
		for (int w = 0; w < game.getWordList().size(); w++) {
			wordLabelList[w] = new WordLabel(game.getWordList().get(w).toUpperCase());
			rightPanel.add(wordLabelList[w]);
		}
	}

	private void createTextFields() {
		GridBagConstraints gbc = new GridBagConstraints();
		for (int row = 0; row < fieldGrid.length; row++) {
			for (int col = 0; col < fieldGrid[row].length; col++) {
				fieldGrid[row][col] = createField(row, col);
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.gridx = 0;
				gbc.gridy = 0;
				panels[row][col].add(fieldGrid[row][col], gbc);
			}
		}
	}

	private LetterTextField createField(int row, int col) {
		LetterTextField letterTextField = new LetterTextField();
		letterTextField.setEnabled(false);
		letterTextField.setHorizontalAlignment(JTextField.CENTER);
		letterTextField.setFont(letterTextField.getFont().deriveFont(Font.BOLD, FIELD_PTS));
		letterTextField.addMouseListener(new SelectAction(this, row, col));
		return letterTextField;
	}

}
