package mainPackage;
import org.eclipse.jface.viewers.TableViewer;

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
	public AgeEditingSupport(TableViewer viewer, TableViewerUserEditingListener userEditingListener, ErrorInputListener errorInputListener) {
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
		userEditingListener.changeUserAgeInStorage((User) element, Integer.parseInt(String.valueOf(userInputValue)));
	}
}