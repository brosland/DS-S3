package sk.uniza.fri.II008.s3.model;

public class Crane
{
	public static int LAST_ID = 0;
	private final int id;
	private boolean busy = false;

	public Crane()
	{
		this.id = ++LAST_ID;
	}

	public int getId()
	{
		return id;
	}

	public synchronized boolean isBusy()
	{
		return busy;
	}

	public void setBusy(boolean busy)
	{
		this.busy = busy;
	}

	@Override
	public String toString()
	{
		return String.format("[%d]", id);
	}

	public void reset()
	{
		busy = false;
	}
}
