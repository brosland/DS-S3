package sk.uniza.fri.II008.s3.gui.animation;

public class ScreenPosition {
	private int x;
	private int y;
	
	public ScreenPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	@Override
	public String toString() {
		return "SP["+x+", "+y+"]";
	}
}
