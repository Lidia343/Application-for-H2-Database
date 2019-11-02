import java.util.Stack;

public class CommandsExecuter {
	
	Stack<Command> commands;
	
	public CommandsExecuter () {
		commands = new Stack<Command>();
	}
	
	public void clearCommandsStack() {
		commands.clear();
	}
	
	public void executeCommand(Command command, User user) {
		commands.push(command);
		commands.peek().execute(user);
	}
	
	public void undoLastCommand() {
		commands.pop().undo();
	}
	
	public int getCommandsStackSize() {
		return commands.size();
	}
}
