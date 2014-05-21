package sk.uniza.fri.II008.s3.gui.animation;

import java.awt.Color;

import sk.uniza.fri.II008.s3.model.Elevator;

public class ElevatorPainter {
	private PaintingContext context;
	
	public ElevatorPainter(PaintingContext context)
	{
		this.context = context;
	}
	
	public void paint(Elevator elevator)
	{
		paint(context.layout.getElevatorPosition(elevator), elevator);
	}
	
	private void paint(FactoryPosition position, Elevator elevator)
	{
		int centerX = context.view.transformX(position.getX());
		int centerY = context.view.transformY(position.getY());
		
		context.graphics.setColor(Color.BLACK);
		context.graphics.fillRect(centerX-10, centerY-10, 20, 20);
		context.graphics.setColor(Color.WHITE);
		context.graphics.fillRect(centerX-8, centerY-8, 16, 16);
		
		context.graphics.setColor(Color.YELLOW);
		context.graphics.fillRect(centerX-8, centerY-8, (int)Math.round((float)16*elevator.getFilling()), 16);
		
		context.graphics.setColor(Color.BLACK);
		context.graphics.drawString(String.valueOf(elevator.getRolls().size()), centerX-8, centerY+4);
	}
}
