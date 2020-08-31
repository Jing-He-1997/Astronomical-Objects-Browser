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
 *  The MessierTableFrame class is to design the messier table GUI interface
 */
public class MessierTableFrame extends JFrame {

	List<Messier> dataMessiers = new ArrayList<Messier>();

	// Model: Responsible for data
	DefaultTableModel tableModel = new DefaultTableModel();

	// View: Responsible for display

	JPanel root = new JPanel();
	JTable table = new JTable(tableModel);
	JTextField searchField1 = new JTextField();
	JTextField searchField2 = new JTextField();
	JButton backButton = new JButton("Back");
	JButton searchButton = new JButton("Search");
	JButton returnButton = new JButton("Complete table");

	private boolean retValue = false;

	public MessierTableFrame(String title) {

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
		tableModel.addColumn("constellation");
		tableModel.addColumn("description");

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

		// Search function
		jToolBar.add(searchButton);
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

	private void addTableRow(Messier item) {
		Vector<Object> rowData = new Vector<Object>();
		rowData.add(item.getName());
		rowData.add(item.getRa());
		rowData.add(item.getDecl());
		rowData.add(item.getMagn());
		rowData.add(item.getDistance());
		rowData.add(item.getConstellation());
		rowData.add(item.getDescription());
		tableModel.addRow(rowData);
	}

	// Search method
	private void onSearch() {

		// Search through sql statement
		String sql = "select*from " + searchField1.getText().trim() + " " + searchField2.getText();

		// Insert searching results into table
		// empty
		tableModel.setRowCount(0);

		if (searchField1.getText().trim().equals("messiers")) {
			try {
				PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(sql);
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {

					Messier messier = new Messier();
					messier.setName(rs.getNString("number"));
					messier.setRa(rs.getDouble("ra"));
					messier.setDecl(rs.getDouble("decl"));
					messier.setMagn(rs.getDouble("magn"));
					messier.setDistance(rs.getDouble("distance"));
					messier.setConstellation(rs.getNString("constellation"));
					messier.setDescription(rs.getNString("description"));

					addTableRow(messier);

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
		for (Messier m : dataMessiers) {
			addTableRow(m);
		}
		return;
	}

	protected void onAdd(String file2) {

		try {

			FileReader fr = new FileReader(file2);
			BufferedReader bf = new BufferedReader(fr);
			String str;
			String[] strs;
			while ((str = bf.readLine()) != null) {
				// intercept the word
				Messier messier = new Messier();
				// delete the extra space
				strs = str.split("\\|");
				messier.setName("M" + strs[0].trim());
				messier.setRa(Double.parseDouble(strs[1].trim()));
				messier.setDecl(Double.parseDouble(strs[2].trim()));
				messier.setMagn(Double.parseDouble(strs[3].trim()));
				messier.setDistance(Double.parseDouble(strs[4].trim()));
				messier.setConstellation(strs[5].trim());
				messier.setDescription(strs[6].trim());
				addTableRow(messier);
				dataMessiers.add(messier);
			}

			MessierDao messierDao = new MessierDaoImpl();
			messierDao.insertMessier(dataMessiers);

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

		String sql = "delete from messiers";
		try {
			Statement statement = DbConnection.getConnection().createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
