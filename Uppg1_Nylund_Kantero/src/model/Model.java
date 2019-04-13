package model;

import java.util.ArrayList;

public class Model {
	
	public static String openStr = "";
	public static String highStr = "===Showing data for 2. high===\n";
	public static String lowStr = "===Showing data for 3. low===\n";
	public static String closeStr = "===Showing data for 4. close===\n";
	public static String volumeStr = "===Showing data for 5. volume===\n";
	public static String adjustedCloseStr = "===Showing data for 6. adjusted close===\n";
	public static String dividendAmountStr = "===Showing data for 7. dividend amount===\n";
	public static String splitCoefficientStr = "===Showing data for 8. split coefficient===\n";
	
	
	
	public static ArrayList openGraph = new ArrayList<Double>();
	public static ArrayList openGraph2 = new ArrayList<Double>();
	public static ArrayList highGraph = new ArrayList<Double>();
	public static ArrayList highGraph2 = new ArrayList<Double>();
	public static ArrayList lowGraph = new ArrayList<Double>();
	public static ArrayList lowGraph2 = new ArrayList<Double>();
	public static ArrayList closeGraph = new ArrayList<Double>();
	public static ArrayList closeGraph2 = new ArrayList<Double>();
	public static ArrayList volumeGraph = new ArrayList<Double>();
	public static ArrayList volumeGraph2 = new ArrayList<Double>();
	public static ArrayList adjustedCloseGraph = new ArrayList<Double>();
	public static ArrayList adjustedCloseGraph2 = new ArrayList<Double>();
	public static ArrayList dividendAmountGraph = new ArrayList<Double>();
	public static ArrayList dividendAmountGraph2 = new ArrayList<Double>();
	public static ArrayList splitCoefficientGraph = new ArrayList<Double>();
	public static ArrayList splitCoefficientGraph2 = new ArrayList<Double>();
	
	public static ArrayList xlist = new ArrayList<Double>(); //Denna lista består av 0, 1, 2...=> open.size() för att få gjort grafen med koordinater
	
	public static String[] symbolChoices = new String[16];
	public static String[] timeSeriesChoices = new String[7];
	public static String[] timeIntervalChoices = new String[5];
	public static String[] outPutSizeChoices = new String[2];
	public static String apiKey = null;
	
	public static ArrayList open = new ArrayList<String>();
	public static ArrayList high = new ArrayList<String>();
	public static ArrayList low = new ArrayList<String>();
	public static ArrayList close = new ArrayList<String>();
	public static ArrayList volume = new ArrayList<String>();
	public static ArrayList adjustedClose = new ArrayList<String>();
	public static ArrayList dividendAmount = new ArrayList<String>();
	public static ArrayList splitCoefficient = new ArrayList<String>();

	public static ArrayList date = new ArrayList<String>();
	
}
