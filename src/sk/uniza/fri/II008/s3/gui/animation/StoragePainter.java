package sk.uniza.fri.II008.s3.gui.animation;

import java.awt.Color;

import sk.uniza.fri.II008.s3.model.RollStorage;

public class StoragePainter {
	private int margin = 5;
	private int width = 150;
	private int height = 50;
	private PaintingContext context;
	
	public StoragePainter(PaintingContext context)
	{
		this.context = context;
	}

	public void paint(FactoryPosition position, RollStorage storage)
	{
		int centerX = context.view.transformX(position.getX());
		int centerY = context.view.transformY(position.getY());
		
		float filling = (float)storage.getRolls().size() / storage.getCapacity();
		
		int fillWidth = Math.round(width*filling);
		
		context.graphics.setColor(Color.BLACK);
		context.graphics.fillRect(centerX-margin-width/2, centerY-margin-height/2, width+2*margin, height+2*margin);
		context.graphics.setColor(Color.WHITE);
		context.graphics.fillRect(centerX-width/2, centerY-height/2, width, height);
		
		context.graphics.setColor(Color.YELLOW);
		context.graphics.fillRect(centerX-width/2, centerY-height/2, fillWidth, height);
		
		context.graphics.setColor(Color.BLACK);
		context.graphics.drawString(storage.getName(), centerX, centerY-5);
		context.graphics.drawString(String.valueOf(storage.getRolls().size()), centerX, centerY+5);
	}
}
