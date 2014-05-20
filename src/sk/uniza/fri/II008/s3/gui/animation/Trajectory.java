package sk.uniza.fri.II008.s3.gui.animation;

public interface Trajectory {
	/**
	 * 
	 * @param coeficient <0; 1> where 0 indicates position at the start and 1 indicates position at the end
	 * @return interpolated position
	 */
	public FactoryPosition getInterpolated(float coeficient);
	
	public float getLength();
}
