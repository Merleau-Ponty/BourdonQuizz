package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class QuestionGUI extends JPanel
{
	public QuestionGUI(boolean ajout)
	{
		initGUI(ajout);
	}
	
	private void initGUI(boolean ajout)
	{
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 5, 0);
		JLabel title = new JLabel("d'une question");
		title.setFont(new Font("Roboto", Font.PLAIN, 40));
		add(title, gbc);
		if(ajout)
		{
			title.setText("Ajout " + title.getText());
			JTextArea libelle = new JTextArea();
			libelle.setForeground(Color.GRAY);
			libelle.setFont(getFont());
			gbc.gridy += 1;
			add(libelle, gbc);
		}
		else
		{
			title.setText("Modification " + title.getText());
		}
	}
	
	// Gestionnaires d'évènement
	private class CustomFocusListener extends FocusAdapter
	{
		public void focusGained(FocusEvent e)
		{
			
		}
	}
}
