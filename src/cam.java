import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
public class cam {
	int x,y;
	vec pos,d,dy,dx;
	spec[][] film;
	node[][] filmn;
	public cam(vec pos,vec d,vec dy,int x,int y){
		this.pos=pos;
		this.d=d.unit();
		this.x=x;
		this.y=y;
		this.film=new spec[x][y];
		this.filmn=new node[x][y];
		this.dy=dy.unit();
		this.dx=vec.cro(this.dy, this.d).unit();
	}
	public void shoot1(int xx,int yy){

				nnode nn=new nnode(minamo.psam);
				for(int i=0;i<minamo.psam;i++){
					ray r=new ray(this.pos,vec.add(this.d,vec.add(this.dx.mul((xx+((float)Math.random()-0.5f)-((float)this.x/2))/(float)this.x), this.dy.mul((yy+((float)Math.random()-0.5f)-(float)(this.y/2))/(float)this.y))));
					nn.setRay(r,i);
					nn.setW(new spec(1,1,1).mul(1.0f/minamo.psam),i);
				}
				this.filmn[xx][yy]=nn;
	}
	public void shoot2(int xx,int yy){
			
				this.filmn[xx][yy].fill();
				this.filmn[xx][yy].genn(new spec(1,1,1),1);
				this.film[xx][yy]=filmn[xx][yy].val();
				this.filmn[xx][yy]=null;
	}
	public void print(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String name=sdf.format(new Date());
		File ppmf=new File("./pictures/"+name+".ppm");
        try {
            ppmf.createNewFile(); 
            Writer out = null;
            out = new FileWriter(ppmf,true); 
            out.write("P3\n");
            out.write(String.valueOf(this.x)+" "+String.valueOf(this.y)+"\n");
            out.write("255\n");
    		for(int yy=0;yy<this.y;yy++){
    			for(int xx=0;xx<this.x;xx++){
    				this.film[xx][yy]=this.film[xx][yy].sRGB();// to sRGB
    				this.film[xx][yy].limit(255);
    				out.write(String.valueOf(Math.round(this.film[xx][yy].r))+" "+String.valueOf(Math.round(this.film[xx][yy].g))+" "+String.valueOf(Math.round(this.film[xx][yy].b))+" ");
    			} 
    			out.write("\n");
    		}
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}
	

class focuscam extends cam{
	float r;
	float ld;

	public focuscam(vec pos,vec d,vec dy,int x,int y,float r,float ld){
		super(pos,d,dy,x,y);
		this.r=r;
		this.ld=ld;
	}
	public void shoot1(int xx,int yy){

		nnode nn=new nnode(minamo.psam);
		for(int i=0;i<minamo.psam;i++){
			vec dir=vec.add(this.d,vec.add(this.dx.mul((xx+((float)Math.random()-0.5f)-((float)this.x/2))/(float)this.x), this.dy.mul((yy+((float)Math.random()-0.5f)-(float)(this.y/2))/(float)this.y))).unit();
			vec p=vec.add(this.pos,dir.mul(vec.dot(dir,this.d.mul(this.ld))));
			float lx=1;
			float ly=1;
			while(Math.pow(lx, 2)+Math.pow(ly,2)>1){
				lx=((float)Math.random()-0.5f)*2;
				ly=((float)Math.random()-0.5f)*2;
			}
			vec pixel=vec.add(this.pos,vec.add(this.dx.mul(this.r*lx),this.dy.mul(this.r*ly)));
			vec dirr=vec.sub(p,pixel).unit();
			ray r=new ray(pixel,dirr);
			nn.setRay(r,i);
			nn.setW(new spec(1,1,1).mul(1.0f/minamo.psam),i);
		}
		this.filmn[xx][yy]=nn;
}
	
}

