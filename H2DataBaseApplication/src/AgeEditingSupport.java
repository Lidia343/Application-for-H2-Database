import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

public class AgeEditingSupport extends EditingSupport {

	private final TableViewer viewer;
	private final CellEditor editor;
	private TableViewerUserEditingListener userEditingListener;
	private ErrorInputListener errorInputListener;

	public AgeEditingSupport(TableViewer viewer, TableViewerUserEditingListener userEditingListener, ErrorInputListener errorInputListener) {
		super(viewer);
		this.viewer = viewer;
		this.editor = new TextCellEditor(viewer.getTable());
		this.userEditingListener = userEditingListener;
		this.errorInputListener = errorInputListener;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return editor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
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
			errorInputListener.createErrorMessage(ageChecker.getErrorMesssage());
			return;
		}
		((User) element).setAge(Integer.parseInt(String.valueOf(userInputValue)));
		userEditingListener.changeUserInStorage((User) element);
		viewer.update(element, null);
	}
}
