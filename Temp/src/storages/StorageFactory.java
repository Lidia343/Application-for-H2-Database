package storages;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.IConfigurationElement;
/**
 * Фабрика для создания объектов классов, реализующих интерфейс Storage.
 */
public class StorageFactory {
	
	private final String storageExtensionID = "Temp.stores";
	
	/**
	 * Метод возвращает хранилище данных пользователей.
	 * @param storageName - имя хранилища
	 */
	public Storage getStorage(String storageName) {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg.getConfigurationElementsFor(storageExtensionID);
		Storage storage;
		for (IConfigurationElement extension : extensions) 
			if (storageName.equals(extension.getAttribute("name"))) {
				storage = new DataBase(storageName);
				return storage;
			}
		if (storageName.endsWith(".txt")) storage = new FileStorage(storageName); else
			storage = new DataBase("jdbc:h2:~/test");
		return storage;
	}
}
