package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import controller.DataManager;

import java.util.Calendar;
import model.Model;
import model.Portfolio;
import model.Stock;

public class BuyWindow { //Fönster för att köpa aktier

	JFrame frame;
	JPanel panel;
	
	public BuyWindow(Portfolio portfolio) { 
		frame = new JFrame("Buy stock");
		panel = new JPanel();
		
		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints cons = new GridBagConstraints();
		frame.setSize(300,225);
		frame.setLocationRelativeTo(null);
		panel.setLayout(gridBag);
		
		cons.fill = GridBagConstraints.HORIZONTAL;
		
		JLabel monthLabel = new JLabel();
		monthLabel.setText("Month");
		cons.gridx = 1;
		cons.gridy = 0;
		panel.add(monthLabel, cons);
		
		SpinnerListModel monthModel = new SpinnerListModel(getMonths());
		JSpinner monthSpinner = new JSpinner(monthModel);
		cons.gridx = 2;
		cons.gridy = 0;
		panel.add(monthSpinner, cons);
		
		
		JLabel yearLabel = new JLabel();
		yearLabel.setText("Year");
		cons.gridx = 1;
		cons.gridy = 1;
		panel.add(yearLabel, cons);
		
		SpinnerListModel yearModel = new SpinnerListModel(getYears());
		JSpinner yearSpinner = new JSpinner(yearModel);
		cons.gridx = 2;
		cons.gridy = 1;
		panel.add(yearSpinner, cons);
		
		JLabel symbolLabel = new JLabel();
		symbolLabel.setText("Symbol");
		cons.gridx = 1;
		cons.gridy = 2;
		panel.add(symbolLabel, cons);
	
		JComboBox<String> symbolCombobox = new JComboBox<String>(Model.symbolChoices);
		cons.gridx = 2;
		cons.gridy = 2;
		panel.add(symbolCombobox, cons);
		
		JLabel amountLabel = new JLabel();
		amountLabel.setText("Amount");
		cons.gridx = 1;
		cons.gridy = 3;
		panel.add(amountLabel, cons);
		
		SpinnerNumberModel numberModel = new SpinnerNumberModel(1,1,null,1);
		JSpinner amountSpinner = new JSpinner(numberModel);
		cons.gridx = 2;
		cons.gridy = 3;
		panel.add(amountSpinner, cons);
		
		JButton buyButton = new JButton("buy");
		cons.gridx = 2;
		cons.gridy = 4;
		panel.add(buyButton, cons);
		
		frame.add(panel);
		//frame.pack();
		frame.setVisible(true);	
		
		buyButton.addActionListener(new ActionListener() { //Köper aktien, och uppdaterar portfolie listan över aktier samt totala värdet
			@Override
			public void actionPerformed(ActionEvent e) {
				String month = (String) monthSpinner.getValue();
				String year = (String) yearSpinner.getValue();
				String date = year + "-" + month;
				int amount = (int) amountSpinner.getValue();
				String stockName = symbolCombobox.getSelectedItem().toString();
				if (stockName.equals("null")) {
					JOptionPane.showMessageDialog(null,"Choose symbol","Ehh?",JOptionPane.PLAIN_MESSAGE);
					return;
				}
				if (stockName.equals("GOOG") && Integer.parseInt(year) < 2015) {
					JOptionPane.showMessageDialog(null,"Data not available before 2015","Apologies!",JOptionPane.PLAIN_MESSAGE);
					return;
				}
				try {
				double value = DataManager.getStockPrice(date, stockName);
				for (int i = 0; i < amount; i++) {
					Stock stock = new Stock(stockName, value, date);
					portfolio.addStock(stock);
				}
				LoadPortfolioWindow.listModel.clear();
				portfolio.updatePortfolio();
				} catch(IOException e2) {
					
				}
				frame.dispose();
				
			
			}});
	}
	private ArrayList getMonths() {
		ArrayList<String> months = new ArrayList<String>();
		months.add("01");
		months.add("02");
		months.add("03");
		months.add("04");
		months.add("05");
		months.add("06");
		months.add("07");
		months.add("08");
		months.add("09");
		months.add("10");
		months.add("11");
		months.add("12");
		return months;
	}
	
	private ArrayList getYears() {
		ArrayList<String> years = new ArrayList<String>();
		for (int i = 2000; i < 2020; i++) {
			years.add(Integer.toString(i));
		}
		
		return years;
	}

}
