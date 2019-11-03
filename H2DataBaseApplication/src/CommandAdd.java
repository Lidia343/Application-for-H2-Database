import java.util.ArrayList;

public class CommandAdd implements Command{
	
	private Storage storage;
	private User user;

	public CommandAdd (Storage storage, User user) {
		this.storage = storage;
		this.user = user;
	}
	
	@Override
	public void execute() {
		storage.addUser(user);	
		/*Поскольку у поля user в данном классе не установлено значение ПК (оно устанавливается непосредственно в хранилище),
		необходимо, предусматривая возможное восстановление пользователя в дальнейшем, после добавления user в хранилище
		присвоить полю user аналогичное значение из текущего хранилища, но с установленным ПК:*/
		ArrayList<User> users = new ArrayList<User>();
		users = storage.getUsersDataSet(false);
		for (User temp : users) {
			user = temp;
		}
	}
	
	@Override public void undo() {
		User user = null;
		ArrayList<User> users = new ArrayList<User>();
		users = storage.getUsersDataSet(false);
		for (User temp : users) user = temp;
		if (users.size() != 0)
		storage.deleteUser(user.getId());
	}
	
	@Override
	public void redo() {
		storage.addUser(user, user.getId());
	}
}
