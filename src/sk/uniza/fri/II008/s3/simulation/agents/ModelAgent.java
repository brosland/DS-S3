package sk.uniza.fri.II008.s3.simulation.agents;

import OSPABA.Simulation;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.managers.ModelManager;

public class ModelAgent extends BaseAgent
{
	public ModelAgent(Simulation simulation)
	{
		super(ComponentType.MODEL_AGENT, simulation, null);

		new ModelManager(this);
	}
}
