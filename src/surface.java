
public abstract class surface {
	 
	 vec p,u,v,n;//point u v normal;
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

class mirrSurface extends surface{
	spec sf,st;
	public mirrSurface(vec p,vec u,vec v,spec f,spec t){
		this.p=p;
		this.u=u;
		this.v=v;
		this.n=vec.cro(u, v);
		this.sf=f;
		this.st=t;
	}
	
	
}
