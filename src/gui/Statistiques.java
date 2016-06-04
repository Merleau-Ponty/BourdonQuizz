package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.DAOFactory;

/**
 * Classe affichant différents statistiques obtenues de la base de données
 * @author BourdonQuizz
 */
public class Statistiques extends JPanel
{
	/**
	 * Constructeur permettant d'initialiser l'interface graphique
	 */
	public Statistiques()
	{
		initGUI();
	}
	
	/**
	 * Méthode permettant d'initialiser l'interface graphique
	 */
	private void initGUI()
	{
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets.bottom = 10;
		JLabel title = new JLabel("Statistiques globales");
		title.setFont(new Font("Roboto", Font.PLAIN, 40));
		add(title, gbc);
		gbc.insets = new Insets(0, 0, 5, 0);
		gbc.gridy += 1;
		DAOFactory dao = DAOFactory.getInstance();
		add(new JLabel("Nombre de joueurs inscrits : " + dao.getJoueur().selecNbJoueurs()), gbc);
		gbc.gridy += 1;
		add(new JLabel("Nombre de quizz joués : " + dao.getTentative().selecNbTentatives()), gbc);
		gbc.gridy += 1;
		gbc.insets.bottom = 0;
		add(new JLabel("Score moyen des utilisateurs : " + dao.getTentative().scoreMoyen()), gbc);
		setBorder(BorderFactory.createEmptyBorder(0, 100, 5, 100));
	}
}
