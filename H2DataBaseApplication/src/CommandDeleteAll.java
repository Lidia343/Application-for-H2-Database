import java.util.ArrayList;

/**
 * Класс предназначен для реализации команды удаления всех пользователей.
 */
public class CommandDeleteAll implements Command{
	
	private Storage storage;
	private ArrayList<User> users = new ArrayList<User>();
	
	/**
	 * Конструктор класса CommandDelete.
	 * @param storage - хранилище данных пользователей
	 * @param user - объект класса User
	 */
	public CommandDeleteAll (Storage storage) {
		this.storage = storage;
	}
	
	@Override
	public void execute() throws Exception {
		users = storage.getUsersDataSet(false);
		for (User user : users) 
			storage.deleteUser(user.getId());
	}
	
	@Override public void undo() throws Exception {
		for (User user : users) 
			storage.addUser(user, user.getId());	
	}
	
	@Override
	public void redo() throws Exception {
		for (User user : users) 
			storage.deleteUser(user.getId());
	}
}
