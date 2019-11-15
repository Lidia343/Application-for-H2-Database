import org.eclipse.jface.viewers.TableViewer;

public class NameEditingSupport extends UserEditingSupport {
	
	public NameEditingSupport(TableViewer viewer, TableViewerUserEditingListener userEditingListener, ErrorInputListener errorInputListener) {
		super(viewer, userEditingListener, errorInputListener);
	}

	@Override
	protected Object getValue(Object element) {
	    return ((User) element).getName();
	}

	@Override
	protected void setValue(Object element, Object userInputValue) {
		ErrorChecker nameChecker = new ErrorChecker();
		nameChecker.checkName(String.valueOf(userInputValue));
		if (!nameChecker.getErrorMesssage().equals("")) {
			errorInputListener.createErrorMessage(nameChecker.getErrorMesssage());
			return;
		}
		User prevUser = (User)element;
		User nextUser = (User)element;
	    nextUser.setName(String.valueOf(userInputValue));
	    userEditingListener.changeUserInStorage(prevUser, nextUser);
	    viewer.update((Object)nextUser, null);
	}
}
