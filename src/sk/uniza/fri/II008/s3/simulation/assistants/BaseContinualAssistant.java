package sk.uniza.fri.II008.s3.simulation.assistants;

import OSPABA.Agent;
import OSPABA.Simulation;
import sk.uniza.fri.II008.s3.FactoryReplication;
import sk.uniza.fri.II008.s3.FactorySimulation;
import sk.uniza.fri.II008.s3.model.Factory;

public abstract class BaseContinualAssistant extends OSPABA.ContinualAssistant
{
	public BaseContinualAssistant(int id, Simulation simulation, Agent agent)
	{
		super(id, simulation, agent);
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
