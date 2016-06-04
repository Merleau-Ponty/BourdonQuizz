package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Classe principal du programme, étant son unique point d'entrée
 * @author BourdonQuizz
 */
public class Main
{
	/**
	 * Méthode principale du programme servant d'unique point d'entrée
	 * @param args arguments optionnels
	 * @throws UnsupportedLookAndFeelException lance une exception si le style graphique de l'OS n'est pas supporté
	 * @throws IllegalAccessException lance une exception de problème d'accès
	 * @throws InstantiationException lance une exception montrant un problème au niveau de l'instantiation
	 * @throws ClassNotFoundException lance une exception indiquant que la classe mentionnée est introuvable
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
