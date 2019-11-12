import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;

public class IsActiveEditingSupport extends EditingSupport {

	private final TableViewer viewer;

	public IsActiveEditingSupport(TableViewer viewer) {
		super(viewer);
	    this.viewer = viewer;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new CheckboxCellEditor(null, SWT.CHECK | SWT.READ_ONLY);
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		User user = (User) element;
	    return user.isActive();
	}

	@Override
	protected void setValue(Object element, Object value) {
		User user = (User) element;
	    user.setIsActive((Boolean) value);
	    viewer.update(element, null);
	}
}
