package gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class outputPane extends JPanel {
	
	private JTextArea outputText;
	private JTextArea lineNumber;
	
	Font font = new Font("Verdana", Font.PLAIN, 12);
	
	public outputPane(){
		
		setLayout(new BorderLayout());

        outputText = new JTextArea();
        outputText.setFont(font);
        
        add(outputText);
	}

	public JTextArea getOutputText() {
		return outputText;
	}

	public void setInputText(JTextArea inputText) {
		this.outputText = inputText;
	} 

}
