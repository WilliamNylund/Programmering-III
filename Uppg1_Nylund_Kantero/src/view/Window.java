package view;

import javax.swing.*;
import JSON.JSONObject;
import controller.DataManager;
import model.Model;
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
		
		try {
			DataManager.getChoices();
		} catch (FileNotFoundException e2) {
			System.out.println("file not found");
		}
		
		String[] dataSeries = {"open", "high", "low", "close", "volume", "adjusted close", "dividend amount", "split coefficient"};
		JComboBox<String> comboBox1 = new JComboBox<String>(dataSeries);
		cons.gridx = 1;
		cons.gridy = 0;
		panel.add(comboBox1, cons);
		
		
		JComboBox<String> comboBox2 = new JComboBox<String>(Model.timeSeriesChoices);
		cons.gridx = 1;
		cons.gridy = 1;
		panel.add(comboBox2, cons);
		
		JComboBox<String> comboBox3 = new JComboBox<String>(Model.symbolChoices);
		cons.gridx = 1;
		cons.gridy = 2;
		panel.add(comboBox3, cons);
		
		JComboBox<String> comboBox4 = new JComboBox<String>(Model.timeIntervalChoices);
		cons.gridx = 1;
		cons.gridy = 3;
		panel.add(comboBox4, cons);
		
		JComboBox<String> comboBox5 = new JComboBox<String>(Model.outPutSizeChoices);
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
					textArea.append(controller.DataManager.getData(dataSeries, timeSeries, symbol, timeInterval, outputSize));
					
					if(dataSeries.equals("open")) chart.updateXYSeries("Stock", model.Model.xlist, model.Model.openGraph, null);
					else if(dataSeries.equals("high")) chart.updateXYSeries("Stock", model.Model.xlist, model.Model.highGraph, null);
					else if(dataSeries.equals("low")) chart.updateXYSeries("Stock", model.Model.xlist, model.Model.lowGraph, null);
					else if(dataSeries.equals("close")) chart.updateXYSeries("Stock", model.Model.xlist, model.Model.closeGraph, null);
					else if(dataSeries.equals("volume")) chart.updateXYSeries("Stock", model.Model.xlist, model.Model.volumeGraph, null);
					pnlChart.repaint();
					
					
					comboBox1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							textArea.setText(null);
							String dataSeries = (String) comboBox1.getSelectedItem();
							if(dataSeries.equals("open")) {
								textArea.append(model.Model.openStr);
								chart.updateXYSeries("Stock", model.Model.xlist, model.Model.openGraph, null);
							}
							else if(dataSeries.equals("high")) {
								textArea.append(model.Model.highStr);
								chart.updateXYSeries("Stock", model.Model.xlist, model.Model.highGraph, null);
							}
							else if(dataSeries.equals("low")) {
								textArea.append(model.Model.lowStr);
								chart.updateXYSeries("Stock", model.Model.xlist, model.Model.lowGraph, null);
							}
							else if(dataSeries.equals("close")) {
								textArea.append(model.Model.closeStr);
								chart.updateXYSeries("Stock", model.Model.xlist, model.Model.closeGraph, null);
							}
							else if(dataSeries.equals("volume")) {
								textArea.append(model.Model.volumeStr);
								chart.updateXYSeries("Stock", model.Model.xlist, model.Model.volumeGraph, null);
							}
							else if(dataSeries.equals("adjusted close")) textArea.append(model.Model.adjustedCloseStr);
							else if(dataSeries.equals("dividend amount")) textArea.append(model.Model.dividendAmountStr);
							else if(dataSeries.equals("split coefficient")) textArea.append(model.Model.splitCoefficientStr);
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
				if (!timeSeries.equals("TIME_SERIES_INTRADAY")) {
					comboBox4.setEnabled(false);
				} else comboBox4.setEnabled(true);
				if (!timeSeries.equals("TIME_SERIES_INTRADAY") && !timeSeries.equals("TIME_SERIES_DAILY") && !timeSeries.equals("TIME_SERIES_DAILY_ADJUSTED")) {
					comboBox5.setEnabled(false);
				} else comboBox5.setEnabled(true);
				
			}
		});
	}

	
}
