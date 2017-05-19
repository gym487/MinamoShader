
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
	public spec mul(double  w){
		return new spec(w*this.r,w*this.g,w*this.b);
	}
	public spec add(spec a){
		return new spec(this.r+a.r,this.g+a.g,this.b+a.b);
	}
	public double tol(){
		return this.r+this.g+this.b;
	}
	public void limit(double l){
		if(this.r>l)
			this.r=l;
		if(this.r<0)
			this.r=0;
		if(this.b>l)
			this.b=l;
		if(this.b<0)
			this.b=0;
		if(this.g>l)
			this.g=l;
		if(this.g<0)
			this.g=0;
	}
	public spec sRGB(){
		double rr=Math.pow(this.r*25,1.0/2.2);
		double gg=Math.pow(this.g*25,1.0/2.2);
		double bb=Math.pow(this.b*25,1.0/2.2);
		return new spec(rr,gg,bb);
	}
}
