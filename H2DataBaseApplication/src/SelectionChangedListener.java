import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;

public class SelectionChangedListener implements ISelectionChangedListener {
	@Override
	public void selectionChanged(SelectionChangedEvent arg0) {
	}
	public User getPrevUser() {
		User user = new User();
		return user;
	}
}
