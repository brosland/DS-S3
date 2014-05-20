package sk.uniza.fri.II008.s3.simulation.managers;

import OSPABA.Agent;
import OSPABA.MessageForm;
import sk.uniza.fri.II008.s3.model.*;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.MessageType;
import sk.uniza.fri.II008.s3.simulation.messages.*;

public class TransportManager extends BaseManager
{
	public TransportManager(Agent agent)
	{
		super(ComponentType.TRANSPORT_MANAGER, agent.mySim(), agent);

		agent.addOwnMessage(MessageType.TRANSPORT_ROLL);
		agent.addOwnMessage(MessageType.ASSIGN_VEHICLE_DONE);
		agent.addOwnMessage(MessageType.TRANSFER_CRANE_DONE);
		agent.addOwnMessage(MessageType.TRANSFER_VEHICLE_DONE);
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
			case MessageType.TRANSPORT_ROLL:
				onTransportRollMessageReceived((TransportRollMessage) message);
				break;
			case MessageType.ASSIGN_VEHICLE_DONE:
				((AssignVehicleMessage) message).onDone();
				break;
			case MessageType.TRANSFER_CRANE_DONE:
				((CraneTransportMessage) message).onDone();
				break;
			case MessageType.TRANSFER_VEHICLE_DONE:
				((TransferVehicleMessage) message).onDone();
				break;
		}
	}

	private void onTransportRollMessageReceived(final TransportRollMessage transportRollMessage)
	{
		final RollStorage from = transportRollMessage.getFrom();
		final RollStorage to = transportRollMessage.getTo();
		final Roll roll = transportRollMessage.getRoll();

		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format("TransportManager[TRANSPORT_ROLL]\n"
				+ " - transport roll %s from %s to %s", roll, from, to));
		}

		if (Navigation.getDistance(from, to) > 0.0)
		{
			final AssignVehicleMessage assignVehicleMessage = new AssignVehicleMessage(_mySim, from)
			{
				@Override
				public void onDone()
				{
					onVehicleAssigned(transportRollMessage, this.getVehicle());
				}
			};
			assignVehicleMessage.setCode(MessageType.ASSIGN_VEHICLE);
			assignVehicleMessage.setAddressee(_mySim.findAgent(ComponentType.VEHICLE_AGENT));

			request(assignVehicleMessage);
		}
		else
		{
			Crane crane = from.getCrane();
			CraneTransportMessage craneMessage = new CraneTransportMessage(
				_mySim, crane, roll, from, to)
				{
					@Override
					public void onDone()
					{
						if (from instanceof Storage)
						{
							notice(createEmptyStorageMessage((Storage) from));
						}

						onRollTransported(transportRollMessage);
					}
				};
			craneMessage.setCode(MessageType.TRANSFER_CRANE);
			craneMessage.setAddressee(mySim().findAgent(ComponentType.CRANE_AGENT));

			request(craneMessage);
		}
	}

	private void onVehicleAssigned(final TransportRollMessage transportRollMessage, final Vehicle vehicle)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format("TransportManager[TRANSPORT_ROLL]\n"
				+ " - vehicle %s assigned for roll %s", vehicle, transportRollMessage.getRoll()));
		}

		TransferVehicleMessage transferVehicleMessage = new TransferVehicleMessage(
			_mySim, vehicle, transportRollMessage.getFrom().getLocation())
			{
				@Override
				public void onDone()
				{
					onVehiclePrepared(transportRollMessage, vehicle);
				}
			};
		transferVehicleMessage.setCode(MessageType.TRANSFER_VEHICLE);
		transferVehicleMessage.setAddressee(_mySim.findAgent(ComponentType.VEHICLE_AGENT));

		request(transferVehicleMessage);
	}

	private void onVehiclePrepared(final TransportRollMessage transportRollMessage, final Vehicle vehicle)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format("TransportManager[TRANSPORT_ROLL]\n"
				+ " - vehicle %s prepared for loading roll %s", vehicle, transportRollMessage.getRoll()));
		}

		final Crane crane = transportRollMessage.getFrom().getCrane();
		CraneTransportMessage craneMessage = new CraneTransportMessage(
			_mySim, crane, transportRollMessage.getRoll(), transportRollMessage.getFrom(), vehicle)
			{
				@Override
				public void onDone()
				{
					if (getFactorySimulation().isEnabledLogging())
					{
						getFactoryReplication().log(String.format("TransportManager[TRANSPORT_ROLL]\n"
								+ " - crane %s transfered role %s from %s on vehicle %s",
								crane, transportRollMessage.getRoll(), transportRollMessage.getFrom(), vehicle));
					}

					if (transportRollMessage.getFrom() instanceof Storage)
					{
						notice(createEmptyStorageMessage((Storage) transportRollMessage.getFrom()));
					}

					onVehicleLoaded(transportRollMessage, vehicle);
				}
			};
		craneMessage.setCode(MessageType.TRANSFER_CRANE);
		craneMessage.setAddressee(mySim().findAgent(ComponentType.CRANE_AGENT));

		request(craneMessage);
	}

	private void onVehicleLoaded(final TransportRollMessage transportRollMessage, final Vehicle vehicle)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format("TransportManager[TRANSPORT_ROLL]\n"
				+ " - vehicle %s has loaded roll %s", vehicle, transportRollMessage.getRoll()));
		}

		TransferVehicleMessage transferVehicleMessage = new TransferVehicleMessage(
			_mySim, vehicle, transportRollMessage.getTo().getLocation())
			{
				@Override
				public void onDone()
				{
					onVehicleTransfered(transportRollMessage, vehicle);
				}
			};

		transferVehicleMessage.setCode(MessageType.TRANSFER_VEHICLE);
		transferVehicleMessage.setAddressee(_mySim.findAgent(ComponentType.VEHICLE_AGENT));

		request(transferVehicleMessage);
	}

	private void onVehicleTransfered(final TransportRollMessage transportRollMessage, final Vehicle vehicle)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format("TransportManager[TRANSPORT_ROLL]\n"
				+ " - vehicle %s transported roll %s from %s to %s",
				vehicle, transportRollMessage.getRoll(), transportRollMessage.getFrom(), transportRollMessage.getTo()));
		}

		final Crane crane = transportRollMessage.getTo().getCrane();
		CraneTransportMessage craneMessage = new CraneTransportMessage(
			_mySim, crane, transportRollMessage.getRoll(), vehicle, transportRollMessage.getTo())
			{
				@Override
				public void onDone()
				{
					onCraneUnloadVehicle(transportRollMessage, vehicle);
				}
			};
		craneMessage.setCode(MessageType.TRANSFER_CRANE);
		craneMessage.setAddressee(mySim().findAgent(ComponentType.CRANE_AGENT));
		request(craneMessage);
	}

	private void onCraneUnloadVehicle(TransportRollMessage transportRollMessage, Vehicle vehicle)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format("TransportManager[TRANSPORT_ROLL]\n"
				+ " - crane %s transfered role %s from vehicle %s to %s",
				transportRollMessage.getFrom().getCrane(), transportRollMessage.getRoll(),
				vehicle, transportRollMessage.getFrom()));
		}

		VehicleMessage vehicleMessage = new VehicleMessage(_mySim)
		{
			@Override
			public void onDone()
			{
			}
		};
		vehicleMessage.setVehicle(vehicle);
		vehicleMessage.setCode(MessageType.RELEASE_VEHICLE);

		notice(vehicleMessage);

		onRollTransported(transportRollMessage);
	}

	private void onRollTransported(TransportRollMessage transportRollMessage)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format(
				"TransportManager[TRANSPORT_ROLL_DONE]\n - role %s transported from %s to %s",
				transportRollMessage.getRoll(), transportRollMessage.getFrom(), transportRollMessage.getTo()));
		}

		transportRollMessage.setCode(MessageType.TRANSPORT_ROLL_DONE);

		response(transportRollMessage);
	}

	private StorageMessage createEmptyStorageMessage(Storage storage)
	{
		StorageMessage storageMessage = new StorageMessage(_mySim, storage);
		storageMessage.setCode(MessageType.EMPTY_STORAGE);
		storageMessage.setAddressee(_mySim.findAgent(ComponentType.FACTORY_AGENT));

		return storageMessage;
	}
}
