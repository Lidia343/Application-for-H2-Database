package databases;

/**
 * Класс предназначен для работы с базой данных.
 */
public class H2Database extends Database
{
	public H2Database (String a_databaseName) 
	{
		super (a_databaseName, "org.h2.Driver");
	}
}