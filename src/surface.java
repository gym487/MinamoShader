
public abstract class surface {
	private double r,g,b;
	 private static int glass=1;
	 private static int mirror=2;
	 private static int diffuse=3;
	 private static int tri =1;
	 private static int squ=2;
	 private int m;
	 private int t;
	private vec p,u,v,n;//point u v normal;
	public surface(vec p,vec u,vec v,int m,int t,double r,double g,double b){
		this.p=p;
		this.u=u;
		this.v=v;
		this.n=vec.cro(u, v);
		this.m=m;
		this.t=t;
		this.r=r;
		this.g=g;
		this.b=b;
	}
}
