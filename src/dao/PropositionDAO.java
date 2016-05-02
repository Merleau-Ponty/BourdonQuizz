package dao;

import java.sql.SQLException;

import metier.Proposition;

public class PropositionDAO extends DAOImpl
{
	public PropositionDAO(DAOFactory fac)
	{
		dao = fac;
		prep = null;
		conn = null;
		res = null;
	}
	
	public void enregistrerProp(Proposition p, int idQ)
	{
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "insert into PROPOSITION(ID_QUESTION, ENONCE, VALIDE) values (?, ?, ?)", false, 
					idQ, p.getEnonce(), p.getValide());
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
}
