import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class TetrisGrid{
	
//boolean[][] grid;
//public Blocks[][] bgrid;

//draw grid
	public int x,y;
	
	public TetrisGrid(int x, int y) {
		//grid = new boolean[19][10];
		//bgrid = new Blocks[19][10];
		//for(int i = 0; i < grid.length;i++) {
			//for(int j = 0; j < grid[i].length;j++) {
			//	grid[i][j] = false;
				//bgrid[i][j] = null;
			//}
		//}
		this.x = x;
		this.y = y;
	
	}

	
	
	
	/* Drawing commands */
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.black);
		g.fillRect(0, 0, 700, 600);
		g.setColor(Color.GRAY);
		for(int i= 0;i<301; i = i+30) {
		g.drawLine(i,0,i,600);
		}
		for(int i= 0;i<600; i = i+30) {
			g.drawLine(0,i,299,i);
		}
		
		//update();
		
		
		
		
	
		
		

	}
	/* update variables here */
	private void update() {
	//for(int r = bgrid.length-1; r>= 0;r++) {
		//for(int c = 0; c < bgrid.length;c++) {
			//if(grid[r][c] == true && grid[r+1][c]==false) {
			//	grid[r][c] =false;
				//grid[r+1][c] =true;
				//bgrid[r][c].setYPos(30);
			//}
		//}
	//}
	
	
	
	}


	
	

}
