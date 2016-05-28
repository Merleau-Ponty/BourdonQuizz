package dao;

import java.sql.SQLException;

/**
 * Classe g�rant toutes les requ�tes SQL ayant attrait � la table CONTENIR
 * @author BourdonQuizz
 */
public class ContenirDAO extends DAOImpl
{
	/**
	 * Constructeur de la classe ContenirDAO
	 * @param fac le lien avec la classe DAOFactory
	 * @see DAOFactory
	 */
	public ContenirDAO(DAOFactory fac)
	{
		dao = fac;
	}
	
	/**
	 * M�thode supprimant toutes les questions d'un quizz
	 * @param idQuizz quizz auquel on va supprimer toutes les questions
	 */
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
	
	/**
	 * M�thode permettant d'ajouter une question � un quizz
	 * @param idQuestion identifiant de la question � ajouter
	 * @param idQuizz identifiant du quizz auquel ajouter la question
	 */
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
	
	/**
	 * M�thode permettant d'obtenir le nombre de questions d'un quizz
	 * @param idQuizz identifiant du quizz
	 * @return un entier d�signant le nombre de questions du quizz
	 */
	public Integer selecNbQuestionsQuizz(int idQuizz)
	{
		Integer resultat = null;
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "select count(*) from CONTENIR where ID_QUIZZ = ?", false, idQuizz);
			this.res = prep.executeQuery();
			if(res.next())
				resultat = res.getInt(1);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			close(res, prep, conn);
		}
		return resultat;
	}
	
	
}
