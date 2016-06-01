package dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe g�rant tous les acc�s � la base de donn�es
 * Elle permet de se connecter � la base gr�ce aux fichiers properties et d'instancier des objets DAO
 * @author BourdonQuizz
 */
public class DAOFactory
{
	public static String weburl;
	
	private static final String FICHIER_PROPERTIES = "dao.local.properties";
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_WEBURL = "weburl";
	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_NOM_UTILISATEUR = "nomutilisateur";
	private static final String PROPERTY_MOT_DE_PASSE = "motdepasse";

	private String url;
	private String username;
	private String password;

	/**
	 * Unique constructeur de la classe DAOFactory
	 * @param url url vers les fichiers Web
	 * @param username nom d'utilisateur d'acc�s � la base de donn�es
	 * @param password mot de passe pour l'acc�s � la base de donn�es
	 */
	public DAOFactory(String url, String username, String password)
	{
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/**
	 * M�thode statique lisant toutes les propri�t�s du fichier de configuration pour l'acc�s � la base de donn�es et au serveur Web
	 * @return une instance DAOFactory avec les champs de mot de passe, de nom d'utilisateur et d'url instanci�s
	 * @throws DAOConfigurationException lance une exception au niveau de la configuration du fichier properties
	 */
	public static DAOFactory getInstance() throws DAOConfigurationException
	{
		Properties properties = new Properties();
		String url;
		String driver;
		String nomUtilisateur;
		String motDePasse;

		InputStream fichierProperties = null;
		try
		{
			fichierProperties = new FileInputStream("src/" + FICHIER_PROPERTIES);
		}
		catch(FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		if (fichierProperties == null)
		{
			throw new DAOConfigurationException("Le fichier properties " + FICHIER_PROPERTIES + " est introuvable.");
		}
		try
		{
			// Lecture des diff�rentes propri�t�s de configuration
			properties.load(fichierProperties);
			url = properties.getProperty(PROPERTY_URL);
			weburl = properties.getProperty(PROPERTY_WEBURL);
			driver = properties.getProperty(PROPERTY_DRIVER);
			nomUtilisateur = properties.getProperty(PROPERTY_NOM_UTILISATEUR);
			motDePasse = properties.getProperty(PROPERTY_MOT_DE_PASSE);
		}
		catch (IOException e)
		{
			throw new DAOConfigurationException("Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e);
		}
		try
		{
			Class.forName(driver);
		}
		catch (ClassNotFoundException e)
		{
			throw new DAOConfigurationException("Le driver est introuvable dans le classpath.", e);
		}
		return new DAOFactory(url, nomUtilisateur, motDePasse);
	}

	/**
	 * M�thode renvoyant un objet contenant toutes les informations de connexion � la base de donn�es
	 * @return un objet Connection gr�ce � l'url, le nom d'utilisateur et le mot de passe d�j� r�cup�r�s
	 * @throws SQLException lance une exception SQL
	 * @see Connection
	 */
	public Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection(url, username, password);
	}
	
	/**
	 * M�thode renvoyant une instance JoueurDAO pour toutes les requ�tes concernant la table JOUEUR
	 * @return un objet JoueurDAO
	 * @see JoueurDAO
	 */
	public JoueurDAO getJoueur()
	{
		return new JoueurDAO(this);
	}
	
	/**
	 * M�thode renvoyant une instance QuestionDAO pour toutes les requ�tes concernant la table QUESTION
	 * @return un objet QuestionDAO
	 * @see QuestionDAO
	 */
	public QuestionDAO getQuestion()
	{
		return new QuestionDAO(this);
	}
	
	/**
	 * M�thode renvoyant une instance PropositionDAO pour toutes les requ�tes concernant la table PROPOSITION
	 * @return un objet PropositionDAO
	 * @see PropositionDAO
	 */
	public PropositionDAO getProposition()
	{
		return new PropositionDAO(this);
	}
	
	/**
	 * M�thode renvoyant une instance QuizzDAO pour toutes les requ�tes concernant la table QUIZZ
	 * @return un objet QuizzDAO
	 * @see QuizzDAO
	 */
	public QuizzDAO getQuizz()
	{
		return new QuizzDAO(this);
	}
	
	/**
	 * M�thode renvoyant une instance ContenirDAO pour toutes les requ�tes concernant la table CONTENIR
	 * @return un objet ContenirDAO
	 * @see ContenirDAO
	 */
	public ContenirDAO getContenir()
	{
		return new ContenirDAO(this);
	}
	
	/**
	 * M�thode renvoyant une instance TentativeDAO pour toutes les requ�tes concernant la table TENTATIVE
	 * @return un objet TentativeDAO
	 * @see TentativeDAO
	 */
	public TentativeDAO getTentative()
	{
		return new TentativeDAO(this);
	}
	
	/**
	 * M�thode renvoyant une instance ReponseDAO pour toutes les requ�tes concernant la table REPONSE
	 * @return un objet ReponseDAO
	 * @see ReponseDAO
	 */
	public ReponseDAO getReponse()
	{
		return new ReponseDAO(this);
	}
}
