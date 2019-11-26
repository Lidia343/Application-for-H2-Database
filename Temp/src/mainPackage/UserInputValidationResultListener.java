package mainPackage;

/**
 * Интерфейс слушателя ввода данных.
 */
public interface UserInputValidationResultListener {
	
	/**
	 * Метод для вывода сообщения пользователю.
	 * @param message - сообщение
	 */
	public void createMessage(String message);
}
