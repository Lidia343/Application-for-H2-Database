import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import java.util.ArrayList;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;

/**
 * Класс предназначен для создания интерфейса программы и реализации взаимодействия с пользователем через данный интерфейс.
 */
public class Graphics {
	
	private ShellPropertiesFactory shellPropertiesFactory;
	private Display display; 
	private Shell shell; 
	private Color foreColor; //Цвет шрифта
	private Color backColor; //Цвет фона
	private Font font; 
	
	private Label titleLabel;
	private Text nameText;
	private Text surnameText;
	private Text ageText;
	private Button isActiveButton;
	
	private StorageFactory storageFactory;
	private Storage storage;
	private Table table;
	private Button updatingUserButton;
	private Button deletingUserButton;
	private Button isFileStorageButton;
	private Button isDataBaseButton;
	
	private int selectedRowIndex;
	private int selectedId;
	private boolean rowIsNotSelected;
	
	private boolean isConnectionToDataBase;
	private boolean isConnectionToFileStorage;
	private boolean isConnection;
	
	private ArrayList <Command> command;
	
	/**
	 * Конструктор класса Graphics.
	 */
	public Graphics() {
		display = new Display();
		shell = new Shell (display);
		shellPropertiesFactory = new ShellPropertiesFactory(display);
		storageFactory = new StorageFactory();
		isConnectionToDataBase = false;
		isConnectionToFileStorage = false;
		isConnection = false;
		command = new ArrayList <Command>();
	}
	
