package stage3;


public class AstronomicalObjects {
	
	protected String name;
	protected Double ra;
	protected Double decl;
	protected Double magn;
	protected Double distance;

	
	public void setName(String name){
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setRa(Double ra) {
		this.ra=ra;
	}
	
	public Double getRa() {
		return ra;
	}
	
	public void setDecl(Double decl) {
		this.decl=decl;
	}
	
	public Double getDecl() {
		return decl;
	}
	
	public void setMagn(Double magn) {
		this.magn=magn;
	}
	
	public Double getMagn() {
		return magn;
	}
	
	public void setDistance(Double distance) {
		this.distance=distance;
	}
	
	public Double getDistance() {
		return distance;
	}
	

}
