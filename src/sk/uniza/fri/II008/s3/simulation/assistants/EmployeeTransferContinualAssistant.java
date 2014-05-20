package sk.uniza.fri.II008.s3.simulation.assistants;

import OSPABA.Agent;
import OSPABA.MessageForm;
import sk.uniza.fri.II008.generators.IContinuosGenerator;
import sk.uniza.fri.II008.s3.FactorySimulation;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.MessageType;
import sk.uniza.fri.II008.s3.simulation.messages.EmployeeMessage;

public class EmployeeTransferContinualAssistant extends BaseContinualAssistant
{
	private final IContinuosGenerator employeeTransferDurationGen;

	public EmployeeTransferContinualAssistant(Agent agent)
	{
		super(ComponentType.EMPLOYEE_TRANSFER_CONTINUAL_ASSISTANT, agent.mySim(), agent);

		employeeTransferDurationGen = ((IContinuosGenerator) getFactorySimulation().getGenerator(
			FactorySimulation.Generator.EMPLOYEE_TRANSFER_DURATION));

		agent.addOwnMessage(MessageType.TRANSFER_EMPLOYEE_DONE);
	}

	@Override
	public void processMessage(MessageForm message)
	{
		EmployeeMessage employeeMessage = (EmployeeMessage) message;

		switch (message.code())
		{
			case MessageType.start:
				message.setCode(MessageType.TRANSFER_EMPLOYEE_DONE);

				hold(employeeTransferDurationGen.nextValue(), message);
				break;
			case MessageType.TRANSFER_EMPLOYEE_DONE:
				assistantFinished(message);
				break;
		}
	}
}
