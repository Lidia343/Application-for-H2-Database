package mainPackage;
import org.eclipse.swt.SWT;

/**
 *  Класс предназначен для определения наличия ошибок ввода данных пользователем.
 */
public class ErrorChecker {

	private String errorMessage;
	private int messageCode;
	
	/**
	 * Конструктор класса ErorChecker. 
	 */
	public ErrorChecker () {
		errorMessage = "";
		messageCode = SWT.OK;
	}
	
	/**
	 * Метод проверяет переданное имя на наличие ошибок.
	 */
	public void checkName(String name) {
		if (isEmptyInput (name)) return;
		if (!isName(name)) 
			errorMessage = "Значение поля \"Имя\"/\"Фамилия\" должно содержать только символы русского или английского алфавита. Длина имени/фамилии не должна превышать 50 символов и быть менее 2 символов.";
	}
	
	/**
	 * Метод проверяет переданный возраст на наличие ошибок.
	 */
	public void checkAge (String age) {
		if (isEmptyInput (age)) return;
		int intAge = 18;
		try { 
			intAge = Integer.parseInt(age);
		} catch (NumberFormatException e) {
			messageCode = SWT.ICON_WARNING; 
			errorMessage = "Значение поля \"Возраст\" должно быть целым числом в пределах от 18 до 200.";
			return;
		}	
		if (!((intAge >= 18) && (intAge <= 200))){ 
			messageCode = SWT.ICON_WARNING;
			errorMessage = "Значение поля \"Возраст\" должно быть целым числом в пределах от 18 до 200.";
		}
	}
	
	/**
	 * Метод проверяет переданное количество пользователей на наличие ошибок.
	 */
	public void checkUserNumbers(String userNumbers) {
		if (isEmptyInput (userNumbers)) return;
		try { 
			Integer.parseInt(userNumbers);
		} catch (NumberFormatException e) {
			messageCode = SWT.ICON_WARNING; 
			errorMessage = "Введите целое число";
		}
	}
	
	/**
	 * Метод проверяет, равна ли ссылка на переданную строку равной null и, если нет, является ли строка пустой.
	 * @return true - если ссылка на строку input равна null или строка input пустая, false - в остальных случаях
	 */
	private boolean isEmptyInput(String input) {
		if (input == null) {
			messageCode = SWT.ERROR;
			errorMessage = "Значение переданного параметра равно null.";
			return true;
		}
		if (input.equals("")) {
			messageCode = SWT.ICON_WARNING;
			errorMessage = "Все поля должны быть заполнены";
			return true;
		}
		return false;
	}
	
	/**
	 *  Метод для определения того, является ли переданная строка именем/фамилией.
	 * @param name - строка
	 * @return true - строка является именем/фамилией, false - иначе
	 */
	private boolean isName(String name) {
		for (int i = 0; i < name.length(); i++) 
			if (!(((name.charAt(i) >= 'a') && (name.charAt(i) <= 'z')) ||
			   ((name.charAt(i) >= 'A') && (name.charAt(i) <= 'Z')) || 
			   ((name.charAt(i) >= 'а') && (name.charAt(i) <= 'я')) ||
			   ((name.charAt(i) >= 'А') && (name.charAt(i) <= 'Я')))) {
					messageCode = SWT.ICON_WARNING;
					return false;
				}	
			if ((name.length() < 2) || (name.length() > 50)) {
				messageCode = SWT.ICON_WARNING;
				return false;
			}
		return true;
	}
	
	/**
	 * Метод возвращает код сообщения для пользователя.
	 */
	public int getMessageCode() {
		return messageCode;
	}
	
	/**
	 * Метод возвращает сообщение об ошибке, если она произошла.
	 */
	public String getErrorMesssage() {
		return errorMessage;
	}
}
