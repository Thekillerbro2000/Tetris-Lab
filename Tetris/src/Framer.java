import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

import java.io.FileWriter; // Import the FileWriter class
import java.io.IOException; // Import the IOException class to handle errors

import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Framer extends JPanel implements ActionListener, MouseListener, KeyListener {
	static boolean[][] noActgrid;
	public static Blocks[][] bgrid;
	public static Blocks[][] pgrid;
	public static Blocks[][] ggrid;
	public static int delay = 1000;
	TetrisGrid bg = new TetrisGrid(0, 0);
	ArrayList<Active> control = new ArrayList<Active>();
	ArrayList<Active> prep = new ArrayList<Active>();
	ArrayList<Active> ghost = new ArrayList<Active>();
	private Random rnd = new Random();
	Square square;
	private static int score = 0;
	private static int countBlocks = 0;
	private static int Level = 1;
	private static int speed = 0;
	private boolean isGameOver = false;
	private String pblock;
	private static Timer t;
	private static File myObj;
	private static File myObj2;
	private static boolean isPaused;
	private static int mins;
	private static int secs;
	private static ArrayList<String> keepScores;
	private static ArrayList<Integer> highScore;
	Font f1 = new Font(Font.SERIF, Font.PLAIN, 50);
	Font f2 = new Font(Font.SERIF, Font.PLAIN, 25);
	Font f3 = new Font(Font.SERIF, Font.PLAIN, 100);
	Font f4 = new Font(Font.SERIF, Font.PLAIN, 75);
	static Music Tetris = new Music("TM.wav",false);
	static int counter = 0;
	static int test = 0;
	
	
	ActionListener taskPerformer = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			dropCheck();
		}
	};
	ActionListener taskPerformer2 = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			counter++;
			if(counter %84 == 0 && counter != 0) {
				Tetris.play();	
				
			}
		}
	};
	Timer x = new Timer(delay, taskPerformer);
	Timer time = new Timer(1000,taskPerformer2);
	//draw the score, paint, grid
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		mins = counter/60;
		secs = counter%60;
		
		if(test < counter) {
			System.out.println(counter + " seconds have passed");
		}
		test = counter;
		
		if (!noActgrid[4][4] && !noActgrid[4][5]) {

			bg.paint(g);
			prep.get(0).paint(g);
			for (int r = bgrid.length - 1; r >= 0; r--) {
				for (int c = 0; c < bgrid[r].length; c++) {
					
					if(ggrid[r][c] != null) {
						ggrid[r][c].ghostpaints(g);
					}
					if (bgrid[r][c] != null) {
						bgrid[r][c].paints(g);

					}

				}
			}

			g.setFont(f1);
			g.setColor(Color.yellow);

			g.setFont(f2);
			g.drawString("score: " + score, 350, 120);
			g.drawString("level:" + Level, 350, 150);
			g.drawString("blocks:" + countBlocks, 350, 180);
			g.drawString("speed:" + (((1000 - delay) / 10) + 1), 350, 210);
			if(secs < 10) {
			g.drawString("time:" + mins+ ".0" + secs, 350, 240);
			}else {
			g.drawString("time:" + mins+ "." + secs, 350, 240);
			}
			g.drawString("Press Space to Pause", 350, 270);
			g.setColor(Color.black);
			g.fillRect(0, 0, 301, 115);
			g.setColor(Color.yellow);
			g.setFont(f1);

			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 115, 300, 30);
			g.fillRect(300, 115, 30, 615);
			g.setColor(Color.GRAY);

			for (int i = 115; i < 720; i += 30) {
				g.setColor(Color.GRAY);
				g.fillRect(300, i, 30, 30);
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(305, i + 5, 20, 20);
			}
			for (int i = 0; i < 300; i += 30) {
				g.setColor(Color.GRAY);
				g.fillRect(i, 115, 30, 30);
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(i + 5, 120, 20, 20);
			}
			g.setColor(Color.yellow);
			g.setFont(f3);
			g.drawString("Tetris", 40, 100);
			g.setFont(f2);
			g.drawString("Next block: " + pblock, 350, 480);
			
		} else {

			gameOver(g);
			time.stop();
			counter = 0;
			t.stop();

		}
		
		if(isPaused) {
			pause(g);
		}
	}
	
	private void pause(Graphics g) {
		t.stop();
		g.setColor(Color.black);
		g.fillRect(0, 0, 1000, 1000);
		g.setColor(Color.yellow);
		g.setFont(f3);
		g.drawString("PAUSED", 100, 150);
		g.setFont(f2);
		g.drawString("Press Enter to Resume", 170,200 );
		time.stop();
		g.drawString("Current score: " + score, 100, 330);
		g.drawString("Current level:" + Level, 100, 360);
		g.drawString("HighScores:",175,460);
		for (int i = 0; i < 5; i++) {
			System.out.println(i + 1 + ": " + highScore.get(i));
			g.drawString((i+1)+ ": " + highScore.get(i),175,500+(30*i));
		}
		
	}
	
	private void gameOver(Graphics g) {

		g.setColor(Color.black);
		g.fillRect(0, 0, 1000, 1000);
		g.setColor(Color.yellow);
		g.setFont(f3);
		g.drawString("GAME", 150, 120);
		g.drawString("OVER", 150, 200);
		g.setFont(f4);
		g.drawString("score: " + score, 0, 320);
		g.setFont(f2);
		g.drawString("Press Space to Start Over", 170, 400);
		isGameOver = true;

		try {
			FileWriter myWriter = new FileWriter("Test15.txt");
			FileWriter myWriter3 = new FileWriter("highscore6.txt");
			FileWriter myWriter2 = new FileWriter("david.txt");

			for (int i = 0; i < 5; i++) {
				myWriter2.write("" + i + "\n");
			}
			myWriter2.close();
			highScore.add(score);
			keepScores.add("" + score);
			for (int x = 0; x < keepScores.size(); x++) {

				myWriter.write("Player: " + (x + 1) + " " + keepScores.get(x) + "\n");

			}
			myWriter.close();
			int b = 0;
			for (int x = 0; x < highScore.size() - 1; x++) {
				for (int c = x+1; c < highScore.size(); c++) {

					if (highScore.get(c) > highScore.get(x)) {
						b = highScore.get(x);
						highScore.set(x, highScore.get(c));
						highScore.set(c, b);
					}
				}

			}
			System.out.println("Highscores:");
			for (int i = 0; i < highScore.size(); i++) {
				myWriter3.write(highScore.get(i)+""+"\n");
				
			}
			myWriter3.close();
			g.drawString("HighScores:",175,460);
			for (int i = 0; i < 5; i++) {
System.out.println(i + 1 + ": " + highScore.get(i));
g.drawString((i+1)+ ": " + highScore.get(i),175,500+(30*i));
			}

			System.out.println("Successfully wrote to highscore6");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		try {

			Scanner myReader = new Scanner(myObj);

			while (myReader.hasNextLine()) {

				String data = myReader.nextLine();
				System.out.println(data);

			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}

	public void startOver() {
		for (int r = bgrid.length - 1; r >= 0; r--) {
			for (int c = 0; c < bgrid[r].length; c++) {
				bgrid[r][c] = null;

			}
		}
		for (int r = 0; r < noActgrid.length; r++) {
			for (int c = 0; c < noActgrid[0].length; c++) {
				noActgrid[r][c] = false;

			}
		}
		score = 0;
		Level = 1;
		countBlocks = 0;
		speed = 0;
		isGameOver = false;
		prepper();
		spawn();
		mins = 0;
		secs = 0;
		t.start();
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tetris.play();
		
		keepScores = new ArrayList<String>();
		highScore = new ArrayList<Integer>();
		try {
			myObj = new File("Test15.txt");

			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		try {
			myObj2 = new File("highscore6.txt");

			if (myObj2.createNewFile()) {
				System.out.println("File created: " + myObj2.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		try {

			Scanner myReader = new Scanner(myObj);
			Scanner myReader2 = new Scanner(myObj2);
			int count = 0;
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				
				
				// System.out.println(data);
				count++;
				if(count/100 != 0) {
				data = data.substring(12);	
				}else if(count/10 != 0) {
				data = data.substring(11);		
				}else {
				data = data.substring(10);
				}
				keepScores.add(data);
			}
			while (myReader2.hasNextInt()) {
				int datas = myReader2.nextInt();
				// System.out.println(data);

				highScore.add(datas);
			}
			myReader.close();
			myReader2.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		noActgrid = new boolean[24][10];
		bgrid = new Blocks[24][10];
		ggrid = new Blocks[24][10];
		pgrid = new Blocks[4][4];
		for (int r = bgrid.length - 1; r >= 0; r--) {
			for (int c = 0; c < bgrid[r].length; c++) {
				bgrid[r][c] = null;

			}
		}
		Framer f = new Framer();

	}

	private void levelUp() {
		x.stop();
		Level += score / (1000 * Level);
		if (delay > 100) {
			delay = 1000 - (Level * 40) + 40;
		}
		x = new Timer(delay, taskPerformer);
		x.start();
	}
	//get the active block and divide it by 30 so it will not go off the grid and +1 to check the one below the y axis
		//check all the blocks below the active block and make sure it does not leave the grid
	public void dropCheck() {
		
		

		if (((control.get(0).b1.y) / 30) + 1 != bgrid.length && ((control.get(0).b2.y) / 30) + 1 != bgrid.length
				&& ((control.get(0).b3.y) / 30) + 1 != bgrid.length
				&& ((control.get(0).b4.y) / 30) + 1 != bgrid.length) {

			if (noActgrid[((control.get(0).b1.y) / 30) + 1][((control.get(0).b1.x) / 30)] != true
					&& noActgrid[((control.get(0).b2.y) / 30) + 1][((control.get(0).b2.x) / 30)] != true
					&& noActgrid[((control.get(0).b3.y) / 30) + 1][((control.get(0).b3.x) / 30)] != true
					&& noActgrid[((control.get(0).b4.y) / 30) + 1][((control.get(0).b4.x) / 30)] != true) {
				ActiveDropper();
				levelUp();
			} else {
				noActgrid[((control.get(0).b1.y) / 30)][((control.get(0).b1.x) / 30)] = true;
				noActgrid[((control.get(0).b2.y) / 30)][((control.get(0).b2.x) / 30)] = true;
				noActgrid[((control.get(0).b3.y) / 30)][((control.get(0).b3.x) / 30)] = true;
				noActgrid[((control.get(0).b4.y) / 30)][((control.get(0).b4.x) / 30)] = true;
				control.remove(0);
				ghost.remove(0);
				levelUp();
				rowChecker();
				spawn();

			}
		} else {
			noActgrid[((control.get(0).b1.y) / 30)][((control.get(0).b1.x) / 30)] = true;
			noActgrid[((control.get(0).b2.y) / 30)][((control.get(0).b2.x) / 30)] = true;
			noActgrid[((control.get(0).b3.y) / 30)][((control.get(0).b3.x) / 30)] = true;
			noActgrid[((control.get(0).b4.y) / 30)][((control.get(0).b4.x) / 30)] = true;
			control.remove(0);
			ghost.remove(0);
			levelUp();
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
		bgrid[((control.get(0).b1.y) / 30) - 1][((control.get(0).b1.x) / 30)] = null;
		bgrid[((control.get(0).b2.y) / 30) - 1][((control.get(0).b2.x) / 30)] = null;
		bgrid[((control.get(0).b3.y) / 30) - 1][((control.get(0).b3.x) / 30)] = null;
		bgrid[((control.get(0).b4.y) / 30) - 1][((control.get(0).b4.x) / 30)] = null;
		bgrid[((control.get(0).b1.y) / 30)][((control.get(0).b1.x) / 30)] = control.get(0).b1;
		bgrid[((control.get(0).b2.y) / 30)][((control.get(0).b2.x) / 30)] = control.get(0).b2;
		bgrid[((control.get(0).b3.y) / 30)][((control.get(0).b3.x) / 30)] = control.get(0).b3;
		bgrid[((control.get(0).b4.y) / 30)][((control.get(0).b4.x) / 30)] = control.get(0).b4;

	}

	public Framer() {
		prepper();
		spawn();
		JFrame f = new JFrame("Tetris");
		f.setSize(new Dimension(614, 750));
		f.setBackground(Color.blue);
		f.add(this);
		f.setResizable(false);
		f.setLayout(new GridLayout(1, 2));
		f.addMouseListener(this);
		f.addKeyListener(this);

		x.start();
		t = new Timer(20, this);
		t.start();
		time.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		

	}

	private void prepper() {

		int r = (int) (Math.random() * 7);

		if (r == 0) {
			pblock = "Straight piece";
			prep.add(new Straight(430, 530, Color.red));
		} else if (r == 1) {
			pblock = "Square piece";
			prep.add(new Square(430, 560, Color.cyan));
		} else if (r == 2) {
			pblock = "Z piece";
			prep.add(new Zbox(460, 530, Color.green));
		} else if (r == 3) {
			pblock = "S piece";
			prep.add(new SBox(430, 530, Color.yellow));
		} else if (r == 4) {
			pblock = "T piece";
			prep.add(new Tbox(430, 530, Color.ORANGE));
		} else if (r == 5) {
			pblock = "L piece";
			prep.add(new Lbox(430, 530, Color.MAGENTA));
		} else {
			pblock = "J piece";
			prep.add(new Jbox(460, 530, Color.LIGHT_GRAY));
		}

	}

	public void spawn() {
		
		countBlocks++;
		if (pblock.equals("Straight piece")) {
			Active straight = new Straight(120, 30, Color.red);//set Straight line to red 
			control.add(straight);
			Active gstraight = new Straight(120, 30, Color.red);//set ghost Straight line to red 
			ghost.add(gstraight);
		} else if (pblock.equals("Square piece")) {
			square = new Square(120, 90, Color.cyan);//set Cyan to Square
			control.add(square);
			Active gsquare = new Straight(120, 90, Color.cyan); //set ghost Cyan to Square
			ghost.add(gsquare);
		} else if (pblock.equals("Z piece")) {
			Active z = new Zbox(120, 60, Color.green); //set Zbox to green
			control.add(z);
			Active gz = new Straight(120, 60, Color.green); //set ghost Zbox to green
			ghost.add(gz);
		} else if (pblock.equals("S piece")) {
			Active s = new SBox(120, 60, Color.yellow); //set Sbox to yellow
			control.add(s);
			Active gs = new Straight(120, 60, Color.yellow); //set ghost Sbox to yellow
			ghost.add(gs);
		} else if (pblock.equals("T piece")) {
			Active t = new Tbox(120, 60, Color.ORANGE);//set Tbox to orange
			control.add(t);
			Active gt = new Straight(120, 60, Color.ORANGE);//set ghost Tbox to orange
			ghost.add(gt);
		} else if (pblock.equals("L piece")) {
			Active l = new Lbox(120, 60, Color.MAGENTA);//set Lbox to magneta
			control.add(l);
			Active gl = new Straight(120, 60, Color.MAGENTA);//set ghostLbox to magneta
			ghost.add(gl);
		} else {
			Active j = new Jbox(120, 60, Color.LIGHT_GRAY);//set Jbox to Lightgray
			control.add(j);
			Active gj = new Straight(120, 60, Color.LIGHT_GRAY); //set ghost Jbox to Lightgray
			ghost.add(gj);
		}
		bgrid[((control.get(0).b1.y) / 30)][((control.get(0).b1.x) / 30)] = control.get(0).b1;
		bgrid[((control.get(0).b2.y) / 30)][((control.get(0).b2.x) / 30)] = control.get(0).b2;
		bgrid[((control.get(0).b3.y) / 30)][((control.get(0).b3.x) / 30)] = control.get(0).b3;
		bgrid[((control.get(0).b4.y) / 30)][((control.get(0).b4.x) / 30)] = control.get(0).b4;
		prep.remove(0);

		prepper();
		ghostPiece();
	}
	//check the row to see if it has 10 blocks in one row or not
	public void rowChecker() {
		int sum = 0;
		int start = 0;
		boolean consecutive = false;
		for (int r = bgrid.length - 1; r >= 0; r--) {
			for (int c = 0; c < bgrid[r].length; c++) {
				if (noActgrid[r][c] == true) {
					sum++;
				}
			}
			if (sum == 10) {
				if (!consecutive) {
					consecutive = true;
					start = r;
				}

			} else if (sum != 10 && consecutive) {
				consecutive = false;
				dropRows(start, r + 1); //calls the drop row method when consecutive is true which drops all the rows that have been classified as true.
				scoreRows(start - r);
				start = 0;
			}
			sum = 0;
		}

	}
	
	
	//drops the rows when they find 10 blocks in a row
		// Clear completed rows from the field and award score according to
		// the number of simultaneously cleared rows.
	private void dropRows(int start, int end) {

		for (int r = start; r >= end; r--) {
			for (int col = 0; col < bgrid[r].length; col++) {
				bgrid[r][col] = null;
				noActgrid[r][col] = false;
			}
		}
		for (int r = end - 1; r >= 0; r--) {
			for (int col = 0; col < bgrid[r].length; col++) {
				if (bgrid[r][col] != null) {
					bgrid[r + (start - end) + 1][col] = bgrid[r][col];
					bgrid[r][col].y = (r + (start - end) + 1) * 30;
					noActgrid[r + (start - end) + 1][col] = true;
					noActgrid[r][col] = false;
					bgrid[r][col] = null;

				}
			}
		}
	}
	//add score to the game
	public void scoreRows(int rows) {
		switch(rows) {
		//first row that clear add 30
		case 1:
			score += 40 * Level;
			break;
		case 2: //second row that clear add 100
			score += 100 * Level;
			break;
		case 3: //third row that clear add 300
			score += 300 * Level;
			break;
		case 4: //else score will go up by 1200
			score += 1200 * Level;
			break;
		}
	}

	
	
	public void ghostPiece() {
	//needs position of the active piece
	//
		for (int r = ggrid.length - 1; r >= 0; r--) {
			for (int c = 0; c < ggrid[r].length; c++) {
				ggrid[r][c] = null;

			}
		}
		ghost.get(0).b1.y = control.get(0).b1.y;
		ghost.get(0).b2.y = control.get(0).b2.y;
		ghost.get(0).b3.y = control.get(0).b3.y;
		ghost.get(0).b4.y = control.get(0).b4.y;
		ghost.get(0).b1.x = control.get(0).b1.x;
		ghost.get(0).b2.x = control.get(0).b2.x;
		ghost.get(0).b3.x = control.get(0).b3.x;
		ghost.get(0).b4.x = control.get(0).b4.x;
		
		ggrid[((ghost.get(0).b1.y) / 30)][((ghost.get(0).b1.x) / 30)] = ghost.get(0).b1;
		ggrid[((ghost.get(0).b2.y) / 30)][((ghost.get(0).b2.x) / 30)] = ghost.get(0).b2;
		ggrid[((ghost.get(0).b3.y) / 30)][((ghost.get(0).b3.x) / 30)] = ghost.get(0).b3;
		ggrid[((ghost.get(0).b4.y) / 30)][((ghost.get(0).b4.x) / 30)] = ghost.get(0).b4;
		while(((ghost.get(0).b1.y) / 30) + 1 != ggrid.length && ((ghost.get(0).b2.y) / 30) + 1 != ggrid.length
				&& ((ghost.get(0).b3.y) / 30) + 1 != ggrid.length
				&& ((ghost.get(0).b4.y) / 30) + 1 != ggrid.length && noActgrid[((ghost.get(0).b1.y) / 30) + 1][((ghost.get(0).b1.x) / 30)] != true
				&& noActgrid[((ghost.get(0).b2.y) / 30) + 1][((ghost.get(0).b2.x) / 30)] != true
				&& noActgrid[((ghost.get(0).b3.y) / 30) + 1][((ghost.get(0).b3.x) / 30)] != true
				&& noActgrid[((ghost.get(0).b4.y) / 30) + 1][((ghost.get(0).b4.x) / 30)] != true) {
			ghost.get(0).b1.y += 30;
			ghost.get(0).b2.y += 30;
			ghost.get(0).b3.y += 30;
			ghost.get(0).b4.y += 30;
			ggrid[((ghost.get(0).b1.y) / 30) - 1][((ghost.get(0).b1.x) / 30)] = null;
			ggrid[((ghost.get(0).b2.y) / 30) - 1][((ghost.get(0).b2.x) / 30)] = null;
			ggrid[((ghost.get(0).b3.y) / 30) - 1][((ghost.get(0).b3.x) / 30)] = null;
			ggrid[((ghost.get(0).b4.y) / 30) - 1][((ghost.get(0).b4.x) / 30)] = null;
			ggrid[((ghost.get(0).b1.y) / 30)][((ghost.get(0).b1.x) / 30)] = ghost.get(0).b1;
			ggrid[((ghost.get(0).b2.y) / 30)][((ghost.get(0).b2.x) / 30)] = ghost.get(0).b2;
		    ggrid[((ghost.get(0).b3.y) / 30)][((ghost.get(0).b3.x) / 30)] = ghost.get(0).b3;
			ggrid[((ghost.get(0).b4.y) / 30)][((ghost.get(0).b4.x) / 30)] = ghost.get(0).b4;
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
		int q, w, e, r, y, u, i;
		q = (((control.get(0).b2.y) / 30) - ((control.get(0).b1.y) / 30));
		w = (((control.get(0).b2.x) / 30) - ((control.get(0).b1.x) / 30));
		e = (((control.get(0).b3.y) / 30) - ((control.get(0).b1.y) / 30));
		r = (((control.get(0).b3.x) / 30) - ((control.get(0).b1.x) / 30));
		u = (((control.get(0).b4.y) / 30) - ((control.get(0).b1.y) / 30));
		i = (((control.get(0).b4.x) / 30) - ((control.get(0).b1.x) / 30));
		if (arg0.getKeyCode() == 38 && control.get(0) != square
				&& noActgrid[((control.get(0).b1.y) / 30) - w][((control.get(0).b1.x) / 30) + q] == false
				&& noActgrid[((control.get(0).b1.y) / 30) - r][((control.get(0).b1.x) / 30) + e] == false
				&& noActgrid[((control.get(0).b1.y) / 30) - i][((control.get(0).b1.x) / 30) + u] == false && !isPaused) {
			bgrid[((control.get(0).b2.y) / 30)][((control.get(0).b2.x) / 30)] = null;
			bgrid[((control.get(0).b3.y) / 30)][((control.get(0).b3.x) / 30)] = null;
			bgrid[((control.get(0).b4.y) / 30)][((control.get(0).b4.x) / 30)] = null;
			(control.get(0).b2.y) = (control.get(0).b1.y) - (w * 30);
			(control.get(0).b2.x) = (control.get(0).b1.x) + (q * 30);
			bgrid[((control.get(0).b1.y) / 30) - w][((control.get(0).b1.x) / 30) + q] = control.get(0).b2;

			(control.get(0).b3.y) = (control.get(0).b1.y) - (r * 30);
			(control.get(0).b3.x) = (control.get(0).b1.x) + (e * 30);
			bgrid[((control.get(0).b1.y) / 30) - r][((control.get(0).b1.x) / 30) + e] = control.get(0).b3;

			(control.get(0).b4.y) = (control.get(0).b1.y) - (i * 30);
			(control.get(0).b4.x) = (control.get(0).b1.x) + (u * 30);
			bgrid[((control.get(0).b1.y) / 30) - i][((control.get(0).b1.x) / 30) + u] = control.get(0).b4;
			ghostPiece();
		}
		//down arrows key
		if (arg0.getKeyCode() == 40 && control.get(0).getb1().y <= 660 && control.get(0).b2.y <= 660
				&& control.get(0).b3.y <= 660 && control.get(0).b4.y <= 660 && !isPaused) {
			dropCheck();

		}
		if (arg0.getKeyCode() == 32 && !isGameOver) {
			isPaused = true;
		}
		if (arg0.getKeyCode() == 32 && isGameOver) {
			startOver();
		}
		
		if (arg0.getKeyCode() == 10 && !isGameOver) {
			isPaused = false;
			t.start();
			time.start();
			
		}
		if(arg0.getKeyCode() == 84 && !isPaused) {
			while(((control.get(0).b1.y) / 30) + 1 != bgrid.length && ((control.get(0).b2.y) / 30) + 1 != bgrid.length
					&& ((control.get(0).b3.y) / 30) + 1 != bgrid.length
					&& ((control.get(0).b4.y) / 30) + 1 != bgrid.length && noActgrid[((control.get(0).b1.y) / 30) + 1][((control.get(0).b1.x) / 30)] != true
					&& noActgrid[((control.get(0).b2.y) / 30) + 1][((control.get(0).b2.x) / 30)] != true
					&& noActgrid[((control.get(0).b3.y) / 30) + 1][((control.get(0).b3.x) / 30)] != true
					&& noActgrid[((control.get(0).b4.y) / 30) + 1][((control.get(0).b4.x) / 30)] != true) {
				control.get(0).b1.y += 30;
				control.get(0).b2.y += 30;
				control.get(0).b3.y += 30;
				control.get(0).b4.y += 30;
				bgrid[((control.get(0).b1.y) / 30) - 1][((control.get(0).b1.x) / 30)] = null;
				bgrid[((control.get(0).b2.y) / 30) - 1][((control.get(0).b2.x) / 30)] = null;
				bgrid[((control.get(0).b3.y) / 30) - 1][((control.get(0).b3.x) / 30)] = null;
				bgrid[((control.get(0).b4.y) / 30) - 1][((control.get(0).b4.x) / 30)] = null;
				bgrid[((control.get(0).b1.y) / 30)][((control.get(0).b1.x) / 30)] = control.get(0).b1;
				bgrid[((control.get(0).b2.y) / 30)][((control.get(0).b2.x) / 30)] = control.get(0).b2;
			    bgrid[((control.get(0).b3.y) / 30)][((control.get(0).b3.x) / 30)] = control.get(0).b3;
				bgrid[((control.get(0).b4.y) / 30)][((control.get(0).b4.x) / 30)] = control.get(0).b4;	
			
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		//move left and right
		System.out.println(arg0.getKeyCode());
		if (arg0.getKeyCode() == 37 && control.get(0).getb1().x != 0 && control.get(0).b2.x != 0
				&& control.get(0).b3.x != 0 && control.get(0).b4.x != 0
				&& noActgrid[((control.get(0).b2.y) / 30)][((control.get(0).b2.x) / 30) - 1] == false
				&& noActgrid[((control.get(0).b3.y) / 30)][((control.get(0).b3.x) / 30) - 1] == false
				&& noActgrid[((control.get(0).b4.y) / 30)][((control.get(0).b4.x) / 30) - 1] == false
				&& noActgrid[((control.get(0).b1.y) / 30)][((control.get(0).b1.x) / 30) - 1] == false && !isPaused) {
			control.get(0).b1.x -= 30;
			control.get(0).b2.x -= 30;
			control.get(0).b3.x -= 30;
			control.get(0).b4.x -= 30;
			bgrid[((control.get(0).b1.y) / 30)][((control.get(0).b1.x) / 30) + 1] = null;
			bgrid[((control.get(0).b2.y) / 30)][((control.get(0).b2.x) / 30) + 1] = null;
			bgrid[((control.get(0).b3.y) / 30)][((control.get(0).b3.x) / 30) + 1] = null;
			bgrid[((control.get(0).b4.y) / 30)][((control.get(0).b4.x) / 30) + 1] = null;
			bgrid[((control.get(0).b1.y) / 30)][((control.get(0).b1.x) / 30)] = control.get(0).b1;

			bgrid[((control.get(0).b2.y) / 30)][((control.get(0).b2.x) / 30)] = control.get(0).b2;

			bgrid[((control.get(0).b3.y) / 30)][((control.get(0).b3.x) / 30)] = control.get(0).b3;

			bgrid[((control.get(0).b4.y) / 30)][((control.get(0).b4.x) / 30)] = control.get(0).b4;
			
		
			ghostPiece();

		}
		//tests all the blocks to make sure it doesn't go into any other blocks are goes outside of the grid.
		if (arg0.getKeyCode() == 39 && control.get(0).b1.x != 270 && control.get(0).b2.x != 270
				&& control.get(0).b3.x != 270 && control.get(0).b4.x != 270
				&& noActgrid[((control.get(0).b2.y) / 30)][((control.get(0).b2.x) / 30) + 1] == false
				&& noActgrid[((control.get(0).b3.y) / 30)][((control.get(0).b3.x) / 30) + 1] == false
				&& noActgrid[((control.get(0).b4.y) / 30)][((control.get(0).b4.x) / 30) + 1] == false
				&& noActgrid[((control.get(0).b1.y) / 30)][((control.get(0).b1.x) / 30) + 1] == false && !isPaused) {
			control.get(0).b1.x += 30;
			control.get(0).b2.x += 30;
			control.get(0).b3.x += 30;
			control.get(0).b4.x += 30;
			bgrid[((control.get(0).b1.y) / 30)][((control.get(0).b1.x) / 30) - 1] = null;
			bgrid[((control.get(0).b2.y) / 30)][((control.get(0).b2.x) / 30) - 1] = null;
			bgrid[((control.get(0).b3.y) / 30)][((control.get(0).b3.x) / 30) - 1] = null;
			bgrid[((control.get(0).b4.y) / 30)][((control.get(0).b4.x) / 30) - 1] = null;
			bgrid[((control.get(0).b1.y) / 30)][((control.get(0).b1.x) / 30)] = control.get(0).b1;

			bgrid[((control.get(0).b2.y) / 30)][((control.get(0).b2.x) / 30)] = control.get(0).b2;

			bgrid[((control.get(0).b3.y) / 30)][((control.get(0).b3.x) / 30)] = control.get(0).b3;

			bgrid[((control.get(0).b4.y) / 30)][((control.get(0).b4.x) / 30)] = control.get(0).b4;
			
			
			ghostPiece();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
