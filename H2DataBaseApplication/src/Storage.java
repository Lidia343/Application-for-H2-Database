import java.util.List;

/**
 * Интерфейс, необходимый для определения методов, которые должны содержать классы, реализующие хранение данных.
 */
public interface Storage {
	
	/**
	 * Метод для установки хранилища.
	 */
	void setStorage () throws Exception;
	
	/**
	 * Метод для создания объекта хранилища.
	 */
	void createStorageObject() throws Exception;
	
	/**
	 * Метод для сброса первичного ключа.
	 */
	void updateStorageObject() throws Exception;
	
	/**
	 * Метод для добавления данных пользователя в хранилище.
	 * @param user - объект, содержащий данные пользователя
	 */
	void addUser(User user) throws Exception;
	
	/**
	 * Метод для добавления данных пользователя в хранилище.
	 * @param user - объект, содержащий данные пользователя
	 * @param deletedUserId - код удалённого пользователя (передаётся при восстановлении удалённого пользователя)
	 */
	void addUser(User user, int deletedUserId) throws Exception;
	
	/**
	 * Метод для изменения данных пользователя в хранилище.
	 * @param id - код, необходимый для поиска данных пользователя (не изменяется)
	 * @param name - имя
	 * @param surname - фамилия
	 * @param age - возраст
	 * @param isActive - активен/неактивен
	 */
	void updateUser(User user) throws Exception;
	
	
	/**
	 * Метод для удаления данных пользователя из хранилища.
	 * @param id - код пользователя
	 */
	void deleteUser(int id) throws Exception;
	
	/**
	 * Метод для удаления данных всех пользователей из хранилища.
	 */
	void deleteAllUsers () throws Exception;
	
	/**
	 * Метод для возврата списка, содержащего данные всех пользователей хранилища.
	 * @param isSorted - сортировать данные пользователей по коду / не сортировать
	 */
	List <User> getUsersDataSet(boolean isSorted, boolean deletedUsers) throws Exception;
	
	/**
	 * Метод для закрытия хранилища без потерь данных, содержащихся в нём.
	 */
	void closeStorage() throws Exception;
}
