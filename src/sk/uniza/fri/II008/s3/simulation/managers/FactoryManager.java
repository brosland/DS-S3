package sk.uniza.fri.II008.s3.simulation.managers;

import OSPABA.Agent;
import OSPABA.MessageForm;
import sk.uniza.fri.II008.s3.FactorySimulation.Generator;
import sk.uniza.fri.II008.s3.model.*;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.generators.ImportElevatorGenerator;
import sk.uniza.fri.II008.s3.simulation.MessageType;
import sk.uniza.fri.II008.s3.simulation.messages.RollMessage;
import sk.uniza.fri.II008.s3.simulation.messages.StorageMessage;
import sk.uniza.fri.II008.s3.simulation.messages.TransportRollMessage;

public class FactoryManager extends BaseManager
{
	private final ImportElevatorGenerator importElevatorGenerator;

	public FactoryManager(Agent agent)
	{
		super(ComponentType.FACTORY_MANAGER, agent.mySim(), agent);

		agent.addOwnMessage(MessageType.INIT);
		agent.addOwnMessage(MessageType.IMPORT);
		agent.addOwnMessage(MessageType.EMPTY_STORAGE);
		agent.addOwnMessage(MessageType.TRANSPORT_ROLL_DONE);
		agent.addOwnMessage(MessageType.PROCESS_ROLL_DONE);
		agent.addOwnMessage(MessageType.EXPORT);

		importElevatorGenerator = (ImportElevatorGenerator) getFactorySimulation()
			.getGenerator(Generator.IMPORT_ELEVATOR);
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
			case MessageType.INIT:
				onInitMessageReceived();
				break;
			case MessageType.IMPORT:
				onImportMessageReceived((RollMessage) message);
				break;
			case MessageType.EMPTY_STORAGE:
				onEmptyStorageMessageReceived((StorageMessage) message);
				break;
			case MessageType.TRANSPORT_ROLL_DONE:
				onTransportRollDoneMessage((TransportRollMessage) message);
				break;
			case MessageType.PROCESS_ROLL_DONE:
				onProcessRollDoneReceived((RollMessage) message);
				break;
			case MessageType.finish:
				message.setCode(MessageType.PREPARE_ROLL_TO_EXPORT_DONE);
				onPrepareRollToExportDoneMessageReceived((RollMessage) message);
				break;
			case MessageType.EXPORT:
				onExportMessageReceived();
				break;
		}
	}

	private void onInitMessageReceived()
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log("FactoryManager[INIT]");
		}

