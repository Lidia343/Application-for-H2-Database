import java.util.ArrayList;

public class CommandAdd implements Command{
	
	private Storage storage;
	
	public CommandAdd (Storage storage) {
		this.storage = storage;
	}
	
	@Override
	public void execute(User user) {
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
		System.out.println(Integer.toString(user.getId()));
	}
}
