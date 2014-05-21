package sk.uniza.fri.II008.s3.model;

import sk.uniza.fri.II008.s3.model.requests.BaseRequest;
import sk.uniza.fri.II008.s3.model.requests.EmployeeWorkRequest;
import sk.uniza.fri.II008.s3.model.requests.TransferEmployeeRequest;

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
	private double workingTime;
	private ProcessingStorage storage = null;
	private BaseRequest employeeRequest = null;

	public Employee()
	{
		this.id = ++LAST_ID;
		workingTime = 0f;
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

	public double getWorkingTime()
	{
		return workingTime;
	}

	public double getCurrentWorkingTime(double currentTimestamp)
	{
		if (hasEmployeeRequest() && employeeRequest.getEndTimestamp() > currentTimestamp)
		{
			return workingTime - (employeeRequest.getEndTimestamp() - currentTimestamp);
		}

		return workingTime;
	}

	public synchronized ProcessingStorage getCurrentStorage()
	{
		return storage;
	}

	public void setCurrentStorage(ProcessingStorage storage)
	{
		this.storage = storage;
	}

	public boolean hasEmployeeRequest()
	{
		return employeeRequest != null;
	}

	public BaseRequest getEmployeeRequest()
	{
		return employeeRequest;
	}

	public void setEmployeeRequest(BaseRequest employeeRequest)
	{
		if (!(employeeRequest instanceof TransferEmployeeRequest
			|| employeeRequest instanceof EmployeeWorkRequest))
		{
			throw new IllegalArgumentException("Unsupported type of request for employee.");
		}

		this.employeeRequest = employeeRequest;
		workingTime += employeeRequest.getDuration();
	}

	public void removeEmployeeRequest()
	{
		employeeRequest = null;
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
