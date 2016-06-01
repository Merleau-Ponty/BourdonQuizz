package dao;

/**
 * Classe g�rant les exceptions relatives � la configuration DAO
 * @author BourdonQuizz
 */
public class DAOConfigurationException extends RuntimeException
{
	/**
	 * Constructeur de l'exception avec un message
	 * @param message valeur du message de l'exception
	 */
	public DAOConfigurationException(String message)
	{
		super(message);
	}
	
	/**
	 * Constructeur de l'exception avec un message et une cause
	 * @param message message en particulier � mentionner
	 * @param cause exception en particulier � mentionner
	 */
	public DAOConfigurationException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	/**
	 * Constructeur de l'exception avec une cause
	 * @param cause exception en particulier � mentionner
	 */
	public DAOConfigurationException(Throwable cause)
	{
		super(cause);
	}
}
