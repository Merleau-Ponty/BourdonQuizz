package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.xml.soap.Text;

import dao.DAOFactory;
import dao.JoueurDAO;

public class Connexion extends JPanel
{
	private JTextField txtLogin;
	private JPasswordField txtMotDePasse;
	private JButton pasInscrit;
	private JButton connexion;
	private boolean displayErr = false;

	public Connexion()
	{
		init();
	}

	private void init()
	{
		setBounds(100, 100, 800, 450);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[14];
		gridBagLayout.rowHeights = new int[14];
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblPageDeConnexion = new JLabel("Page de connexion");
		lblPageDeConnexion.setHorizontalAlignment(SwingConstants.CENTER);
		lblPageDeConnexion.setFont(new Font("Roboto", Font.PLAIN, 40));
		GridBagConstraints gbc_lblPageDeConnexion = new GridBagConstraints();
		gbc_lblPageDeConnexion.insets = new Insets(0, 0, 5, 5);
		gbc_lblPageDeConnexion.gridx = 4;
		gbc_lblPageDeConnexion.gridy = 0;
		add(lblPageDeConnexion, gbc_lblPageDeConnexion);

		txtLogin = new JTextField();
		txtLogin.setText("Login");
		txtLogin.setForeground(Color.GRAY);
		GridBagConstraints gbc_txtLogin = new GridBagConstraints();
		gbc_txtLogin.insets = new Insets(0, 0, 5, 5);
		gbc_txtLogin.gridx = 4;
		gbc_txtLogin.gridy = 3;
		add(txtLogin, gbc_txtLogin);
		txtLogin.setColumns(10);
		txtLogin.addFocusListener(new CustomFocusListener());

		txtMotDePasse = new JPasswordField();
		txtMotDePasse.setEchoChar((char)0);
		txtMotDePasse.setText("Mot de passe");
		txtMotDePasse.setForeground(Color.GRAY);
		GridBagConstraints gbc_txtMotDePasse = new GridBagConstraints();
		gbc_txtMotDePasse.insets = new Insets(0, 0, 5, 5);
		gbc_txtMotDePasse.gridx = 4;
		gbc_txtMotDePasse.gridy = 4;
		add(txtMotDePasse, gbc_txtMotDePasse);
		txtMotDePasse.setColumns(10);
		txtMotDePasse.addFocusListener(new CustomFocusListener());

		pasInscrit = new JButton("Pas encore inscrit ?");
		GridBagConstraints gbc_pasInscrit = new GridBagConstraints();
		gbc_pasInscrit.insets = new Insets(0, 0, 5, 5);
		gbc_pasInscrit.gridx = 4;
		gbc_pasInscrit.gridy = 9;
		add(pasInscrit, gbc_pasInscrit);
		pasInscrit.addMouseListener(new CustomMouseListener());

		connexion = new JButton("Valider");
		GridBagConstraints gbc_seConnecter = new GridBagConstraints();
		gbc_seConnecter.gridx = 4;
		gbc_seConnecter.gridy = 12;
		add(connexion, gbc_seConnecter);
		connexion.addMouseListener(new CustomMouseListener());
		
		// Perte de focus
		this.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				grabFocus();
			}
		});
	}
	
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
			revalidate();
			repaint();
		}
	}
		
	// Gestionnaires d'�v�nement
	private class CustomMouseListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if(e.getSource() == pasInscrit)
			{
				((MainGUI)SwingUtilities.getWindowAncestor(Connexion.this)).changePanel(new Inscription());;
			}
			else if(e.getSource() == connexion)
			{
				DAOFactory f = DAOFactory.getInstance();
				JoueurDAO j = f.getJoueur();
				String login = txtLogin.getText();
				if(j.seConnecter(login, String.valueOf(txtMotDePasse.getPassword())))
				{
					((MainGUI)SwingUtilities.getWindowAncestor(Connexion.this)).changeToMenuChoix(login, j.selecJoueurParLogin(login));
				}
				else
					Connexion.this.displayError();
			}
		}
	}
	
	private class CustomFocusListener extends FocusAdapter
	{
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
}
