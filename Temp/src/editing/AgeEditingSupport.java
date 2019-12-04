package editing;

import org.eclipse.jface.viewers.TableViewer;

import errors.ErrorChecker;
import user.User;
import user.UserEditingListener;

/**
 * Класс предназначен для реализации поддержки редактирования столбца "Возраст" таблицы Table.
 */
public class AgeEditingSupport extends UserEditingSupport 
{
	/**
	 * Конструктор класса AgeEditingSupport.
	 * @param a_viewer - объект класса TableViewer, связанный с таблицей
	 * @param a_userEditingListener - слушатель нажатия на столбец таблицы
	 * @param a_errorInputListener - слушатель ввода в таблицу неподдерживаемого столбцом значения 
	 */
	public AgeEditingSupport(TableViewer a_viewer, UserEditingListener a_userEditingListener, InputValidationResultHandler a_errorInputListener) 
	{
		super(a_viewer, a_userEditingListener, a_errorInputListener);
	}

	@Override
	protected Object getValue(Object a_element) 
	{
		return Integer.toString(((User) a_element).getAge());
	}

	@Override
	protected void setValue(Object a_element, Object a_userInputValue) 
	{
		ErrorChecker m_ageChecker = new ErrorChecker();
		m_ageChecker.checkAge(String.valueOf(a_userInputValue));
		if (!m_ageChecker.getErrorMesssage().equals("")) 
		{
			m_errorInputListener.createMessage(m_ageChecker.getErrorMesssage());
			return;
		}
		m_userEditingListener.changeUserAge((User) a_element, Integer.parseInt(String.valueOf(a_userInputValue)));
	}
}