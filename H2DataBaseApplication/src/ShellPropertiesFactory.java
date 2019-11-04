
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

/**
 * Фабрика для создания объектов класса ShellProperties.
 */
public class ShellPropertiesFactory {
	
	private Display display;
	
	public ShellPropertiesFactory (Display display) {
		this.display = display;
	}
	
	/**
	 * Метод возвращает объект класса ShellProperties.
	 * @param storage - хранилище данных
	 * @param size - размер компонента Shell
	 * @param backColor - фоновый цвет
	 */
	public ShellProperties getShellProperties (Storage storage, Point size, Color backColor) {
		ShellProperties shellProperties = null;
		if (storage == null) shellProperties = new ShellProperties.Builder("Работа с данными пользователей", "question.png", display).size(size).backColor(backColor).build(); else
		if (storage.getStorageName().startsWith("jdbc:h2:")) shellProperties = new ShellProperties.Builder("Работа с базой данных", "database.png", display).size(size).backColor(backColor).build(); else
		if (storage.getStorageName().endsWith(".txt")) shellProperties = new ShellProperties.Builder("Работа с файлом", "file.png", display).size(size).backColor(backColor).build();
		return shellProperties;
	}
	
	/**
	 * Метод возвращает объект класса ShellProperties.
	 * @param storage - хранилище данных
	 * @param size - размер компонента Shell
	 */
	public ShellProperties getShellProperties (Storage storage, Point size) {
		ShellProperties shellProperties = null;
		if (storage == null) shellProperties = new ShellProperties.Builder("Работа с данными пользователей", "question.png", display).size(size).build(); else
		if (storage.getStorageName().startsWith("jdbc:h2:")) shellProperties = new ShellProperties.Builder("Работа с базой данных", "database.png", display).size(size).build(); else
		if (storage.getStorageName().endsWith(".txt")) shellProperties = new ShellProperties.Builder("Работа с файлом", "file.png", display).size(size).build();
		return shellProperties;
	}
	
	/**
	 * Метод возвращает объект класса ShellProperties.
	 * @param storage - хранилище данных
	 * @param backColor - фоновый цвет
	 */
	public ShellProperties getShellProperties (Storage storage, Color backColor) {
		ShellProperties shellProperties = null;
		if (storage == null) shellProperties = new ShellProperties.Builder("Работа с данными пользователей", "question.png", display).backColor(backColor).build(); else
		if (storage.getStorageName().startsWith("jdbc:h2:")) shellProperties = new ShellProperties.Builder("Работа с базой данных", "database.png", display).backColor(backColor).build(); else
		if (storage.getStorageName().endsWith(".txt")) shellProperties = new ShellProperties.Builder("Работа с файлом", "file.png", display).backColor(backColor).build();
		return shellProperties;
	}
	
	/**
	 * Метод возвращает объект класса ShellProperties.
	 * @param storage - хранилище данных
	 */
	public ShellProperties getShellProperties (Storage storage) {
		ShellProperties shellProperties = null;
		if (storage == null) shellProperties = new ShellProperties.Builder("Работа с данными пользователей", "question.png", display).build(); else
		if (storage.getStorageName().startsWith("jdbc:h2:")) shellProperties = new ShellProperties.Builder("Работа с базой данных", "database.png", display).build(); else
		if (storage.getStorageName().endsWith(".txt")) shellProperties = new ShellProperties.Builder("Работа с файлом", "file.png", display).build();
		return shellProperties;
	}
}
	
