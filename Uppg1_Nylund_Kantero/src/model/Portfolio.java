package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;

import view.LoadPortfolioWindow;

public class Portfolio implements Serializable, ActionListener {
	
	ArrayList<Stock> stocks = new ArrayList<Stock>();
	String name;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String dateCreated;
	double totalValue;
	int amountOfStocks;
	
	
	public Portfolio(String name) {
		this.name = name;
		Calendar date = Calendar.getInstance();
		dateCreated = sdf.format(date.getTime());
		JMenuItem portfolio = new JMenuItem(name);
		portfolio.addActionListener(this);
		view.MainWindow.loadPortfolioItem.add(portfolio);
		view.MainWindow.loadPortfolioItem.remove(view.MainWindow.noPortfolios);
		save();
	}
	public String getName() {
		return name;
	}
	public void save() { //sparar portfolion i en .ser fil
		Object[] portfolioValues = new Object[3];
		portfolioValues[0] = this.name;
		portfolioValues[1] = this.dateCreated;
		portfolioValues[2] = this.stocks;
		
		try {
			File f = new File(name + ".ser"); //skapar filen för objektet
			FileOutputStream fos = new FileOutputStream(f); 
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(portfolioValues);
			fos.close();
			oos.close();
		} catch(IOException e) {
			System.out.println("Save-Exception");
		}
	}
	
	public void load (String file) {  //laddar portföljen från .ser filen
		//Portfolierna sparas ej vid omstart av programmet
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object[] portfolioValues = (Object[]) ois.readObject();
			this.name = (String) portfolioValues[0];
			this.dateCreated = (String) portfolioValues[1];
			this.stocks = (ArrayList<Stock>) portfolioValues[2];
		} catch(IOException e) {
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) { //öppnar portföljen
		String portfolioName = e.getActionCommand();
		load(portfolioName + ".ser");
		LoadPortfolioWindow loadWindow = new LoadPortfolioWindow(this);
	}
	public void addStock(Stock stock) { //sätter till aktien i portföljen
		stocks.add(stock);
	}
	public void updatePortfolio() { //uppdaterar portföljens aktier samt totala värdet
		this.totalValue = 0.0;
		this.amountOfStocks = 0;
		LoadPortfolioWindow.listModel.clear();
		LoadPortfolioWindow.amountOfStocks.setText("");
		for (int i = 0; i < stocks.size(); i++) {
			String stockString;
			Stock stock = stocks.get(i);
			stockString = stock.getName() + ": " + Double.toString(stock.getValue()) + "  Date bought: " + stock.getDateBought();
			LoadPortfolioWindow.listModel.addElement(stockString);
			totalValue += stock.getValue();
			amountOfStocks++;
			LoadPortfolioWindow.amountOfStocks.setText("Amount of stocks: " + Integer.toString(amountOfStocks));
			
		}
		
		DecimalFormat numberFormat = new DecimalFormat("#.000");
		LoadPortfolioWindow.totalValueField.setText(numberFormat.format(totalValue));
		
	}
	public double getTotalValue() {
		return totalValue;
	}
	public void removeStock(String symbol, String value) {
		boolean symbolNotRemoved = true;
		int i = 0;
		while (symbolNotRemoved) {
			Stock stock = stocks.get(i);
			if (stock.getName().equals(symbol) && Double.toString(stock.getValue()).equals(value)) {
				stocks.remove(i);
				symbolNotRemoved = false;
			}
			i++;
		}
	}
	
	
}
