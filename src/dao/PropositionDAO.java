package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.PrimitiveIterator.OfDouble;

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
	
	public ArrayList<Proposition> selecPropQuestion(int idQ)
	{
		ArrayList<Proposition> a = new ArrayList<Proposition>();
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "select * from PROPOSITION where ID_QUESTION = ?", false, idQ);
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
