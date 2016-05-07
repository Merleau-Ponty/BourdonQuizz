package gui;

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
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import dao.DAOFactory;

public class ChoixModifQuizz extends JPanel
{
	private ArrayList<Integer> qArrayList;
	
	public ChoixModifQuizz()
	{
		initGUI();
	}

	public void initGUI()
	{
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 5, 0);
		JLabel titre = new JLabel("Modifier un quizz");
		titre.setFont(new Font("Roboto", Font.PLAIN, 40));
		add(titre, gbc);
		qArrayList = DAOFactory.getInstance().getQuizz().selectTousQuizz();
		gbc.gridy += 1;
		JScrollPane scrollPane = new JScrollPane(genQuizz());
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		add(scrollPane, gbc);

		setBorder(BorderFactory.createEmptyBorder(0, 100, 5, 100));
	}

	private JPanel genQuizz()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		for(Integer i : qArrayList)
		{
			gbc.insets = new Insets(0, 0, 15, 0);
			JButton b = new JButton("Modifier le quizz n°" + String.valueOf(i));
			b.addMouseListener(new CustomMouseListener());
			panel.add(b, gbc);
			gbc.gridy += 1;
		}
		return panel;
	}

	// Gestionnaire d'évènements
	private class CustomMouseListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			String text = ((JButton) e.getSource()).getText();
			text = text.substring(text.lastIndexOf('°') + 1);
			((MainGUI) SwingUtilities.getWindowAncestor(ChoixModifQuizz.this)).changePanel(new ModifQuizz(Integer.parseInt(text)));
		}
	}
}
