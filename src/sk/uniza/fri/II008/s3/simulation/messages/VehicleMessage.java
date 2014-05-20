package sk.uniza.fri.II008.s3.simulation.messages;

import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.uniza.fri.II008.s3.model.Vehicle;

public abstract class VehicleMessage extends MessageForm
{
	private Vehicle vehicle = null;

	public VehicleMessage(Simulation simulation)
	{
		super(simulation);
	}

	public Vehicle getVehicle()
	{
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle)
	{
		this.vehicle = vehicle;
	}

	public abstract void onDone();
}
