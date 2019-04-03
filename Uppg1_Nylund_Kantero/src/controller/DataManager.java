package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import JSON.JSONObject;
import model.Model;

public class DataManager {
	
	static boolean flag = true;
	
	public static String getData(String dataSeries, String ts, String sy, String ti, String os, String ak, String sd, String ed) throws IOException {
		
		
		String url = "https://www.alphavantage.co/query?function=" + ts + "&symbol=" + sy + "&interval="+ ti +"&outputsize="+ os +"&apikey=" + ak;
		String date = null;
		String ds = null;
		System.out.println(url);
		reset();
		ArrayList open = new ArrayList();
		ArrayList high = new ArrayList();
		ArrayList low = new ArrayList();
		ArrayList close = new ArrayList();
		ArrayList volume = new ArrayList();
		ArrayList adjustedClose = new ArrayList();
		ArrayList dividendAmount = new ArrayList();
		ArrayList splitCoefficient = new ArrayList();
		ArrayList allDates = new ArrayList();
		
		
		JSONObject json = JsonReader.readJsonFromUrl(url);
		String timeSeries = getTimeSeries(ts, ti);
		JSONObject tsObject =  (JSONObject) (json.get(timeSeries)); 
		
		Iterator<String> keys = tsObject.keys();
		while(keys.hasNext()) { //loopar genom Time Series
			date = keys.next();
			allDates.add(date);
			JSONObject dateObject = (JSONObject) tsObject.get(date);
			Iterator<String> dateIterator = dateObject.keys();
			while(dateIterator.hasNext()) { //loopar genom dataSeries
				ds = dateIterator.next();
				if (ds.equals("1. open")) {
					open.add(dateObject.get(ds));
				} else if (ds.equals("2. high")) {
					high.add(dateObject.get(ds));
				} else if (ds.equals("3. low")) {
					low.add(dateObject.get(ds));
				} else if (ds.equals("4. close")) {
					close.add(dateObject.get(ds));
				}
				else if (ds.equals("5. volume")) {
					volume.add(dateObject.get(ds));
				}
				if (ts.equals("TIME_SERIES_DAILY_ADJUSTED") || ts.equals("TIME_SERIES_WEEKLY_ADJUSTED") || ts.equals("TIME_SERIES_MONTHLY_ADJUSTED")) {
					if (ds.equals("5. adjusted close")) {
						adjustedClose.add(dateObject.get(ds));
					} else if (ds.equals("6. volume")) {
						volume.add(dateObject.get(ds));
					} else if (ds.equals("7. dividend amount")) {
						dividendAmount.add(dateObject.get(ds));
					}
					if (ts.equals("TIME_SERIES_DAILY_ADJUSTED")) {
						if (ds.equals("8. split coefficient")) {
							splitCoefficient.add(dateObject.get(ds));
						}
					}
				}
			}
		}
		
		TreeMap<String, String> openMap = new TreeMap<>(Comparator.reverseOrder());
		TreeMap<String, String> highMap = new TreeMap<>(Comparator.reverseOrder());
		TreeMap<String, String> lowMap = new TreeMap<>(Comparator.reverseOrder());
		TreeMap<String, String> closeMap = new TreeMap<>(Comparator.reverseOrder());
		TreeMap<String, String> volumeMap = new TreeMap<>(Comparator.reverseOrder());
		TreeMap<String, String> adjustedCloseMap = new TreeMap<>(Comparator.reverseOrder());
		TreeMap<String, String> dividendAmountMap = new TreeMap<>(Comparator.reverseOrder());
		TreeMap<String, String> splitCoefficientMap = new TreeMap<>(Comparator.reverseOrder());

		
		
		
		
		if (ts.equals("TIME_SERIES_DAILY_ADJUSTED") || ts.equals("TIME_SERIES_WEEKLY_ADJUSTED") || ts.equals("TIME_SERIES_MONTHLY_ADJUSTED")) {
			for (int i = 0; i < allDates.size(); i++) {
				openMap.put(allDates.get(i).toString(), open.get(i).toString());
				highMap.put(allDates.get(i).toString(), high.get(i).toString());
				lowMap.put(allDates.get(i).toString(), low.get(i).toString());
				closeMap.put(allDates.get(i).toString(), close.get(i).toString());
				volumeMap.put(allDates.get(i).toString(), volume.get(i).toString());
				adjustedCloseMap.put(allDates.get(i).toString(), adjustedClose.get(i).toString());
				dividendAmountMap.put(allDates.get(i).toString(), dividendAmount.get(i).toString());
				if (ts.equals("TIME_SERIES_DAILY_ADJUSTED")) splitCoefficientMap.put(allDates.get(i).toString(), splitCoefficient.get(i).toString());
			}
			
		} else{
			for (int i = 0; i < allDates.size(); i++) {
		
			openMap.put(allDates.get(i).toString(), open.get(i).toString());
			highMap.put(allDates.get(i).toString(), high.get(i).toString());
			lowMap.put(allDates.get(i).toString(), low.get(i).toString());
			closeMap.put(allDates.get(i).toString(), close.get(i).toString());
			volumeMap.put(allDates.get(i).toString(), volume.get(i).toString());
			}
		}
		
		for(Map.Entry<String,String> entry : openMap.entrySet()) {
			
			String key = entry.getKey();
			String value = entry.getValue();
			int a = key.compareTo(sd);
			int b = key.compareTo(ed);
			if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
				//model.Model.openStr += "Date: " + key + ": " + value + "\n";
				model.Model.date.add(key);
				model.Model.open.add(value);
				model.Model.openGraph.add(Double.valueOf(value));
			}
		}
		
