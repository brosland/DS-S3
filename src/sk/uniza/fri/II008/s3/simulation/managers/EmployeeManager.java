package sk.uniza.fri.II008.s3.simulation.managers;

import OSPABA.Agent;
import OSPABA.MessageForm;
import java.util.ArrayList;
import java.util.HashMap;
import sk.uniza.fri.II008.s3.model.Employee;
import sk.uniza.fri.II008.s3.model.ProcessingStorage;
import sk.uniza.fri.II008.s3.model.Roll;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.MessageType;
import sk.uniza.fri.II008.s3.simulation.messages.ProcessRollMessage;

public class EmployeeManager extends BaseManager
{
	private final HashMap<Roll.Type, ArrayList<ProcessRollMessage>> requests;
	private final HashMap<Roll.Type, Employee> employees;

	public EmployeeManager(Agent agent)
	{
		super(ComponentType.EMPLOYEE_MANAGER, agent.mySim(), agent);

		agent.addOwnMessage(MessageType.PROCESS_ROLL);
		agent.addOwnMessage(MessageType.TRANSFER_EMPLOYEE);

		requests = new HashMap<>();
		employees = new HashMap<>();

		for (Roll.Type rollType : Roll.Type.values())
		{
			requests.put(rollType, new ArrayList<ProcessRollMessage>());
		}

		for (Employee employee : getFactory().getEmployees())
		{
			employees.put(employee.getCurrentStorage().getRollType(), employee);
		}
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
			case MessageType.PROCESS_ROLL:
				onProcessRollMessageReceived((ProcessRollMessage) message);
				break;
			case MessageType.finish:
				message.setCode(MessageType.PROCESS_ROLL_DONE);
				onProcessRollDoneMessageReceived((ProcessRollMessage) message);
				break;
		}
	}

	private void onProcessRollMessageReceived(ProcessRollMessage processRollMessage)
	{
		Roll roll = processRollMessage.getRoll();

		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format("EmployeeManager[PROCESS_ROLL]\n - roll %s", roll));
		}

		Employee employee = employees.get(roll.getType());

		if (employee.getState() == Employee.State.FREE)
		{
			roll.setState(Roll.State.PROCESSING);

			employee.setState(Employee.State.BUSY);
			employee.setCurrentStorage((ProcessingStorage) roll.getRollStorage());

			processRollMessage.setEmployee(employee);
			processRollMessage.setAddressee(_myAgent.findAssistant(
				ComponentType.PROCESS_ROLL_CONTINUAL_ASSISTANT));

			startContinualAssistant(processRollMessage);
		}
		else
		{
			requests.get(roll.getType()).add(processRollMessage);
		}
	}

	private void onProcessRollDoneMessageReceived(ProcessRollMessage processRollMessage)
	{
		Roll roll = processRollMessage.getRoll();
		Employee employee = processRollMessage.getEmployee();

		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format("EmployeeManager[PROCESS_ROLL_DONE]\n"
				+ " - employee %s processed roll %s", employee, roll));
		}

		employee.setState(Employee.State.FREE);
		roll.setState(roll.getType() == Roll.Type.C ? Roll.State.READY : Roll.State.PROCESSED);

		response(processRollMessage);

		if (!requests.get(roll.getType()).isEmpty())
		{
			onProcessRollMessageReceived(requests.get(roll.getType()).remove(0));
		}
	}
}
