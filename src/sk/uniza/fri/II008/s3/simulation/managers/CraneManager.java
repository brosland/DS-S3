package sk.uniza.fri.II008.s3.simulation.managers;

import OSPABA.Agent;
import OSPABA.MessageForm;
import OSPDataStruct.SimQueue;
import java.util.HashMap;
import java.util.PriorityQueue;
import sk.uniza.fri.II008.s3.model.Crane;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.MessageType;
import sk.uniza.fri.II008.s3.simulation.messages.CraneTransportMessage;

public class CraneManager extends BaseManager
{
	private final HashMap<Crane, PriorityQueue<CraneTransportMessage>> requests;

	public CraneManager(Agent agent)
	{
		super(ComponentType.CRANE_MANAGER, agent.mySim(), agent);

		agent.addOwnMessage(MessageType.TRANSFER_CRANE);

		requests = new HashMap<>();

		for (Crane crane : getFactory().getCranes())
		{
			requests.put(crane, new PriorityQueue<CraneTransportMessage>());
		}
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
			// [Request] Transfer crane
			case MessageType.TRANSFER_CRANE:
				onTransferCraneMessageReceived((CraneTransportMessage) message);
				break;
			// [Notice] Crane transfered
			case MessageType.finish:
				message.setCode(MessageType.TRANSFER_CRANE_DONE);
				onTransferCraneDoneMessageReceived((CraneTransportMessage) message);
				break;
		}
	}

	private void onTransferCraneMessageReceived(CraneTransportMessage craneTransportMessage)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format("CraneManager[TRANSFER_CRANE]\n"
				+ " - crane %s", craneTransportMessage.getCrane()));
		}

		if (craneTransportMessage.getCrane().isBusy())
		{
			requests.get(craneTransportMessage.getCrane()).add(craneTransportMessage);
			return;
		}

		craneTransportMessage.getCrane().setBusy(true);

		craneTransportMessage.setAddressee(myAgent().findAssistant(ComponentType.CRANE_CONTINUAL_ASSISTANT));
		startContinualAssistant(craneTransportMessage);
	}

	private void onTransferCraneDoneMessageReceived(CraneTransportMessage craneTransportMessage)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format("CraneManager[TRANSFER_CRANE_DONE]\n"
				+ " - crane %s transfered", craneTransportMessage.getCrane()));
		}

		response(craneTransportMessage);

		craneTransportMessage.getCrane().setBusy(false);

		if (!requests.get(craneTransportMessage.getCrane()).isEmpty())
		{
			onTransferCraneMessageReceived(requests.get(craneTransportMessage.getCrane()).remove());
		}
	}
}
