import org.eclipse.swt.SWT;

/**
 *  Класс предназначен для определения наличия ошибок ввода данных пользователем.
 */
public class ErrorChecker {

	private String name;
	private String surname;
	private String age;
	private String errorMessage;
	private int messageCode;
	
	public ErrorChecker() {
	}
	
	/**
	 *  Метод для определения ошибок ввода.
	 */
	public void checkUserInput() {
		
		if (name.equals("") || surname.equals("") || age.equals("")) {
			messageCode = SWT.ICON_WARNING;
			errorMessage = "Все поля должны быть заполнены";
			return;
		}
		if (!isName(name)) {
			errorMessage = "Значение поля \"Имя\" должно содержать только символы русского или английского алфавита. Длина имени не должна превышать 50 символов и быть менее 2 символов.";
			return;
		}
		if (!isName(surname)) {
			errorMessage = "Значение поля \"Фамилия\" должно содержать только символы русского или английского алфавита. Длина фамилии не должна превышать 50 символов и быть менее 2 символов.";
			return;
		}
		
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
			return;
		}
	}
	
	/**
	 *  Метод для определения того, является ли переданная строка именем/фамилией.
	 * @param name - строка
	 * @return true - если является, false - не является
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
	 * Метод устанавливает конфигурацию класса.
	 * @param name - имя
	 * @param surname - фамилия
	 * @param age - возраст
	 */
	public void setConfig(String name, String surname, String age) {
		this.name = name;
		this.surname = surname;
		this.age = age;
		errorMessage = "";
		messageCode = SWT.OK;
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
