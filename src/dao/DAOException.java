package dao;

/**
 * Classe gérant les exceptions relatives aux divers problèmes DAO
 * @author BourdonQuizz
 */
public class DAOException extends RuntimeException
{
	/**
	 * Constructeur de l'exception avec un message
	 * @param message valeur du message de l'exception
	 */
	public DAOException(String message)
	{
		super(message);
	}
	
	/**
	 * Constructeur de l'exception avec un message et une cause
	 * @param message valeur du message de l'exception
	 * @param cause exception en particulier à mentionner
	 */
	public DAOException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	/**
	 * Constructeur de l'exception avec une cause
	 * @param cause exception en particulier à mentionner
	 */
	public DAOException(Throwable cause)
	{
		super(cause);
	}
}
