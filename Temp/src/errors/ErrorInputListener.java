package errors;

import editing.InputValidationResultListener;

/**
 * Класс предназначен для реализации слушателя неверного ввода данных.
 */
public class ErrorInputListener implements InputValidationResultListener {
	
	/**
	 * Метод выводит в консоли сообщение об ошибке ввода.
	 * @param message - сообщение об ошибке
	 */
	public void createMessage(String message) {
		System.out.println(message);
	}
}
