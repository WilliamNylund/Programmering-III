package uppgifter;

import javax.swing.*;
import JSON.JSONObject;
import JSON.JSONArray;
import JSON.JSONException;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;
import org.knowm.*;
import org.knowm.xchart.*;
import org.knowm.xchart.style.XYStyler;

public class Window extends JFrame {
	
	public Window() {
		
		JFrame frame = new JFrame("Stock Analyzer");
		JPanel panel = new JPanel();
		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints cons = new GridBagConstraints();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 600);
		frame.setLocationRelativeTo(null);
		panel.setLayout(gridBag);
		
		cons.fill = GridBagConstraints.HORIZONTAL;
		JLabel label1 = new JLabel("Data Series");
		cons.gridx = 0;
		cons.gridy = 0;
		panel.add(label1, cons);
		
		cons.gridx = 0;
		cons.gridy = 1;
		JLabel label2 = new JLabel("Time Series");
		panel.add(label2, cons);
		
		JLabel label3 = new JLabel("Symbol");
		cons.gridx = 0;
		cons.gridy = 2;
		panel.add(label3, cons);
		
		cons.gridx = 0;
		cons.gridy = 3;
		JLabel label4 = new JLabel("Time Interval");
		panel.add(label4, cons);
		
		JLabel label5 = new JLabel("Output Size");
		cons.gridx = 0;
		cons.gridy = 4;
		panel.add(label5, cons);
		
		String[] dataSeries = {"open", "high", "low", "close", "volume", "adjusted close", "dividend amount", "split coefficient"};
		JComboBox<String> comboBox1 = new JComboBox<String>(dataSeries);
		cons.gridx = 1;
		cons.gridy = 0;
		panel.add(comboBox1, cons);
		
		String[] timeSeries = { "TIME_SERIES_INTRADAY", "TIME_SERIES_DAILY", "TIME_SERIES_DAILY_ADJUSTED", "TIME_SERIES_WEEKLY", "TIME_SERIES_WEEKLY_ADJUSTED","TIME_SERIES_MONTHLY","TIME_SERIES_MONTHLY_ADJUSTED"};
		JComboBox<String> comboBox2 = new JComboBox<String>(timeSeries);
		cons.gridx = 1;
		cons.gridy = 1;
		panel.add(comboBox2, cons);
		
		String[] symbols = { "AAPL", "GOOG", "MSFT"};
		JComboBox<String> comboBox3 = new JComboBox<String>(symbols);
		cons.gridx = 1;
		cons.gridy = 2;
		panel.add(comboBox3, cons);
		
		String[] timeInterval = { "1min", "5min", "15min", "30min", "60min"};
		JComboBox<String> comboBox4 = new JComboBox<String>(timeInterval);
		cons.gridx = 1;
		cons.gridy = 3;
		panel.add(comboBox4, cons);
		
		String[] outputSize = { "full", "compact"};
		JComboBox<String> comboBox5 = new JComboBox<String>(outputSize);
		cons.gridx = 1;
		cons.gridy = 4;
		panel.add(comboBox5, cons);
		cons.fill = GridBagConstraints.NONE;

		
		JButton button = new JButton("---Do query---");
		cons.gridx = 1;
		cons.gridy = 5;
		panel.add(button, cons);
		
		
		JTextArea textArea = new JTextArea(20,30);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(10,60,780,500);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	
		cons.gridheight = 2;
		cons.gridwidth = 2;
		cons.gridx = 0;
		cons.gridy = 6;
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		panel.add(scrollPane, cons);
		
		double[] xData = new double[] {1};
		double[] yData = new double[] {1};

		XYChart chart = new XYChartBuilder().height(330).width(800).title("Stock graph").build();
		chart.setXAxisTitle("Stockprices from old to new");
		XYSeries series = chart.addSeries("Stock", xData, yData);
		XYStyler styler = chart.getStyler();
		JPanel pnlChart = new XChartPanel(chart);
		
		pnlChart.validate();
		
		cons.gridheight = 3;
		cons.gridwidth = 3;
		cons.gridx = 2;
		cons.gridy = 7;
		
		panel.add(pnlChart, cons);
	
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
		
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String dataSeries = (String) comboBox1.getSelectedItem();
				String timeSeries = (String) comboBox2.getSelectedItem();
				String symbol = (String) comboBox3.getSelectedItem();
				String timeInterval = (String) comboBox4.getSelectedItem();
				String outputSize = (String) comboBox5.getSelectedItem();
				
				try {
					textArea.setText(null);
					textArea.append(DataManager.getData(dataSeries, timeSeries, symbol, timeInterval, outputSize));
					
					if(dataSeries.equals("open")) chart.updateXYSeries("Stock", DataManager.xlist, DataManager.openGraph, null);
					else if(dataSeries.equals("high")) chart.updateXYSeries("Stock", DataManager.xlist, DataManager.highGraph, null);
					else if(dataSeries.equals("low")) chart.updateXYSeries("Stock", DataManager.xlist, DataManager.lowGraph, null);
					else if(dataSeries.equals("close")) chart.updateXYSeries("Stock", DataManager.xlist, DataManager.closeGraph, null);
					else if(dataSeries.equals("volume")) chart.updateXYSeries("Stock", DataManager.xlist, DataManager.volumeGraph, null);
					pnlChart.repaint();
					
					
					comboBox1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							textArea.setText(null);
							String dataSeries = (String) comboBox1.getSelectedItem();
							if(dataSeries.equals("open")) {
								textArea.append(DataManager.openStr);
								chart.updateXYSeries("Stock", DataManager.xlist, DataManager.openGraph, null);
							}
							else if(dataSeries.equals("high")) {
								textArea.append(DataManager.highStr);
								chart.updateXYSeries("Stock", DataManager.xlist, DataManager.highGraph, null);
							}
							else if(dataSeries.equals("low")) {
								textArea.append(DataManager.lowStr);
								chart.updateXYSeries("Stock", DataManager.xlist, DataManager.lowGraph, null);
							}
							else if(dataSeries.equals("close")) {
								textArea.append(DataManager.closeStr);
								chart.updateXYSeries("Stock", DataManager.xlist, DataManager.closeGraph, null);
							}
							else if(dataSeries.equals("volume")) {
								textArea.append(DataManager.volumeStr);
								chart.updateXYSeries("Stock", DataManager.xlist, DataManager.volumeGraph, null);
							}
							else if(dataSeries.equals("adjusted close")) textArea.append(DataManager.adjustedCloseStr);
							else if(dataSeries.equals("dividend amount")) textArea.append(DataManager.dividendAmountStr);
							else if(dataSeries.equals("split coefficient")) textArea.append(DataManager.splitCoefficientStr);
							pnlChart.repaint();
						}
					});
				} catch (IOException e1) {
					
				}
			}
		});
		
		
		comboBox2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String timeSeries = (String) comboBox2.getSelectedItem();
				
				if (timeSeries != "TIME_SERIES_INTRADAY") {
					comboBox4.setEnabled(false);
				} else comboBox4.setEnabled(true);
				if (timeSeries != "TIME_SERIES_INTRADAY" && timeSeries != "TIME_SERIES_DAILY" && timeSeries != "TIME_SERIES_DAILY_ADJUSTED") {
					comboBox5.setEnabled(false);
				} else comboBox5.setEnabled(true);
				
			}
		});
	}

	
}
