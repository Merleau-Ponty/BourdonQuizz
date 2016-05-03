package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import dao.DAOFactory;
import metier.Question;

public class ChoixModifQuestion extends JPanel
{
	ArrayList<Question> qArrayList;
	
	public ChoixModifQuestion()
	{
		initGUI();
	}
	
	private void initGUI()
	{
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 5, 0);
		JLabel titre = new JLabel("Modifier une question");
		titre.setFont(new Font("Roboto", Font.PLAIN, 40));
		add(titre, gbc);
		qArrayList = DAOFactory.getInstance().getQuestion().selecToutesQuestions();
		gbc.gridy += 1;
		JScrollPane scrollPane = new JScrollPane(genQuestions());
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
//		Dimension dim = ((JFrame)SwingUtilities.getWindowAncestor(this)).getBounds().getSize();
//		dim.setSize(dim.getWidth(), 0.75 * dim.getHeight());
//		scrollPane.setPreferredSize(dim);
		add(scrollPane, gbc);
		
		setBorder(BorderFactory.createEmptyBorder(0,100,5,100));
	}
	
	private JPanel genQuestions()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		for(Question q : qArrayList)
		{
			gbc.insets = new Insets(5, 0, 5, 0);
			panel.add(new JLabel(q.getEnonce()), gbc);
			gbc.insets = new Insets(0, 0, 15, 0);
			gbc.gridy += 1;
			JButton b = new JButton("Modifier la question n°" + q.getIdQuestion());
			b.addMouseListener(new CustomMouseListener());
			panel.add(b, gbc);
			gbc.gridy += 1;
		}
		return panel;
	}
	
	// Gestionnaire d'évènement
	private class CustomMouseListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			String text = ((JButton)e.getSource()).getText();
			text = text.substring(text.lastIndexOf('°') + 1);
			((MainGUI)SwingUtilities.getWindowAncestor(ChoixModifQuestion.this)).changePanel(new GestQuestion(Integer.parseInt(text)));
		}
	}
}
