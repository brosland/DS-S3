package sk.uniza.fri.II008.s3.gui.animation;

public class FactoryPosition
{
	static public float getDistance(FactoryPosition from, FactoryPosition to) {
		float dx = from.getX()-to.getX();
		float dy = from.getY()-to.getY();
		
		return (float)Math.sqrt(dx*dx+dy*dy);
	}
	
	private float x;
	private float y;
	
	public FactoryPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public FactoryPosition add(float x, float y)
	{
		return new FactoryPosition(this.x+x, this.y+y);
	}
	
	@Override
	public String toString() {
		return "FP["+x+", "+y+"]";
	}
}
