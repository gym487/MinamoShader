
public abstract class surface {
	 
	 vec p,u,v,n;//point u v normal;
}

class diffSurface extends surface{
	double r,g,b;
	public diffSurface(vec p,vec u,vec v,int m,int t,double r,double g,double b){
		this.p=p;
		this.u=u;
		this.v=v;
		this.n=vec.cro(u, v);
		this.r=r;
		this.g=g;
		this.b=b;
	}
}

class mirrSurface extends surface{
	double rf,gf,bf;
	double rt,gt,bt;
	public mirrSurface(vec p,vec u,vec v,int m,int t,double rf,double gf,double bf,double rt,double gt,double bt){
		this.p=p;
		this.u=u;
		this.v=v;
		this.n=vec.cro(u, v);
		this.rf=rf;
		this.gf=gf;
		this.bf=bf;
		this.rt=rt;
		this.gt=gt;
		this.bt=bt;
	}
	
	
}
