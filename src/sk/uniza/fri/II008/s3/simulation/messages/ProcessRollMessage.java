package sk.uniza.fri.II008.s3.simulation.messages;

import OSPABA.Simulation;
import sk.uniza.fri.II008.s3.model.Employee;
import sk.uniza.fri.II008.s3.model.Roll;

public class ProcessRollMessage extends RollMessage
{
	private Employee employee;

	public ProcessRollMessage(Simulation simulation, Roll roll)
	{
		super(simulation, roll);
	}

	public Employee getEmployee()
	{
		return employee;
	}

	public void setEmployee(Employee employee)
	{
		this.employee = employee;
	}
}
