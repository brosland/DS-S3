package sk.uniza.fri.II008.s3.simulation.generators;

import java.util.Random;
import java.util.TreeMap;
import sk.uniza.fri.II008.generators.*;
import sk.uniza.fri.II008.s3.model.Elevator;
import sk.uniza.fri.II008.s3.model.Factory;
import sk.uniza.fri.II008.s3.model.Roll;

public class ImportElevatorGenerator implements IGenerator
{
	private final Factory factory;
	private final IDiscreteGenerator elevatorGen;

	public ImportElevatorGenerator(Random seedGen, Factory factory)
	{
		this.factory = factory;

		TreeMap<Double, IDiscreteGenerator> values = new TreeMap<>();
		values.put(0.3, new DiscreteDeterministicGenerator(Roll.Type.A.ordinal()));
		values.put(1.0, new DiscreteDeterministicGenerator(Roll.Type.B.ordinal()));

		elevatorGen = new DiscreteEmpiricGenerator(
			new ContinuousGenerator(new Random(seedGen.nextInt()), 0f, 1f), values);
	}

	public Elevator nextValue()
	{
		Roll.Type rollType = Roll.Type.values()[elevatorGen.nextValue()];

		return factory.getImportElevator(rollType);
	}
}
