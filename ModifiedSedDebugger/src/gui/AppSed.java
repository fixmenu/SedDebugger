package gui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AppSed {
	
	public static void main(String[] args){
		
	SwingUtilities.invokeLater(new Runnable(){
		public void run(){
			try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }
			new MainFrame();
			
		}
		});
	}
}
