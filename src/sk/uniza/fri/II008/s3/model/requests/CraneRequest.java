package sk.uniza.fri.II008.s3.model.requests;

import sk.uniza.fri.II008.s3.model.Roll;
import sk.uniza.fri.II008.s3.model.RollStorable;

public class CraneRequest
{
	private final double startTimestamp, duration;
	private final Roll roll;
	private final RollStorable from, to;

	public CraneRequest(double startTimestamp, double duration, Roll roll,
		RollStorable from, RollStorable to)
	{
		this.startTimestamp = startTimestamp;
		this.duration = duration;
		this.roll = roll;
		this.from = from;
		this.to = to;
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

	public Roll getRoll()
	{
		return roll;
	}

	public RollStorable getFrom()
	{
		return from;
	}

	public RollStorable getTo()
	{
		return to;
	}
}
