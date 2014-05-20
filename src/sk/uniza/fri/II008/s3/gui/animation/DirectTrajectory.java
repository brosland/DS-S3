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
			from.getX()*(1-coeficient) + to.getX()*coeficient,
			from.getY()*(1-coeficient) + to.getY()*coeficient
		);
	}
	
	public float getLength() {
		return FactoryPosition.getDistance(from, to);
	}
}
