public abstract class node{
	public node(){
	}
	public spec val(){
		return null;
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

 