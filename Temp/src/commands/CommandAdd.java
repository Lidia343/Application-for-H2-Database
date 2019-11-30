package commands;

import storages.Storage;
import user.User;

/**
 * Класс предназначен для реализации команды добавления пользователя.
 */
public class CommandAdd implements Command {
	
	private Storage storage;
	private User user;

	/**
	 * Конструктор класса CommandAdd.
	 * @param storage - хранилище данных пользователей
	 * @param user - объект класса User (пользователь, которого необходимо добавить в хранилище)
	 */
	public CommandAdd (Storage storage, User user) {
		this.storage = storage;
		this.user = user;
	}
	
	@Override
	public void execute() throws Exception {
		int currentID = storage.addUser(user);	
		user.setId(currentID);
	}
	
	@Override public void undo() throws Exception {
		storage.deleteUser(user.getId());
	}
	
	@Override
	public void redo() throws Exception {
		storage.addUser(user);
	}
}
