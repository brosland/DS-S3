package sk.uniza.fri.II008.s3.model;

import sk.uniza.fri.II008.s3.model.Navigation.Location;
import sk.uniza.fri.II008.s3.model.requests.VehicleRequest;

public class Vehicle implements RollStorable
{
	public static int LAST_ID = 0;
	private final int id;
	private final double speed;
	private Location location;
	private boolean busy = false;
	private Roll roll = null;
	private VehicleRequest vehicleRequest = null;

	public Vehicle(double speed, Location location)
	{
		this.id = ++LAST_ID;
		this.speed = speed;
		this.location = location;
	}

	public int getId()
	{
		return id;
	}

	public double getSpeed()
	{
		return speed;
	}

	public synchronized boolean isBusy()
	{
		return busy;
	}

	public void setBusy(boolean busy)
	{
		this.busy = busy;
	}

	public boolean hasVehicleRequest()
	{
		return vehicleRequest != null;
	}

	public VehicleRequest getVehicleRequest()
	{
		return vehicleRequest;
	}

	public void setVehicleRequest(VehicleRequest vehicleRequest)
	{
		this.vehicleRequest = vehicleRequest;
	}

	public void removeVehicleRequest()
	{
		vehicleRequest = null;
	}

	public synchronized boolean hasRoll()
	{
		return roll != null;
	}

	public synchronized Roll getRoll()
	{
		return roll;
	}

	@Override
	public void addRoll(Roll roll)
	{
		if (hasRoll())
		{
			throw new IllegalStateException("Vehicle is full.");
		}

		this.roll = roll;
	}

	@Override
	public boolean removeRoll(Roll roll)
	{
		if (this.roll != roll)
		{
			throw new IllegalStateException("Vehicle is not containing roll " + roll + ".");
		}

		this.roll = null;

		return true;
	}

	public synchronized Location getLocation()
	{
		return location;
	}

	public void setLocation(Location location)
	{
		this.location = location;
	}

	@Override
	public String toString()
	{
		return String.format("[%d]", id);
	}

	public void reset()
	{
		busy = false;
		roll = null;
	}
}
