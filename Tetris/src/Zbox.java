import java.awt.Color;
//draw zbox
public class Zbox extends Active{
	
	public Zbox(int x, int y,Color c) {
		super(x,y,c);
	}
	public void BlockMaker() {
		b1 = new Blocks(x,y,c);
		b2 = new Blocks(x-30,y,c);
		b3 = new Blocks(x,y+30,c);
		b4 = new Blocks(x+30,y+30,c);
	}
}
