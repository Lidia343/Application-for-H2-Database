package user;

/**
 * Класс необходим для предоставления доступа к открытым статическим 
 * неизменяемым полям, каждое из которых соответствует одному из столбцов 
 * таблицы, в которой хранятся данные пользователей.
 */
public interface UserData 
{
	int FIRSTNAME = 0;
	int LASTNAME = 1;
	int AGE = 2;
	int ISACTIVE = 3;
	int MIN_AGE = 18;
	int MAX_AGE = 200;
	int MIN_NAME_LENGTH = 2;
	int MAX_NAME_LENGTH = 50;
	int MAX_USERS_COUNT_IN_TABLE_FOR_AUTOMATIC_WINDOW_PACK = 8;
}
