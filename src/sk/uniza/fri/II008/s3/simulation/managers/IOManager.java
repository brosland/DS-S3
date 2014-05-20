package sk.uniza.fri.II008.s3.simulation.managers;

import OSPABA.Agent;
import OSPABA.MessageForm;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.MessageType;
import sk.uniza.fri.II008.s3.simulation.messages.RollMessage;

public class IOManager extends BaseManager
{
	public IOManager(Agent agent)
	{
		super(ComponentType.IO_MANAGER, agent.mySim(), agent);

		agent.addOwnMessage(MessageType.INIT);
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
			case MessageType.INIT:
				onInitMessageReceived();
				break;
			case MessageType.finish:
				if (message.sender().id() == ComponentType.IMPORT_CONTINUAL_ASSISTANT)
				{
					message.setCode(MessageType.IMPORT);
					onImportMessageReceived((RollMessage) message);
				}
				else if (message.sender().id() == ComponentType.EXPORT_CONTINUAL_ASSISTANT)
				{
					message.setCode(MessageType.EXPORT);
					onExportMessageReceived(message);
				}

				break;
		}
	}

	private void onInitMessageReceived()
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log("IOManager[INIT]");
		}

		MessageForm importMessage = new MessageForm(mySim());
		importMessage.setAddressee(myAgent().findAssistant(ComponentType.IMPORT_CONTINUAL_ASSISTANT));
		startContinualAssistant(importMessage);

		MessageForm exportMessage = new MessageForm(mySim());
		exportMessage.setAddressee(myAgent().findAssistant(ComponentType.EXPORT_CONTINUAL_ASSISTANT));
		startContinualAssistant(exportMessage);
	}

	private void onImportMessageReceived(RollMessage rollMessage)
	{
		rollMessage.setAddressee(mySim().findAgent(ComponentType.MODEL_AGENT));
		notice(rollMessage);

		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format(
				"IOManager[IMPORT]\n - roll %s", rollMessage.getRoll()));
		}
	}

	private void onExportMessageReceived(MessageForm message)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log("ModelManager[EXPORT]");
		}

		message.setAddressee(mySim().findAgent(ComponentType.MODEL_AGENT));
		notice(message);
	}
}
