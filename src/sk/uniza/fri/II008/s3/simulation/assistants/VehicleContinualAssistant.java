package sk.uniza.fri.II008.s3.simulation.assistants;

import OSPABA.Agent;
import OSPABA.MessageForm;
import sk.uniza.fri.II008.s3.model.Navigation;
import sk.uniza.fri.II008.s3.model.Vehicle;
import sk.uniza.fri.II008.s3.model.requests.VehicleRequest;
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
		switch (message.code())
		{
			case MessageType.start:
				onStartMessageReceived((TransferVehicleMessage) message);
				break;
			case MessageType.TRANSFER_VEHICLE_DONE:
				onTransferVehicleDone((TransferVehicleMessage) message);
				break;
		}
	}

	private void onStartMessageReceived(TransferVehicleMessage transferVehicleMessage)
	{
		Vehicle vehicle = transferVehicleMessage.getVehicle();

		double distance = Navigation.getDistance(vehicle.getLocation(),
			transferVehicleMessage.getDestination());
		double duration = Navigation.getDuration(vehicle.getLocation(),
			transferVehicleMessage.getDestination(), vehicle.getSpeed()) * 3600;

		VehicleRequest vehicleRequest = new VehicleRequest(_mySim.currentTime(),
			duration, vehicle.getLocation(), transferVehicleMessage.getDestination());

		vehicle.setVehicleRequest(vehicleRequest);

		transferVehicleMessage.setCode(MessageType.TRANSFER_VEHICLE_DONE);
		hold(duration, transferVehicleMessage);

		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format("VehicleContinualAssistant[start]\n"
				+ " - vehicle %s is transfering from %s to %s (distance: %.2fkm/h, %.2fs)",
				vehicle, vehicle.getLocation(), transferVehicleMessage.getDestination(), distance, duration));
		}
	}

	private void onTransferVehicleDone(TransferVehicleMessage transferVehicleMessage)
	{
		Vehicle vehicle = transferVehicleMessage.getVehicle();
		vehicle.setLocation(transferVehicleMessage.getDestination());
		vehicle.removeVehicleRequest();

		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format("VehicleContinualAssistant[TRANSFER_VEHICLE_DONE]\n"
				+ " - vehicle %s transfered", vehicle));
		}

		assistantFinished(transferVehicleMessage);
	}
}
