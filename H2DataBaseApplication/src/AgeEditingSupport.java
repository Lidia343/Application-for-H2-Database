import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

public class AgeEditingSupport extends EditingSupport {

	private final TableViewer viewer;
	private final CellEditor editor;
	private CommandsExecuter commandsExecuter;
	private Storage storage;

	public AgeEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
		this.editor = new TextCellEditor(viewer.getTable());
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return editor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		return Integer.toString(((User) element).getAge());
	}

	@Override
	protected void setValue(Object element, Object userInputValue) {
		try {
			if (storage != null && commandsExecuter != null)
			commandsExecuter.execute(new CommandUpdate (storage, (User) element));
			System.out.println("" + commandsExecuter.getCommandsListSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
		((User) element).setAge(Integer.parseInt(String.valueOf(userInputValue)));
		viewer.update(element, null);
	}
	
	public void setCommandsExecuter(CommandsExecuter commandsExecuter) {
		this.commandsExecuter = commandsExecuter;
	}
	
	public void setStorage (Storage storage) {
		this.storage = storage;
	}
	
	public CommandsExecuter getCommandsExecuter() {
		return commandsExecuter;
	}
	
	public Storage getStorage() {
		return storage;
	}
}
