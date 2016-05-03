package dao;

import java.sql.SQLException;

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
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			close(res, prep, conn);
		}
		return idQuizz;
	}
}
