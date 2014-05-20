package sk.uniza.fri.II008.s3.model;

import java.util.List;

public interface RollStorage extends Navigation.Point, RollStorable
{
	public int getId();

	public String getName();

	public int getCapacity();

	public boolean isEmpty();

	public boolean isFull();

	public double getFilling();

	public List<Roll> getRolls();

	public Crane getCrane();

	public void reset();
}
