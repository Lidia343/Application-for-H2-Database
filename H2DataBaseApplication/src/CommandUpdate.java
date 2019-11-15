import java.util.ArrayList;
import java.util.List;

/**
 * Класс предназначен для реализации команды обновления пользователя.
 */
public class CommandUpdate implements Command {
	
	private Storage storage;
	private User prevUser;
	private User nextUser;
	
	/**
	 * Конструктор класса CommandUpdate.
	 * @param storage - хранилище данных пользователей
	 * @param user - объект класса User
	 */
	public CommandUpdate (Storage storage, User prevUser, User nextUser) {
		this.storage = storage;
		this.prevUser = prevUser;
		this.nextUser = nextUser;
	}
	
	@Override
	public void execute() throws Exception {
		//List<User> users = new ArrayList<>();
		//users = storage.getUsersDataSet(false, false);
		//for (User temp : users) 
			//if (temp.getId() == nextUser.getId()) {
				storage.updateUser(nextUser);
				//prevUser = temp;
				//prevUser = selectionChangedListener.getPrevUser();
				//System.out.println(prevUser.getName());
				//break;
			//}
	}
	
	@Override public void undo() throws Exception {
		storage.updateUser(prevUser);
	}
	
	@Override
	public void redo() throws Exception {
		storage.updateUser(nextUser);
	}
}