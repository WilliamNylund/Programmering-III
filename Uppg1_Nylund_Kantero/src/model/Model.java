package model;

import java.util.ArrayList;

public class Model {
	public static String openStr = "===Showing data for 1. open===\n";
	public static String highStr = "===Showing data for 2. high===\n";
	public static String lowStr = "===Showing data for 3. low===\n";
	public static String closeStr = "===Showing data for 4. close===\n";
	public static String volumeStr = "===Showing data for 5. volume===\n";
	public static String adjustedCloseStr = "===Showing data for 6. adjusted close===\n";
	public static String dividendAmountStr = "===Showing data for 7. dividend amount===\n";
	public static String splitCoefficientStr = "===Showing data for 8. split coefficient===\n";
	
	public static ArrayList openGraph = new ArrayList<Double>();
	public static ArrayList highGraph = new ArrayList<Double>();
	public static ArrayList lowGraph = new ArrayList<Double>();
	public static ArrayList closeGraph = new ArrayList<Double>();
	public static ArrayList volumeGraph = new ArrayList<Double>();
	public static ArrayList xlist = new ArrayList<Double>(); //Denna lista består av 0, 1, 2...=> open.size() för att få gjort grafen med koordinater
	
	public static String[] symbolChoices = new String[16];
	public static String[] timeSeriesChoices = new String[7];
	public static String[] timeIntervalChoices = new String[5];
	public static String[] outPutSizeChoices = new String[2];
	public static String apiKey = null;
	
}
