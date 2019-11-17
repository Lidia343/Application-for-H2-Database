import java.util.ArrayList;
import java.util.List;

/**
 * Класс предназначен для управления списком команд.
 */
public class CommandsExecuter {
	
	private List<Command> commands;
	private int index;
	
	/**
	 * Конструктор класса CommandsExecuter.
	 */
	public CommandsExecuter () {
		commands = new ArrayList<>();
		index = -1;
	}
	
	/**
	 * Метод очищает список команд и сбрасывает указатель списка команд.
	 */
	public void reset () {
		commands.clear();
		index = -1;
	}
	
	/**
	 * Метод выполняет переданную команду.
	 */
	public void execute(Command command) throws Exception {
		commands.add(command);
		command.execute();
		index++;
	}
	
	/**
	 * Метод отменяет последнюю команду.
	 */
	public void undo() throws Exception {
		if ((index > -1) && (index < commands.size())) {
			commands.get(index).undo();
			index--; 
		} 
	}
	
	/**
	 * Метод отменяет отмену команды.
	 */
	public void redo() throws Exception {
		if ((index >= -1) && (index < commands.size() - 1)) {
			commands.get(index + 1).redo();
			index++;
		}
	}
	
	/**
	 * Метод возвращает размер списка команд.
	 */
	public int getCommandsListSize() {
		return commands.size();
	}
}
