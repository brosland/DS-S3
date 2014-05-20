package sk.uniza.fri.II008.s3.simulation.agents;

import OSPABA.Simulation;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.assistants.CraneContinualAssistant;
import sk.uniza.fri.II008.s3.simulation.managers.CraneManager;

public class CraneAgent extends BaseAgent
{
	public CraneAgent(Simulation simulation, BaseAgent agent)
	{
		super(ComponentType.CRANE_AGENT, simulation, agent);

		new CraneManager(this);

		new CraneContinualAssistant(this);
	}
}
