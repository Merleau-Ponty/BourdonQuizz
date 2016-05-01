package dao;

import java.sql.SQLException;

public class QuestionDAO extends DAOImpl
{
	public QuestionDAO(DAOFactory fac)
	{
		dao = fac;
		prep = null;
		conn = null;
		res = null;
	}
	
	public int selecNbQuestions()
	{
		Integer countRes = null;
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "select count(*) from QUESTION", false);
			res = prep.executeQuery();
			if(res.next())
			{
				countRes = res.getInt("count(*)");
			}
		}
		catch(SQLException e)
		{
			throw new DAOException(e);
		}
		finally 
		{
			close(res, prep, conn);
		}
		return countRes;
	}
	
	public void enregistrerQuestion(String libelle, String corrige, String chemImg)
	{
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "insert into QUESTION(ENONCE, PHOTO, CORRIGE) values (?, ?, ?)", false, libelle, chemImg, corrige);
			prep.executeUpdate();
		}
		catch(SQLException e)
		{
			throw new DAOException(e);
		}
		finally
		{
			close(res, prep, conn);
		}
	}
}
