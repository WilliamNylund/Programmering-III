package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.DataManager;
import model.Portfolio;

public class LoadPortfolioWindow { //Fönster för att visa portfolien
	
	JFrame frame;
	JPanel panel;
	JList stocklist;
	public static JTextField totalValueField;
	public static DefaultListModel listModel;
	JButton sellButton;
	JButton buyButton;
	public static JLabel amountOfStocks;
	
	public LoadPortfolioWindow(Portfolio portfolio) {
		frame = new JFrame(portfolio.getName());
		panel = new JPanel();
		panel.setSize(600, 400);
	
		
		
		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints cons = new GridBagConstraints();
		frame.setSize(600,400);
		frame.setLocationRelativeTo(null);
		panel.setLayout(gridBag);
		
		amountOfStocks = new JLabel ();
		cons.gridx = 0;
		cons.gridy = 0;
		panel.add(amountOfStocks, cons);
		
		JLabel totalValueLabel = new JLabel("Total value:");
		cons.gridx = 1;
		cons.gridy = 1;
		panel.add(totalValueLabel, cons);
		
		totalValueField = new JTextField();
		totalValueField.setEditable(false);
		totalValueField.setText("0");
		totalValueField.setEditable(false);
		totalValueField.setColumns(6); 
		cons.gridx = 2;
		cons.gridy = 1;
		panel.add(totalValueField, cons);
		
		totalValueField.setText(Double.toString(portfolio.getTotalValue()));
		
		buyButton = new JButton("Buy stock");
		cons.gridx = 1;
		cons.gridy = 2;
		panel.add(buyButton, cons);
		
		
		listModel = new DefaultListModel();
		stocklist = new JList(listModel);
		stocklist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		stocklist.setLayoutOrientation(JList.VERTICAL);
		JScrollPane listScroller = new JScrollPane(stocklist);
		listScroller.setPreferredSize(new Dimension(250, 200));
		cons.weightx = 2.0;
		cons.fill = GridBagConstraints.WEST;
		cons.gridx = 0;
		cons.gridy = 1;
		panel.add(listScroller, cons);
		
		
		
		sellButton = new JButton("Sell selected stocks");
		cons.gridx = 0;
		cons.gridy = 2;
		panel.add(sellButton, cons);
		sellButton.setEnabled(false);
		
		frame.add(panel);
		//frame.pack();
		frame.setVisible(true);		
		
		portfolio.updatePortfolio();

		buyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BuyWindow buyWindow = new BuyWindow(portfolio);
				
			}});	
		frame.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
		    	  portfolio.save();
		      }
		});
	
		stocklist.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {

			        if (stocklist.getSelectedIndex() == -1) {
			        //No selection, disable fire button.
			            sellButton.setEnabled(false);

			        } else {
			        //Selection, enable the fire button.
			            sellButton.setEnabled(true);
			        }
			    }
			}
			
		});
	
	
		sellButton.addActionListener(new ActionListener() { //säljer, tar bort och räknar ut win loss

			@Override
				public void actionPerformed(ActionEvent e) {
				
				String stockToSell = (String) stocklist.getSelectedValue();

				String [] parts = stockToSell.split(" ");
				
				String symbol = parts[0];
				String value = parts[1];
				symbol = symbol.substring(0, symbol.length()-1);
				try {
					Double sellPrice = DataManager.getSellPrice(symbol);
					if (sellPrice == 0.0) return;
					listModel.remove(stocklist.getSelectedIndex());
					portfolio.removeStock(symbol, value);
					portfolio.updatePortfolio();
					
					double profit = (sellPrice - Double.parseDouble(value));
					DecimalFormat numberFormat = new DecimalFormat("#.000");
					
					JOptionPane.showMessageDialog(null,"Profit / Loss: " + numberFormat.format(profit), "The art of the deal",JOptionPane.PLAIN_MESSAGE);

				} catch (IOException e1) {
					e1.printStackTrace();
				}																																				
			}
		
		});
		
	}
	
}
