package mainPackage;

/**
 * Класс предназначен для реализации временного хранения данных пользователя и возврата этих данных.
 */
public class User {
	
	public static final int DEFAULT_ID = -1;
	private int id;
	private String name;
	private String surname;
	private int age;
	private boolean isActive;
	
	public User() {
		id = DEFAULT_ID;
	}
	
	/**
	 * Метод устанавливает код пользователя.
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Метод устанавливает имя пользователя.
	 */
	public void setName (String name) {
		this.name = name;
	}
	
	/**
	 * Метод устанавливает фамилию пользователя.
	 */
	public void setSurname (String surname) {
		this.surname = surname;
	}
	
	/**
	 * Метод устанавливает возраст пользователя.
	 */
	public void setAge (int age) {
		this.age = age;
	}
	
	/**
	 * Метод устанавливает состояние активен/неактивен пользователя.
	 */
	public void setIsActive (boolean isActive) {
		this.isActive = isActive;
	}
	
	/**
	 * Метод возвращает код пользователя.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Метод возвращает имя пользователя.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Метод возвращает фамилию пользователя.
	 */
	public String getSurname() {
		return surname;
	}
	
	/**
	 * Метод возвращает возраст пользователя.
	 */
	public int getAge() {
		return age;
	}
	
	/**
	 * Метод возвращает состояние активен/неактивен пользователя.
	 */
	public boolean isActive() {
		return isActive;
	}
}
