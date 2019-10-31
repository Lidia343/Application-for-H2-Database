import java.util.ArrayList;

/**
 * Класс предназначен для реализации временного хранения данных всех пользователей и возврата этих данных.
 */
public class User {
	
	private ArrayList<User> users;
	private int id;
	private String name;
	private String surname;
	private int age;
	private boolean isActive;
	private String errorMessage;
	
	/**
	 * Конструктор класса User.
	 */
	public User() {
		errorMessage = "";
		users = new ArrayList<User>();
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public void setSurname (String surname) {
		this.surname = surname;
	}
	
	public void setAge (int age) {
		this.age = age;
	}
	
	public void setIsActive (boolean isActive) {
		this.isActive = isActive;
	}
	
	/**
	 * Метод добавляет данные одного пользователя.
	 * @param user - данные пользователя (код, имя, фамилия, возраст, активен/неактивен)
	 */
	public void addUser(User user) {
		users.add(user);
	}
	
	/**
	 * Метод возвращает код пользователя.
	 */
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public int getAge() {
		return age;
	}
	
	public boolean getIsActive() {
		return isActive;
	}
	
	public ArrayList<User> getUsersList(){
		return users;
	}
	
	/**
	 * Метод возвращает сообщение об ошибке.
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
}
