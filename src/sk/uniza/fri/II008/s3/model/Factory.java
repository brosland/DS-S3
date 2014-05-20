package sk.uniza.fri.II008.s3.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import sk.uniza.fri.II008.s3.model.Navigation.Location;

public class Factory
{
	private final HashMap<Roll.Type, Elevator> importElevators;
	private final Elevator exportElevator;
	private final HashMap<Roll.Type, ProcessingStorage> processingStorages;
	private final Storage coolingStorage;
	private final ArrayList<Crane> cranes;
	private final ArrayList<Vehicle> vehicles;
	private final ArrayList<Employee> employees;
	public static Location DEFAULT_VEHICLE_LOCATION = Location.A;

	public static Factory createInstance()
	{
		Factory factory = new Factory();

		double[] vehicleSpeeds = new double[]
		{
			2.9f, 2.9f, 2.9f, 4.5f
		};

		for (double speed : vehicleSpeeds)
		{
			Vehicle vehicle = new Vehicle(speed, Factory.DEFAULT_VEHICLE_LOCATION);
			factory.addVehicle(vehicle);
		}

		for (Roll.Type rollType : Roll.Type.values())
		{
			Employee employee = new Employee();
			employee.setCurrentStorage(factory.getProcessingStorage(rollType));
			factory.addEmployee(employee);
		}

		return factory;
	}

	public void initProcessingStorage(Roll.Type rollType, double filled, double processed)
	{
		ArrayList<Roll> rolls = new ArrayList<>();
		ProcessingStorage processingStorage = getProcessingStorage(rollType);

		for (int i = 0; i < processingStorage.getCapacity() * filled; i++)
		{
			Roll roll = new Roll(rollType);
			rolls.add(roll);
			processingStorage.addRoll(roll);
		}

		for (int i = 0; i < rolls.size() * processed; i++)
		{
			rolls.get(i).setState(Roll.State.PROCESSED);
		}
	}

	public void initCoolingStorage(double filled, double preparedToExport)
	{
		ArrayList<Roll> rolls = new ArrayList<>();
		Storage storage = getCoolingStorage();

		for (int i = 0; i < storage.getCapacity() * filled; i++)
		{
			Roll roll = new Roll(Roll.Type.A);
			rolls.add(roll);
			storage.addRoll(roll);
		}

		for (int i = 0; i < rolls.size() * preparedToExport; i++)
		{
			rolls.get(i).setState(Roll.State.READY);
		}
	}

	public Factory()
	{
		cranes = new ArrayList<>(3);
		Crane craneA = new Crane();
		cranes.add(craneA);
		Crane craneB = new Crane();
		cranes.add(craneB);
		Crane craneC = new Crane();
		cranes.add(craneC);

		importElevators = new HashMap<>();
		importElevators.put(Roll.Type.A, new Elevator(10, Location.A, craneA));
		importElevators.put(Roll.Type.B, new Elevator(8, Location.B, craneB));
		exportElevator = new Elevator(13, Location.C, craneC);

		processingStorages = new HashMap<>();
		processingStorages.put(Roll.Type.A,
			new ProcessingStorage(120, Roll.Type.A, Location.A, craneA));
		processingStorages.put(Roll.Type.B,
			new ProcessingStorage(110, Roll.Type.B, Location.B, craneB));
		processingStorages.put(Roll.Type.C,
			new ProcessingStorage(70, Roll.Type.C, Location.C, craneC));
		coolingStorage = new Storage(170, Location.C, craneC);

		vehicles = new ArrayList<>();
		employees = new ArrayList<>();
	}

	public synchronized List<RollStorage> getAllRollStorages()
	{
		ArrayList<RollStorage> rollStorages = new ArrayList<>();
		rollStorages.addAll(importElevators.values());
		rollStorages.add(exportElevator);
		rollStorages.addAll(processingStorages.values());
		rollStorages.add(coolingStorage);

		Collections.sort(rollStorages, new Comparator<RollStorage>()
		{
			@Override
			public int compare(RollStorage rollStorageA, RollStorage rollStorageB)
			{
				if ((rollStorageA instanceof Elevator && rollStorageB instanceof Elevator)
					|| (rollStorageA instanceof Storage && rollStorageB instanceof Storage))
				{
					return rollStorageA.getId() - rollStorageB.getId();
				}
				else
				{
					return rollStorageA instanceof Elevator ? -1 : 1;
				}
			}
		});

		return rollStorages;
	}

	public synchronized Elevator getImportElevator(Roll.Type rollType)
	{
		return importElevators.get(rollType);
	}

	public synchronized List<Elevator> getImportElevators()
	{
		return new ArrayList<>(importElevators.values());
	}

	public synchronized Elevator getExportElevator()
	{
		return exportElevator;
	}

	public synchronized List<ProcessingStorage> getProcessingStorages()
	{
		return new ArrayList<>(processingStorages.values());
	}

	public synchronized ProcessingStorage getProcessingStorage(Roll.Type rollType)
	{
		return processingStorages.get(rollType);
	}

	public synchronized Storage getCoolingStorage()
	{
		return coolingStorage;
	}

	public synchronized List<Crane> getCranes()
	{
		return new ArrayList<>(cranes);
	}

	public synchronized List<Vehicle> getVehicles()
	{
		return new ArrayList<>(vehicles);
	}

	public void addVehicle(Vehicle vehicle)
	{
		vehicles.add(vehicle);
	}

	public synchronized List<Employee> getEmployees()
	{
		return new ArrayList<>(employees);
	}

	public void addEmployee(Employee employee)
	{
		employees.add(employee);
	}

	public void reset()
	{
		for (RollStorage rollstorage : getAllRollStorages())
		{
			rollstorage.reset();
		}

		for (Crane crane : cranes)
		{
			crane.reset();
		}

		for (Vehicle vehicle : vehicles)
		{
			vehicle.reset();
		}

		for (Employee employee : employees)
		{
			employee.reset();
		}

		Roll.LAST_ID = 0;
	}
}
