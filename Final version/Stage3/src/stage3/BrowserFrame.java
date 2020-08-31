package stage3;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;

/**
 * 
 * @author hejing
 * The BrowserFrame class is to design the main GUI interface
 */
public class BrowserFrame extends JFrame {

	JLabel starJLabel = new JLabel("Input stars.txt");
	JLabel messierJLabel = new JLabel("Input messier.txt");
	JLabel planetJLabel = new JLabel("Input planets.txt");
	JTextField starsField = new JTextField(20);
	JTextField messiersField = new JTextField(20);
	JTextField planetsField = new JTextField(20);
	JButton starsButton = new JButton("View stars data");
	JButton messiersButton = new JButton("View messier data");
	JButton planetsButton = new JButton("View planets data");

	// Create JFrame method
	public BrowserFrame(String title) {

		super(title);

		JPanel root = new JPanel();
		this.setContentPane(root);
		root.setLayout(new FlowLayout());

		root.add(starJLabel);
		root.add(starsField);
		root.add(starsButton);
		
		root.add(messierJLabel);
		root.add(messiersField);
		root.add(messiersButton);
		
		root.add(planetJLabel);
		root.add(planetsField);
		root.add(planetsButton);

		// Lambda expression
		// Create stars table and insert data into table
		starsButton.addActionListener((e) -> {
			StarsTableFrame starsTable = new StarsTableFrame("stars");
			starsTable.onAdd(starsField.getText().trim());
			starsTable.setVisible(true);
			starsTable.setSize(1000, 300);
			
		});
		// Create messiers table and insert data into table
		messiersButton.addActionListener((e)->{
			MessierTableFrame messiersTableFrame=new MessierTableFrame("messiers");
			messiersTableFrame.onAdd(messiersField.getText().trim());
			messiersTableFrame.setVisible(true);
			messiersTableFrame.setSize(1000, 300);
		});
		// Create planets table and insert data into table
		planetsButton.addActionListener((e)->{
			PlanetTableFrame planetTableFrame=new PlanetTableFrame("planets");
			planetTableFrame.onAdd(planetsField.getText().trim());
			planetTableFrame.setVisible(true);
			planetTableFrame.setSize(1000, 300);
		});
	}

}
