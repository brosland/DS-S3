package sk.uniza.fri.II008.s3.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import sk.uniza.fri.II008.ISimulation.ISimulationListener;
import sk.uniza.fri.II008.ISimulation.State;
import sk.uniza.fri.II008.SimulationListener;
import sk.uniza.fri.II008.s3.FactorySimulation;
import sk.uniza.fri.II008.s3.gui.tabelModels.RollStorageTableModel;
import sk.uniza.fri.II008.s3.model.Factory;

public class Window extends javax.swing.JFrame
{
	private final ISimulationListener simulationListener;
	private FactorySimulation simulation;
	private XYSeries series;
	private JFreeChart chart;
	private ReplicationWindow replicationWindow;

	public Window()
	{
		initComponents();
		initJFreeChart();
		setLocationRelativeTo(null);

		simulationListener = new SimulationListener()
		{
			@Override
			public void onReplicationEnd(final long replication, final Object[] values)
			{
				java.awt.EventQueue.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						if (replication > simulation.getReplicationCount() * 0.3
							&& replication % (simulation.getReplicationCount() / 200.0) == 0)
						{
//							updateStats(replication, (Stats.StatsSnapshot) values[1]);
						}

						updateReplicationProgressBar(replication);
					}
				});
			}

			@Override
			public void onStart()
			{
				java.awt.EventQueue.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						updateReplicationProgressBar(0);
						series.clear();

						stopButton.setEnabled(true);
						startButton.setText("Pause");
					}
				});
			}

			@Override
			public void onStop()
			{
				initSimulation(simulation);
			}
		};
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
        replicationLabel = new javax.swing.JLabel();
        replicationProgressBar = new javax.swing.JProgressBar();
        startButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        replicationDetailButton = new javax.swing.JButton();
        tabbedPane = new javax.swing.JTabbedPane();
        resultsScrollPane = new javax.swing.JScrollPane();
        resultsPanel = new javax.swing.JPanel();
        resultsPanel2 = new javax.swing.JPanel();
        avrgRollStorageFillingLabel = new javax.swing.JLabel();
        avrgRollStorageFillingValueLabel = new javax.swing.JLabel();
        intervalLabel = new javax.swing.JLabel();
        intervalValueLabel = new javax.swing.JLabel();
        rollStorageFillingPanel = new javax.swing.JPanel();
        rollStorageFillingLabel = new javax.swing.JLabel();
        rollStorageFillingScrollPane = new javax.swing.JScrollPane();
        rollStorageFillingTable = new javax.swing.JTable();
        resultsChartPanel = new javax.swing.JPanel();
        separator = new javax.swing.JSeparator();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newSimulationMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simulácia oceliarne");
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);

        replicationLabel.setText("Replikácia:");

        replicationProgressBar.setMaximum(0);
        replicationProgressBar.setToolTipText("");
        replicationProgressBar.setString("");

        startButton.setText("Štart");
        startButton.setEnabled(false);
        startButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                startButtonActionPerformed(evt);
            }
        });

        stopButton.setText("Stop");
        stopButton.setEnabled(false);
        stopButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                stopButtonActionPerformed(evt);
            }
        });

        replicationDetailButton.setText("Detail replikácie");
        replicationDetailButton.setEnabled(false);
        replicationDetailButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                replicationDetailButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout descriptionPanelLayout = new javax.swing.GroupLayout(descriptionPanel);
        descriptionPanel.setLayout(descriptionPanelLayout);
        descriptionPanelLayout.setHorizontalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(descriptionPanelLayout.createSequentialGroup()
                .addComponent(startButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stopButton)
                .addGap(187, 187, 187)
                .addComponent(replicationLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(replicationProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(replicationDetailButton))
        );
        descriptionPanelLayout.setVerticalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(replicationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(startButton)
                .addComponent(stopButton))
            .addComponent(replicationProgressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(descriptionPanelLayout.createSequentialGroup()
                .addComponent(replicationDetailButton)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        resultsScrollPane.setBorder(null);
        resultsScrollPane.setName(""); // NOI18N

        avrgRollStorageFillingLabel.setText("Priemerné zaplnenie skladov:");

        avrgRollStorageFillingValueLabel.setText("NA");

        intervalLabel.setText("90% interval spoľahlivosti:");

        intervalValueLabel.setText("NA");

        javax.swing.GroupLayout resultsPanel2Layout = new javax.swing.GroupLayout(resultsPanel2);
        resultsPanel2.setLayout(resultsPanel2Layout);
        resultsPanel2Layout.setHorizontalGroup(
            resultsPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultsPanel2Layout.createSequentialGroup()
                .addGroup(resultsPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(intervalLabel)
                    .addComponent(avrgRollStorageFillingLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addGroup(resultsPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(intervalValueLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(avrgRollStorageFillingValueLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        resultsPanel2Layout.setVerticalGroup(
            resultsPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultsPanel2Layout.createSequentialGroup()
                .addGroup(resultsPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(avrgRollStorageFillingLabel)
                    .addComponent(avrgRollStorageFillingValueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(resultsPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(intervalLabel)
                    .addComponent(intervalValueLabel))
                .addContainerGap(111, Short.MAX_VALUE))
        );

        rollStorageFillingLabel.setText("Zaplnenie skladov");

        rollStorageFillingTable.setModel(new RollStorageTableModel());
        rollStorageFillingScrollPane.setViewportView(rollStorageFillingTable);

        javax.swing.GroupLayout rollStorageFillingPanelLayout = new javax.swing.GroupLayout(rollStorageFillingPanel);
        rollStorageFillingPanel.setLayout(rollStorageFillingPanelLayout);
        rollStorageFillingPanelLayout.setHorizontalGroup(
            rollStorageFillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rollStorageFillingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rollStorageFillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rollStorageFillingScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(rollStorageFillingPanelLayout.createSequentialGroup()
                        .addComponent(rollStorageFillingLabel)
                        .addGap(0, 178, Short.MAX_VALUE))))
        );
        rollStorageFillingPanelLayout.setVerticalGroup(
            rollStorageFillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rollStorageFillingPanelLayout.createSequentialGroup()
                .addComponent(rollStorageFillingLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rollStorageFillingScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout resultsChartPanelLayout = new javax.swing.GroupLayout(resultsChartPanel);
        resultsChartPanel.setLayout(resultsChartPanelLayout);
        resultsChartPanelLayout.setHorizontalGroup(
            resultsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        resultsChartPanelLayout.setVerticalGroup(
            resultsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 149, Short.MAX_VALUE)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(rollStorageFillingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        resultsPanelLayout.setVerticalGroup(
            resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(resultsPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rollStorageFillingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(resultsChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        resultsScrollPane.setViewportView(resultsPanel);

        tabbedPane.addTab("Simulácia", resultsScrollPane);

        fileMenu.setText("Súbor");

        newSimulationMenuItem.setText("Nová simulácia");
        newSimulationMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                newSimulationMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(newSimulationMenuItem);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tabbedPane, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(separator)
                    .addComponent(descriptionPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
			"replikácia", // x-axis Label
			"priemerné zaplnenie skladu", // y-axis Label
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

    private void newSimulationMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_newSimulationMenuItemActionPerformed
    {//GEN-HEADEREND:event_newSimulationMenuItemActionPerformed
		final Window window = this;

		java.awt.EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				SimulationDialog dialog = new SimulationDialog(window, true)
				{
					@Override
					public void onSubmit(final SimulationDialog dialog)
					{
						if (simulation != null)
						{
							simulation.stop();
						}

						if (replicationWindow != null)
						{
							replicationWindow.dispose();
							replicationWindow = null;
						}

						Factory factory = Factory.createInstance();
						FactorySimulation simulation = new FactorySimulation(
							new Random(1), dialog.getReplicationCountValue(),
							dialog.getMaxTimestampValue(), factory);

						initSimulation(simulation);
						dialog.dispose();
					}
				};
				dialog.setVisible(true);
			}
		});
    }//GEN-LAST:event_newSimulationMenuItemActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_startButtonActionPerformed
    {//GEN-HEADEREND:event_startButtonActionPerformed
		if (simulation.getState() == State.STOPPED)
		{
			new Thread(simulation).start();
		}
		else
		{
			simulation.pause();
		}
    }//GEN-LAST:event_startButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_stopButtonActionPerformed
    {//GEN-HEADEREND:event_stopButtonActionPerformed
		simulation.stop();
    }//GEN-LAST:event_stopButtonActionPerformed

    private void replicationDetailButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_replicationDetailButtonActionPerformed
    {//GEN-HEADEREND:event_replicationDetailButtonActionPerformed
		if (simulation.getState() == State.RUNNING)
		{
			simulation.pause();
		}

		java.awt.EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				if (replicationWindow != null)
				{
					replicationWindow.setState(java.awt.Frame.NORMAL);
					replicationWindow.requestFocus();
					return;
				}

				replicationWindow = new ReplicationWindow(simulation);
				replicationWindow.addWindowListener(new WindowAdapter()
				{
					@Override
					public void windowClosing(WindowEvent evt)
					{
						replicationWindow = null;
					}
				});

				replicationWindow.setVisible(true);
			}
		});
    }//GEN-LAST:event_replicationDetailButtonActionPerformed

	private void initSimulation(final FactorySimulation simulation)
	{
		this.simulation = simulation;
		this.simulation.setSimulationListener(simulationListener);

		java.awt.EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				startButton.setText("Štart");
				startButton.setEnabled(true);
				stopButton.setEnabled(false);
				replicationDetailButton.setEnabled(true);
				updateReplicationProgressBar(simulation.getCurrentReplicationId());
			}
		});
	}

	private void updateReplicationProgressBar(final long replication)
	{
		if (simulation == null)
		{
			replicationProgressBar.setValue(0);
			replicationProgressBar.setStringPainted(false);
		}
		else
		{
			int replicationCount = (int) simulation.getReplicationCount();

			replicationProgressBar.setMaximum(replicationCount);
			replicationProgressBar.setValue((int) replication);
			replicationProgressBar.setString(String.format("%d / %d",
				replication, replicationCount));
			replicationProgressBar.setStringPainted(true);
		}
	}

//	private void updateStats(long replication, Stats.StatsSnapshot stats)
//	{
//		avrgWaitTimeValueLabel.setText(String.format("%s (%.3f min)",
//			Utils.formatTime(stats.avrgWaitingTime), stats.avrgWaitingTime / 60.0));
//		intervalValueLabel.setText(String.format("<%.3f ; %.3f)",
//			stats.confidenceInterval[0] / 60.0, stats.confidenceInterval[1] / 60.0));
//		customerCountValueLabel.setText(String.format("%.3f", stats.avrgCustomers));
//		servedCustomerCountValueLabel.setText(String.format("%.3f %%", stats.avrgServedCustomers * 100.0));
//		leftCustomerCountValueLabel.setText(String.format("%.3f %%", stats.avrgLeftCustomers * 100.0));
//		avrgFreeEmployeeValueLabel.setText(String.format("%.3f / %.3f",
//			stats.avrgFreeCooks, stats.avrgFreeWaiters));
//		avrgPreparingMealTimeValueLabel.setText(String.format("%s (%.3f min)",
//			Utils.formatTime(stats.avrgPreparingMealTime), stats.avrgPreparingMealTime / 60.0));
//
//		ArrayList<Employee> employees = new ArrayList<>();
//		employees.addAll(simulation.getRestaurant().getCooks());
//		employees.addAll(simulation.getRestaurant().getWaiters());
//
//		EmployeeDowntimeTableModel model = (EmployeeDowntimeTableModel) employeeDowntimeTable.getModel();
//		model.setValues(employees);
//
//		series.add(replication, stats.avrgWaitingTime, true);
//		chart.getXYPlot().getRangeAxis().setRange(series.getMinY(), series.getMaxY());
//	}

	// <editor-fold defaultstate="collapsed" desc="Components">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel avrgRollStorageFillingLabel;
    private javax.swing.JLabel avrgRollStorageFillingValueLabel;
    private javax.swing.JPanel descriptionPanel;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JLabel intervalLabel;
    private javax.swing.JLabel intervalValueLabel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem newSimulationMenuItem;
    private javax.swing.JButton replicationDetailButton;
    private javax.swing.JLabel replicationLabel;
    private javax.swing.JProgressBar replicationProgressBar;
    private javax.swing.JPanel resultsChartPanel;
    private javax.swing.JPanel resultsPanel;
    private javax.swing.JPanel resultsPanel2;
    private javax.swing.JScrollPane resultsScrollPane;
    private javax.swing.JLabel rollStorageFillingLabel;
    private javax.swing.JPanel rollStorageFillingPanel;
    private javax.swing.JScrollPane rollStorageFillingScrollPane;
    private javax.swing.JTable rollStorageFillingTable;
    private javax.swing.JSeparator separator;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables
	// </editor-fold>
}