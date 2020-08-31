package stage3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;



public class MessierDaoImpl implements MessierDao{
	
	public Connection connection = null;
	public PreparedStatement preparedStatement = null;

	// Insert messier object into database table
	@Override
	public void insertMessier(List<Messier> messiers) {
		// TODO Auto-generated method stub
		try {
			String sql = "insert into messiers(number,ra,decl,magn,distance,constellation,description) values(?,?,?,?,?,?,?)";
			connection = DbConnection.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; i < messiers.size(); i++) {
				preparedStatement.setString(1, messiers.get(i).getName());
				preparedStatement.setDouble(2, messiers.get(i).getRa());
				preparedStatement.setDouble(3, messiers.get(i).getDecl());
				preparedStatement.setDouble(4, messiers.get(i).getMagn());
				preparedStatement.setDouble(5, messiers.get(i).getDistance());
				preparedStatement.setString(6, messiers.get(i).getConstellation());
				preparedStatement.setString(7, messiers.get(i).getDescription());
				preparedStatement.executeUpdate();
			}
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
