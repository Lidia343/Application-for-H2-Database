
public interface Command {
	
	void execute(User user);
	
	void undo();
}
