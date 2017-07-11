import java.util.Arrays; 
public  class minamo extends Thread {
	public static int sam=1;
	public static int psam=100;
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
		cam c=new focuscam(new vec(0,0,0),new vec(0,0,1),new vec(0,1,0),500,500,0.1f,11.0f);
		scene=new surface[7];
		threadn=4;
		thok=new int[c.x];
		Arrays.fill(thok,-1);
		threads=new thread[threadn];
		scene[0]=new plain(new vec(-6,-4,18),new vec(-6,4,18),new vec(6,-4,18),1,new diff(new spec(0.5f,0.1f,0.1f)));
		scene[1]=new plain(new vec(6,4,18),new vec(-6,4,18),new vec(6,4,-4),1,new grid(new spec(0.1f,0.5f,0.1f),new spec(0.5f,0.1f,0.5f),3,6));
		scene[2]=new plain(new vec(6,-4,18),new vec(6,4,18),new vec(6,-4,-4),1,new diff(new spec(0.1f,0.1f,0.5f)));
		scene[3]=new plain(new vec(-6,-4,18),new vec(6,-4,18),new vec(-6,-4,-4),1,new diff(new spec(0.5f,0.5f,0.1f)));
		scene[4]=new plain(new vec(-6,4,18),new vec(-6,-4,18),new vec(-6,4,-4),1,new diff(new spec(0.1f,0.5f,0.5f)));
		//scene[5]=new plain(new vec(-4,-3.99,16),new vec(4,-3.99,16),new vec(-4,-3.99,8),1,new light(new spec(30000,30000,30000)));
		scene[6]=new sphere(new vec(2,2.5f,10),1.5f,new ref(new spec(0.9f,0.9f,0.9f),1.5f));
		scene[5]=new sphere(new vec(-2f,2.5f,12f),1.5f,new light(new spec(0.9f,0.9f,0.9f).mul(60000f)));

		
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
