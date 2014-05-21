package sk.uniza.fri.II008.s3.gui.animation;

import java.awt.Color;

import sk.uniza.fri.II008.s3.model.Storage;

public class EmployeesPainter {
	private int oneOffset = 9;
	private int size = 4;
	private PaintingContext context;
	
	public EmployeesPainter(PaintingContext context)
	{
		this.context = context;
	}
	
	public void paint(Storage storage, int employeeCount)
	{
		FactoryPosition storagePosition = context.figureOutPositionFor(storage);
		int centerX = context.view.transformX(storagePosition.getX());
		int centerY = context.view.transformY(storagePosition.getY());
		
		int startX = centerX - (employeeCount/2)*oneOffset;
		int startY = centerY + 15;
		
		context.graphics.setColor(Color.pink.darker());
		
		for(int i=0; i<employeeCount; i++)
		{
			context.fillCircle(startX+oneOffset*i, startY, size);
		}
	}
}
