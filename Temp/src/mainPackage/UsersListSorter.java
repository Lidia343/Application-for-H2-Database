package mainPackage;

import java.util.Comparator;

/**
 * Класс предназначен для сортировки данных пользователей по первичному ключу.
 */
public class UsersListSorter implements Comparator <User>{
	@Override
	public int compare (User user1, User user2) {
		int id1 = ((User)user1).getId();
		int id2 = ((User)user2).getId();
		if (id1 > id2) return 1;
		if (id1 < id2) return -1;
		return 0;
	}
}
