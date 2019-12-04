package user;

/**
 * Класс предназначен для реализации временного хранения данных пользователя и возврата этих данных.
 */
public class User 
{
	public static final int DEFAULT_ID = -1;
	private int m_id;
	private String m_name;
	private String m_surname;
	private int m_age;
	private boolean m_isActive;
	
	public User() 
	{
		m_id = DEFAULT_ID;
	}
	
	/**
	 * Метод устанавливает код пользователя.
	 */
	public void setId(int a_id) 
	{
		m_id = a_id;
	}
	
	/**
	 * Метод устанавливает имя пользователя.
	 */
	public void setName (String a_name) 
	{
		m_name = a_name;
	}
	
	/**
	 * Метод устанавливает фамилию пользователя.
	 */
	public void setSurname (String a_surname) 
	{
		m_surname = a_surname;
	}
	
	/**
	 * Метод устанавливает возраст пользователя.
	 */
	public void setAge (int a_age) 
	{
		m_age = a_age;
	}
	
	/**
	 * Метод устанавливает состояние активен/неактивен пользователя.
	 */
	public void setIsActive (boolean a_isActive) 
	{
		m_isActive = a_isActive;
	}
	
	/**
	 * Метод возвращает код пользователя.
	 */
	public int getId() 
	{
		return m_id;
	}
	
	/**
	 * Метод возвращает имя пользователя.
	 */
	public String getName() 
	{
		return m_name;
	}
	
	/**
	 * Метод возвращает фамилию пользователя.
	 */
	public String getSurname()
	{
		return m_surname;
	}
	
	/**
	 * Метод возвращает возраст пользователя.
	 */
	public int getAge() 
	{
		return m_age;
	}
	
	/**
	 * Метод возвращает состояние активен/неактивен пользователя.
	 */
	public boolean isActive() 
	{
		return m_isActive;
	}
}
