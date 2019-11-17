import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

public class UserEditingSupport extends EditingSupport {

	protected final TableViewer viewer;
	private final CellEditor editor;
	protected TableViewerUserEditingListener userEditingListener;
	protected ErrorInputListener errorInputListener;

	public UserEditingSupport(TableViewer viewer, TableViewerUserEditingListener userEditingListener, ErrorInputListener errorInputListener) {
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
	protected Object getValue(Object arg0) {
		return null;
	}

	@Override
	protected void setValue(Object arg0, Object arg1) {
	}
}
