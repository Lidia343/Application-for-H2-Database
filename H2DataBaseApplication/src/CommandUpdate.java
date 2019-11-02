import java.util.ArrayList;

public class CommandUpdate implements Command{
	
	private Storage storage;
	private User user;
	
	public CommandUpdate (Storage storage, User user) {
		this.storage = storage;
		this.user = user;
	}
	
	@Override
	public void execute() {
		ArrayList<User> users = new ArrayList<User>();
		users = storage.getUsersDataSet(false);
		for (User temp : users) 
			if (temp.getId() == user.getId()) {
				storage.updateUser(user);
				this.user = temp;
				break;
			}
	}
	
	@Override public void undo() {
		storage.updateUser(user);
	}
}