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

import gui.question.ChoixModifQuestion;
import gui.question.GestQuestion;
import gui.quizz.ChoixJouerQuizz;
import gui.quizz.ChoixModifQuizz;
import gui.quizz.CreationQuizz;
import main.MainFrame;
import metier.Joueur;

/**
 * Classe d�finissant un JPanel affichant le menu principal d'un administrateur ou d'un membre
 * @author BourdonQuizz
 */
public class MenuChoix extends JPanel
{
	// Joueur connect�
	public static Joueur joueur;
	
	private JButton ajouterQues;
	private JButton modifierQues;
	private JButton ajouterQuizz;
	private JButton modifierQuizz;
	private JButton resultats;
	private JButton jouerQuizz;
	
	/**
	 * Constructeur permettant d'initialiser l'interface graphique et de m�moriser les informations d'un joueur
	 * @param j objet Joueur d�signant le joueur � sauvegarder en attribut
	 */
	public MenuChoix(Joueur j)
	{
		joueur = j;
		initGUI();
	}
	
	/**
	 * Constructeur permettant d'initialiser l'interface graphique
	 */
	public MenuChoix()
	{
		initGUI();
	}

	/**
	 * M�thode permettant d'initialiser l'interface graphique
	 */
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
			gbc.insets.bottom = 12;
			add(modifierQues, gbc);
			ajouterQuizz = new JButton("Ajouter un quizz");
			gbc.gridy += 1;
			gbc.insets.bottom = 2;
			add(ajouterQuizz, gbc);
			modifierQuizz = new JButton("Modifier un quizz");
			gbc.gridy += 1;
			gbc.insets.bottom = 12;
			add(modifierQuizz, gbc);
			resultats = new JButton("Visualiser les r�sultats");
			gbc.gridy += 1;
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
		setBorder(BorderFactory.createEmptyBorder(0, 100, 5, 100));
	}
	
	/**
	 * M�thode permettant d'initialiser les diff�rents �couteurs des composants
	 */
	private void initListeners()
	{
		for(Component c : getComponents())
		{
			if(c instanceof JButton)
				c.addMouseListener(new CustomMouseListener());
		}
	}
	
	/**
	 * Classe interne d�finissant un �couteur d'�v�nements souris
	 * @author BourdonQuizz
	 */
	private final class CustomMouseListener extends MouseAdapter
	{
		/**
		 * M�thode d�clench�e lors d'un clic sur un composant
		 */
		public void mouseClicked(MouseEvent e)
		{
			JButton src = (JButton)e.getSource();
			MainFrame gui = (MainFrame)SwingUtilities.getWindowAncestor(MenuChoix.this);
			if(src == ajouterQues)
			{
				gui.changePanel(new GestQuestion());
			}
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
				gui.changePanel(new Statistiques());
			}
			else if(src == jouerQuizz)
			{
				gui.changePanel(new ChoixJouerQuizz());
			}
		}
	}
}
