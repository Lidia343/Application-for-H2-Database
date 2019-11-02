import java.util.Stack;

public class CommandsExecuter {
	
	Stack<Command> commands;
	
	public CommandsExecuter () {
		commands = new Stack<Command>();
	}
	
	public void clearCommandsStack() {
		commands.clear();
	}
	
	public void executeCommand(Command command) {
		commands.push(command);
		commands.peek().execute();
	}
	
	public void undoLastCommand() {
		commands.pop().undo();
	}
	
	public int getCommandsStackSize() {
		return commands.size();
	}
}
