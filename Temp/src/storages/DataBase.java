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
public class DataBase implements Storage {
	
	private String JDBC_Driver;
	private String databaseName;
	  
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	  
    /**
	* Конструктор класса DataBase
	* @param databaseName - имя базы данных
	*/
	public DataBase(String databaseName) {
		this.databaseName = databaseName;
		JDBC_Driver = "org.h2.Driver"; 
	}
	  
	@Override
	public void setStorage () throws ClassNotFoundException, SQLException { 
		connection = null; 
		statement = null;  
		Class.forName(JDBC_Driver);      
		connection = DriverManager.getConnection(databaseName, "sa", ""); 
		statement = connection.createStatement(); 		
	 }
	  
	@Override
	public void createStorageObject() throws SQLException{
		String sql = "CREATE TABLE IF NOT EXISTS USERS (id INT NOT NULL AUTO_INCREMENT (0, 1) PRIMARY KEY, "
		+ "Name varchar(255) NOT NULL, Surname varchar(255) NOT NULL, Age INT NOT NULL, isActive BOOLEAN NOT NULL)"; 
		statement.executeUpdate(sql);
	 }
	
	@Override
	public void updateStorageObject() throws SQLException {
		String sql = "DROP TABLE IF EXISTS USERS"; 
		statement.executeUpdate(sql);
		createStorageObject();
	}
	
	@Override
	public int addUser(User user) throws SQLException {
		String insertingRequestPart = "INSERT INTO Users VALUES (";
		String userRequestPart = ", '" + user.getName() + "', '" + user.getSurname() + "', " + user.getAge() + ", " + user.isActive() + ")";
		String addingReguest;
		if (user.getId() == -1)
			addingReguest = insertingRequestPart + "DEFAULT" + userRequestPart; 
		else
			addingReguest = insertingRequestPart + user.getId() + userRequestPart; 
		
		statement.executeUpdate(addingReguest, Statement.RETURN_GENERATED_KEYS);	
		resultSet = statement.getGeneratedKeys();
		if (resultSet != null)
			while (resultSet.next()) 
				return resultSet.getInt(1);
		return -1;
	}
	
	@Override
	public void updateUser(User user) throws SQLException {
		String sql = "UPDATE Users SET NAME='" + user.getName() + "', SURNAME = '" + user.getSurname() 
		+  "', AGE = " + user.getAge() + ", ISACTIVE = " + user.isActive() + " WHERE ID=" + user.getId();
		statement.executeUpdate(sql);
	  }
	  
	@Override
	public void deleteUser(int id) throws SQLException { 
		String sql = "DELETE FROM Users WHERE ID=" + id;
		statement.executeUpdate(sql);
	  }
	
	@Override
	public void deleteAllUsers() throws SQLException { 
		String sql = "DELETE FROM Users";
		statement.executeUpdate(sql);
	}
	
	
	@Override
	public List <User> getUsersDataSet(boolean sorting, boolean usersIsDeleted) throws SQLException {
		List <User> users = new ArrayList <>();
		String selectingRequest = "SELECT id, Name, Surname, Age, isActive FROM Users";
		if (sorting)  resultSet = statement.executeQuery(selectingRequest + "order by id"); else
		resultSet = statement.executeQuery(selectingRequest);
		
	    while (resultSet.next())
		if (resultSet != null) {	
			User user = new User();
			user.setId(resultSet.getInt("id"));
			user.setName(resultSet.getString("Name"));
			user.setSurname(resultSet.getString("Surname"));
			user.setAge(resultSet.getInt("Age"));
			user.setIsActive(resultSet.getBoolean("isActive"));
			users.add(user);
		}
		else return null;
		return users;
	  }

	@Override
	public void closeStorage() throws SQLException {	
		if (resultSet != null) resultSet.close();
		if (statement != null) statement.close(); 
		if (connection != null) connection.close();
	}
}
