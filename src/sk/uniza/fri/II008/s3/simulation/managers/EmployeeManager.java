package sk.uniza.fri.II008.s3.simulation.managers;

import OSPABA.Agent;
import OSPABA.MessageForm;
import OSPDataStruct.SimQueue;
import sk.uniza.fri.II008.s3.model.Employee;
import sk.uniza.fri.II008.s3.model.ProcessingStorage;
import sk.uniza.fri.II008.s3.model.Roll;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.MessageType;
import sk.uniza.fri.II008.s3.simulation.messages.ProcessRollMessage;
import sk.uniza.fri.II008.s3.simulation.messages.RollMessage;

public class EmployeeManager extends BaseManager
{
	private final SimQueue<RollMessage> requestQueue;

	public EmployeeManager(Agent agent)
	{
		super(ComponentType.EMPLOYEE_MANAGER, agent.mySim(), agent);

		agent.addOwnMessage(MessageType.PROCESS_ROLL);
		agent.addOwnMessage(MessageType.TRANSFER_EMPLOYEE);

		requestQueue = new SimQueue<>();
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
			case MessageType.PROCESS_ROLL:
				onProcessRollMessageReceived((RollMessage) message);
				break;
			case MessageType.finish:
				message.setCode(MessageType.PROCESS_ROLL_DONE);
				onProcessRollDoneMessageReceived((ProcessRollMessage) message);
				break;
		}
	}

	private void onProcessRollMessageReceived(RollMessage rollMessage)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format(
				"EmployeeManager[PROCESS_ROLL]\n - roll %s", rollMessage.getRoll()));
		}

		Employee employee = null;

		for (Employee e : getFactory().getEmployees())
		{
			if (e.getState() == Employee.State.FREE) // todo find employee in current storage
			{
				employee = e;
				break;
			}
		}

		if (employee != null)
		{
			employee.setState(Employee.State.BUSY);
			employee.setCurrentStorage((ProcessingStorage) rollMessage.getRoll().getRollStorage());
			rollMessage.getRoll().setState(Roll.State.PROCESSING);

			ProcessRollMessage processRollMessage = new ProcessRollMessage(
				_mySim, employee, rollMessage);
			processRollMessage.setCode(MessageType.PROCESS_ROLL);
			processRollMessage.setAddressee(_myAgent.findAssistant(
				ComponentType.PROCESS_ROLL_CONTINUAL_ASSISTANT));

			startContinualAssistant(processRollMessage);
		}
		else
		{
			requestQueue.add(rollMessage);
		}
	}

	private void onProcessRollDoneMessageReceived(ProcessRollMessage processRollMessage)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format(
				"EmployeeManager[PROCESS_ROLL_DONE]\n - employee %s processed roll %s",
				processRollMessage.getEmployee(), processRollMessage.getRollMessage().getRoll()));
		}

		processRollMessage.getRollMessage().getRoll().setState(Roll.State.READY);
		processRollMessage.getEmployee().setState(Employee.State.FREE);

		RollMessage rollMessage = processRollMessage.getRollMessage();
		rollMessage.setCode(MessageType.PROCESS_ROLL_DONE);
		response(rollMessage);

		if (!requestQueue.isEmpty())
		{
			onProcessRollMessageReceived(requestQueue.dequeue());
		}
	}
}
