package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Portfolio;

public class CreatePortfolioWindow {
	
	String name;
	
	public CreatePortfolioWindow() {
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

	}

}