	/**
	 * Метод для создания и инициализации графических компонентов.
	 */
	public void createWindow() {
		
		shell.addKeyListener(cancelPressingListener);
		
		rowIsNotSelected = true;
		
		foreColor = new Color (display, 45, 5, 5);
		backColor = new Color (display, 255, 252, 245);
		font = new Font(display, "Courier New", 13, SWT.NORMAL);
		
		shellPropertiesFactory.setShell(shell, new Point (620, 422), "Работа с данными пользователей", "question.png", backColor);
		
		createGridLayout(shell); 
		
		titleLabel = new Label(shell, SWT.NONE);
		GridData gridData = createGridData(SWT.CENTER, false, 0, 0, 0, 2);
		titleLabel.setLayoutData(gridData);
		setLabel(titleLabel, "Добавление пользователя:");
		
		Label nameLabel = new Label(shell, SWT.NONE);
		gridData = createGridData(SWT.RIGHT, false, 0, 0, 0, 0);
		nameLabel.setLayoutData(gridData);
		setLabel(nameLabel, "Имя:");
		
		nameText = new Text (shell, SWT.BORDER);
		gridData = createGridData(SWT.FILL, true, 0, 0, 0, 0);
		nameText.setLayoutData(gridData);
		setText(nameText, true);
		
		Label surnameLabel = new Label (shell, SWT.NONE);
		gridData = createGridData(SWT.RIGHT, false, 0, 0, 0, 0);
		surnameLabel.setLayoutData(gridData);
		setLabel (surnameLabel, "Фамилия:");
		
		surnameText = new Text (shell, SWT.BORDER);
		gridData = createGridData(SWT.FILL, true, 0, 0, 0, 0);
		surnameText.setLayoutData(gridData);
		setText(surnameText, true);
		
		Label ageLabel = new Label (shell, SWT.NONE);
		gridData = createGridData(SWT.RIGHT, false, 0, 0, 0, 0);
		ageLabel.setLayoutData(gridData);
		setLabel (ageLabel, "Возраст:");
		
		ageText = new Text (shell, SWT.BORDER);
		gridData = createGridData(SWT.FILL, true, 0, 0, 0, 0);
		ageText.setLayoutData(gridData);
		setText(ageText, true);
		
		Label isActiveLabel = new Label (shell, SWT.NONE);
		gridData = createGridData(SWT.RIGHT, false, 0, 0, 0, 0);
		isActiveLabel.setLayoutData(gridData);
		setLabel (isActiveLabel, "Активен:");
		
		isActiveButton = new Button (shell, SWT.CHECK);
		gridData = createGridData(SWT.LEFT, false, 0, 0, 0, 0);
		isActiveButton.setLayoutData(gridData);
		setButton(isActiveButton, ""); 
		
		Button addingUserButton = new Button (shell, SWT.PUSH);
		gridData = createGridData (SWT.RIGHT, false, 120, 30, 50, 2);
		addingUserButton.setLayoutData(gridData);
		setButton(addingUserButton, "Добавить"); 
		
		shell.setDefaultButton(addingUserButton);
		addingUserButton.addSelectionListener(addingUserSelection);
		
		//Композит для размещения Layout для таблицы:
		Composite composite = new Composite (shell, SWT.BORDER);
		gridData = createGridData(SWT.FILL, true, 0, 0, 0, 2);
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		composite.setLayoutData(gridData);
		
		table = new Table (composite, SWT.BORDER | SWT.FULL_SELECTION);
		
	    setTable(table);
	    for (int i = 0; i < 5; i++) 
	        new TableColumn(table, SWT.NONE);
	    
	    TableColumnLayout tableColumnLayout = new TableColumnLayout();
	    composite.setLayout(tableColumnLayout);
	    
	    table.getColumn(0).setText("Код");
	    table.getColumn(1).setText("Имя");
	    table.getColumn(2).setText("Фамилия");
	    table.getColumn(3).setText("Возраст");
	    table.getColumn(4).setText("Активен");
	    	
	    ColumnWeightData columnWeightData = new ColumnWeightData(100);
	    columnWeightData.resizable = true;
	    columnWeightData.minimumWidth = 100;
	    tableColumnLayout.setColumnData(table.getColumn(0), columnWeightData);
	    tableColumnLayout.setColumnData(table.getColumn(1), columnWeightData);
	    tableColumnLayout.setColumnData(table.getColumn(2), columnWeightData);
	    tableColumnLayout.setColumnData(table.getColumn(3), columnWeightData);
	    tableColumnLayout.setColumnData(table.getColumn(4), columnWeightData);
	   	
		updatingUserButton = new Button (shell, SWT.PUSH);
		gridData = createGridData (SWT.LEFT, false, 0, 0, 0, 0);
		updatingUserButton.setLayoutData(gridData);
		setButton(updatingUserButton, "Изменить"); 
		
		updatingUserButton.addSelectionListener(updatingUserSelection); 
		
		deletingUserButton = new Button (shell, SWT.PUSH);
		gridData = createGridData (SWT.RIGHT, false, 0, 0, 0, 0);
		deletingUserButton.setLayoutData(gridData);
		setButton(deletingUserButton, "Удалить"); 
		
		isFileStorageButton = new Button (shell, SWT.PUSH);
		gridData = createGridData (SWT.RIGHT, false, 0, 0, 0, 2);
		isFileStorageButton.setLayoutData(gridData);
		setButton(isFileStorageButton, "Работать с файлом"); 
		
		isDataBaseButton = new Button (shell, SWT.PUSH);
		gridData = createGridData (SWT.RIGHT, false, 0, 0, 0, 2);
		isDataBaseButton.setLayoutData(gridData);
		setButton(isDataBaseButton, "Работать с базой данных"); 
		
		isFileStorageButton.addSelectionListener(isFileStorageSelection);
		isDataBaseButton.addSelectionListener(isDataBaseSelection);
		
		deletingUserButton.addSelectionListener(deletingUserSelection);
		
		table.addSelectionListener(tableRowSelection);
		
		shell.pack(); //Установка оптимального размера окна под имеющиеся компоненты
		shell.open(); //Открытие окна
			
		/* Пока ресурсы, ассоциированные с shell, не освобождены,
		 * если display не используется, выполнение текущего потока приостанавливается:*/
		while (!shell.isDisposed()) { 
			if (!display.readAndDispatch()) display.sleep(); 
		}
		
		//Освобождение ресурсов:
		backColor.dispose();
		foreColor.dispose();
		font.dispose();
		display.dispose(); 	
	}
	
	/**
	 * Создание таблицы из ячеек на shell для расположения в них графических компонентов.
	 * @param s - объект класса Shell
	 */
	private void createGridLayout(Shell s) { 
		
		GridLayout g = new GridLayout();
		g.numColumns = 2; //Количество столбцов
		g.marginWidth = 5; //Расстояние от левого и правого краев окна
		g.marginHeight = 5; //Расстояние от верхнего и нижненго краев окна
		g.horizontalSpacing = 5; //Расстояние между соседними ячейками по горизонтали
		g.verticalSpacing = 10; //Расстояние между соседними ячейками по вертикали
		s.setLayout(g); //Установка слоя на окно shell
	}
	
