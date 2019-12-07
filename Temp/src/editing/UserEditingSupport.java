package editing;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import user.UserEditingListener;

/**
 * Класс предназначен для предоставления наследующим данный класс классам функционала поддержки 
 * редактирования данных пользователем непосредственно в столбце таблицы Table.
 */
public class UserEditingSupport extends EditingSupport 
{
	protected final TableViewer m_viewer;
	private final CellEditor m_editor;
	protected UserEditingListener m_userEditingListener;
	protected InputValidationResultHandler m_errorInputListener;

	/**
	 * Конструктор класса UserEditingSupport.
	 * @param a_viewer - объект класса TableViewer, связанный с таблицей
	 * @param a_userEditingListener - слушатель нажатия на столбец таблицы
	 * @param a_errorInputListener - слушатель ввода в таблицу неподдерживаемого столбцом значения 
	 */
	public UserEditingSupport(TableViewer a_viewer, UserEditingListener a_userEditingListener, InputValidationResultHandler a_errorInputListener) 
	{
		super(a_viewer);
		m_viewer = a_viewer;
		m_editor = new TextCellEditor(a_viewer.getTable());
		m_userEditingListener = a_userEditingListener;
		m_errorInputListener = a_errorInputListener;
	}

	/**
	 * Метод для возврата редактора столбца CellEditor.
	 * @param a_element - столбец таблицы
	 */
	@Override
	protected CellEditor getCellEditor(Object a_element) 
	{
		return m_editor;
	}

	/**
	 * Метод, устанавливающий возможность редактирования столбца.
	 * @param a_element - столбец таблицы
	 */
	@Override
	protected boolean canEdit(Object a_element) 
	{
		return true;
	}

	/**
	 * Метод для возврата введённого пользвоателем значения.
	 * @param a_value - введённое значение
	 */
	@Override
	protected Object getValue(Object a_value) 
	{
		return new Object();
	}

	/**
	 * Метод для вызова одного из методов слушателя TableViewerUserEditingListener 
	 * (выбор метода зависит от изменяемого столбца) для изменения одного из свойств 
	 * переданной строки таблицы на введённое пользователем в таблицу значение, если оно корректно.
	 * @param a_element - строка таблицы 
	 * @param a_value - введённое значение
	 */
	@Override
	protected void setValue(Object a_element, Object a_value) 
	{
	}
}
