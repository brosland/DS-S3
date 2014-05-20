package sk.uniza.fri.II008.s3.gui.tabelModels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import sk.uniza.fri.II008.s3.model.RollStorage;

public class RollStorageTableModel extends AbstractTableModel
{
	private static enum Column
	{
		ID("ID")
			{
				@Override
				public Object getValue(RollStorage rollStorage)
				{
					return rollStorage.getId();
				}
			},
		NAME("Názov")
			{
				@Override
				public Object getValue(RollStorage rollStorage)
				{
					return rollStorage.getName();
				}
			},
		CAPACITY("Kapacita")
			{
				@Override
				public Object getValue(RollStorage rollStorage)
				{
					return rollStorage.getCapacity();
				}
			},
		FILLING("Zaplnenie (%)")
			{
				@Override
				public Object getValue(RollStorage rollStorage)
				{
					return String.format("%.2f", rollStorage.getFilling() * 100f);
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

		public abstract Object getValue(RollStorage rollStorage);
	}

	private List<RollStorage> rollStorages;

	public RollStorageTableModel()
	{
		this.rollStorages = new ArrayList<>();
	}

	public void setValues(List<RollStorage> rollStorages)
	{
		this.rollStorages = rollStorages;
		onChangeList();
	}

	public RollStorage getValue(int rowIndex)
	{
		return rollStorages.get(rowIndex);
	}

	@Override
	public int getColumnCount()
	{
		return Column.values().length;
	}

	@Override
	public int getRowCount()
	{
		return rollStorages.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return getColumn(columnIndex).getValue(rollStorages.get(rowIndex));
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

	public void onChangeList()
	{
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
