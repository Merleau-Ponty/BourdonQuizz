package metier;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.lang.model.element.NestingKind;

public class Joueur
{
	private int id_joueur;
	private String nom;
	private String prenom;
	private String telephone;
	private String email;
	private String service;
	private String login;
	private String mot_de_passe;
	private String type_utilisateur;

	public Joueur(String nom, String prenom, String telephone, String email, String service, String login, String mot_de_passe, String type_utilisateur)
	{
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.email = email;
		this.service = service;
		this.login = login;
		this.mot_de_passe = mot_de_passe;
		this.type_utilisateur = type_utilisateur;
	}
	
	public Joueur()
	{}
	
	// Méthodes
	public static Joueur genJoueur(ResultSet r) throws SQLException
	{
		Joueur j = new Joueur();
		j.setId_joueur(Integer.parseInt(r.getString("ID_JOUEUR")));
		j.setEmail(r.getString("EMAIL"));
		j.setLogin(r.getString("LOGIN"));
		j.setMot_de_passe(r.getString("MOT_DE_PASSE"));
		j.setNom(r.getString("NOM"));
		j.setPrenom(r.getString("PRENOM"));
		j.setService(r.getString("SERVICE"));
		j.setTelephone(r.getString("TELEPHONE"));
		j.setType_utilisateur(r.getString("TYPE_UTILISATEUR"));
		return j;
	}

	// Getters & setters
	public int getId_joueur()
	{
		return id_joueur;
	}

	public String getNom()
	{
		return nom;
	}

	public String getPrenom()
	{
		return prenom;
	}

	public String getTelephone()
	{
		return telephone;
	}

	public String getEmail()
	{
		return email;
	}

	public String getService()
	{
		return service;
	}

	public String getLogin()
	{
		return login;
	}

	public String getMot_de_passe()
	{
		return mot_de_passe;
	}

	public String getType_utilisateur()
	{
		return type_utilisateur;
	}

	public void setId_joueur(int id_joueur)
	{
		this.id_joueur = id_joueur;
	}

	public void setNom(String nom)
	{
		this.nom = nom;
	}

	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
	}

	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setService(String service)
	{
		this.service = service;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public void setMot_de_passe(String mot_de_passe)
	{
		this.mot_de_passe = mot_de_passe;
	}

	public void setType_utilisateur(String type_utilisateur)
	{
		this.type_utilisateur = type_utilisateur;
	}
}
