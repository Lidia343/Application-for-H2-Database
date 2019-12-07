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
	private static final String STORAGE_EXTENSION_ID = "H2DataBasePlug-in.stores";
	
	/**
	 * Метод возвращает хранилище данных пользователей.
	 * @param a_storageName - имя хранилища
	 * @throws CoreException 
	 */
	public Storage getStorage(String a_storageName) 
	{
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg.getConfigurationElementsFor(STORAGE_EXTENSION_ID);
		Storage storage;
		
		for (IConfigurationElement extension : extensions)
		{
			if (Pattern.compile(extension.getAttribute("pattern")).matcher(a_storageName).matches())
			{
				try
				{
					storage = (Storage) extension.createExecutableExtension("class");
					((IExecutableExtension)storage).setInitializationData(extension, "name", a_storageName);
					return storage;
				}
				catch(CoreException a_e)
				{
					a_e.printStackTrace();
				}
			}
		}
		return new FileStorage("file.txt");
	}
}
