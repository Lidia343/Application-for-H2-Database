package temp;

/**
 * Интерфейс, необходимый для определения методов, которые должны содержать классы, реализующие различные команды.
 */
public interface Command {
	
	/**
	 * Метод для выполнения команды.
	 */
	void execute() throws Exception;
	
	/**
	 * Метод для отмены команды.
	 */
	void undo() throws Exception;
	
	/**
	 * Метод для отмены отмены команды.
	 */
	void redo() throws Exception;
}
