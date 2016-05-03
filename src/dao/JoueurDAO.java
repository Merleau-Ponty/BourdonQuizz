package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import metier.Joueur;

public class JoueurDAO extends DAOImpl
{	
	public JoueurDAO(DAOFactory fac)
	{
		dao = fac;
	}
	
	public void insererJoueur(Joueur j) throws DAOException
	{
		Integer index = null;
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "insert into JOUEUR(LOGIN, MOT_DE_PASSE, NOM, PRENOM, SERVICE, TELEPHONE, TYPE_UTILISATEUR) values (?, ?, ?, ?, ?, ?, ?)", 
					true, j.getLogin(), j.getMot_de_passe(), j.getNom(), j.getPrenom(), j.getService(), j.getTelephone(), j.getType_utilisateur());
			prep.executeUpdate();
			res = prep.getGeneratedKeys();
			if(res.next())
				index = res.getInt(1);
			j.setId_joueur(index);
		}
		catch(SQLException e)
		{
			throw new DAOException(e);
		}
		finally
		{
			close(res, prep, conn);
		}
	}
	
	public Joueur selecJoueurParLogin(String login)
	{
		Joueur j = null;
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "select * from JOUEUR where LOGIN = ?", false, login);
			res = prep.executeQuery();
			if(res.next())
				j = Joueur.genJoueur(res);
		}
		catch(SQLException e)
		{
			throw new DAOException(e);
		}
		finally
		{
			close(res, prep, conn);
		}
		return j;
	}
	
	public String inscrireJoueur(ArrayList<String> champs)
	{
		String s;
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "insert into JOUEUR(NOM, PRENOM, TELEPHONE, EMAIL, SERVICE, LOGIN, MOT_DE_PASSE, TYPE_UTILISATEUR) values (?, ?, ?, ?, ?, ?, ?, ?)", 
					false, champs.get(0), champs.get(1), champs.get(2), champs.get(3), champs.get(4), champs.get(5), champs.get(6), "membre");
			prep.executeUpdate();
			s = champs.get(5);
		}
		catch(SQLException e)
		{
			throw new DAOException(e);
		}
		finally
		{
			close(res, prep, conn);
		}
		return s;
	}
	
	public boolean seConnecter(String login, String mdp)
	{
		boolean valide = false;
		try
		{
			conn = dao.getConnection();
			prep = initialisationRequetePreparee(conn, "select * from JOUEUR where LOGIN = ? and MOT_DE_PASSE = ?", false, login, mdp);
			res = prep.executeQuery();
			if(res.next())
				valide = true;
		}
		catch(SQLException e)
		{
			throw new DAOException(e);
		}
		finally
		{
			close(res, prep, conn);
		}
		return valide;
	}
}
