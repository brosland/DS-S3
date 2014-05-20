package sk.uniza.fri.II008.s3.gui.animation;

import java.awt.Color;

import sk.uniza.fri.II008.s3.model.Vehicle;
import sk.uniza.fri.II008.s3.model.requests.VehicleRequest;

public class VehiclePainter {
	private PaintingContext context;
	
	public VehiclePainter(PaintingContext painter) {
		this.context = painter;
	}
	
	public void paint(Vehicle vehicle) {
		System.out.print("Vehicle "+vehicle);
		
		FactoryPosition position = context.layout.getPositionFor(vehicle.getLocation());
		
		if(vehicle.hasVehicleRequest()) {
			System.out.print(" which has request");
			VehicleRequest request = vehicle.getVehicleRequest();
			
			if(request.getFrom() != request.getTo()) {
				System.out.println(" that is valid");
				Trajectory trajectory = context.layout.getVehicleTrajectory(request.getFrom(), request.getTo());

				double currentTime = context.replication.currentTime();
				
				double interpolation = (currentTime - request.getStartTimestamp()) / request.getDuration();
				
				if(interpolation > 1) {
					interpolation = 1;
				}
				if(interpolation < 0) {
					interpolation = 0;
				}
				
				System.out.println(interpolation);
				
				position = trajectory.getInterpolated((float)interpolation);
			}
		}
		
		System.out.println();
		
		paint(position, vehicle);
	}
	
	public void paint(FactoryPosition position, Vehicle vehicle) {
		context.graphics.setColor(Color.BLACK);
		context.fillCircle(
				context.view.transformX(position.getX()), 
				context.view.transformY(position.getY()), 
				5
		);
	}
}
