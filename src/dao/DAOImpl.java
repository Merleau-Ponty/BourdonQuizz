package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.Statement;

public abstract class DAOImpl
{
	protected DAOFactory dao;
	protected PreparedStatement prep;
	protected Connection conn;
	protected ResultSet res;

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
