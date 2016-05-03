package metier;

import java.sql.ResultSet;
import java.sql.SQLException;

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
