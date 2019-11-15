import org.eclipse.jface.viewers.TableViewer;

public class SurnameEditingSupport extends UserEditingSupport {
	
	public SurnameEditingSupport(TableViewer viewer, TableViewerUserEditingListener userEditingListener, ErrorInputListener errorInputListener) {
		super(viewer, userEditingListener, errorInputListener);
	}

	@Override
	protected Object getValue(Object element) {
	    return ((User) element).getSurname();
	}

	@Override
	protected void setValue(Object element, Object userInputValue) {
		ErrorChecker surnameChecker = new ErrorChecker();
		surnameChecker.checkName(String.valueOf(userInputValue));
		if (!surnameChecker.getErrorMesssage().equals("")) {
			errorInputListener.createErrorMessage(surnameChecker.getErrorMesssage());
			return;
		}
		User prevUser = (User)element;
		User nextUser = (User)element;
		nextUser.setSurname(String.valueOf(userInputValue));
	    userEditingListener.changeUserInStorage(prevUser, nextUser);
	    viewer.update(nextUser, null);
	}
}