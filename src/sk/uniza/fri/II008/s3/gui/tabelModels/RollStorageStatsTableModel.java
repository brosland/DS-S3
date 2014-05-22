package sk.uniza.fri.II008.s3.gui.tabelModels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import sk.uniza.fri.II008.s3.FactoryStats;
import sk.uniza.fri.II008.s3.model.RollStorage;

public class RollStorageStatsTableModel extends AbstractTableModel
{
	private static enum Column
	{
		ID("ID")
			{
				@Override
				public Object getValue(FactoryStats factoryStats, RollStorage rollStorage, long replication)
				{
					return rollStorage.getId();
				}
			},
		NAME("Názov")
			{
				@Override
				public Object getValue(FactoryStats factoryStats, RollStorage rollStorage, long replication)
				{
					return rollStorage.getName();
				}
			},
		CAPACITY("Kapacita")
			{
				@Override
				public Object getValue(FactoryStats factoryStats, RollStorage rollStorage, long replication)
				{
					return rollStorage.getCapacity();
				}
			},
		FILLING("Priemerné zalnenie")
			{
				@Override
				public Object getValue(FactoryStats factoryStats, RollStorage rollStorage, long replication)
				{
					return String.format("%.2f %%", factoryStats.getRollStorageStat(rollStorage).mean());
				}
			},
		INTERVAL("95% interval spoľahlivosti")
			{
				@Override
				public Object getValue(FactoryStats factoryStats, RollStorage rollStorage, long replication)
				{
					if (replication < 2)
					{
						return "";
					}

					double[] interval = factoryStats.getRollStorageStat(rollStorage).confidenceInterval_95();

					return String.format("<%.3f ; %.3f>", interval[0], interval[1]);
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

		public abstract Object getValue(FactoryStats factoryStats, RollStorage rollStorage, long replication);
	}

	private long replication = 0;
	private List<RollStorage> rollStorages;
	private FactoryStats factoryStats;

	public RollStorageStatsTableModel()
	{
		rollStorages = new ArrayList<>();
	}

	public void init(FactoryStats factoryStats, List<RollStorage> rollStorages)
	{
		this.rollStorages = rollStorages;
		this.factoryStats = factoryStats;
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
		return getColumn(columnIndex).getValue(factoryStats, rollStorages.get(rowIndex), replication);
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

	public void onChangeList(long replication)
	{
		this.replication = replication;

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
