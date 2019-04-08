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

public class Window extends JFrame implements FocusListener {

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
				
		JLabel label0 = new JLabel("Api Key");
		cons.gridx = 0;
		cons.gridy = 0;
		panel.add(label0, cons);
		
		JLabel label1 = new JLabel("Data Series");
		cons.gridx = 0;
		cons.gridy = 1;
		panel.add(label1, cons);
		
		JLabel label2 = new JLabel("Time Series");
		cons.gridx = 0;
		cons.gridy = 2;
		panel.add(label2, cons);
		
		JLabel label3 = new JLabel("Symbol 1");
		cons.gridx = 0;
		cons.gridy = 3;
		panel.add(label3, cons);
		
		JLabel label31 = new JLabel("Symbol 2");
		cons.gridx = 0;
		cons.gridy = 4;
		panel.add(label31, cons);
		
		JLabel label4 = new JLabel("Time Interval");
		cons.gridx = 0;
		cons.gridy = 5;
		panel.add(label4, cons);
		
		JLabel label5 = new JLabel("Output Size");
		cons.gridx = 0;
		cons.gridy = 6;
		panel.add(label5, cons);
		
		JLabel startLabel = new JLabel("Start date (yyyy-mm-dd)");
		cons.gridx = 0;
		cons.gridy = 7;
		panel.add(startLabel, cons);
		
		JLabel endLabel = new JLabel("End date (yyyy-mm-dd)");
		cons.gridx = 0;
		cons.gridy = 8;
		panel.add(endLabel, cons);
		
		try {
			DataManager.getChoices();
		} catch (FileNotFoundException e2) {
			System.out.println("file not found");
		}
		
		JTextField apiEdit = new JTextField();
		apiEdit.setText(Model.apiKey);
		cons.gridx = 1;
		cons.gridy = 0;
		panel.add(apiEdit, cons);
		
		String[] dataSeries = {"open", "high", "low", "close", "volume", "adjusted close", "dividend amount", "split coefficient"};
		JComboBox<String> comboBox1 = new JComboBox<String>(dataSeries);
		cons.gridx = 1;
		cons.gridy = 1;
		panel.add(comboBox1, cons);
		
		
		JComboBox<String> comboBox2 = new JComboBox<String>(Model.timeSeriesChoices);
		cons.gridx = 1;
		cons.gridy = 2;
		panel.add(comboBox2, cons);
		
		JComboBox<String> comboBox3 = new JComboBox<String>(Model.symbolChoices);
		cons.gridx = 1;
		cons.gridy = 3;
		panel.add(comboBox3, cons);
		
		JComboBox<String> comboBox31 = new JComboBox<String>(Model.symbolChoices);
		cons.gridx = 1;
		cons.gridy = 4;
		panel.add(comboBox31, cons);
		
		JComboBox<String> comboBox4 = new JComboBox<String>(Model.timeIntervalChoices);
		cons.gridx = 1;
		cons.gridy = 5;
		panel.add(comboBox4, cons);
		
		JComboBox<String> comboBox5 = new JComboBox<String>(Model.outPutSizeChoices);
		cons.gridx = 1;
		cons.gridy = 6;
		panel.add(comboBox5, cons);
		
		JTextField startDate = new JTextField();
		cons.gridx = 1;
		cons.gridy = 7;
		panel.add(startDate, cons);
		

		
		JTextField endDate = new JTextField();
		cons.gridx = 1;
		cons.gridy = 8;
		panel.add(endDate, cons);
		
		cons.fill = GridBagConstraints.NONE;
		
		JButton button = new JButton("---Do query---");
		cons.gridx = 1;
		cons.gridy = 9;
		panel.add(button, cons);
		
		
		JTextArea textArea = new JTextArea(20,30);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(10,60,780,500);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	
		cons.gridheight = 2;
		cons.gridwidth = 2;
		cons.gridx = 0;
		cons.gridy = 10;
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		panel.add(scrollPane, cons);
		
		double[] xData = new double[] {1};
		double[] yData = new double[] {1};
		double[] x1Data = new double[] {2};
		double[] y1Data = new double[] {2};

		XYChart chart = new XYChartBuilder().height(330).width(800).title("Stock graph").build();
		chart.setXAxisTitle("Stockprices from old to new");
		XYSeries series = chart.addSeries("Symbol1", xData, yData);		
		
