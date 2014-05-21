package sk.uniza.fri.II008.s3.gui.tabelModels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import sk.uniza.fri.II008.s3.model.Employee;

public class EmployeeTableModel extends AbstractTableModel
{
	private static enum Column
	{
		ID("ID")
			{
				@Override
				public Object getValue(double timestamp, Employee employee)
				{
					return employee.getId();
				}
			},
		CURRENT_STORAGE("Sklad")
			{
				@Override
				public Object getValue(double timestamp, Employee employee)
				{
					return employee.getCurrentStorage().getName();
				}
			},
		STATE("Stav")
			{
				@Override
				public Object getValue(double timestamp, Employee employee)
				{
					return employee.getState().label;
				}
			},
		WORKING_TIME("Vyťaženie")
			{
				@Override
				public Object getValue(double timestamp, Employee employee)
				{
					return String.format("%.2f%%", timestamp != 0f
						? 100f * employee.getCurrentWorkingTime(timestamp) / timestamp : 0);
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

		public abstract Object getValue(double timestamp, Employee employee);
	}

	private final List<Employee> employees;
	public double timestamp = 0f;

	public EmployeeTableModel(List<Employee> employees)
	{
		this.employees = employees;
	}

	public Employee getValue(int rowIndex)
	{
		return employees.get(rowIndex);
	}

	@Override
	public int getColumnCount()
	{
		return Column.values().length;
	}

	@Override
	public int getRowCount()
	{
		return employees.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return getColumn(columnIndex).getValue(timestamp, employees.get(rowIndex));
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
