package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import metier.Joueur;

public class MainGUI extends JFrame
{		
	private JMenuBar barre = new JMenuBar();
	private JMenu menu = new JMenu("Menu");
	private JMenu utilisateur = new JMenu("Utilisateur");
	private JMenuItem userInfo = new JMenuItem("Non connecté");
	private JMenu aide = new JMenu("Aide");
	
	public MainGUI() 
	{
		super("Application code de la route");
		initGUI();
	}
	
	private void initGUI()
	{
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		userInfo = new JMenuItem("Non connecté");
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
	
	public void changePanel(JPanel pan)
	{
		getContentPane().removeAll();
		add(pan, BorderLayout.NORTH);
		applyChanges();
	}
	
	public void applyChanges()
	{
		revalidate();
		repaint();
		pack();
	}
	
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
		menu.add(new JMenuItem("Visualiser les résultats"));
		menu.addSeparator();
		menu.add(new JMenuItem("Se déconnecter"));
		initListeners(true);
	}
	
	private void changeConnectedStateMember()
	{
		menu.removeAll();
		menu.add(new JMenuItem("Faire un quizz"));
		menu.addSeparator();
		menu.add(new JMenuItem("Se déconnecter"));
		initListeners(true);
	}
	
	private void changeConnectedStateDisconnected()
	{
		menu.removeAll();
		menu.add(new JMenuItem("S'inscrire"));
		menu.add(new JMenuItem("Se connecter"));
		userInfo.setText("Non connecté");
		userInfo.setEnabled(false);
		MenuChoix.joueur = null;
		changePanel(new Connexion());
		initListeners(false);
	}
	
	private void modifyPlayerInfo(String login)
	{
		userInfo.setEnabled(true);
		userInfo.setText(login);
	}
	
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
	
	// Gestionnaire d'évènements
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
				case "Se déconnecter":
					changeConnectedStateDisconnected();
					break;
				case "A propos":
					new InfosCreateurs();
					break;
			}
		}
	}
}
