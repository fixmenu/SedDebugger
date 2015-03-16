package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class TextPanel extends JPanel {
	
	private InputPane input ; 
	private outputPane editText;
	private outputPane TerminalPane;
	private outputPane holdSpace;
	private outputPane patternSpace;
	
	private JScrollPane scrollPaneInput;
	private JScrollPane scrollPaneEdit;
	private JScrollPane scrollPaneTerminal;
	private JScrollPane scrollPaneHold;
	private JScrollPane scrollPanePattern;
	
	private JSplitPane HorizontalPane;
	private JSplitPane bottomHorizontalPane;
	private JSplitPane bottomHorizontalPane2;
	private JSplitPane VerticalPane;

	private TextLineNumber lineNumberOrigin;
	private TextLineNumber lineNumberEdited;
	private TextLineNumber lineNumberPattern;
	private TextLineNumber lineNumberHold;
	private TextLineNumber lineNumberTerm;
	
	public TextPanel(){
			
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createMatteBorder(0, 14, 0, 14, Color.DARK_GRAY));
		
		input = new InputPane();
		editText = new outputPane();
		TerminalPane = new outputPane();
		holdSpace = new outputPane();
		patternSpace = new outputPane();
		
		lineNumberOrigin  = new TextLineNumber(input.getInputText());
		lineNumberEdited  = new TextLineNumber(editText.getOutputText());
		lineNumberTerm  = new TextLineNumber(TerminalPane.getOutputText());
		lineNumberHold  = new TextLineNumber(holdSpace.getOutputText());
		lineNumberPattern  = new TextLineNumber(patternSpace.getOutputText());
		
		TerminalPane.getOutputText().setEditable(false);
		holdSpace.getOutputText().setEditable(false);
		patternSpace.getOutputText().setEditable(false);
		
		scrollPaneInput = new JScrollPane(input);
		scrollPaneEdit = new JScrollPane(editText);
		scrollPaneTerminal = new JScrollPane(TerminalPane);
		scrollPaneHold = new JScrollPane(holdSpace);
		scrollPanePattern = new JScrollPane(patternSpace);
		
		scrollPaneInput.setRowHeaderView(lineNumberOrigin);
		scrollPaneHold.setRowHeaderView(lineNumberHold);
		scrollPanePattern.setRowHeaderView(lineNumberPattern);
		scrollPaneEdit.setRowHeaderView(lineNumberEdited);
		scrollPaneTerminal.setRowHeaderView(lineNumberTerm);
		
		HorizontalPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,scrollPaneInput,scrollPaneEdit);
		bottomHorizontalPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,scrollPaneTerminal,scrollPaneHold);
		bottomHorizontalPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,bottomHorizontalPane,scrollPanePattern);
		VerticalPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,HorizontalPane,bottomHorizontalPane2);
		
		HorizontalPane.setDividerSize(10);
		HorizontalPane.setUI(new BasicSplitPaneUI() {
	        public BasicSplitPaneDivider createDefaultDivider() {
	        return new BasicSplitPaneDivider(this) {
	            public void setBorder(Border b) {
	            }

	            @Override
	                public void paint(Graphics g) {
	                g.setColor(Color.DARK_GRAY);
	                g.fillRect(0, 0, getSize().width, getSize().height);
	                    super.paint(g);
	                }
	        };
	        }
	    });
		
		VerticalPane.setDividerSize(10);
		VerticalPane.setUI(new BasicSplitPaneUI() {
	        public BasicSplitPaneDivider createDefaultDivider() {
	        return new BasicSplitPaneDivider(this) {
	            public void setBorder(Border b) {
	            }

	            @Override
	                public void paint(Graphics g) {
	                g.setColor(Color.DARK_GRAY);
	                g.fillRect(0, 0, getSize().width, getSize().height);
	                    super.paint(g);
	                }
	        };
	        }
	    });
		
		bottomHorizontalPane.setDividerSize(10);
		bottomHorizontalPane.setUI(new BasicSplitPaneUI() {
	        public BasicSplitPaneDivider createDefaultDivider() {
	        return new BasicSplitPaneDivider(this) {
	            public void setBorder(Border b) {
	            }

	            @Override
	                public void paint(Graphics g) {
	                g.setColor(Color.DARK_GRAY);
	                g.fillRect(0, 0, getSize().width, getSize().height);
	                    super.paint(g);
	                }
	        };
	        }
	    });
		
		bottomHorizontalPane2.setDividerSize(10);
		bottomHorizontalPane2.setUI(new BasicSplitPaneUI() {
	        public BasicSplitPaneDivider createDefaultDivider() {
	        return new BasicSplitPaneDivider(this) {
	            public void setBorder(Border b) {
	            }

	            @Override
	                public void paint(Graphics g) {
	                g.setColor(Color.DARK_GRAY);
	                g.fillRect(0, 0, getSize().width, getSize().height);
	                    super.paint(g);
	                }
	        };
	        }
	    });
		HorizontalPane.setBorder(null);
		VerticalPane.setBorder(null);
		bottomHorizontalPane.setBorder(null);
		bottomHorizontalPane2.setBorder(null);
		
		scrollPaneInput.setBorder(BorderFactory.createTitledBorder("Original File"));
		scrollPaneInput.setBackground(Color.WHITE);
		scrollPaneEdit.setBorder(BorderFactory.createTitledBorder("Edited File"));
		scrollPaneEdit.setBackground(Color.WHITE);
		scrollPaneTerminal.setBorder(BorderFactory.createTitledBorder("Terminal Output"));
		scrollPaneTerminal.setBackground(Color.WHITE);
		scrollPaneHold.setBorder(BorderFactory.createTitledBorder("Hold Space"));
		scrollPaneHold.setBackground(Color.WHITE);
		scrollPanePattern.setBorder(BorderFactory.createTitledBorder("Pattern Space"));
		scrollPanePattern.setBackground(Color.WHITE);
		
		HorizontalPane.setResizeWeight(0.4);
		VerticalPane.setResizeWeight(0.4);
		bottomHorizontalPane.setResizeWeight(0.72);
		bottomHorizontalPane2.setResizeWeight(0.80);
		
		add(VerticalPane,BorderLayout.CENTER);
	}


	public InputPane getInput() {
		return input;
	}


	public outputPane getEditText() {
		return editText;
	}


	public outputPane getTerminalPane() {
		return TerminalPane;
	}


	public outputPane getHoldSpace() {
		return holdSpace;
	}


	public outputPane getPatternSpace() {
		return patternSpace;
	}


	public void setInput(InputPane input) {
		this.input = input;
	}


	public void setEditText(outputPane editText) {
		this.editText = editText;
	}


	public void setTerminalPane(outputPane terminalPane) {
		TerminalPane = terminalPane;
	}


	public void setHoldSpace(outputPane holdSpace) {
		this.holdSpace = holdSpace;
	}


	public void setPatternSpace(outputPane patternSpace) {
		this.patternSpace = patternSpace;
	}
	
}
