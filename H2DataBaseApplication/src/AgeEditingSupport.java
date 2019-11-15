import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

public class AgeEditingSupport extends EditingSupport {

	private final TableViewer viewer;
	private final CellEditor editor;
	private ITableViewerUserEditingListener userEditingListener;

	public AgeEditingSupport(TableViewer viewer, ITableViewerUserEditingListener userEditingListener) {
		super(viewer);
		this.viewer = viewer;
		this.editor = new TextCellEditor(viewer.getTable());
		this.userEditingListener = userEditingListener;
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
		boolean correctAge = true;
		((User) element).setAge(Integer.parseInt(String.valueOf(userInputValue)));
		userEditingListener.changeUserInStorage((User) element);
		viewer.update(element, null);
	}
}
