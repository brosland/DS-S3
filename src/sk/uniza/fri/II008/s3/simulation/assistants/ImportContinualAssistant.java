package sk.uniza.fri.II008.s3.simulation.assistants;

import OSPABA.Agent;
import OSPABA.MessageForm;
import sk.uniza.fri.II008.generators.IContinuosGenerator;
import sk.uniza.fri.II008.s3.FactorySimulation;
import sk.uniza.fri.II008.s3.simulation.ComponentType;
import sk.uniza.fri.II008.s3.simulation.MessageType;
import sk.uniza.fri.II008.s3.model.Roll;
import sk.uniza.fri.II008.s3.simulation.messages.RollMessage;

public class ImportContinualAssistant extends BaseContinualAssistant
{
	private final IContinuosGenerator rollAImportTimeGen, rollBImportTimeGen, rollCImportTimeGen;

	public ImportContinualAssistant(Agent agent)
	{
		super(ComponentType.IMPORT_CONTINUAL_ASSISTANT, agent.mySim(), agent);

		rollAImportTimeGen = (IContinuosGenerator) getFactorySimulation().getGenerator(
			FactorySimulation.Generator.ROLL_A_IMPORT_TIME);
		rollBImportTimeGen = (IContinuosGenerator) getFactorySimulation().getGenerator(
			FactorySimulation.Generator.ROLL_B_IMPORT_TIME);
		rollCImportTimeGen = (IContinuosGenerator) getFactorySimulation().getGenerator(
			FactorySimulation.Generator.ROLL_C_IMPORT_TIME);

		agent.addOwnMessage(MessageType.IMPORT);
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
			case MessageType.start:
				for (Roll.Type rollType : Roll.Type.values())
				{
					hold(generateDuration(rollType), createImportMessage(rollType));
				}

				if (getFactorySimulation().isEnabledLogging())
				{
					getFactoryReplication().log("ImportContinualAssistant[start]");
				}
				break;

			case MessageType.IMPORT:
				if (getFactorySimulation().isEnabledLogging())
				{
					getFactoryReplication().log(String.format(
						"ImportContinualAssistant[IMPORT]\n - roll %s",
						((RollMessage) message).getRoll()));
				}

				assistantFinished(message);

				Roll.Type rollType = ((RollMessage) message).getRoll().getType();
				hold(generateDuration(rollType), createImportMessage(rollType));
				break;
		}
	}

	private RollMessage createImportMessage(Roll.Type rollType)
	{
		RollMessage message = new RollMessage(_mySim, new Roll(rollType));
		message.setCode(MessageType.IMPORT);

		return message;
	}

	private double generateDuration(Roll.Type rollType)
	{
		switch (rollType)
		{
			case A:
				return rollAImportTimeGen.nextValue();
			case B:
				return rollBImportTimeGen.nextValue();
			case C:
				return rollCImportTimeGen.nextValue();
		}

		throw new IllegalStateException("Unsupported roll type " + rollType + ".");
	}
}
