package gui.quizz;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dao.DAOFactory;
import main.MainFrame;

public class ChoixJouerQuizz extends JPanel
{
	public ChoixJouerQuizz()
	{
		initGUI();
	}
	
	private void initGUI()
	{
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets.bottom = 5;
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel titre = new JLabel("Choisissez un quizz");
		titre.setFont(new Font("Roboto", Font.PLAIN, 40));
		add(titre, gbc);
		gbc.insets.top = 5;
		DAOFactory dao = DAOFactory.getInstance();
		ArrayList<Integer> listeQuizz = dao.getQuizz().selectTousQuizz();
		for(int i : listeQuizz)
		{
			gbc.gridy += 1;
			add(new JButton("Quizz n°" + i + " (" + dao.getContenir().selecNbQuestionsQuizz(i) + " questions)"), gbc);
		}
		for(Component c : getComponents())
		{
			if(c instanceof JButton)
			{
				c.addMouseListener(new CustomMouseListener());
			}
		}
		setBorder(BorderFactory.createEmptyBorder(0, 100, 5, 100));
	}
	
	// Gestionnaire d'évènements
	private class CustomMouseListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			String s = ((JButton)e.getSource()).getText();
			((MainFrame)SwingUtilities.getWindowAncestor(ChoixJouerQuizz.this)).changePanel(
					new JouerQuizz(Integer.parseInt(s.substring(8, s.lastIndexOf('(') - 1))));
		}
	}
}
