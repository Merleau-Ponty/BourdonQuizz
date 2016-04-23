package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.Main;
import metier.Joueur;

public class MenuChoix extends JPanel
{
	// Joueur connect�
	public static Joueur joueur;
	
	private JButton ajouterQues;
	private JButton modifierQues;
	private JButton ajouterQuizz;
	private JButton modifierQuizz;
	private JButton resultats;
	
	public MenuChoix(Joueur j)
	{
		joueur = j;
		init();
	}

	private void init()
	{
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		JLabel title = new JLabel("Choisissez votre action");
		title.setFont(new Font("Roboto", Font.PLAIN, 40));
		gbc.insets = new Insets(0, 0, 3, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		if(joueur.getType_utilisateur().equals("admin"))
		{
			title.setText(title.getText() + " (admin)");
			add(title, gbc);
			ajouterQues = new JButton("Ajouter une question");
			gbc.gridy += 1;
			gbc.insets = new Insets(2, 0, 2, 0);
			add(ajouterQues, gbc);
			modifierQues = new JButton("Modifier une question");
			gbc.gridy += 1;
			gbc.insets = new Insets(2, 0, 12, 0);
			add(modifierQues, gbc);
			ajouterQuizz = new JButton("Ajouter un quizz");
			gbc.gridy += 1;
			gbc.insets = new Insets(2, 0, 2, 0);
			add(ajouterQuizz, gbc);
			modifierQuizz = new JButton("Modifier un quizz");
			gbc.gridy += 1;
			gbc.insets = new Insets(2, 0, 12, 0);
			add(modifierQuizz, gbc);
			resultats = new JButton("Visualiser les r�sultats");
			gbc.gridy += 1;
			add(resultats, gbc);
		}
		else
		{
			add(title, gbc);
		}
	}
	
	private class AdminMouseListener extends MouseAdapter
	{
		
	}
}
