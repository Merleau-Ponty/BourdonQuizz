package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Classe abstraite contenant différentes méthodes ayant attrait aux bases de données
 * Cette classe sert de base à toutes les autres classes DAO (classe mère)
 * @author BourdonQuizz
 */
public abstract class DAOImpl
{
	protected DAOFactory dao;
	protected PreparedStatement prep;
	protected Connection conn;
	protected ResultSet res;

	/**
	 * Méthode permettant d'exécuter une requête préparée dans la base de données
	 * @param conn objet Connection pour l'interactivité avec la base de données
	 * @param sql requête SQL à exécuter
	 * @param returnGeneratedKeys booléen énoncant si l'on veut récupérer les identifiants générés par l'insertion
	 * @param objets 
	 * @return une instance de requête préparée PreparedStatement
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
	 * Méthode permettant d'exécuter une requête préparée dans la base de données
	 * @param conn objet Connection pour l'interactivité avec la base de données
	 * @param sql requête SQL à exécuter 
	 * @param returnGeneratedKeys booléen énoncant si l'on veut récupérer les identifiants générés par l'insertion
	 * @return une instance de requête préparée PreparedStatement
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
	 * Méthode 
	 * @param resultSet
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
				System.out.println("Échec de la fermeture du ResultSet : " + e.getMessage());
			}
		}
	}

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
				System.out.println("Échec de la fermeture du Statement : " + e.getMessage());
			}
		}
	}

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
				System.out.println("Échec de la fermeture de la connexion : " + e.getMessage());
			}
		}
	}

	protected static void close(Statement statement, Connection connexion)
	{
		close(statement);
		close(connexion);
	}

	protected static void close(ResultSet resultSet, Statement statement, Connection connexion)
	{
		close(resultSet);
		close(statement);
		close(connexion);
	}
}
