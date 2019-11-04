
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

/**
 * Фабрика для создания объектов класса ShellProperties.
 */
public class ShellPropertiesFactory {
	
	private Display display;
	
	/**
	 * Конструктор класса ShellPropertiesFactory.
	 * @param display - объект класса Display
	 */
	public ShellPropertiesFactory (Display display) {
		this.display = display;
	}
	
	/**
	 * Метод возвращает объект класса ShellProperties.
	 * @param storage - хранилище данных пользователей
	 * @param size - размер компонента Shell
	 * @param backColor - фоновый цвет
	 */
	public ShellProperties getShellProperties (Storage storage, Point size, Color backColor) {
		ShellProperties shellProperties = null;
		if (storage == null) {
			if (backColor.getRed() == 83) shellProperties = new ShellProperties.Builder("Работа с данными пользователей", "darkQuestion.png", display).size(size).backColor(backColor).build(); else
			shellProperties = new ShellProperties.Builder("Работа с данными пользователей", "lightQuestion.png", display).size(size).backColor(backColor).build();
		} else
		if (storage.getStorageName().startsWith("jdbc:h2:")) {
			if (backColor.getRed() == 83) shellProperties = new ShellProperties.Builder("Работа с базой данных", "darkDatabase.png", display).size(size).backColor(backColor).build(); else
			shellProperties = new ShellProperties.Builder("Работа с базой данных", "lightDatabase.png", display).size(size).backColor(backColor).build();
		} else
		if (storage.getStorageName().endsWith(".txt")) {
			if (backColor.getRed() == 83) shellProperties = new ShellProperties.Builder("Работа с файлом", "darkFile.png", display).size(size).backColor(backColor).build(); else
			shellProperties = new ShellProperties.Builder("Работа с файлом", "lightFile.png", display).size(size).backColor(backColor).build();
		}
		return shellProperties;
	}
	
	/**
	 * Метод возвращает объект класса ShellProperties.
	 * @param storage - хранилище данных пользователей
	 * @param size - размер компонента Shell
	 */
	public ShellProperties getShellProperties (Storage storage, Point size) {
		ShellProperties shellProperties = null;
		if (storage == null) shellProperties = new ShellProperties.Builder("Работа с данными пользователей", "darkQuestion.png", display).size(size).build(); else
		if (storage.getStorageName().startsWith("jdbc:h2:")) shellProperties = new ShellProperties.Builder("Работа с базой данных", "darkDatabase.png", display).size(size).build(); else
		if (storage.getStorageName().endsWith(".txt")) shellProperties = new ShellProperties.Builder("Работа с файлом", "darkFile.png", display).size(size).build();
		return shellProperties;
	}
	
	/**
	 * Метод возвращает объект класса ShellProperties.
	 * @param storage - хранилище данных пользователей
	 * @param backColor - фоновый цвет
	 */
	public ShellProperties getShellProperties (Storage storage, Color backColor) {
		ShellProperties shellProperties = null;
		if (storage == null) {
			if (backColor.getRed() == 83) shellProperties = new ShellProperties.Builder("Работа с данными пользователей", "darkQuestion.png", display).backColor(backColor).build(); else
			shellProperties = new ShellProperties.Builder("Работа с данными пользователей", "lightQuestion.png", display).backColor(backColor).build();	
		} else
		if (storage.getStorageName().startsWith("jdbc:h2:")) {
			if (backColor.getRed() == 83) shellProperties = new ShellProperties.Builder("Работа с базой данных", "darkDatabase.png", display).backColor(backColor).build(); else
			shellProperties = new ShellProperties.Builder("Работа с базой данных", "lightDatabase.png", display).backColor(backColor).build();
		} else
		if (storage.getStorageName().endsWith(".txt")) {
			if (backColor.getRed() == 83) shellProperties = new ShellProperties.Builder("Работа с файлом", "darkFile.png", display).backColor(backColor).build(); else
			shellProperties = new ShellProperties.Builder("Работа с файлом", "lightFile.png", display).backColor(backColor).build();
		}
		return shellProperties;
	}
	
	/**
	 * Метод возвращает объект класса ShellProperties.
	 * @param storage - хранилище данных пользователей
	 */
	public ShellProperties getShellProperties (Storage storage) {
		ShellProperties shellProperties = null;
		if (storage == null) shellProperties = new ShellProperties.Builder("Работа с данными пользователей", "darkQuestion.png", display).build(); else
		if (storage.getStorageName().startsWith("jdbc:h2:")) shellProperties = new ShellProperties.Builder("Работа с базой данных", "darkDatabase.png", display).build(); else
		if (storage.getStorageName().endsWith(".txt")) shellProperties = new ShellProperties.Builder("Работа с файлом", "darkFile.png", display).build();
		return shellProperties;
	}
}
	
