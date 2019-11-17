import org.eclipse.jface.viewers.TableViewer;

public class AgeEditingSupport extends UserEditingSupport {

	public AgeEditingSupport(TableViewer viewer, TableViewerUserEditingListener userEditingListener, ErrorInputListener errorInputListener) {
		super(viewer, userEditingListener, errorInputListener);
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
		userEditingListener.changeUserAgeInStorage((User) element, Integer.parseInt(String.valueOf(userInputValue)));
	}
}
