package dao;

import java.sql.SQLException;

import gui.MenuChoix;

/**
 * Classe contenant toutes les méthodes gérant les requêtes SQL de la table REPONSE
 * @author BourdonQuizz
 */
public class ReponseDAO extends DAOImpl
{
	/**
	 * Constructeur devant uniquement être appelé depuis la classe DAOFactory
	 * @param dao instance de la classe DAOFactory
	 * @see DAOFactory
	 */
	public ReponseDAO(DAOFactory dao)
	{
		this.dao = dao;
	}
	
	/** 
	 * Méthode permettant d'ajouter une nouvelle réponse
	 * @param idQ identifiant de la question de la réponse
	 * @param idTentative identifiant de la tentative concernant la réponse
	 * @param valeur booléen étant à true si la réponse est cochée, sinon false
	 */
	public void ajouter(int idQ, int idTentative, boolean valeur)
	{
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "insert into REPONSE(ID_JOUEUR, ID_QUESTION, ID_TENTATIVE, VALEUR) "
					+ "values (?, ?, ?, ?)", false, MenuChoix.joueur.getId_joueur(), idQ, idTentative, valeur);
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
