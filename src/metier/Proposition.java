package metier;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe m�tier correspondant � la table PROPOSITION
 * @author BourdonQuizz
 */
public class Proposition
{
	private int id_prop;
	private String enonce;
	private Boolean valide;
	
	/**
	 * Constructeur renseignant les valeurs de tous les attributs hormis l'identifiant
	 * @param enonce 
	 * @param valide
	 */
	public Proposition(String enonce, Boolean valide)
	{
		this.enonce = enonce;
		this.valide = valide;
	}
	
	/**
	 * Constructeur vide
	 */
	public Proposition()
	{}
	
	// M�thodes
	/**
	 * M�thode permettant de cr�er un objet Proposition � partir d'un objet ResultSet (et donc d'un r�sultat de requ�te SQL)
	 * @param res objet ResultSet provenant de la classe PropositionDAO
	 * @return un objet Proposition comportant toutes les donn�es de l'objet ResultSet
	 */
	public static Proposition genProposition(ResultSet res)
	{
		Proposition p = new Proposition();
		try
		{
			p.setId_prop(Integer.parseInt(res.getString("ID_PROP")));
			p.setEnonce(res.getString("ENONCE"));
			p.setValide(Integer.parseInt(res.getString("VALIDE")) == 1 ? true : false);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return p;
	}

	// Getters & setters
	/**
	 * Getter de l'enonce
	 * @return l'�nonc� de la proposition
	 */
	public String getEnonce()
	{
		return enonce;
	}

	/**
	 * Getter de la validit�
	 * @return un bool�en indiquant si la proposition doit �tre coch�e ou non
	 */
	public Boolean getValide()
	{
		return valide;
	}

	/**
	 * Setter de l'identifiant
	 * @param id_prop identifiant de la proposition
	 */
	public void setId_prop(int id_prop)
	{
		this.id_prop = id_prop;
	}

	/**
	 * Setter de l'�nonc�
	 * @param enonce �nonc� de la proposition
	 */
	public void setEnonce(String enonce)
	{
		this.enonce = enonce;
	}

	/**
	 * Setter de la validit�
	 * @param valide un bool�en indiquant si la proposition doit �tre coch�e ou non
	 */
	public void setValide(Boolean valide)
	{
		this.valide = valide;
	}
}
