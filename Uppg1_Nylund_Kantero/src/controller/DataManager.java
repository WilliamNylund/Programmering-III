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
		
	public static void getData(String dataSeries, String ts, String sy, String ti, String os, String ak, String sd, String ed) throws IOException {
		
		
		String url = "https://www.alphavantage.co/query?function=" + ts + "&symbol=" + sy + "&interval="+ ti +"&outputsize="+ os +"&apikey=" + ak;
		String date = null;
		String ds = null;
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
				model.Model.date.add(key);
				model.Model.open.add(value);
			}
		}
		
		for(Map.Entry<String,String> entry : highMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			int a = key.compareTo(sd);
			int b = key.compareTo(ed);
			if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
				model.Model.high.add(value);
				
			}
		}
		
		for(Map.Entry<String,String> entry : lowMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			int a = key.compareTo(sd);
			int b = key.compareTo(ed);
			if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
				model.Model.low.add(value);
			}
		}
		
		for(Map.Entry<String,String> entry : closeMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			int a = key.compareTo(sd);
			int b = key.compareTo(ed);
			if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
				model.Model.close.add(value);
			}
		}
		for(Map.Entry<String,String> entry : volumeMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			int a = key.compareTo(sd);
			int b = key.compareTo(ed);
			if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
				model.Model.volume.add(value);
			}
		}
		if (ts.equals("TIME_SERIES_DAILY_ADJUSTED") || ts.equals("TIME_SERIES_WEEKLY_ADJUSTED") || ts.equals("TIME_SERIES_MONTHLY_ADJUSTED")) {
			for(Map.Entry<String,String> entry : adjustedCloseMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				int a = key.compareTo(sd);
				int b = key.compareTo(ed);
				if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
					model.Model.adjustedClose.add(value);

				}
			}
			for(Map.Entry<String,String> entry : dividendAmountMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				int a = key.compareTo(sd);
				int b = key.compareTo(ed);
				if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
					model.Model.dividendAmount.add(value);

				}
			}
			if (ts.equals("TIME_SERIES_DAILY_ADJUSTED")) {
				for(Map.Entry<String,String> entry : splitCoefficientMap.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					int a = key.compareTo(sd);
					int b = key.compareTo(ed);
					if ( a >= 0 && b <= 0 || sd.equals(ed)) { //om datumet är mellan start date och end date
						model.Model.splitCoefficient.add(value);

					} 
				}
			}
			
		}
		

		reverseLists(); //svänger om ordningen på listan med värden till graferna för att gå från äldst => nyast
	
		
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
	public static void reset() { //resettar allt
		model.Model.openStr = "===Showing data for 1. open===\n";
		model.Model.highStr = "===Showing data for 2. high===\n";
		model.Model.lowStr = "===Showing data for 3. low===\n";
		model.Model.closeStr = "===Showing data for 4. close===\n";
		model.Model.volumeStr = "===Showing data for 5. volume===\n";
		model.Model.adjustedCloseStr = "===Showing data for 6. adjusted close===\n";
		model.Model.dividendAmountStr = "===Showing data for 7. dividend amount===\n";
		model.Model.splitCoefficientStr = "===Showing data for 8. split coefficient===\n";
		model.Model.openGraph.clear();
		model.Model.openGraph2.clear();
		model.Model.highGraph.clear();
		model.Model.highGraph2.clear();
		model.Model.lowGraph.clear();
		model.Model.lowGraph2.clear();
		model.Model.closeGraph.clear();
		model.Model.closeGraph2.clear();
		model.Model.volumeGraph.clear();
		model.Model.volumeGraph2.clear();
		model.Model.adjustedCloseGraph.clear();
		model.Model.adjustedCloseGraph2.clear();
		model.Model.dividendAmountGraph.clear();
		model.Model.dividendAmountGraph2.clear();
		model.Model.splitCoefficientGraph.clear();
		model.Model.splitCoefficientGraph2.clear();
		model.Model.xlist.clear();
		model.Model.date.clear();
		model.Model.open.clear(); 
		
	}
	private static void reverseLists() { //svänger om grafens värden för att visa äldst -> minst
		Collections.reverse(model.Model.openGraph);
		Collections.reverse(model.Model.openGraph2);
		Collections.reverse(model.Model.highGraph);
		Collections.reverse(model.Model.highGraph2);
		Collections.reverse(model.Model.lowGraph);
		Collections.reverse(model.Model.lowGraph2);
		Collections.reverse(model.Model.closeGraph);
		Collections.reverse(model.Model.closeGraph2);
		Collections.reverse(model.Model.volumeGraph);
		Collections.reverse(model.Model.volumeGraph2);
		Collections.reverse(model.Model.adjustedCloseGraph);
		Collections.reverse(model.Model.adjustedCloseGraph2);
		Collections.reverse(model.Model.dividendAmountGraph);
		Collections.reverse(model.Model.dividendAmountGraph2);
		Collections.reverse(model.Model.splitCoefficientGraph);
		Collections.reverse(model.Model.splitCoefficientGraph2);

	}
	public static void getChoices() throws FileNotFoundException { //hämtar dropdownboxarnas innehåll
		
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
	
	public static String calculatePear() {  //räknar ut Pearson Correlation Coefficient
		//formeln tagen från https://study.com/academy/lesson/pearson-correlation-coefficient-formula-example-significance.html
		//pearsons correlation coefficient är beräknad enligt close values.
		
		double symbolSum1 = 0; //summan av alla symbol1 värden
		double symbolSum2 = 0 ; //summan av alla symbol2 värden
		double symbolSum12 = 0; //summan av alla symbolSum1*symbolSum2
		double symbolSum11 = 0; //summan av alla symbolSum1*symbolSum1
		double symbolSum22 = 0; //summan av alla symbolSum2*symbolSum2
		int n = model.Model.closeGraph.size(); 
		for (int i = 0; i < n; i++) {
			symbolSum1 += (double) model.Model.closeGraph.get(i);
			symbolSum2 += (double) model.Model.closeGraph2.get(i);
			symbolSum12 += ((double) model.Model.closeGraph.get(i)) * ((double) model.Model.closeGraph2.get(i));
			symbolSum11 += ((double) model.Model.closeGraph.get(i)) * ((double) model.Model.closeGraph.get(i));
			symbolSum22 += ((double) model.Model.closeGraph2.get(i)) * ((double) model.Model.closeGraph2.get(i));
		}
		
		double numerator = (n*symbolSum12) - (symbolSum1*symbolSum2);
		double denominator = Math.sqrt(((n-1)*symbolSum11)*((n-1)*symbolSum22));
		double pear = numerator / denominator;
		return Double.toString(pear);
	}
	
	
	//Don't go deeper
	
	public static String makeStrings(String dataSeries, String symbol1, String symbol2) {
		//skapar strängarna om man har valt 2 aktier
		ArrayList openList1 = new ArrayList<String>();
		ArrayList openList2 = new ArrayList<String>();
		ArrayList highList1 = new ArrayList<String>();
		ArrayList highList2 = new ArrayList<String>();
		ArrayList lowList1 = new ArrayList<String>();
		ArrayList lowList2 = new ArrayList<String>();
		ArrayList closeList1 = new ArrayList<String>();
		ArrayList closeList2 = new ArrayList<String>();
		ArrayList volumeList1 = new ArrayList<String>();
		ArrayList volumeList2 = new ArrayList<String>();
		ArrayList adjustedCloseList1 = new ArrayList<String>();
		ArrayList adjustedCloseList2 = new ArrayList<String>();
		ArrayList dividendAmountList1 = new ArrayList<String>();
		ArrayList dividendAmountList2 = new ArrayList<String>();
		ArrayList splitCoefficientList1 = new ArrayList<String>();
		ArrayList splitCoefficientList2 = new ArrayList<String>();
		
		for (int i = 0; i < model.Model.open.size(); i++) {
			if (i < (model.Model.open.size()+1)/2 ) {
				openList1.add(model.Model.open.get(i));
				model.Model.openGraph.add(Double.valueOf((String) model.Model.open.get(i)));
				
				highList1.add(model.Model.high.get(i));
				model.Model.highGraph.add(Double.valueOf((String) model.Model.high.get(i)));
				
				lowList1.add(model.Model.low.get(i));
				model.Model.lowGraph.add(Double.valueOf((String) model.Model.low.get(i)));
				
				closeList1.add(model.Model.close.get(i));
				model.Model.closeGraph.add(Double.valueOf((String) model.Model.close.get(i)));
				
				volumeList1.add(model.Model.volume.get(i));
				model.Model.volumeGraph.add(Double.valueOf((String) model.Model.volume.get(i)));
				
				try {
				adjustedCloseList1.add(model.Model.adjustedClose.get(i));
				model.Model.adjustedCloseGraph.add(Double.valueOf((String) model.Model.adjustedClose.get(i)));
				
				dividendAmountList1.add(model.Model.dividendAmount.get(i));
				model.Model.dividendAmountGraph.add(Double.valueOf((String) model.Model.dividendAmount.get(i)));
				
				splitCoefficientList1.add(model.Model.splitCoefficient.get(i));
				model.Model.splitCoefficientGraph.add(Double.valueOf((String) model.Model.splitCoefficient.get(i)));
				} catch (IndexOutOfBoundsException e1) {}
			
			} else {
				openList2.add(model.Model.open.get(i));
				model.Model.openGraph2.add(Double.valueOf((String) model.Model.open.get(i)));
				
				highList2.add(model.Model.high.get(i));
				model.Model.highGraph2.add(Double.valueOf((String) model.Model.high.get(i)));
				
				lowList2.add(model.Model.low.get(i));
				model.Model.lowGraph2.add(Double.valueOf((String) model.Model.low.get(i)));
				
				closeList2.add(model.Model.close.get(i));
				model.Model.closeGraph2.add(Double.valueOf((String) model.Model.close.get(i)));
				
				volumeList2.add(model.Model.volume.get(i));
				model.Model.volumeGraph2.add(Double.valueOf((String) model.Model.volume.get(i)));
				
				try {
				adjustedCloseList2.add(model.Model.adjustedClose.get(i));
				model.Model.adjustedCloseGraph2.add(Double.valueOf((String) model.Model.adjustedClose.get(i)));
				
				dividendAmountList2.add(model.Model.dividendAmount.get(i));
				model.Model.dividendAmountGraph2.add(Double.valueOf((String) model.Model.dividendAmount.get(i)));
				
				splitCoefficientList2.add(model.Model.splitCoefficient.get(i));
				model.Model.splitCoefficientGraph2.add(Double.valueOf((String) model.Model.splitCoefficient.get(i)));
				} catch (IndexOutOfBoundsException e1) {}
			}
		}

		for (int i = 0; i < openList1.size(); i++) { 
			model.Model.openStr += model.Model.date.get(i) + ": " + symbol1 + " " + openList1.get(i) + "      " + symbol2 + " "+ openList2.get(i) + "\n";
			model.Model.highStr += model.Model.date.get(i) + ": " + symbol1 + " " + highList1.get(i) + "      " + symbol2 + " "+ highList2.get(i) + "\n";
			model.Model.lowStr += model.Model.date.get(i) + ": " + symbol1 + " " + lowList1.get(i) + "      " + symbol2 + " "+ lowList2.get(i) + "\n";
			model.Model.closeStr += model.Model.date.get(i) + ": " + symbol1 + " " + closeList1.get(i) + "      " + symbol2 + " "+ closeList2.get(i) + "\n";
			model.Model.volumeStr += model.Model.date.get(i) + ": " + symbol1 + " " + volumeList1.get(i) + "      " + symbol2 + " "+ volumeList2.get(i) + "\n";
			try {
			model.Model.adjustedCloseStr += model.Model.date.get(i) + ": " + symbol1 + " " + adjustedCloseList1.get(i) + "      " + symbol2 + " "+ adjustedCloseList2.get(i) + "\n";
			model.Model.dividendAmountStr += model.Model.date.get(i) + ": " + symbol1 + " " + dividendAmountList1.get(i) + "      " + symbol2 + " "+ dividendAmountList2.get(i) + "\n";
			model.Model.splitCoefficientStr += model.Model.date.get(i) + ": " + symbol1 + " " + splitCoefficientList1.get(i) + "      " + symbol2 + " "+ splitCoefficientList2.get(i) + "\n";
			} catch (IndexOutOfBoundsException e1) {}
		}
		
		for(int i = 0; i < Model.openGraph.size(); i++) {
			model.Model.xlist.add(i);
		}
		
		if(dataSeries.equals("open")) return model.Model.openStr;
		else if(dataSeries.equals("high")) return model.Model.highStr;
		else if(dataSeries.equals("low")) return model.Model.lowStr;
		else if(dataSeries.equals("close")) return model.Model.closeStr;
		else if(dataSeries.equals("split coefficient")) return model.Model.splitCoefficientStr;
		else if(dataSeries.equals("dividend amount")) return model.Model.dividendAmountStr;
		else if(dataSeries.equals("adjusted close")) return model.Model.adjustedCloseStr;
		else return model.Model.volumeStr;
	}
	
	public static String makeString(String dataSeries) {
		//skapar strängarna om man har valt en aktie
		for (int i = 0; i < model.Model.open.size(); i++) {
			model.Model.openStr += "Date: " + model.Model.date.get(i) + ": " + model.Model.open.get(i) + "\n";
			model.Model.openGraph.add(Double.valueOf((String) model.Model.open.get(i)));
			
			model.Model.highStr += "Date: " + model.Model.date.get(i) + ": " + model.Model.high.get(i) + "\n";
			model.Model.highGraph.add(Double.valueOf((String) model.Model.high.get(i)));
			
			model.Model.lowStr += "Date: " + model.Model.date.get(i) + ": " + model.Model.low.get(i) + "\n";
			model.Model.lowGraph.add(Double.valueOf((String) model.Model.low.get(i)));
			
			model.Model.closeStr += "Date: " + model.Model.date.get(i) + ": " + model.Model.close.get(i) + "\n";
			model.Model.closeGraph.add(Double.valueOf((String) model.Model.close.get(i)));
			
			try {
			model.Model.splitCoefficientStr += "Date: " + model.Model.date.get(i) + ": " + model.Model.splitCoefficient.get(i) + "\n";
			model.Model.splitCoefficientGraph.add(Double.valueOf((String) model.Model.splitCoefficient.get(i)));
			
			model.Model.dividendAmountStr += "Date: " + model.Model.date.get(i) + ": " + model.Model.dividendAmount.get(i) + "\n";
			model.Model.dividendAmountGraph.add(Double.valueOf((String) model.Model.dividendAmount.get(i)));
			
			model.Model.adjustedCloseStr += "Date: " + model.Model.date.get(i) + ": " + model.Model.adjustedClose.get(i) + "\n";
			model.Model.adjustedCloseGraph.add(Double.valueOf((String) model.Model.adjustedClose.get(i)));
			} catch (IndexOutOfBoundsException e1) {}
		}
		
		
		for(int i = 0; i < Model.openGraph.size(); i++) {
			model.Model.xlist.add(i);
		}
		
		if(dataSeries.equals("open")) return model.Model.openStr;
		else if(dataSeries.equals("high")) return model.Model.highStr;
		else if(dataSeries.equals("low")) return model.Model.lowStr;
		else if(dataSeries.equals("close")) return model.Model.closeStr;
		else if(dataSeries.equals("split coefficient")) return model.Model.splitCoefficientStr;
		else if(dataSeries.equals("dividend amount")) return model.Model.dividendAmountStr;
		else if(dataSeries.equals("adjusted close")) return model.Model.adjustedCloseStr;
		else return model.Model.volumeStr;
	}

	
	
}
