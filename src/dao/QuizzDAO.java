package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import metier.Question;

/**
 * Classe contenant toutes les méthodes gérant les requêtes SQL de la table QUIZZ
 * @author BourdonQuizz
 */
public class QuizzDAO extends DAOImpl
{
	/**
	 * Constructeur devant uniquement être appelé depuis la classe DAOFactory
	 * @param fac instance de la classe DAOFactory
	 * @see DAOFactory
	 */
	public QuizzDAO(DAOFactory fac)
	{
		dao = fac;
	}

	/**
	 * Méthode permettant de créer un nouveau quizz
	 * @return un entier désignant l'identifiant du quizz créé
	 */
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

	/**
	 * Méthode permettant de sélectionner tous les identifiants de quizz existants
	 * @return un objet ArrayList contenant des entiers désignant tous les identifiants des quizzs
	 */
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

	/**
	 * Méthode permettant de sélectionner toutes les questions d'un quizz
	 * @param idQuizz identifiant du quizz concerné
	 * @return un objet ArrayList contenant des objets Question correspondant chacun à une question trouvée
	 */
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
