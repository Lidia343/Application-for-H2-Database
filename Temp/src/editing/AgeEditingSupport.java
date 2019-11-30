package editing;
import org.eclipse.jface.viewers.TableViewer;

import errors.ErrorChecker;
import errors.ErrorInputListener;
import user.User;
import user.UserEditingListener;

/**
 * Класс предназначен для реализации поддержки редактирования столбца "Возраст" таблицы Table.
 */
public class AgeEditingSupport extends UserEditingSupport {

	/**
	 * Конструктор класса AgeEditingSupport.
	 * @param viewer - объект класса TableViewer, связанный с таблицей
	 * @param userEditingListener - слушатель нажатия на столбец таблицы
	 * @param errorInputListener - слушатель ввода в таблицу неподдерживаемого столбцом значения 
	 */
	public AgeEditingSupport(TableViewer viewer, UserEditingListener userEditingListener, ErrorInputListener errorInputListener) {
		super(viewer, userEditingListener, errorInputListener);
	}

	@Override
	protected Object getValue(Object element) {
		return Integer.toString(((User) element).getAge());
	}

	@Override
	protected void setValue(Object element, Object userInputValue) {
		ErrorChecker ageChecker = new ErrorChecker();
		ageChecker.checkAge(String.valueOf(userInputValue));
		if (!ageChecker.getErrorMesssage().equals("")) {
			errorInputListener.createMessage(ageChecker.getErrorMesssage());
			return;
		}
		userEditingListener.changeUserAge((User) element, Integer.parseInt(String.valueOf(userInputValue)));
	}
}