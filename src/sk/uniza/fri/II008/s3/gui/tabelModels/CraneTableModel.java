package sk.uniza.fri.II008.s3.gui.tabelModels;

import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import sk.uniza.fri.II008.s3.model.Crane;

public class CraneTableModel extends AbstractTableModel
{
	private static enum Column
	{
		ID("ID")
			{
				@Override
				public Object getValue(double timestamp, Crane crane)
				{
					return crane.getId();
				}
			},
		STATE("Stav")
			{
				@Override
				public Object getValue(double timestamp, Crane crane)
				{
					return crane.isBusy() ? "Zaneprázdnený" : "Voľný";
				}
			},
		WORKING_TIME("Vyťaženie")
			{
				@Override
				public Object getValue(double timestamp, Crane crane)
				{
					return String.format("%.2f %%", timestamp != 0f
						? 100f * crane.getCurrentWorkingTime(timestamp) / timestamp : 0);
				}
			};

		private final String label;

		private Column(String name)
		{
			this.label = name;
		}

		public String getLabel()
		{
			return label;
		}

		public abstract Object getValue(double timestamp, Crane crane);
	}

	private final List<Crane> cranes;
	private double timestamp = 0f;

	public CraneTableModel(List<Crane> cranes)
	{
		this.cranes = cranes;
	}

	public Crane getValue(int rowIndex)
	{
		return cranes.get(rowIndex);
	}

	@Override
	public int getColumnCount()
	{
		return Column.values().length;
	}

	@Override
	public int getRowCount()
	{
		return cranes.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return getColumn(columnIndex).getValue(timestamp, cranes.get(rowIndex));
	}

	@Override
	public String getColumnName(int columnIndex)
	{
		return getColumn(columnIndex).getLabel();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return false;
	}

	private Column getColumn(int columnIndex)
	{
		return Column.values()[columnIndex];
	}

	public void onChangeList(double timestamp)
	{
		this.timestamp = timestamp;

		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				fireTableDataChanged();
			}
		});
	}
}
