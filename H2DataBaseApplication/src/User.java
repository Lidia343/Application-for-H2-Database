
/**
 * Класс предназначен для реализации временного хранения данных всех пользователей и возврата этих данных.
 */
public class User {
	
	private int id;
	private String name;
	private String surname;
	private int age;
	private boolean isActive;
	
	
	/**
	 * Конструктор класса User.
	 */
	public User() {
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
}
