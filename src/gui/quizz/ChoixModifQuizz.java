package gui.quizz;

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
import main.MainFrame;

/**
 * Classe définissant un JPanel permettant de choisir le quizz que l'on souhaite modifier
 * @author BourdonQuizz
 */
public class ChoixModifQuizz extends JPanel
{
	private ArrayList<Integer> qArrayList;
	
	/**
	 * Constructeur permettant d'initialiser l'interface graphique
	 */
	public ChoixModifQuizz()
	{
		initGUI();
	}

	/**
	 * Méthode permettant d'initialiser l'interface graphique
	 */
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

	/**
	 * Méthode permettant de générer tous les quizz pouvant être modifiés
	 * @return
	 */
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
	/**
	 * Classe interne définissant un écouteur d'évènements souris
	 * @author BourdonQuizz
	 */
	private final class CustomMouseListener extends MouseAdapter
	{
		/**
		 * Méthode déclenchée lors d'un clic sur un composant
		 */
		public void mouseClicked(MouseEvent e)
		{
			String text = ((JButton) e.getSource()).getText();
			text = text.substring(text.lastIndexOf('°') + 1);
			((MainFrame) SwingUtilities.getWindowAncestor(ChoixModifQuizz.this)).changePanel(new ModifQuizz(Integer.parseInt(text)));
		}
	}
}
