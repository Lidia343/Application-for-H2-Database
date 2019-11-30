package user;
import java.util.List;

import storages.Storage;

/**
 * Класс предназначен для реализации модели данных, которую необходимо передать в метод setInput() класса TableViewer.
 */
public class UsersModelProvider {
	
	private Storage storage;
	
	/**
	 * Конструктор класса ModelProvider.
	 * @param storage - хранилище данных
	 */
	public UsersModelProvider(Storage storage) {
		this.storage = storage;
	}
	
	/**
	 * Метод возвращает данные всех пользователей из хранилища.
	 * @throws Exception
	 */
	public List<User> getUsersData() throws Exception {
		return storage.getUsersDataSet(false, false);
	}
}
