package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import metier.Question;

public class QuizzDAO extends DAOImpl
{
	public QuizzDAO(DAOFactory fac)
	{
		dao = fac;
	}

	public int creerQuizz()
	{
		Integer idQuizz = null;
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "insert into QUIZZ values ()", true);
			prep.executeUpdate();
			res = prep.getGeneratedKeys();
			if(res.next())
				idQuizz = res.getInt(1);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(res, prep, conn);
		}
		return idQuizz;
	}

	public ArrayList<Integer> selectTousQuizz()
	{
		ArrayList<Integer> a = new ArrayList<Integer>();
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "select * from QUIZZ", false);
			res = prep.executeQuery();
			while(res.next())
				a.add(res.getInt(1));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(res, prep, conn);
		}
		return a;
	}

	public ArrayList<Question> selecQuestionsQuizz(int idQuizz)
	{
		ArrayList<Question> a = new ArrayList<Question>();
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "select * from QUESTION where ID_QUESTION in "
					+ "(select ID_QUESTION from CONTENIR where ID_QUIZZ = ?) order by QUESTION.ID_QUESTION asc", false, idQuizz);
			res = prep.executeQuery();
			while(res.next())
				a.add(Question.genQuestion(res));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(res, prep, conn);
		}
		return a;
	}
}
