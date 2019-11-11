import java.util.ArrayList;
import java.util.List;

/**
 * Класс предназначен для реализации команды добавления пользователя.
 */
public class CommandAdd implements Command{
	
	private Storage storage;
	private User user;

	/**
	 * Конструктор класса CommandAdd.
	 * @param storage - хранилище данных пользователей
	 * @param user - объект класса User
	 */
	public CommandAdd (Storage storage, User user) {
		this.storage = storage;
		this.user = user;
	}
	
	@Override
	public void execute() throws Exception {
		storage.addUser(user);	
		/*Поскольку у поля user в данном классе не установлено значение ПК (оно устанавливается непосредственно в хранилище),
		необходимо, предусматривая возможное восстановление пользователя в дальнейшем, после добавления user в хранилище
		присвоить полю user аналогичное значение из текущего хранилища, но с установленным ПК:*/
		List<User> users = new ArrayList<>();
		users = storage.getUsersDataSet(false, false);
		for (User temp : users) {
			user = temp;
		}
	}
	
	@Override public void undo() throws Exception {
		User user = null;
		List<User> users = new ArrayList<>();
		users = storage.getUsersDataSet(false, false);
		for (User temp : users) user = temp;
		if (users.size() != 0)
		storage.deleteUser(user.getId());
	}
	
	@Override
	public void redo() throws Exception {
		storage.addUser(user, user.getId());
	}
}
