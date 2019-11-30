package editing;
import org.eclipse.jface.viewers.TableViewer;

import errors.ErrorChecker;
import errors.ErrorInputListener;
import user.User;
import user.UserEditingListener;

/**
 * Класс предназначен для реализации поддержки редактирования столбца "Фамилия" таблицы Table.
 */
public class SurnameEditingSupport extends UserEditingSupport {
	
	/**
	 * Конструктор класса SurnameEditingSupport.
	 * @param viewer - объект класса TableViewer, связанный с таблицей
	 * @param userEditingListener - слушатель нажатия на столбец таблицы
	 * @param errorInputListener - слушатель ввода в таблицу неподдерживаемого столбцом значения 
	 */
	public SurnameEditingSupport(TableViewer viewer, UserEditingListener userEditingListener, ErrorInputListener errorInputListener) {
		super(viewer, userEditingListener, errorInputListener);
	}

	@Override
	protected Object getValue(Object element) {
	    return ((User) element).getSurname();
	}

	@Override
	protected void setValue(Object element, Object userInputValue) {
		ErrorChecker surnameChecker = new ErrorChecker();
		surnameChecker.checkName(String.valueOf(userInputValue));
		if (!surnameChecker.getErrorMesssage().equals("")) {
			errorInputListener.createMessage(surnameChecker.getErrorMesssage());
			return;
		}
		userEditingListener.changeUserSurname((User)element, String.valueOf(userInputValue));
	}
}