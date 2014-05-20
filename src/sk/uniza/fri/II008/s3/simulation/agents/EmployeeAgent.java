package sk.uniza.fri.II008.s3.simulation.agents;

import OSPABA.Simulation;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.assistants.ProcessRollContinualAssistant;
import sk.uniza.fri.II008.s3.simulation.managers.EmployeeManager;

public class EmployeeAgent extends BaseAgent
{
	public EmployeeAgent(Simulation simulation, BaseAgent agent)
	{
		super(ComponentType.EMPLOYEE_AGENT, simulation, agent);

		new EmployeeManager(this);

		new ProcessRollContinualAssistant(this);

		// todo new EmployeeTransportContinualAssistant
	}
}
