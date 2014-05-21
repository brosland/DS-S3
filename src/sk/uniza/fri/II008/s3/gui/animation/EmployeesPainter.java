package sk.uniza.fri.II008.s3.gui.animation;

import java.awt.Color;
import java.util.Collection;

import sk.uniza.fri.II008.s3.model.Employee;
import sk.uniza.fri.II008.s3.model.Storage;
import sk.uniza.fri.II008.s3.model.requests.BaseRequest;

public class EmployeesPainter {
	private int oneOffset = 9;
	private int size = 4;
	private PaintingContext context;
	
	public EmployeesPainter(PaintingContext context)
	{
		this.context = context;
	}
	
	public void paint(Storage storage, Collection<Employee> employees)
	{
		FactoryPosition storagePosition = context.figureOutPositionFor(storage);
		int centerX = context.view.transformX(storagePosition.getX());
		int centerY = context.view.transformY(storagePosition.getY());
		
		int startX = centerX - (employees.size()/2)*oneOffset;
		int startY = centerY + 15;
		
		int i=0;
		for(Employee employee : employees)
		{
			paint(startX+oneOffset*i, startY, employee);
			
			i++;
		}
	}
	
	private void paint(int x, int y, Employee employee)
	{
		context.graphics.setColor(Color.pink.darker());
		context.fillCircle(x, y, size);
		
		if(employee.hasEmployeeRequest())
		{
			BaseRequest request = employee.getEmployeeRequest();
			
			double amount = (context.replication.currentTime() - request.getStartTimestamp()) / request.getDuration();
			amount = Util.clip(amount, 0, 1);
			
			int indicatorWidth = (int)Math.round(amount*size*2);
			
			context.graphics.setColor(Color.GRAY);
			context.graphics.fillRect(x-size, y+size+3, size*2, 4);
			
			context.graphics.setColor(Color.GREEN);
			context.graphics.fillRect(x-size, y+size+3, indicatorWidth, 4);
		}
	}
}
