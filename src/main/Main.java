package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.*;

public class Main extends JPanel {

	 private static final int CLUSTER = 3;
	    private static final int MAX_ROWS = 9;
	    private static final float FIELD_PTS = 32f;
	    private static final int GAP = 3;
	    private static final Color BG = Color.BLACK;
	    private static final Color SOLVED_BG = Color.LIGHT_GRAY;
	    public static final int TIMER_DELAY = 2 * 1000;
	    private LetterTextField[][] fieldGrid = new LetterTextField[MAX_ROWS][MAX_ROWS];

	    public Main() {
	        JPanel mainPanel = new JPanel(new GridLayout(CLUSTER, CLUSTER));
	        JPanel mainPanel2 = new JPanel(new GridLayout(CLUSTER, CLUSTER));
//	        mainPanel.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
	        mainPanel.setBackground(BG);
	        JPanel[][] panels = new JPanel[CLUSTER][CLUSTER];
	        for (int i = 0; i < panels.length; i++) {
	            for (int j = 0; j < panels[i].length; j++) {
	                panels[i][j] = new JPanel(new GridLayout(CLUSTER, CLUSTER, 0, 0));
	                panels[i][j].setBackground(BG);
//	                panels[i][j].addMouseListener(new SelectAction(""));
//	                panels[i][j].add(new JButton(new SelectAction("")), BorderLayout.PAGE_START);
//	                panels[i][j].setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
	                mainPanel.add(panels[i][j]);
	            }
	        }

	        for (int row = 0; row < fieldGrid.length; row++) {
	            for (int col = 0; col < fieldGrid[row].length; col++) {
	                fieldGrid[row][col] = createField(row, col);
	                int i = row / 3;
	                int j = col / 3;
	                panels[i][j].add(fieldGrid[row][col]);
	            }
	        }

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
	        mainPanel3.add(new JLabel("Something"));
	        mainPanel3.add(new JLabel("Rocket"));
	        mainPanel3.add(new JLabel("League"));
	        mainPanel2.add(mainPanel3);
	        
//	        mainPanel.add(mainPanel2, BorderLayout.WEST);
	        setLayout(new BorderLayout());
	        add(mainPanel, BorderLayout.WEST);
	        add(mainPanel2, BorderLayout.EAST);
//	        add(new JButton(new SolveAction("Solve")), BorderLayout.PAGE_END);
	    }
	    private class LetterTextField extends JTextField {

	    	boolean selected = false;
	    	int row, col;
	        public LetterTextField(int row, int col) {
	        	super(2);
	        	this.row = row;
	        	this.col = col;
	        }
	    }

	    private LetterTextField createField(int row, int col) {
	    	LetterTextField letterTextField = new LetterTextField(row, col);
//	        JTextField field = new JTextField(2);
	    	letterTextField.setText("A");
	    	letterTextField.setEnabled(false);
	    	letterTextField.setHorizontalAlignment(JTextField.CENTER);
	    	letterTextField.setFont(letterTextField.getFont().deriveFont(Font.BOLD, FIELD_PTS));
	        letterTextField.addMouseListener(new SelectAction(row, col));
	        return letterTextField;
	    }
	    private class SelectAction implements MouseListener  {

	    	int row, col;
	        public SelectAction(int row, int col) {
	        	this.row = row;
	        	this.col = col;
	        }

			@Override
			public void mouseClicked(MouseEvent e) {
                fieldGrid[row][col].selected = !fieldGrid[row][col].selected;
				if(fieldGrid[row][col].selected) {
	                fieldGrid[row][col].setBackground(Color.GREEN);
				}else {
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

	    private class SolveAction extends AbstractAction {

	        public SolveAction(String name) {
	            super(name);
	            int mnemonic = (int) name.charAt(0);
	            putValue(MNEMONIC_KEY, mnemonic);
	        }

	        @Override
	        public void actionPerformed(ActionEvent e) {
	            new Timer(TIMER_DELAY, new ActionListener() {
	                private int i = 0;
	                private int j = 0;

	                @Override
	                public void actionPerformed(ActionEvent e) {
	                    // MAX_ROWS is 9
	                    if (i == MAX_ROWS) {
	                        ((Timer) e.getSource()).stop();
	                    }
	                    if (j == MAX_ROWS) {
	                        i++;
	                        j = 0;
	                    }
	                    int number = (int) (MAX_ROWS * Math.random()) + 1;
	                    fieldGrid[i][j].setBackground(SOLVED_BG);
	                    fieldGrid[i][j].setText(String.valueOf(number));

	                    j++;
	                }
	            }).start();
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
