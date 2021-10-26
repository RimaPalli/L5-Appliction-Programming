import java.awt.Window.Type;

import javax.swing.JFrame;

/**
 * The main driver program for the GUI based conversion program.
 * 
 * @author mdixon
 */
public class Converter {
	 
    public static void main(String[] args) {
    	
        JFrame frame = new JFrame("Converter");
        frame.setSize(1000, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
 
        MainPanel panel = new MainPanel();
        
        frame.setJMenuBar(panel.setupMenu());
    
        frame.getContentPane().add(panel);
        
        frame.pack();
        frame.setVisible(true);
    }
}

