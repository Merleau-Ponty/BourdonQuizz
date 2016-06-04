package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Classe définissant un JPanel permettant d'obtenir les noms des créateurs du programme
 * @author BourdonQuizz
 */
public class InfosCreateurs extends JFrame
{
	private final String[] developpers = {"Gabriel Fruhauf", "Lilian Lefort", "Maxime Salvat"};
	
	/**
	 * Constructeur permettant d'initialiser l'interface graphique
	 */
	public InfosCreateurs()
	{
		super("A propos");
		initGUI();
	}
	
	/**
	 * Méthode permettant d'initialiser l'interface graphique en affichant notamment les noms des créateurs
	 */
	private void initGUI()
	{
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		JLabel title = new JLabel("Développé par");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Roboto", Font.PLAIN, 40));
		gbc.insets = new Insets(0, 5, 3, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(title, gbc);
		for(String s : developpers)
		{
			JLabel j = new JLabel(s);
			gbc.gridy += 1;
			gbc.insets = new Insets(2, 0, 2, 0);
			add(j, gbc);
		}
		pack();
		setAlwaysOnTop(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
