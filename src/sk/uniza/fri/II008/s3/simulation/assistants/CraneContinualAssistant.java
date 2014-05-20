package sk.uniza.fri.II008.s3.simulation.assistants;

import OSPABA.Agent;
import OSPABA.MessageForm;
import sk.uniza.fri.II008.generators.IContinuosGenerator;
import sk.uniza.fri.II008.s3.FactorySimulation;
import sk.uniza.fri.II008.s3.model.Crane;
import sk.uniza.fri.II008.s3.model.requests.CraneRequest;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.MessageType;
import sk.uniza.fri.II008.s3.simulation.messages.CraneTransportMessage;

public class CraneContinualAssistant extends BaseContinualAssistant
{
	private final IContinuosGenerator craneTransferDurationGen;

	public CraneContinualAssistant(Agent agent)
	{
		super(ComponentType.CRANE_CONTINUAL_ASSISTANT, agent.mySim(), agent);

		craneTransferDurationGen = ((IContinuosGenerator) getFactorySimulation().getGenerator(
			FactorySimulation.Generator.CRANE_TRANSFER_DURATION));

		agent.addOwnMessage(MessageType.TRANSFER_CRANE_DONE);
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
			case MessageType.start:
				onStartMessageReceived((CraneTransportMessage) message);
				break;
			case MessageType.TRANSFER_CRANE_DONE:
				onTransferCraneDoneMessageReceived((CraneTransportMessage) message);
				break;
		}
	}

	private void onStartMessageReceived(CraneTransportMessage craneTransportMessage)
	{
		Crane crane = craneTransportMessage.getCrane();

		double duration = craneTransferDurationGen.nextValue();

		CraneRequest craneRequest = new CraneRequest(_mySim.currentTime(), duration,
			craneTransportMessage.getRoll(), craneTransportMessage.getFrom(), craneTransportMessage.getTo());
		crane.setCraneRequest(craneRequest);

		craneTransportMessage.setCode(MessageType.TRANSFER_CRANE_DONE);
		hold(duration, craneTransportMessage);

		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format("CraneContinualAssistant[start]\n"
				+ " - crane %s is transfering", crane));
		}
	}

	private void onTransferCraneDoneMessageReceived(CraneTransportMessage craneTransportMessage)
	{
		Crane crane = craneTransportMessage.getCrane();
		crane.removeCraneRequest();

		craneTransportMessage.getFrom().removeRoll(craneTransportMessage.getRoll());
		craneTransportMessage.getTo().addRoll(craneTransportMessage.getRoll());

		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format("CraneContinualAssistant[TRANSFER_CRANE_DONE]\n"
				+ " - crane %s transfered", crane));
		}

		assistantFinished(craneTransportMessage);
	}
}
