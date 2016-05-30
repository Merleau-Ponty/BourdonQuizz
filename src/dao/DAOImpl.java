package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Classe abstraite contenant diff�rentes m�thodes ayant attrait aux bases de donn�es
 * Cette classe sert de base � toutes les autres classes DAO (classe m�re)
 * @author BourdonQuizz
 */
public abstract class DAOImpl
{
	protected DAOFactory dao;
	protected PreparedStatement prep;
	protected Connection conn;
	protected ResultSet res;

	/**
	 * M�thode permettant d'ex�cuter une requ�te pr�par�e dans la base de donn�es
	 * @param conn objet Connection pour l'interactivit� avec la base de donn�es
	 * @param sql requ�te SQL � ex�cuter
	 * @param returnGeneratedKeys bool�en �noncant si l'on veut r�cup�rer les identifiants g�n�r�s par l'insertion
	 * @param objets 
	 * @return une instance de requ�te pr�par�e PreparedStatement
	 * @throws SQLException
	 * @see PreparedStatement
	 */
	protected static PreparedStatement initialisationRequetePreparee(Connection conn, String sql,
			boolean returnGeneratedKeys, Object... objets) throws SQLException
	{
		PreparedStatement req = conn.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		for (int i = 0; i < objets.length; i++)
		{
			req.setObject(i + 1, objets[i]);
		}
		return req;
	}
	
	/**
	 * M�thode permettant d'ex�cuter une requ�te pr�par�e dans la base de donn�es
	 * @param conn objet Connection pour l'interactivit� avec la base de donn�es
	 * @param sql requ�te SQL � ex�cuter 
	 * @param returnGeneratedKeys bool�en �noncant si l'on veut r�cup�rer les identifiants g�n�r�s par l'insertion
	 * @return une instance de requ�te pr�par�e PreparedStatement
	 * @throws SQLException
	 * @see PreparedStatement
	 */
	protected static PreparedStatement initialisationRequetePreparee(Connection conn, String sql,
			boolean returnGeneratedKeys) throws SQLException
	{
		PreparedStatement req = conn.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		return req;
	}

	/**
	 * M�thode permettant de fermer un objet ayant �t� utilis� pour l'ex�cution de requ�tes SQL
	 * @param resultSet objet ResultSet utilis� pour r�cup�rer les r�sultats des requ�tes
	 * @see ResultSet
	 */
	protected static void close(ResultSet resultSet)
	{
		if (resultSet != null)
		{
			try
			{
				resultSet.close();
			}
			catch (SQLException e)
			{
				System.out.println("�chec de la fermeture du ResultSet : " + e.getMessage());
			}
		}
	}

	/**
	 * M�thode permettant de fermer un objet ayant �t� utilis� pour l'ex�cution de requ�tes SQL
	 * @param statement objet Statement ayant �t� utilis� pour la requ�te pr�par�e
	 * @see Statement
	 */
	protected static void close(Statement statement)
	{
		if (statement != null)
		{
			try
			{
				statement.close();
			}
			catch (SQLException e)
			{
				System.out.println("�chec de la fermeture du Statement : " + e.getMessage());
			}
		}
	}

	/**
	 * M�thode permettant de fermer un objet ayant �t� utilis� pour l'ex�cution de requ�tes SQL
	 * @param connexion objet Connection permettant la connexion � la base de donn�es
	 * @see Connection
	 */
	public static void close(Connection connexion)
	{
		if (connexion != null)
		{
			try
			{
				connexion.close();
			}
			catch (SQLException e)
			{
				System.out.println("�chec de la fermeture de la connexion : " + e.getMessage());
			}
		}
	}

	/**
	 * M�thode permettant de fermer plusieurs objets ayant �t� utilis�s pour l'ex�cution de requ�tes SQL
	 * @param statement objet Statement ayant �t� utilis� pour la requ�te pr�par�e
	 * @param connexion objet Connection permettant la connexion � la base de donn�es
	 * @see Statement Connection 
	 */
	protected static void close(Statement statement, Connection connexion)
	{
		close(statement);
		close(connexion);
	}

	/**
	 * M�thode permettant de fermer plusieurs objets ayant �t� utilis�s pour l'ex�cution de requ�tes SQL
	 * @param resultSet objet Statement ayant �t� utilis� pour la requ�te pr�par�e
	 * @param statement objet Statement ayant �t� utilis� pour la requ�te pr�par�e
	 * @param connexion objet Connection permettant la connexion � la base de donn�es
	 * @see ResultSet Statement Connection
	 */
	protected static void close(ResultSet resultSet, Statement statement, Connection connexion)
	{
		close(resultSet);
		close(statement);
		close(connexion);
	}
}
