import java.util.Arrays; 
public  class minamo extends Thread {
	public static int sam=1;
	public static int psam=1000;
	public static int threadn;
	public static int[] thok;
	public static thread threads[];
	static surface scene[];
	public static boolean nok(){
		 int flag=1;
		 for(int i=0;i<minamo.thok.length;i++){
			 if(minamo.thok[i]!=-2){
				 flag=0;
				 break;
			 }
		 }
		 if(flag==1) return false;
		 else return true;
	}
	public  static void main(String arg[]){//Test scene below
		//TODO: Load scene from text file.
		//cam c=new cam(new vec(0,0,0),new vec(0,0,1),new vec(0,1,0),500,500);
		cam c=new focuscam(new vec(0,0,0),new vec(0,0,1),new vec(0,1,0),500,500,0.1,11.0);
		scene=new surface[8];
		threadn=4;
		thok=new int[c.x];
		Arrays.fill(thok,-1);
		threads=new thread[threadn];
		scene[0]=new diffSurface(new vec(-6,-4,18),new vec(-6,4,18),new vec(6,-4,18),new spec(0.5,0.1,0.1),1);
		//scene[1]=new diffSurface(new vec(6,4,18),new vec(-6,4,18),new vec(6,4,-4),new spec(0.1,0.5,0.1),1);
		scene[1]=new gridDiffSurface(new vec(6,4,18),new vec(-6,4,18),new vec(6,4,-4),new spec(0.1,0.5,0.1),new spec(0.5,0.1,0.5),1,3,6);
		//scene[0]=new gridDiffSurface(new vec(60,4,180),new vec(-60,4,180),new vec(60,4,-4),new spec(0.1,0.5,0.1),new spec(0.5,0.1,0.5),1,15,30);
		scene[2]=new diffSurface(new vec(6,-4,18),new vec(6,4,18),new vec(6,-4,-4),new spec(0.1,0.1,0.5),1);
		scene[3]=new diffSurface(new vec(-6,-4,18),new vec(6,-4,18),new vec(-6,-4,-4),new spec(0.5,0.5,0.1),1);
		scene[4]=new diffSurface(new vec(-6,4,18),new vec(-6,-4,18),new vec(-6,4,-4),new spec(0.1,0.5,0.5),1);
		scene[5]=new lightSurface(new vec(-5,-3.99,17),new vec(5,-3.99,17),new vec(-5,-3.99,8),new spec(30000,30000,30000),1);
		scene[6]=new refsphere(new vec(2,2.5,10),1.5,new spec(0.9,0.9,0.9),1.5);
		scene[7]=new mirrsphere(new vec(-2,2.5,12),1.5,new spec(0.9,0.9,0.9));
		//scene[8]=new diffSurface(new vec(-6,4,-4),new vec(-6,-4,-4),new vec(6,4,-4),new spec(0.5,0.1,0.5),1);
		//scene[6]=new refSurface(new vec(-2,-2,10),new vec(-2,2,10),new vec(2,-2,10),new spec(0.05,0.05,0.05),new spec(0.9,0.9,0.9),1,1.5);
		//scene[6]=new water(new vec(6,2,18),new vec(-6,2,18),new vec(6,2,-4),24,48,new spec(0.9,0.9,0.9),1.3);
		
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
