package user;
import org.eclipse.jface.viewers.IStructuredContentProvider;

/**
 * Класс предназначен для реализации возможности предоставления компоненту TableViewer данных для хранения, редактирования и удаления.
 */
public class UsersContentProvider implements IStructuredContentProvider 
{
	/**
	 * Метод для возврата массива, содержащего данные всех пользователей хранилища.
	 */
	@Override
	public Object[] getElements(Object a_inputElement) 
	{
		if (a_inputElement instanceof UsersModelProvider)
		{
			try 
			{
				return ((UsersModelProvider)a_inputElement).getUsersData().toArray();
			} catch (Exception a_e) 
			{
				System.out.println(a_e.getMessage());
			}
		}
		return new Object[0];
	}
}
