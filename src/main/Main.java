package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Classe principal du programme, �tant son unique point d'entr�e
 * @author BourdonQuizz
 */
public class Main
{
	/**
	 * M�thode principale du programme servant d'unique point d'entr�e
	 * @param args arguments optionnels
	 * @throws UnsupportedLookAndFeelException lance une exception si le style graphique de l'OS n'est pas support�
	 * @throws IllegalAccessException lance une exception de probl�me d'acc�s
	 * @throws InstantiationException lance une exception montrant un probl�me au niveau de l'instantiation
	 * @throws ClassNotFoundException lance une exception indiquant que la classe mentionn�e est introuvable
	 */
	public static void main(String[] args) throws UnsupportedLookAndFeelException, IllegalAccessException, InstantiationException, ClassNotFoundException
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new MainFrame();
			}
		});
	}
}
