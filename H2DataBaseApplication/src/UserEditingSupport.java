import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

/**
 * Класс предназначен для предоставления наследующим данный класс классам функционала поддержки 
 * редактирования данных пользователем непосредственно в столбце таблицы Table.
 */
public class UserEditingSupport extends EditingSupport {

	protected final TableViewer viewer;
	private final CellEditor editor;
	protected TableViewerUserEditingListener userEditingListener;
	protected ErrorInputListener errorInputListener;

	/**
	 * Конструктор класса UserEditingSupport.
	 * @param viewer - объект класса TableViewer, связанный с таблицей
	 * @param userEditingListener - слушатель нажатия на столбец таблицы
	 * @param errorInputListener - слушатель ввода в таблицу неподдерживаемого столбцом значения 
	 */
	public UserEditingSupport(TableViewer viewer, TableViewerUserEditingListener userEditingListener, ErrorInputListener errorInputListener) {
		super(viewer);
		this.viewer = viewer;
		this.editor = new TextCellEditor(viewer.getTable());
		this.userEditingListener = userEditingListener;
		this.errorInputListener = errorInputListener;
	}

	/**
	 * Метод для возврата редактора столбца (CellEditor editor).
	 * @param element - столбец таблицы
	 */
	@Override
	protected CellEditor getCellEditor(Object element) {
		return editor;
	}

	/**
	 * Метод, устанавливающий возможность редактирования столбца.
	 * @param element - столбец таблицы
	 */
	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	/**
	 * Метод для возврата введённого пользвоателем значения.
	 * @param value - введённое значение
	 */
	@Override
	protected Object getValue(Object value) {
		return null;
	}

	/**
	 * Метод для вызова одного из методов слушателя TableViewerUserEditingListener 
	 * (выбор метода зависит от изменяемого столбца) для изменения одного из свойств 
	 * переданной строки таблицы на введённое пользователем в таблицу значение, если оно корректно.
	 * @param element - строка таблицы 
	 * @param value - введённое значение
	 */
	@Override
	protected void setValue(Object element, Object value) {
	}
}
