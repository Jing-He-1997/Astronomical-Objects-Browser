package stage3;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author hejing
 *  The PlanetTableFrame class is to design the planet table GUI interface
 */

public class PlanetTableFrame extends JFrame {

	List<Planet> dataPlanets = new ArrayList<Planet>();

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

	public PlanetTableFrame(String title) {

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
		tableModel.addColumn("name");
		tableModel.addColumn("ra");
		tableModel.addColumn("decl");
		tableModel.addColumn("magn");
		tableModel.addColumn("distance");
		tableModel.addColumn("albedo");

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

	private void addTableRow(Planet item) {
		Vector<Object> rowData = new Vector<Object>();
		rowData.add(item.getName());
		rowData.add(item.getRa());
		rowData.add(item.getDecl());
		rowData.add(item.getMagn());
		rowData.add(item.getDistance());
		rowData.add(item.getAlbedo());

		tableModel.addRow(rowData);
	}

	// Search method
	private void onSearch() {
		// Search through sql statement
		String sql = "select*from " + searchField1.getText().trim() + " " + searchField2.getText();

		// Insert searching results into table
		// empty
		tableModel.setRowCount(0);

		if (searchField1.getText().trim().equals("planets")) {
			try {
				PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(sql);
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {

					Planet planet = new Planet();
					planet.setName(rs.getNString("name"));
					planet.setRa(rs.getDouble("ra"));
					planet.setDecl(rs.getDouble("decl"));
					planet.setMagn(rs.getDouble("magn"));
					planet.setDistance(rs.getDouble("distance"));
					planet.setAlbedo(rs.getDouble("albedo"));

					addTableRow(planet);

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
		for (Planet p : dataPlanets) {
			addTableRow(p);
		}
		return;
	}

	protected void onAdd(String file3) {

		try {

			FileReader fr = new FileReader(file3);
			BufferedReader bf = new BufferedReader(fr);
			String str;
			String[] strs;
			while ((str = bf.readLine()) != null) {
				// intercept the word
				Planet planet=new Planet();
				// delete the extra space
				strs = str.split("\\s+");
				planet.setName(strs[0].trim());
				planet.setRa(Double.parseDouble(strs[1].trim()));
				planet.setDecl(Double.parseDouble(strs[2].trim()));
				planet.setMagn(Double.parseDouble(strs[3].trim()));
				planet.setDistance(Double.parseDouble(strs[4].trim()));
				planet.setAlbedo(Double.parseDouble(strs[5].trim()));
				
				addTableRow(planet);
				dataPlanets.add(planet);
			}

			PlanetDao planetDao=new PlanetDaoImpl();
			planetDao.insertPlanet(dataPlanets);

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

		String sql = "delete from planets";
		try {
			Statement statement = DbConnection.getConnection().createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
