package dao;

import java.sql.SQLException;

import gui.MenuChoix;

/**
 * Classe contenant toutes les m�thodes g�rant les requ�tes SQL de la table REPONSE
 * @author BourdonQuizz
 */
public class ReponseDAO extends DAOImpl
{
	/**
	 * Constructeur devant uniquement �tre appel� depuis la classe DAOFactory
	 * @param dao instance de la classe DAOFactory
	 * @see DAOFactory
	 */
	public ReponseDAO(DAOFactory dao)
	{
		this.dao = dao;
	}
	
	/** 
	 * M�thode permettant d'ajouter une nouvelle r�ponse
	 * @param idQ identifiant de la question de la r�ponse
	 * @param idTentative identifiant de la tentative concernant la r�ponse
	 * @param valeur bool�en �tant � true si la r�ponse est coch�e, sinon false
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