	/**
	 * Метод для создания объекта класса GridData (для управления данными, связанными с различными компонентами).
	 * Обязательным является указание значений horAl и grab, остальные параметры можно передать как 0 (будут установлены значения по умолчанию).
	 * @param horAl - выравнивание по горизонтали
	 * @param grab - разрешение/запрет захвата свободного пространства по горизонтали при изменении размеров окна
	 * @param width - ширина компонента
	 * @param height - высота компонента
	 * @param horInd - отступ по горизонтали от начального положения (что задаётся способом выравнивания) компонента в ячейке
	 * @param horSpan - количество ячеек, занимаемое компонентом по горизонтали
	 * @return gridData - объект класса GridData
	 */
	private GridData createGridData(int horAl, boolean grab, int width, int height, int horInd, int horSpan) {
		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = horAl; 
		gridData.grabExcessHorizontalSpace = grab; 
		if (width != 0) gridData.widthHint = width; 
		if (height != 0) gridData.heightHint = height; 
		if (horInd != 0) gridData.horizontalIndent = horInd; 
		if (horSpan != 0) gridData.horizontalSpan = horSpan; 
		return gridData;
	}
	
	/**
	 * Метод установки свойств для компонента Label
	 * @param label - компонент Label
	 * @param text - текст
	 */
	private void setLabel(Label label, String text) {
		
		label.setText(text); 
		label.setBackground(backColor);
		label.setForeground(foreColor);
		label.setFont(font); 
	}
	
	/**
	 * Метод установки свойств для компонента Text.
	 * @param text - компонент Text
	 * @param editable - редактируемость
	 */
	private void setText (Text text, boolean editable) { 
		
		text.setEditable(editable); 
		text.setForeground(foreColor);
		text.setFont(font);
		text.addKeyListener(cancelPressingListener);
	}
		
	/**
	 * Метод установки свойств для компонента Button.
	 * @param button - компонент Button
	 * @param text - название кнопки
	 */
	private void setButton(Button button, String text) { 
		
		button.setText(text);
		button.setBackground(backColor);
		button.setForeground(foreColor);
		button.setFont(font);
		button.addKeyListener(cancelPressingListener);
	}	
	
	/**
	 * Метод для устновки свойств компонента Table.
	 * @param table - объект класса Table
	 */
	private void setTable(Table table) {
		
		GridData gridData = createGridData(SWT.FILL, true, 0, 0, 0, 2);
		table.setLayoutData(gridData);
		table.setBackground(backColor);
		table.setForeground(foreColor);
		table.setFont(font);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addKeyListener(cancelPressingListener);
	}
	
	/**
	 * Метод для создания дочернего окна с сообщением пользователю (вид сообщения зависит от переданных параметров).
	 * @param messageCode - код сообщения (SWT.ERROR или SWT.ICON_INFORMATION)
	 * @param message - сообщение для пользователя
	 */
	private void createMessageBox (int messageCode, String message) {
		
		MessageBox messageBox = new MessageBox (shell, messageCode);
		messageBox.setText("Сообщение");
		messageBox.setMessage(message);
		messageBox.open();
	}
	
	/**
	 * Метод, возвращающий true, если при выполнении одного из методов
	 * класса DataBase не произошло ошибок, иначе - false.
	 * Выводит на экран сообщение пользователю в случае возникновения ошибки.
	 */
	private boolean isRightStorage() {
		if (!storage.getErrorMessage().equals("")) {
			createMessageBox (SWT.ERROR, storage.getErrorMessage());
			return false;
		} 
		return true;
	}
	
