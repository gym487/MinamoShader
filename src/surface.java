
public abstract class surface {
	 public node gen(point pt){
		 return null;
	 }
	 
	 public point check(ray r){
		 return null;
	 }
	 public vec getn(point pos){
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
		 //if(vec.dot(t,this.n)*vec.dot(r.d,this.n)>0)
			 //return null;
	//		vec vv=vec.sub(r.p,vec.add(this.p,vec.add(this.u.mul(0.5),this.v.mul(0.5))));
		//	if(Math.pow(vec.dot(r.d.mul(2),vv),2)-4*(Math.pow(vv.mod(),2)-Math.pow(vec.add(this.v,this.u).mod()/2, 2))<0)
				//return null;//surround sphere
		 double h=1/vec.dot(vec.cro(r.d,this.v),this.u);
		 double rt=(vec.dot(vec.cro(t,this.u),this.v))*h;
		 if(rt>0.00001){//&&vec.dot(r.d, this.n)<0  direction check // if rt=0 the ray will intersect the surface witch it from... that might cause a endless loop..
			 double ru=(vec.dot(vec.cro(r.d,this.v),t))*h;
			 if(ru<0||ru>1)
				 return null;
			 double rv=(vec.dot(vec.cro(t,this.u),r.d))*h;
			 if(rv<0||rv>1)
				 return null;
			 if((this.shape==0&&ru+rv<=1)||(this.shape==1&&ru<=1&&rv<=1)){
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
			double t;
			if(v.mod()-this.r>0.00001){
				t=-vec.dot(r.d, v)-Math.sqrt(Math.pow(vec.dot(r.d,v),2)-(Math.pow(v.mod(), 2)-Math.pow(this.r,2)));
			}else{
				t=-vec.dot(r.d, v)+Math.sqrt(Math.pow(vec.dot(r.d,v),2)-(Math.pow(v.mod(), 2)-Math.pow(this.r,2)));
			}
			if(t>0.00001){
				vec pos=vec.add(r.p,r.d.mul(t));
				return new point(pos,0,0,t,this,r);
			}else{
				return null;
			}
		}
	}
	public vec getn(vec pos){
		return vec.sub(pos,this.c).unit();
	}
}
class refsphere extends sphere{
	double ref;
	spec sf,st;
	public refsphere(vec c,double r,spec f,double ref){
		this.c=c;
		this.sf=f;
		this.ref=ref;
		this.r=r;
	}
	public node gen(point pt){

		double reff;
		vec n=this.getn(pt.pos);
		reff=this.ref;
		if(vec.dot(pt.r.d,this.getn(pt.pos))>0){
			reff=1/reff;
			n=n.opp();
		}
		double cos1=-vec.dot(n,pt.r.d);
		if(1-(1/Math.pow(reff,2))*(1-Math.pow(cos1, 2))>=0){
				ray rt=new ray(pt.pos,vec.sub(pt.r.d,n.mul(2*vec.dot(n,pt.r.d))));
				nnode rn=new nnode(2);
				rn.setRay(rt,1);
				double cos2=Math.sqrt(1-(1/Math.pow(reff,2))*(1-Math.pow(cos1,2)) );
				double a=cos1/cos2;
				double b=reff;
				double wr=Math.pow((a-b)/(a+b), 2);
				double wt=a*b*(2/(a+b));
				ray tt=new ray(pt.pos,vec.add(pt.r.d.mul(1/reff),n.mul((cos1/reff)-cos2)));
				rn.setRay(tt,0);
				rn.setW(this.sf.mul(wr),1);
				rn.setW(this.sf.mul(wt),0);
				return rn;
		}else{
				ray rt=new ray(pt.pos,vec.sub(pt.r.d,n.mul(2*vec.dot(n,pt.r.d))));
				nnode rn=new nnode(1);
				rn.setRay(rt,0);
				rn.setW(this.sf,0);
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

class diffsphere extends sphere{
	spec sd;
	public diffsphere(vec c,double r,spec d){
		this.c=c;
		this.sd=d;
		this.r=r;
	}

	public node gen(point pt){
		nnode rn=new nnode(minamo.sam);
		for(int i=0;i<minamo.sam;i++){
			rn.setRay(new ray(pt.pos,vec.normalRand(this.getn(pt.pos))),i);
			rn.setW(this.sd.mul(1.0/minamo.sam),i);
		}
		return rn;
	}
}

class diffSurface extends plain{
	spec sd;
	public diffSurface(vec p,vec p1,vec p2,spec diff,int sh){
		this.p=p;
		this.u=vec.sub(p1,p);
		this.v=vec.sub(p2,p);
		this.n=vec.cro(this.u,this.v).unit();
		this.sd=diff;
		this.shape=sh;
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

class gridDiffSurface extends plain{
	spec sd;
	spec sd2;
	int vs,us;
	public gridDiffSurface(vec p,vec p1,vec p2,spec diff,spec diff2,int sh,int us,int vs){
		this.p=p;
		this.u=vec.sub(p1,p);
		this.v=vec.sub(p2,p);
		this.n=vec.cro(this.u,this.v).unit();
		this.sd=diff;
		this.sd2=diff2;
		this.shape=sh;
		this.us=us;
		this.vs=vs;
	}

	public node gen(point pt){
		nnode rn=new nnode(minamo.sam);
		for(int i=0;i<minamo.sam;i++){
			rn.setRay(new ray(pt.pos,vec.normalRand(this.n)),i);
			if(pt.u%(1.0/this.us)>0.5/this.us^pt.v%(1.0/this.vs)>0.5/this.vs)
				rn.setW(this.sd.mul(1.0/minamo.sam),i);
			else
				rn.setW(this.sd2.mul(1.0/minamo.sam),i);
		}
		return rn;
	}
}


class refSurface extends plain{
	spec sf;
	double ref;
	public refSurface(vec p,vec p1,vec p2,spec f,int sh,double ref){
		this.p=p;
		this.u=vec.sub(p1,p);
		this.v=vec.sub(p2, p);
		this.n=vec.cro(this.u,this.v).unit();
		this.sf=f;
		this.shape=sh;
		this.ref=ref;
	}

	public node gen(point pt){

		double reff;
		vec n=this.n;
		reff=this.ref;
		if(vec.dot(pt.r.d,this.n)>0){
			reff=1/reff;
			n=n.opp();
		}
		double cos1=-vec.dot(n,pt.r.d);
		if(1-(1/Math.pow(reff,2))*(1-Math.pow(cos1, 2))>=0){
				ray rt=new ray(pt.pos,vec.sub(pt.r.d,n.mul(2*vec.dot(n,pt.r.d))));
				nnode rn=new nnode(2);
				rn.setRay(rt,1);
				double cos2=Math.sqrt(1-(1/Math.pow(reff,2))*(1-Math.pow(cos1,2)) );
				double a=cos1/cos2;
				double b=reff;
				double wr=Math.pow((a-b)/(a+b), 2);
				double wt=a*b*(2/(a+b));
				ray tt=new ray(pt.pos,vec.add(pt.r.d.mul(1/reff),n.mul((cos1/reff)-cos2)));
				rn.setRay(tt,0);
				rn.setW(this.sf.mul(wr),1);
				rn.setW(this.sf.mul(wt),0);
				return rn;
		}else{
				ray rt=new ray(pt.pos,vec.sub(pt.r.d,n.mul(2*vec.dot(n,pt.r.d))));
				nnode rn=new nnode(1);
				rn.setRay(rt,0);
				rn.setW(this.sf,0);
				return rn;
		}
	}
}

class mirrSurface extends plain{
	spec sf;
	public mirrSurface(vec p,vec p1,vec p2,spec f,int sh){
		this.p=p;
		this.u=vec.sub(p1,p);
		this.v=vec.sub(p2, p);
		this.n=vec.cro(this.u,this. v).unit();
		this.sf=f;
		this.shape=sh;
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
	public lightSurface(vec p,vec p1,vec p2,spec l,int sh){
		this.p=p;
		this.u=vec.sub(p1,p);;
		this.v=vec.sub(p2,p);;
		this.n=vec.cro(this.u, this.v).unit();
		this.light=l;
		this.shape=sh;
	}

	public node gen(point pt){
		if(vec.dot(pt.r.d,this.n)<0)
			return new lnode(this.light);
		else
			return new lnode(new spec(0,0,0));
	}
}





class water extends surface{
	public surface[] surfs;
	int xs,ys;
	vec u,v;
	vec posstart;
	surface a;
	spec sp;
	double reff;
	public water(vec p,vec p1, vec p2,int xs,int ys,spec sp,double r){
		this.posstart=p;
		this.u=vec.sub(p1,p);
		this.v=vec.sub(p2,p);
		this.xs=xs;
		this.ys=ys;
		this.sp=sp;
		this.reff=r;
		this.surfs=new surface[xs*ys*2];
		System.out.println(xs+","+ys+","+xs*ys*2);
		vec[][] poss=new vec[xs+1][ys+1];
		double h[][]=new double[xs+1][ys+1];
		for(int i=0;i<=xs;i++){
			for(int j=0;j<=ys;j++){
				h[i][j]=this.wav((double)i/xs,(double)j/ys);
			//	System.out.println(i+" "+j+" "+":"+h[i][j]);
			}
		}
		for(int i=0;i<=xs;i++){
			for(int j=0;j<=ys;j++){
				poss[i][j]=vec.add(p,vec.add(vec.add(this.u.mul((double)i/xs),this.v.mul((double)j/ys)),vec.cro(this.u,this.v).unit().mul(h[i][j])));
				//System.out.println(i+" "+j+" "+":"+poss[i][j].x+" "+poss[i][j].y+" "+poss[i][j].z);
			}
		}
		for(int i=0;i<xs;i++){
			for(int j=0;j<ys;j++){
				this.surfs[(i*ys+j)*2]=new refSurface(poss[i][j],poss[i+1][j],poss[i][j+1],sp,0,r);
				this.surfs[(i*ys+j)*2+1]=new refSurface(poss[i+1][j+1],poss[i][j+1],poss[i+1][j],sp,0,r);
			}
		}
		this.a=new mirrSurface(p,p1,p2,new spec(0,0,0),1);
		///this.b=new diffSurface(this.posstart,vec.add(new vec(this.lx,-0.4,0),this.posstart),vec.add(new vec(0,-0.4,this.ly),this.posstart),new spec(0,0,0),1);
	}
	public double wav(double uu,double vv){
		return Math.sin(24*uu+20*vv)*0.1+Math.sin(20*uu-18*vv+2.8)*0.1+Math.sin(30*uu+1)*0.05;
		//return 0;
	}
	public point check(ray r){
		point pa=this.a.check(r);
		//point pb=this.b.check(r);
		if(pa==null)
			return null;
		point n=null;
		vec sh=r.d.mul(1/vec.dot(r.d,vec.cro(this.u,this.v).unit()));
		double ii=pa.u*this.xs;
		double jj=pa.v*this.ys;
		double ri=vec.dot(sh,this.u.unit())*this.xs/this.u.mod();
		double rj=vec.dot(sh,this.v.unit())*this.ys/this.v.mod();
		double dist=1000000000;
		for(int i=Math.max((int)(ii-Math.abs(ri))-1,0);i<Math.min((int)(ii+Math.abs(ri))+1,this.xs);i++){
			for(int j=2*Math.max((int)(jj-Math.abs(rj))-1,0);j<2*Math.min((int)(jj+Math.abs(rj))+1,this.ys);j++){
				if(Math.abs(i*rj-j*ri/2+jj*ri-ii*rj)/Math.sqrt(Math.pow(ri,2)+Math.pow(rj,2))>=2)
					continue;
				point nn=this.surfs[i*this.ys*2+j].check(r);
				 if(nn!=null&&nn.t<dist){
						n=nn;
						dist=nn.t;
						
					}
			}
		}
		if(n!=null) n.surf=this;
		return n;
	}
	public vec getn(vec pos){
		double del=1e-4;
		vec rr=vec.sub(pos,this.posstart);
		double uu=vec.dot(rr,this.u.unit())/this.u.mod();
		double vv=vec.dot(rr,this.v.unit())/this.v.mod();
		double du=(this.wav(uu+del,vv)-this.wav(uu-del,vv))/(2*del);
		double dv=(this.wav(uu,vv+del)-this.wav(uu,vv-del))/(2*del);
		vec fn=vec.cro(this.u,this.v).unit();
		vec a=vec.add(this.u,fn.mul(du));
		vec b=vec.add(this.v,fn.mul(dv));
		return vec.cro(a,b).unit();
	}
	public node gen(point pt){
		double reff;
		vec n=this.getn(pt.pos);
		reff=this.reff;
		if(vec.dot(pt.r.d,this.getn(pt.pos))>0){
			reff=1/reff;
			n=n.opp();
		}
		double cos1=-vec.dot(n,pt.r.d);
		if(1-(1/Math.pow(reff,2))*(1-Math.pow(cos1, 2))>=0){
				ray rt=new ray(pt.pos,vec.sub(pt.r.d,n.mul(2*vec.dot(n,pt.r.d))));
				nnode rn=new nnode(2);
				rn.setRay(rt,1);
				double cos2=Math.sqrt(1-(1/Math.pow(reff,2))*(1-Math.pow(cos1,2)) );
				double a=cos1/cos2;
				double b=reff;
				double wr=Math.pow((a-b)/(a+b), 2);
				double wt=a*b*(2/(a+b));
				ray tt=new ray(pt.pos,vec.add(pt.r.d.mul(1/reff),n.mul((cos1/reff)-cos2)));
				rn.setRay(tt,0);
				rn.setW(this.sp.mul(wr),1);
				rn.setW(this.sp.mul(wt),0);
				return rn;
		}else{
				ray rt=new ray(pt.pos,vec.sub(pt.r.d,n.mul(2*vec.dot(n,pt.r.d))));
				nnode rn=new nnode(1);
				rn.setRay(rt,0);
				rn.setW(this.sp,0);
				return rn;
		}
		//return pt.surf.gen(pt);
	}
}

class point{
	surface surf;
	vec pos;
	ray r;
	double u,v,t;
	//int num;//for water
	 point(vec pos,double u,double v,double t,surface surf,ray r){
		 this.pos=pos;
		 this.surf=surf;
		 this.v=v;
		 this.u=u;
		 this.t=t;
		 this.r=r;
	 }
}