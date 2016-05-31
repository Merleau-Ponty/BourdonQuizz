package metier;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe métier correspondant à la table JOUEUR
 * @author BourdonQuizz
 */
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

	/**
	 * Constructeur permettant de renseigner tous les attributs de la classe
	 * @param nom nom du joueur
	 * @param prenom prénom du joueur
	 * @param telephone numéro de téléphone du joueur
	 * @param email adresse mail du joueur
	 * @param service service du joueur (Informatique, Comptabilité etc.)
	 * @param login nom d'utilisateur du joueur
	 * @param mot_de_passe mot de passe du joueur
	 * @param type_utilisateur désigne le "rang" du joueur (administrateur ou membre)
	 */
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
	
	/**
	 * Constructeur vide n'initialisant aucun attribut
	 */
	public Joueur()
	{}
	
	// Méthodes
	/**
	 * Méthode permettant de créer un objet Joueur à partir d'un objet ResultSet (et donc d'un résultat de requête SQL)
	 * @param r objet ResultSet provenant de la classe JoueurDAO
	 * @return un objet Joueur comportant toutes les données de l'objet ResultSet
	 * @throws SQLException
	 * @see ResultSet
	 * @see JoueurDAO
	 */
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
	/**
	 * Getter de l'identifiant
	 * @return l'identifiant du joueur
	 */
	public int getId_joueur()
	{
		return id_joueur;
	}

	/**
	 * Getter du nom
	 * @return le nom du joueur
	 */
	public String getNom()
	{
		return nom;
	}

	/**
	 * Getter du prénom
	 * @return le prénom du joueur
	 */
	public String getPrenom()
	{
		return prenom;
	}

	/**
	 * Getter du numéro de téléphone
	 * @return le numéro de téléphone du joueur en chaîne de caractères
	 */
	public String getTelephone()
	{
		return telephone;
	}

	/**
	 * Getter de l'adresse mail
	 * @return l'adresse mail du joueur
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * Getter du service
	 * @return le service du joueur
	 */
	public String getService()
	{
		return service;
	}

	/**
	 * Getter du login (nom d'utilisateur du joueur)
	 * @return le login du joueur
	 */
	public String getLogin()
	{
		return login;
	}

	/**
	 * Getter du mot de passe
	 * @return le mot de passe du joueur
	 */
	public String getMot_de_passe()
	{
		return mot_de_passe;
	}

	/**
	 * Getter du type d'utilisateur
	 * @return le type d'utilisateur du joueur
	 */
	public String getType_utilisateur()
	{
		return type_utilisateur;
	}

	/**
	 * Setter de l'identifiant
	 * @param id_joueur identifiant du joueur
	 */
	public void setId_joueur(int id_joueur)
	{
		this.id_joueur = id_joueur;
	}

	/**
	 * Setter du nom
	 * @param nom nom du joueur
	 */
	public void setNom(String nom)
	{
		this.nom = nom;
	}

	/**
	 * Setter du prénom
	 * @param prenom prénom du joueur
	 */
	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
	}

	/**
	 * Setter du numéro de téléphone
	 * @param telephone numéro de téléphone du joueur
	 */
	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
	}

	/**
	 * Setter de l'adresse mail
	 * @param email adresse mail du joueur
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	/**
	 * Setter du service
	 * @param service service du joueur (chaîne de caractères)
	 */
	public void setService(String service)
	{
		this.service = service;
	}

	/**
	 * Setter du login (nom d'utilisateur)
	 * @param login login du joueur
	 */
	public void setLogin(String login)
	{
		this.login = login;
	}

	/**
	 * Setter du mot de passe
	 * @param mot_de_passe mot de passe du joueur
	 */
	public void setMot_de_passe(String mot_de_passe)
	{
		this.mot_de_passe = mot_de_passe;
	}

	/**
	 * Setter du type d'utilisateur
	 * @param type_utilisateur type d'utilisateur du joueur (administrateur ou membre)
	 */
	public void setType_utilisateur(String type_utilisateur)
	{
		this.type_utilisateur = type_utilisateur;
	}
}
