
public class rand {
	float[] rands;
	int num;
	int p;
	public rand(int num){
		this.rands=new float[num];
		this.num=num;
		for(int i=0;i<num;i++){
			rands[i]=(float)Math.random();
		}
		this.p=0;
	}
	public float getrand(){
		this.p++;
		if(this.p>num){
			this.p=1;
		}
		return this.rands[this.p-1];
	}
}
