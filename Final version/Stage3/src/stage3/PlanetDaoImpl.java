package stage3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PlanetDaoImpl implements PlanetDao{
	
	
	// Insert planet object into database table
	@Override
	public void insertPlanet(List<Planet> planets) {
		try {

			String sql = "insert into planets(name,ra,decl,magn,distance,albedo) values(?,?,?,?,?,?)";
			Connection connection = DbConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; i < planets.size(); i++) {
				preparedStatement.setString(1, planets.get(i).getName());
				preparedStatement.setDouble(2, planets.get(i).getRa());
				preparedStatement.setDouble(3, planets.get(i).getDecl());
				preparedStatement.setDouble(4, planets.get(i).getMagn());
				preparedStatement.setDouble(5, planets.get(i).getDistance());
				preparedStatement.setDouble(6, planets.get(i).getAlbedo());
				
				preparedStatement.executeUpdate();
			}
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
		
	}


