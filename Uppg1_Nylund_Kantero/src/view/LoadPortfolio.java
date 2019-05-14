package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Portfolio;

public class LoadPortfolio {
	
	JFrame frame;
	JPanel panel;
	
	
	public LoadPortfolio(Portfolio portfolio) {
		// här måst vi nu beroende på portfolioName load all data å fyll i rätt data i window
		frame = new JFrame(portfolio.getName());
		panel = new JPanel();
		
		
		//model.Portfolio.getName(); //dehe sko kanske borda ske i LoadPortfolio,
		
		
		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints cons = new GridBagConstraints();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,600);
		frame.setLocationRelativeTo(null);
		panel.setLayout(gridBag);
		
		JLabel totalValueLabel = new JLabel("Total value:");
		cons.gridx = 1;
		cons.gridy = 1;
		panel.add(totalValueLabel, cons);
		
		JTextField totalValueField = new JTextField();
		totalValueField.setEditable(false);
		totalValueField.setText("0");		
		cons.gridx = 2;
		cons.gridy = 1;
		panel.add(totalValueField, cons);
		
		totalValueField.setText("3450");
		
		frame.add(panel);
		//frame.pack();
		frame.setVisible(true);		
	}

}
