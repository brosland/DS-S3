package sk.uniza.fri.II008.s3.gui.animation;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.LinkedList;

import sk.uniza.fri.II008.s3.FactoryReplication;
import sk.uniza.fri.II008.s3.model.Crane;
import sk.uniza.fri.II008.s3.model.Elevator;
import sk.uniza.fri.II008.s3.model.Employee;
import sk.uniza.fri.II008.s3.model.Factory;
import sk.uniza.fri.II008.s3.model.Roll;
import sk.uniza.fri.II008.s3.model.RollStorable;
import sk.uniza.fri.II008.s3.model.Storage;
import sk.uniza.fri.II008.s3.model.Vehicle;
import sk.uniza.fri.II008.s3.model.requests.VehicleRequest;

public class PaintingContext {
	final protected FactoryView view;
	final protected Graphics2D graphics;
	final protected FactoryLayout layout;
	final protected Factory factory;
	final protected FactoryReplication replication;
	
	private OutlinePainter outlinePainter = new OutlinePainter(this);
	private VehiclePainter vehiclePainter = new VehiclePainter(this);
	private ElevatorPainter elevatorPainter = new ElevatorPainter(this);
	private StoragePainter storagePainter = new StoragePainter(this);
	private CranePainter cranePainter = new CranePainter(this);
	private EmployeesPainter employeesPainter = new EmployeesPainter(this);
	
	public PaintingContext(FactoryView view, Graphics2D graphics, FactoryLayout layout, Factory factory, FactoryReplication replication)
	{
		this.replication = replication;
		this.layout = layout;
		this.view = view;
		this.graphics = graphics;
		this.factory = factory;
	}
	
	protected void fillCircle(int x, int y, int radius)
	{
		graphics.fillRoundRect(x-radius, y-radius, radius*2, radius*2, radius*2, radius*2);
	}
	
	private void paintVehicles()
	{
		for(Vehicle vehicle : factory.getVehicles()) {
			vehiclePainter.paint(vehicle);
		}
	}
	
	private void paintImportElevators()
	{
		for(Elevator elevator : factory.getImportElevators())
		{
			elevatorPainter.paint(elevator);
		}
	}
	
	private void paintExportElevators()
	{
		elevatorPainter.paint(factory.getExportElevator());
	}
	
	private void paintStorages()
	{
		for(Roll.Type type : Roll.Type.values()) {
			Storage storage = factory.getProcessingStorage(type);
			storagePainter.paint(layout.getStoragePosition(type), storage);
		}
		
		storagePainter.paint(layout.getCoolingStoragePosition(), factory.getCoolingStorage());
	}
	
	private void paintCranes()
	{
		for(Crane crane : factory.getCranes())
		{
			cranePainter.paint(crane);
		}
	}
	
	private void paintEmployees()
	{
		HashMap<Storage, Integer> employeesInStorages = new HashMap<>();
		for(Employee employee : factory.getEmployees())
		{
			Storage storage = employee.getCurrentStorage();
			if(!employeesInStorages.containsKey(storage)) {
				employeesInStorages.put(storage, 0);
			}
			employeesInStorages.put(storage, employeesInStorages.get(storage)+1);
		}
		
		for(Storage storage : employeesInStorages.keySet())
		{
			employeesPainter.paint(storage, employeesInStorages.get(storage));
		}
	}
	
	public void paint()
	{
		outlinePainter.paint();
	
		if(replication != null) {
			paintVehicles();
			paintImportElevators();
			paintExportElevators();
			paintStorages();
			paintCranes();
			paintEmployees();
		}
	}
	
	protected FactoryPosition figureOutPositionFor(RollStorable storable)
	{
		if(storable instanceof Storage)
		{
			return layout.getStoragePosition(figureRollTypeByRollStorage((Storage)storable));
		}
		else if(storable instanceof Vehicle)
		{
			return figureVehiclePosition((Vehicle)storable);
		}
		else if(storable instanceof Elevator)
		{
			return layout.getElevatorPosition((Elevator)storable);
		}
		throw new RuntimeException("Figuring out position for unknown storable "+storable);
	}
	
	protected FactoryPosition figureVehiclePosition(Vehicle vehicle)
	{
		FactoryPosition position = layout.getPositionFor(vehicle.getLocation());
		
		if(vehicle.hasVehicleRequest()) {
			VehicleRequest request = vehicle.getVehicleRequest();
			
			if(request.getFrom() != request.getTo()) {
				Trajectory trajectory = layout.getVehicleTrajectory(request.getFrom(), request.getTo());

				double currentTime = replication.currentTime();
				
				double interpolation = (currentTime - request.getStartTimestamp()) / request.getDuration();
				
				if(interpolation > 1) {
					interpolation = 1;
				}
				if(interpolation < 0) {
					interpolation = 0;
				}
				
				position = trajectory.getInterpolated((float)interpolation);
			}
		}
		
		return position;
	}
	
	protected Roll.Type figureRollTypeByRollStorage(Storage storage)
	{
		if(storage == factory.getCoolingStorage()) {
			return null;
		}
		for(Roll.Type type : Roll.Type.values()) {
			Storage otherStorage = factory.getProcessingStorage(type);
			if(otherStorage == storage) {
				return type;
			}
		}
		throw new RuntimeException("Some unexpected storage given to figure out roll type for "+storage);
	}
}
