package sk.uniza.fri.II008.s3.model;

import sk.uniza.fri.II008.s3.model.Navigation.Location;

public class ProcessingStorage extends Storage
{
	private final Roll.Type rollType;

	public ProcessingStorage(int capacity, Roll.Type rollType, Location location, Crane crane)
	{
		super(capacity, location, crane);

		this.rollType = rollType;
	}

	@Override
	public void addRoll(Roll roll)
	{
		if (roll.getType() != rollType)
		{
			throw new IllegalArgumentException(String.format(
				"Can not add roll %s to the storage of type %s.", roll, rollType));
		}

		super.addRoll(roll);
	}

	public Roll.Type getRollType()
	{
		return rollType;
	}
}
