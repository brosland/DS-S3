package sk.uniza.fri.II008.s3.simulation.agents;

import OSPABA.Simulation;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.managers.TransportManager;

public class TransportAgent extends BaseAgent
{
	public TransportAgent(Simulation simulation, BaseAgent agent)
	{
		super(ComponentType.TRANSPORT_AGENT, simulation, agent);

		new TransportManager(this);
	}
}
