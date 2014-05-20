package sk.uniza.fri.II008.s3.model;

import sk.uniza.fri.II008.s3.model.requests.CraneRequest;

public class Crane
{
	public static int LAST_ID = 0;
	private final int id;
	private boolean busy = false;
	private CraneRequest craneRequest = null;

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

	public boolean hasCraneRequest()
	{
		return craneRequest != null;
	}

	public CraneRequest getCraneRequest()
	{
		return craneRequest;
	}

	public void setCraneRequest(CraneRequest craneRequest)
	{
		this.craneRequest = craneRequest;
	}

	public void removeCraneRequest()
	{
		craneRequest = null;
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
