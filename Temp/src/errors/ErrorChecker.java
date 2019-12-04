package errors;
import org.eclipse.swt.SWT;

import user.UserData;

/**
 *  Класс предназначен для определения наличия ошибок ввода данных пользователем.
 */
public class ErrorChecker 
{
	private String m_errorMessage;
	private int m_messageCode;
	
	/**
	 * Конструктор класса ErorChecker. 
	 */
	public ErrorChecker () 
	{
		m_errorMessage = "";
		m_messageCode = SWT.OK;
	}
	
	/**
	 * Метод проверяет переданное имя на наличие ошибок.
	 */
	public void checkName(String a_name) 
	{
		if (isEmptyInput (a_name)) return;
		if (!isName(a_name)) 
			m_errorMessage = "Значение поля \"Имя\"/\"Фамилия\" должно содержать только символы русского или английского алфавита. Длина имени/фамилии не должна превышать 50 символов и быть менее 2 символов.";
	}
	
	/**
	 * Метод проверяет переданный возраст на наличие ошибок.
	 */
	public void checkAge (String a_age) 
	{
		if (isEmptyInput (a_age)) return;
		int m_intAge = UserData.MIN_AGE;
		try 
		{ 
			m_intAge = Integer.parseInt(a_age);
		} catch (NumberFormatException e) 
		{
			m_messageCode = SWT.ICON_WARNING; 
			m_errorMessage = "Значение поля \"Возраст\" должно быть целым числом в пределах от " +  Integer.toString(UserData.MIN_AGE) + " до " + Integer.toString(UserData.MAX_AGE) + ".";
			return;
		}	
		if (!((m_intAge >= UserData.MIN_AGE) && (m_intAge <= UserData.MAX_AGE)))
		{ 
			m_messageCode = SWT.ICON_WARNING;
			m_errorMessage = "Значение поля \"Возраст\" должно быть целым числом в пределах от " +  Integer.toString(UserData.MIN_AGE) + " до " + Integer.toString(UserData.MAX_AGE) + ".";
		}
	}
	
	/**
	 * Метод проверяет переданное количество пользователей на наличие ошибок.
	 */
	public void checkUserNumbers(String a_userNumbers) 
	{
		if (isEmptyInput (a_userNumbers)) return;
		try 
		{ 
			Integer.parseInt(a_userNumbers);
		} catch (NumberFormatException e) 
		{
			m_messageCode = SWT.ICON_WARNING; 
			m_errorMessage = "Введите целое число";
		}
	}
	
	/**
	 * Метод проверяет, равна ли ссылка на переданную строку равной null и, если нет, является ли строка пустой.
	 * @return true - если ссылка на строку a_input равна null или строка a_input пустая, false - в остальных случаях
	 */
	private boolean isEmptyInput(String a_input) 
	{
		if (a_input == null) 
			throw new IllegalArgumentException("В метод isEmptyInput() не должно передаваться значение null.");
		if (a_input.equals("")) 
		{
			m_messageCode = SWT.ICON_WARNING;
			m_errorMessage = "Все поля должны быть заполнены";
			return true;
		}
		return false;
	}
	
	/**
	 *  Метод для определения того, является ли переданная строка именем/фамилией.
	 * @param a_name - строка
	 * @return true - строка является именем/фамилией, false - иначе
	 */
	private boolean isName(String a_name) 
	{
		for (int i = 0; i < a_name.length(); i++) 
			if (!(((a_name.charAt(i) >= 'a') && (a_name.charAt(i) <= 'z')) ||
			   ((a_name.charAt(i) >= 'A') && (a_name.charAt(i) <= 'Z')) || 
			   ((a_name.charAt(i) >= 'а') && (a_name.charAt(i) <= 'я')) ||
			   ((a_name.charAt(i) >= 'А') && (a_name.charAt(i) <= 'Я')))) 
			{
				m_messageCode = SWT.ICON_WARNING;
				return false;
			}	
			if ((a_name.length() < UserData.MIN_NAME_LENGTH) || (a_name.length() > UserData.MAX_NAME_LENGTH)) 
			{
				m_messageCode = SWT.ICON_WARNING;
				return false;
			}
		return true;
	}
	
	/**
	 * Метод возвращает код сообщения для пользователя.
	 */
	public int getMessageCode() 
	{
		return m_messageCode;
	}
	
	/**
	 * Метод возвращает сообщение об ошибке, если она произошла.
	 */
	public String getErrorMesssage() 
	{
		return m_errorMessage;
	}
}
