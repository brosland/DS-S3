package sk.uniza.fri.II008.s3.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import sk.uniza.fri.II008.ISimulation.State;
import sk.uniza.fri.II008.ReplicationListener;
import sk.uniza.fri.II008.Utils;
import sk.uniza.fri.II008.s3.FactorySimulation;
import sk.uniza.fri.II008.s3.gui.tabelModels.*;
import sk.uniza.fri.II008.s3.model.Roll;
import sk.uniza.fri.II008.s3.model.RollStorage;

public class ReplicationWindow extends javax.swing.JFrame
{
	private final FactorySimulation simulation;
	private final TextAreaHandler logsHandler;
	private XYSeries series;
	private JFreeChart chart;
	private double lastAvrgWaitingTime = 0;
	private RollStorage selectedRollStorage = null;

	public ReplicationWindow(final FactorySimulation simulation)
	{
		initComponents();
		initJFreeChart();

		logsHandler = new TextAreaHandler(logsTextArea);
		FactorySimulation.LOGGER.addHandler(logsHandler);

		addWindowListener(createWindowListener());

		this.simulation = simulation;
		this.simulation.setReplicationListener(createReplicationListener());

		rollStorageTable.getSelectionModel().addListSelectionListener(
			createRollStorageTableSelectionListener());

		updateSimulationPauseEvent();
		updateComponents();
	}

