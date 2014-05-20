package sk.uniza.fri.II008.s3.simulation.agents;

import OSPABA.Agent;
import OSPABA.Simulation;
import sk.uniza.fri.II008.s3.FactoryReplication;
import sk.uniza.fri.II008.s3.FactorySimulation;
import sk.uniza.fri.II008.s3.model.Factory;

public abstract class BaseAgent extends Agent
{
	public BaseAgent(int id, Simulation simulation, Agent parent)
	{
		super(id, simulation, parent);
	}

	public FactorySimulation getFactorySimulation()
	{
		return ((FactoryReplication) mySim()).getFactorySimulation();
	}

	public FactoryReplication getFactoryReplication()
	{
		return (FactoryReplication) _mySim;
	}

	public Factory getFactory()
	{
		return getFactorySimulation().getFactory();
	}
}
