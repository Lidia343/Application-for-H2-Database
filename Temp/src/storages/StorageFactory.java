package storages;

import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.IConfigurationElement;
/**
 * Фабрика для создания объектов классов, реализующих интерфейс Storage.
 */
public class StorageFactory 
{
	private final String m_storageExtensionID = "H2DataBasePlug-in.stores";
	
	/**
	 * Метод возвращает хранилище данных пользователей.
	 * @param a_storageName - имя хранилища
	 */
	public Storage getStorage(String a_storageName) 
	{
		IExtensionRegistry m_reg = Platform.getExtensionRegistry();
		IConfigurationElement[] m_extensions = m_reg.getConfigurationElementsFor(m_storageExtensionID);
		Storage m_storage;
		for (IConfigurationElement m_extension : m_extensions) 
		{
			if (a_storageName.equals(m_extension.getAttribute("name"))) 
			{
				m_storage = new DataBase(a_storageName);
				return m_storage;
			}
		}
		if (a_storageName.endsWith(".txt")) 
			m_storage = new FileStorage(a_storageName); 
		else
			m_storage = new DataBase("jdbc:h2:~/test");
		return m_storage;
	}
}
