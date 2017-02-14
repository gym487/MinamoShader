
public class thread extends Thread{
	 int num;
	 cam c;
	 public thread(int n,cam c){
		 this.num=n;
		 this.c=c;
	 }
	 public void run(){
		 for(int i=this.num*this.c.x/minamo.threadn;i<(this.num+1)*this.c.x/minamo.threadn;i++){
			 System.out.println(String.valueOf(i-this.num*this.c.x/minamo.threadn)+"/"+String.valueOf(this.c.x/minamo.threadn)+"  thread "+String.valueOf(this.num));
			 for(int j=0;j<this.c.y;j++){
				 this.c.shoot1(i, j);
				 this.c.shoot2(i,j);
			 }
		 }
		 minamo.thok[this.num]=1;
	 }
}
