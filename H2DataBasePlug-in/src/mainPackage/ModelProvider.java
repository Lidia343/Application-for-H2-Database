package mainPackage;
import java.util.List;

/**
 * Класс предназначен для реализации модели данных, которую необходимо передать в метод setInput() класса TableViewer.
 */
public class ModelProvider {
	
	private Storage storage;
	
	/**
	 * Конструктор класса ModelProvider.
	 * @param storage - хранилище данных
	 */
	public ModelProvider(Storage storage) {
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
