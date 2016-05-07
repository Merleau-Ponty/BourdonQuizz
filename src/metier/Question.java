package metier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Question
{
	private int id_question;
	private String enonce;
	private String photo;
	private String corrige;
	
	public Question()
	{}
	
	public Question(String enonce, String corrige, String photo)
	{
		this.enonce = enonce;
		this.corrige = corrige;
		this.photo = photo;
	}
	
	// Méthodes
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
	public String getEnonce()
	{
		return enonce;
	}

	public int getIdQuestion()
	{
		return id_question;
	}

	public String getPhoto()
	{
		return photo;
	}

	public String getCorrige()
	{
		return corrige;
	}

	public void setId_question(int id_question)
	{
		this.id_question = id_question;
	}

	public void setEnonce(String enonce)
	{
		this.enonce = enonce;
	}

	public void setPhoto(String photo)
	{
		this.photo = photo;
	}

	public void setCorrige(String corrige)
	{
		this.corrige = corrige;
	}
	
	
}
