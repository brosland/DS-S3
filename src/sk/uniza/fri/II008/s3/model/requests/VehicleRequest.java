package sk.uniza.fri.II008.s3.model.requests;

import sk.uniza.fri.II008.s3.model.Navigation.Location;

public class VehicleRequest extends BaseRequest
{
	private final Location from, to;

	public VehicleRequest(double startTimestamp, double duration, Location from, Location to)
	{
		super(startTimestamp, duration);

		this.from = from;
		this.to = to;
	}

	public Location getFrom()
	{
		return from;
	}

	public Location getTo()
	{
		return to;
	}
}
