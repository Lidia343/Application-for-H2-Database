package user;

/**
 * Интерфейс слушателя редактирования данных пользователя.
 */
public interface UserEditingListener 
{
	/**
	 * Метод для изменения имени пользователя.
	 * @param a_user - объект класса User (пользователь, имя которого необходимо изменить)
	 * @param a_name - имя, которое необходимо присвоить 
	 */
	public void changeUserName(User a_user, String a_name);
	
	/**
	 * Метод для изменения фамилии пользователя.
	 * @param a_user - объект класса User (пользователь, фамилию которого необходимо изменить)
	 * @param a_surname - фамилия, которую необходимо присвоить 
	 */
	public void changeUserSurname(User a_user, String a_surname);
		
	/**
	 * Метод для изменения возраста пользователя.
	 * @param a_user - объект класса User (пользователь, возраст которого необходимо изменить)
	 * @param a_age - возраст, который необходимо присвоить 
	 */
	public void changeUserAge(User a_user, int a_age);
	
	/**
	 * Метод для изменения состояния активности пользователя.
	 * @param a_user - объект класса User (пользователь, состояние активности  которого необходимо изменить)
	 * @param a_isActive - состояние активности , которое необходимо присвоить 
	 */
	public void changeUserIsActive(User a_user, boolean a_isActive);
}
