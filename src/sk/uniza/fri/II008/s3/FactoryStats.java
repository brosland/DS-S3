package sk.uniza.fri.II008.s3;

import OSPStat.Stat;
import OSPStat.WStat;
import java.util.HashMap;
import sk.uniza.fri.II008.s3.model.Crane;
import sk.uniza.fri.II008.s3.model.Employee;
import sk.uniza.fri.II008.s3.model.Factory;
import sk.uniza.fri.II008.s3.model.RollStorage;
import sk.uniza.fri.II008.s3.model.Vehicle;

public class FactoryStats
{
	private final Factory factory;
	private final HashMap<RollStorage, Stat> rollStorageStats;
	private final HashMap<Crane, Stat> craneStats;
	private final HashMap<Vehicle, Stat> vehicleStats;
	private final HashMap<Employee, Stat> employeeStats;

	public FactoryStats(Factory factory)
	{
		this.factory = factory;

		rollStorageStats = new HashMap<>();
		craneStats = new HashMap<>();
		vehicleStats = new HashMap<>();
		employeeStats = new HashMap<>();

		for (RollStorage rollStorage : factory.getAllRollStorages())
		{
			rollStorageStats.put(rollStorage, new Stat());
		}

		for (Crane crane : factory.getCranes())
		{
			craneStats.put(crane, new Stat());
		}

		for (Vehicle vehicle : factory.getVehicles())
		{
			vehicleStats.put(vehicle, new Stat());
		}

		for (Employee employee : factory.getEmployees())
		{
			employeeStats.put(employee, new Stat());
		}
	}

	public void prepareReplication(FactoryReplication factoryReplication)
	{
		for (RollStorage rollStorage : factory.getAllRollStorages())
		{
			rollStorage.setWStat(new WStat(factoryReplication));
		}
	}

	public void addReplicationResults(FactoryReplication factoryReplication)
	{
		for (RollStorage rollStorage : factory.getAllRollStorages())
		{
			rollStorageStats.get(rollStorage).addSample(rollStorage.getWStat().mean());
		}

		double maxTimestamp = factoryReplication.getFactorySimulation().getMaxReplicationTimestamp();

		for (Crane crane : factory.getCranes())
		{
			craneStats.get(crane).addSample(100f * crane.getWorkingTime() / maxTimestamp);
		}

		for (Vehicle vehicle : factory.getVehicles())
		{
			vehicleStats.get(vehicle).addSample(100f * vehicle.getWorkingTime() / maxTimestamp);
		}

		for (Employee employee : factory.getEmployees())
		{
			employeeStats.get(employee).addSample(100f * employee.getWorkingTime() / maxTimestamp);
		}
	}

	public Stat getRollStorageStat(RollStorage rollStorage)
	{
		return rollStorageStats.get(rollStorage);
	}

	public double getAvrgCraneWorkingTime()
	{
		double avrg = 0f;

		for (Crane crane : factory.getCranes())
		{
			avrg += craneStats.get(crane).mean();
		}

		return avrg / factory.getCranes().size();
	}

	public Stat getCraneStat(Crane crane)
	{
		return craneStats.get(crane);
	}

	public double getAvrgVehicleWorkingTime()
	{
		double avrg = 0f;

		for (Vehicle vehicle : factory.getVehicles())
		{
			avrg += vehicleStats.get(vehicle).mean();
		}

		return avrg / factory.getVehicles().size();
	}

	public Stat getVehicleStat(Vehicle vehicle)
	{
		return vehicleStats.get(vehicle);
	}

	public double getAvrgEmployeeWorkingTime()
	{
		double avrg = 0f;

		for (Employee employee : factory.getEmployees())
		{
			avrg += employeeStats.get(employee).mean();
		}

		return avrg / factory.getEmployees().size();
	}

	public Stat getEmployeeStat(Employee employee)
	{
		return employeeStats.get(employee);
	}
}
