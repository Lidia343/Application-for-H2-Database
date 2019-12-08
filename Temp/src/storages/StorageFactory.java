package storages;

import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

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
	public IStorage getStorage(String a_storageName) throws InstantiationException, IllegalAccessException 
	{
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg.getConfigurationElementsFor(STORAGE_EXTENSION_ID);
		
		for (IConfigurationElement extension : extensions)
		{
			if (Pattern.compile(extension.getAttribute("pattern")).matcher(a_storageName).matches())
			{
				try
				{
					String bundleSymbolicName = extension.getContributor().getName();
					Class<?> storageClass = Platform.getBundle(bundleSymbolicName).loadClass(extension.getAttribute("class"));
					return (IStorage)storageClass.getConstructor(String.class).newInstance(a_storageName);
				}
				catch(ClassNotFoundException e)
				{
					e.printStackTrace();
				}
				catch(InvalidRegistryObjectException e)
				{
					e.printStackTrace();
				}
				catch(NoSuchMethodException e)
				{
					e.printStackTrace();
				}
				catch(SecurityException e)
				{
					e.printStackTrace();
				}
				catch(IllegalArgumentException e)
				{
					e.printStackTrace();
				}
				catch(InvocationTargetException e)
				{
					e.printStackTrace();
				}
			}
		}
		return new FileStorage("file.txt");
	}
}
