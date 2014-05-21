package sk.uniza.fri.II008.s3.simulation.messages;

import OSPABA.Simulation;
import sk.uniza.fri.II008.s3.model.Elevator;
import sk.uniza.fri.II008.s3.model.RollStorage;
import sk.uniza.fri.II008.s3.model.Storage;

public abstract class AssignVehicleMessage extends VehicleMessage implements Comparable<AssignVehicleMessage>
{
	private final RollStorage rollStorage;

	public AssignVehicleMessage(Simulation simulation, RollStorage rollStorage)
	{
		super(simulation);

		this.rollStorage = rollStorage;
	}

	public RollStorage getRollStorage()
	{
		return rollStorage;
	}

	@Override
	public int compareTo(AssignVehicleMessage assignVehicleMessage)
	{
		if (rollStorage instanceof Elevator
			&& assignVehicleMessage.getRollStorage() instanceof Storage)
		{
			return -1;
		}
		else if (rollStorage instanceof Storage
			&& assignVehicleMessage.getRollStorage() instanceof Elevator)
		{
			return 1;
		}

		return Double.compare(assignVehicleMessage.getRollStorage().getFilling(), rollStorage.getFilling());
	}
}
