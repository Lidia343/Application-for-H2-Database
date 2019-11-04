import java.util.ArrayList;

/**
 * Класс предназначен для управления списком команд.
 */
public class CommandsExecuter {
	
	private ArrayList<Command> commands;
	private int index;
	private int undoingCounter;
	
	/**
	 * Конструктор класса CommandsExecuter.
	 */
	public CommandsExecuter () {
		commands = new ArrayList<Command>();
		index = -1;
		undoingCounter = 0;
	}
	
	/**
	 * Метод очищает список команд.
	 */
	public void clearCommandsStack() {
		commands.clear();
	}
	
	/**
	 * Метод выполняет переданную команду.
	 */
	public void executeCommand(Command command) throws Exception {
		commands.add(command);
		commands.get(commands.size() - 1).execute();
		index++;
	}
	
	/**
	 * Метод отменяет последнюю команду.
	 */
	public void undoLastCommand() throws Exception {
		if ((index > -1) && (index < commands.size())) {
			commands.get(index).undo();
			undoingCounter++;
			index--; 
		} 
	}
	
	/**
	 * Метод сбрасывает указатель списка команд и счётчик отмен команд.
	 */
	public void updateIndex() {
		index = -1;
		undoingCounter = 0;
	}
	
	/**
	 * Метод отменяет отмену команды.
	 */
	public void redoLastUndoing() throws Exception {
		if (undoingCounter > 0) { 
			commands.get(index + 1).redo();
			index++;
			undoingCounter--;
		}
	}
	
	/**
	 * Метод возвращает размер списка команд.
	 */
	public int getCommandsListSize() {
		return commands.size();
	}
}
