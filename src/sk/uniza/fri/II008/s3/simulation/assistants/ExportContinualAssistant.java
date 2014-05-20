package sk.uniza.fri.II008.s3.simulation.assistants;

import OSPABA.Agent;
import OSPABA.MessageForm;
import sk.uniza.fri.II008.generators.IContinuosGenerator;
import sk.uniza.fri.II008.s3.FactorySimulation;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.MessageType;

public class ExportContinualAssistant extends BaseContinualAssistant
{
	private final IContinuosGenerator rollExportTimeGen;

	public ExportContinualAssistant(Agent agent)
	{
		super(ComponentType.EXPORT_CONTINUAL_ASSISTANT, agent.mySim(), agent);

		rollExportTimeGen = ((IContinuosGenerator) getFactorySimulation().getGenerator(
			FactorySimulation.Generator.ROLL_EXPORT_TIME));

		agent.addOwnMessage(MessageType.EXPORT);
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
			case MessageType.start:
				hold(rollExportTimeGen.nextValue(), createExportMessage());

				if (getFactorySimulation().isEnabledLogging())
				{
					getFactoryReplication().log("ExportContinualAssistant[start]");
				}
				break;
			case MessageType.EXPORT:
				if (getFactorySimulation().isEnabledLogging())
				{
					getFactoryReplication().log("ExportContinualAssistant[EXPORT]");
				}

				assistantFinished(message);

				hold(rollExportTimeGen.nextValue(), createExportMessage());
				break;
		}
	}

	private MessageForm createExportMessage()
	{
		MessageForm exportMessage = new MessageForm(_mySim);
		exportMessage.setCode(MessageType.EXPORT);

		return exportMessage;
	}
}
