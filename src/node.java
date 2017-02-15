

public abstract class node{
	public node(){
	}
	public spec val(){
		return null;
	}
	public void fill(){
	}
	public void genn(spec w,int times){
	}
}
class nnode extends node {
	private int rn;//number of rays;
	private ray[] rays;
	private spec[] weights;
	private node[] childs;
	public nnode(int rn){
		this.rn=rn;
		this.rays=new ray[rn];
		this.weights=new spec[rn];
		this.childs=new node[rn];
	}
	public void setRay(ray r,int i){
		this.rays[i]=r;
	}
	public ray getRay(int i){
		return this.rays[i];
	}
	public void setW(spec w,int i){
		this.weights[i]=w;
	}	
	public spec val(){
		spec r=new spec(0,0,0);
		for(int i=0;i<this.rn;i++){
			r=r.add(this.childs[i].val().mul(this.weights[i]));
		}
		return r;
	}
	
	public void fill(){
		
		for(int i=0;i<this.rn;i++){
			if(this.rays[i]!=null&&this.childs[i]==null){
				double distance=1000000000;
				point ptt=null;
				for(int j=0;j<minamo.scene.length;j++){
					point pt=minamo.scene[j].check(this.rays[i]);
					if(pt!=null&&pt.t<distance){
						distance=pt.t;
						ptt=pt;
					}
				}
				if(ptt!=null){
					this.childs[i]=ptt.surf.gen(ptt);
				}else{
					this.childs[i]=new lnode(new spec(0,0,0));
				}
			}
		}
	}
	public void genn(spec w,int times){
		if(times<10){//&& times<10  or w.tol()>0.001 or w.tol>1/minamo.psam
			for(int i=0;i<this.childs.length;i++){
				this.childs[i].fill();
			}
			for(int i=0;i<this.childs.length;i++){
				this.childs[i].genn(w.mul(this.weights[i]),times+1);
			}
		}else{
			for(int i=0;i<this.childs.length;i++){
				this.childs[i]=new lnode(new spec(0,0,0));
			}
		}
	}
}
class lnode extends node{
	private spec col;
	public lnode(spec c){
		this.col=c;
	}
	public spec val(){
		return this.col;
	}
}

 