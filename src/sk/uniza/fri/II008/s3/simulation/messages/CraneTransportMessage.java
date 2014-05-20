package sk.uniza.fri.II008.s3.simulation.messages;

import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.uniza.fri.II008.s3.model.Crane;
import sk.uniza.fri.II008.s3.model.Elevator;
import sk.uniza.fri.II008.s3.model.Roll;
import sk.uniza.fri.II008.s3.model.RollStorable;
import sk.uniza.fri.II008.s3.model.Storage;
import sk.uniza.fri.II008.s3.model.Vehicle;

public abstract class CraneTransportMessage extends MessageForm implements Comparable<CraneTransportMessage>
{
	private final Crane crane;
	private final Roll roll;
	private final RollStorable from, to;

	public CraneTransportMessage(Simulation simulation, Crane crane, Roll roll,
		RollStorable from, RollStorable to)
	{
		super(simulation);

		this.crane = crane;
		this.roll = roll;
		this.from = from;
		this.to = to;
	}

	public Crane getCrane()
	{
		return crane;
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

	public abstract void onDone();

	@Override
	public int compareTo(CraneTransportMessage craneTransportMessage)
	{
		return getPriority(from, to) - getPriority(craneTransportMessage.getFrom(), craneTransportMessage.getTo()) < 0 ? -1 : 1;
	}

	private int getPriority(RollStorable from, RollStorable to)
	{
		if (from instanceof Elevator && to instanceof Vehicle)
		{
			return 1;
		}
		else if (from instanceof Elevator && to instanceof Storage)
		{
			return 2;
		}
		else if (from instanceof Storage && to instanceof Elevator)
		{
			return 3;
		}
		else if (from instanceof Vehicle && to instanceof Storage)
		{
			return 4;
		}

		return 5;
	}
}
