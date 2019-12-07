package editing;

import org.eclipse.jface.viewers.TableViewer;

import errors.ErrorChecker;
import user.User;
import user.UserEditingListener;

/**
 * Класс предназначен для реализации поддержки редактирования столбца "Имя" таблицы Table.
 */
public class NameEditingSupport extends UserEditingSupport 
{
	/**
	 * Конструктор класса AgeEditingSupport.
	 * @param a_viewer - объект класса TableViewer, связанный с таблицей
	 * @param a_userEditingListener - слушатель нажатия на столбец таблицы
	 * @param a_errorInputListener - слушатель ввода в таблицу неподдерживаемого столбцом значения 
	 */
	public NameEditingSupport(TableViewer a_viewer, UserEditingListener a_userEditingListener, InputValidationResultHandler a_errorInputListener) 
	{
		super(a_viewer, a_userEditingListener, a_errorInputListener);
	}

	@Override
	protected Object getValue(Object a_element) 
	{
	    return ((User) a_element).getName();
	}

	@Override
	protected void setValue(Object a_element, Object a_userInputValue) 
	{
		ErrorChecker nameChecker = new ErrorChecker();
		nameChecker.checkName(String.valueOf(a_userInputValue));
		if (!nameChecker.getErrorMesssage().equals("")) 
		{
			m_errorInputListener.createMessage(nameChecker.getErrorMesssage());
			return;
		}
		m_userEditingListener.changeUserName((User)a_element, String.valueOf(a_userInputValue));
	}
}
