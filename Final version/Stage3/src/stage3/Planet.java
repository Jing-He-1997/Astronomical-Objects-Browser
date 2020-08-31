package stage3;

/**
 * 
 * @author hejing
 * The Planet class is the subclass of AstronomicalObjects
 */

public class Planet extends AstronomicalObjects {
	private Double albedo;

	public void setAlbedo(Double albedo) {
		this.albedo = albedo;
	}

	public Double getAlbedo() {
		return albedo;
	}

}
