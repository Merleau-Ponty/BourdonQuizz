package dao;

/**
 * Classe gérant les exceptions relatives à la configuration DAO
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
	 * @param message message en particulier à mentionner
	 * @param cause exception en particulier à mentionner
	 */
	public DAOConfigurationException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	/**
	 * Constructeur de l'exception avec une cause
	 * @param cause exception en particulier à mentionner
	 */
	public DAOConfigurationException(Throwable cause)
	{
		super(cause);
	}
}
