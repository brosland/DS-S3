package sk.uniza.fri.II008.s3;

import OSPABA.ISimDelegate;
import OSPABA.MessageForm;
import OSPABA.SimState;
import java.util.Random;
import sk.uniza.fri.II008.Simulation;
import sk.uniza.fri.II008.generators.*;
import sk.uniza.fri.II008.s3.model.Employee;
import sk.uniza.fri.II008.s3.model.Factory;
import sk.uniza.fri.II008.s3.model.Roll;
import sk.uniza.fri.II008.s3.simulation.MessageType;
import sk.uniza.fri.II008.s3.simulation.generators.ImportElevatorGenerator;
import sk.uniza.fri.II008.s3.simulation.generators.ErlangGenerator;

public class FactorySimulation extends Simulation
{
	public enum Generator implements IGenerator.IGeneratorType
	{
		ROLL_A_IMPORT_TIME, ROLL_B_IMPORT_TIME, ROLL_C_IMPORT_TIME, ROLL_EXPORT_TIME,
		CRANE_TRANSFER_DURATION, IMPORT_ELEVATOR, PROCESS_ROLL_DURATION,
		EMPLOYEE_TRANSFER_DURATION, ROLL_COOLING_DURATION
	}

	private final Factory factory;
	private final double maxTimestamp;
	private final ISimDelegate simDelegate;
	private FactoryReplication factoryReplication;
	private double pauseInterval, pauseDuration;
	private FactoryStats factoryStats;

	public FactorySimulation(Random seedGen, long replicationCount, double maxTimestamp, Factory factory)
	{
		super(replicationCount, 1);

		this.maxTimestamp = maxTimestamp;
		this.factory = factory;

		registerGenerators(seedGen);

		simDelegate = new ISimDelegate()
		{
			@Override
			public void simStateChanged(OSPABA.Simulation replication, SimState state)
			{
			}

			@Override
			public void refresh(OSPABA.Simulation simulation)
			{
				replicationListener.onChange();
			}
		};

		factoryStats = new FactoryStats(factory);
	}

	private void registerGenerators(Random seedGen)
	{
		IContinuosGenerator rollAImportTime = new ContinuousExponencialGenerator(
			new Random(seedGen.nextInt()), 1.0 / 1490, 87.0);
		generators.put(Generator.ROLL_A_IMPORT_TIME, rollAImportTime);

		IContinuosGenerator rollBImportTime = new ContinuousExponencialGenerator(
			new Random(seedGen.nextInt()), 1.0 / 2280, 178.0);
		generators.put(Generator.ROLL_B_IMPORT_TIME, rollBImportTime);

		IContinuosGenerator rollCImportTime = new ContinuousExponencialGenerator(
			new Random(seedGen.nextInt()), 1.0 / 1460, 102.0);
		generators.put(Generator.ROLL_C_IMPORT_TIME, rollCImportTime);

		IContinuosGenerator rollExportTime = new ContinuosDeterministicGenerator(590.0);
		generators.put(Generator.ROLL_EXPORT_TIME, rollExportTime);

		IContinuosGenerator craneTransferDurationGen = new ContinuousGenerator(
			new Random(seedGen.nextInt()), 15.0, 69.0);
		generators.put(Generator.CRANE_TRANSFER_DURATION, craneTransferDurationGen);

		generators.put(Generator.IMPORT_ELEVATOR, new ImportElevatorGenerator(seedGen, factory));

		IContinuosGenerator processRollDurationGen = new ContinuousTriangularGenerator(
			new Random(seedGen.nextInt()), 0.03 * 3600, 0.87 * 3600, 0.17 * 3600);		// values in hours
		generators.put(Generator.PROCESS_ROLL_DURATION, processRollDurationGen);

		generators.put(Generator.EMPLOYEE_TRANSFER_DURATION, new ContinuosDeterministicGenerator(13.0 * 60));

		generators.put(Generator.ROLL_COOLING_DURATION, new ErlangGenerator(0.11 * 3600, 0.07 * 3600));
	}

	@Override
	public void pause()
	{
		if (getState() == State.RUNNING)
		{
			factoryReplication.pauseSimulation();
		}
		else if (getState() == State.PAUSED)
		{
			factoryReplication.resumeSimulation();
		}

		super.pause();
	}

	@Override
	public void stop()
	{
		if (getState() != State.STOPPED)
		{
			factoryReplication.stopSimulation();
		}

		super.stop();
	}

	@Override
	protected void prepareReplication(long replication)
	{
		if (isEnabledLogging())
		{
			log("Preparing replication " + replication);
		}

		factory.reset();
		factory.initProcessingStorage(Roll.Type.A, 0.5, 0.6);
		factory.initProcessingStorage(Roll.Type.B, 0.33, 0.4);
		factory.initProcessingStorage(Roll.Type.C, 0.65, 0.55);
		factory.initCoolingStorage(0.51, 0.35);

		for (int i = 0; i < factory.getEmployees().size(); i++)
		{
			factory.getEmployees().get(i).setCurrentStorage(
				factory.getProcessingStorage(Roll.Type.values()[i % 3]));
		}

		factoryReplication = new FactoryReplication(this);
		factoryReplication.registerDelegate(simDelegate);

		if (pauseInterval == UNLIMITED || pauseDuration == UNLIMITED)
		{
			factoryReplication.setMaxSimSpeed();
		}
		else
		{
			factoryReplication.setSimSpeed(pauseInterval, pauseDuration);
		}

		factoryStats.prepareReplication(factoryReplication);

		MessageForm message = new MessageForm(factoryReplication);
		message.setCode(MessageType.INIT);
		message.setAddressee(factoryReplication.boss());

		factoryReplication.boss().manager().notice(message);
	}

	@Override
	protected Object[] runReplication(long replication)
	{
		if (isEnabledLogging())
		{
			log("Running replication " + replication);
		}

		factoryReplication.simulate(maxTimestamp);

		factoryStats.addReplicationResults(factoryReplication);

		return new Object[0];
	}

	public double getMaxReplicationTimestamp()
	{
		return maxTimestamp;
	}

	public synchronized Factory getFactory()
	{
		return factory;
	}

	public FactoryReplication getCurrentReplication()
	{
		return factoryReplication;
	}

	public void setMaxSpeed()
	{
		pauseInterval = pauseDuration = UNLIMITED;

		if (factoryReplication != null)
		{
			factoryReplication.setMaxSimSpeed();
		}
	}

	public void setSpeed(double interval, double duration)
	{
		pauseInterval = interval;
		pauseDuration = duration;

		if (factoryReplication != null)
		{
			factoryReplication.setSimSpeed(interval, duration);
		}
	}

	public FactoryStats getFactoryStats()
	{
		return factoryStats;
	}
}
