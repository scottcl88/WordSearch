package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.font.TextAttribute;
import java.util.Map;
import javax.swing.*;

public class MainPanel extends JPanel {

	/**
	 * Default
	 */
	private static final long serialVersionUID = -7478413237523422826L;
	//private static final int CLUSTER = 3;
	private static final float FIELD_PTS = 32f;
	private static final Color BG = Color.BLACK;
	private static final Color BG2 = Color.BLUE;
	private static final Color BG3 = Color.RED;
	public static final Color SOLVED_BG = Color.LIGHT_GRAY;
	private WordLabel[] wordLabelList = new WordLabel[Game.WORD_LIST.length];
	private LetterTextField[][] fieldGrid = new LetterTextField[Game.MAX_ROWS][Game.MAX_COLS];

	private JPanel[][] panels;
	private JPanel rightPanel = new JPanel();

	public LetterTextField[][] getFieldGrid() {
		return fieldGrid;
	}

	public WordLabel[] getWordLabelList() {
		return wordLabelList;
	}

	public MainPanel(Game game) {
		JPanel mainPanel = new JPanel(new GridLayout(Game.MAX_ROWS, Game.MAX_COLS));
		mainPanel.setBackground(BG);
		panels = new JPanel[Game.MAX_ROWS][Game.MAX_COLS];
		for (int i = 0; i < panels.length; i++) {
			for (int j = 0; j < panels[i].length; j++) {
				GridBagLayout layout=new GridBagLayout(); 
				panels[i][j] = new JPanel();
				panels[i][j].setLayout(layout); 
				
//				int randDirection = (int) (Math.random() * (2 - 1 + 1) + 1);
//				boolean horizontal = randDirection == 1;
//				if (horizontal) {
//					panels[i][j].setBackground(BG2);
//				} else {
//					panels[i][j].setBackground(BG3);
//				}				
				mainPanel.add(panels[i][j]);
			}
		}

		createTextFields();

		createWordList();

		//JPanel mainPanel2 = new JPanel(new GridLayout(Game.MAX_ROWS, Game.MAX_ROWS));
		
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
		//mainPanel2.add(rightPanel);

		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.WEST);
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
		for (int w = 0; w < Game.WORD_LIST.length; w++) {
			wordLabelList[w] = new WordLabel(Game.WORD_LIST[w].toUpperCase());
			rightPanel.add(wordLabelList[w]);
		}
	}

	private void createTextFields() {
		GridBagConstraints gbc = new GridBagConstraints();
		for (int row = 0; row < fieldGrid.length; row++) {
			for (int col = 0; col < fieldGrid[row].length; col++) {
				fieldGrid[row][col] = createField(row, col);
				gbc.fill=GridBagConstraints.HORIZONTAL; 
				gbc.gridx=0;   
				gbc.gridy=0;  
				panels[row][col].add(fieldGrid[row][col], gbc);
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
