
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
	double lx,ly;
	vec posstart;
	surface a;
	public water(vec p,double lx,double ly,int xs,int ys){
		this.posstart=p;
		this.lx=lx;
		this.ly=ly;
		this.xs=xs;
		this.ys=ys;
		
		this.surfs=new surface[xs*ys*2];
		System.out.println(xs+","+ys+","+xs*ys*2);
		vec[][] poss=new vec[xs+1][ys+1];
		double h[][]=new double[xs+1][ys+1];
		double dx=lx/xs;
		double dy=ly/ys;
		for(int i=0;i<=xs;i++){
			for(int j=0;j<=ys;j++){
				h[i][j]=Math.sin(2*i*dx+3*j*dy)*0.12+Math.sin(i*dx-j*dy+0.5)*0.1;
			}
		}
		for(int i=0;i<=xs;i++){
			for(int j=0;j<=ys;j++){
				poss[i][j]=vec.add(this.posstart,new vec(i*dx,h[i][j],j*dy));
			}
		}
		for(int i=0;i<xs;i++){
			for(int j=0;j<ys;j++){
				this.surfs[(i*ys+j)*2]=new refSurface(poss[i][j],poss[i+1][j],poss[i][j+1],new spec(0.9,0.9,0.9),0,1.3);
				this.surfs[(i*ys+j)*2+1]=new refSurface(poss[i+1][j+1],poss[i][j+1],poss[i+1][j],new spec(0.7,0.7,0.9),0,1.3);
			}
		}
		this.a=new mirrSurface(this.posstart,vec.add(new vec(0,0,this.ly+1),this.posstart),vec.add(new vec(this.lx+1,0,0),this.posstart),new spec(0,0,0),1);
		///this.b=new diffSurface(this.posstart,vec.add(new vec(this.lx,-0.4,0),this.posstart),vec.add(new vec(0,-0.4,this.ly),this.posstart),new spec(0,0,0),1);
	}
	public point check(ray r){
		point pa=this.a.check(r);
		//point pb=this.b.check(r);
		if(pa==null)
			return null;

		point n=null;
		vec rp=vec.sub(pa.pos,this.posstart);
		// this trick is only useful for ray of small incident angle.. 
		double ii=vec.dot(rp, new vec(1,0,0))*this.xs/this.lx;
		double jj=vec.dot(rp, new vec(0,0,1))*this.ys/this.ly;
		double dist=1000000000;
		for(int i=Math.max((int)(ii-this.lx*8/this.xs),0);i<Math.min((int)(ii+this.lx*8/this.xs),this.xs);i++){
			for(int j=2*Math.max((int)(jj-this.ly*4/this.ys),0);j<2*Math.min((int)(jj+this.ly*4/this.ys),this.ys);j++){
				point nn=this.surfs[i*this.ys*2+j].check(r);
				 if(nn!=null&&nn.t<dist){
						n=nn;
					}
			}
		}
		/*double dist=1000000000;
		for(int i=0;i<this.surfs.length;i++){
				point nn=this.surfs[i].check(r);
			 if(nn!=null&&nn.t<dist){
				n=nn;
			}
		}*/
		return n;
	}
	public node gen(point pt){
		return this.surfs[pt.num].gen(pt);
	}
}

class point{
	surface surf;
	vec pos;
	ray r;
	double u,v,t;
	int num;//for water
	 point(vec pos,double u,double v,double t,surface surf,ray r){
		 this.pos=pos;
		 this.surf=surf;
		 this.v=v;
		 this.u=u;
		 this.t=t;
		 this.r=r;
	 }
}