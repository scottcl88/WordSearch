package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.font.TextAttribute;
import java.util.Map;
import javax.swing.*;

public class MainPanel extends JPanel {

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
	private JPanel rightPanel = new JPanel();

	public LetterTextField[][] getFieldGrid() {
		return fieldGrid;
	}

	public WordLabel[] getWordLabelList() {
		return wordLabelList;
	}

	public MainPanel(Game game) {
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

		createPanels();

		createWordList();

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
		mainPanel2.add(rightPanel);

		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.WEST);
		add(mainPanel2, BorderLayout.EAST);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createWordList() {
		rightPanel.setBorder(BorderFactory.createEmptyBorder(GAP, 15, GAP, 15));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		JLabel wordsLabel = new JLabel("Words");
		Font font = wordsLabel.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		attributes.put(TextAttribute.SIZE, 18);
		wordsLabel.setFont(font.deriveFont(attributes));
		rightPanel.add(wordsLabel);
		for (int w = 0; w < Game.WORD_LIST.length; w++) {
			wordLabelList[w] = new WordLabel(Game.WORD_LIST[w].toUpperCase());
			rightPanel.add(wordLabelList[w]);
		}
	}

	private void createPanels() {
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
		letterTextField.setEnabled(false);
		letterTextField.setHorizontalAlignment(JTextField.CENTER);
		letterTextField.setFont(letterTextField.getFont().deriveFont(Font.BOLD, FIELD_PTS));
		letterTextField.addMouseListener(new SelectAction(this, row, col));
		return letterTextField;
	}

}
