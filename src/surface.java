
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

 class plain extends surface{
	 int shape;//0=tri 1=squ
	 vec p,u,v,n;//point u v normal;
	 texture tx;
	public plain(vec p,vec p1,vec p2,int sh,texture tx){
			this.p=p;
			this.u=vec.sub(p1,p);
			this.v=vec.sub(p2,p);
			this.n=vec.cro(this.u, this.v).unit();
			this.tx=tx;
			this.shape=sh;
		}

	 public node gen(point pt){
		 return tx.gen(pt);
	 }
	 public vec getn(point pt){
		 return this.n;
	 }
	 public point check(ray r){
		 
		 vec t=vec.sub(r.p,this.p);
		 //if(vec.dot(t,this.n)*vec.dot(r.d,this.n)>0)
			 //return null;
	//		vec vv=vec.sub(r.p,vec.add(this.p,vec.add(this.u.mul(0.5),this.v.mul(0.5))));
		//	if(Math.pow(vec.dot(r.d.mul(2),vv),2)-4*(Math.pow(vv.mod(),2)-Math.pow(vec.add(this.v,this.u).mod()/2, 2))<0)
				//return null;//surround sphere
		 float h=1/vec.dot(vec.cro(r.d,this.v),this.u);
		 float rt=(vec.dot(vec.cro(t,this.u),this.v))*h;
		 if(rt>0.00001){//&&vec.dot(r.d, this.n)<0  direction check // if rt=0 the ray will intersect the surface witch it from... that might cause a endless loop..
			 float ru=(vec.dot(vec.cro(r.d,this.v),t))*h;
			 if(ru<0||ru>1)
				 return null;
			 float rv=(vec.dot(vec.cro(t,this.u),r.d))*h;
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

class sphere extends surface{
	vec c;//center
	float r;//radius
	texture tx;
	public sphere(vec c,float r,texture tx){
		this.c=c;
		this.tx=tx;
		this.r=r;
	}
	public point check(ray r){
		vec v=vec.sub(r.p,this.c);
		if(Math.pow(vec.dot(r.d.mul(2),v),2)-4*(Math.pow(v.mod(),2)-Math.pow(this.r, 2))<0)
			return null;
		else{
			float t;
			if(v.mod()-this.r>0.00001){
				t=-vec.dot(r.d, v)-(float)Math.sqrt(Math.pow(vec.dot(r.d,v),2)-(Math.pow(v.mod(), 2)-Math.pow(this.r,2)));
			}else{
				t=-vec.dot(r.d, v)+(float)Math.sqrt(Math.pow(vec.dot(r.d,v),2)-(Math.pow(v.mod(), 2)-Math.pow(this.r,2)));
			}
			if(t>0.00001){
				vec pos=vec.add(r.p,r.d.mul(t));
				return new point(pos,0,0,t,this,r);
			}else{
				return null;
			}
		}
	}
	public vec getn(point pt){
		return vec.sub(pt.pos,this.c).unit();
	}
	public node gen(point pt){
		return this.tx.gen(pt);
	}
}





class water extends surface{
	public surface[] surfs;
	int xs,ys;
	vec u,v;
	vec posstart;
	surface a;
	spec sp;
	float reff;
	public water(vec p,vec p1, vec p2,int xs,int ys,spec sp,float r){
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
		float h[][]=new float[xs+1][ys+1];
		for(int i=0;i<=xs;i++){
			for(int j=0;j<=ys;j++){
				h[i][j]=this.wav((float)i/xs,(float)j/ys);
			//	System.out.println(i+" "+j+" "+":"+h[i][j]);
			}
		}
		for(int i=0;i<=xs;i++){
			for(int j=0;j<=ys;j++){
				poss[i][j]=vec.add(p,vec.add(vec.add(this.u.mul((float)i/xs),this.v.mul((float)j/ys)),vec.cro(this.u,this.v).unit().mul(h[i][j])));
				//System.out.println(i+" "+j+" "+":"+poss[i][j].x+" "+poss[i][j].y+" "+poss[i][j].z);
			}
		}
		for(int i=0;i<xs;i++){
			for(int j=0;j<ys;j++){
				this.surfs[(i*ys+j)*2]=new plain(poss[i][j],poss[i+1][j],poss[i][j+1],0,null);
				this.surfs[(i*ys+j)*2+1]=new plain(poss[i+1][j+1],poss[i][j+1],poss[i+1][j],0,null);
			}
		}
		this.a=new plain(p,p1,p2,1,null);
		///this.b=new diffSurface(this.posstart,vec.add(new vec(this.lx,-0.4,0),this.posstart),vec.add(new vec(0,-0.4,this.ly),this.posstart),new spec(0,0,0),1);
	}
	public float wav(float uu,float vv){
		return (float)(Math.sin(24*uu+20*vv)*0.1+Math.sin(20*uu-18*vv+2.8f)*0.1f+Math.sin(30*uu+1)*0.05f);
		//return 0;
	}
	public point check(ray r){
		point pa=this.a.check(r);
		//point pb=this.b.check(r);
		if(pa==null)
			return null;
		point n=null;
		vec sh=r.d.mul(1/vec.dot(r.d,vec.cro(this.u,this.v).unit()));
		float ii=pa.u*this.xs;
		float jj=pa.v*this.ys;
		float ri=vec.dot(sh,this.u.unit())*this.xs/this.u.mod();
		float rj=vec.dot(sh,this.v.unit())*this.ys/this.v.mod();
		float dist=1000000000;
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
		float del=1e-4f;
		vec rr=vec.sub(pos,this.posstart);
		float uu=vec.dot(rr,this.u.unit())/this.u.mod();
		float vv=vec.dot(rr,this.v.unit())/this.v.mod();
		float du=(this.wav(uu+del,vv)-this.wav(uu-del,vv))/(2*del);
		float dv=(this.wav(uu,vv+del)-this.wav(uu,vv-del))/(2*del);
		vec fn=vec.cro(this.u,this.v).unit();
		vec a=vec.add(this.u,fn.mul(du));
		vec b=vec.add(this.v,fn.mul(dv));
		return vec.cro(a,b).unit();
	}
	public node gen(point pt){
		float reff;
		vec n=this.getn(pt.pos);
		reff=this.reff;
		if(vec.dot(pt.r.d,this.getn(pt.pos))>0){
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
		//return pt.surf.gen(pt);
	}
}

class point{
	surface surf;
	vec pos;
	ray r;
	float u,v,t;
	//int num;//for water
	 point(vec pos,float u,float v,float t,surface surf,ray r){
		 this.pos=pos;
		 this.surf=surf;
		 this.v=v;
		 this.u=u;
		 this.t=t;
		 this.r=r;
	 }
}