	/**
	 * Метод создаёт объект класса ErrorChecker, вызывает его методы и возвращает изменённый объект.
	 */
	private ErrorChecker createErrorChecker() {
		ErrorChecker errorChecker = new ErrorChecker();
		errorChecker.setConfig(nameText.getText(), surnameText.getText(), ageText.getText());
		errorChecker.checkUserInput();
		return errorChecker;
	}
	
	
	/**
	 * Слушатель нажатия кнопки "Работать с базой данных".
	 */
	SelectionAdapter isDataBaseSelection = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent event) {
			
			if (!isConnectionToDataBase) command.clear();
			if (isConnection) {
				storage.closeStorage();
				/*if (!storage.getErrorMessage().equals("")) {
					createMessageBox (SWT.ERROR, storage.getErrorMessage());
					return;
				} */
			} else isConnection = true;
			isConnectionToDataBase = true;
			isConnectionToFileStorage = false;
			setStorage();	
			clearTextFields();
			//shell.setText("Работа с базой данных");
			shellPropertiesFactory.setShell(shell, null, "Работа с базой данных", "database.png", backColor);
			if (table.getItemCount() != 0) shell.pack();
		}
	};
	
	/**
	 * Слушатель нажатия кнопки "Работать с файлом".
	 */
	SelectionAdapter isFileStorageSelection = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent event) {
			
			if (!isConnectionToFileStorage) command.clear();
			if (isConnection) {
				storage.closeStorage();
				/*if (!storage.getErrorMessage().equals("")) {
					createMessageBox (SWT.ERROR, storage.getErrorMessage());
					return;
				} */
			} else isConnection = true;
			isConnectionToFileStorage = true;
			isConnectionToDataBase = false;
			setStorage();
			clearTextFields();
			///shell.setText("Работа с текстовым файлом");
			shellPropertiesFactory.setShell(shell, null, "Работа с текстовым файлом", "file.png", backColor);
			if (table.getItemCount() != 0) shell.pack();
		}
	};
	
	/**
	 * Слушатель нажатия кнопки "Добавить".
	 */
	SelectionAdapter addingUserSelection = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent event) {
			
			if (!isConnection) {
				createMessageBox(SWT.ERROR, "Выберите хранилище данных.");
				return;
			}
			titleLabel.setText("Добавление пользователя:");
			ErrorChecker errorChecker = createErrorChecker();
			
			if (errorChecker.getMessageCode() == SWT.OK) {
				
				User user = new User();
				user.setName(nameText.getText());
				user.setSurname(surnameText.getText()); 
				user.setAge(Integer.parseInt(ageText.getText()));
				user.setIsActive(isActiveButton.getSelection());
				//storage.addUser(user);
				command.add(new CommandAdd (storage));
				
				command.get(command.size() - 1).execute(user);
				if (!isRightStorage()) return;
				
				int id = 0, age = 0;
				String name = "", surname = "";
				boolean isActive = false;
				
				try {
					
					ArrayList <User> users = storage.getUsersDataSet(true);///////////////////////////////////////////////////////////////////////////////////////////////////////
					
					if (!isRightStorage()) return;
					
					for (int i = 0; i < users.size(); i++) {
						
						id = users.get(i).getId();
						name = users.get(i).getName();
						surname = users.get(i).getSurname();
						age = users.get(i).getAge();
						isActive = users.get(i).getIsActive();
					}

					TableItem item = new TableItem(table, SWT.NONE);
					item.setText(new String[] {Integer.toString(id), name, surname, Integer.toString(age), Boolean.toString(isActive)});
					clearTextFields();
					shell.pack();
					
					rowIsNotSelected = true;
				} catch(Exception e) { 		
					createMessageBox (SWT.ERROR, e.getMessage());
				  } 	
			} else createMessageBox (errorChecker.getMessageCode(), errorChecker.getErrorMesssage());
		}
	};
	
	/**
	 * Слушатель нажатия кнопки "Изменить".
	 */
	SelectionAdapter updatingUserSelection = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent event) {
			
			if (!isConnection) {
				createMessageBox(SWT.ERROR, "Выберите хранилище данных.");
				return;
			}
			if (rowIsNotSelected) {
				createMessageBox(SWT.ICON_WARNING, "Пользователь не выбран.");
				return;
			}
			ErrorChecker errorChecker = createErrorChecker();
			if (errorChecker.getMessageCode() == SWT.OK) {
				User user = new User();
				user.setId(selectedId);
				user.setName(nameText.getText());
				user.setSurname(surnameText.getText());
				user.setAge(Integer.parseInt(ageText.getText()));
				user.setIsActive(isActiveButton.getSelection());
				//storage.updateUser(user);
				command.add(new CommandUpdate(storage));
				command.get(command.size() - 1).execute(user);
				if (!isRightStorage()) return;

				table.getItem(selectedRowIndex).setText(new String[] {Integer.toString(user.getId()), user.getName(), user.getSurname(), Integer.toString(user.getAge()), Boolean.toString(user.getIsActive())});
			} else createMessageBox (errorChecker.getMessageCode(), errorChecker.getErrorMesssage());
		}
	};
	
	/**
	 * Слушатель нажатия на строки таблицы.
	 */
	SelectionAdapter tableRowSelection = new SelectionAdapter () {
		@Override
		public void widgetSelected(SelectionEvent event ) {
			
			titleLabel.setText("Изменение данных:");
			rowIsNotSelected = false;
			selectedRowIndex = table.indexOf((TableItem)event.item);
			selectedId = Integer.parseInt(table.getItem(selectedRowIndex).getText(0));
			String name = table.getItem(selectedRowIndex).getText(1), 
			surname = table.getItem(selectedRowIndex).getText(2);
			int age = Integer.parseInt(table.getItem(selectedRowIndex).getText(3)); 
			boolean isActive = Boolean.parseBoolean(table.getItem(selectedRowIndex).getText(4));
					
			nameText.setText(name);
			surnameText.setText(surname);
			ageText.setText("" + age);
			isActiveButton.setSelection(isActive);
		}
	};
	
	KeyAdapter cancelPressingListener  = new KeyAdapter() {
		@Override
		public void keyPressed (KeyEvent key) {
			
			if ((int)key.character == 0x1a) {
				if (command.size() != 0) {
					System.out.println(Integer.toString(command.size()));
					command.get(command.size() - 1).undo();
					command.remove(command.size() - 1);
					System.out.println(Integer.toString(command.size()));
					clearTextFields();
					showTable(true);
					shell.pack();
				}
			}
		}
	};
	
	/**
	 * Метод для удаления текста из полей.
	 */
	private void clearTextFields() {
		nameText.setText("");
		surnameText.setText("");
		ageText.setText("");
		isActiveButton.setSelection(false);
	}
	
	/**
	 * Слушатель нажатия кнопки "Удалить".
	 */
	SelectionAdapter deletingUserSelection = new SelectionAdapter() {
		@Override
		public void widgetSelected (SelectionEvent event) {
			
			if (!isConnection) {
				createMessageBox(SWT.ERROR, "Выберите хранилище данных.");
				return;
			}
			ErrorChecker errorChecker = createErrorChecker();
			if (errorChecker.getMessageCode() != SWT.OK) {
				createMessageBox(SWT.ICON_WARNING, "Пользователь не выбран.");
				return;
			}
			clearTextFields();
			shell.pack();
				
			command.add(new CommandDelete (storage));//////////////////////////////////////////////////////////////
			ArrayList<User> users = new ArrayList<User>();
			users = storage.getUsersDataSet(false);
			User user = null;
			for (User temp : users) {
				if (temp.getId() == selectedId) user = temp;
			}
			command.get(command.size() - 1).execute(user);/////////////////////////////////////////////////////////////////////////
			if (!isRightStorage()) return;
				
			titleLabel.setText("Добавление пользователя:");
			rowIsNotSelected = true;
			
			showTable(false);
			if (table.getItemCount() == 0) storage.updateStorageObject();
		}
	};
	
	/**
	 * Метод для установки соединения с базой данных.
	 */
	private void setStorage() {
		if (isConnectionToDataBase) storage = storageFactory.getStorage("jdbc:h2:~/test"); 
		if (isConnectionToFileStorage) storage = storageFactory.getStorage("file.txt"); 
		storage.setStorage(); 
		if (!isRightStorage()) return;
		storage.createStorageObject();
		if (!isRightStorage()) return;
		showTable(true);
	}
	
	/**
	 * Метод для вывода таблицы с текущими значениями строк в БД.
	 */
	private void showTable(boolean isAfterDeleteCanceling) {
		
		table.removeAll();
		
		int id, age;
		String name, surname;
		boolean isActive;
		try {
			ArrayList <User> users = storage.getUsersDataSet(isAfterDeleteCanceling);
			if (!isRightStorage()) return;
			
			for (int i = 0; i < users.size(); i++) {
				id = users.get(i).getId();
				name = users.get(i).getName();
				surname = users.get(i).getSurname();
				age = users.get(i).getAge();
				isActive = users.get(i).getIsActive();
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] {Integer.toString(id), name, surname, Integer.toString(age), Boolean.toString(isActive)});
			}
		} catch(Exception e) { 
			 createMessageBox (SWT.ERROR, e.getMessage());
		  } 	
	}
}
