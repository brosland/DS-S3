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
		paint(context.figureVehiclePosition(vehicle), vehicle);
	}
	
	public void paint(FactoryPosition position, Vehicle vehicle) {
		int x = context.view.transformX(position.getX());
		int y = context.view.transformY(position.getY());
		
		context.graphics.setColor(Color.BLACK);
		context.fillCircle(x, y, 5);
		
		if(vehicle.hasRoll()) {
			context.graphics.setColor(Color.orange);
			context.fillCircle(x+2, y+2, 3);
		}
	}
}
