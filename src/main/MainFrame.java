package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import gui.Connexion;
import gui.InfosCreateurs;
import gui.Inscription;
import gui.MenuChoix;
import gui.question.ChoixModifQuestion;
import gui.question.GestQuestion;
import gui.quizz.ChoixJouerQuizz;
import gui.quizz.ChoixModifQuizz;
import gui.quizz.CreationQuizz;
import metier.Joueur;

/**
 * Classe "m�re" repr�sentant la JFrame � laquelle viendra se greffer un JPanel qui sera chang�. 
 * Cette classe fait en fin de compte office de controleur et est le squelette du programme.
 * @author BourdonQuizz
 */
public class MainFrame extends JFrame
{		
	private JMenuBar barre = new JMenuBar();
	private JMenu menu = new JMenu("Menu");
	private JMenu utilisateur = new JMenu("Utilisateur");
	private JMenuItem userInfo = new JMenuItem("Non connect�");
	private JMenu aide = new JMenu("Aide");
	
	/**
	 * Constructeur permettant de cr�er et initialiser la JFrame
	 * @see JFrame
	 */
	public MainFrame() 
	{
		super("Application code de la route");
		initGUI();
	}
	
	/**
	 * M�thode permettant d'initialiser l'interface graphique avec les menus et le JPanel d'accueil
	 */
	private void initGUI()
	{
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		userInfo = new JMenuItem("Non connect�");
		userInfo.setEnabled(false);
		menu.add(new JMenuItem("Se connecter"));
		menu.add(new JMenuItem("S'inscrire"));
		aide.add(new JMenuItem("A propos"));
		utilisateur.add(userInfo);
		barre.setBorderPainted(false);
		barre.add(menu);
		barre.add(utilisateur);
		barre.add(aide);
		
		initListeners(false);
		add(new Connexion(), BorderLayout.NORTH);
		setJMenuBar(barre);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * M�thode permettant de modifier les menus de la JMenuBar selon le type d'utilisateur connect�
	 * @param login nom d'utilisateur du joueur se connectant
	 * @param j objet Joueur correspondant au joueur se connectant
	 */
	public void changeToMenuChoix(String login, Joueur j)
	{
		getContentPane().removeAll();
		add(new MenuChoix(j), BorderLayout.NORTH);
		if(j.getType_utilisateur().equals("admin"))
			changeConnectedStateAdmin();
		else
			changeConnectedStateMember();
		modifyPlayerInfo(login);
		applyChanges();
	}
	
	/**
	 * M�thode permettant de changer l'objet JPanel � afficher
	 * @param pan objet JPanel � afficher
	 * @see JPanel
	 */
	public void changePanel(JPanel pan)
	{
		getContentPane().removeAll();
		add(pan, BorderLayout.NORTH);
		applyChanges();
	}
	
	/**
	 * M�thode permettant de valider les changements graphiques op�r�s et de redimensionner la fen�tre
	 */
	public void applyChanges()
	{
		revalidate();
		repaint();
		pack();
	}
	
	/**
	 * M�thode changeant les menus de la JMenuBar si l'utilisateur est connect�e en tant qu'administrateur
	 */
	private void changeConnectedStateAdmin()
	{
		menu.removeAll();
		JMenuItem quest = new JMenu("Question");
		JMenuItem quizz = new JMenu("Quizz");
		menu.add(quest);
		quest.add(new JMenuItem("Ajouter une question"));
		quest.add(new JMenuItem("Modifier une question"));
		menu.add(quizz);
		quizz.add(new JMenuItem("Ajouter un quizz"));
		quizz.add(new JMenuItem("Modifier un quizz"));
		menu.add(new JMenuItem("Visualiser les r�sultats"));
		menu.addSeparator();
		menu.add(new JMenuItem("Se d�connecter"));
		initListeners(true);
	}
	
	/**
	 * M�thode changeant les menus de la JMenuBar si l'utilisateur est connect�e en tant que membre
	 */
	private void changeConnectedStateMember()
	{
		menu.removeAll();
		menu.add(new JMenuItem("Faire un quizz"));
		menu.addSeparator();
		menu.add(new JMenuItem("Se d�connecter"));
		initListeners(true);
	}
	
	/**
	 * M�thode permettant de changer le menu conform�ment � si l'utilisateur est connect� ou non
	 */
	private void changeConnectedStateDisconnected()
	{
		menu.removeAll();
		menu.add(new JMenuItem("S'inscrire"));
		menu.add(new JMenuItem("Se connecter"));
		userInfo.setText("Non connect�");
		userInfo.setEnabled(false);
		MenuChoix.joueur = null;
		changePanel(new Connexion());
		initListeners(false);
	}
	
	/**
	 * M�thode permettant d'afficher le nom d'utilisateur de la personne connect�e
	 * @param login nom d'utilisateur de la personne connect�e
	 */
	private void modifyPlayerInfo(String login)
	{
		userInfo.setEnabled(true);
		userInfo.setText(login);
	}
	
	/**
	 * M�thode permettant d'initialiser les listeners
	 * @param menuChoix bool�en d�signant si oui ou non l'utilisateur est connect�
	 */
	private void initListeners(boolean menuChoix)
	{
		for(Component c : barre.getComponents())
		{
			if(c instanceof JMenu)
			{
				if(!menuChoix)
				{
					for(Component c2 : ((JMenu)c).getMenuComponents())
					{
						if(c2 instanceof JMenuItem)
						{
							((JMenuItem)c2).addActionListener(new CustomActionListener());
						}
					}
				}
				else
				{
					if((JMenu)c == menu)
					{
						for(Component c2 : ((JMenu)c).getMenuComponents())
						{
							if(c2 instanceof JMenu)
							{
								for(Component c3 : ((JMenu)c2).getMenuComponents())
								{
									if(c3 instanceof JMenuItem)
									{
										((JMenuItem) c3).addActionListener(new CustomActionListener());
									}	
								}
							}
							else if(c2 instanceof JMenuItem)
							{
								JMenuItem deconnexion = ((JMenuItem)c2);
								if(c2 == deconnexion)
								{
									deconnexion.addActionListener(new CustomActionListener());
								}
							}
						}
					}
				}
			}
		}
	}
	
	// Gestionnaire d'�v�nements
	/**
	 * Classe interne �tant l'�couteur d'�v�nements de la JMenuBar
	 * @author BourdonQuizz
	 */
	private class CustomActionListener extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			switch(((JMenuItem)e.getSource()).getText())
			{
				case "Se connecter":
					changePanel(new Connexion());
					break;
				case "S'inscrire":
					changePanel(new Inscription());
					break;
				case "Ajouter une question":
					changePanel(new GestQuestion());
					break;
				case "Modifier une question":
					changePanel(new ChoixModifQuestion());
					break;
				case "Ajouter un quizz":
					changePanel(new CreationQuizz());
					break;
				case "Modifier un quizz":
					changePanel(new ChoixModifQuizz());
					break;
				case "Faire un quizz":
					changePanel(new ChoixJouerQuizz());
					break;
				case "Se d�connecter":
					changeConnectedStateDisconnected();
					break;
				case "A propos":
					new InfosCreateurs();
					break;
			}
		}
	}
}
