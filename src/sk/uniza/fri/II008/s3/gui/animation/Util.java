package sk.uniza.fri.II008.s3.gui.animation;

public class Util {
	static public double clip(double value, double minimum, double maximum)
	{
		if(value < minimum)
		{
			return minimum;
		}
		
		if(value > maximum)
		{
			return maximum;
		}
		
		return value;
	}
}
