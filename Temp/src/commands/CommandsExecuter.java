package commands;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс предназначен для управления списком команд.
 */
public class CommandsExecuter 
{
	private List<Command> m_commands;
	private int m_index;
	
	/**
	 * Конструктор класса CommandsExecuter.
	 */
	public CommandsExecuter () 
	{
		m_commands = new ArrayList<>();
		m_index = -1;
	}
	
	/**
	 * Метод очищает список команд и сбрасывает указатель списка команд.
	 */
	public void reset () 
	{
		m_commands.clear();
		m_index = -1;
	}
	
	/**
	 * Метод выполняет переданную команду.
	 */
	public void execute(Command a_command) throws Exception 
	{
		m_commands.add(a_command);
		a_command.execute();
		m_index++;
	}
	
	/**
	 * Метод отменяет последнюю команду.
	 */
	public void undo() throws Exception 
	{
		if ((m_index > -1) && (m_index < m_commands.size())) 
		{
			m_commands.get(m_index).undo();
			m_index--; 
		} 
	}
	
	/**
	 * Метод отменяет отмену команды.
	 */
	public void redo() throws Exception 
	{
		if ((m_index >= -1) && (m_index < m_commands.size() - 1)) 
		{
			m_commands.get(m_index + 1).redo();
			m_index++;
		}
	}
	
	/**
	 * Метод возвращает размер списка команд.
	 */
	public int getCommandsListSize() 
	{
		return m_commands.size();
	}
}
