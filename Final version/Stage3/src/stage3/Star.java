package stage3;

/**
 * 
 * @author hejing
 * The Star class is the subclass of AstronomicalObjects
 */

public class Star extends AstronomicalObjects {

	private String type;
	private String constellation;

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public String getConstellation() {
		return constellation;
	}
	
}
