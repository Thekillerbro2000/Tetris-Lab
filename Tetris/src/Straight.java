import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

//draw straight
public class Straight extends Active {
//public int x;
//public int y;

	public Straight(int x, int y,Color c) {
		super(x,y,c);
		
		
	}
	
	
	public void BlockMaker() {
		b1 = new Blocks(x,y,c);
		b2 = new Blocks(x,y-30,c);
		b3 = new Blocks(x,y+30,c);
		b4 = new Blocks(x,y+60,c);
	}
	
	
	
	
	
	
	
	
}
