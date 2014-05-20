package sk.uniza.fri.II008.s3.simulation.managers;

import OSPABA.Agent;
import OSPABA.MessageForm;
import java.util.PriorityQueue;
import sk.uniza.fri.II008.s3.model.Navigation.Location;
import sk.uniza.fri.II008.s3.model.Vehicle;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.MessageType;
import sk.uniza.fri.II008.s3.simulation.messages.AssignVehicleMessage;
import sk.uniza.fri.II008.s3.simulation.messages.VehicleMessage;
import sk.uniza.fri.II008.s3.simulation.messages.TransferVehicleMessage;

public class VehicleManager extends BaseManager
{
	private final PriorityQueue<AssignVehicleMessage> requests;
	private static final Location START_LOCATION = Location.A;

	public VehicleManager(Agent agent)
	{
		super(ComponentType.VEHICLE_MANAGER, agent.mySim(), agent);

		agent.addOwnMessage(MessageType.ASSIGN_VEHICLE);
		agent.addOwnMessage(MessageType.TRANSFER_VEHICLE);
		agent.addOwnMessage(MessageType.TRANSFER_VEHICLE_DONE);
		agent.addOwnMessage(MessageType.RELEASE_VEHICLE);

		requests = new PriorityQueue<>();
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
			// [Request] Assign vehicle
			case MessageType.ASSIGN_VEHICLE:
				onAssignVehicleMessageReceived((AssignVehicleMessage) message);
				break;
			// [Request] Transfer vehicle
			case MessageType.TRANSFER_VEHICLE:
				onTransferVehicleMessageReceived((TransferVehicleMessage) message);
				break;
			// [Response] Vehicle transfered (called when vehicle is on required location)
			case MessageType.finish:
				message.setCode(MessageType.TRANSFER_VEHICLE_DONE);
				response(message);
				break;
			// [Response] Vehicle transfered (called when vehicle is back on START_LOCATION)
			case MessageType.TRANSFER_VEHICLE_DONE:
				((TransferVehicleMessage) message).onDone();
				break;
			// [Notice] Vehicle released
			case MessageType.RELEASE_VEHICLE:
				onReleaseVehicleMessageReceived((VehicleMessage) message);
				break;
		}
	}

	private void onAssignVehicleMessageReceived(AssignVehicleMessage vehicleMessage)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log("VehicleManager[ASSIGN_VEHICLE]");
		}

		Vehicle vehicle = findFreeVehicle();

		if (vehicle == null)
		{
			requests.add(vehicleMessage);
		}
		else
		{
			vehicle.setBusy(true);

			vehicleMessage.setVehicle(vehicle);
			vehicleMessage.setCode(MessageType.ASSIGN_VEHICLE_DONE);
			response(vehicleMessage);
		}
	}

	public void onTransferVehicleMessageReceived(TransferVehicleMessage transferVehicleMessage)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format(
				"VehicleManager[TRANSFER_VEHICLE]\n - vehicle %s from %s to %s",
				transferVehicleMessage.getVehicle(), transferVehicleMessage.getVehicle().getLocation(),
				transferVehicleMessage.getDestination()));
		}

		transferVehicleMessage.setAddressee(myAgent().findAssistant(ComponentType.VEHICLE_CONTINUAL_ASSISTANT));
		startContinualAssistant(transferVehicleMessage);
	}

	private void onReleaseVehicleMessageReceived(final VehicleMessage vehicleMessage)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format("VehicleManager[RELEASE_VEHICLE]\n - vehicle %s",
				vehicleMessage.getVehicle()));
		}

		TransferVehicleMessage transferVehicleMessage = new TransferVehicleMessage(
			_mySim, vehicleMessage.getVehicle(), START_LOCATION)
			{
				@Override
				public void onDone()
				{
					vehicleMessage.getVehicle().setBusy(false);

					if (getFactorySimulation().isEnabledLogging())
					{
						getFactoryReplication().log(String.format(
							"VehicleManager[RELEASE_VEHICLE]\n - vehicle %s released",
							vehicleMessage.getVehicle()));
					}

					if (!requests.isEmpty())
					{
						onAssignVehicleMessageReceived(requests.remove());
					}
				}
			};
		transferVehicleMessage.setCode(MessageType.TRANSFER_VEHICLE);
		transferVehicleMessage.setAddressee(_myAgent);

		request(transferVehicleMessage);
	}

	private Vehicle findFreeVehicle()
	{
		Vehicle vehicle = null;

		for (Vehicle v : getFactory().getVehicles())
		{
			if (!v.isBusy() && (vehicle == null || v.getSpeed() > vehicle.getSpeed()))
			{
				vehicle = v;
			}
		}

		return vehicle;
	}
}
