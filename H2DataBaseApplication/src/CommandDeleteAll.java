import java.util.List;

/**
 * Класс предназначен для реализации команды удаления всех пользователей.
 */
public class CommandDeleteAll implements Command{
	
	private final Storage storage;
	private List<User> users;
	
	/**
	 * Конструктор класса CommandDelete.
	 * @param storage - хранилище данных пользователей
	 * @param user - объект класса User
	 * @throws Exception 
	 */
	public CommandDeleteAll (Storage storage) {
		this.storage = storage;
	}
	
	@Override
	public void execute() throws Exception {
		users = storage.getUsersDataSet(false);
		storage.deleteAllUsers();
	}
	
	@Override public void undo() throws Exception {
		for (User user : users) {
			storage.addUser(user, user.getId());	
		}
	}
	
	@Override
	public void redo() throws Exception {
		storage.deleteAllUsers();
	}
}
