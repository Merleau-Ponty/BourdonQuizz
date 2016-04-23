package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.concurrent.BrokenBarrierException;

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
		initMain();
	}
	
	private void initMain()
	{
		setSize(800, 600);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		userInfo = new JMenuItem("Non connecté");
		userInfo.setEnabled(false);
		menu.add(new JMenuItem("Se connecter"));
		menu.add(new JMenuItem("S'inscrire"));
		aide.add(new JMenuItem("A propos"));
		utilisateur.add(userInfo);
		barre.add(menu);
		barre.add(utilisateur);
		barre.add(aide);
		
		initListeners();
		add(new Connexion(), BorderLayout.NORTH);
		setJMenuBar(barre);
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
	
	private void changeConnectedStateAdmin()
	{
		menu.removeAll();
		JMenuItem quest = new JMenu("Question");
		JMenuItem quizz = new JMenu("Quizz");
		menu.add(quest);
		quest.add(new JMenuItem("Ajouter une question"));
		quest.add(new JMenuItem("Modifier une question"));
		menu.add(quizz);
		quizz.add(new JMenuItem("Créer un quizz"));
		quizz.add(new JMenuItem("Modifier un quizz"));
		menu.add(new JMenuItem("Visualiser les résultats"));
		menu.addSeparator();
		menu.add(new JMenuItem("Se déconnecter"));
		initListeners();
		applyChanges();
	}
	
	private void changeConnectedStateMember()
	{
		// Modification du menu pour les membres
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
		initListeners();
		//applyChanges();
	}
	
	private void modifyPlayerInfo(String login)
	{
		userInfo.setEnabled(true);
		userInfo.setText(login);
	}
	
	private void applyChanges()
	{
		revalidate();
		repaint();
	}
	
	private void initListeners()
	{
		for(Component c : barre.getComponents())
		{
			if(c instanceof JMenu)
			{
				for(Component c2 : ((JMenu)c).getMenuComponents())
				{
					if(c2 instanceof JMenuItem)
						((JMenuItem)c2).addActionListener(new CustomActionListener());
				}
			}
		}
	}
	
	// Gestionnaire d'évènement
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
