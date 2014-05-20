package sk.uniza.fri.II008.s3;

import sk.uniza.fri.II008.Utils;
import sk.uniza.fri.II008.s3.simulation.agents.*;

public class FactoryReplication extends OSPABA.Simulation
{
	private final FactorySimulation factorySimulation;
	private final ModelAgent modelAgent;

	public FactoryReplication(FactorySimulation factorySimulation)
	{
		this.factorySimulation = factorySimulation;

		modelAgent = new ModelAgent(this);
		IOAgent ioAgent = new IOAgent(this, modelAgent);
		FactoryAgent factoryAgent = new FactoryAgent(this, modelAgent);
		EmployeeAgent employeeAgent = new EmployeeAgent(this, factoryAgent);
		TransportAgent storageAgent = new TransportAgent(this, factoryAgent);
		CraneAgent craneAgent = new CraneAgent(this, storageAgent);
		VehicleAgent vehicleAgent = new VehicleAgent(this, storageAgent);
	}

	public FactorySimulation getFactorySimulation()
	{
		return factorySimulation;
	}

	public double getTimestamp()
	{
		return currentTime();
	}

	public void log(String message)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactorySimulation().log(String.format("[%s] %s",
				Utils.formatTime(getTimestamp()), message));
		}
	}
}
