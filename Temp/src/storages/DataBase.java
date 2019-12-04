package storages;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import user.User;  

/**
 * Класс предназначен для работы с базой данных.
 */
public class DataBase implements Storage 
{
	private String m_JDBC_Driver;
	private String m_databaseName;
	  
	private Connection m_connection;
	private Statement m_statement;
	private ResultSet m_resultSet;
	  
    /**
	* Конструктор класса DataBase
	* @param a_databaseName - имя базы данных
	*/
	public DataBase(String a_databaseName) 
	{
		m_databaseName = a_databaseName;
		m_JDBC_Driver = "org.h2.Driver"; 
	}
	  
	@Override
	public void setStorage () throws ClassNotFoundException, SQLException 
	{ 
		m_connection = null; 
		m_statement = null;  
		Class.forName(m_JDBC_Driver);      
		m_connection = DriverManager.getConnection(m_databaseName, "sa", ""); 
		m_statement = m_connection.createStatement(); 		
	 }
	  
	@Override
	public void createStorageObject() throws SQLException
	{
		String m_sql = "CREATE TABLE IF NOT EXISTS USERS (id INT NOT NULL AUTO_INCREMENT (0, 1) PRIMARY KEY, "
		+ "Name varchar(255) NOT NULL, Surname varchar(255) NOT NULL, Age INT NOT NULL, isActive BOOLEAN NOT NULL)"; 
		m_statement.executeUpdate(m_sql);
	 }
	
	@Override
	public void updateStorageObject() throws SQLException 
	{
		String m_sql = "DROP TABLE IF EXISTS USERS"; 
		m_statement.executeUpdate(m_sql);
		createStorageObject();
	}
	
	@Override
	public int addUser(User a_user) throws SQLException 
	{
		String m_insertingRequestPart = "INSERT INTO Users VALUES (";
		String m_userRequestPart = ", '" + a_user.getName() + "', '" + a_user.getSurname() + "', " + a_user.getAge() + ", " + a_user.isActive() + ")";
		String m_addingReguest;
		if (a_user.getId() == -1)
			m_addingReguest = m_insertingRequestPart + "DEFAULT" + m_userRequestPart; 
		else
			m_addingReguest = m_insertingRequestPart + a_user.getId() + m_userRequestPart; 
		
		m_statement.executeUpdate(m_addingReguest, Statement.RETURN_GENERATED_KEYS);	
		m_resultSet = m_statement.getGeneratedKeys();
		if (m_resultSet != null)
		{
			while (m_resultSet.next()) 
			{
				return m_resultSet.getInt(1);
			}
		}
		return -1;
	}
	
	@Override
	public void updateUser(User a_user) throws SQLException 
	{
		String m_sql = "UPDATE Users SET NAME='" + a_user.getName() + "', SURNAME = '" + a_user.getSurname() 
		+  "', AGE = " + a_user.getAge() + ", ISACTIVE = " + a_user.isActive() + " WHERE ID=" + a_user.getId();
		m_statement.executeUpdate(m_sql);
	}
	  
	@Override
	public void deleteUser(int a_id) throws SQLException 
	{ 
		String m_sql = "DELETE FROM Users WHERE ID=" + a_id;
		m_statement.executeUpdate(m_sql);
	}
	
	@Override
	public void deleteAllUsers() throws SQLException 
	{ 
		String m_sql = "DELETE FROM Users";
		m_statement.executeUpdate(m_sql);
	}
	
	
	@Override
	public List <User> getUsersDataSet(boolean a_sorting, boolean a_usersAreDeleted) throws SQLException 
	{
		List <User> m_users = new ArrayList <>();
		String m_selectingRequest = "SELECT id, Name, Surname, Age, isActive FROM Users";
		if (a_sorting)  m_resultSet = m_statement.executeQuery(m_selectingRequest + "order by id"); else
			m_resultSet = m_statement.executeQuery(m_selectingRequest);
		
	    while (m_resultSet.next())
	    {
			if (m_resultSet != null) 
			{	
				User m_user = new User();
				m_user.setId(m_resultSet.getInt("id"));
				m_user.setName(m_resultSet.getString("Name"));
				m_user.setSurname(m_resultSet.getString("Surname"));
				m_user.setAge(m_resultSet.getInt("Age"));
				m_user.setIsActive(m_resultSet.getBoolean("isActive"));
				m_users.add(m_user);
			} else 
				return null;
	    }
		return m_users;
	  }

	@Override
	public void closeStorage() throws SQLException 
	{	
		if (m_resultSet != null) m_resultSet.close();
		if (m_statement != null) m_statement.close(); 
		if (m_connection != null) m_connection.close();
	}
}
