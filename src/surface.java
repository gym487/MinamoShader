
public abstract class surface {
	 double r,g,b;
	 vec p,u,v,n;//point u v normal;
	public surface(vec p,vec u,vec v,int m,int t,double r,double g,double b){
		this.p=p;
		this.u=u;
		this.v=v;
		this.n=vec.cro(u, v);
		this.r=r;
		this.g=g;
		this.b=b;
	}
}

class diffSurface extends surface{
}

class mirrSurface extends surface{
	
	public mirrSurface(vec p,vec u,vec v,int m,int t,double r,double g,double b){
		this.p=p;
		this.u=u;
		this.v=v;
		this.n=vec.cro(u, v);
		this.r=r;
		this.g=g;
		this.b=b;
	}
}
