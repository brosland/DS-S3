package sk.uniza.fri.II008.s3;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import sk.uniza.fri.II008.s3.gui.Window;

public class Main
{
	public static void main(String args[])
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e)
		{
		}

		java.awt.EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				new Window().setVisible(true);
			}
		});
	}
}
