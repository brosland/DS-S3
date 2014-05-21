package sk.uniza.fri.II008.s3.gui.tabelModels;

import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import sk.uniza.fri.II008.Utils;
import sk.uniza.fri.II008.s3.model.Vehicle;
import sk.uniza.fri.II008.s3.model.requests.VehicleRequest;

public class VehicleTableModel extends AbstractTableModel
{
	private static enum Column
	{
		ID("ID")
			{
				@Override
				public Object getValue(double timestamp, Vehicle vehicle)
				{
					return vehicle.getId();
				}
			},
		SPEED("Rýchlosť")
			{
				@Override
				public Object getValue(double timestamp, Vehicle vehicle)
				{
					return String.format("%.1f km/h", vehicle.getSpeed());
				}
			},
		STATE("Stav")
			{
				@Override
				public Object getValue(double timestamp, Vehicle vehicle)
				{
					if (vehicle.hasVehicleRequest())
					{
						VehicleRequest vehicleRequest = vehicle.getVehicleRequest();

						if (vehicle.hasRoll())
						{
							return String.format("Presun rolky %s z %s do %s (%s)",
								vehicle.getRoll(), vehicleRequest.getFrom(), vehicleRequest.getTo(),
								Utils.formatTime(vehicleRequest.getEndTimestamp()));
						}
						else
						{
							return String.format("Presun z %s do %s (%s)",
								vehicleRequest.getFrom(), vehicleRequest.getTo(),
								Utils.formatTime(vehicleRequest.getEndTimestamp()));
						}
					}
					else
					{
						return String.format(vehicle.isBusy() ? "Obsadené (%s)" : "Voľné (%s)",
							vehicle.getLocation());
					}
				}
			},
		WORKING_TIME("Vyťaženie")
			{
				@Override
				public Object getValue(double timestamp, Vehicle vehicle)
				{
					return String.format("%.2f %%", timestamp != 0f
						? 100f * vehicle.getCurrentWorkingTime(timestamp) / timestamp : 0);
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

		public abstract Object getValue(double timestamp, Vehicle vehicle);
	}

	private final List<Vehicle> vehicles;
	private double timestamp = 0f;

	public VehicleTableModel(List<Vehicle> vehicles)
	{
		this.vehicles = vehicles;
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
		return getColumn(columnIndex).getValue(timestamp, vehicles.get(rowIndex));
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
