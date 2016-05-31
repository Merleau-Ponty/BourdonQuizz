package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import metier.Question;

/**
 * Classe contenant toutes les méthodes gérant les requêtes SQL de la table QUESTION
 * @author BourdonQuizz
 */
public class QuestionDAO extends DAOImpl
{
	/**
	 * Constructeur devant uniquement être appelé depuis la classe DAOFactory
	 * @param fac instance de la classe DAOFactory
	 * @see DAOFactory
	 */
	public QuestionDAO(DAOFactory fac)
	{
		dao = fac;
	}
	
	/**
	 * Méthode permettant d'obtenir le nombre total de questions
	 * @return un entier désignant le nombre total de questions trouvées
	 */
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
	
	/**
	 * Méthode permettant d'ajouter une nouvelle question dans la base
	 * @param q l'objet Question désignant la question à ajouter
	 * @return un entier désignant l'identifiant de la question qui vient d'être ajoutée
	 */
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
	
	/**
	 * Méthode permettant de sélectionner toutes les questions existantes
	 * @return un objet ArrayList contenant des objets Question correspondant à chaque question trouvée
	 */
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
	
	/**
	 * Méthode permettant de sélectionner les questions ajoutées à un quizz précis
	 * @param idQuizz identifiant du quizz
	 * @return un objet ArrayList contenant des objets Question correspondant chacun à une question
	 */
	public ArrayList<Question> selecQuestionsSupprimablesQuizz(int idQuizz)
	{
		ArrayList<Question> a = new ArrayList<Question>();
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "select * from QUESTION q inner join CONTENIR c on q.ID_QUESTION = c.ID_QUESTION "
					+ "inner join QUIZZ qu on qu.ID_QUIZZ = c.ID_QUIZZ where qu.ID_QUIZZ = ?", 
					false, idQuizz);
			res = prep.executeQuery();
			while(res.next())
				a.add(Question.genQuestion(res));
		}
		catch(SQLException e)
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
	 * Méthode permettant de sélectionner une question en connaissant son identifiant
	 * @param idQ identifiant de la question
	 * @return un objet Question désignant la question trouvée
	 */
	public Question selecParId(int idQ)
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
	
	/**
	 * Méthode permettant de mettre à jour une question grâce à son identifiant
	 * @param q objet Question contenant les informations de la question à mettre à jour
	 * @param idQ identifiant de la question
	 */
	public void update(Question q, int idQ)
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
