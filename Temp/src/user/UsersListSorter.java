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
		int a_id1 = ((User)a_user1).getId();
		int a_id2 = ((User)a_user2).getId();
		if (a_id1 > a_id2) return 1;
		if (a_id1 < a_id2) return -1;
		return 0;
	}
}