		XYStyler styler = chart.getStyler();
		JPanel pnlChart = new XChartPanel(chart);
		
		pnlChart.validate();
		
		cons.gridheight = 3;
		cons.gridwidth = 3;
		cons.gridx = 2;
		cons.gridy = 11;
		
		panel.add(pnlChart, cons);
	
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
		
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.DataManager.reset();
				chart.removeSeries("Symbol2"); //sattt till
				if (DataManager.isValidDate(startDate.getText()) && DataManager.isValidDate(endDate.getText())) {
					String dataSeries = (String) comboBox1.getSelectedItem();
					String timeSeries = (String) comboBox2.getSelectedItem();
					String symbol = (String) comboBox3.getSelectedItem();
					String symbol2 = (String) comboBox31.getSelectedItem();
					String timeInterval = (String) comboBox4.getSelectedItem();
					String outputSize = (String) comboBox5.getSelectedItem();
					String apiKey = apiEdit.getText();
					String sd = startDate.getText();
					String ed = endDate.getText();
					try {
						textArea.setText(null);
						if (ed.equals("")) ed = "9999";
						if (symbol.equals("null") && symbol2.equals("null")) textArea.append("choose symbol");
						else {
							if (bothSymbolsChosen(symbol, symbol2)) {
								controller.DataManager.getData(dataSeries, timeSeries, symbol, timeInterval, outputSize, apiKey, sd, ed);
								controller.DataManager.getData(dataSeries, timeSeries, symbol2, timeInterval, outputSize, apiKey, sd, ed);
								textArea.append(controller.DataManager.makeStrings(dataSeries, symbol, symbol2));

							} else {
								if (symbol.equals("null")) {
									controller.DataManager.getData(dataSeries, timeSeries, symbol2, timeInterval, outputSize, apiKey, sd, ed);
									textArea.append(controller.DataManager.makeString(dataSeries));
								} else {
									controller.DataManager.getData(dataSeries, timeSeries, symbol, timeInterval, outputSize, apiKey, sd, ed);
									textArea.append(controller.DataManager.makeString(dataSeries));
								}
							}
							//compact funkar men int full
							//probleem då int listorna e lika stor av två symboler
							//test aapl + google = error pga ojämna tal
							//aapl å A går men blir fel värden
							//fix dataManager fö alla dataseries
							if(dataSeries.equals("open")) {
								if (bothSymbolsChosen(symbol, symbol2)) {
									//Måst kanske lägg till chart.removeSeries("Symbol2");

									chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.openGraph, null);
									XYSeries series2 = chart.addSeries("Symbol2", model.Model.xlist, model.Model.openGraph2);
								} else {
									chart.removeSeries("Symbol2");
									chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.openGraph, null);

								}
							}
							else if(dataSeries.equals("high")) {
								if (bothSymbolsChosen(symbol, symbol2)) {
									chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.highGraph, null);
									XYSeries series2 = chart.addSeries("Symbol2", model.Model.xlist, model.Model.highGraph2);
								} else {
									chart.removeSeries("Symbol2");
									chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.highGraph, null);

								}
							}
							else if(dataSeries.equals("low")) {
								if (bothSymbolsChosen(symbol, symbol2)) {
									chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.lowGraph, null);
									XYSeries series2 = chart.addSeries("Symbol2", model.Model.xlist, model.Model.lowGraph2);
								} else {
									chart.removeSeries("Symbol2");
									chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.lowGraph, null);

								}
							}
							else if(dataSeries.equals("close")) {
								if (bothSymbolsChosen(symbol, symbol2)) {
									chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.closeGraph, null);
									XYSeries series2 = chart.addSeries("Symbol2", model.Model.xlist, model.Model.closeGraph2);
								} else {
									chart.removeSeries("Symbol2");
									chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.closeGraph, null);

								}
							}
							else if(dataSeries.equals("volume")) {
								if (bothSymbolsChosen(symbol, symbol2)) {
									chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.volumeGraph, null);
									XYSeries series2 = chart.addSeries("Symbol2", model.Model.xlist, model.Model.volumeGraph2);
								} else {
									chart.removeSeries("Symbol2");
									chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.volumeGraph, null);

								}
							}
							else if(dataSeries.equals("adjusted close")) {
								if (bothSymbolsChosen(symbol, symbol2)) {
									chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.adjustedCloseGraph, null);
									XYSeries series2 = chart.addSeries("Symbol2", model.Model.xlist, model.Model.adjustedCloseGraph2);
								} else {
									chart.removeSeries("Symbol2");
									chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.adjustedCloseGraph, null);

								}
							}
							else if(dataSeries.equals("dividend amount")) {
								if (bothSymbolsChosen(symbol, symbol2)) {
									chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.dividendAmountGraph, null);
									XYSeries series2 = chart.addSeries("Symbol2", model.Model.xlist, model.Model.dividendAmountGraph2);
								} else {
									chart.removeSeries("Symbol2");
									chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.dividendAmountGraph, null);

								}
							}
							else if(dataSeries.equals("split coefficient")) {
								if (bothSymbolsChosen(symbol, symbol2)) {
									chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.splitCoefficientGraph, null);
									XYSeries series2 = chart.addSeries("Symbol2", model.Model.xlist, model.Model.splitCoefficientGraph2);
								} else {
									chart.removeSeries("Symbol2");
									chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.splitCoefficientGraph, null);

								}
							}
							pnlChart.repaint();
							pnlChart.validate();
						
							comboBox1.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
								try {
									textArea.setText(null);
									String dataSeries = (String) comboBox1.getSelectedItem();
									if(dataSeries.equals("open")) {
										textArea.append(model.Model.openStr);
										if (bothSymbolsChosen(symbol, symbol2)) {
											chart.removeSeries("Symbol2");
											chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.openGraph, null);
											XYSeries series2 = chart.addSeries("Symbol2", model.Model.xlist, model.Model.openGraph2);
										} else {
											chart.removeSeries("Symbol2");
											chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.openGraph, null);
										}
									}
									else if(dataSeries.equals("high")) {
										textArea.append(model.Model.highStr);
										chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.highGraph, null);
									}
									else if(dataSeries.equals("low")) {
										textArea.append(model.Model.lowStr);
										chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.lowGraph, null);
									}
									else if(dataSeries.equals("close")) {
										textArea.append(model.Model.closeStr);
										chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.closeGraph, null);
									}
									else if(dataSeries.equals("volume")) {
										textArea.append(model.Model.volumeStr);
										chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.volumeGraph, null);
									}
									else if(dataSeries.equals("adjusted close")) {
										textArea.append(model.Model.adjustedCloseStr);
										chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.adjustedCloseGraph, null);										}
									else if(dataSeries.equals("dividend amount")) {
										textArea.append(model.Model.dividendAmountStr);
										chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.dividendAmountGraph, null);
									}
									else if(dataSeries.equals("split coefficient")) {
										textArea.append(model.Model.splitCoefficientStr);
										chart.updateXYSeries("Symbol1", model.Model.xlist, model.Model.splitCoefficientGraph, null);		
									}
									
									pnlChart.repaint();
								} catch (IllegalArgumentException e3) {
									textArea.append(timeSeries + " does not contain " + dataSeries);
								}
								}
							});
						}
					}
					catch (IllegalArgumentException e3) {
						textArea.append(timeSeries + " does not contain " + dataSeries);
					} 
					catch (NoSuchElementException e2) {
						textArea.append("Data only available from recent past");
					} 
					catch (JSONException e4) {
						textArea.append("Please don't spam queries.");
					}
					catch (IOException e1) {
		
					}
				} else {
					textArea.setText(null);
					textArea.append("Enter valid dates");
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
		startDate.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				startDate.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!DataManager.isValidDate(startDate.getText())) {
					startDate.setText("Please enter valid date");
				}
			}
			
		});
		endDate.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				endDate.setText("");
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!DataManager.isValidDate(startDate.getText())) {
					endDate.setText("Please enter valid date");				
				}
			}
		});
		
		
	}
	
	
	@Override
	public void focusGained(FocusEvent e) {
		System.out.println("focusGained");
	}

	@Override
	public void focusLost(FocusEvent e) {
		System.out.println("focusLost");
		
	}
	public boolean bothSymbolsChosen(String Symbol, String Symbol2) {
		if (!Symbol.equals("null") && !Symbol2.equals("null")){
			return true;
		} else return false;
		
	}
	
}