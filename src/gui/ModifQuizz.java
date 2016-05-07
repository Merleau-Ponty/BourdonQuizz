package gui;

import java.awt.Choice;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dao.ContenirDAO;
import dao.DAOFactory;
import dao.QuestionDAO;
import metier.Question;

public class ModifQuizz extends JPanel
{
	private int idQuizz;
	private ArrayList<Question> listeQuestDispo = new ArrayList<Question>();
	private ArrayList<Question> listeQuestSupprimables = new ArrayList<Question>();
	private Choice qDispo;
	private Choice qSupprimables;
	
	public ModifQuizz(int idQuizz)
	{
		this.idQuizz = idQuizz;
		initGUI();
	}
	
	public void initGUI()
	{
		QuestionDAO qDao = DAOFactory.getInstance().getQuestion();
		listeQuestSupprimables = qDao.selecQuestionsSupprimablesQuizz(idQuizz);
		for(Question q : qDao.selecToutesQuestions())
		{
			if(!listeQuestSupprimables.contains(q))
			{
				listeQuestDispo.add(q);
			}
		}
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 5, 0);
		JLabel titre = new JLabel("Modifier un quizz");
		titre.setFont(new Font("Roboto", Font.PLAIN, 40));
		add(titre, gbc);
		gbc.gridy += 1;
		gbc.insets = new Insets(5, 0, 0, 0);
		add(new JLabel("Choisissez une question à ajouter :"), gbc);
		gbc.insets = new Insets(0, 0, 5, 0);
		gbc.gridy += 1;
		qDispo = new Choice();
		for(Question q : listeQuestDispo)
		{
			qDispo.add(q.getEnonce());
		}
		add(qDispo, gbc);
		gbc.insets = new Insets(0, 0, 10, 0);
		gbc.gridy += 1;
		JButton ajouterQ = new JButton("Ajouter");
		ajouterQ.addMouseListener(new CustomMouseListener());
		add(ajouterQ, gbc);
		gbc.gridy += 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(new JLabel("Choisissez une question à supprimer :"), gbc);
		gbc.gridy += 1;
		gbc.insets = new Insets(0, 0, 5, 0);
		qSupprimables = new Choice();
		for(Question q : listeQuestSupprimables)
		{
			qSupprimables.add(q.getEnonce());
		}
		add(qSupprimables, gbc);
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridy += 1;
		JButton supprQ = new JButton("Supprimer");
		supprQ.addMouseListener(new CustomMouseListener());
		add(supprQ, gbc);
		gbc.gridy += 1;
		gbc.insets = new Insets(20, 0, 0, 0);
		JButton toutVal = new JButton("Valider la modification du quizz");
		toutVal.addMouseListener(new CustomMouseListener());
		add(toutVal, gbc);
		setBorder(BorderFactory.createEmptyBorder(0, 100, 5, 100));
	}
	
	private void applyChangesToPanel()
	{
		revalidate();
		repaint();
	}
	
	// Gestionnaire d'évènements
	private class CustomMouseListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			switch(((JButton)e.getSource()).getText())
			{
				case "Ajouter":
					String qAjout = qDispo.getSelectedItem();
					if(qAjout != null)
					{
						for(Question q : listeQuestDispo)
						{
							if(q.getEnonce().equals(qAjout))
							{
								listeQuestDispo.remove(q);
								listeQuestSupprimables.add(q);
								qDispo.remove(qAjout);
								qSupprimables.add(qAjout);
								applyChangesToPanel();
								break;
							}
						}
					}
					break;
				case "Supprimer":
					String qSuppr = qSupprimables.getSelectedItem();
					if(qSuppr != null)
					{
						for(Question q : listeQuestSupprimables)
						{
							if(q.getEnonce().equals(qSuppr))
							{
								listeQuestSupprimables.remove(q);
								listeQuestDispo.add(q);
								qSupprimables.remove(qSuppr);
								qDispo.add(qSuppr);
								applyChangesToPanel();
								break;
							}
						}
					}
					break;
				case "Valider la modification du quizz":
					ContenirDAO cDAO = DAOFactory.getInstance().getContenir();
					cDAO.supprimerQuestions(idQuizz);
					for(Question q : listeQuestSupprimables)
					{
						cDAO.ajouterQuestionQuizz(q.getIdQuestion(), idQuizz);
					}
					((MainGUI)SwingUtilities.getWindowAncestor(ModifQuizz.this)).changePanel(new MenuChoix());
					break;
			}
		}
	}
}