		for(Map.Entry<String,String> entry : highMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			int a = key.compareTo(sd);
			int b = key.compareTo(ed);
			if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
				model.Model.highStr += "Date: " + key + ": " + value + "\n";
				model.Model.highGraph.add(Double.valueOf(value));
			}
		}
		
		for(Map.Entry<String,String> entry : lowMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			int a = key.compareTo(sd);
			int b = key.compareTo(ed);
			if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
				model.Model.lowStr += "Date: " + key + ": " + value + "\n";
				model.Model.lowGraph.add(Double.valueOf(value));
			}
		}
		
		for(Map.Entry<String,String> entry : closeMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			int a = key.compareTo(sd);
			int b = key.compareTo(ed);
			if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
				model.Model.closeStr += "Date: " + key + ": " + value + "\n";
				model.Model.closeGraph.add(Double.valueOf(value));
			}
		}
		for(Map.Entry<String,String> entry : volumeMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			int a = key.compareTo(sd);
			int b = key.compareTo(ed);
			if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
				model.Model.volumeStr += "Date: " + key + ": " + value + "\n";
				model.Model.volumeGraph.add(Double.valueOf(value));
			}
		}
		if (ts.equals("TIME_SERIES_DAILY_ADJUSTED") || ts.equals("TIME_SERIES_WEEKLY_ADJUSTED") || ts.equals("TIME_SERIES_MONTHLY_ADJUSTED")) {
			for(Map.Entry<String,String> entry : adjustedCloseMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				int a = key.compareTo(sd);
				int b = key.compareTo(ed);
				if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
					model.Model.adjustedCloseStr += "Date: " + key + ": " + value + "\n";
					model.Model.adjustedCloseGraph.add(Double.valueOf(value));

				}
			}
			for(Map.Entry<String,String> entry : dividendAmountMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				int a = key.compareTo(sd);
				int b = key.compareTo(ed);
				if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
					model.Model.dividendAmountStr += "Date: " + key + ": " + value + "\n";
					model.Model.dividendAmountGraph.add(Double.valueOf(value));

				}
			}
			if (ts.equals("TIME_SERIES_DAILY_ADJUSTED")) {
				for(Map.Entry<String,String> entry : splitCoefficientMap.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					int a = key.compareTo(sd);
					int b = key.compareTo(ed);
					if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
						model.Model.splitCoefficientStr += "Date: " + key + ": " + value + "\n";
						model.Model.splitCoefficientGraph.add(Double.valueOf(value));

					} 
				}
			}
			
		}
		
		for(int i = 0; i < Model.openGraph.size(); i++) {
			model.Model.xlist.add(i);
		}

		reverseLists(); //svänger om ordningen på listan med värden till graferna för att gå från äldst => nyast
	

		makeString();

		if(dataSeries.equals("open")) return model.Model.openStr;
		else if(dataSeries.equals("high")) return model.Model.highStr;
		else if(dataSeries.equals("low")) return model.Model.lowStr;
		else if(dataSeries.equals("close")) return model.Model.closeStr;
		else if(dataSeries.equals("split coefficient")) return model.Model.splitCoefficientStr;
		else if(dataSeries.equals("dividend amount")) return model.Model.dividendAmountStr;
		else if(dataSeries.equals("adjusted close")) return model.Model.adjustedCloseStr;
		else return model.Model.volumeStr;
		
	}
	
	static String getTimeSeries(String ts, String ti) { //hämtar rätt arraynamn
		String TimeSeries = null;
		
		if (ts.equals("TIME_SERIES_INTRADAY")) TimeSeries =  "Time Series (" + ti + ")";
		else if (ts.equals("TIME_SERIES_DAILY") || ts.equals("TIME_SERIES_DAILY_ADJUSTED")) TimeSeries = "Time Series (Daily)";
		else if (ts.equals("TIME_SERIES_WEEKLY")) TimeSeries = "Weekly Time Series";
		else if (ts.equals("TIME_SERIES_WEEKLY_ADJUSTED")) TimeSeries = "Weekly Adjusted Time Series";
		else if (ts.equals("TIME_SERIES_MONTHLY")) TimeSeries = "Monthly Time Series";
		else if (ts.equals("TIME_SERIES_MONTHLY_ADJUSTED")) TimeSeries = "Monthly Adjusted Time Series";
		
		return TimeSeries;
		
	}
	private static void reset() {
		model.Model.openStr = "";
		model.Model.highStr = "===Showing data for 2. high===\n";
		model.Model.lowStr = "===Showing data for 3. low===\n";
		model.Model.closeStr = "===Showing data for 4. close===\n";
		model.Model.volumeStr = "===Showing data for 5. volume===\n";
		model.Model.adjustedCloseStr = "===Showing data for 6. adjusted close===\n";
		model.Model.dividendAmountStr = "===Showing data for 7. dividend amount===\n";
		model.Model.splitCoefficientStr = "===Showing data for 8. split coefficient===\n";
		model.Model.openGraph.clear();
		model.Model.highGraph.clear();
		model.Model.lowGraph.clear();
		model.Model.closeGraph.clear();
		model.Model.volumeGraph.clear();
		model.Model.adjustedCloseGraph.clear();
		model.Model.dividendAmountGraph.clear();
		model.Model.splitCoefficientGraph.clear();
		model.Model.xlist.clear();
	}
	private static void reverseLists() {
		Collections.reverse(model.Model.openGraph);
		Collections.reverse(model.Model.highGraph);
		Collections.reverse(model.Model.lowGraph);
		Collections.reverse(model.Model.closeGraph);
		Collections.reverse(model.Model.volumeGraph);
	}
	public static void getChoices() throws FileNotFoundException {
		
		Scanner scanner = new Scanner(new FileReader("./iniFiles/StockAnalyzer.ini"));
		String apiKey = scanner.nextLine().split("=")[1];
		String timeSeries = scanner.nextLine().split("=")[1];
		String symbol = scanner.nextLine().split("=")[1];
		String timeInterval = scanner.nextLine().split("=")[1];
		String outPutSize = scanner.nextLine().split("=")[1];
		
		String[] timeSeriesParts = timeSeries.split(", ");
		String[] symbolParts = symbol.split(", ");
		String[] timeIntervalParts = timeInterval.split(", ");
		String[] outPutSizeParts = outPutSize.split(", ");

		Model.apiKey = apiKey;
		for (int i = 0; i < timeSeriesParts.length; i++) {
			Model.timeSeriesChoices[i] = timeSeriesParts[i];
		}
		Model.symbolChoices[0] = "null";
		for (int i = 1; i < symbolParts.length+1; i++) {
			Model.symbolChoices[i] = symbolParts[i-1];
		}

		for (int i = 0; i < timeIntervalParts.length; i++) {
			Model.timeIntervalChoices[i] = timeIntervalParts[i];
		}
		Model.outPutSizeChoices[0] = outPutSizeParts[0];
		Model.outPutSizeChoices[1] = outPutSizeParts[1];
		
	}
	
	public static boolean isValidDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        if (date.equals("")) return true;
        try {
            dateFormat.parse(date.trim());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
	
	public static void makeString() {
		if (flag) {
			flag = false;
			return;
		}
		model.Model.openStr = "===Showing data for 1. open===\n";
		ArrayList list1 = new ArrayList<String>();
		ArrayList list2 = new ArrayList<String>();

		for (int i = 0; i < model.Model.open.size(); i++) {
			if (i < (model.Model.open.size()+1) / 2) {
				list1.add(model.Model.open.get(i));
			} else list2.add(model.Model.open.get(i));
		}
		for (int i = 0; i < list1.size(); i++) {
			model.Model.openStr += model.Model.date.get(i) + ": " + list1.get(i) + "      " + list2.get(i) + "\n";
		}
	}
	
/*	public static void makeTreeMaps() {

		for(Map.Entry<String,String> entry : openMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			int a = key.compareTo(sd);
			int b = key.compareTo(ed);
			if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
				model.Model.openStr += "Date: " + key + ": " + value + "\n";
				model.Model.openGraph.add(Double.valueOf(value));
			}
		}
		
		for(Map.Entry<String,String> entry : highMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			int a = key.compareTo(sd);
			int b = key.compareTo(ed);
			if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
				model.Model.highStr += "Date: " + key + ": " + value + "\n";
				model.Model.highGraph.add(Double.valueOf(value));
			}
		}
		
		for(Map.Entry<String,String> entry : lowMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			int a = key.compareTo(sd);
			int b = key.compareTo(ed);
			if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
				model.Model.lowStr += "Date: " + key + ": " + value + "\n";
				model.Model.lowGraph.add(Double.valueOf(value));
			}
		}
		for(Map.Entry<String,String> entry : closeMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			int a = key.compareTo(sd);
			int b = key.compareTo(ed);
			if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
				model.Model.closeStr += "Date: " + key + ": " + value + "\n";
				model.Model.closeGraph.add(Double.valueOf(value));
			}
		}
		for(Map.Entry<String,String> entry : volumeMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			int a = key.compareTo(sd);
			int b = key.compareTo(ed);
			if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
				model.Model.volumeStr += "Date: " + key + ": " + value + "\n";
				model.Model.volumeGraph.add(Double.valueOf(value));
			}
		}
		if (ts.equals("TIME_SERIES_DAILY_ADJUSTED") || ts.equals("TIME_SERIES_WEEKLY_ADJUSTED") || ts.equals("TIME_SERIES_MONTHLY_ADJUSTED")) {
			for(Map.Entry<String,String> entry : adjustedCloseMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				int a = key.compareTo(sd);
				int b = key.compareTo(ed);
				if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
					model.Model.adjustedCloseStr += "Date: " + key + ": " + value + "\n";
					model.Model.adjustedCloseGraph.add(Double.valueOf(value));

				}
			}
			for(Map.Entry<String,String> entry : dividendAmountMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				int a = key.compareTo(sd);
				int b = key.compareTo(ed);
				if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
					model.Model.dividendAmountStr += "Date: " + key + ": " + value + "\n";
					model.Model.dividendAmountGraph.add(Double.valueOf(value));

				}
			}
			if (ts.equals("TIME_SERIES_DAILY_ADJUSTED")) {
				for(Map.Entry<String,String> entry : splitCoefficientMap.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					int a = key.compareTo(sd);
					int b = key.compareTo(ed);
					if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
						model.Model.splitCoefficientStr += "Date: " + key + ": " + value + "\n";
						model.Model.splitCoefficientGraph.add(Double.valueOf(value));

					} 
				}
				
				for ( int i = 0; i <lista; i++
				string += "keys[i] + values[i] + values2[i]
				}
			}*/
	//}
	
}
