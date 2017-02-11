
public  class vec {
	private double x,y,z;
	public  vec(double x,double y,double z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	public static vec add(vec a,vec b){
		return new vec(a.x+b.x,a.y+b.y,a.z+b.z);
	}
	public  vec opp(){
		return new vec(-this.x,-this.y,-this.z);
	}
	public static vec sub(vec a,vec b){
		return vec.add(a,b.opp());
	}
	public static double dot(vec a,vec b){
		return a.x*b.x+a.y*b.y+a.z*b.z;
	}
	public double mod(){
		return Math.sqrt(Math.pow(this.x,2)+Math.pow(this.y,2)+Math.pow(this.z,2));
	}
	public vec dir(){
		return new vec(this.x/this.mod(),this.y/this.mod(),this.z/this.mod());
	}
	public static vec cro(vec a,vec b){
		return new vec(a.y*b.z-a.z*b.y,a.z*b.x-a.x*b.y,a.x*b.y-a.y*b.x);
	}
	public vec mul(double a){
		return new vec(this.x*a,this.y*a,this.z*a);
	}
	public vec unit(){
		return new vec(this.x/this.mod(),this.y/this.mod(),this.z/this.mod());
	}
	public static vec randvec(){
		return new vec(Math.random(),Math.random(),Math.random());
	}
	public static vec normalRand(vec n){
		vec r=new vec(0,0,0);
		while(true){
			r=vec.randvec();
			if(r.mod()<=1&&r.mod()>=0.01&&vec.dot(r, n)>0)
				break;
		}
		return r;
	}
}
