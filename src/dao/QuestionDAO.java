package dao;

import java.sql.SQLException;

import metier.Question;

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
	
	public Integer enregistrerQuestion(Question q)
	{
		Integer index = null;
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "insert into QUESTION(ENONCE, PHOTO, CORRIGE) values (?, ?, ?)", true, q.getEnonce(), q.getPhoto(), q.getCorrige());
			prep.executeUpdate();
			res = prep.getGeneratedKeys();
			if(res.next())
				index = res.getInt(1);
		}
		catch(SQLException e)
		{
			throw new DAOException(e);
		}
		finally
		{
			close(res, prep, conn);
		}
		return index;
	}
}
