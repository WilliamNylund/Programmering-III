package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Portfolio implements Serializable {
	
	ArrayList<Stock> stocks = new ArrayList<Stock>();
	String name;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String dateCreated;
	
	//? ArrayList<String> stockNames = new ArrayList<String>();
	// https://stackoverflow.com/questions/447898/what-is-object-serialization
	// https://gist.github.com/Sietesoles/dae3a4959f36df3632a9
	// man sko konn lag en list<Object>me portfolier som man serializerar
	
	public Portfolio(String name) {
		this.name = name;
		Calendar date = Calendar.getInstance();
		dateCreated = sdf.format(date.getTime());
		
	}
	public String getName() {
		return name;
	}
	public void save() {
		
	}
	
}
