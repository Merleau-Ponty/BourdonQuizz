package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class InfosCreateurs extends JFrame
{
	private String[] developpers = {"Gabriel Fruhauf", "Lilian Lefort", "Maxime Salvat"};
	
	public InfosCreateurs()
	{
		super("A propos");
		init();
	}
	
	private void init()
	{
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		JLabel title = new JLabel("Développé par");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Roboto", Font.PLAIN, 40));
		gbc.insets = new Insets(0, 5, 3, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(title, gbc);
		for(String s : developpers)
		{
			JLabel j = new JLabel(s);
			gbc.gridy += 1;
			gbc.insets = new Insets(2, 0, 2, 0);
			add(j, gbc);
		}
		pack();
		setAlwaysOnTop(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
