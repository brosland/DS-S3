package sk.uniza.fri.II008.s3.model.requests;

import sk.uniza.fri.II008.s3.model.Roll;
import sk.uniza.fri.II008.s3.model.RollStorable;

public class CraneRequest extends BaseRequest
{
	private final Roll roll;
	private final RollStorable from, to;

	public CraneRequest(double startTimestamp, double duration, Roll roll,
		RollStorable from, RollStorable to)
	{
		super(startTimestamp, duration);

		this.roll = roll;
		this.from = from;
		this.to = to;
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
