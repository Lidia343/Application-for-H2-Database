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
import org.eclipse.swt.widgets.Combo;
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
	private ShellProperties shellProperties;
	private Display display; 
	private Shell shell; 
	private Color paleForeColor; 
	private Color lightForeColor;
	private Color whiteForeColor;
	private Color darkForeColor;
	private Color backColor; 
	private Font font; 
	
	private Combo combo;
	private Button openingStorageButton;
	private Label titleLabel;
	private Text nameText;
	private Text surnameText;
	private Text ageText;
	private Button addingUserButton;
	private Button isActiveButton;
	
	private StorageFactory storageFactory;
	private Storage storage;
	private Table table;
	private Button updatingUserButton;
	private Button deletingUserButton;
	
	private int selectedRowIndex;
	private int selectedId;
	private boolean rowIsNotSelected;
	
	private boolean isConnection;
	
	
	private CommandsExecuter commandsExecuter;
	
	/**
	 * Конструктор класса Graphics.
	 */
	public Graphics() {
		display = new Display();
		shell = new Shell (display);
		shellPropertiesFactory = new ShellPropertiesFactory(display);
		storageFactory = new StorageFactory();
		isConnection = false;
		commandsExecuter = new CommandsExecuter();
	}
	
	/**
	 * Метод для создания и инициализации графических компонентов.
	 */
	public void createWindow() {
		
		shell.addKeyListener(cancelPressingListener);
		shell.addKeyListener(doubleCancelPressingListener);
		
		rowIsNotSelected = true;
		
		paleForeColor = new Color (display, 170, 250, 170); //foreColor = new Color (display, 45, 5, 5); - прошлый вариант
		lightForeColor = new Color (display, 100, 250, 100);
		whiteForeColor = new Color (display, 200, 200, 200);
		darkForeColor = new Color (display, 30, 50, 30);
		backColor = new Color (display, 83, 82, 82);   //backColor = new Color (display, 255, 252, 245); - прошлый вариант
		font = new Font(display, "Courier New", 13, SWT.NORMAL);
		
		shellProperties = shellPropertiesFactory.getShellProperties(storage, new Point (620, 422), backColor);
		setShell(shell);
		
		createGridLayout(shell); 
		
		//Композит для размещения choosingLabel с надписью "Имя хранилища", списка combo для временного хранения имён хранилищ и кнопки "Открыть" для открытия указанного хранилища:
		Composite heightComposite = new Composite (shell, SWT.BORDER);
		GridData gridData = createGridData(SWT.FILL, true, 0, 0, 0, 2);
		heightComposite.setBackground(backColor);
		heightComposite.setLayoutData(gridData);
		
		createGridLayout (heightComposite);
		
		Label choosingLabel = new Label (heightComposite, SWT.NONE);
		gridData = createGridData(SWT.LEFT, false, 0, 0, 0, 0);
		choosingLabel.setLayoutData(gridData);
		setLabel(choosingLabel, "Имя хранилища:");
		
		combo = new Combo (heightComposite, SWT.DROP_DOWN);
		gridData = createGridData(SWT.FILL, true, 0, 0, 0, 0);
		combo.setLayoutData(gridData);
		combo.setBackground(backColor);
		combo.setForeground(lightForeColor);
		combo.setFont(font);
		
		openingStorageButton = new Button (heightComposite, SWT.PUSH);
		gridData = createGridData(SWT.RIGHT, false, 0, 0, 0, 0);
		int choosingButtonWidthHint = gridData.widthHint;
		openingStorageButton.setLayoutData(gridData);
		setButton(openingStorageButton, "Открыть"); 
		
		titleLabel = new Label(shell, SWT.NONE);
		gridData = createGridData(SWT.CENTER, false, 0, 0, 0, 2);
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
		
		addingUserButton = new Button (shell, SWT.PUSH);
		gridData = createGridData (SWT.RIGHT, false, 120, 30, 50, 2);
		gridData.widthHint = choosingButtonWidthHint;
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
		
		openingStorageButton.addSelectionListener(isOpeningSelection);
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
		lightForeColor.dispose();
		paleForeColor.dispose();
		whiteForeColor.dispose();
		darkForeColor.dispose();
		font.dispose();
		display.dispose(); 	
	}
	
	/**
	 * Метод для установки свойств компонента shell.
	 * @param shell - объект класса Shell
	 */
	private void setShell (Shell shell) {
		shell.setText (shellProperties.getTitle());
		shell.setImage(shellProperties.getImage());
		shell.setMinimumSize(shellProperties.getSize());
		shell.setBackground(shellProperties.getBackColor());
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
	 * Создание таблицы из ячеек на composite для расположения в них графических компонентов.
	 * @param composite - объект класса Composite
	 */
	private void createGridLayout(Composite composite) { 
		
		GridLayout g = new GridLayout();
		g.numColumns = 3; //Количество столбцов
		g.marginWidth = 5; //Расстояние от левого и правого краев окна
		g.marginHeight = 5; //Расстояние от верхнего и нижненго краев окна
		g.horizontalSpacing = 5; //Расстояние между соседними ячейками по горизонтали
		g.verticalSpacing = 10; //Расстояние между соседними ячейками по вертикали
		composite.setLayout(g); //Установка слоя на окно shell
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
		label.setForeground(paleForeColor);
		label.setFont(font); 
	}
	
	/**
	 * Метод установки свойств для компонента Text.
	 * @param text - компонент Text
	 * @param editable - редактируемость
	 */
	private void setText (Text text, boolean editable) { 
		
		text.setEditable(editable); 
		text.setBackground(backColor);
		text.setForeground(lightForeColor);
		text.setFont(font);
		text.addKeyListener(cancelPressingListener);
		text.addKeyListener(doubleCancelPressingListener);
	}
		
	/**
	 * Метод установки свойств для компонента Button.
	 * @param button - компонент Button
	 * @param text - название кнопки
	 */
	private void setButton(Button button, String text) { 
		
		button.setText(text);
		button.setBackground(backColor);
		button.setForeground(paleForeColor);
		button.setFont(font);
		button.addKeyListener(cancelPressingListener);
		button.addKeyListener(doubleCancelPressingListener);
		button.addSelectionListener(isPressingSelection);
	}	
	
	/**
	 * Метод для устновки свойств компонента Table.
	 * @param table - объект класса Table
	 */
	private void setTable(Table table) {
		
		GridData gridData = createGridData(SWT.FILL, true, 0, 0, 0, 2);
		table.setLayoutData(gridData);
		table.setBackground(backColor);
		table.setForeground(lightForeColor);
		table.setFont(font);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addKeyListener(cancelPressingListener);
		table.addKeyListener(doubleCancelPressingListener);
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
	 * Метод создаёт объект класса ErrorChecker, вызывает его методы и возвращает изменённый объект.
	 */
	private ErrorChecker createErrorChecker() {
		ErrorChecker errorChecker = new ErrorChecker();
		errorChecker.setConfig(nameText.getText(), surnameText.getText(), ageText.getText());
		errorChecker.checkUserInput();
		return errorChecker;
	}
	
	
	/**
	 * Метод для установки цветов на кнопках.
	 */
	private void setButtonsDefaultColor(Button b1, Button b2, Button b3) {
		b1.setBackground(backColor);
		b2.setBackground(backColor);
		b3.setBackground(backColor);
		b1.setForeground(paleForeColor);
		b2.setForeground(paleForeColor);
		b3.setForeground(paleForeColor);
	}
	
	/**
	 * Слушатель нажатия на любую кнопку (изменяет её цвета).
	 */
	SelectionAdapter isPressingSelection = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent event) {
			String buffer = "";
			for (int i = 0; i < event.toString().length(); i++) {
				
				if (event.toString().charAt(i) == ' ') buffer = ""; else
					buffer += Character.toString (event.toString().charAt(i));
					if (buffer.equals("{Открыть}")) {
						setButtonsDefaultColor(updatingUserButton, deletingUserButton, addingUserButton);
						openingStorageButton.setBackground(whiteForeColor);
						openingStorageButton.setForeground(darkForeColor);
						break;
					}
					if (buffer.equals("{Добавить}")) {
						setButtonsDefaultColor(updatingUserButton, deletingUserButton, openingStorageButton);
						addingUserButton.setBackground(whiteForeColor);
						addingUserButton.setForeground(darkForeColor);
						break;
					}
					if (buffer.equals("{Изменить}")) {
						setButtonsDefaultColor(addingUserButton, deletingUserButton, openingStorageButton);
						updatingUserButton.setBackground(whiteForeColor);
						updatingUserButton.setForeground(darkForeColor);
						break;
					}
					if (buffer.equals("{Удалить}")) {
						setButtonsDefaultColor(updatingUserButton, addingUserButton, openingStorageButton);
						deletingUserButton.setBackground(whiteForeColor);
						deletingUserButton.setForeground(darkForeColor);
						break;
					}
			}
		}
	};
	
	/**
	 * Слушатель нажатия кнопки "Открыть".
	 */
	SelectionAdapter isOpeningSelection = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent event) {
			
			if (!combo.getText().equals("")) {
				
				boolean isContain = false;
				for (int i = 0; i < combo.getItemCount(); i++) 
					if (combo.getItem(i).equals(combo.getText())) isContain = true;
				
				if (!isContain) combo.add(combo.getText());
				
				if (isConnection) {
					commandsExecuter.clearCommandsStack();
					commandsExecuter.updateIndex();
					try {
						storage.closeStorage();
					} catch (Exception e) {
						//createMessageBox (SWT.ERROR, e.getMessage());
						//return;
					}
				} else isConnection = true;
				
				setStorage();	
				clearTextFields();
				if (table.getItemCount() != 0) shell.pack();
			} else createMessageBox (SWT.ICON_WARNING, "Введите/выберите имя хранилища.");
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
				
				try {
					commandsExecuter.executeCommand(new CommandAdd (storage, user));
				
					int id = 0, age = 0;
					String name = "", surname = "";
					boolean isActive = false;
				
					ArrayList <User> users = storage.getUsersDataSet(true);
					
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
				
				try {
					commandsExecuter.executeCommand(new CommandUpdate(storage, user));
				} catch (Exception e) {
					createMessageBox(SWT.ERROR, e.getMessage());
					return;
				}

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
	
	
	/**
	 * Слушатель нажатия "cntrl+z".
	 */
	KeyAdapter cancelPressingListener  = new KeyAdapter() {
		@Override
		public void keyPressed (KeyEvent key) {
			
			if ((int)key.character == 0x1a) { //Код комбинации Control + Z
				if (commandsExecuter.getCommandsListSize() != 0) {
					try {
						commandsExecuter.undoLastCommand();
					} catch (Exception e) {
						createMessageBox(SWT.ERROR, e.getMessage());
						return;
					}
					clearTextFields();
					showTable(true);
					shell.pack();
				} else return;
			}
		}
	};
	
	/**
	 * Слушатель нажатия "cntrl+y".
	 */
	KeyAdapter doubleCancelPressingListener  = new KeyAdapter() {
		@Override
		public void keyPressed (KeyEvent key) {
			
			if ((int)key.character == 0x19) { //Код комбинации Control + Y
				if (commandsExecuter.getCommandsListSize() != 0) {
					try {
						commandsExecuter.redoLastUndoing();
					} catch (Exception e) {
						createMessageBox(SWT.ERROR, e.getMessage());
						return;
					}
					clearTextFields();
					showTable(true);
					shell.pack();
				} else return;
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
			
			try {
				ArrayList<User> users = new ArrayList<User>();
				users = storage.getUsersDataSet(false);
				User user = null;
				for (User temp : users) {
					if (temp.getId() == selectedId) user = temp;
				}
				commandsExecuter.executeCommand(new CommandDelete (storage, user));
			
				titleLabel.setText("Добавление пользователя:");
				rowIsNotSelected = true;
			
				showTable(false);
				if (table.getItemCount() == 0) storage.updateStorageObject();
			} catch (Exception e) {
				createMessageBox(SWT.ERROR, e.getMessage());
			}
		}
	};
	
	/**
	 * Метод для установки соединения с базой данных.
	 */
	private void setStorage() {
		storage = storageFactory.getStorage(combo.getText());
		try {
			if (storage != null) {
				storage.setStorage(); 
				shellProperties = shellPropertiesFactory.getShellProperties(storage);
				setShell (shell);
			} else {
				createMessageBox (SWT.ERROR, "Некорректное имя хранилища.");
				isConnection = false;
				combo.remove(combo.getItemCount() - 1);
				combo.setText("");
				return;
			}
			storage.createStorageObject();
			showTable(true);
		} catch (Exception e) {
			createMessageBox(SWT.ERROR, e.getMessage());
			return;
		}
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
