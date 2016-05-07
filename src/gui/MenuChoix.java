package gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import metier.Joueur;

public class MenuChoix extends JPanel
{
	// Joueur connecté
	public static Joueur joueur;
	
	private JButton ajouterQues;
	private JButton modifierQues;
	private JButton ajouterQuizz;
	private JButton modifierQuizz;
	private JButton resultats;
	private JButton jouerQuizz;
	
	public MenuChoix(Joueur j)
	{
		joueur = j;
		initGUI();
	}
	
	public MenuChoix()
	{
		initGUI();
	}

	private void initGUI()
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
			resultats = new JButton("Visualiser les résultats");
			gbc.gridy += 1;
			gbc.insets = new Insets(2, 0, 12, 0);
			add(resultats, gbc);
		}
		else
		{
			add(title, gbc);
			jouerQuizz = new JButton("Faire un quizz");
			gbc.gridy += 1;
			gbc.insets = new Insets(2, 0, 2, 0);
			add(jouerQuizz, gbc);
		}
		initListeners();
		setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
	}
	
	private void initListeners()
	{
		for(Component c : getComponents())
		{
			if(c instanceof JButton)
				c.addMouseListener(new CustomMouseListener());
		}
	}
	
	private class CustomMouseListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			JButton src = (JButton)e.getSource();
			MainGUI gui = (MainGUI)SwingUtilities.getWindowAncestor(MenuChoix.this);
			if(src == ajouterQues)
				gui.changePanel(new GestQuestion());
			else if(src == modifierQues)
			{
				gui.changePanel(new ChoixModifQuestion());
			}
			else if(src == ajouterQuizz)
			{
				gui.changePanel(new CreationQuizz());
			}
			else if(src == modifierQuizz)
			{
				gui.changePanel(new ChoixModifQuizz());
			}
			else if(src == resultats)
			{
				
			}
			else if(src == jouerQuizz)
			{
				
			}
		}
	}
}
