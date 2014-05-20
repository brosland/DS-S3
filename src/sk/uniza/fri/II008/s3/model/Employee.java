package sk.uniza.fri.II008.s3.model;

public class Employee
{
	public enum State
	{
		FREE("Voľný"), BUSY("Zaneprázdnený"), TRANSFERING("V stave presunu");

		public final String label;

		private State(String label)
		{
			this.label = label;
		}
	}

	public static int LAST_ID = 0;
	private final int id;
	private State state = State.FREE;
	private ProcessingStorage storage = null;

	public Employee()
	{
		this.id = ++LAST_ID;
	}

	public int getId()
	{
		return id;
	}

	public synchronized State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	public synchronized ProcessingStorage getCurrentStorage()
	{
		return storage;
	}

	public void setCurrentStorage(ProcessingStorage storage)
	{
		this.storage = storage;
	}

	@Override
	public String toString()
	{
		return String.format("[%d]", id);
	}

	public void reset()
	{
		state = State.FREE;
	}
}
