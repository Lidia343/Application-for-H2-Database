
public class CommandDelete implements Command{
	
	private Storage storage;
	private User user;
	
	public CommandDelete (Storage storage, User user) {
		this.storage = storage;
		this.user = user;
	}
	
	@Override
	public void execute() throws Exception {
		storage.deleteUser(user.getId());
	}
	
	@Override public void undo() throws Exception {
		storage.addUser(user, user.getId());
	}
	
	@Override
	public void redo() throws Exception {
		storage.deleteUser(user.getId());
	}
}
