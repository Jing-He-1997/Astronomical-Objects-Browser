package stage3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class StarDaoImpl implements StarDao {
	
	// Insert star object into database table
	@Override
	public void insertStar(List<Star> stars) {
		// TODO Auto-generated method stub

		try {

			String sql = "insert into stars(number,ra,decl,magn,distance,type,constellation) values(?,?,?,?,?,?,?)";
			Connection connection = DbConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; i < stars.size(); i++) {
				preparedStatement.setString(1, stars.get(i).getName());
				preparedStatement.setDouble(2, stars.get(i).getRa());
				preparedStatement.setDouble(3, stars.get(i).getDecl());
				preparedStatement.setDouble(4, stars.get(i).getMagn());
				preparedStatement.setDouble(5, stars.get(i).getDistance());
				preparedStatement.setString(6, stars.get(i).getType());
				preparedStatement.setString(7, stars.get(i).getConstellation());
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
