package sk.uniza.fri.II008.s3.gui.animation;

import java.awt.Color;
import java.awt.Graphics2D;

import sk.uniza.fri.II008.s3.model.Factory;
import sk.uniza.fri.II008.s3.model.Vehicle;
import sk.uniza.fri.II008.s3.model.requests.VehicleRequest;

public class Painter {
	private FactoryView view;
	private Graphics2D graphics;
	private FactoryLayout layout;
	private Factory factory;
	
	public Painter(FactoryView view, Graphics2D graphics, FactoryLayout layout, Factory factory)
	{
		this.layout = layout;
		this.view = view;
		this.graphics = graphics;
		this.factory = factory;
	}
	
	private void fillCircle(int x, int y, int radius)
	{
		graphics.fillRoundRect(x-radius, y-radius, radius*2, radius*2, radius, radius);
	}
	
	private void paintOutline()
	{
		graphics.setColor(Color.WHITE);
		graphics.fillRect(
				view.transformX(0), 
				view.transformY(0), 
				view.transformX(layout.getWidth()), 
				view.transformY(layout.getHeight())
		);
	}
	
	private void paintTrajectories()
	{
		graphics.setColor(Color.BLUE);
		for(PointedTrajectory trajectory : layout.getPointedTrajectories()) {
			FactoryPosition[] positions = trajectory.getPositions();
			
			for(int i=1; i<positions.length; i++) {
				graphics.drawLine(
						view.transformX(positions[i-1].getX()),
						view.transformY(positions[i-1].getY()), 
						view.transformX(positions[i].getX()), 
						view.transformY(positions[i].getY())
				);
			}
		}
	}
	
	private void paintVehicles()
	{
		graphics.setColor(Color.BLACK);
		for(Vehicle vehicle : factory.getVehicles()) {
			FactoryPosition position = layout.getPositionFor(vehicle.getLocation());
			
			if(vehicle.hasVehicleRequest()) {
				VehicleRequest request = vehicle.getVehicleRequest();
				Trajectory trajectory = layout.getVehicleTrajectory(request.getFrom(), request.getTo());
			
				double currentTime = 0f; // TODO load from simulation
				
				double interpolation = (currentTime - request.getStartTimestamp()) / request.getDuration();
				
				position = trajectory.getInterpolated((float)interpolation);
			}
			
			int x = view.transformX(position.getX());
			int y = view.transformY(position.getY());
			
			fillCircle(x, y, 5);
		}
	}
	
	public void paint()
	{
		paintOutline();
		paintTrajectories();
		
		if(true /* simulation is running */) {
			paintVehicles();
		}
	}
}
