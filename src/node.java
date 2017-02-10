
public class node {
	private int rn;//number of rays;
	private ray[] rays;
	private double[] weights;
	public node(int rn){
		this.rn=rn;
		this.rays=new ray[rn];
		
	}
	public void setRay(ray r,int i){
		this.rays[i]=r;
	}
	public ray getRay(int i){
		return this.rays[i];
	}
}
