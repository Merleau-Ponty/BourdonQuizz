package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import metier.Proposition;

/**
 * Classe contenant toutes les méthodes gérant les requêtes SQL de la table PROPOSITION
 * @author BourdonQuizz
 */
public class PropositionDAO extends DAOImpl
{
	/**
	 * Constructeur devant uniquement être appelé depuis la classe DAOFactory
	 * @param fac instance de DAOFactory
	 * @see DAOFactory
	 */
	public PropositionDAO(DAOFactory fac)
	{
		dao = fac;
	}
	
	/**
	 * Méthode permettant d'enregistrer une proposition d'une question
	 * @param p objet Proposition désignant la proposition à ajouter
	 * @param idQ identifiant de la question contenant la proposition
	 */
	public void enregistrerProp(Proposition p, int idQ)
	{
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "insert into PROPOSITION values (?, ?, ?, ?)", false, 
					idQ, 100, p.getEnonce(), p.getValide());
			prep.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(res, prep, conn);
		}
	}
	
	/**
	 * Méthode permettant de sélectionner toutes les propositions d'une question grâce à l'identifiant de la question
	 * @param idQ identifiant de la question
	 * @return un objet ArrayList contenant une ou plusieurs propositions
	 */
	public ArrayList<Proposition> selecPropQuestion(int idQ)
	{
		ArrayList<Proposition> a = new ArrayList<Proposition>();
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "select * from PROPOSITION where ID_QUESTION = ? order by ID_PROP asc", false, idQ);
			res = prep.executeQuery();
			while(res.next())
			{
				a.add(Proposition.genProposition(res));
			}
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
	 * Méthode permettant de mettre à jour une proposition
	 * @param enonce enonce de la proposition à mettre à jour
	 * @param idQ identifiant de la question contenant la proposition
	 * @param valide booléen à mettre à jour désignant la validité de la proposition
	 * @param idProp identifiant de la proposition
	 */
	public void updateProposition(String enonce, int idQ, boolean valide, int idProp)
	{
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "update PROPOSITION set ENONCE = ?, VALIDE = ? where ID_QUESTION = ? and ID_PROP = ?", 
					false, enonce, valide, idQ, idProp);
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
	 * Méthode sélectionnat l'identifiant d'une proposition grâce à son énoncé et l'identifiant de sa question
	 * @param enonce enonce de la proposition
	 * @param idQ identifiant de la question de la proposition
	 * @return un entier désignant l'identifiant de la proposition
	 */
	public int selecIdPropParEnonceEtQuestion(String enonce, int idQ)
	{
		Integer idProp = null;
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "select * from PROPOSITION where ID_QUESTION = ? and ENONCE = ?", false,
					idQ, enonce);
			res = prep.executeQuery();
			if(res.next())
				idProp = Integer.parseInt(res.getString("ID_PROP"));
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(res, prep, conn);
		}
		return idProp;
	}
}
