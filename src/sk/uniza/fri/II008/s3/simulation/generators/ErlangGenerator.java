package sk.uniza.fri.II008.s3.simulation.generators;

import OSPRNG.ErlangRNG;
import sk.uniza.fri.II008.generators.IContinuosGenerator;

public class ErlangGenerator implements IContinuosGenerator
{
	private final ErlangRNG erlangRNG;

	public ErlangGenerator(double average, double dispersion)
	{
		erlangRNG = new ErlangRNG(average, dispersion);
	}

	@Override
	public double nextValue()
	{
		return erlangRNG.sample();
	}
}
