package sk.uniza.fri.II008.s3.model.requests;

import sk.uniza.fri.II008.s3.model.Roll;

public class EmployeeWorkRequest extends BaseRequest
{
	private final Roll roll;

	public EmployeeWorkRequest(double startTimestamp, double duration, Roll roll)
	{
		super(startTimestamp, duration);

		this.roll = roll;
	}

	public Roll getRoll()
	{
		return roll;
	}
}
