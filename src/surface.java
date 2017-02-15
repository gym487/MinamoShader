
public abstract class surface {
	 public node gen(point pt){
		 return null;
	 }
	 
	 public point check(ray r){
		 return null;
	 }
}

abstract class plain extends surface{
	 int shape;//0=tri 1=squ
	 vec p,u,v,n;//point u v normal;
	 public node gen(point pt){
		 return null;
	 }
	 
	 public point check(ray r){
		 
		 vec t=vec.sub(r.p,this.p);
		 double h=1/vec.dot(vec.cro(r.d,this.v),this.u);
		 double rt=(vec.dot(vec.cro(t,this.u),this.v))*h;
		 if(rt>0){//&&vec.dot(r.d, this.n)<0  direction check // if rt=0 the ray will intersect the surface witch it from... that might cause a endless loop..
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

abstract class sphere extends surface{
	vec c;//center
	double r;//radius
	public point check(ray r){
		vec v=vec.sub(r.p,this.c);
		if(Math.pow(vec.dot(r.d.mul(2),v),2)-4*(Math.pow(v.mod(),2)-Math.pow(this.r, 2))<0)
			return null;
		else{
			double t=-vec.dot(r.d, v)-Math.sqrt(Math.pow(vec.dot(r.d,v),2)-(Math.pow(v.mod(), 2)-Math.pow(this.r,2)));
			vec pos=vec.add(r.p,r.d.mul(t));
			if(t>0){
				return new point(pos,0,0,t,this,r);
			}else{
				return null;
			}
		}
	}
	public vec getn(vec pos){
		return vec.sub(pos,this.c);
	}
}
class refsphere extends sphere{
	double ref;
	spec sf,st;
	public refsphere(vec c,spec f,spec t,double ref){
		this.c=c;
		this.sf=f;
		this.st=t;
		this.ref=ref;
	}
	public node gen(point pt){
		double cos1=-vec.dot(this.getn(pt.pos),pt.r.d);
		if(1-(1-Math.pow(this.ref,2))*(1-Math.pow(cos1, 2))>=0){
				ray rt=new ray(pt.pos,vec.sub(pt.r.d,this.getn(pt.pos).mul(2*vec.dot(this.getn(pt.pos),pt.r.d))));
				nnode rn=new nnode(2);
				rn.setRay(rt,0);
				rn.setW(this.sf,0);
				double cos2=Math.sqrt(1-(1/Math.pow(ref,2))*(1-Math.pow(cos1,2)) );
				ray tt=new ray(pt.pos,vec.add(pt.r.d.mul(1/ref),this.getn(pt.pos).mul(cos1/ref-cos2)));
				rn.setRay(tt,1);
				rn.setW(this.st,1);
				return rn;
		}else{
				ray rt=new ray(pt.pos,vec.sub(pt.r.d,this.getn(pt.pos).mul(2*vec.dot(this.getn(pt.pos),pt.r.d))));
				nnode rn=new nnode(1);
				rn.setRay(rt,0);
				rn.setW(this.sf.add(this.st),0);
				return rn;
		}
}
}

class mirrsphere extends sphere{
	spec sf;
	public mirrsphere(vec c,double r,spec f){
		this.c=c;
		this.sf=f;
		this.r=r;
	}
	public node gen(point pt){
		ray rt=new ray(pt.pos,vec.sub(pt.r.d,this.getn(pt.pos).mul(2*vec.dot(this.getn(pt.pos),pt.r.d))));
		nnode rn=new nnode(1);
		rn.setRay(rt,0);
		rn.setW(this.sf,0);
		return rn;
	}
}


class diffSurface extends plain{
	spec sd;
	public diffSurface(vec p,vec u,vec v,spec diff,int sh){
		this.p=p;
		this.u=u;
		this.v=v;
		this.n=vec.cro(u, v).unit();
		this.sd=diff;
		this.shape=sh;
	}
	public static diffSurface newdiff(vec o,vec p1,vec p2,spec diff,int sh){
		return new diffSurface(o,vec.sub(p1,o),vec.sub(p2,o),diff,sh);
	}
	public node gen(point pt){
		nnode rn=new nnode(minamo.sam);
		for(int i=0;i<minamo.sam;i++){
			rn.setRay(new ray(pt.pos,vec.normalRand(this.n)),i);
			rn.setW(this.sd.mul(1.0/minamo.sam),i);
		}
		return rn;
	}
}



class refSurface extends plain{
	spec sf,st;
	double ref;
	public refSurface(vec p,vec u,vec v,spec f,spec t,int sh,double ref){
		this.p=p;
		this.u=u;
		this.v=v;
		this.n=vec.cro(u, v).unit();
		this.sf=f;
		this.st=t;
		this.shape=sh;
		this.ref=ref;
	}
	public static refSurface newref(vec o,vec p1,vec p2,spec f,spec t,int sh,double ref){
		
		return new refSurface(o,vec.sub(p1,o),vec.sub(p2,o),f,t,sh,ref);
	}
	public node gen(point pt){
		double cos1=-vec.dot(this.n,pt.r.d);
		if(1-(1-Math.pow(this.ref,2))*(1-Math.pow(cos1, 2))>=0){
				ray rt=new ray(pt.pos,vec.sub(pt.r.d,this.n.mul(2*vec.dot(this.n,pt.r.d))));
				nnode rn=new nnode(2);
				rn.setRay(rt,0);
				rn.setW(this.sf,0);
				double cos2=Math.sqrt(1-(1/Math.pow(ref,2))*(1-Math.pow(cos1,2)) );
				ray tt=new ray(pt.pos,vec.add(pt.r.d.mul(1/ref),this.n.mul(cos1/ref-cos2)));
				rn.setRay(tt,1);
				rn.setW(this.st,1);
				return rn;
			
		}else{
				ray rt=new ray(pt.pos,vec.sub(pt.r.d,this.n.mul(2*vec.dot(this.n,pt.r.d))));
				nnode rn=new nnode(1);
				rn.setRay(rt,0);
				rn.setW(this.sf.add(this.st),0);
				return rn;
		}
	}
}

class mirrSurface extends plain{
	spec sf;
	public mirrSurface(vec p,vec u,vec v,spec f,int sh){
		this.p=p;
		this.u=u;
		this.v=v;
		this.n=vec.cro(u, v).unit();
		this.sf=f;
		this.shape=sh;
	}
	public static mirrSurface newmirr(vec o,vec p1,vec p2,spec sf,int sh){
		return new mirrSurface(o,vec.sub(p1,o),vec.sub(p2,o),sf,sh);
	}
	public node gen(point pt){
		ray rt=new ray(pt.pos,vec.sub(pt.r.d,this.n.mul(2*vec.dot(this.n,pt.r.d))));
		nnode rn=new nnode(1);
		rn.setRay(rt,0);
		rn.setW(this.sf,0);
		return rn;
	}
}


class lightSurface extends plain{
	spec light;
	public lightSurface(vec p,vec u,vec v,spec l,int sh){
		this.p=p;
		this.u=u;
		this.v=v;
		this.n=vec.cro(u, v).unit();
		this.light=l;
		this.shape=sh;
	}
	public static lightSurface newlight(vec o,vec p1,vec p2,spec l,int sh){
		return new lightSurface(o,vec.sub(p1,o),vec.sub(p2,o),l,sh);
	}
	public node gen(point pt){
		if(vec.dot(pt.r.d,this.n)<0)
			return new lnode(this.light);
		else
			return new lnode(new spec(0,0,0));
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