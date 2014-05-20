package sk.uniza.fri.II008.s3.simulation.agents;

import OSPABA.Simulation;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.assistants.CoolingContinualAssistant;
import sk.uniza.fri.II008.s3.simulation.managers.FactoryManager;

public class FactoryAgent extends BaseAgent
{
	public FactoryAgent(Simulation simulation, BaseAgent agent)
	{
		super(ComponentType.FACTORY_AGENT, simulation, agent);

		new FactoryManager(this);

		new CoolingContinualAssistant(this);
	}
}
