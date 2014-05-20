package sk.uniza.fri.II008.s3.simulation.agents;

import OSPABA.Simulation;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.assistants.VehicleContinualAssistant;
import sk.uniza.fri.II008.s3.simulation.managers.VehicleManager;

public class VehicleAgent extends BaseAgent
{
	public VehicleAgent(Simulation simulation, BaseAgent agent)
	{
		super(ComponentType.VEHICLE_AGENT, simulation, agent);

		new VehicleManager(this);

		new VehicleContinualAssistant(this);
	}
}
