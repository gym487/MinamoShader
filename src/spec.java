
public class spec {
	double r,g,b;
	public spec (double r,double g,double b){
		this.r=r;
		this.g=g;
		this.b=b;
	}
	public spec mul(spec w){
		return new spec(w.r*this.r,w.g*this.g,w.b*this.b);
	}
	public spec mul(int  w){
		return new spec(w*this.r,w*this.g,w*this.b);
	}
	public spec add(spec a){
		return new spec(this.r+a.r,this.g+a.g,this.b+a.b);
	}
	public double tol(){
		return this.r+this.g+this.b;
	}
}
