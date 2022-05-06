import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Blocks {
	int x;
	int y;
	Color c;
 static int count = 0;		
		public Blocks(int x, int y,Color color) {
			c = color;
			this.x = x;
			this.y = y;
			
		}
		
		
		
		
		
		
		
		
		
		
		public void paints(Graphics g) {
			//these are the 2 lines of code needed draw an image on the screen
			
			Graphics2D g2 = (Graphics2D) g;
			
			
			//call update to update the actualy picture location
			
			g.setColor(c);
			g2.fillRect(x, y, 30,30 );
			g.setColor(Color.black);
			g2.drawRect(x, y, 30,30 );
			
			
		
			
			

		}
		
		
		
		
		public void setYPos(int drop) {
			y+= drop;
		}
	}

