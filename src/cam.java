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
	public void shoot1(){
		for(int xx=0;xx<this.x;xx++){
			for(int yy=0;yy<this.y;yy++){
				nnode nn=new nnode(minamo.sam);
				for(int i=0;i<minamo.sam;i++){
					ray r=new ray(this.pos,vec.add(this.d,vec.add(this.dx.mul((xx+(Math.random()-0.5)-((double)this.x/2))/(double)this.x), this.dy.mul((yy+(Math.random()-0.5)-(double)(this.y/2))/(double)this.y))));
					nn.setRay(r,i);
					nn.setW(new spec(1,1,1).mul(1.0/minamo.sam),i);
				}
				this.filmn[xx][yy]=nn;
			}
		}
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
	
