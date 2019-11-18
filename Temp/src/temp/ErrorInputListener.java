package temp;

/**
 * Класс предназначен для реализации слушателя неверного ввода данных.
 */
public class ErrorInputListener {
	
	/**
	 * Метод отправляет сообщение об ошибке ввода пользователю.
	 * @param message - сообщение об ошибке
	 */
	public void createErrorMessage(String message) {
		System.out.println(message);
	}
}
