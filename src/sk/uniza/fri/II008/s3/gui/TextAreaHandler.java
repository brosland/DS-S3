package sk.uniza.fri.II008.s3.gui;

import java.util.logging.LogRecord;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class TextAreaHandler extends java.util.logging.Handler
{
	private final JTextArea textArea;

	public TextAreaHandler(JTextArea textArea)
	{
		this.textArea = textArea;
	}

	@Override
	public void publish(final LogRecord record)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				StringBuilder sb = new StringBuilder(textArea.getText());
				sb.append(record.getMessage()).append("\n");
				textArea.setText(sb.toString());
			}
		});
	}

	@Override
	public void flush()
	{
	}

	@Override
	public void close() throws SecurityException
	{
	}
}
