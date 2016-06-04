package gui.quizz;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import dao.DAOFactory;
import gui.MenuChoix;
import main.MainFrame;
import metier.Question;

/**
 * Classe d�finissant un JPanel permettant de cr�er un quizz
 * @author BourdonQuizz
 */
public class CreationQuizz extends JPanel
{
	private Choice listeQDisponibles;
	private ArrayList<Question> listeToutesQuestions = new ArrayList<Question>();
	private ArrayList<Question> listeQAAjouter = new ArrayList<Question>();
	private JLabel messageConfirmation;
	private boolean displayError = false;
	
	/**
	 * Constructeur permettant d'initialiser l'interface graphique
	 */
	public CreationQuizz()
	{
		initGUI();
	}
	
	/**
	 * M�thode permettant d'initialiser l'interface graphique
	 */
	public void initGUI()
	{
		setBorder(BorderFactory.createEmptyBorder(0, 100, 5, 100));
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 5, 0);
		JLabel titre = new JLabel("Cr�ation d'un quizz");
		titre.setFont(new Font("Roboto", Font.PLAIN, 40));
		add(titre, gbc);
		gbc.insets = new Insets(5, 0, 0, 0);
		gbc.gridy += 1;
		add(new JLabel("Choisissez une question � ajouter :"), gbc);
		gbc.insets = new Insets(0, 0, 5, 0);
		gbc.gridy += 1;
		listeQDisponibles = new Choice();
		DAOFactory daoF = DAOFactory.getInstance();
		for(Question q : daoF.getQuestion().selecToutesQuestions())
		{
			listeQDisponibles.add(q.getEnonce());
			listeToutesQuestions.add(q);
		}
		add(listeQDisponibles, gbc);
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridy += 1;
		JButton ajouterQ = new JButton("Ajouter la question");
		ajouterQ.addMouseListener(new CustomMouseListener());
		add(ajouterQ, gbc);
		gbc.gridy += 1;
		gbc.insets.top = 20;
		JButton valider = new JButton("Cr�er le quizz");
		valider.addMouseListener(new CustomMouseListener());
		add(valider, gbc);
	}
	
	/**
	 * M�thode permettant d'appliquer les changements effectu�s sur le JPanel CreationQuizz
	 */
	private void applyChangesToPanel()
	{
		revalidate();
		repaint();
	}
	
	// Gestionnaire d'�v�nements
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
			switch(((JButton)e.getSource()).getText())
			{
				case "Ajouter la question":
					String enonceActuel = listeQDisponibles.getSelectedItem();
					if(enonceActuel != null)
					{
						for(Question q : listeToutesQuestions)
						{
							if(q.getEnonce().equals(enonceActuel))
							{
								listeQAAjouter.add(q);
							}
						}
						listeQDisponibles.remove(enonceActuel);
						messageConfirmation = new JLabel("Question ajout�e au quizz");
						GridBagConstraints gbc = new GridBagConstraints();
						gbc.gridx = 1;
						gbc.gridy = 3;
						gbc.insets = new Insets(0, -100, 0, 0);
						add(messageConfirmation, gbc);
						applyChangesToPanel();
						Timer timer = new Timer(1000, new ActionListener() 
						{
							@Override
							public void actionPerformed(ActionEvent e) 
							{
								remove(messageConfirmation);
								applyChangesToPanel();
							}
						});
						timer.setRepeats(false); 
						timer.start();
					}
					break;
				case "Cr�er le quizz":
					if(listeQAAjouter.size() == 0)
					{
						if(!displayError)
						{
							GridBagConstraints gbc = new GridBagConstraints();
							gbc.gridx = 0;
							gbc.gridy = 5;
							gbc.insets.top = 5;
							JLabel erreur = new JLabel("Veuillez ajouter au moins une question");
							erreur.setForeground(Color.RED);
							add(erreur, gbc);
							displayError = true;
							((MainFrame)SwingUtilities.getWindowAncestor(CreationQuizz.this)).applyChanges();
						}
					}
					else 
					{
						DAOFactory dao = DAOFactory.getInstance();
						int idQuizz = dao.getQuizz().creerQuizz();
						for(Question q : listeQAAjouter)
						{
							dao.getContenir().ajouterQuestionQuizz(q.getIdQuestion(), idQuizz);
						}
						((MainFrame)SwingUtilities.getWindowAncestor(CreationQuizz.this)).changePanel(new MenuChoix());
					}
					break;
			}
		}
	}
}
