package gui.accueil;

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
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel lblPageDinscription = new JLabel("Inscription");
		lblPageDinscription.setFont(new Font("Roboto", Font.PLAIN, 40));
		gbc.insets.bottom = 10;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(lblPageDinscription, gbc);

		nom = new JTextField("Nom");
		gbc.insets.bottom = 5;
		gbc.gridy += 1;
		add(nom, gbc);
		nom.setColumns(10);

		prenom = new JTextField("Prénom");
		gbc.gridy += 1;
		add(prenom, gbc);
		prenom.setColumns(10);

		telephone = new JTextField("Téléphone");
		gbc.gridy += 1;
		add(telephone, gbc);
		telephone.setColumns(10);

		email = new JTextField("E-mail");
		gbc.gridy += 1;
		add(email, gbc);
		email.setColumns(10);

		service = new JTextField("Service");
		gbc.gridy += 1;
		add(service, gbc);
		service.setColumns(10);

		login = new JTextField("Login");
		gbc.gridy += 1;
		add(login, gbc);
		login.setColumns(10);

		mdp = new JPasswordField("Mot de passe");
		gbc.gridy += 1;
		gbc.insets.bottom = 10;
		add(mdp, gbc);
		mdp.setColumns(10);

		valider = new JButton("Valider");
		gbc.insets.bottom = 0;
		gbc.gridy += 1;
		add(valider, gbc);
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
