package databases;

/**
 * Класс предназначен для работы с базой данных H2.
 */
public class H2Database extends Database
{
	/**
	 * Конструктор класса H2Database.
	 * @param a_databaseName - имя базы данных H2
	 */
	public H2Database (String a_databaseName) 
	{
		super (a_databaseName, "org.h2.Driver");
	}
}