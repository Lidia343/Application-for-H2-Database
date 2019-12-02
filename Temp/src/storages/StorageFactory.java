package storages;
/**
 * Фабрика для создания объектов классов, реализующих интерфейс Storage.
 */
public class StorageFactory {
	
	/**
	 * Метод возвращает хранилище данных пользователей.
	 * @param storageName - имя хранилища
	 */
	public Storage getStorage(String storageName) {
		Storage storage;
		if (storageName.startsWith("jdbc:h2:")) storage = new DataBase(storageName); else 
			if (storageName.endsWith(".txt")) storage = new FileStorage(storageName); else
				storage = new DataBase("jdbc:h2:~/test");
		return storage;
	}
}
