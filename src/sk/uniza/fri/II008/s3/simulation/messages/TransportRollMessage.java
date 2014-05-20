package sk.uniza.fri.II008.s3.simulation.messages;

import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.uniza.fri.II008.s3.model.Roll;
import sk.uniza.fri.II008.s3.model.RollStorage;

public class TransportRollMessage extends MessageForm
{
	private final Roll roll;
	private final RollStorage from, to;

	public TransportRollMessage(Simulation simulation, Roll roll, RollStorage from, RollStorage to)
	{
		super(simulation);

		this.roll = roll;
		this.from = from;
		this.to = to;
	}

	public Roll getRoll()
	{
		return roll;
	}

	public RollStorage getFrom()
	{
		return from;
	}

	public RollStorage getTo()
	{
		return to;
	}
}
