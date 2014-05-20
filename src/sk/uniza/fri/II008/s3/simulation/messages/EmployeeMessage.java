package sk.uniza.fri.II008.s3.simulation.messages;

import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.uniza.fri.II008.s3.model.Employee;

public abstract class EmployeeMessage extends MessageForm
{
	private final Employee employee;

	public EmployeeMessage(Simulation simulation, Employee employee)
	{
		super(simulation);

		this.employee = employee;
	}

	public Employee getEmployee()
	{
		return employee;
	}

	public abstract void onDone();
}
