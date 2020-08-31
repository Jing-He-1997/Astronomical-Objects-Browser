package stage3;

import java.util.List;

/**
 * 
 * @author hejing
 * PlanetDao use to insert messier object into database table
 */

public interface PlanetDao {

	public void insertPlanet(List<Planet> planets);
}
