package editing;

/**
 * Интерфейс обработчика результата проверки введённого значения.
 */
public interface InputValidationResultHandler
{
	/**
	 * Метод для вывода сообщения о результате проверки пользователю.
	 * @param a_message - сообщение
	 */
	public void createMessage(String a_message);
}
