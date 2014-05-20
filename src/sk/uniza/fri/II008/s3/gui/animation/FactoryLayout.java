package sk.uniza.fri.II008.s3.gui.animation;

import sk.uniza.fri.II008.s3.model.Navigation.Location;
import sk.uniza.fri.II008.s3.model.Elevator;
import sk.uniza.fri.II008.s3.model.Roll;

public class FactoryLayout {
	private FactoryPosition vehiclePositionA = new FactoryPosition(490, 50);
	private FactoryPosition vehiclePositionB = new FactoryPosition(90, 50);
	private FactoryPosition vehiclePositionC = new FactoryPosition(200, 310);
	
	private FactoryPosition vehicleTopLeftCorner = new FactoryPosition(50, 50);
	private FactoryPosition vehicleBottomLeftCorner = new FactoryPosition(50, 310);
	private FactoryPosition vehicleTopRightCorner = new FactoryPosition(550, 50);
	private FactoryPosition vehicleBottomRightCorner = new FactoryPosition(550, 310);
	
	private PointedTrajectory vehicleTrajectoryFromAtoC = new PointedTrajectory(
			vehiclePositionA, vehiclePositionB, vehicleTopLeftCorner, vehicleBottomLeftCorner, vehiclePositionC
			);
	
	private PointedTrajectory vehicleTrajectoryFromBtoC = new PointedTrajectory(
			vehiclePositionB, vehicleTopLeftCorner, vehicleBottomLeftCorner, vehiclePositionC
			);
	
	private PointedTrajectory vehicleTrajectoryFromCtoA = new PointedTrajectory(
			vehiclePositionC, vehicleBottomRightCorner, vehicleTopRightCorner, vehiclePositionA
			);
	
	private PointedTrajectory vehicleTrajectoryFromAtoB = new PointedTrajectory(
			vehiclePositionA, vehiclePositionB
			);
	
	private float width;
	private float height;
	
	public FactoryLayout()
	{
		this.width = 600;
		this.height = 360;
	}
	
	public float getWidth()
	{
		return width;
	}
	
	public float getHeight()
	{
		return height;
	}
	
	protected FactoryPosition getPositionFor(Location location) {
		switch(location) {
		case A: return vehiclePositionA;
		case B: return vehiclePositionB;
		case C: return vehiclePositionC;
		}
		throw new RuntimeException("Unknown location "+location);
	}
	
	public FactoryPosition getElevatorPosition(Elevator elevator)
	{
		switch(elevator.getLocation()) {
		case A: return getPositionFor(Location.A).add(0, -30);
		case B: return getPositionFor(Location.B).add(0, -30);
		case C: return getPositionFor(Location.C).add(0, 30);
		default: throw new RuntimeException("Unknown elevator location for "+elevator);
		}
	}
	
	public Trajectory getVehicleTrajectory(Location from, Location to) {
		if(from == Location.A && to == Location.C) {
			return vehicleTrajectoryFromAtoC;
		} else if(from == Location.B && to == Location.C) {
			return vehicleTrajectoryFromBtoC;
		} else if(from == Location.C && to == Location.A) {
			return vehicleTrajectoryFromCtoA;
		} else if(from == Location.A && to == Location.B) {
			return vehicleTrajectoryFromAtoB;
		} else {
			throw new RuntimeException("Unexpected trajectory from "+from+" to "+to);
		}
	}
	
	public PointedTrajectory [] getPointedTrajectories()
	{
		return new PointedTrajectory[]{
			vehicleTrajectoryFromAtoC,
			vehicleTrajectoryFromBtoC,
			vehicleTrajectoryFromCtoA,
			vehicleTrajectoryFromAtoB
		};
	}
	
	public FactoryPosition getStoragePosition(Roll.Type storageType)
	{
		if(storageType == null) {
			return getCoolingStoragePosition();
		}
		
		switch(storageType) {
		case A: return new FactoryPosition(400, 100);
		case B: return new FactoryPosition(200, 100);
		case C: return new FactoryPosition(200, 260);
		default: throw new RuntimeException("Unknown storage type "+storageType);
		}
	}
	
	public FactoryPosition [] getStorageBounds(Roll.Type storageType)
	{
		return new FactoryPosition[]{
			getStoragePosition(storageType).add(-50, -25),
			getStoragePosition(storageType).add(50, 25)
		};
	}
	
	public FactoryPosition getCoolingStoragePosition()
	{
		return new FactoryPosition(400, 260);
	}
}
