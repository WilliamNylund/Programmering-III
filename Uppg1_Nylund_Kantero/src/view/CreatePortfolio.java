package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Portfolio;

public class CreatePortfolio {
	
	String name;
	
	public CreatePortfolio() {
		boolean nameNotChosen = true;
		name = JOptionPane.showInputDialog(null, "Enter name of portfolio", "Portfolio name");
		
		if (!name.equals("")) {
			nameNotChosen = false;
		}
		
		while(nameNotChosen) {
			name = JOptionPane.showInputDialog(null, "Enter valid name", "Portfolio name");
			if (!name.equals("")) {
				nameNotChosen = false;
			}
		}
		
		if (!name.equals(null)){
			Portfolio portfolio = new Portfolio(name);
			
			
		}

		/*JFrame frame = new JFrame("Create portfolio");
		JPanel panel = new JPanel();
		
		
		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints cons = new GridBagConstraints();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,75);
		frame.setLocationRelativeTo(null);
		panel.setLayout(gridBag);
		
		//cons.anchor = GridBagConstraints.WEST;
		JLabel nameLabel = new JLabel("Enter portfolio name");
		cons.gridx = 0;
		cons.gridy = 0;
		panel.add(nameLabel, cons);
		
		JTextField name = new JTextField(20);
		cons.gridx = 1;
		cons.gridy = 0;
		panel.add(name, cons);
		
		JButton okButton = new JButton("OK");
		cons.gridx = 2;
		cons.gridy = 0;
		panel.add(okButton, cons);
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
		*/
		
		
		
	}

}
