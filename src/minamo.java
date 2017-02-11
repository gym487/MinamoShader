
public  class minamo {
	public static int sam;

	static surface scene[];
	
	public  static void main(String arg[]){
		cam c=new cam(new vec(0,0,0),new vec(0,0,1),new vec(0,1,0),500,500);
		scene=new surface[2];
		scene[0]=lightSurface.newlight(new vec(-2,-4,13),new vec(2,-4,13),new vec(-2,-4,17),new spec(1000,1000,1000),1);
		scene[1]=lightSurface.newlight(new vec(4,4,10),new vec(-4,4,10),new vec(-4,4,18),new spec(1000,1000,1000),1);
	}

}
