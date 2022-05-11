# Tetris-Lab

Tetris is a classic game made within java where you try and fit blocks falling from the top of the screen into each other. You can move the blocks around, either left to right and/or you can rotate them. The blocks fall at a certain rate, but you can make them fall faster if you're sure of your positioning

![tetris-micro-blizz](https://user-images.githubusercontent.com/70667702/167948879-28354ac1-02a2-4bcc-baa6-882f46c65352.gif)

# How to Play

Use the Up Arrow to rotate the piece, use the down arrow to move the piece down faster, use the left and right arrow keys to move the piece left and right. 
- 1 Line complete = 40 Points
- 2 Line complete = 100 Points 
- 3 Line complete = 300 Points
- 4 Line complete = Tetris = 1200 Points
- As level increases points gained per line also increases




#### Classes
- Active.class
- Blocks.class
- Framer$1.class
- Framer$2.class
- Framer.class
- Jbox.class
- Lbox.class
- Zbox.class
- Sbox.class
- Tbox.class
- Square.class
- Straight.class
- TetrisGrid.class

## Code

Main framer code

  import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Framer extends JPanel implements ActionListener, MouseListener, KeyListener{
	static boolean[][] noActgrid;
	public static Blocks[][] bgrid;
	 int delay = 1000;
	TetrisGrid bg = new TetrisGrid(0,0);
	ArrayList<Active> control = new ArrayList<Active>();
	private Random rnd = new Random();
	Square square;
	private static int score = 0;
	Font f1 = new Font(Font.SERIF, Font.PLAIN, 50);
	Font f2 = new Font(Font.SERIF, Font.PLAIN, 25);
	//draw the score, paint, grid
	public void paint(Graphics g) {
		super.paintComponent(g);
		bg.paint(g);
		
		for(int r = bgrid.length-1; r>= 0;r--) {
			for(int c = 0; c < bgrid[r].length;c++) {
				if(bgrid[r][c] != null) {
				bgrid[r][c].paints(g);
				
				}
			
				
		}
		}

		g.setFont(f1);
		g.setColor(Color.yellow);
		g.drawString("Tetris" , 400, 70);
		g.setFont(f2);
		g.drawString("score: " + score , 350,120 );
		
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		noActgrid = new boolean[19][10];
		bgrid = new Blocks[19][10];
		for(int r = bgrid.length-1; r>= 0;r--) {
			for(int c = 0; c < bgrid[r].length;c++) {
				bgrid[r][c] = null;
				
			}
		}
		Framer f = new Framer();
		
		System.out.println("" + 9);
	
	
	}
	//get the active block and divid it by 30 so it will not go off the grid and +1 to check the one below the y axis
	//check all the blocks below the active block and make sure it does not leave the grid
	public void dropCheck() {
		ArrayList<Blocks> low = new ArrayList<Blocks>();
		
		if(((control.get(0).b1.y)/30)+1 != bgrid.length && ((control.get(0).b2.y)/30)+1 != bgrid.length && ((control.get(0).b3.y)/30)+1 != bgrid.length && ((control.get(0).b4.y)/30)+1 != bgrid.length ) {
			
			if(noActgrid[((control.get(0).b1.y)/30)+1][((control.get(0).b1.x)/30)] != true && 
			noActgrid[((control.get(0).b2.y)/30)+1][((control.get(0).b2.x)/30)] != true &&
			noActgrid[((control.get(0).b3.y)/30)+1][((control.get(0).b3.x)/30)] != true &&
			noActgrid[((control.get(0).b4.y)/30)+1][((control.get(0).b4.x)/30)] != true) {
				ActiveDropper();
			}else {
			noActgrid[((control.get(0).b1.y)/30)][((control.get(0).b1.x)/30)] = true; 
			noActgrid[((control.get(0).b2.y)/30)][((control.get(0).b2.x)/30)] = true; 
			noActgrid[((control.get(0).b3.y)/30)][((control.get(0).b3.x)/30)] = true; 
			noActgrid[((control.get(0).b4.y)/30)][((control.get(0).b4.x)/30)] = true;
			control.remove(0);
			rowChecker();
			spawn();
				
			}
		}
		else {
			noActgrid[((control.get(0).b1.y)/30)][((control.get(0).b1.x)/30)] = true; 
			noActgrid[((control.get(0).b2.y)/30)][((control.get(0).b2.x)/30)] = true; 
			noActgrid[((control.get(0).b3.y)/30)][((control.get(0).b3.x)/30)] = true; 
			noActgrid[((control.get(0).b4.y)/30)][((control.get(0).b4.x)/30)] = true;
			control.remove(0);
		
			rowChecker();
			spawn();
			
		}
	}
	//erase the row that have 10 blocks and get the perivous line of rows go down by 1 
	public void ActiveDropper() {
		

	control.get(0).b1.y += 30;
	control.get(0).b2.y += 30;
	control.get(0).b3.y += 30;
	control.get(0).b4.y += 30;
	bgrid[((control.get(0).b1.y)/30)-1][((control.get(0).b1.x)/30)] = null;
	bgrid[((control.get(0).b2.y)/30)-1][((control.get(0).b2.x)/30)] = null;
	bgrid[((control.get(0).b3.y)/30)-1][((control.get(0).b3.x)/30)] = null;
	bgrid[((control.get(0).b4.y)/30)-1][((control.get(0).b4.x)/30)] = null;
	bgrid[((control.get(0).b1.y)/30)][((control.get(0).b1.x)/30)] = control.get(0).b1;
	bgrid[((control.get(0).b2.y)/30)][((control.get(0).b2.x)/30)] = control.get(0).b2;	
	bgrid[((control.get(0).b3.y)/30)][((control.get(0).b3.x)/30)] = control.get(0).b3;	
	bgrid[((control.get(0).b4.y)/30)][((control.get(0).b4.x)/30)] = control.get(0).b4;	
	
	
	}
	
 
	
	public Framer() {
		JFrame f = new JFrame("Tetris");
		f.setSize(new Dimension(614, 600));
		f.setBackground(Color.blue);
		f.add(this);
		f.setResizable(false);
		f.setLayout(new GridLayout(1,2));
		f.addMouseListener(this);
		f.addKeyListener(this);
		 ActionListener taskPerformer = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		         dropCheck();
		      }
		  };
	 
		new Timer(delay, taskPerformer).start();
		Timer t = new Timer(20, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		spawn();
		
	}
	
	
	public void blockStop() {
		
	}
	
	boolean spawn = true;
	
	public void spawn() {
	int r= rnd.nextInt(7);
	//set each kind of block to certain color
	if(r == 0) {
	Active straight = new Straight(120,30,Color.red); //set Staright line to red
	control.add(straight);
	}else if(r==1) {
	square = new Square(120,30,Color.cyan);	//set Cyan to Square
	control.add(square);
	}else if(r==2) {
	Active z = new Zbox(120,0,Color.green); //set Zbox to green
	control.add(z);
	}else if(r==3) {
	Active s = new SBox(120,0,Color.yellow); //set Sbox to yellow
	control.add(s);
	}else if(r==4) {
	Active t = new Tbox(120,0,Color.ORANGE); //set Tbox to orange
	control.add(t);
	}else if(r==5) {
	Active l = new Lbox(120,30,Color.MAGENTA); //set Lbox to magneta
	control.add(l);
	}else {
	Active j = new Jbox(120,30,Color.LIGHT_GRAY); //set Jbox to Lightgray
	control.add(j);
	}
	bgrid[((control.get(0).b1.y)/30)][((control.get(0).b1.x)/30)] = control.get(0).b1; 
	bgrid[((control.get(0).b2.y)/30)][((control.get(0).b2.x)/30)] = control.get(0).b2; 
	bgrid[((control.get(0).b3.y)/30)][((control.get(0).b3.x)/30)] = control.get(0).b3; 
	bgrid[((control.get(0).b4.y)/30)][((control.get(0).b4.x)/30)] = control.get(0).b4;
	
	}
	
	//check the row to see if it has 10 blocks in one row or not
	public void rowChecker() {
	int sum = 0;
	int start = 0;
	boolean consecutive = false;
	for(int r = bgrid.length-1; r >= 0;r--) {
		for(int c = 0; c < bgrid[r].length;c++){
			if(noActgrid[r][c] == true) {
				sum++;
			}
		}
		if(sum == 10) {
			if(!consecutive) {
				consecutive = true;
				start = r;
			}
			
			
		}else if(sum != 10 && consecutive ){
			consecutive = false;
			dropRows(start,r+1);
			scoreRows(start-r);
			start = 0;
		}
		sum =0;
	}
		
		
		
		
	
	}
	//drops the rows when they find 10 blocks in a row
	// Clear completed rows from the field and award score according to
	// the number of simultaneously cleared rows.
	private void dropRows(int start, int end) {
		
	for(int r = start; r >= end; r--) {
		for(int col = 0; col < bgrid[r].length; col++) {
			bgrid[r][col] = null;
			noActgrid[r][col] = false;
		}
	}
	for(int r = end-1; r >=0; r--) {
		for(int col = 0; col < bgrid[r].length; col++) {
			if(bgrid[r][col] != null) {
				bgrid[r+(start-end)+1][col] = bgrid[r][col];
				bgrid[r][col].y = (r+(start-end)+1)*30;
				noActgrid[r+(start-end)+1][col] = true;
				noActgrid[r][col] = false;
				bgrid[r][col] = null;
				
			}
		}
	}
	}
	//add score to the game
	public void scoreRows(int rows) {
	//first row that clear add 30
	if(rows == 1) {
		score+= 40;
	//second row that clear add 100
	}else if(rows == 2) {
		score += 100;
	//third row that clear add 300
	}else if(rows == 3) {
		score += 300;
	//else score will go up by 1200
	}else {
		score += 1200;
	}
	

	}
	
	
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {

	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}
	
	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
	
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		//rotating blockwise and counter clockwise
		int q , w,e,r,t,y,u,i;
		q= (((control.get(0).b2.y)/30) - ((control.get(0).b1.y)/30));
		w= (((control.get(0).b2.x)/30) - ((control.get(0).b1.x)/30));
		e= (((control.get(0).b3.y)/30) - ((control.get(0).b1.y)/30));
		r= (((control.get(0).b3.x)/30) - ((control.get(0).b1.x)/30));
		u= (((control.get(0).b4.y)/30) - ((control.get(0).b1.y)/30));
		i= (((control.get(0).b4.x)/30) - ((control.get(0).b1.x)/30));
		if(arg0.getKeyCode() == 38 && control.get(0) != square && noActgrid[((control.get(0).b1.y)/30)-w][((control.get(0).b1.x)/30)+q] == false && noActgrid[((control.get(0).b1.y)/30)-r][((control.get(0).b1.x)/30)+e] == false && noActgrid[((control.get(0).b1.y)/30)-i][((control.get(0).b1.x)/30)+u] == false) {
		bgrid[((control.get(0).b2.y)/30)][((control.get(0).b2.x)/30)] = null;
		bgrid[((control.get(0).b3.y)/30)][((control.get(0).b3.x)/30)] = null;
		bgrid[((control.get(0).b4.y)/30)][((control.get(0).b4.x)/30)] = null;
		(control.get(0).b2.y) = (control.get(0).b1.y) - (w*30);
		(control.get(0).b2.x) = (control.get(0).b1.x) + (q*30);
		bgrid[((control.get(0).b1.y)/30)-w][((control.get(0).b1.x)/30)+q] = control.get(0).b2;
		
		
		(control.get(0).b3.y) = (control.get(0).b1.y) - (r*30);
		(control.get(0).b3.x) = (control.get(0).b1.x) + (e*30);
		bgrid[((control.get(0).b1.y)/30)-r][((control.get(0).b1.x)/30)+e] = control.get(0).b3;
		
		
		(control.get(0).b4.y) = (control.get(0).b1.y) - (i*30);
		(control.get(0).b4.x) = (control.get(0).b1.x) + (u*30);
		bgrid[((control.get(0).b1.y)/30)-i][((control.get(0).b1.x)/30)+u] = control.get(0).b4;
				
		
		
		}
		//down arrows key
		if(arg0.getKeyCode() == 40 && control.get(0).getb1().y <=510 &&  control.get(0).b2.y <=510  && control.get(0).b3.y <=510  && control.get(0).b4.y <=510) {
			dropCheck();
			
		
		}
			
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		//move left and right
		System.out.println(arg0.getKeyCode());
		if(arg0.getKeyCode() == 37 && control.get(0).getb1().x !=0 &&  control.get(0).b2.x !=0  && control.get(0).b3.x !=0  && control.get(0).b4.x !=0 && noActgrid[((control.get(0).b2.y)/30)][((control.get(0).b2.x)/30)-1] == false &&  noActgrid[((control.get(0).b3.y)/30)][((control.get(0).b3.x)/30)-1] == false &&  noActgrid[((control.get(0).b4.y)/30)][((control.get(0).b4.x)/30)-1] == false && noActgrid[((control.get(0).b1.y)/30)][((control.get(0).b1.x)/30)-1] == false) {
			control.get(0).b1.x -=30;
			control.get(0).b2.x -=30;
			control.get(0).b3.x -=30;
			control.get(0).b4.x -=30;
			bgrid[((control.get(0).b1.y)/30)][((control.get(0).b1.x)/30)+1] = null;
			bgrid[((control.get(0).b2.y)/30)][((control.get(0).b2.x)/30)+1] = null;
			bgrid[((control.get(0).b3.y)/30)][((control.get(0).b3.x)/30)+1] = null;	
			bgrid[((control.get(0).b4.y)/30)][((control.get(0).b4.x)/30)+1] = null;
			bgrid[((control.get(0).b1.y)/30)][((control.get(0).b1.x)/30)] = control.get(0).b1;
			
			bgrid[((control.get(0).b2.y)/30)][((control.get(0).b2.x)/30)] = control.get(0).b2;
			
			bgrid[((control.get(0).b3.y)/30)][((control.get(0).b3.x)/30)] = control.get(0).b3;
			
			bgrid[((control.get(0).b4.y)/30)][((control.get(0).b4.x)/30)] = control.get(0).b4;
			
		}
		if(arg0.getKeyCode() == 39 && control.get(0).b1.x !=270 &&  control.get(0).b2.x !=270  && control.get(0).b3.x !=270  && control.get(0).b4.x !=270 && noActgrid[((control.get(0).b2.y)/30)][((control.get(0).b2.x)/30)+1] == false &&  noActgrid[((control.get(0).b3.y)/30)][((control.get(0).b3.x)/30)+1] == false &&  noActgrid[((control.get(0).b4.y)/30)][((control.get(0).b4.x)/30)+1] == false && noActgrid[((control.get(0).b1.y)/30)][((control.get(0).b1.x)/30)+1] == false) {
			control.get(0).b1.x +=30;
			control.get(0).b2.x +=30;
			control.get(0).b3.x +=30;
			control.get(0).b4.x +=30;
			bgrid[((control.get(0).b1.y)/30)][((control.get(0).b1.x)/30)-1] = null;
			bgrid[((control.get(0).b2.y)/30)][((control.get(0).b2.x)/30)-1] = null;
			bgrid[((control.get(0).b3.y)/30)][((control.get(0).b3.x)/30)-1] = null;	
			bgrid[((control.get(0).b4.y)/30)][((control.get(0).b4.x)/30)-1] = null;
			bgrid[((control.get(0).b1.y)/30)][((control.get(0).b1.x)/30)] = control.get(0).b1;
			
			bgrid[((control.get(0).b2.y)/30)][((control.get(0).b2.x)/30)] = control.get(0).b2;
			
			bgrid[((control.get(0).b3.y)/30)][((control.get(0).b3.x)/30)] = control.get(0).b3;
			
			bgrid[((control.get(0).b4.y)/30)][((control.get(0).b4.x)/30)] = control.get(0).b4;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

## Contributing
Pull requests are welcome. Contributors, John Paday, Matthew Rierdan, Kelly Tran. 
