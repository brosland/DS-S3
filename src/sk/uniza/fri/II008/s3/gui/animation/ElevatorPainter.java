package sk.uniza.fri.II008.s3.gui.animation;

import java.awt.Color;

import sk.uniza.fri.II008.s3.model.Elevator;

public class ElevatorPainter {
	private int width = 80;
	private int height = 20;
	private int margin = 3;
	
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
		context.graphics.fillRect(centerX-margin-width/2, centerY-margin-height/2, width+2*margin, height+2*margin);
		context.graphics.setColor(Color.WHITE);
		context.graphics.fillRect(centerX-width/2, centerY-height/2, width, height);
		
		float filling = (float)elevator.getRolls().size() / elevator.getCapacity();
		
		int fillingWidth = (int)Math.round(width*filling);
		
		context.graphics.setColor(Color.YELLOW);
		context.graphics.fillRect(centerX-width/2, centerY-height/2, fillingWidth, height);
		
		context.graphics.setColor(Color.BLACK);
		context.graphics.drawString(String.valueOf(elevator.getRolls().size()), centerX-8, centerY+4);
	}
}
