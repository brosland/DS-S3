package sk.uniza.fri.II008.s3.simulation.assistants;

import OSPABA.Agent;
import OSPABA.MessageForm;
import sk.uniza.fri.II008.generators.IContinuosGenerator;
import sk.uniza.fri.II008.s3.FactorySimulation;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.MessageType;
import sk.uniza.fri.II008.s3.simulation.messages.ProcessRollMessage;
import sk.uniza.fri.II008.s3.simulation.messages.RollMessage;

public class ProcessRollContinualAssistant extends BaseContinualAssistant
{
	private final IContinuosGenerator processRollDurationGen;

	public ProcessRollContinualAssistant(Agent agent)
	{
		super(ComponentType.PROCESS_ROLL_CONTINUAL_ASSISTANT, agent.mySim(), agent);

		processRollDurationGen = ((IContinuosGenerator) getFactorySimulation().getGenerator(
			FactorySimulation.Generator.PROCESS_ROLL_DURATION));

		agent.addOwnMessage(MessageType.PROCESS_ROLL_DONE);
	}

	@Override
	public void processMessage(MessageForm message)
	{
		ProcessRollMessage processRollMessage = (ProcessRollMessage) message;

		switch (message.code())
		{
			case MessageType.start:
				message.setCode(MessageType.PROCESS_ROLL_DONE);

				hold(processRollDurationGen.nextValue(), message);

				if (getFactorySimulation().isEnabledLogging())
				{
					getFactoryReplication().log(String.format(
						"ProcessRollContinualAssistant[start]\n - employee %s processing roll %s",
						processRollMessage.getEmployee(), processRollMessage.getRollMessage().getRoll()));
				}
				break;
			case MessageType.PROCESS_ROLL_DONE:
				if (getFactorySimulation().isEnabledLogging())
				{
					getFactoryReplication().log(String.format(
						"ProcessRollContinualAssistant[PROCESS_ROLL_DONE]\n - roll %s processed",
						processRollMessage.getRollMessage().getRoll()));
				}

				assistantFinished(message);
				break;
		}
	}
}
