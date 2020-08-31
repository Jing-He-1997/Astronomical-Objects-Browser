package stage3;

import java.util.List;

/**
 * 
 * @author hejing
 * StarDao use to insert messier object into database table
 */

public interface StarDao {

	public void insertStar(List<Star> stars);
}
