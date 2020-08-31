package stage3;

/**
 * 
 * @author hejing
 * The Messier class is the subclass of AstronomicalObjects
 */

public class Messier extends AstronomicalObjects {

	private String constellation;
	private String decription;

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setDescription(String description) {
		this.decription = description;
	}

	public String getDescription() {
		return decription;
	}

}
