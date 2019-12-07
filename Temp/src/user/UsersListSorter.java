package user;
import java.util.Comparator;

/**
 * Класс предназначен для сортировки данных пользователей по первичному ключу.
 */
public class UsersListSorter implements Comparator <User>
{
	@Override
	public int compare (User a_user1, User a_user2) 
	{
		int id1 = ((User)a_user1).getId();
		int id2 = ((User)a_user2).getId();
		if (id1 > id2) return 1;
		if (id1 < id2) return -1;
		return 0;
	}
}
