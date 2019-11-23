package mainPackage;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;

/**
 * Класс предназначен для реализации поддержки редактирования столбца "Активен" таблицы Table.
 */
public class IsActiveEditingSupport extends UserEditingSupport {

	/**
	 * Конструктор класса IsActiveEditingSupport.
	 * @param viewer - объект класса TableViewer, связанный с таблицей
	 * @param userEditingListener - слушатель нажатия на столбец таблицы
	 */
	public IsActiveEditingSupport(TableViewer viewer, UserEditingListener userEditingListener) {
		super(viewer, userEditingListener, null);
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new CheckboxCellEditor(null, SWT.CHECK | SWT.READ_ONLY);
	}

	@Override
	protected Object getValue(Object element) {
	    return ((User) element).isActive();
	}

	@Override
	protected void setValue(Object element, Object value) {
		userEditingListener.changeUserIsActive((User)element, Boolean.parseBoolean(String.valueOf(value)));
	}
}
