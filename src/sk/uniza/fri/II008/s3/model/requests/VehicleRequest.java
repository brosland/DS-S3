package sk.uniza.fri.II008.s3.model.requests;

import sk.uniza.fri.II008.s3.model.Navigation.Location;

public class VehicleRequest
{
	private final double startTimestamp, duration;
	private final Location from, to;

	public VehicleRequest(double startTimestamp, double duration, Location from, Location to)
	{
		this.startTimestamp = startTimestamp;
		this.duration = duration;
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

	public Location getFrom()
	{
		return from;
	}

	public Location getTo()
	{
		return to;
	}
}
