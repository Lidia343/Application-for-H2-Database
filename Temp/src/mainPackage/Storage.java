package mainPackage;
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
	 * @param user - объект, содержащий данные для добавления
	 * @return код добавленного пользователя
	 */
	int addUser(User user) throws Exception;
	
	/**
	 * Метод для изменения данных пользователя в хранилище.
	 * @param user - объект класса User, на который необходимо заменить объект с аналогичным ключом в хранилище
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
	 * @param sorting - сортировать данные пользователей по коду / не сортировать
	 * @param usersIsDeleted - вернуть список удалённых пользователей / вернуть список текущих пользователей
	 */
	List <User> getUsersDataSet(boolean isSorted, boolean deletedUsers) throws Exception;
	
	/**
	 * Метод для закрытия хранилища без потерь данных, содержащихся в нём.
	 */
	void closeStorage() throws Exception;
}
