package stage3;

import java.util.List;

/**
 * 
 * @author hejing
 * MessierDao use to insert messier object into database table
 */

public interface MessierDao {
	
	public void insertMessier(List<Messier> messiers);

}
