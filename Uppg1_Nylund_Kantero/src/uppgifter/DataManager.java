package uppgifter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


import JSON.JSONObject;

public class DataManager {
	static String openStr = "===Showing data for 1. open===\n";
	static String highStr = "===Showing data for 2. high===\n";
	static String lowStr = "===Showing data for 3. low===\n";
	static String closeStr = "===Showing data for 4. close===\n";
	static String volumeStr = "===Showing data for 5. volume===\n";
	static String adjustedCloseStr = "===Showing data for 6. adjusted close===\n";
	static String dividendAmountStr = "===Showing data for 7. dividend amount===\n";
	static String splitCoefficientStr = "===Showing data for 8. split coefficient===\n";
	
	static ArrayList openGraph = new ArrayList<Double>();
	static ArrayList highGraph = new ArrayList<Double>();
	static ArrayList lowGraph = new ArrayList<Double>();
	static ArrayList closeGraph = new ArrayList<Double>();
	static ArrayList volumeGraph = new ArrayList<Double>();
	static ArrayList xlist = new ArrayList<Double>(); //Denna lista består av 0, 1, 2...=> open.size() för att få gjort grafen med koordinater

	
	static String getData(String dataSeries, String ts, String sy, String ti, String os) throws IOException {
		String url = "https://www.alphavantage.co/query?function=" + ts + "&symbol=" + sy + "&interval="+ ti +"&outputsize="+ os +"&apikey=INA6YDFMZ7TW5X4I";
		String date = null;
		String ds = null;
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
		String TimeSeries = getTimeSeries(ts,ti);
		JSONObject tsObject =  (JSONObject) (json.get(TimeSeries));
		
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
				} else if (ds.equals("5. volume")) {
					volume.add(dateObject.get(ds));
				}
				if (ts == "TIME_SERIES_DAILY_ADJUSTED" || ts == "TIME_SERIES_WEEKLY_ADJUSTED" || ts == "TIME_SERIES_MONTHLY_ADJUSTED") {
					if (ds.equals("5. adjusted close")) {
						adjustedClose.add(dateObject.get(ds));
					} else if (ds.equals("6. volume")) {
						volume.add(dateObject.get(ds));
					} else if (ds.equals("7. dividend amount")) {
						dividendAmount.add(dateObject.get(ds));
					}
					if (ts == "TIME_SERIES_DAILY_ADJUSTED") {
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

		
		
		
		
		if (ts == "TIME_SERIES_DAILY_ADJUSTED" || ts == "TIME_SERIES_WEEKLY_ADJUSTED" || ts == "TIME_SERIES_MONTHLY_ADJUSTED") {
			for (int i = 0; i < allDates.size(); i++) {
				openMap.put(allDates.get(i).toString(), open.get(i).toString());
				highMap.put(allDates.get(i).toString(), high.get(i).toString());
				lowMap.put(allDates.get(i).toString(), low.get(i).toString());
				closeMap.put(allDates.get(i).toString(), close.get(i).toString());
				volumeMap.put(allDates.get(i).toString(), volume.get(i).toString());
				adjustedCloseMap.put(allDates.get(i).toString(), adjustedClose.get(i).toString());
				dividendAmountMap.put(allDates.get(i).toString(), dividendAmount.get(i).toString());
				if (ts == "TIME_SERIES_DAILY_ADJUSTED") splitCoefficientMap.put(allDates.get(i).toString(), splitCoefficient.get(i).toString());
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
			  openStr += "Date: " + key + ": " + value + "\n";
			  openGraph.add(Double.valueOf(value));
		}
		for(Map.Entry<String,String> entry : highMap.entrySet()) {
			  String key = entry.getKey();
			  String value = entry.getValue();
			  highStr += "Date: " + key + ": " + value + "\n";
			  highGraph.add(Double.valueOf(value));
		}
		for(Map.Entry<String,String> entry : lowMap.entrySet()) {
			  String key = entry.getKey();
			  String value = entry.getValue();
			  lowStr += "Date: " + key + ": " + value + "\n";
			  lowGraph.add(Double.valueOf(value));
		}
		for(Map.Entry<String,String> entry : closeMap.entrySet()) {
			  String key = entry.getKey();
			  String value = entry.getValue();
			  closeStr += "Date: " + key + ": " + value + "\n";
			  closeGraph.add(Double.valueOf(value));
		}
		for(Map.Entry<String,String> entry : volumeMap.entrySet()) {
			  String key = entry.getKey();
			  String value = entry.getValue();
			  volumeStr += "Date: " + key + ": " + value + "\n";
			  volumeGraph.add(Double.valueOf(value));
		}
		if (ts == "TIME_SERIES_DAILY_ADJUSTED" || ts == "TIME_SERIES_WEEKLY_ADJUSTED" || ts == "TIME_SERIES_MONTHLY_ADJUSTED") {
			for(Map.Entry<String,String> entry : adjustedCloseMap.entrySet()) {
				  String key = entry.getKey();
				  String value = entry.getValue();
				  adjustedCloseStr += "Date: " + key + ": " + value + "\n";
			}
			for(Map.Entry<String,String> entry : dividendAmountMap.entrySet()) {
				  String key = entry.getKey();
				  String value = entry.getValue();
				  dividendAmountStr += "Date: " + key + ": " + value + "\n";
			}
			if (ts == "TIME_SERIES_DAILY_ADJUSTED") {
				for(Map.Entry<String,String> entry : splitCoefficientMap.entrySet()) {
					  String key = entry.getKey();
					  String value = entry.getValue();
					  splitCoefficientStr += "Date: " + key + ": " + value + "\n";
				}
			}
			
		}
		
		for(int i = 0; i <open.size(); i++) {
			xlist.add(i);
		}
		reverseLists(); //svänger om ordningen på listan med värden till graferna för att gå från äldst => nyast
		
		
		if(dataSeries.equals("open")) return openStr;
		else if(dataSeries.equals("high")) return highStr;
		else if(dataSeries.equals("low")) return lowStr;
		else if(dataSeries.equals("close")) return closeStr;
		else if(dataSeries.equals("split coefficient")) return splitCoefficientStr;
		else if(dataSeries.equals("dividend amount")) return dividendAmountStr;
		else if(dataSeries.equals("adjusted close")) return adjustedCloseStr;
		else return volumeStr;
	}
	
	static String getTimeSeries(String ts, String ti) { //hämtar rätt arraynamn
		String TimeSeries = null;
		if (ts=="TIME_SERIES_INTRADAY") TimeSeries =  "Time Series (" + ti + ")";
		else if (ts == "TIME_SERIES_DAILY" || ts == "TIME_SERIES_DAILY_ADJUSTED") TimeSeries = "Time Series (Daily)";
		else if (ts == "TIME_SERIES_WEEKLY") TimeSeries = "Weekly Time Series";
		else if (ts == "TIME_SERIES_WEEKLY_ADJUSTED") TimeSeries = "Weekly Adjusted Time Series";
		else if (ts == "TIME_SERIES_MONTHLY") TimeSeries = "Monthly Time Series";
		else if (ts == "TIME_SERIES_MONTHLY_ADJUSTED") TimeSeries = "Monthly Adjusted Time Series";
		return TimeSeries;
		
	}
	private static void reset() {
		openStr = "===Showing data for 1. open===\n";
		highStr = "===Showing data for 2. high===\n";
		lowStr = "===Showing data for 3. low===\n";
		closeStr = "===Showing data for 4. close===\n";
		volumeStr = "===Showing data for 5. volume===\n";
		adjustedCloseStr = "===Showing data for 6. adjusted close===\n";
		dividendAmountStr = "===Showing data for 7. dividend amount===\n";
		splitCoefficientStr = "===Showing data for 8. split coefficient===\n";
		openGraph.clear();
		highGraph.clear();
		lowGraph.clear();
		closeGraph.clear();
		volumeGraph.clear();
		xlist.clear();
	}
	private static void reverseLists() {
		Collections.reverse(openGraph);
		Collections.reverse(highGraph);
		Collections.reverse(lowGraph);
		Collections.reverse(closeGraph);
		Collections.reverse(volumeGraph);
	}

}
