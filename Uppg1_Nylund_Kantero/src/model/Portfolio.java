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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;

import view.LoadPortfolio;

public class Portfolio implements Serializable, ActionListener {
	
	ArrayList<Stock> stocks = new ArrayList<Stock>();
	String name;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String dateCreated;
	
	//? ArrayList<String> stockNames = new ArrayList<String>();
	// https://stackoverflow.com/questions/447898/what-is-object-serialization
	// https://gist.github.com/Sietesoles/dae3a4959f36df3632a9
	// man sko konn lag en list<Object>me portfolier som man serializerar
	// .ser
	
	public Portfolio(String name) {
		this.name = name;
		Calendar date = Calendar.getInstance();
		dateCreated = sdf.format(date.getTime());
		JMenuItem portfolio = new JMenuItem(name);
		portfolio.addActionListener(this);
		view.Window.loadPortfolioItem.add(portfolio);
		view.Window.loadPortfolioItem.remove(view.Window.noPortfolios);
		save();
	}
	public String getName() {
		return name;
	}
	public void save() {
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
	
	public void load (String file) {
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
	public void actionPerformed(ActionEvent e) {
		String portfolioName = e.getActionCommand();
		load(portfolioName + ".ser");
		LoadPortfolio loadWindow = new LoadPortfolio(this);
	}
	
}
