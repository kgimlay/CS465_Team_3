/**
*
*/

import java.net.Socket;

/**
*
*/
public class ConnectionThread implements runnable
{
	/**
	*
	*/
	private Socket connection;

	/**
	*
	*/
	public ConnectionThread()
	{
	 Socket connection = null;
	}

	/**
	*
	*/
	Boolean acceptConnection()
	{
		if(connection == null)
		{
			connection = new ConnectionThread();
			return true;
		}
		else
		{
			return false;
		}

	}

	/**
	*
	*/
	Boolean openConnection()
	{

	}

	/**
	*
	*/
	Boolean closeConnection()
	{

	}

}
