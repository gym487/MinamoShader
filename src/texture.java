
public abstract class texture {
	public node gen(point pt){
		return null;
	}
}
class diff extends texture{
	spec sp;
	public diff(spec sp){
		this.sp=sp;
	}
	public node gen(point pt){
		nnode rn=new nnode(minamo.sam);
		for(int i=0;i<minamo.sam;i++){
			rn.setRay(new ray(pt.pos,vec.normalRand(pt.surf.getn(pt))),i);
			rn.setW(this.sp.mul(1.0f/minamo.sam),i);
		}
		return rn;
	}
	
}
class ref extends texture{
	float ref;
	spec sp;
	public ref(spec sp,float ref){
		this.sp=sp;
		this.ref=ref;
	}
	public node gen(point pt){
		float reff;
		vec n=pt.surf.getn(pt);
		reff=this.ref;
		if(vec.dot(pt.r.d,pt.surf.getn(pt))>0){
			reff=1/reff;
			n=n.opp();
		}
		float cos1=-vec.dot(n,pt.r.d);
		if(1-(1/Math.pow(reff,2))*(1-Math.pow(cos1, 2))>=0){
				ray rt=new ray(pt.pos,vec.sub(pt.r.d,n.mul(2*vec.dot(n,pt.r.d))));
				nnode rn=new nnode(2);
				rn.setRay(rt,1);
				float cos2=(float)Math.sqrt(1-(1/Math.pow(reff,2))*(1-Math.pow(cos1,2)) );
				float a=cos1/cos2;
				float b=reff;
				float wr=(float)Math.pow((a-b)/(a+b), 2);
				float wt=a*b*(2/(a+b));
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
}
}

class mirr extends texture{
	spec sp;
	public mirr(spec sp){
		this.sp=sp;
	}
	public node gen(point pt){
		ray rt=new ray(pt.pos,vec.sub(pt.r.d,pt.surf.getn(pt).mul(2*vec.dot(pt.surf.getn(pt),pt.r.d))));
		nnode rn=new nnode(1);
		rn.setRay(rt,0);
		rn.setW(this.sp,0);
		return rn;
	}
}
class grid extends texture{
	spec sp;
	spec sp2;
	int vs,us;
	public grid(spec diff,spec diff2,int us,int vs){
		this.sp=diff;
		this.sp2=diff2;
		this.us=us;
		this.vs=vs;
	}

	public node gen(point pt){
		nnode rn=new nnode(minamo.sam);
		for(int i=0;i<minamo.sam;i++){
			rn.setRay(new ray(pt.pos,vec.normalRand(pt.surf.getn(pt))),i);
			if(pt.u%(1.0/this.us)>0.5/this.us^pt.v%(1.0/this.vs)>0.5/this.vs)
				rn.setW(this.sp.mul(1.0f/minamo.sam),i);
			else
				rn.setW(this.sp2.mul(1.0f/minamo.sam),i);
		}
		return rn;
	}
}

class light extends texture{
	spec light;
	public light(spec l){
		this.light=l;
	}

	public node gen(point pt){
		if(vec.dot(pt.r.d,pt.surf.getn(pt))<0)
			return new lnode(this.light);
		else
			return new lnode(new spec(0,0,0));
	}
}