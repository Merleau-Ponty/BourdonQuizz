package dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import metier.Tentative;

public class TentativeDAO extends DAOImpl
{
	public TentativeDAO(DAOFactory dao)
	{
		this.dao = dao;
	}
	
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
}
