package sk.uniza.fri.II008.s3.gui.animation;

public class FactoryView {
	private FactoryLayout layout;
	private int viewWidth;
	private int viewHeight;
	private int margin = 50;
	
	public FactoryView(FactoryLayout layout, int viewWidth, int viewHeight)
	{
		this.layout = layout;
		this.viewWidth = viewWidth;
		this.viewHeight = viewHeight;
	}
	
	public int transformX(float x)
	{
		return margin+Math.round((float)(viewWidth-3*margin) * x/layout.getWidth());
	}
	
	public int transformY(float y)
	{
		return margin+Math.round((float)(viewHeight-3*margin) * y/layout.getHeight());
	}
	
	public ScreenPosition transform(FactoryPosition position)
	{
		return new ScreenPosition(transformX(position.getX()), transformY(position.getY()));
	}
}
