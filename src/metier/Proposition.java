package metier;

public class Proposition
{
	public int id_prop;
	public String enonce;
	public Boolean valide;
	
	public Proposition(String enonce, Boolean valide)
	{
		this.enonce = enonce;
		this.valide = valide;
	}

	public String getEnonce()
	{
		return enonce;
	}

	public Boolean getValide()
	{
		return valide;
	}
}
