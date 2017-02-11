
public abstract class surface {
	 int shape;//0=tri 1=squ
	 vec p,u,v,n;//point u v normal;
	 public node gen(ray r,int sam){
		 return null;
	 }
}

class diffSurface extends surface{
	spec sd;
	public diffSurface(vec p,vec u,vec v,spec diff){
		this.p=p;
		this.u=u;
		this.v=v;
		this.n=vec.cro(u, v);
		this.sd=diff;
	}
}

class refSurface extends surface{
	spec sf,st;
	double ref;
	public refSurface(vec p,vec u,vec v,spec f,spec t){
		this.p=p;
		this.u=u;
		this.v=v;
		this.n=vec.cro(u, v);
		this.sf=f;
		this.st=t;
	}

}
class mirrSurface extends surface{
	spec sf;
	public mirrSurface(vec p,vec u,vec v,spec f){
		this.p=p;
		this.u=u;
		this.v=v;
		this.n=vec.cro(u, v);
		this.sf=f;
	}
	
}

class point{
	surface surf;
	vec pos;
	double u,v,l;
	
}