package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import metier.Joueur;

/**
 * Classe gérant toutes les méthodes concernant les requêtes de la table JOUEUR
 * @author BourdonQuizz
 */
public class JoueurDAO extends DAOImpl
{	
	/**
	 * Constructeur devant être appelé uniquement depuis la classe DAOFactory
	 * @param fac instance de DAOFactory
	 * @see DAOFactory
	 */
	public JoueurDAO(DAOFactory fac)
	{
		dao = fac;
	}
	
	/**
	 * Méthode permettant d'ajouter un joueur à la base de données
	 * @param j instance de Joueur désignant le joueur à ajouter
	 * @throws DAOException lance une exception lors de l'exécution d'une méthode DAO
	 * @see Joueur
	 */
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
	
	/**
	 * Méthode permettant d'obtenir les informations d'un joueur en connaissant son login
	 * @param login login du joueur
	 * @return un objet Joueur retourné par la requête SQL
	 * @see Joueur
	 */
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
	
	/**
	 * Méthode inscrivant un joueur dans la base de données
	 * @param champs un objet ArrayList contenant tous les champs du joueur
	 * @return le login du joueur inscrit
	 */
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
	
	/**
	 * Méthode déterminant si les identifiants de connexion sont corrects
	 * @param login nom d'utilisateur de la personne souhaitant se connecter
	 * @param mdp mot de passe permettant de se connecter
	 * @return true si le login et le mot de passe coincident, sinon false
	 */
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
