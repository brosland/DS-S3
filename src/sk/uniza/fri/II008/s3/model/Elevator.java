package sk.uniza.fri.II008.s3.model;

import sk.uniza.fri.II008.s3.model.Navigation.Location;

public class Elevator extends BaseRollStorage
{
	public static int LAST_ID = 0;

	public Elevator(int capacity, Location location, Crane crane)
	{
		super(++LAST_ID, capacity, location, crane);
	}

	@Override
	public String getName()
	{
		return "D" + getId();
	}
}
