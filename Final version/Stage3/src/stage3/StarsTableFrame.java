package stage3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author hejing
 * The StarTableFrame class is to design the planet table GUI interface
 */

public class StarsTableFrame extends JFrame {

	List<Star> dataStars = new ArrayList<Star>();

	// Model: Responsible for data
	DefaultTableModel tableModel = new DefaultTableModel();

	// View: Responsible for display
	JPanel root = new JPanel();
	JTable table = new JTable(tableModel);
	JTextField searchField1 = new JTextField();
	JTextField searchField2 = new JTextField();
	JButton backButton = new JButton("Back");
	JButton searchButton = new JButton("Search");
	JButton returnButton = new JButton("Complete form");

	private boolean retValue = false;

	public StarsTableFrame(String title) {

		super(title);

		// Content Pane
		this.setContentPane(root);
		root.setLayout(new BorderLayout());

		// Add into main Pane
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		root.add(scrollPane, BorderLayout.CENTER);

		// Initialise settings
		tableModel.addColumn("number");
		tableModel.addColumn("ra");
		tableModel.addColumn("decl");
		tableModel.addColumn("magn");
		tableModel.addColumn("distance");
		tableModel.addColumn("type");
		tableModel.addColumn("constellation");

		JToolBar jToolBar = new JToolBar();
		jToolBar.setSize(600, 100);
		root.add(jToolBar, BorderLayout.PAGE_START);
		jToolBar.setFloatable(false);

		// Return to the main GUI interface
		jToolBar.add(backButton);
		backButton.addActionListener((e) -> {

			retValue = true; 
			setVisible(false);
			Delete();

		});

		jToolBar.addSeparator(new Dimension(40, 10));
		jToolBar.add(new JLabel("Table name:"));
		jToolBar.add(searchField1);
		jToolBar.add(new JLabel("Conditions:"));
		jToolBar.add(searchField2);
		jToolBar.add(new JLabel("（sql Format: where ... and ...）"));

		jToolBar.add(searchButton);
		// Search function
		searchButton.addActionListener((e) -> {

			onSearch();

		});

		jToolBar.add(returnButton);
		// Return to the table with all data
		returnButton.addActionListener((e) -> {

			onReturn();

		});
		
		searchField1.setMaximumSize(new Dimension(120, 30));
		searchField2.setMaximumSize(new Dimension(150, 30));

	}

	public boolean exec() {
		// Show window
		this.setVisible(true);
		return retValue;
	}

	private void addTableRow(Star item) {
		Vector<Object> rowData = new Vector<Object>();
		rowData.add(item.getName());
		rowData.add(item.getRa());
		rowData.add(item.getDecl());
		rowData.add(item.getMagn());
		rowData.add(item.getDistance());
		rowData.add(item.getType());
		rowData.add(item.getConstellation());
		tableModel.addRow(rowData);
	}

	// Search method
	private void onSearch() {
		// Search through sql statement
		String sql = "select*from " + searchField1.getText().trim() + " " + searchField2.getText();

		// Insert searching results into table
		// empty
		tableModel.setRowCount(0);

			if (searchField1.getText().trim().equals("stars")) {
				try {
					PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(sql);
					ResultSet rs = preparedStatement.executeQuery();
					while (rs.next()) {
						
						Star star=new Star();
						star.setName(rs.getNString("number"));
						star.setRa(rs.getDouble("ra"));
						star.setDecl(rs.getDouble("decl"));
						star.setMagn(rs.getDouble("magn"));
						star.setDistance(rs.getDouble("distance"));
						star.setType(rs.getNString("type"));
						star.setConstellation(rs.getNString("constellation"));
						
						addTableRow(star);
						
						
						
						
					}
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	

	private void onReturn() {

		    // restore data
			// empty
			tableModel.setRowCount(0);
			for (Star s : dataStars) {
				addTableRow(s);
			}
			return;
		}
	
	
	protected void onAdd(String file1) {
		
		
		try {

			FileReader fr = new FileReader(file1);
			BufferedReader bf = new BufferedReader(fr);
			String str;
			String[] strs;
			while ((str = bf.readLine()) != null) {
				// intercept the word
				Star star = new Star();
				// delete the extra space
				strs = str.split("\\|");
				star.setName(strs[0].trim());
				star.setRa(Double.parseDouble(strs[1].trim()));
				star.setDecl(Double.parseDouble(strs[2].trim()));
				star.setMagn(Double.parseDouble(strs[3].trim()));
				star.setDistance(Double.parseDouble(strs[4].trim()));
				star.setType(strs[5].trim());
				star.setConstellation(strs[6].trim());
				addTableRow(star);
				dataStars.add(star);
			}

			StarDao starDao = new StarDaoImpl();
			starDao.insertStar(dataStars);

			bf.close();
			fr.close();
		} // Exception situation
		catch (FileNotFoundException fnfe) {
			System.out.println(fnfe);
			System.out.println("Run Correct file");
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException nfe) {
			System.out.println(nfe);
			System.exit(1);
		}
		
	}
	
	private void Delete() {
		
		String sql="delete from stars";
		try {
			Statement statement=DbConnection.getConnection().createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
