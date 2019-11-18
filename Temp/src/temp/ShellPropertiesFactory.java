package temp;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;

/**
 * Фабрика для создания объектов класса ShellProperties.
 */
public class ShellPropertiesFactory {
	
	/**
	 * Метод возвращает объект класса ShellProperties.
	 * @param storage - хранилище данных пользователей
	 * @param size - размер компонента Shell
	 * @param backColor - фоновый цвет
	 * @param isDarkColor - установить тёмный фон / установить светлый фон
	 */
	public ShellProperties getShellProperties (Storage storage, Point size, Color backColor, boolean isDarkColor) {
		ShellProperties shellProperties = null;
		if (storage == null) {
			if (isDarkColor) shellProperties = new ShellProperties.Builder("Работа с данными пользователей", "darkQuestion.png").size(size).backColor(backColor).build(); else
			shellProperties = new ShellProperties.Builder("Работа с данными пользователей", "lightQuestion.png").size(size).backColor(backColor).build();
		} else
		if (storage instanceof DataBase) {
			if (isDarkColor) shellProperties = new ShellProperties.Builder("Работа с базой данных", "darkDatabase.png").size(size).backColor(backColor).build(); else
			shellProperties = new ShellProperties.Builder("Работа с базой данных", "lightDatabase.png").size(size).backColor(backColor).build();
		} else
		if (storage instanceof FileStorage) {
			if (isDarkColor) shellProperties = new ShellProperties.Builder("Работа с файлом", "darkFile.png").size(size).backColor(backColor).build(); else
			shellProperties = new ShellProperties.Builder("Работа с файлом", "lightFile.png").size(size).backColor(backColor).build();
		}
		return shellProperties;
	}
	
	/**
	 * Метод возвращает объект класса ShellProperties.
	 * @param storage - хранилище данных пользователей
	 * @param size - размер компонента Shell
	 */
	public ShellProperties getShellProperties (Storage storage, Point size) {
		ShellProperties shellProperties = getShellProperties (storage, size, null, true);
		return shellProperties;
	}
	
	/**
	 * Метод возвращает объект класса ShellProperties.
	 * @param storage - хранилище данных пользователей
	 * @param backColor - фоновый цвет
	 * @param isDarkColor - установить тёмный фон / установить светлый фон
	 */
	public ShellProperties getShellProperties (Storage storage, Color backColor, boolean isDarkColor) {
		ShellProperties shellProperties = getShellProperties (storage, null, backColor, isDarkColor);
		return shellProperties;
	}
	
	/**
	 * Метод возвращает объект класса ShellProperties.
	 * @param storage - хранилище данных пользователей
	 */
	public ShellProperties getShellProperties (Storage storage) {
		ShellProperties shellProperties = getShellProperties (storage, null, null, true);
		return shellProperties;
	}
}
	
