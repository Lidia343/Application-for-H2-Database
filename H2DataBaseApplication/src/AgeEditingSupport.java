import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

public class AgeEditingSupport extends EditingSupport {

	private final TableViewer viewer;
	private final CellEditor editor;

	public AgeEditingSupport(TableViewer viewer) {
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
	    return ((User) element).getAge();
	}

	@Override
	protected void setValue(Object element, Object userInputValue) {
		/*String temp = String.valueOf(userInputValue);
		((User) element).setAge(Integer.parseInt(temp));*/
		((User) element).setAge((Integer)userInputValue);
	    viewer.update(element, null);
	}
}
