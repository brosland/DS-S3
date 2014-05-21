package sk.uniza.fri.II008.s3.gui.tabelModels;

import java.util.ArrayList;
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
				public Object getValue(Vehicle vehicle)
				{
					return vehicle.getId();
				}
			},
		SPEED("Rýchlosť")
			{
				@Override
				public Object getValue(Vehicle vehicle)
				{
					return String.format("%.1fkm/h", vehicle.getSpeed());
				}
			},
		WORKING_TIME("Vyťaženie")
			{
				@Override
				public Object getValue(Vehicle vehicle)
				{
					return String.format("%.2f%%", CURRENT_TIMESTAMP == 0f
						? 0 : 100f * vehicle.getCurrentWorkingTime(CURRENT_TIMESTAMP) / CURRENT_TIMESTAMP);
				}
			},
		LOCATION("Pozícia")
			{
				@Override
				public Object getValue(Vehicle vehicle)
				{
					return vehicle.getLocation();
				}
			},
		ROLL("Rolka")
			{
				@Override
				public Object getValue(Vehicle vehicle)
				{
					return vehicle.hasRoll() ? vehicle.getRoll() : "";
				}
			},
		STATE("Stav")
			{
				@Override
				public Object getValue(Vehicle vehicle)
				{
					if (vehicle.hasVehicleRequest())
					{
						VehicleRequest vehicleRequest = vehicle.getVehicleRequest();

						return String.format("Presun z %s do %s (%s)", vehicleRequest.getFrom(),
							vehicleRequest.getTo(), Utils.formatTime(vehicleRequest.getEndTimestamp()));
					}
					else
					{
						return vehicle.isBusy() ? "Obsadené" : "Voľné";
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

	public static double CURRENT_TIMESTAMP = 0f;
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
