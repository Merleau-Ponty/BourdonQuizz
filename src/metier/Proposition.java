package metier;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Proposition
{
	private int id_prop;
	private String enonce;
	private Boolean valide;
	
	public Proposition(String enonce, Boolean valide)
	{
		this.enonce = enonce;
		this.valide = valide;
	}
	
	public Proposition()
	{}
	
	// Méthodes
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
	public String getEnonce()
	{
		return enonce;
	}

	public Boolean getValide()
	{
		return valide;
	}

	public void setId_prop(int id_prop)
	{
		this.id_prop = id_prop;
	}

	public void setEnonce(String enonce)
	{
		this.enonce = enonce;
	}

	public void setValide(Boolean valide)
	{
		this.valide = valide;
	}
}
