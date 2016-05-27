package dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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

	public DAOFactory(String url, String username, String password)
	{
		this.url = url;
		this.username = username;
		this.password = password;
	}

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

	public Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection(url, username, password);
	}
	
	// M�thodes retournant des instances DAO
	public JoueurDAO getJoueur()
	{
		return new JoueurDAO(this);
	}
	
	public QuestionDAO getQuestion()
	{
		return new QuestionDAO(this);
	}
	
	public PropositionDAO getProposition()
	{
		return new PropositionDAO(this);
	}
	
	public QuizzDAO getQuizz()
	{
		return new QuizzDAO(this);
	}
	
	public ContenirDAO getContenir()
	{
		return new ContenirDAO(this);
	}
	
	public TentativeDAO getTentative()
	{
		return new TentativeDAO(this);
	}
	
	public ReponseDAO getReponse()
	{
		return new ReponseDAO(this);
	}
}
