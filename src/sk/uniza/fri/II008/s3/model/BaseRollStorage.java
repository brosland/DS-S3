package sk.uniza.fri.II008.s3.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import sk.uniza.fri.II008.s3.model.Navigation.Location;

public abstract class BaseRollStorage implements RollStorage
{
	private final int id, capacity;
	protected final ArrayList<Roll> rolls, reservations;
	private final HashMap<Roll.State, ArrayList<Roll>> rollsByState;
	private final Navigation.Location location;
	private final Crane crane;
	private final Roll.ChangeStateListener rollChangeStateListener;

	public BaseRollStorage(int id, int capacity, Location location, Crane crane)
	{
		this.id = id;
		this.capacity = capacity;
		this.location = location;
		this.crane = crane;
		this.reservations = new ArrayList<>();
		this.rolls = new ArrayList<>(capacity);
		this.rollsByState = new HashMap<>();

		for (Roll.State rollState : Roll.State.values())
		{
			rollsByState.put(rollState, new ArrayList<Roll>());
		}

		rollChangeStateListener = new Roll.ChangeStateListener()
		{
			@Override
			public void onChange(Roll roll, Roll.State oldState)
			{
				rollsByState.get(oldState).remove(roll);
				rollsByState.get(roll.getState()).add(roll);
			}
		};
	}

	@Override
	public int getId()
	{
		return id;
	}

	@Override
	public int getCapacity()
	{
		return capacity;
	}

	@Override
	public synchronized boolean isEmpty()
	{
		return rolls.size() + reservations.size() == 0;
	}

	@Override
	public synchronized boolean isFull()
	{
		return rolls.size() + reservations.size() == capacity;
	}

	@Override
	public synchronized double getFilling()
	{
		return 1f * (rolls.size() + reservations.size()) / capacity;
	}

	@Override
	public synchronized List<Roll> getRolls()
	{
		return new ArrayList<>(rolls);
	}

	public synchronized List<Roll> getRolls(Roll.State rollState)
	{
		return new ArrayList<>(rollsByState.get(rollState));
	}

	public synchronized boolean hasRoll(Roll.State rollState)
	{
		return !getRolls(rollState).isEmpty();
	}

	public synchronized boolean hasRoll(Roll.State rollState, Roll.Type rollType)
	{
		return getRoll(rollState, rollType) != null;
	}

	public synchronized Roll getRoll(Roll.State rollState)
	{
		return hasRoll(rollState) ? getRolls(rollState).get(0) : null;
	}

	public synchronized Roll getRoll(Roll.State rollState, Roll.Type rollType)
	{
		for (Roll roll : getRolls(rollState))
		{
			if (roll.getType() == rollType)
			{
				return roll;
			}
		}

		return null;
	}

	public boolean getReservation(Roll roll)
	{
		if (isFull())
		{
			return false;
		}

		return reservations.add(roll);
	}

	@Override
	public void addRoll(Roll roll)
	{
		if (!reservations.remove(roll) && isFull())
		{
			throw new IllegalStateException("Roll storage is full.");
		}

		roll.setListener(rollChangeStateListener);
		roll.setRollStorage(this);

		rolls.add(roll);
		rollsByState.get(roll.getState()).add(roll);
	}

	@Override
	public boolean removeRoll(Roll roll)
	{
		if (roll.getRollStorage() != this)
		{
			throw new IllegalStateException("Roll is not in this storage.");
		}

		roll.removeListener();
		roll.setRollStorage(null);

		return rolls.remove(roll) && rollsByState.get(roll.getState()).remove(roll);
	}

	@Override
	public Location getLocation()
	{
		return location;
	}

	@Override
	public Crane getCrane()
	{
		return crane;
	}

	@Override
	public String toString()
	{
		return String.format("[%d] %s", id, getName());
	}

	@Override
	public void reset()
	{
		rolls.clear();
		reservations.clear();

		for (ArrayList<Roll> rollList : rollsByState.values())
		{
			rollList.clear();
		}
	}
}
