package storages;

import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;

/**
 * Фабрика для создания объектов классов, реализующих интерфейс Storage.
 */
public class StorageFactory 
{
	private final String m_storageExtensionID = "H2DataBasePlug-in.store";
	
	/**
	 * Метод возвращает хранилище данных пользователей.
	 * @param a_storageName - имя хранилища
	 * @throws CoreException 
	 */
	public Storage getStorage(String a_storageName) 
	{
		IExtensionRegistry m_reg = Platform.getExtensionRegistry();
		IConfigurationElement[] m_extensions = m_reg.getConfigurationElementsFor(m_storageExtensionID);
		Storage m_storage;
		
		for (IConfigurationElement m_extension : m_extensions)
		{
			if (Pattern.compile(m_extension.getAttribute("pattern")).matcher(a_storageName).matches())
			{
				try
				{
					m_storage = (Storage) m_extension.createExecutableExtension("class");
					((IExecutableExtension)m_storage).setInitializationData(m_extension, "name", a_storageName);
					return m_storage;
				}
				catch(CoreException e)
				{
					e.printStackTrace();
				}
			}
		}
		return new FileStorage("file.txt");
	}
}
