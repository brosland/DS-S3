package sk.uniza.fri.II008.s3.simulation.messages;

import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.uniza.fri.II008.s3.model.Navigation.Location;
import sk.uniza.fri.II008.s3.model.Vehicle;

public abstract class TransferVehicleMessage extends MessageForm
{
	private final Vehicle vehicle;
	private final Location destination;

	public TransferVehicleMessage(Simulation simulation, Vehicle vehicle, Location destination)
	{
		super(simulation);

		this.vehicle = vehicle;
		this.destination = destination;
	}

	public Vehicle getVehicle()
	{
		return vehicle;
	}

	public Location getDestination()
	{
		return destination;
	}

	public abstract void onDone();
}
