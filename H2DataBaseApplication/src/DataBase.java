import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;  

/**
 * Класс предназначен для работы с базой данных.
 */
public class DataBase implements Storage {
	
	private String JDBC_Driver;   
	  
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	  
	private String errorMessage;
	  
    /**
	* Конструктор класса DataBase
	*/
	public DataBase() {
		JDBC_Driver = "org.h2.Driver"; 
		errorMessage = "";
	}
	  
	@Override
	public void setStorage () { 
		  connection = null; 
		  statement = null; 
		  try { 
			  Class.forName(JDBC_Driver);      
			  connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", ""); 
			  statement = connection.createStatement(); 		
			  errorMessage = "";
		  } catch(Exception e) { 
			  errorMessage = e.getMessage();
		  } 
	 }
	  
	@Override
	public void createStorageObject(){
		 try {
			  String sql = "CREATE TABLE IF NOT EXISTS USERS (id INT NOT NULL AUTO_INCREMENT (0, 1) PRIMARY KEY, "
			  		+ "Name varchar(255) NOT NULL, Surname varchar(255) NOT NULL, Age INT NOT NULL, isActive BOOLEAN NOT NULL)"; 
			  statement.executeUpdate(sql);
			  errorMessage = "";
		  } catch(Exception e) { 
			  errorMessage = e.getMessage();
		  } 
	 }
	
	@Override
	public void updateStorageObject() {
		 try {
			  String sql = "DROP TABLE IF EXISTS USERS"; 
			  statement.executeUpdate(sql);
			  createStorageObject();
			  errorMessage = "";
		  } catch(Exception e) { 
			  errorMessage = e.getMessage();
		  } 
	}
	
	private void sendAddingRequest(String request) {
		  try {
			  statement.executeUpdate(request);
			  errorMessage = "";
		  } catch(Exception e) { 
			  errorMessage = e.getMessage();
		  } 
	}
	
	@Override
	public void addUser(User user) {
		String sql = "INSERT INTO Users VALUES (DEFAULT, '" + user.getName() + "', '" + user.getSurname() + "', " + user.getAge() + ", " + user.getIsActive() + ")"; 
		sendAddingRequest (sql);
	}
	
	@Override
	public void addUser(User user, int deletedId) {
		String sql = "INSERT INTO Users VALUES (" + deletedId + ", '" + user.getName() + "', '" + user.getSurname() + "', " + user.getAge() + ", " + user.getIsActive() + ")"; 
	    sendAddingRequest (sql);
	}
	  
	@Override
	public void updateUser(User user) {
		  try {
			  String sql = "UPDATE Users SET NAME='" + user.getName() + "', SURNAME = '" + user.getSurname() +  "', AGE = " + user.getAge() + ", ISACTIVE = " + user.getIsActive() + " WHERE ID=" + user.getId();
			  statement.executeUpdate(sql);
			  errorMessage = "";
		  } catch(Exception e) { 
			  errorMessage = e.getMessage();
		  } 
	  }
	  
	@Override
	public void deleteUser(int id) {
		 try {
			  String sql = "DELETE FROM Users WHERE ID=" + id;
			  statement.executeUpdate(sql);
			  errorMessage = "";
		  } catch(Exception e) { 
			  errorMessage = e.getMessage(); 
		  } 
	  }
	
	@Override
	public User getUsersDataSet(boolean isSorted) {
		 User users = new User ();
		 try {
			  if (isSorted)  resultSet = statement.executeQuery("SELECT id, Name, Surname, Age, isActive FROM Users order by id"); else
			  resultSet = statement.executeQuery("SELECT id, Name, Surname, Age, isActive FROM Users");
			  
			  while (resultSet.next())
			  if (resultSet != null) {
			
				  User user = new User();
				  user.setId(resultSet.getInt("id"));
				  user.setName(resultSet.getString("Name"));
				  user.setSurname(resultSet.getString("Surname"));
				  user.setAge(resultSet.getInt("Age"));
				  user.setIsActive(resultSet.getBoolean("isActive"));
				  users.addUser(user);
			  }
			  else return null;
			  errorMessage = "";
		  } catch(Exception e) { 
			  errorMessage = e.getMessage(); 
		  } 
		  return users;
	  }
	
	@Override
	public String getErrorMessage() {
		  return errorMessage;
	  }
	  
	@Override
	public void closeStorage() {	
		try {
			resultSet.close();
			statement.close(); 
			connection.close();
			errorMessage = "";
		} catch(Exception e) { 
			errorMessage = e.getMessage();
		} 
	}
}
