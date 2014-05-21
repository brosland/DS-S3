package sk.uniza.fri.II008.s3.model.requests;

public abstract class BaseRequest
{
	private final double startTimestamp, duration;

	public BaseRequest(double startTimestamp, double duration)
	{
		this.startTimestamp = startTimestamp;
		this.duration = duration;
	}

	public double getStartTimestamp()
	{
		return startTimestamp;
	}

	public double getEndTimestamp()
	{
		return startTimestamp + duration;
	}

	public double getDuration()
	{
		return duration;
	}
}
