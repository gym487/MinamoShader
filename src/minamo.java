
public  class minamo {
	public static int sam=100;

	static surface scene[];
	
	public  static void main(String arg[]){
		cam c=new cam(new vec(0,0,0),new vec(0,0,1),new vec(0,1,0),500,500);
		scene=new surface[2];
		scene[0]=mirrSurface.newmirr(new vec(8,4,5),new vec(8,4,17),new vec(-8,4,5),new spec(1000,1000,1000),1);
		scene[1]=lightSurface.newlight(new vec(-4,-4,17),new vec(-4,4,17),new vec(4,-4,17),new spec(1000,1000,1000),1);
		c.shoot1();
		c.shoot2();
		c.print();
	}

}
