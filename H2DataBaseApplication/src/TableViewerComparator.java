import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

public class TableViewerComparator extends ViewerComparator {
	private int propertyIndex;
	private static final int DESCENDING = 1; // нисходящее направление
	private int direction = DESCENDING;

	public TableViewerComparator() {
		this.propertyIndex = 0;
		direction = DESCENDING;
	}

	public int getDirection() {
		return direction == 1 ? SWT.DOWN : SWT.UP;
	}

	public void setColumn(int column) {
		if (column == this.propertyIndex) {
			direction = 1 - direction;
		} else {
			this.propertyIndex = column;
			direction = DESCENDING;
		}
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		User u1 = (User) e1;
		User u2 = (User) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			if (u1.getId() == u2.getId()) {
				rc = 0;
			} else
				rc = ((u1.getId() > u2.getId()) ? 1 : -1);
			break;
		case 1:
			rc = u1.getName().compareTo(u2.getName());
			break;
		case 2:
			rc = u1.getSurname().compareTo(u2.getSurname());
			break;
		case 3:
			if (u1.getAge() == u2.getAge()) {
				rc = 0;
			} else
				rc = ((u1.getAge() > u2.getAge()) ? 1 : -1);
			break;
		case 4:
			if (u1.isActive() == u2.isActive()) {
				rc = 0;
			} else
				rc = (u1.isActive() ? 1 : -1);
			break;
		default:
			rc = 0;
		}
		if (direction == DESCENDING) {
			rc = -rc;
		}
		return rc;
	}
}