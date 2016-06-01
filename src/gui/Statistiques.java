package gui;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class Statistiques extends JPanel
{
	public Statistiques()
	{
		initGUI();
	}
	
	private void initGUI()
	{
		setLayout(new GridLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
	}
}
