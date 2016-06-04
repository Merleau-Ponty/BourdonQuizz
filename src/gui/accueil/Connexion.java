package gui.accueil;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import dao.DAOFactory;
import dao.JoueurDAO;
import main.MainFrame;

/**
 * Classe désignant le JPanel permettant de se connecter en tant que membre ou administrateur
 * @author BourdonQuizz
 */
public class Connexion extends JPanel
{
	private JTextField txtLogin;
	private JPasswordField txtMotDePasse;
	private JButton pasInscrit;
	private JButton connexion;
	private boolean displayErr = false;

	/**
	 * Constructeur permettant d'initialiser l'interface graphique
	 */
	public Connexion()
	{
		initGUI();
	}

	/**
	 * Méthode permettant d'initialiser l'interface graphique
	 */
	private void initGUI()
	{
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets.bottom = 5;
		
		JLabel lblPageDeConnexion = new JLabel("Page de connexion");
		lblPageDeConnexion.setFont(new Font("Roboto", Font.PLAIN, 40));
		add(lblPageDeConnexion, gbc);

		txtLogin = new JTextField();
		txtLogin.setText("Login");
		txtLogin.setForeground(Color.GRAY);
		gbc.gridy += 1;
		add(txtLogin, gbc);
		txtLogin.setColumns(10);
		txtLogin.addFocusListener(new CustomFocusListener());
		txtLogin.addKeyListener(new CustomKeyListener());

		txtMotDePasse = new JPasswordField();
		txtMotDePasse.setEchoChar((char)0);
		txtMotDePasse.setText("Mot de passe");
		txtMotDePasse.setForeground(Color.GRAY);
		gbc.gridy += 1;
		add(txtMotDePasse, gbc);
		txtMotDePasse.setColumns(10);
		txtMotDePasse.addFocusListener(new CustomFocusListener());
		txtMotDePasse.addKeyListener(new CustomKeyListener());

		pasInscrit = new JButton("Pas encore inscrit ?");
		gbc.insets.bottom = 20;
		gbc.gridy += 1;
		add(pasInscrit, gbc);
		pasInscrit.addMouseListener(new CustomMouseListener());

		connexion = new JButton("Valider");
		gbc.insets.bottom = 0;
		gbc.gridy += 1;
		add(connexion, gbc);
		connexion.addMouseListener(new CustomMouseListener());
		connexion.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				seConnecter();
			}
		});
		
		setBorder(BorderFactory.createEmptyBorder(0,100,5,100));
		// Perte de focus
		this.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				grabFocus();
			}
		});
		this.addKeyListener(new CustomKeyListener());
	}
	
	/**
	 * Méthode permettant d'afficher une erreur lorsque les identifiants sont erronés
	 */
	public void displayError()
	{
		if(!displayErr)
		{
			JLabel err = new JLabel("Erreur de connexion : mauvais identifiants");
			err.setForeground(Color.RED);
			GridBagConstraints gbc_err = new GridBagConstraints();
			gbc_err.insets = new Insets(0, 0, 5, 5);
			gbc_err.gridx = 4;
			gbc_err.gridy = 13;
			add(err, gbc_err);
			displayErr = true;
			((MainFrame)SwingUtilities.getWindowAncestor(this)).applyChanges();
		}
	}
	
	/**
	 * Méthode permettant de se connecter et de changer d'interface graphique
	 */
	private void seConnecter()
	{
		DAOFactory f = DAOFactory.getInstance();
		JoueurDAO j = f.getJoueur();
		String login = txtLogin.getText();
		if(j.seConnecter(login, String.valueOf(txtMotDePasse.getPassword())))
		{
			((MainFrame)SwingUtilities.getWindowAncestor(Connexion.this)).changeToMenuChoix(login, j.selecJoueurParLogin(login));
		}
		else
			Connexion.this.displayError();
	}
		
	// Gestionnaires d'évènements
	/**
	 * Classe interne gérant l'écouteur d'évènements de souris
	 * @author BourdonQuizz
	 */
	private class CustomMouseListener extends MouseAdapter
	{
		/**
		 * Méthode se déclenchant lorsqu'un composant a été cliqué
		 */
		public void mouseClicked(MouseEvent e)
		{
			System.out.println("Entré !");
			if(e.getSource() == pasInscrit)
			{
				((MainFrame)SwingUtilities.getWindowAncestor(Connexion.this)).changePanel(new Inscription());;
			}
			else if(e.getSource() == connexion)
			{
				seConnecter();
			}
		}
	}
	
	// Réinitialisation des champs
	/**
	 * Classe interne gérant l'écouteur d'évènements de focus
	 * @author BourdonQuizz
	 */
	private final class CustomFocusListener extends FocusAdapter
	{
		/**
		 * Méthode déclenchée lorsqu'un composant obtient le focus
		 */
		public void focusGained(FocusEvent e)
		{
			if(e.getSource() == txtLogin)
			{
				if(txtLogin.getText().equals("Login"))
				{
					txtLogin.setText("");
					txtLogin.setForeground(Color.BLACK);
				}
			}
			else if(e.getSource() == txtMotDePasse)
			{
				if(String.valueOf(txtMotDePasse.getPassword()).equals("Mot de passe"))
				{
					txtMotDePasse.setText("");
					txtMotDePasse.setForeground(Color.BLACK);
					txtMotDePasse.setEchoChar('*');
				}
			}
		}
		
		/**
		 * Méthode déclenchée lorsqu'un composant perd le focus
		 */
		public void focusLost(FocusEvent e)
		{
			if(e.getSource() == txtLogin)
			{
				if(txtLogin.getText().equals(""))
				{
					txtLogin.setText("Login");
					txtLogin.setForeground(Color.GRAY);
				}
			}
			else if(e.getSource() == txtMotDePasse)
			{
				if(String.valueOf(txtMotDePasse.getPassword()).equals(""))
				{
					txtMotDePasse.setText("Mot de passe");
					txtMotDePasse.setForeground(Color.GRAY);
					txtMotDePasse.setEchoChar((char)0);
				}
			}
		}
	}
	
	// Soumission du formulaire avec la touche entrée
	/**
	 * Classe interne gérant l'écouteur d'évènements clavier
	 * @author BourdonQuizz
	 */
	private final class CustomKeyListener extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				connexion.doClick();
			}
		}
	}
}
