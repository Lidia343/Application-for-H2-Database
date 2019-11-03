import java.util.ArrayList;

public class CommandUpdate implements Command{
	
	private Storage storage;
	private User prevUser;
	private User nextUser;
	
	public CommandUpdate (Storage storage, User user) {
		this.storage = storage;
		nextUser = user;
		prevUser = null;
	}
	
	@Override
	public void execute() {
		ArrayList<User> users = new ArrayList<User>();
		users = storage.getUsersDataSet(false);
		for (User temp : users) 
			if (temp.getId() == nextUser.getId()) {
				storage.updateUser(nextUser);
				prevUser = temp;
				break;
			}
	}
	
	@Override public void undo() {
		storage.updateUser(prevUser);
	}
	
	@Override
	public void redo() {
		storage.updateUser(nextUser);
	}
}