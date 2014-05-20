package sk.uniza.fri.II008.s3.simulation.assistants;

import OSPABA.Agent;
import OSPABA.MessageForm;
import sk.uniza.fri.II008.s3.model.Navigation;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.MessageType;
import sk.uniza.fri.II008.s3.simulation.messages.TransferVehicleMessage;

public class VehicleContinualAssistant extends BaseContinualAssistant
{
	public VehicleContinualAssistant(Agent agent)
	{
		super(ComponentType.VEHICLE_CONTINUAL_ASSISTANT, agent.mySim(), agent);
		
		agent.addOwnMessage(MessageType.TRANSFER_VEHICLE_DONE);
	}

	@Override
	public void processMessage(MessageForm message)
	{
		TransferVehicleMessage tvMessage = (TransferVehicleMessage) message;

		switch (message.code())
		{
			case MessageType.start:
				
				tvMessage.setCode(MessageType.TRANSFER_VEHICLE_DONE);
				
				double distance = Navigation.getDistance(tvMessage.getVehicle().getLocation(),
					tvMessage.getDestination());

				double duration = Navigation.getDuration(tvMessage.getVehicle().getLocation(),
					tvMessage.getDestination(), tvMessage.getVehicle().getSpeed());

				hold(duration, tvMessage);

				if (getFactorySimulation().isEnabledLogging())
				{
					getFactoryReplication().log(String.format("VehicleContinualAssistant[start]\n"
						+ " - vehicle %s is transfering from %s to %s (distance: %.2fkm/h, %.2fs)",
						tvMessage.getVehicle(), tvMessage.getVehicle().getLocation(),
						tvMessage.getDestination(), distance, duration));
				}
				break;
			case MessageType.TRANSFER_VEHICLE_DONE:
				if (getFactorySimulation().isEnabledLogging())
				{
					getFactoryReplication().log(String.format(
						"VehicleContinualAssistant[TRANSFER_VEHICLE_DONE]\n - vehicle %s transfered",
						tvMessage.getVehicle()));
				}

				assistantFinished(message);
				break;
		}
	}
}
