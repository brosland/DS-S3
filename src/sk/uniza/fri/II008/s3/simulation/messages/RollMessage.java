package sk.uniza.fri.II008.s3.simulation.messages;

import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.uniza.fri.II008.s3.model.Roll;

public class RollMessage extends MessageForm
{
	private final Roll roll;

	public RollMessage(Simulation simulation, Roll roll)
	{
		super(simulation);

		this.roll = roll;
	}

	public Roll getRoll()
	{
		return roll;
	}
}