	private WindowAdapter createWindowListener()
	{
		return new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent evt)
			{
				simulation.setMaxSpeed();
				simulation.setReplicationListener(new ReplicationListener()
				{
				});

				FactorySimulation.LOGGER.removeHandler(logsHandler);
				dispose();
			}
		};
	}

	private ReplicationListener createReplicationListener()
	{
		return new ReplicationListener()
		{
			@Override
			public void onChange()
			{
				if (useStepsCheckBox.isSelected())
				{
					simulation.pause();
				}

				updateComponents();
			}
		};
	}

	private ListSelectionListener createRollStorageTableSelectionListener()
	{
		return new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent event)
			{
				if (rollStorageTable.getSelectedRow() == -1)
				{
					selectedRollStorage = null;
					((RollTableModel) rollTable.getModel()).setValues(new ArrayList<Roll>());
				}
				else
				{
					selectedRollStorage = ((RollStorageTableModel) rollStorageTable.getModel()).getValue(
						rollStorageTable.getSelectedRow());

					((RollTableModel) rollTable.getModel()).setValues(selectedRollStorage.getRolls());
				}

//				System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
			}
		};
	}

	private void initJFreeChart()
	{
		series = new XYSeries("XYGraph");
		series.setMaximumItemCount(1000);

		// Add the series to your data set
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);

		// Generate the graph
		chart = ChartFactory.createXYLineChart(
			"", // Title
			"Simulačný čas", // x-axis Label
			"Priem. čas čakania zákazníka", // y-axis Label
			dataset, // Dataset
			PlotOrientation.VERTICAL, // Plot Orientation
			false, // Show Legend
			false, // Use tooltips
			false // Configure chart to generate URLs?
		);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setVisible(true);

		resultsChartPanel.setLayout(new java.awt.BorderLayout());
		resultsChartPanel.add(chartPanel, BorderLayout.CENTER);
		resultsChartPanel.validate();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        descriptionPanel = new javax.swing.JPanel();
        startButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        useStepsCheckBox = new javax.swing.JCheckBox();
        speedLabel = new javax.swing.JLabel();
        speedSlider = new javax.swing.JSlider();
        timeLabel = new javax.swing.JLabel();
        timeProgressBar = new javax.swing.JProgressBar();
        contentSeparator = new javax.swing.JSeparator();
        tabbedPane = new javax.swing.JTabbedPane();
        detailScrollPane = new javax.swing.JScrollPane();
        detailPanel = new javax.swing.JPanel();
        rollStoragePanel = new javax.swing.JPanel();
        rollStorageTablePanel = new javax.swing.JPanel();
        rollStorageTableLabel = new javax.swing.JLabel();
        rollStorageTableScrollPane = new javax.swing.JScrollPane();
        rollStorageTable = new javax.swing.JTable();
        rollTablePanel = new javax.swing.JPanel();
        rollTableLabel = new javax.swing.JLabel();
        rollTableScrollPane = new javax.swing.JScrollPane();
        rollTable = new javax.swing.JTable();
        customersAndRequestsPanel = new javax.swing.JPanel();
        craneTablePanel = new javax.swing.JPanel();
        craneTableLabel = new javax.swing.JLabel();
        craneTableScrollPane = new javax.swing.JScrollPane();
        craneTable = new javax.swing.JTable();
        vehicleTablePanel = new javax.swing.JPanel();
        vehicleTableLabel = new javax.swing.JLabel();
        vehicleTableScrollPane = new javax.swing.JScrollPane();
        vehicleTable = new javax.swing.JTable();
        employeeTablePanel = new javax.swing.JPanel();
        employeeTableLabel = new javax.swing.JLabel();
        employeeTableScrollPane = new javax.swing.JScrollPane();
        employeeTable = new javax.swing.JTable();
        logsPanel = new javax.swing.JPanel();
        logsLabel = new javax.swing.JLabel();
        allowLogsCheckBox = new javax.swing.JCheckBox();
        logsScrollPane = new javax.swing.JScrollPane();
        logsTextArea = new javax.swing.JTextArea();
        resultsScrollPane = new javax.swing.JScrollPane();
        resultsPanel = new javax.swing.JPanel();
        resultsPanel2 = new javax.swing.JPanel();
        avrgWaitTimeLabel = new javax.swing.JLabel();
        avrgWaitTimeValueLabel = new javax.swing.JLabel();
        rollStorageFillingTablePanel = new javax.swing.JPanel();
        rollStorageFillingTableLabel = new javax.swing.JLabel();
        rollStorageFillingTableScrollPane = new javax.swing.JScrollPane();
        rollStorageFillingTable = new javax.swing.JTable();
        resultsChartPanel = new javax.swing.JPanel();
        animationScrollPane = new javax.swing.JScrollPane();
        animationPanel = new AnimationPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        startButton.setText("Spustiť");
        startButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                startButtonActionPerformed(evt);
            }
        });

        pauseButton.setText("Prerušiť");
        pauseButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                pauseButtonActionPerformed(evt);
            }
        });

        useStepsCheckBox.setText("Krokovanie simulácie");
        useStepsCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                useStepsCheckBoxActionPerformed(evt);
            }
        });

        speedLabel.setText("Rýchlosť");

        speedSlider.setMaximum(950);
        speedSlider.setToolTipText("");
        speedSlider.setValue(500);
        speedSlider.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                speedSliderStateChanged(evt);
            }
        });

        timeLabel.setText("Simulačný čas");

        javax.swing.GroupLayout descriptionPanelLayout = new javax.swing.GroupLayout(descriptionPanel);
        descriptionPanel.setLayout(descriptionPanelLayout);
        descriptionPanelLayout.setHorizontalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(descriptionPanelLayout.createSequentialGroup()
                .addComponent(startButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pauseButton)
                .addGap(18, 18, 18)
                .addComponent(useStepsCheckBox)
                .addGap(18, 18, 18)
                .addComponent(speedLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(timeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        descriptionPanelLayout.setVerticalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(descriptionPanelLayout.createSequentialGroup()
                .addGroup(descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(useStepsCheckBox)
                        .addComponent(startButton)
                        .addComponent(pauseButton))
                    .addGroup(descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(speedSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(speedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        detailScrollPane.setBorder(null);

        rollStoragePanel.setMaximumSize(detailPanel.getSize());
        rollStoragePanel.setPreferredSize(new java.awt.Dimension(722, 450));
        rollStoragePanel.setLayout(new java.awt.GridLayout(1, 2, 10, 0));

        rollStorageTableLabel.setText("Dopravníky a Sklady");

        rollStorageTableScrollPane.setBackground(new java.awt.Color(255, 255, 255));

        rollStorageTable.setModel(new RollStorageTableModel());
        rollStorageTable.setGridColor(new java.awt.Color(255, 255, 255));
        rollStorageTable.setRowMargin(2);
        rollStorageTableScrollPane.setViewportView(rollStorageTable);

        javax.swing.GroupLayout rollStorageTablePanelLayout = new javax.swing.GroupLayout(rollStorageTablePanel);
        rollStorageTablePanel.setLayout(rollStorageTablePanelLayout);
        rollStorageTablePanelLayout.setHorizontalGroup(
            rollStorageTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rollStorageTablePanelLayout.createSequentialGroup()
                .addComponent(rollStorageTableLabel)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(rollStorageTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
        );
        rollStorageTablePanelLayout.setVerticalGroup(
            rollStorageTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rollStorageTablePanelLayout.createSequentialGroup()
                .addComponent(rollStorageTableLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rollStorageTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
        );

        rollStoragePanel.add(rollStorageTablePanel);

        rollTableLabel.setText("Obsah dopravníka / skladu");

        rollTableScrollPane.setBackground(new java.awt.Color(255, 255, 255));

        rollTable.setModel(new RollTableModel());
        rollTable.setGridColor(new java.awt.Color(255, 255, 255));
        rollTableScrollPane.setViewportView(rollTable);

        javax.swing.GroupLayout rollTablePanelLayout = new javax.swing.GroupLayout(rollTablePanel);
        rollTablePanel.setLayout(rollTablePanelLayout);
        rollTablePanelLayout.setHorizontalGroup(
            rollTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rollTablePanelLayout.createSequentialGroup()
                .addComponent(rollTableLabel)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(rollTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
        );
        rollTablePanelLayout.setVerticalGroup(
            rollTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rollTablePanelLayout.createSequentialGroup()
                .addComponent(rollTableLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rollTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
        );

        rollStoragePanel.add(rollTablePanel);

        customersAndRequestsPanel.setLayout(new java.awt.GridLayout(1, 3, 10, 0));

        craneTableLabel.setText("Žeriavy");

        craneTableScrollPane.setBackground(new java.awt.Color(255, 255, 255));

        craneTable.setModel(new CraneTableModel());
        craneTable.setGridColor(new java.awt.Color(255, 255, 255));
        craneTableScrollPane.setViewportView(craneTable);

        javax.swing.GroupLayout craneTablePanelLayout = new javax.swing.GroupLayout(craneTablePanel);
        craneTablePanel.setLayout(craneTablePanelLayout);
        craneTablePanelLayout.setHorizontalGroup(
            craneTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(craneTablePanelLayout.createSequentialGroup()
                .addComponent(craneTableLabel)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(craneTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
        );
        craneTablePanelLayout.setVerticalGroup(
            craneTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(craneTablePanelLayout.createSequentialGroup()
                .addComponent(craneTableLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(craneTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
        );

        customersAndRequestsPanel.add(craneTablePanel);

        vehicleTableLabel.setText("Vozidlá");

        vehicleTableScrollPane.setBackground(new java.awt.Color(255, 255, 255));

        vehicleTable.setModel(new VehicleTableModel());
        vehicleTable.setGridColor(new java.awt.Color(255, 255, 255));
        vehicleTableScrollPane.setViewportView(vehicleTable);

        javax.swing.GroupLayout vehicleTablePanelLayout = new javax.swing.GroupLayout(vehicleTablePanel);
        vehicleTablePanel.setLayout(vehicleTablePanelLayout);
        vehicleTablePanelLayout.setHorizontalGroup(
            vehicleTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vehicleTablePanelLayout.createSequentialGroup()
                .addComponent(vehicleTableLabel)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(vehicleTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
        );
        vehicleTablePanelLayout.setVerticalGroup(
            vehicleTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vehicleTablePanelLayout.createSequentialGroup()
                .addComponent(vehicleTableLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vehicleTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
        );

        customersAndRequestsPanel.add(vehicleTablePanel);

        employeeTableLabel.setText("Zamestnanci");

        employeeTableScrollPane.setBackground(new java.awt.Color(255, 255, 255));

        employeeTable.setModel(new EmployeeTableModel());
        employeeTable.setGridColor(new java.awt.Color(255, 255, 255));
        employeeTableScrollPane.setViewportView(employeeTable);

        javax.swing.GroupLayout employeeTablePanelLayout = new javax.swing.GroupLayout(employeeTablePanel);
        employeeTablePanel.setLayout(employeeTablePanelLayout);
        employeeTablePanelLayout.setHorizontalGroup(
            employeeTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeeTablePanelLayout.createSequentialGroup()
                .addComponent(employeeTableLabel)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(employeeTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
        );
        employeeTablePanelLayout.setVerticalGroup(
            employeeTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeeTablePanelLayout.createSequentialGroup()
                .addComponent(employeeTableLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
        );

        customersAndRequestsPanel.add(employeeTablePanel);

        logsPanel.setMaximumSize(new java.awt.Dimension(32767, 200));

        logsLabel.setText("Správy o činnostiach");

        allowLogsCheckBox.setText("zapnúť výpis");
        allowLogsCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                allowLogsCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout logsPanelLayout = new javax.swing.GroupLayout(logsPanel);
        logsPanel.setLayout(logsPanelLayout);
        logsPanelLayout.setHorizontalGroup(
            logsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logsPanelLayout.createSequentialGroup()
                .addComponent(logsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(allowLogsCheckBox))
        );
        logsPanelLayout.setVerticalGroup(
            logsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logsPanelLayout.createSequentialGroup()
                .addGroup(logsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(logsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                    .addComponent(allowLogsCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        logsTextArea.setEditable(false);
        logsTextArea.setColumns(20);
        logsTextArea.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        logsTextArea.setRows(5);
        logsTextArea.setMargin(new java.awt.Insets(5, 5, 5, 5));
        logsTextArea.setMaximumSize(new java.awt.Dimension(2147483647, 200));
        logsScrollPane.setViewportView(logsTextArea);

        javax.swing.GroupLayout detailPanelLayout = new javax.swing.GroupLayout(detailPanel);
        detailPanel.setLayout(detailPanelLayout);
        detailPanelLayout.setHorizontalGroup(
            detailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(customersAndRequestsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rollStoragePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(logsScrollPane))
                .addContainerGap())
        );
        detailPanelLayout.setVerticalGroup(
            detailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rollStoragePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(customersAndRequestsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(logsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logsScrollPane)
                .addContainerGap())
        );

        detailScrollPane.setViewportView(detailPanel);

        tabbedPane.addTab("Priebeh", detailScrollPane);

        resultsScrollPane.setBorder(null);

        avrgWaitTimeLabel.setText("Priemerné zaplnenie skladov:");

        avrgWaitTimeValueLabel.setText("NA");

        javax.swing.GroupLayout resultsPanel2Layout = new javax.swing.GroupLayout(resultsPanel2);
        resultsPanel2.setLayout(resultsPanel2Layout);
        resultsPanel2Layout.setHorizontalGroup(
            resultsPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, resultsPanel2Layout.createSequentialGroup()
                .addComponent(avrgWaitTimeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addComponent(avrgWaitTimeValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        resultsPanel2Layout.setVerticalGroup(
            resultsPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultsPanel2Layout.createSequentialGroup()
                .addGroup(resultsPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(avrgWaitTimeLabel)
                    .addComponent(avrgWaitTimeValueLabel))
                .addContainerGap(131, Short.MAX_VALUE))
        );

        rollStorageFillingTableLabel.setText("Zaplnenie skladov");

        rollStorageFillingTable.setModel(new RollStorageTableModel());
        rollStorageFillingTableScrollPane.setViewportView(rollStorageFillingTable);

        javax.swing.GroupLayout rollStorageFillingTablePanelLayout = new javax.swing.GroupLayout(rollStorageFillingTablePanel);
        rollStorageFillingTablePanel.setLayout(rollStorageFillingTablePanelLayout);
        rollStorageFillingTablePanelLayout.setHorizontalGroup(
            rollStorageFillingTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rollStorageFillingTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rollStorageFillingTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rollStorageFillingTableScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(rollStorageFillingTablePanelLayout.createSequentialGroup()
                        .addComponent(rollStorageFillingTableLabel)
                        .addGap(0, 178, Short.MAX_VALUE))))
        );
        rollStorageFillingTablePanelLayout.setVerticalGroup(
            rollStorageFillingTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rollStorageFillingTablePanelLayout.createSequentialGroup()
                .addComponent(rollStorageFillingTableLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rollStorageFillingTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout resultsChartPanelLayout = new javax.swing.GroupLayout(resultsChartPanel);
        resultsChartPanel.setLayout(resultsChartPanelLayout);
        resultsChartPanelLayout.setHorizontalGroup(
            resultsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        resultsChartPanelLayout.setVerticalGroup(
            resultsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 280, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout resultsPanelLayout = new javax.swing.GroupLayout(resultsPanel);
        resultsPanel.setLayout(resultsPanelLayout);
        resultsPanelLayout.setHorizontalGroup(
            resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resultsChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(resultsPanelLayout.createSequentialGroup()
                        .addComponent(resultsPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                        .addComponent(rollStorageFillingTablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        resultsPanelLayout.setVerticalGroup(
            resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(resultsPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rollStorageFillingTablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(resultsChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        resultsScrollPane.setViewportView(resultsPanel);

        tabbedPane.addTab("Výsledky", resultsScrollPane);

        animationScrollPane.setBorder(null);

        javax.swing.GroupLayout animationPanelLayout = new javax.swing.GroupLayout(animationPanel);
        animationPanel.setLayout(animationPanelLayout);
        animationPanelLayout.setHorizontalGroup(
            animationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 728, Short.MAX_VALUE)
        );
        animationPanelLayout.setVerticalGroup(
            animationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 465, Short.MAX_VALUE)
        );

        animationScrollPane.setViewportView(animationPanel);

        tabbedPane.addTab("Vizualizácia", animationScrollPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(contentSeparator)
                    .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tabbedPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contentSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_startButtonActionPerformed
    {//GEN-HEADEREND:event_startButtonActionPerformed
		if (simulation.getState() == State.STOPPED)
		{
			new Thread(simulation).start();
		}
		else if (simulation.getState() == State.PAUSED)
		{
			simulation.pause();
		}
    }//GEN-LAST:event_startButtonActionPerformed

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_pauseButtonActionPerformed
    {//GEN-HEADEREND:event_pauseButtonActionPerformed
		if (simulation.getState() == State.RUNNING)
		{
			simulation.pause();
		}
    }//GEN-LAST:event_pauseButtonActionPerformed

    private void useStepsCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_useStepsCheckBoxActionPerformed
    {//GEN-HEADEREND:event_useStepsCheckBoxActionPerformed
		updateSimulationPauseEvent();
    }//GEN-LAST:event_useStepsCheckBoxActionPerformed

    private void speedSliderStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_speedSliderStateChanged
    {//GEN-HEADEREND:event_speedSliderStateChanged
		updateSimulationPauseEvent();
    }//GEN-LAST:event_speedSliderStateChanged

    private void allowLogsCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_allowLogsCheckBoxActionPerformed
    {//GEN-HEADEREND:event_allowLogsCheckBoxActionPerformed
		if (allowLogsCheckBox.isSelected())
		{
			FactorySimulation.LOGGER.addHandler(logsHandler);
		}
		else
		{
			FactorySimulation.LOGGER.removeHandler(logsHandler);
		}
    }//GEN-LAST:event_allowLogsCheckBoxActionPerformed

	private void updateComponents()
	{
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				setTitle(String.format("Replikácia %d / %d",
					simulation.getCurrentReplicationId(), simulation.getReplicationCount()));

				updateTimeProgressBar();
				updateTables();

				allowLogsCheckBox.setSelected(simulation.isEnabledLogging());

//				updateResults(simulation.getTimestamp(), simulation.getStats().createReplicationStatsSnapshot());
			}
		});
	}

	private void updateTimeProgressBar()
	{
		if (simulation.getState() == State.STOPPED)
		{
			timeProgressBar.setStringPainted(false);
			return;
		}

		int timestamp = (int) simulation.getCurrentReplication().getTimestamp();
		int maxTimestamp = (int) simulation.getMaxReplicationTimestamp();

		timeProgressBar.setMaximum(maxTimestamp);
		timeProgressBar.setValue(timestamp);
		timeProgressBar.setString(String.format("%s / %s",
			Utils.formatTime(timestamp), Utils.formatTime(maxTimestamp)));
		timeProgressBar.setStringPainted(true);
	}

	private void updateTables()
	{
		final int selectedRow = rollStorageTable.getSelectedRow();

		((RollStorageTableModel) rollStorageTable.getModel()).setValues(
			simulation.getFactory().getAllRollStorages());

		if (selectedRow != -1)
		{
			java.awt.EventQueue.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					rollStorageTable.setRowSelectionInterval(selectedRow, selectedRow);
				}
			});
		}

		if (selectedRollStorage != null)
		{
			((RollTableModel) rollTable.getModel()).setValues(
				selectedRollStorage.getRolls());
		}

		((CraneTableModel) craneTable.getModel()).setValues(
			simulation.getFactory().getCranes());

		((VehicleTableModel) vehicleTable.getModel()).setValues(
			simulation.getFactory().getVehicles());

		((EmployeeTableModel) employeeTable.getModel()).setValues(
			simulation.getFactory().getEmployees());
	}

//	private void updateResults(double timestamp, Stats.ReplicationStatsSnapshot stats)
//	{
//		avrgWaitTimeValueLabel.setText(String.format("%s (%.3f min)",
//			Utils.formatTime(stats.avrgWaitingTime), stats.avrgWaitingTime / 60.0));
//		customerCountValueLabel.setText(Long.toString(stats.customers));
//		servedCustomerCountValueLabel.setText(String.format("%d (%.3f %%)",
//			stats.servedCustomers, stats.customers > 0 ? 100.0 * stats.servedCustomers / stats.customers : 0));
//		leftCustomerCountValueLabel.setText(String.format("%d (%.3f %%)",
//			stats.leftCustomers, stats.customers > 0 ? 100.0 * stats.leftCustomers / stats.customers : 0));
//		avrgFreeEmployeeValueLabel.setText(String.format("%.3f / %.3f",
//			stats.avrgFreeCooks, stats.avrgFreeWaiters));
//		avrgPreparingMealTimeValueLabel.setText(String.format("%s (%.3f min)",
//			Utils.formatTime(stats.avrgPreparingMealTime), stats.avrgPreparingMealTime / 60.0));
//
//		ArrayList<Employee> employees = new ArrayList<>();
//		employees.addAll(simulation.getRestaurant().getCooks());
//		employees.addAll(simulation.getRestaurant().getWaiters());
//
//		EmployeeDowntimeTableModel model = (EmployeeDowntimeTableModel) rollStorageFillingTable.getModel();
//		model.setValues(employees);
//
//		if (lastAvrgWaitingTime != stats.avrgWaitingTime)
//		{
//			series.add(timestamp, stats.avrgWaitingTime, true);
//			chart.getXYPlot().getRangeAxis().setRange(series.getMinY(), series.getMaxY());
//
//			lastAvrgWaitingTime = stats.avrgWaitingTime;
//		}
//	}
	private void updateSimulationPauseEvent()
	{
		if (useStepsCheckBox.isSelected())
		{
			simulation.setSpeed(1000, 0.1);
		}
		else
		{
			simulation.setSpeed(1, (1000f - speedSlider.getValue()) / 1000);
		}
	}

	// <editor-fold defaultstate="collapsed" desc="Components">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox allowLogsCheckBox;
    private javax.swing.JPanel animationPanel;
    private javax.swing.JScrollPane animationScrollPane;
    private javax.swing.JLabel avrgWaitTimeLabel;
    private javax.swing.JLabel avrgWaitTimeValueLabel;
    private javax.swing.JSeparator contentSeparator;
    private javax.swing.JTable craneTable;
    private javax.swing.JLabel craneTableLabel;
    private javax.swing.JPanel craneTablePanel;
    private javax.swing.JScrollPane craneTableScrollPane;
    private javax.swing.JPanel customersAndRequestsPanel;
    private javax.swing.JPanel descriptionPanel;
    private javax.swing.JPanel detailPanel;
    private javax.swing.JScrollPane detailScrollPane;
    private javax.swing.JTable employeeTable;
    private javax.swing.JLabel employeeTableLabel;
    private javax.swing.JPanel employeeTablePanel;
    private javax.swing.JScrollPane employeeTableScrollPane;
    private javax.swing.JLabel logsLabel;
    private javax.swing.JPanel logsPanel;
    private javax.swing.JScrollPane logsScrollPane;
    private javax.swing.JTextArea logsTextArea;
    private javax.swing.JButton pauseButton;
    private javax.swing.JPanel resultsChartPanel;
    private javax.swing.JPanel resultsPanel;
    private javax.swing.JPanel resultsPanel2;
    private javax.swing.JScrollPane resultsScrollPane;
    private javax.swing.JTable rollStorageFillingTable;
    private javax.swing.JLabel rollStorageFillingTableLabel;
    private javax.swing.JPanel rollStorageFillingTablePanel;
    private javax.swing.JScrollPane rollStorageFillingTableScrollPane;
    private javax.swing.JPanel rollStoragePanel;
    private javax.swing.JTable rollStorageTable;
    private javax.swing.JLabel rollStorageTableLabel;
    private javax.swing.JPanel rollStorageTablePanel;
    private javax.swing.JScrollPane rollStorageTableScrollPane;
    private javax.swing.JTable rollTable;
    private javax.swing.JLabel rollTableLabel;
    private javax.swing.JPanel rollTablePanel;
    private javax.swing.JScrollPane rollTableScrollPane;
    private javax.swing.JLabel speedLabel;
    private javax.swing.JSlider speedSlider;
    private javax.swing.JButton startButton;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JProgressBar timeProgressBar;
    private javax.swing.JCheckBox useStepsCheckBox;
    private javax.swing.JTable vehicleTable;
    private javax.swing.JLabel vehicleTableLabel;
    private javax.swing.JPanel vehicleTablePanel;
    private javax.swing.JScrollPane vehicleTableScrollPane;
    // End of variables declaration//GEN-END:variables
	// </editor-fold>
}
