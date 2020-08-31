package stage3;

import javax.swing.JFrame;
/**
 * 
 * @author hejing
 * Create the main GUI interface
 */

public class CreateGUI {
	
	private static void createGUI() {
		
		JFrame jFrame=new BrowserFrame("Browser");
		jFrame.setResizable(false);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		jFrame.setSize(600,400);
		
		jFrame.setVisible(true);
	}
	
	public static void main(String[] args) {
		
		createGUI();
	}

}
