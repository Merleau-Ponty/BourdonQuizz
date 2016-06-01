package metier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Classe métier correspondant à la table QUESTION
 * @author BourdonQuizz
 */
public class Question
{
	private int id_question;
	private String enonce;
	private String photo;
	private String corrige;
	
	/**
	 * Constructeur vide
	 */
	public Question()
	{}
	
	/**
	 * Constructeur initialisant tous les attributs
	 * @param enonce enonce de la question
	 * @param corrige corrigé de la question
	 * @param photo chemin indiquant l'image utilisée pour la question
	 */
	public Question(String enonce, String corrige, String photo)
	{
		this.enonce = enonce;
		this.corrige = corrige;
		this.photo = photo;
	}
	
	// Méthodes
	/**
	 * Méthode permettant de créer un objet Question à partir d'un objet ResultSet (et donc d'un résultat de requête SQL)
	 * @param r objet ResultSet provenant de la classe QuestionDAO
	 * @return un objet Question comportant toutes les données de l'objet ResultSet
	 */
	public static Question genQuestion(ResultSet r)
	{
		Question q = new Question();
		try
		{
			q.setId_question(r.getInt("ID_QUESTION"));
			q.setCorrige(r.getString("CORRIGE"));
			q.setEnonce(r.getString("ENONCE"));
			q.setPhoto(r.getString("PHOTO"));
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return q;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof Question))
			return false;
		Question other = (Question) obj;
		if(corrige == null)
		{
			if(other.corrige != null)
				return false;
		}
		else if(!corrige.equals(other.corrige))
			return false;
		if(enonce == null)
		{
			if(other.enonce != null)
				return false;
		}
		else if(!enonce.equals(other.enonce))
			return false;
		if(id_question != other.id_question)
			return false;
		if(photo == null)
		{
			if(other.photo != null)
				return false;
		}
		else if(!photo.equals(other.photo))
			return false;
		return true;
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(enonce, corrige, id_question, photo);
	}

	// Getters & setters
	/**
	 * Getter de l'énoncé
	 * @return l'énoncé de la question
	 */
	public String getEnonce()
	{
		return enonce;
	}

	/**
	 * Getter de l'identifiant
	 * @return l'identifiant de la question
	 */
	public int getIdQuestion()
	{
		return id_question;
	}

	/**
	 * Getter de la photo
	 * @return retourne une chaîne de caractères indiquant le chemin vers la photo
	 */
	public String getPhoto()
	{
		return photo;
	}

	/**
	 * Getter corrigé
	 * @return le corrigé de la question
	 */
	public String getCorrige()
	{
		return corrige;
	}

	/**
	 * Setter de l'identifiant
	 * @param id_question identifiant de la question
	 */
	public void setId_question(int id_question)
	{
		this.id_question = id_question;
	}

	/**
	 * Setter de l'énoncé
	 * @param enonce énoncé de la question
	 */
	public void setEnonce(String enonce)
	{
		this.enonce = enonce;
	}

	/**
	 * Setter de la photo
	 * @param photo chaîne de caractères indiquant le chemin vers la photo
	 */
	public void setPhoto(String photo)
	{
		this.photo = photo;
	}

	/**
	 * Setter du corrigé
	 * @param corrige corrigé de la question
	 */
	public void setCorrige(String corrige)
	{
		this.corrige = corrige;
	}
}
