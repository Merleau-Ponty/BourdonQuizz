package metier;

import java.util.Date;

public class Tentative
{
	private int id_tentative;
	private Date date_tentative;
	private Integer score;
	
	public Tentative(Date date, int score)
	{
		date_tentative = date;
		this.score = score;
	}

	public Date getDate_tentative()
	{
		return date_tentative;
	}

	public Integer getScore()
	{
		return score;
	}
}
