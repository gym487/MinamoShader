
public class spec {
	float r,g,b;
	public spec (float r,float g,float b){
		this.r=r;
		this.g=g;
		this.b=b;
	}
	public spec mul(spec w){
		return new spec(w.r*this.r,w.g*this.g,w.b*this.b);
	}
	public spec mul(float  w){
		return new spec(w*this.r,w*this.g,w*this.b);
	}
	public spec add(spec a){
		return new spec(this.r+a.r,this.g+a.g,this.b+a.b);
	}
	public float tol(){
		return this.r+this.g+this.b;
	}
	public void limit(float l){
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
		float rr=(float)Math.pow(this.r*25f,1.0f/2.2f);
		float gg=(float)Math.pow(this.g*25f,1.0f/2.2f);
		float bb=(float)Math.pow(this.b*25f,1.0f/2.2f);
		return new spec(rr,gg,bb);
	}
}