//		Storage processingStorage = null;
//		// find max filled processing storage
//		for (Roll.Type rollType : Roll.Type.values())
//		{
//			Storage s = getFactory().getProcessingStorage(rollType);
//
//			if (processingStorage == null || processingStorage.getFilling() < s.getFilling())
//			{
//				processingStorage = s;
//			}
//		}
//
//		// 2. zhodnotí, ktorý sklad je na tom najhoršie a v tom sklade dá opracovať nejakú rolku
//		// 3. ak je niečo, čo sa môže odviesť zo skladu na odkladacie miesto, povie StorageManagerovi aby to zabezpečil
//		// 4. ak je niečo, čo sa môže preniesť na export, --//--
//		throw new UnsupportedOperationException("Not supported yet.");
	}

	private void onImportMessageReceived(RollMessage rollMessage)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format(
				"FactoryManager[IMPORT]\n - roll %s", rollMessage.getRoll()));
		}

		Roll roll = rollMessage.getRoll();
		Elevator elevator = roll.getType() == Roll.Type.C
			? importElevatorGenerator.nextValue() : getFactory().getImportElevator(roll.getType());
		elevator.addRoll(roll);
		Storage storage = getFactory().getProcessingStorage(roll.getType());

		if (storage.getReservation(roll))
		{
			transportRoll(roll, elevator, storage);
		}
	}

	private void onEmptyStorageMessageReceived(StorageMessage storageMessage)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format(
				"FactoryManager[EMPTY_STORAGE]\n - storage %s", storageMessage.getStorage()));
		}

		Roll roll = null;
		RollStorage from = null;

		if (storageMessage.getStorage() instanceof ProcessingStorage)
		{
			ProcessingStorage processingStorage = (ProcessingStorage) storageMessage.getStorage();
			Roll.Type rollType = processingStorage.getRollType();

			for (Elevator elevator : getFactory().getImportElevators())
			{
				if ((from == null || from.getFilling() < elevator.getFilling())
					&& elevator.hasRoll(Roll.State.UNPROCESSED, rollType))
				{
					from = elevator;
					roll = elevator.getRoll(Roll.State.UNPROCESSED, rollType);
				}
			}
		}
		else // výber skladu (S1, S2, S3) z ktorého sa presunie rolka do tohto (chladiacého) skladu
		{
			for (Roll.Type rollType : Roll.Type.values())
			{
				ProcessingStorage storage = getFactory().getProcessingStorage(rollType);

				if (storage.hasRoll(Roll.State.PROCESSED)
					&& (from == null || from.getFilling() < storage.getFilling()))
				{
					from = storage;
					roll = storage.getRoll(Roll.State.PROCESSED);
				}
			}
		}

		if (roll != null && storageMessage.getStorage().getReservation(roll))
		{
			transportRoll(roll, from, storageMessage.getStorage());
		}
	}

	private void onTransportRollDoneMessage(TransportRollMessage transportRollMessage)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format(
				"FactoryManager[TRANSPORT_ROLE_DONE]\n - role %s transported from %s to %s",
				transportRollMessage.getRoll(), transportRollMessage.getFrom(), transportRollMessage.getTo()));
		}

		if (transportRollMessage.getTo() instanceof ProcessingStorage) // processing storage
		{
			transportRollMessage.getRoll().setState(Roll.State.UNPROCESSED);

			RollMessage rollMessage = new RollMessage(_mySim, transportRollMessage.getRoll());
			rollMessage.setCode(MessageType.PROCESS_ROLL);
			rollMessage.setAddressee(_mySim.findAgent(ComponentType.EMPLOYEE_AGENT));

			request(rollMessage);
		}
		else if (transportRollMessage.getTo() instanceof Storage) // cooling storage
		{
			transportRollMessage.getRoll().setState(Roll.State.PROCESSED);

			RollMessage rollMessage = new RollMessage(_mySim, transportRollMessage.getRoll());
			rollMessage.setAddressee(_myAgent.findAssistant(ComponentType.COOLING_CONTINUAL_ASSISTANT));
			startContinualAssistant(rollMessage);
		}
		else
		{
			transportRollMessage.getRoll().setState(Roll.State.READY);
		}
	}

	private void onProcessRollDoneReceived(RollMessage rollMessage)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format(
				"FactoryManager[PROCESS_ROLL_DONE]\n - roll %s processed", rollMessage.getRoll()));
		}

		Storage storage = getFactory().getCoolingStorage();
		Roll roll = rollMessage.getRoll();

		if (storage.getReservation(roll))
		{
			transportRoll(roll, roll.getRollStorage(), storage);
		}
	}

	private void onPrepareRollToExportDoneMessageReceived(RollMessage rollMessage)
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format(
				"FactoryManager[PREPARE_ROLL_TO_EXPORT_DONE]\n - roll %s prepared to export",
				rollMessage.getRoll()));
		}

		Elevator elevator = getFactory().getExportElevator();
		Roll roll = rollMessage.getRoll();

		if (elevator.getReservation(roll))
		{
			transportRoll(roll, roll.getRollStorage(), elevator);
		}
	}

	private void onExportMessageReceived()
	{
		if (getFactorySimulation().isEnabledLogging())
		{
			getFactoryReplication().log(String.format("FactoryManager[EXPORT]"));
		}

		Elevator elevator = getFactory().getExportElevator();

		if (elevator.hasRoll(Roll.State.READY))
		{
			Roll roll = elevator.getRoll(Roll.State.READY);
			elevator.removeRoll(roll);

			if (getFactorySimulation().isEnabledLogging())
			{
				getFactoryReplication().log(String.format(
					"FactoryManager[EXPORT]\n - roll %s exported", roll));
			}
		}

		Storage storage = getFactory().getCoolingStorage();

		if (storage.hasRoll(Roll.State.READY))
		{
			Roll roll = storage.getRoll(Roll.State.READY);

			if (elevator.getReservation(roll))
			{
				transportRoll(storage.getRoll(Roll.State.READY), storage, elevator);
			}
		}
	}

	private void transportRoll(Roll roll, RollStorage from, RollStorage to)
	{
		roll.setState(Roll.State.TRANSPORTING);

		TransportRollMessage transportRollMessage = new TransportRollMessage(
			_mySim, roll, from, to);
		transportRollMessage.setCode(MessageType.TRANSPORT_ROLL);
		transportRollMessage.setAddressee(_mySim.findAgent(ComponentType.TRANSPORT_AGENT));

		request(transportRollMessage);
	}
}
