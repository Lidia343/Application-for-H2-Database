import java.util.ArrayList;

public class CommandsExecuter {
	
	private ArrayList<Command> commands;
	private int index;
	private int undoingCounter;
	
	public CommandsExecuter () {
		commands = new ArrayList<Command>();
		index = -1;
		undoingCounter = 0;
	}
	
	public void clearCommandsStack() {
		commands.clear();
	}
	
	public void executeCommand(Command command) {
		commands.add(command);
		commands.get(commands.size() - 1).execute();
		index++;
	}
	
	public void undoLastCommand() {
		if ((index > -1) && (index < commands.size())) {
			commands.get(index).undo();
			undoingCounter++;
			index--; 
		} 
	}
	
	public void updateIndex() {
		index = -1;
		undoingCounter = 0;
	}
	
	public void redoLastUndoing() {
		if (undoingCounter > 0) { 
			commands.get(index + 1).redo();
			index++;
			undoingCounter--;
		}
	}
	
	public int getCommandsStackSize() {
		return commands.size();
	}
}
