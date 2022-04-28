import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Active {
public int x;
public int y;
public Blocks b1;	
public Blocks b2;	
public Blocks b3;	
public Blocks b4;		
public Color c;
	public Active(int x1,int y1, Color color) {
	x = x1;
	y = y1;
	c= color;
	BlockMaker();
	}
	
	public void BlockMaker() {
		
	}
	
	
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		
		Graphics2D g2 = (Graphics2D) g;
		b1.paints(g2);
		b2.paints(g2);
		b3.paints(g2);
		b4.paints(g2);
		//call update to update the actualy picture location
		
		update();
		
		
		
	
		
		

	
	}
	public void update() {
	b1.y += 30;
	b2.y += 30;
	b3.y += 30;
	b4.y += 30;
	}
	public Blocks getb1() {
		return b1;
	}
}
