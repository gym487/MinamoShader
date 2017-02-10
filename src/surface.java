
public abstract class surface {
	private vec p,u,v,n;//point u v normal;
	public surface(vec p,vec u,vec v){
		this.p=p;
		this.u=u;
		this.v=v;
		this.n=vec.cro(u, v);
	}
}
