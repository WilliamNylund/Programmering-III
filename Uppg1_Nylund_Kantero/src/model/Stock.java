package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Stock implements Serializable {
	
	double value;
	String symbol;
	String name;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String dateBought;
	
	public Stock(String name) {
		this.name = name;
		Calendar date = Calendar.getInstance();
		dateBought = sdf.format(date.getTime());
	}
	public String getDateBought() {
		return dateBought;
	}
	public String getSymbol() {
		return symbol;
	}
	
	
}
