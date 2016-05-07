package dao;

import java.sql.SQLException;

public class ContenirDAO extends DAOImpl
{
	public ContenirDAO(DAOFactory fac)
	{
		dao = fac;
	}
	
	public void supprimerQuestions(int idQuizz)
	{
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "delete from CONTENIR where ID_QUIZZ = ?", false, idQuizz);
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
	
	public void ajouterQuestionQuizz(int idQuestion, int idQuizz)
	{
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "insert into CONTENIR values (?, ?)", false, idQuestion, idQuizz);
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
