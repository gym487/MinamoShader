
public class thread extends Thread{
	 int num;
	 cam c;
	 public int getNum(){
		 return this.num;
	 }
	 public thread(int n,cam c){
		 this.num=n;
		 this.c=c;
	 }
	 public void run(){
		 int thid=this.num;
		 for(int i=0;i<this.c.x;i++){
			 if(minamo.thok[i]==-1){
				 minamo.thok[i]=this.num;
				 System.out.println("Thread: "+num+", Line: "+i+"/"+c.x);
				 for(int j=0;j<this.c.y;j++){
					 this.c.shoot1(i, j);
					 this.c.shoot2(i,j);
				 }
				 minamo.thok[i]=-2;
			 }
		 }
		
	 }
}
