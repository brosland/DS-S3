package sk.uniza.fri.II008.s3.simulation.assistants;

import OSPABA.Agent;
import OSPABA.MessageForm;
import sk.uniza.fri.II008.generators.IContinuosGenerator;
import sk.uniza.fri.II008.s3.FactorySimulation;
import sk.uniza.fri.II008.s3.model.Roll;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.MessageType;
import sk.uniza.fri.II008.s3.simulation.messages.RollMessage;

public class CoolingContinualAssistant extends BaseContinualAssistant
{
	private final IContinuosGenerator rollCoolingDurationGen;

	public CoolingContinualAssistant(Agent agent)
	{
		super(ComponentType.COOLING_CONTINUAL_ASSISTANT, agent.mySim(), agent);

		rollCoolingDurationGen = ((IContinuosGenerator) getFactorySimulation().getGenerator(
			FactorySimulation.Generator.ROLL_COOLING_DURATION));

		agent.addOwnMessage(MessageType.PREPARE_ROLL_TO_EXPORT_DONE);
	}

	@Override
	public void processMessage(MessageForm message)
	{
		RollMessage rollMessage = (RollMessage) message;

		switch (message.code())
		{
			case MessageType.start:
				rollMessage.getRoll().setState(Roll.State.COOLING);

				message.setCode(MessageType.PREPARE_ROLL_TO_EXPORT_DONE);

				hold(rollCoolingDurationGen.nextValue(), message);

				if (getFactorySimulation().isEnabledLogging())
				{
					getFactoryReplication().log(String.format(
						"CoolingContinualAssistant[start]\n - roll %s is preparing to export",
						rollMessage.getRoll()));
				}
				break;
			case MessageType.PREPARE_ROLL_TO_EXPORT_DONE:
				rollMessage.getRoll().setState(Roll.State.READY);

				if (getFactorySimulation().isEnabledLogging())
				{
					getFactoryReplication().log(String.format(
						"CoolingContinualAssistant[PREPARE_ROLL_TO_EXPORT_DONE]\n - roll %s prepared to export",
						rollMessage.getRoll()));
				}

				assistantFinished(message);
				break;
		}
	}
}
