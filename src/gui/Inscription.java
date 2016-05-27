package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

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

public class Inscription extends JPanel
{
	private JTextField nom;
	private JTextField prenom;
	private JTextField telephone;
	private JTextField email;
	private JTextField service;
	private JTextField login;
	private JTextField mdp;
	private JButton valider;
	private boolean displayErr = false;

	private HashMap<String, JTextField> components = new HashMap<String, JTextField>();

	public Inscription()
	{
		initGUI();
	}

	private void initGUI()
	{
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[15];
		gridBagLayout.rowHeights = new int[13];
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblPageDinscription = new JLabel("Inscription");
		lblPageDinscription.setFont(new Font("Roboto", Font.PLAIN, 40));
		GridBagConstraints gbc_lblPageDinscription = new GridBagConstraints();
		gbc_lblPageDinscription.insets = new Insets(0, 0, 5, 0);
		gbc_lblPageDinscription.gridx = 8;
		gbc_lblPageDinscription.gridy = 0;
		add(lblPageDinscription, gbc_lblPageDinscription);

		nom = new JTextField("Nom");
		GridBagConstraints gbc_nom = new GridBagConstraints();
		gbc_nom.insets = new Insets(0, 0, 5, 5);
		gbc_nom.gridx = 8;
		gbc_nom.gridy = 2;
		add(nom, gbc_nom);
		nom.setColumns(10);

		prenom = new JTextField("Prénom");
		GridBagConstraints gbc_txtPrnom = new GridBagConstraints();
		gbc_txtPrnom.insets = new Insets(0, 0, 5, 5);
		gbc_txtPrnom.gridx = 8;
		gbc_txtPrnom.gridy = 3;
		add(prenom, gbc_txtPrnom);
		prenom.setColumns(10);

		telephone = new JTextField("Téléphone");
		GridBagConstraints gbc_txtTlphone = new GridBagConstraints();
		gbc_txtTlphone.insets = new Insets(0, 0, 5, 5);
		gbc_txtTlphone.gridx = 8;
		gbc_txtTlphone.gridy = 4;
		add(telephone, gbc_txtTlphone);
		telephone.setColumns(10);

		email = new JTextField("E-mail");
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.insets = new Insets(0, 0, 5, 5);
		gbc_txtEmail.gridx = 8;
		gbc_txtEmail.gridy = 5;
		add(email, gbc_txtEmail);
		email.setColumns(10);

		service = new JTextField("Service");
		GridBagConstraints gbc_txtService = new GridBagConstraints();
		gbc_txtService.insets = new Insets(0, 0, 5, 5);
		gbc_txtService.gridx = 8;
		gbc_txtService.gridy = 6;
		add(service, gbc_txtService);
		service.setColumns(10);

		login = new JTextField("Login");
		GridBagConstraints gbc_txtLogin = new GridBagConstraints();
		gbc_txtLogin.insets = new Insets(0, 0, 5, 5);
		gbc_txtLogin.gridx = 8;
		gbc_txtLogin.gridy = 7;
		add(login, gbc_txtLogin);
		login.setColumns(10);

		mdp = new JPasswordField("Mot de passe");
		GridBagConstraints gbc_txtMotDePasse = new GridBagConstraints();
		gbc_txtMotDePasse.insets = new Insets(0, 0, 20, 5);
		gbc_txtMotDePasse.gridx = 8;
		gbc_txtMotDePasse.gridy = 8;
		add(mdp, gbc_txtMotDePasse);
		mdp.setColumns(10);

		valider = new JButton("Valider");
		GridBagConstraints gbc_btnValider = new GridBagConstraints();
		// gbc_btnValider.insets = new Insets(0, 0, 0, 0);
		gbc_btnValider.gridx = 8;
		gbc_btnValider.gridy = 11;
		add(valider, gbc_btnValider);
		valider.addMouseListener(new CustomMouseListener());

		setBorder(BorderFactory.createEmptyBorder(0, 100, 5, 100));
		initFields();
		this.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				grabFocus();
			}
		});
	}

	public void initFields()
	{
		for (Component c : this.getComponents())
		{
			if (c instanceof JTextField)
			{
				c.setForeground(Color.GRAY);
				c.addFocusListener(new CustomFocusListener());
				if (c instanceof JPasswordField)
				{
					((JPasswordField) c).setEchoChar((char) 0);
					components.put((String.valueOf(((JPasswordField) c).getPassword())), (JPasswordField) c);
				}
				else
					components.put(((JTextField) c).getText(), (JTextField) c);
			}
		}
	}

	public void displayError()
	{
		if (!displayErr)
		{
			JLabel err = new JLabel("Veuillez remplir correctement tous les champs");
			err.setForeground(Color.RED);
			GridBagConstraints gbc_err = new GridBagConstraints();
			gbc_err.insets = new Insets(0, 0, 5, 5);
			gbc_err.gridx = 8;
			gbc_err.gridy = 12;
			add(err, gbc_err);
			displayErr = true;
			((MainFrame) SwingUtilities.getWindowAncestor(this)).applyChanges();
		}
	}

	// Gestionnaires d'évènements
	private class CustomMouseListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if (e.getSource() == valider)
			{
				boolean res = true;
				ArrayList<String> ins = new ArrayList<String>();
				for (Component c : Inscription.this.getComponents())
				{
					if (c instanceof JTextField)
					{
						JTextField field = (JTextField) c;
						ins.add(field.getText());
						for (Entry<String, JTextField> entry : components.entrySet())
						{
							if (field == entry.getValue())
							{
								if (field.getText().equals(entry.getKey()) || field.getText().equals(""))
									res = false;
							}
						}
					}
				}
				if (!res)
					Inscription.this.displayError();
				else
				{
					DAOFactory f = DAOFactory.getInstance();
					JoueurDAO jdao = f.getJoueur();
					((MainFrame) SwingUtilities.getWindowAncestor(Inscription.this))
							.changeToMenuChoix(jdao.inscrireJoueur(ins), jdao.selecJoueurParLogin(login.getText()));
				}
			}
		}
	}

	private class CustomFocusListener extends FocusAdapter
	{
		private String valeur;

		public void focusGained(FocusEvent e)
		{
			if (components.containsValue((JTextField) e.getSource()))
			{
				JTextField field = (JTextField) e.getSource();
				for (Entry<String, JTextField> entry : components.entrySet())
				{
					if (field == entry.getValue())
					{
						if (field.getText().equals(entry.getKey()))
						{
							valeur = entry.getKey();
							if (field instanceof JPasswordField)
								((JPasswordField) field).setEchoChar('*');
							field.setText("");
							field.setForeground(Color.BLACK);
						}
					}
				}
			}
		}

		public void focusLost(FocusEvent e)
		{
			JTextField j = (JTextField) e.getSource();
			if (j.getText().equals(""))
			{
				j.setText(valeur);
				j.setForeground(Color.GRAY);
				if (j instanceof JPasswordField)
					((JPasswordField) j).setEchoChar((char) 0);
			}
		}
	}
}
