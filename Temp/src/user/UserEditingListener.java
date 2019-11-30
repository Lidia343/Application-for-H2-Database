package user;

/**
 * Интерфейс слушателя редактирования данных пользователя.
 */
public interface UserEditingListener {
	
	/**
	 * Метод для изменения имени пользователя.
	 * @param user - объект класса User (пользователь, имя которого необходимо изменить)
	 * @param name - имя, которое необходимо присвоить 
	 */
	public void changeUserName(User user, String name);
	
	/**
	 * Метод для изменения фамилии пользователя.
	 * @param user - объект класса User (пользователь, фамилию которого необходимо изменить)
	 * @param surname - фамилия, которую необходимо присвоить 
	 */
	public void changeUserSurname(User user, String surname);
		
	/**
	 * Метод для изменения возраста пользователя.
	 * @param user - объект класса User (пользователь, возраст которого необходимо изменить)
	 * @param age - возраст, который необходимо присвоить 
	 */
	public void changeUserAge(User user, int age);
	
	/**
	 * Метод для изменения состояния активности пользователя.
	 * @param user - объект класса User (пользователь, состояние активности  которого необходимо изменить)
	 * @param isActive - состояние активности , которое необходимо присвоить 
	 */
	public void changeUserIsActive(User user, boolean isActive);
}
