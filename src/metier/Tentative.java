package metier;

import java.util.Date;

/**
 * Classe métier correspondant à la table TENTATIVE
 * @author BourdonQuizz
 */
public class Tentative
{
	private int id_tentative;
	private Date date_tentative;
	private Integer score;
	
	/**
	 * Constructeur initialisant tous les attributs sauf l'identifiant
	 * @param date objet Date désignant la date de la tentative
	 * @param score entier désignant le score de la tentative
	 */
	public Tentative(Date date, int score)
	{
		date_tentative = date;
		this.score = score;
	}

	/**
	 * Getter de la date
	 * @return un objet Date correspondant à la date de la tentative
	 */
	public Date getDate_tentative()
	{
		return date_tentative;
	}

	/**
	 * Getter du score
	 * @return un entier correspondant au score de la tentative
	 */
	public Integer getScore()
	{
		return score;
	}
}
