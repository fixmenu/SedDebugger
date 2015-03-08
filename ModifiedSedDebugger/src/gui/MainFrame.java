package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.PageAttributes.OriginType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

public class MainFrame extends JFrame {
	
	private TextPanel textPanel;
	private ToolBar toolBar;
	private JProgressBar pb;
	private LinePainter painter;
	
	private Process proc = null;
	private Runtime rt = null;
	
	private BufferedReader bfr;
	private BufferedReader procIn = null;
	private BufferedReader procErr = null;
	
	private PrintStream procOutput = null;
	private PrintStream standardOut;
	
	private ArrayList<String> hs = new ArrayList<String>();
	private ArrayList<String> ps = new ArrayList<String>();
	private ArrayList<String> tempArr = new ArrayList<String>();
	
	private int i = 0;
	private int arrayCounter = 0;
	private int textLineCounter = 0;

	private int linecounter ;
	private int prevCountedLines=0;
	
	private String HighlightedWord;
	private String line = null;
	private String temp = "";
	
	private JTextArea OriginalText = new JTextArea();
	private JTextArea textHold = new JTextArea();
	private JTextArea textPattern = new JTextArea();
	private JTextArea EditedText = new JTextArea();
	
	public MainFrame(){
		super("Sed Debugger");
		setLayout(new BorderLayout());
		
		rt = Runtime.getRuntime();
		standardOut = System.out;
		
		textPanel = new TextPanel();
		
		OriginalText = textPanel.getInput().getInputText();
		textHold = textPanel.getHoldSpace().getOutputText();
		textPattern = textPanel.getPatternSpace().getOutputText();
		EditedText = textPanel.getEditText().getOutputText();
		
		painter = new LinePainter(OriginalText);
		painter = new LinePainter(EditedText);
		painter = new LinePainter(textHold);
		painter = new LinePainter(textPattern);
		
		pb = new JProgressBar();
		
		PrintStream printStream = new PrintStream(new CustomOutputStream(
					textPanel.getTerminalPane().getOutputText()));
		
		System.setOut(printStream);
		System.setErr(printStream);
		
		toolBar = new ToolBar();
		toolBar.setBackground(Color.WHITE);
		toolBar.getPlay().addActionListener(new ActionListener() {

		
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					textPanel.getHoldSpace().getOutputText().setText("");
					textPanel.getPatternSpace().getOutputText().setText("");
					textPanel.getTerminalPane().getOutputText().getDocument().remove(0,
							textPanel.getTerminalPane().getOutputText().getDocument().getLength());
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
				
				temp = toolBar.getCommandField().getText();
				
				if(proc == null){
					try {
						proc = rt.exec(temp);
						procErr = new BufferedReader(new InputStreamReader(proc
								.getErrorStream()));
						procIn = new BufferedReader(new InputStreamReader(proc
								.getInputStream()));
						procOutput = new PrintStream(proc.getOutputStream());
						
						Thread thread = new Thread() {
							public void run() {
								String line;
								try {	
										
									while ((line = procIn.readLine()) != null) {
										System.out.println(line);
										
									
										if (line.contains("HOLD")){
											hs.add(line);
											}
										
										if (line.contains("PATTERN")) {
											ps.add(line);
										}
										if(line.contains("[DBG_OUTPUT]")){
											tempArr.add(line);
										}
										if (line.contains("MATCH_S")){
											int Line;
											int Start;
											int Offset;
											int End;
											int Length;
											
											String SourceSplit[] = OriginalText.getText().split("\n");
											String word[] = line.split(" ");
											
											Line   = Integer.parseInt(word[0].replaceAll("[\\D]", ""));
											Start  = Integer.parseInt(word[3]);
											End    = Integer.parseInt(word[7]);
											Offset = Integer.parseInt(word[5]);
											Length = Integer.parseInt(word[9]);
											
											System.out.print("Line: "    + Line   + "\n");
											System.out.print("Start: "   + Start  + "\n");
											System.out.print("End: "     + End    + "\n");
											System.out.print("Offset: "  + Offset + "\n");
											System.out.print("Lenght: "  + Length + "\n");
											
											HighlightedWord = SourceSplit[Line-1].substring(Offset,End);
											
										}
										}
								} catch (IOException e) {
									e.printStackTrace();
								}

							}
								
						};
						thread.start();
					}
					catch (IOException e) {
							e.printStackTrace();
						}
			}else {
				procOutput.println(temp + "\n");
				procOutput.flush();
				System.out.println(temp);
			}
			}});
		
		toolBar.getHighlight().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Highlight(OriginalText,HighlightedWord);
				
		}});
		
		toolBar.getStepIn().addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				procOutput = new PrintStream(proc.getOutputStream());
				procOutput.println("n");
				if(!hs.isEmpty() && !ps.isEmpty()){
				if (i < hs.size()) {
					textHold.append(hs.get(i) + "\n");	
				}
				if (i < ps.size()) {
					textPattern.append(ps.get(i) + "\n");
				}
				i++;
				}
				if(tempArr.size() > arrayCounter)
				{
					EditedText.append(tempArr.get(arrayCounter)+ "\n");
					arrayCounter++;
				}
				procOutput.flush();
			}
		});
		
		
