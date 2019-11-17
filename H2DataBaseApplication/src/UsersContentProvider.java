import org.eclipse.jface.viewers.IStructuredContentProvider;

public class UsersContentProvider implements IStructuredContentProvider {
	
	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof ModelProvider)
			try {
				return ((ModelProvider)inputElement).getUsersData().toArray();
			} catch (Exception e) {
				return null;
			}
		return null;
	}
}
