package commands;

import storages.Storage;
import user.User;

/**
 * Класс предназначен для реализации команды удаления пользователя.
 */
public class CommandDelete implements Command {
	
	private Storage storage;
	private User user;
	
	/**
	 * Конструктор класса CommandDelete.
	 * @param storage - хранилище данных пользователей
	 * @param user - объект класса User (пользователь, которого необходимо удалить из хранилища)
	 */
	public CommandDelete (Storage storage, User user) {
		this.storage = storage;
		this.user = user;
	}
	
	@Override
	public void execute() throws Exception {
		storage.deleteUser(user.getId());
	}
	
	@Override public void undo() throws Exception {
		storage.addUser(user);
	}
	
	@Override
	public void redo() throws Exception {
		storage.deleteUser(user.getId());
	}
}