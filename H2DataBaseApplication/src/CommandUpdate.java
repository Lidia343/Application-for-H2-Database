import java.util.ArrayList;

public class CommandUpdate implements Command{
	
	private Storage storage;
	private User user;
	
	public CommandUpdate (Storage storage) {
		this.storage = storage;
	}
	
	@Override
	public void execute(User user) {
		ArrayList<User> users = new ArrayList<User>();
		users = storage.getUsersDataSet(false).getUsersList();
		for (User temp : users) {
			if (temp.getId() == user.getId())
				this.user = temp;
		}
		storage.updateUser(user);
	}
	
	@Override public void undo() {
		storage.updateUser(user);
	}
}