package sk.uniza.fri.II008.s3.model;

public class Roll
{
	public interface ChangeStateListener
	{
		public void onChange(Roll roll, State oldState);

		public static ChangeStateListener VOID = new ChangeStateListener()
		{
			@Override
			public void onChange(Roll roll, State oldState)
			{
			}
		};
	}

	public enum State
	{
		UNPROCESSED("Nespracovaná"),
		PROCESSING("V stave spracovania"),
		PROCESSED("Spracovaná"),
		COOLING("V stave chladenia"),
		READY("Hotová"),
		TRANSPORTING("V stave presunu");

		public final String label;

		private State(String label)
		{
			this.label = label;
		}
	}

	public enum Type
	{
		A, B, C
	}

	public static int LAST_ID = 0;
	private final int id;
	private final Type type;
	private State state = State.UNPROCESSED;
	private RollStorage rollStorage;
	private ChangeStateListener listener = ChangeStateListener.VOID;

	public Roll(Type type)
	{
		this.id = ++LAST_ID;
		this.type = type;
	}

	public int getId()
	{
		return id;
	}

	public Type getType()
	{
		return type;
	}

	public synchronized State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		State oldState = this.state;

		this.state = state;

		listener.onChange(this, oldState);
	}

	public void setListener(ChangeStateListener listener)
	{
		this.listener = listener;
	}

	public void removeListener()
	{
		listener = ChangeStateListener.VOID;
	}

	public boolean isInRollStorage()
	{
		return rollStorage != null;
	}

	public synchronized RollStorage getRollStorage()
	{
		return rollStorage;
	}

	public void setRollStorage(RollStorage rollStorage)
	{
		this.rollStorage = rollStorage;
	}

	@Override
	public String toString()
	{
		return String.format("[%d] %s", id, type);
	}
}
