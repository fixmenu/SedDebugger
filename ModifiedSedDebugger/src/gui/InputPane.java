package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class InputPane extends JPanel {
	
	private JTextArea inputText;
	private JPanel breakPoint;
	private TextLineNumber tln;
	private int linecounter ;
	private int prevCountedLines=0;
	Font font = new Font("Verdana", Font.PLAIN, 12);
	
	public InputPane(){
		
		setLayout(new BorderLayout());

        inputText = new JTextArea(10,20);
        inputText.setFont(font);
        breakPoint = new JPanel();
        
        breakPoint.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.gray));
        linecounter = checkLineNumber(inputText);
		int difference = linecounter - prevCountedLines;
       
        while( difference != 0 ){
        	JRadioButton rb = new JRadioButton();
        	
        	if(difference > 0 ){
        	breakPoint.add(rb);
        	
        }
        
        if( difference < 0){
        		breakPoint.remove(rb);
        	}
        difference--;
        }
	
        prevCountedLines = checkLineNumber(inputText);
        
        add(inputText);
        add(breakPoint, BorderLayout.WEST);
        
        
	}
	public int checkLineNumber(JTextArea ta){
		String[] lines = inputText.getText().split("/n");
		int linesCount = lines.length;
		
		return linesCount;
	}

	public JTextArea getInputText() {
		return inputText;
	}

	public void setInputText(JTextArea inputText) {
		this.inputText = inputText;
	}
	
	public void setBreakPoint(JRadioButton rd){
		breakPoint.add(rd);
	}
}
