package sk.uniza.fri.II008.s3.simulation.managers;

import OSPABA.Agent;
import OSPABA.MessageForm;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.MessageType;
import sk.uniza.fri.II008.s3.simulation.messages.*;

public class ModelManager extends BaseManager
{
	public ModelManager(Agent agent)
	{
		super(ComponentType.MODEL_MANAGER, agent.mySim(), agent);

		agent.addOwnMessage(MessageType.INIT);
		agent.addOwnMessage(MessageType.IMPORT);
		agent.addOwnMessage(MessageType.EXPORT);
	}

	@Override
	public void processMessage(MessageForm messageForm)
	{
		switch (messageForm.code())
		{
			case MessageType.INIT:
				onInitMessageReceived(messageForm);
				break;
			case MessageType.IMPORT:
				onImportMessageReceived((RollMessage) messageForm);
				break;
			case MessageType.EXPORT:
				onExportMessageReceived(messageForm);
				break;
		}
	}

	private void onInitMessageReceived(MessageForm messageForm)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log("ModelManager[INIT]");
		}

		messageForm.setAddressee(mySim().findAgent(ComponentType.IO_AGENT));
		notice(messageForm);
		messageForm.setAddressee(mySim().findAgent(ComponentType.FACTORY_AGENT));
		notice(messageForm);
	}

	private void onImportMessageReceived(RollMessage rollMessage)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format(
				"ModelManager[IMPORT]\n - roll %s", rollMessage.getRoll()));
		}

		rollMessage.setAddressee(mySim().findAgent(ComponentType.FACTORY_AGENT));
		notice(rollMessage);
	}

	private void onExportMessageReceived(MessageForm message)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log("ModelManager[EXPORT]");
		}

		message.setAddressee(mySim().findAgent(ComponentType.FACTORY_AGENT));
		notice(message);
	}
}
