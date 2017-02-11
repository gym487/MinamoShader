
public abstract class surface {
	 int shape;//0=tri 1=squ
	 vec p,u,v,n;//point u v normal;
	 public node gen(ray r,int sam){
		 return null;
	 }
	 public point check(ray r){
		 
		 vec t=this.p.opp();
		 double h=1/vec.dot(vec.cro(r.d,this.v),this.u);
		 double rt=(vec.dot(vec.cro(t,u),v))*h;
		 if(rt>=0&&vec.dot(r.d, this.n)>0){
			 double ru=(vec.dot(vec.cro(r.d,this.v),t))*h;
			 double rv=(vec.dot(vec.cro(t,this.u),r.d))*h;
			 if(ru>=0&&rv>=0&&((this.shape==0&&ru+rv<=1)||(this.shape==1&&ru<=1&&rv<=1))){
				 vec pos=vec.add(r.p,r.d.mul(rt));
				 return new point(pos,ru,rv,rt,this,r);
			 }
		 }
		 return null;
	 }
}

class diffSurface extends surface{
	spec sd;
	public diffSurface(vec p,vec u,vec v,spec diff,int sh){
		this.p=p;
		this.u=u;
		this.v=v;
		this.n=vec.cro(u, v);
		this.sd=diff;
		this.shape=sh;
	}
	public diffSurface newdiff(vec o,vec p1,vec p2,spec diff,int sh){
		return new diffSurface(o,vec.sub(p1,o),vec.sub(p2,o),diff,sh);
	}
	public nnode gen(point pt,int n){
		nnode rn=new nnode(n);
		for(int i=0;i<n;i++){
			rn.setRay(new ray(pt.pos,vec.normalRand(this.n)),i);
			rn.setW(this.sd.mul(1/n),i);
		}
		return rn;
	}
	
}

class refSurface extends surface{
	spec sf,st;
	double ref;
	public refSurface(vec p,vec u,vec v,spec f,spec t,int sh){
		this.p=p;
		this.u=u;
		this.v=v;
		this.n=vec.cro(u, v);
		this.sf=f;
		this.st=t;
		this.shape=sh;
	}
	public refSurface newref(vec o,vec p1,vec p2,spec f,spec t,int sh){
		
		return new refSurface(o,vec.sub(p1,o),vec.sub(p2,o),f,t,sh);
	}
}

class mirrSurface extends surface{
	spec sf;
	public mirrSurface(vec p,vec u,vec v,spec f,int sh){
		this.p=p;
		this.u=u;
		this.v=v;
		this.n=vec.cro(u, v);
		this.sf=f;
		this.shape=sh;
	}
	public mirrSurface newmirr(vec o,vec p1,vec p2,spec sf,int sh){
		return new mirrSurface(o,vec.sub(p1,o),vec.sub(p2,o),sf,sh);
	}
}

class point{
	surface surf;
	vec pos;
	ray r;
	double u,v,t;
	 point(vec pos,double u,double v,double t,surface surf,ray r){
		 this.pos=pos;
		 this.surf=surf;
		 this.v=v;
		 this.u=u;
		 this.t=t;
		 this.r=r;
	 }
}