package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import metier.Question;

/**
 * Classe contenant toutes les m�thodes g�rant les requ�tes SQL de la table QUESTION
 * @author BourdonQuizz
 */
public class QuestionDAO extends DAOImpl
{
	/**
	 * Constructeur devant uniquement �tre appel� depuis la classe DAOFactory
	 * @param fac instance de la classe DAOFactory
	 * @see DAOFactory
	 */
	public QuestionDAO(DAOFactory fac)
	{
		dao = fac;
	}
	
	/**
	 * M�thode permettant d'obtenir le nombre total de questions
	 * @return un entier d�signant le nombre total de questions trouv�es
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
	 * M�thode permettant d'ajouter une nouvelle question dans la base
	 * @param q l'objet Question d�signant la question � ajouter
	 * @return un entier d�signant l'identifiant de la question qui vient d'�tre ajout�e
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
	 * M�thode permettant de s�lectionner toutes les questions existantes
	 * @return un objet ArrayList contenant des objets Question correspondant � chaque question trouv�e
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
	 * M�thode permettant de s�lectionner les questions ajout�es � un quizz pr�cis
	 * @param idQuizz identifiant du quizz
	 * @return un objet ArrayList contenant des objets Question correspondant chacun � une question
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
	 * M�thode permettant de s�lectionner une question en connaissant son identifiant
	 * @param idQ identifiant de la question
	 * @return un objet Question d�signant la question trouv�e
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
	 * M�thode permettant de mettre � jour une question gr�ce � son identifiant
	 * @param q objet Question contenant les informations de la question � mettre � jour
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
