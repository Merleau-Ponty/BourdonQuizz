package dao;

import java.sql.SQLException;

import gui.MenuChoix;

public class ReponseDAO extends DAOImpl
{
	public ReponseDAO(DAOFactory dao)
	{
		this.dao = dao;
	}
	
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
