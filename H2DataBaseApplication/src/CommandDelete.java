/*import java.util.ArrayList;

public class CommandDelete implements Command{
	
	private Storage storage;
	private User user;
	
	public CommandDelete (Storage storage) {
		this.storage = storage;
	}
	
	@Override
	public void execute(int id) {
		ArrayList<User> list = storage.getUsersDataSet(false).getUsersList();
		User user = null;
		for (User temp : list) {
			if (temp.getId() == id) {
				user = temp;
			}
		}
		this.user = user;
		storage.deleteUser(id);
	}
	
	@Override public void undo() {
		storage.addUser(user, user.getId());
	}
}*/

public class CommandDelete implements Command{
	
	private Storage storage;
	private User user;
	
	public CommandDelete (Storage storage) {
		this.storage = storage;
	}
	
	@Override
	public void execute(User user) {
		storage.deleteUser(user.getId());
		this.user = user;
	}
	
	@Override public void undo() {
		storage.addUser(user, user.getId());
	}
}
