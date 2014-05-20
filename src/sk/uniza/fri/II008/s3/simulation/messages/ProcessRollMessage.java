package sk.uniza.fri.II008.s3.simulation.messages;

import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.uniza.fri.II008.s3.model.Employee;

public class ProcessRollMessage extends MessageForm
{
	private final Employee employee;
	private final RollMessage rollMessage;

	public ProcessRollMessage(Simulation simulation, Employee employee, RollMessage rollMessage)
	{
		super(simulation);

		this.employee = employee;
		this.rollMessage = rollMessage;
	}

	public Employee getEmployee()
	{
		return employee;
	}

	public RollMessage getRollMessage()
	{
		return rollMessage;
	}
}
