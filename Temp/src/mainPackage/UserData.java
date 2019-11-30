package mainPackage;

/**
 * Класс необходим для предоставления доступа к открытым статическим 
 * неизменяемым полям, каждое из которых соответствует одному из столбцов 
 * таблицы, в которой хранятся данные пользователей.
 */
public interface UserData {
	int FIRSTNAME = 0;
	int LASTNAME = 1;
	int AGE = 2;
	int ISACTIVE = 3;
}
