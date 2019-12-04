package editing;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;

import user.User;
import user.UserEditingListener;

/**
 * Класс предназначен для реализации поддержки редактирования столбца "Активен" таблицы Table.
 */
public class IsActiveEditingSupport extends UserEditingSupport 
{
	/**
	 * Конструктор класса IsActiveEditingSupport.
	 * @param a_viewer - объект класса TableViewer, связанный с таблицей
	 * @param a_userEditingListener - слушатель нажатия на столбец таблицы
	 */
	public IsActiveEditingSupport(TableViewer a_viewer, UserEditingListener a_userEditingListener) 
	{
		super(a_viewer, a_userEditingListener, null);
	}

	@Override
	protected CellEditor getCellEditor(Object a_element) 
	{
		return new CheckboxCellEditor(null, SWT.CHECK | SWT.READ_ONLY);
	}

	@Override
	protected Object getValue(Object a_element) 
	{
	    return ((User) a_element).isActive();
	}

	@Override
	protected void setValue(Object a_element, Object a_value) 
	{
		m_userEditingListener.changeUserIsActive((User)a_element, Boolean.parseBoolean(String.valueOf(a_value)));
	}
}
