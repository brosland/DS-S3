package sk.uniza.fri.II008.s3.gui.animation;

public class DirectTrajectory implements Trajectory
{
	private FactoryPosition from;
	private FactoryPosition to;
	
	public DirectTrajectory(FactoryPosition from, FactoryPosition to)
	{
		this.from = from;
		this.to = to;
	}

	@Override
	public FactoryPosition getInterpolated(float coeficient)
	{
		return new FactoryPosition(
			from.getX()*coeficient + to.getX()*(1-coeficient),
			from.getY()*coeficient + to.getY()*(1-coeficient)
		);
	}
	
	public float getLength() {
		return FactoryPosition.getDistance(from, to);
	}
}
