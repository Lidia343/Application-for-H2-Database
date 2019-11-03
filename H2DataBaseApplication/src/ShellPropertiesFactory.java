
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

public class ShellPropertiesFactory {
	
	private Display display;
	
	public ShellPropertiesFactory (Display display) {
		this.display = display;
	}
	
	public ShellProperties getShellProperties (Storage storage, Point size, Color backColor) {
		ShellProperties shellProperties = null;
		if (storage == null) shellProperties = new ShellProperties.Builder("Работа с данными пользователей", "question.png", display).size(size).backColor(backColor).build(); else
		if (storage.getStorageName().startsWith("jdbc:h2:")) shellProperties = new ShellProperties.Builder("Работа с базой данных", "database.png", display).size(size).backColor(backColor).build(); else
		if (storage.getStorageName().endsWith(".txt")) shellProperties = new ShellProperties.Builder("Работа с файлом", "file.png", display).size(size).backColor(backColor).build();
		return shellProperties;
	}
	
	public ShellProperties getShellProperties (Storage storage, Point size) {
		ShellProperties shellProperties = null;
		if (storage == null) shellProperties = new ShellProperties.Builder("Работа с данными пользователей", "question.png", display).size(size).build(); else
		if (storage.getStorageName().startsWith("jdbc:h2:")) shellProperties = new ShellProperties.Builder("Работа с базой данных", "database.png", display).size(size).build(); else
		if (storage.getStorageName().endsWith(".txt")) shellProperties = new ShellProperties.Builder("Работа с файлом", "file.png", display).size(size).build();
		return shellProperties;
	}
	
	public ShellProperties getShellProperties (Storage storage, Color backColor) {
		ShellProperties shellProperties = null;
		if (storage == null) shellProperties = new ShellProperties.Builder("Работа с данными пользователей", "question.png", display).backColor(backColor).build(); else
		if (storage.getStorageName().startsWith("jdbc:h2:")) shellProperties = new ShellProperties.Builder("Работа с базой данных", "database.png", display).backColor(backColor).build(); else
		if (storage.getStorageName().endsWith(".txt")) shellProperties = new ShellProperties.Builder("Работа с файлом", "file.png", display).backColor(backColor).build();
		return shellProperties;
	}
	
	public ShellProperties getShellProperties (Storage storage) {
		ShellProperties shellProperties = null;
		if (storage == null) shellProperties = new ShellProperties.Builder("Работа с данными пользователей", "question.png", display).build(); else
		if (storage.getStorageName().startsWith("jdbc:h2:")) shellProperties = new ShellProperties.Builder("Работа с базой данных", "database.png", display).build(); else
		if (storage.getStorageName().endsWith(".txt")) shellProperties = new ShellProperties.Builder("Работа с файлом", "file.png", display).build();
		return shellProperties;
	}
}
	
