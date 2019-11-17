/**
 * Слушатель редактирования пользователем данных в таблице.
 */
public class TableViewerUserEditingListener {
	
	/**
	 * Метод для изменения имени пользователя
	 * @param user - объект класса User (пользователь, имя которого необходимо изменить)
	 * @param name - имя, которое необходимо присвоить 
	 */
	public void changeUserNameInStorage(User user, String name) {
		user.setName(name);
	}
	
	/**
	 * Метод для изменения фамилии пользователя
	 * @param user - объект класса User (пользователь, фамилию которого необходимо изменить)
	 * @param surname - фамилия, которую необходимо присвоить 
	 */
	public void changeUserSurnameInStorage(User user, String surname) {
		user.setSurname(surname);
	}
	
	/**
	 * Метод для изменения возраста пользователя
	 * @param user - объект класса User (пользователь, возраст которого необходимо изменить)
	 * @param age - возраст, который необходимо присвоить 
	 */
	public void changeUserAgeInStorage(User user, int age) {
		user.setAge(age);
	}
	
	/**
	 * Метод для изменения состояния активности пользователя
	 * @param user - объект класса User (пользователь, состояние активности  которого необходимо изменить)
	 * @param isActive - состояние активности , которое необходимо присвоить 
	 */
	public void changeUserIsActiveInStorage(User user, boolean isActive) {
		user.setIsActive(isActive);
	}
}
