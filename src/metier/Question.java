package metier;

public class Question
{
	private int id_question;
	private String enonce;
	private String photo;
	private String corrige;
	
	public Question(String enonce, String corrige, String photo)
	{
		this.enonce = enonce;
		this.corrige = corrige;
		this.photo = photo;
	}

	// Getters
	public String getEnonce()
	{
		return enonce;
	}

	public String getPhoto()
	{
		return photo;
	}

	public String getCorrige()
	{
		return corrige;
	}
}
