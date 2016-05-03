package dao;

import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public ArrayList<Question> selecToutesQuestions()
	{
		ArrayList<Question> a = new ArrayList<Question>();
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "select * from QUESTION", false);
			res = prep.executeQuery();
			while(res.next())
			{
				a.add(Question.genQuestion(res));
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
		return a;
 	}
	
	public Question selecQuestionParId(int idQ)
	{
		Question q = null;
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "select * from QUESTION where ID_QUESTION = ?", false, idQ);
			res = prep.executeQuery();
			if(res.next())
				q = Question.genQuestion(res);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(res, prep, conn);
		}
		return q;
	}
	
	public void updateQuestion(Question q, int idQ)
	{
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "update QUESTION set ENONCE = ?, PHOTO = ?, CORRIGE = ? where ID_QUESTION = ?", 
					false, q.getEnonce(), q.getPhoto(), q.getCorrige(), idQ);
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
