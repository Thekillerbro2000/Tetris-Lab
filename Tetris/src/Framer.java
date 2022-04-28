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
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		bg.paint(g);
		//(control.get(0)).paint(g);
		//System.out.println("" + );
		for(int r = bgrid.length-1; r>= 0;r--) {
			for(int c = 0; c < bgrid[r].length;c++) {
				if(bgrid[r][c] != null) {
				bgrid[r][c].paints(g);
				}
				
			}
		}

	//System.out.println(control.get(0).b4.y);	
		
	
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
	
	public void dropCheck() {
		ArrayList<Blocks> low = new ArrayList<Blocks>();
		//if()
		if(((control.get(0).b1.y)/30)+1 != bgrid.length && ((control.get(0).b2.y)/30)+1 != bgrid.length && ((control.get(0).b3.y)/30)+1 != bgrid.length && ((control.get(0).b4.y)/30)+1 != bgrid.length ) {
			
			if(noActgrid[((control.get(0).getb1().y)/30)+1][((control.get(0).getb1().x)/30)] != true && 
			noActgrid[((control.get(0).b2.y)/30)+1][((control.get(0).b2.x)/30)] != true &&
			noActgrid[((control.get(0).b3.y)/30)+1][((control.get(0).b3.x)/30)] != true &&
			noActgrid[((control.get(0).b4.y)/30)+1][((control.get(0).b4.x)/30)] != true) {
				ActiveDropper();
			}else {
			noActgrid[((control.get(0).getb1().y)/30)][((control.get(0).getb1().x)/30)] = true; 
			noActgrid[((control.get(0).b2.y)/30)][((control.get(0).b2.x)/30)] = true; 
			noActgrid[((control.get(0).b3.y)/30)][((control.get(0).b3.x)/30)] = true; 
			noActgrid[((control.get(0).b4.y)/30)][((control.get(0).b4.x)/30)] = true;
			control.remove(0);
			spawn();
				
			}
		}
		else {
			noActgrid[((control.get(0).getb1().y)/30)][((control.get(0).getb1().x)/30)] = true; 
			noActgrid[((control.get(0).b2.y)/30)][((control.get(0).b2.x)/30)] = true; 
			noActgrid[((control.get(0).b3.y)/30)][((control.get(0).b3.x)/30)] = true; 
			noActgrid[((control.get(0).b4.y)/30)][((control.get(0).b4.x)/30)] = true;
			control.remove(0);
		
			rowChecker();
			spawn();
			
		}
	}
	
	public void ActiveDropper() {
	control.get(0).getb1().y += 30;
	bgrid[((control.get(0).getb1().y)/30)][((control.get(0).getb1().x)/30)] = control.get(0).getb1();	
	control.get(0).b2.y += 30;
	bgrid[((control.get(0).b2.y)/30)][((control.get(0).b2.x)/30)] = control.get(0).b2;	
	control.get(0).b3.y += 30;
	bgrid[((control.get(0).b3.y)/30)][((control.get(0).b3.x)/30)] = control.get(0).b3;	
	control.get(0).b4.y += 30;
	bgrid[((control.get(0).b4.y)/30)][((control.get(0).b4.x)/30)] = control.get(0).b4;	
	
	}
	 ActionListener taskPerformer = new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {
	         dropCheck();
	      }
	  };
 
	
	public Framer() {
		JFrame f = new JFrame("Tetris");
		f.setSize(new Dimension(307, 600));
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
	
	
	public void spawn() {
	int r= rnd.nextInt(7);

	if(r == 0) {
	Active straight = new Straight(120,30,Color.red);
	control.add(straight);
	}else if(r==1) {
	square = new Square(120,30,Color.cyan);	
	control.add(square);
	}else if(r==2) {
	Active z = new Zbox(120,0,Color.green);
	control.add(z);
	}else if(r==3) {
	Active s = new SBox(120,0,Color.yellow);
	control.add(s);
	}else if(r==4) {
	Active t = new Tbox(120,0,Color.ORANGE);
	control.add(t);
	}else if(r==5) {
	Active l = new Lbox(120,30,Color.MAGENTA);
	control.add(l);
	}else {
	Active j = new Jbox(120,30,Color.LIGHT_GRAY);	
	control.add(j);
	}
	bgrid[((control.get(0).getb1().y)/30)][((control.get(0).getb1().x)/30)] = control.get(0).getb1(); 
	bgrid[((control.get(0).b2.y)/30)][((control.get(0).b2.x)/30)] = control.get(0).b2; 
	bgrid[((control.get(0).b3.y)/30)][((control.get(0).b3.x)/30)] = control.get(0).b3; 
	bgrid[((control.get(0).b4.y)/30)][((control.get(0).b4.x)/30)] = control.get(0).b4;
	
	}
	
	
	public void rowChecker() {
		
	for(int c = 0; c<bgrid[0].length; c++ ) {
		bgrid[18][c] = null;
	}
		
		
		
		
		
	}
	
	public void scoreRows(int rows) {
		
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
		int q , w,e,r,t,y,u,i;
		q= (((control.get(0).b2.y)/30) - ((control.get(0).b1.y)/30));
		w= (((control.get(0).b2.x)/30) - ((control.get(0).b1.x)/30));
		e= (((control.get(0).b3.y)/30) - ((control.get(0).b1.y)/30));
		r= (((control.get(0).b3.x)/30) - ((control.get(0).b1.x)/30));
		u= (((control.get(0).b4.y)/30) - ((control.get(0).b1.y)/30));
		i= (((control.get(0).b4.x)/30) - ((control.get(0).b1.x)/30));
		if(arg0.getKeyCode() == 38 && control.get(0) != square && noActgrid[((control.get(0).b1.y)/30)-w][((control.get(0).b1.x)/30)+q] == false && noActgrid[((control.get(0).b1.y)/30)-r][((control.get(0).b1.x)/30)+e] == false && noActgrid[((control.get(0).b1.y)/30)-i][((control.get(0).b1.x)/30)+u] == false) {
		bgrid[((control.get(0).b2.y)/30)][((control.get(0).b2.x)/30)] = null;
		
		(control.get(0).b2.y) = (control.get(0).b1.y) - (w*30);
		(control.get(0).b2.x) = (control.get(0).b1.x) + (q*30);
		bgrid[((control.get(0).b1.y)/30)-w][((control.get(0).b1.x)/30)+q] = control.get(0).b2;
		bgrid[((control.get(0).b3.y)/30)][((control.get(0).b3.x)/30)] = null;
		
		(control.get(0).b3.y) = (control.get(0).b1.y) - (r*30);
		(control.get(0).b3.x) = (control.get(0).b1.x) + (e*30);
		bgrid[((control.get(0).b1.y)/30)-r][((control.get(0).b1.x)/30)+e] = control.get(0).b3;
		bgrid[((control.get(0).b4.y)/30)][((control.get(0).b4.x)/30)] = null;
		
		(control.get(0).b4.y) = (control.get(0).b1.y) - (i*30);
		(control.get(0).b4.x) = (control.get(0).b1.x) + (u*30);
		bgrid[((control.get(0).b1.y)/30)-i][((control.get(0).b1.x)/30)+u] = control.get(0).b4;
				
		
		
		}
		if(arg0.getKeyCode() == 40 && control.get(0).getb1().y <=510 &&  control.get(0).b2.y <=510  && control.get(0).b3.y <=510  && control.get(0).b4.y <=510) {
			dropCheck();
		
		}
			
		if(arg0.getKeyCode() == 84) {
			rowChecker();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getKeyCode());
		if(arg0.getKeyCode() == 37 && control.get(0).getb1().x !=0 &&  control.get(0).b2.x !=0  && control.get(0).b3.x !=0  && control.get(0).b4.x !=0 && noActgrid[((control.get(0).b2.y)/30)][((control.get(0).b2.x)/30)-1] == false &&  noActgrid[((control.get(0).b3.y)/30)][((control.get(0).b3.x)/30)-1] == false &&  noActgrid[((control.get(0).b4.y)/30)][((control.get(0).b4.x)/30)-1] == false && noActgrid[((control.get(0).b1.y)/30)][((control.get(0).b1.x)/30)-1] == false) {
			control.get(0).b1.x -=30;
			bgrid[((control.get(0).getb1().y)/30)][((control.get(0).getb1().x)/30)] = control.get(0).getb1();	
			control.get(0).b2.x -=30;
			bgrid[((control.get(0).b2.y)/30)][((control.get(0).b2.x)/30)] = control.get(0).b2;	
			control.get(0).b3.x -=30;
			bgrid[((control.get(0).b3.y)/30)][((control.get(0).b3.x)/30)] = control.get(0).b3;	
			control.get(0).b4.x -=30;
			bgrid[((control.get(0).b4.y)/30)][((control.get(0).b4.x)/30)] = control.get(0).b4;	
		}
		if(arg0.getKeyCode() == 39 && control.get(0).getb1().x !=270 &&  control.get(0).b2.x !=270  && control.get(0).b3.x !=270  && control.get(0).b4.x !=270 && noActgrid[((control.get(0).b2.y)/30)][((control.get(0).b2.x)/30)+1] == false &&  noActgrid[((control.get(0).b3.y)/30)][((control.get(0).b3.x)/30)+1] == false &&  noActgrid[((control.get(0).b4.y)/30)][((control.get(0).b4.x)/30)+1] == false && noActgrid[((control.get(0).b1.y)/30)][((control.get(0).b1.x)/30)+1] == false) {
			control.get(0).b1.x +=30;
			bgrid[((control.get(0).getb1().y)/30)][((control.get(0).getb1().x)/30)] = control.get(0).getb1();	
			control.get(0).b2.x +=30;
			bgrid[((control.get(0).b2.y)/30)][((control.get(0).b2.x)/30)] = control.get(0).b2;	
			control.get(0).b3.x +=30;
			bgrid[((control.get(0).b3.y)/30)][((control.get(0).b3.x)/30)] = control.get(0).b3;	
			control.get(0).b4.x +=30;
			bgrid[((control.get(0).b4.y)/30)][((control.get(0).b4.x)/30)] = control.get(0).b4;	
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
