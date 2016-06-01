package dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import metier.Tentative;

/**
 * Classe contenant toutes les m�thodes g�rant les requ�tes SQL de la table TENTATIVE
 * @author BourdonQuizz
 */
public class TentativeDAO extends DAOImpl
{
	/**
	 * Constructeur devant uniquement �tre appel� depuis la classe DAOFactory
	 * @param dao instance de la classe DAOFactory
	 * @see DAOFactory
	 */
	public TentativeDAO(DAOFactory dao)
	{
		this.dao = dao;
	}
	
	/**
	 * M�thode permettant de cr�er une nouvelle tentative
	 * @param t objet Tentative d�signant la tentative
	 * @param idJoueur identifiant du joueur faisant la tentative
	 * @param idQuizz identifiant du quizz de la tentative
	 * @return un entier d�signant l'identifiant de la tentative ajout�e
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
	 * M�thode permettant de r�cup�rer l'identifiant de la derni�re tentative ajout�e
	 * @return un entier d�signant l'identifiant de la derni�re tentative ajout�e
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
	 * M�thode permettant de mettre � jour le score d'une tentative en l'incr�mentant
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
	 * M�thode retournant le nombre total de tentatives
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
	 * M�thode permettant d'obtenir le score moyen des tentatives
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
