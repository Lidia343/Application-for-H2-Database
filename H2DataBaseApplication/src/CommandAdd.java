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
	}
	
	@Override public void undo() {
		User user = null;
		ArrayList<User> users = new ArrayList<User>();
		users = storage.getUsersDataSet(false);
		for (User temp : users) {
			user = temp;
		}
		if (users.size() != 0)
		storage.deleteUser(user.getId());
	}
}
