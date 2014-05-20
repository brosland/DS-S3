package sk.uniza.fri.II008.s3.gui.animation;

import java.awt.Color;

public class OutlinePainter {
	private PaintingContext context;
	
	public OutlinePainter(PaintingContext context) {
		this.context = context;
	}
	
	public void paint()
	{
		context.graphics.setColor(Color.WHITE);
		context.graphics.fillRect(
				context.view.transformX(0), 
				context.view.transformY(0), 
				context.view.transformX(context.layout.getWidth()), 
				context.view.transformY(context.layout.getHeight())
		);
		
		context.graphics.setColor(Color.BLUE);
		for(PointedTrajectory trajectory : context.layout.getPointedTrajectories()) {
			FactoryPosition[] positions = trajectory.getPositions();
			
			for(int i=1; i<positions.length; i++) {
				context.graphics.drawLine(
						context.view.transformX(positions[i-1].getX()),
						context.view.transformY(positions[i-1].getY()), 
						context.view.transformX(positions[i].getX()), 
						context.view.transformY(positions[i].getY())
				);
			}
		}
	}
}
