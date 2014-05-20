package sk.uniza.fri.II008.s3.simulation.agents;

import OSPABA.Simulation;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.assistants.ExportContinualAssistant;
import sk.uniza.fri.II008.s3.simulation.assistants.ImportContinualAssistant;
import sk.uniza.fri.II008.s3.simulation.managers.IOManager;

public class IOAgent extends BaseAgent
{
	public IOAgent(Simulation simulation, BaseAgent agent)
	{
		super(ComponentType.IO_AGENT, simulation, agent);

		new IOManager(this);

		new ImportContinualAssistant(this);
		new ExportContinualAssistant(this);
	}
}
