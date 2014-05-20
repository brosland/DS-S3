package sk.uniza.fri.II008.s3.gui.tabelModels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import sk.uniza.fri.II008.s3.model.Roll;

public class RollTableModel extends AbstractTableModel
{
	private static enum Column
	{
		ID("ID")
			{
				@Override
				public Object getValue(Roll roll)
				{
					return roll.getId();
				}
			},
		TYPE("Typ")
			{
				@Override
				public Object getValue(Roll roll)
				{
					return roll.getType();
				}
			},
		STATE("Stav")
			{
				@Override
				public Object getValue(Roll roll)
				{
					return roll.getState().label;
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

		public abstract Object getValue(Roll roll);
	}

	private List<Roll> rolls;

	public RollTableModel()
	{
		this.rolls = new ArrayList<>();
	}

	public void setValues(List<Roll> rolls)
	{
		this.rolls = rolls;
		onChangeList();
	}

	public Roll getValue(int rowIndex)
	{
		return rolls.get(rowIndex);
	}

	@Override
	public int getColumnCount()
	{
		return Column.values().length;
	}

	@Override
	public int getRowCount()
	{
		return rolls.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return getColumn(columnIndex).getValue(rolls.get(rowIndex));
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
