
public  class minamo extends Thread {
	public static int sam=1;
	public static int psam=10;
	public static int threadn;
	public static int[] thok;
	public static thread threads[];
	static surface scene[];
	public static boolean nok(){
		 int flag=1;
		 for(int i=0;i<minamo.threadn;i++){
			 if(minamo.thok[i]==0)
				 flag=0;
		 }
		 if(flag==1) return false;
		 else return true;
	}
	public  static void main(String arg[]){//Test scene below
		//TODO: Read scene from text file.
		cam c=new cam(new vec(0,0,0),new vec(0,0,1),new vec(0,1,0),500,500);
		scene=new surface[7];
		threadn=4;
		thok=new int[threadn];
		threads=new thread[threadn];
		scene[0]=diffSurface.newdiff(new vec(-4,-4,16),new vec(-4,4,16),new vec(4,-4,16),new spec(0.5,0.1,0.1),1);
		scene[1]=diffSurface.newdiff(new vec(4,4,16),new vec(-4,4,16),new vec(4,4,1),new spec(0.1,0.5,0.1),1);
		scene[2]=diffSurface.newdiff(new vec(4,-4,16),new vec(4,4,16),new vec(4,-4,1),new spec(0.1,0.1,0.5),1);
		scene[3]=diffSurface.newdiff(new vec(-4,-4,16),new vec(4,-4,16),new vec(-4,-4,1),new spec(0.5,0.5,0.1),1);
		scene[4]=diffSurface.newdiff(new vec(-4,4,16),new vec(-4,-4,16),new vec(-4,4,1),new spec(0.1,0.5,0.5),1);
		scene[5]=lightSurface.newlight(new vec(-3,-3.99,12),new vec(3,-3.99,12),new vec(-3,-3.99,8),new spec(1500,1500,1500),1);
		scene[6]=new refsphere(new vec(2,3.1,14),0.9,new spec(0,0,0),new spec(0.9,0.9,0.9),1.5);
		for(int i=0;i<threadn;i++){
			threads[i]=new thread(i,c);
			threads[i].start();
		}
		while(minamo.nok()){
			try{
			minamo.sleep(1000);
			}catch (InterruptedException e)  
	        {  
	            e.printStackTrace();  
	        }  
		}
		c.print();
	}

}
