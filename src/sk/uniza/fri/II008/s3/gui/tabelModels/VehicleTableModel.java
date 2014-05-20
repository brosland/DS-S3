package sk.uniza.fri.II008.s3.gui.tabelModels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import sk.uniza.fri.II008.s3.model.Vehicle;

public class VehicleTableModel extends AbstractTableModel
{
	private static enum Column
	{
		ID("ID")
			{
				@Override
				public Object getValue(Vehicle vehicle)
				{
					return vehicle.getId();
				}
			},
		SPEED("Rýchlosť (km/h)")
			{
				@Override
				public Object getValue(Vehicle vehicle)
				{
					return String.format("%.1f", vehicle.getSpeed());
				}
			},
		STATE("Stav")
			{
				@Override
				public Object getValue(Vehicle vehicle)
				{
					if (vehicle.hasRoll())
					{
						return String.format("Presun rolky %s", vehicle.getRoll());
					}
					else
					{
						return vehicle.isBusy() ? "Presun vozidla" : "Voľný";
					}
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

		public abstract Object getValue(Vehicle vehicle);
	}

	private List<Vehicle> vehicles;

	public VehicleTableModel()
	{
		this.vehicles = new ArrayList<>();
	}

	public void setValues(List<Vehicle> vehicles)
	{
		this.vehicles = vehicles;
		onChangeList();
	}

	public Vehicle getValue(int rowIndex)
	{
		return vehicles.get(rowIndex);
	}

	@Override
	public int getColumnCount()
	{
		return Column.values().length;
	}

	@Override
	public int getRowCount()
	{
		return vehicles.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return getColumn(columnIndex).getValue(vehicles.get(rowIndex));
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
