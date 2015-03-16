package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

public class ToolBar extends JPanel {
	
	private JToolBar toolBar;
	private JButton play;
	private JButton stop;
	private JButton refresh;
	private JButton stepIn;
	private JButton stepOut;
	private JButton stepOver;
	private JButton highlight;
	private JButton breakPoint;
	private JTextField commandField;
	private JLabel commandline;
	
	public ToolBar(){
		
		toolBar = new JToolBar();
		setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.setBackground(Color.WHITE);
		setBorder(BorderFactory.createMatteBorder(0, 4, 2, 4, Color.DARK_GRAY));
		
		commandField = new JTextField();
		commandline = new JLabel("Command: ");
		
		Dimension dim = commandField.getPreferredSize();
		dim.width  = 300;
		dim.height = 35;
		commandField.setPreferredSize(dim);
		commandField.setFont(new Font("Courier New", Font.PLAIN, 12));
		commandline.setFont(new Font("Sans Serif", Font.PLAIN, 14));
		
		play = new JButton("");
		stop = new JButton("");
		refresh = new JButton("");
		stepIn = new JButton("");
		stepOut = new JButton("");
		stepOver = new JButton("");
		highlight = new JButton("");
		breakPoint = new JButton("");
		
		play.setIcon(createIcon("/icons/play-25.png"));
		stop.setIcon(createIcon("/icons/stop-25.png"));
		refresh.setIcon(createIcon("/icons/refresh-26.png"));
		stepIn.setIcon(createIcon("/icons/import-26.png"));
		stepOut.setIcon(createIcon("/icons/export-26.png"));
		stepOver.setIcon(createIcon("/icons/down_left-24.png"));
		highlight.setIcon(createIcon("/icons/chisel_tip_marker-24.png"));
		breakPoint.setIcon(createIcon("/icons/plus-26.png"));
		
		play.setBorder(BorderFactory.createEmptyBorder());
		stop.setBorder(BorderFactory.createEmptyBorder());
		refresh.setBorder(BorderFactory.createEmptyBorder());
		stepIn.setBorder(BorderFactory.createEmptyBorder());
		stepOut.setBorder(BorderFactory.createEmptyBorder());
		stepOver.setBorder(BorderFactory.createEmptyBorder());
		highlight.setBorder(BorderFactory.createEmptyBorder());
		breakPoint.setBorder(BorderFactory.createEmptyBorder());
		
		play.setContentAreaFilled(false);
		stop.setContentAreaFilled(false);
		refresh.setContentAreaFilled(false);
		stepIn.setContentAreaFilled(false);
		stepOut.setContentAreaFilled(false);
		stepOver.setContentAreaFilled(false);
		highlight.setContentAreaFilled(false);
		breakPoint.setContentAreaFilled(false);
		
		play.setRolloverEnabled(true);
		play.setRolloverIcon(createIcon("/icons/play-25g.png"));
		play.setFocusPainted(false);
		stop.setRolloverEnabled(true);
		stop.setRolloverIcon(createIcon("/icons/stop-25g.png"));
		stop.setFocusPainted(false);
		breakPoint.setRolloverEnabled(true);
		breakPoint.setRolloverIcon(createIcon("/icons/plus-26g.png"));
		breakPoint.setFocusPainted(false);
		refresh.setRolloverEnabled(true);
		refresh.setRolloverIcon(createIcon("/icons/refresh-26g.png"));
		refresh.setFocusPainted(false);
		highlight.setRolloverEnabled(true);
		highlight.setRolloverIcon(createIcon("/icons/marker-26g.png"));
		highlight.setFocusPainted(false);
		stepIn.setRolloverEnabled(true);
		stepIn.setRolloverIcon(createIcon("/icons/import-26g.png"));
		stepIn.setFocusPainted(false);
		stepOut.setRolloverEnabled(true);
		stepOut.setRolloverIcon(createIcon("/icons/export-26g.png"));
		stepOut.setFocusPainted(false);
		stepOver.setRolloverEnabled(true);
		stepOver.setRolloverIcon(createIcon("/icons/down_left-24g.png"));
		stepOver.setFocusPainted(false);
		
		Dimension dim1= getPreferredSize();
		dim1.width = 20;
		dim1.height = 20;
		
		toolBar.add(play);
		toolBar.addSeparator(dim1);
		toolBar.add(stop);
		toolBar.addSeparator(dim1);
		toolBar.add(refresh);
		toolBar.addSeparator(dim1);
		toolBar.add(stepIn);
		toolBar.addSeparator(dim1);
		toolBar.add(stepOut);
		toolBar.addSeparator(dim1);
		toolBar.add(stepOver);
		toolBar.addSeparator(dim1);
		toolBar.add(breakPoint);
		toolBar.addSeparator(dim1);
		toolBar.add(highlight);
		toolBar.addSeparator(dim1);
		toolBar.add(commandline);
		toolBar.add(commandField);
		add(toolBar);
	}
	
	private ImageIcon createIcon(String path){
		URL url = getClass().getResource(path);
		
		if(url == null)
			System.err.println("unable to load image: " + path);
		
		ImageIcon icon = new ImageIcon(url);
		
		return icon;
	}

	public JButton getPlay() {
		return play;
	}

	public JButton getStop() {
		return stop;
	}

	public JButton getRefresh() {
		return refresh;
	}

	public JButton getStepIn() {
		return stepIn;
	}

	public JButton getStepOut() {
		return stepOut;
	}

	public JButton getStepOver() {
		return stepOver;
	}

	public JButton getHighlight() {
		return highlight;
	}

	public JButton getBreakPoint() {
		return breakPoint;
	}

	public JTextField getCommandField() {
		return commandField;
	}

	public void setPlay(JButton play) {
		this.play = play;
	}

	public void setStop(JButton stop) {
		this.stop = stop;
	}

	public void setRefresh(JButton refresh) {
		this.refresh = refresh;
	}

	public void setStepIn(JButton stepIn) {
		this.stepIn = stepIn;
	}

	public void setStepOut(JButton stepOut) {
		this.stepOut = stepOut;
	}

	public void setStepOver(JButton stepOver) {
		this.stepOver = stepOver;
	}

	public void setHighlight(JButton highlight) {
		this.highlight = highlight;
	}

	public void setBreakPoint(JButton breakPoint) {
		this.breakPoint = breakPoint;
	}

	public void setCommandField(JTextField commandField) {
		this.commandField = commandField;
	}
	
	
	
}
