package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import storages.IStorage;
import storages.Storage;
import user.User;

/**
 * Класс предназначен для работы с базой данных.
 */
public abstract class Database extends Storage implements IStorage
{
	private String m_JDBC_Driver;
	private Connection m_connection;
	private Statement m_statement;
	private ResultSet m_resultSet;
	  
	/**
	 * Конструктор класса Database.
	 */
	public Database(String a_DatabaseName, String a_JDBCDriver) 
	{
		super (a_DatabaseName);
		m_JDBC_Driver = a_JDBCDriver;
	}
	  
	@Override
	public void setStorage () throws ClassNotFoundException, SQLException 
	{ 
		m_connection = null; 
		m_statement = null;  
		Class.forName(m_JDBC_Driver);      
		m_connection = DriverManager.getConnection(m_storageName, "sa", ""); 
		m_statement = m_connection.createStatement(); 		
	 }
	  
	@Override
	public void createStorageObject() throws SQLException
	{
		String sql = "CREATE TABLE IF NOT EXISTS USERS (id INT NOT NULL AUTO_INCREMENT (0, 1) PRIMARY KEY, "
		+ "Name varchar(255) NOT NULL, Surname varchar(255) NOT NULL, Age INT NOT NULL, isActive BOOLEAN NOT NULL)"; 
		m_statement.executeUpdate(sql);
	 }
	
	@Override
	public void updateStorageObject() throws SQLException 
	{
		String sql = "DROP TABLE IF EXISTS USERS"; 
		m_statement.executeUpdate(sql);
		createStorageObject();
	}
	
	@Override
	public int addUser(User a_user) throws SQLException 
	{
		String insertingRequestPart = "INSERT INTO Users VALUES (";
		String userRequestPart = ", '" + a_user.getName() + "', '" + a_user.getSurname() + "', " + a_user.getAge() + ", " + a_user.isActive() + ")";
		String addingReguest;
		if (a_user.getId() == -1)
			addingReguest = insertingRequestPart + "DEFAULT" + userRequestPart; 
		else
			addingReguest = insertingRequestPart + a_user.getId() + userRequestPart; 
		
		m_statement.executeUpdate(addingReguest, Statement.RETURN_GENERATED_KEYS);	
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
		String sql = "UPDATE Users SET NAME='" + a_user.getName() + "', SURNAME = '" + a_user.getSurname() 
		+  "', AGE = " + a_user.getAge() + ", ISACTIVE = " + a_user.isActive() + " WHERE ID=" + a_user.getId();
		m_statement.executeUpdate(sql);
	}
	  
	@Override
	public void deleteUser(int a_id) throws SQLException 
	{ 
		String sql = "DELETE FROM Users WHERE ID=" + a_id;
		m_statement.executeUpdate(sql);
	}
	
	@Override
	public void deleteAllUsers() throws SQLException 
	{ 
		String sql = "DELETE FROM Users";
		m_statement.executeUpdate(sql);
	}
	
	
	@Override
	public List <User> getUsersDataSet(boolean a_sorting, boolean a_usersAreDeleted) throws SQLException 
	{
		List <User> users = new ArrayList <>();
		String selectingRequest = "SELECT id, Name, Surname, Age, isActive FROM Users";
		if (a_sorting)  m_resultSet = m_statement.executeQuery(selectingRequest + "order by id"); else
			m_resultSet = m_statement.executeQuery(selectingRequest);
		
	    while (m_resultSet.next())
	    {
			if (m_resultSet != null) 
			{	
				User user = new User();
				user.setId(m_resultSet.getInt("id"));
				user.setName(m_resultSet.getString("Name"));
				user.setSurname(m_resultSet.getString("Surname"));
				user.setAge(m_resultSet.getInt("Age"));
				user.setIsActive(m_resultSet.getBoolean("isActive"));
				users.add(user);
			} else 
				return null;
	    }
		return users;
	  }

	@Override
	public void closeStorage() throws SQLException 
	{	
		if (m_resultSet != null) m_resultSet.close();
		if (m_statement != null) m_statement.close(); 
		if (m_connection != null) m_connection.close();
	}
}