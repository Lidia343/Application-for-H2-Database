import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

public class SurnameEditingSupport extends EditingSupport {
	
	private final TableViewer viewer;
	private final CellEditor editor;

	public SurnameEditingSupport(TableViewer viewer) {
		super(viewer);
	    this.viewer = viewer;
	    this.editor = new TextCellEditor(viewer.getTable());
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
	    return ((User) element).getSurname();
	}

	@Override
	protected void setValue(Object element, Object userInputValue) {
	    ((User) element).setSurname(String.valueOf(userInputValue));
	    viewer.update(element, null);
	}
}