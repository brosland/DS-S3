package sk.uniza.fri.II008.s3.gui.animation;

import java.awt.Color;
import java.awt.Graphics2D;

import sk.uniza.fri.II008.s3.FactoryReplication;
import sk.uniza.fri.II008.s3.model.Elevator;
import sk.uniza.fri.II008.s3.model.Factory;
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
	
	public void paint()
	{
		System.out.println(System.currentTimeMillis()+" Repainting");
		
		outlinePainter.paint();
	
		if(replication != null) {
			paintVehicles();
			paintImportElevators();
		}
	}
}
