package graphics;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;

import storages.FileStorage;
import storages.IStorage;

/**
 * Фабрика для создания объектов класса ShellProperties.
 */
public class ShellPropertiesFactory 
{
	/**
	 * Метод возвращает объект класса ShellProperties.
	 * @param a_storage - хранилище данных пользователей
	 * @param a_size - размер компонента Shell
	 * @param a_backColor - фоновый цвет
	 * @param a_isDarkColor - установить тёмный фон / установить светлый фон
	 */
	public ShellProperties getShellProperties (IStorage a_storage, Point a_size, Color a_backColor, boolean a_isDarkColor) 
	{
		ShellProperties shellProperties = null;
		if (a_storage == null) 
		{
			if (a_isDarkColor) 
				shellProperties = new ShellProperties.Builder("Работа с данными пользователей", "darkQuestion.png").size(a_size).backColor(a_backColor).build(); 
			else
				shellProperties = new ShellProperties.Builder("Работа с данными пользователей", "lightQuestion.png").size(a_size).backColor(a_backColor).build();
		} 
		else
			if (a_storage instanceof FileStorage) 
			{
				if (a_isDarkColor) 
					shellProperties = new ShellProperties.Builder("Работа с файлом", "darkFile.png").size(a_size).backColor(a_backColor).build(); 
				else
					shellProperties = new ShellProperties.Builder("Работа с файлом", "lightFile.png").size(a_size).backColor(a_backColor).build();
			} else
				{
					if (a_isDarkColor) 
						shellProperties = new ShellProperties.Builder("Работа с базой данных", "darkDatabase.png").size(a_size).backColor(a_backColor).build(); 
					else
						shellProperties = new ShellProperties.Builder("Работа с базой данных", "lightDatabase.png").size(a_size).backColor(a_backColor).build();
				} 
		return shellProperties;
	}
	
	/**
	 * Метод возвращает объект класса ShellProperties.
	 * @param a_storage - хранилище данных пользователей
	 * @param a_size - размер компонента Shell
	 */
	public ShellProperties getShellProperties (IStorage a_storage, Point a_size) 
	{
		ShellProperties shellProperties = getShellProperties (a_storage, a_size, null, true);
		return shellProperties;
	}
	
	/**
	 * Метод возвращает объект класса ShellProperties.
	 * @param a_storage - хранилище данных пользователей
	 * @param a_backColor - фоновый цвет
	 * @param a_isDarkColor - установить тёмный фон / установить светлый фон
	 */
	public ShellProperties getShellProperties (IStorage a_storage, Color a_backColor, boolean a_isDarkColor) 
	{
		ShellProperties shellProperties = getShellProperties (a_storage, null, a_backColor, a_isDarkColor);
		return shellProperties;
	}
	
	/**
	 * Метод возвращает объект класса ShellProperties.
	 * @param a_storage - хранилище данных пользователей
	 */
	public ShellProperties getShellProperties (IStorage a_storage) 
	{
		ShellProperties shellProperties = getShellProperties (a_storage, null, null, true);
		return shellProperties;
	}
}
	
