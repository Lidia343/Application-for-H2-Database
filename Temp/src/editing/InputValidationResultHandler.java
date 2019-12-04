package editing;

/**
 * Интерфейс обработчика результата проверки введённого значения.
 */
public interface InputValidationResultHandler {
	
	/**
	 * Метод для вывода сообщения о результате проверки пользователю.
	 * @param message - сообщение
	 */
	public void createMessage(String message);
}
