package editing;

/**
 * Интерфейс слушателя ввода данных.
 */
public interface InputValidationResultListener {
	
	/**
	 * Метод для вывода сообщения пользователю.
	 * @param message - сообщение
	 */
	public void createMessage(String message);
}
