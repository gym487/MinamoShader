
public class thread extends Thread{
	 int num;
	 cam c;
	 public thread(int n,cam c){
		 this.num=n;
		 this.c=c;
	 }
	 public void run(){
		 for(int i=0;i<this.c.x;i++){
			 if(minamo.thok[i]==-1){
				 minamo.thok[i]=this.num;
				 System.out.println("Thread: "+num+", Line: "+i);
				 for(int j=0;j<this.c.y;j++){
					 this.c.shoot1(i, j);
					 this.c.shoot2(i,j);
				 }
				 minamo.thok[i]=-2;
			 }
		 }
		
	 }
}
