package temp;
import org.eclipse.jface.viewers.IStructuredContentProvider;

/**
 * Класс предназначен для реализации возможности предоставления компоненту TableViewer данных для хранения, редактирования и удаления.
 */
public class UsersContentProvider implements IStructuredContentProvider {
	
	/**
	 * Метод для возврата массива, содержащего данные всех пользователей хранилища.
	 */
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
