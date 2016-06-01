package dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import metier.Tentative;

/**
 * Classe contenant toutes les méthodes gérant les requêtes SQL de la table TENTATIVE
 * @author BourdonQuizz
 */
public class TentativeDAO extends DAOImpl
{
	/**
	 * Constructeur devant uniquement être appelé depuis la classe DAOFactory
	 * @param dao instance de la classe DAOFactory
	 * @see DAOFactory
	 */
	public TentativeDAO(DAOFactory dao)
	{
		this.dao = dao;
	}
	
	/**
	 * Méthode permettant de créer une nouvelle tentative
	 * @param t objet Tentative désignant la tentative
	 * @param idJoueur identifiant du joueur faisant la tentative
	 * @param idQuizz identifiant du quizz de la tentative
	 * @return un entier désignant l'identifiant de la tentative ajoutée
	 */
	public int ajouter(Tentative t, int idJoueur, int idQuizz)
	{
		try
		{
			conn = dao.getConnection();
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t.getDate_tentative());
			prep = initialisationRequetePreparee(conn, "insert into TENTATIVE values (?, ?, ?, ?, ?)", 
					true, idJoueur, 100, idQuizz, date, t.getScore());
			prep.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(prep, conn);
		}
		return getLastIdTentative();
	}
	
	/**
	 * Méthode permettant de récupérer l'identifiant de la dernière tentative ajoutée
	 * @return un entier désignant l'identifiant de la dernière tentative ajoutée
	 */
	public int getLastIdTentative()
	{
		Integer res = null;
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "select count(*) from TENTATIVE", false);
			this.res = prep.executeQuery();
			if(this.res.next())
				res = this.res.getInt(1);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(this.res, prep, conn);
		}
		return res;
	}
	
	/**
	 * Méthode permettant de mettre à jour le score d'une tentative en l'incrémentant
	 * @param idTentative identifiant de la tentative
	 */
	public void updateScore(int idTentative)
	{
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "update TENTATIVE set SCORE = SCORE + 1 where ID_TENTATIVE = ?", false, idTentative);
			prep.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			close(prep, conn);
		}
	}
	
	/**
	 * Méthode retournant le nombre total de tentatives
	 * @return nombre total de tentatives
	 */
	public int selecNbTentatives()
	{
		Integer nbT = null;
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "select count(*) from TENTATIVE", false);
			res = prep.executeQuery();
			if(res.next())
				nbT = res.getInt(1);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			close(res, prep, conn);
		}
		return nbT;
	}
	
	/**
	 * Méthode permettant d'obtenir le score moyen des tentatives
	 * @return le score moyen
	 */
	public double scoreMoyen()
	{
		Double scoreMoyen = null;
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "select avg(SCORE) from TENTATIVE", false);
			res = prep.executeQuery();
			if(res.next())
				scoreMoyen = res.getDouble(1);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			close(res, prep, conn);
		}
		return scoreMoyen;
	}
}