//		linecounter = checkLineNumber(OriginalText);
//		int difference = linecounter - prevCountedLines;
//       
//        while( difference != 0 ){
//        	JRadioButton rb = new JRadioButton();
//        	
//        	if(difference > 0 ){
//        	textPanel.getInput().setBreakPoint(rb);
//        	
//        }
//        
//        if( difference < 0){
//        		textPanel.getInput().getBreakPoint().remove(rb);
//        	}
//        difference--;
//        }
//	
//        prevCountedLines = checkLineNumber(OriginalText);
        
		
		add(textPanel,BorderLayout.CENTER);
		add(toolBar, BorderLayout.NORTH);
		add(pb,BorderLayout.SOUTH);
		
		setJMenuBar(createMenuBar());
		
		setSize(1200,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
		setVisible(true);
	}
	
	//******MENUBAR******//
	public JMenuBar createMenuBar(){
		 MyMenuBar menuBar = new MyMenuBar();
		 JMenu file = new JMenu("File");
		 file.setForeground(Color.WHITE);
		 JMenu debug = new JMenu("Debug");
		 debug.setForeground(Color.WHITE);
		 JMenu help = new JMenu("Help");
		 help.setForeground(Color.WHITE);
		 
		 JMenuItem newFile = new JMenuItem("New");
		 JMenuItem saveFile = new JMenuItem("Save");
		 JMenuItem openFile = new JMenuItem("Open");
		 JMenuItem exit = new JMenuItem("Exit");
		 
		 openFile.addActionListener(new OpenListener());
		 
		 file.add(newFile);
		 file.add(openFile);
		 file.add(saveFile);
		 file.add(exit);
		 
		 JMenuItem run = new JMenuItem("Run");
		 JMenuItem stop = new JMenuItem("Stop");
		 JMenuItem refresh = new JMenuItem("Refresh");
		 JMenuItem stepIn = new JMenuItem("Step In");
		 JMenuItem stepOut = new JMenuItem("Step Out");
		 JMenuItem stepOver = new JMenuItem("Step Over");
		 JMenuItem breakPoint = new JMenuItem("New Breakpoint");
		 
		 debug.add(run);
		 debug.add(stop);
		 debug.add(refresh);
		 debug.add(stepIn);
		 debug.add(stepOut);
		 debug.add(stepOver);
		 debug.add(breakPoint);
		 
		 JMenuItem commentHelp = new JMenuItem("Comment Help");
		 JMenuItem about = new JMenuItem("About");
		 
		 help.add(commentHelp);
		 help.add(about);
		 
		 menuBar.add(file);
		 menuBar.add(debug);
		 menuBar.add(help);
		 return menuBar;
	}
	
	//******HIGHLIGHTING******//
	class MyHighLightPainter extends DefaultHighlighter.DefaultHighlightPainter {
		public MyHighLightPainter(Color color) {
			super(color);
		}
	};

	Highlighter.HighlightPainter myHighlightPainter = new MyHighLightPainter(
			Color.green);
	
	public void Highlight(JTextComponent textcomp, String pattern) {
		try {
			Highlighter highlight = textcomp.getHighlighter();
			Document doc = textcomp.getDocument();
			String text = doc.getText(0, doc.getLength());
			int pos = 0;
			while ((pos = text.toUpperCase()
					.indexOf(pattern.toUpperCase(), pos)) >= 0) {
				highlight.addHighlight(pos, pos + pattern.length(),
						myHighlightPainter);
				pos += pattern.length();
			}
		} catch (Exception e) {

		}
	}
	
	public int checkLineNumber(JTextArea ta){
		String[] lines = ta.getText().split("/n");
		int linesCount = lines.length;
		
		return linesCount;
	}
	//******OPEN FÝLE******//
	private class OpenListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			textPanel.getInput().getInputText().setText("");
			final JFileChooser fc = new JFileChooser();
			fc.setMultiSelectionEnabled(true);
			JList list = new JList();
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(list)) {
				File file = fc.getSelectedFile();
				try {
					bfr = new BufferedReader(new FileReader(file));
				} catch (FileNotFoundException e1) {
					
					e1.printStackTrace();
				}

				try {
					line = bfr.readLine();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
				tempArr=null;
				tempArr=new ArrayList<String>();
				textPanel.getEditText().getOutputText().setText(null);
				while (line != null) {
					
					OriginalText.append(line + "\n");
					try {
						line = bfr.readLine();
					} catch (IOException e1) {
				
						e1.printStackTrace();
					}
				}

			}
		}
	}
}

