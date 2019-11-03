
public class CommandDelete implements Command{
	
	private Storage storage;
	private User user;
	
	public CommandDelete (Storage storage, User user) {
		this.storage = storage;
		this.user = user;
	}
	
	@Override
	public void execute() {
		storage.deleteUser(user.getId());
	}
	
	@Override public void undo() {
		storage.addUser(user, user.getId());
	}
	
	@Override
	public void redo() {
		storage.deleteUser(user.getId());
	}
}
