package mainPackage;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
	private Color buttonBackColor;
	private Color buttonForeColor;
	private Color backColor; 
	private Font font; 
	
	private TableViewer tableViewer;
	private TableViewerComparator comparator;
	
	private Button darkThemeButton;
	private Button lightThemeButton;
	private Composite heightComposite;
	private Composite genAndAddComposite;
	private Composite themeComposite;
	private Composite tableComposite;
	private Composite downComposite;
	private Combo combo;
	private Button openingStorageButton;
	private Label titleLabel;
	private Label lightThemeLabel;
	private Label darkThemeLabel;
	private Label choosingLabel;
	private Label nameLabel;
	private Label surnameLabel;
	private Label ageLabel;
	private Label isActiveLabel;
	private Label userChoosingLabel;
	private Text nameText;
	private Text surnameText;
	private Text ageText;
	private Text userNumbersText;
	private Button generatingButton;
	private Button addingUserButton;
	private Button isActiveButton;
	private Table table;
	private Button deletingUserButton;
	private Button deletingAllUsersButton;
	
	private final String defaultDatabaseName = "jdbc:h2:~/test";
	private final String defaultFileName = "file.txt";
	
	private AgeEditingSupport ageEditingSupport;
	private NameEditingSupport nameEditingSupport;
	private SurnameEditingSupport surnameEditingSupport;
	private IsActiveEditingSupport isActiveEditingSupport;
	
	private TableViewerColumn nameColumn;
	private TableViewerColumn surnameColumn;
	private TableViewerColumn ageColumn;
	private TableViewerColumn isActiveColumn;
	
	private StorageFactory storageFactory;
	private Storage storage;
	
	private boolean isDarkColor;
	private int selectedId;
	private boolean isConnection;
	
	private CommandsExecuter commandsExecuter;
	
	/**
	 * Конструктор класса Graphics.
	 */
	public Graphics() {
		display = new Display();
		shell = new Shell (display);
		shellPropertiesFactory = new ShellPropertiesFactory();
		storageFactory = new StorageFactory();
		isConnection = false;
		commandsExecuter = new CommandsExecuter();
		isDarkColor = true;
		comparator = new TableViewerComparator();
	}
	
	/**
	 * Метод для создания и инициализации графических компонентов.
	 */
	public void createWindow() {
		
		shell.addKeyListener(cancelPressingListener);
		shell.addKeyListener(doubleCancelPressingListener);
		
		paleForeColor = new Color (display, 170, 250, 170); 
		lightForeColor = new Color (display, 100, 250, 100);
		buttonBackColor = new Color (display, 200, 200, 200);
		buttonForeColor = new Color (display, 30, 50, 30);
		backColor = new Color (display, 83, 82, 82);   
		font = new Font(display, "Courier New", 13, SWT.NORMAL);
		
		shellProperties = shellPropertiesFactory.getShellProperties(storage, new Point (900, 550), backColor, isDarkColor);
		setShell(shell);
		createGridLayout(shell); 
		
		themeComposite = new Composite (shell, SWT.BORDER);
		GridData gridData = createGridData(SWT.FILL, true, 0, 0, 0, 2);
		themeComposite.setBackground(backColor);
		themeComposite.setLayoutData(gridData);
		createGridLayout (themeComposite, 4);
		
		darkThemeLabel = new Label (themeComposite, SWT.NONE);
		gridData = createGridData(SWT.RIGHT, false, 0, 0, 0, 0);
		darkThemeLabel.setLayoutData(gridData);
		setLabel (darkThemeLabel, "Тёмная тема:");
		
		darkThemeButton = new Button (themeComposite, SWT.RADIO);
		gridData = createGridData (SWT.LEFT, false, 0, 0, 0, 0);
		darkThemeButton.setLayoutData(gridData);
		setButton(darkThemeButton, ""); 
		darkThemeButton.setSelection(true);
		
		lightThemeLabel = new Label (themeComposite, SWT.NONE);
		gridData = createGridData(SWT.RIGHT, false, 0, 0, 0, 0);
		lightThemeLabel.setLayoutData(gridData);
		setLabel (lightThemeLabel, "Светлая тема:");
		
		lightThemeButton = new Button (themeComposite, SWT.RADIO);
		gridData = createGridData (SWT.LEFT, false, 0, 0, 0, 0);
		lightThemeButton.setLayoutData(gridData);
		setButton(lightThemeButton, ""); 
	
		darkThemeButton.addSelectionListener(darkThemeSelection);
		lightThemeButton.addSelectionListener(lightThemeSelection);
		
		//Композит для размещения choosingLabel с надписью "Имя хранилища", списка combo для временного хранения имён хранилищ и кнопки "Открыть" для открытия указанного хранилища:
		heightComposite = new Composite (shell, SWT.BORDER);
		gridData = createGridData(SWT.FILL, true, 0, 0, 0, 2);
		heightComposite.setBackground(backColor);
		heightComposite.setLayoutData(gridData);
		
		createGridLayout (heightComposite, 2);
		
		choosingLabel = new Label (heightComposite, SWT.NONE);
		gridData = createGridData(SWT.LEFT, false, 0, 0, 0, 0);
		choosingLabel.setLayoutData(gridData);
		setLabel(choosingLabel, "Имя хранилища:");
		
		combo = new Combo (heightComposite, SWT.DROP_DOWN);
		gridData = createGridData(SWT.FILL, true, 0, 0, 0, 0);
		combo.setLayoutData(gridData);
		setCombo (combo);
		combo.add(defaultDatabaseName);
		combo.add(defaultFileName);
		combo.addModifyListener(comboModification);
		
		openingStorageButton = new Button (heightComposite, SWT.PUSH);
		gridData = createGridData(SWT.RIGHT, false, 0, 0, 0, 2);
		int choosingButtonWidthHint = gridData.widthHint;
		openingStorageButton.setLayoutData(gridData);
		setButton(openingStorageButton, "Открыть"); 
		
		titleLabel = new Label(shell, SWT.NONE);
		gridData = createGridData(SWT.CENTER, false, 0, 0, 0, 2);
		titleLabel.setLayoutData(gridData);
		setLabel(titleLabel, "Добавление пользователя:");
		
		nameLabel = new Label(shell, SWT.NONE);
		gridData = createGridData(SWT.RIGHT, false, 0, 0, 0, 0);
		nameLabel.setLayoutData(gridData);
		setLabel(nameLabel, "Имя:");
		
		nameText = new Text (shell, SWT.BORDER);
		gridData = createGridData(SWT.FILL, true, 0, 0, 0, 0);
		nameText.setLayoutData(gridData);
		setText(nameText, true);
		
		surnameLabel = new Label (shell, SWT.NONE);
		gridData = createGridData(SWT.RIGHT, false, 0, 0, 0, 0);
		surnameLabel.setLayoutData(gridData);
		setLabel (surnameLabel, "Фамилия:");
		
		surnameText = new Text (shell, SWT.BORDER);
		gridData = createGridData(SWT.FILL, true, 0, 0, 0, 0);
		surnameText.setLayoutData(gridData);
		setText(surnameText, true);
		
		ageLabel = new Label (shell, SWT.NONE);
		gridData = createGridData(SWT.RIGHT, false, 0, 0, 0, 0);
		ageLabel.setLayoutData(gridData);
		setLabel (ageLabel, "Возраст:");
		
		ageText = new Text (shell, SWT.BORDER);
		gridData = createGridData(SWT.FILL, true, 0, 0, 0, 0);
		ageText.setLayoutData(gridData);
		setText(ageText, true);
		
		isActiveLabel = new Label (shell, SWT.NONE);
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
		
		genAndAddComposite = new Composite (shell, SWT.NONE);
		gridData = createGridData(SWT.FILL, true, 0, 0, 0, 2);
		genAndAddComposite.setBackground(backColor);
		genAndAddComposite.setLayoutData(gridData);
		createGridLayout (genAndAddComposite, 3);
		
		generatingButton = new Button (genAndAddComposite, SWT.PUSH);
		gridData = createGridData (SWT.LEFT, false, 0, 0, 0, 0);
		generatingButton.setLayoutData(gridData);
		setButton (generatingButton, "Сгенерировать случайных пользователей");
		
		generatingButton.addSelectionListener(generatingSelection);
		
		userChoosingLabel = new Label (genAndAddComposite, SWT.NONE);
		gridData = createGridData(SWT.RIGHT, true, 0, 0, 0, 0);
		userChoosingLabel.setLayoutData(gridData);
		setLabel (userChoosingLabel, "Количество пользователей:");
		userChoosingLabel.setVisible(false);
		
		userNumbersText = new Text (genAndAddComposite, SWT.BORDER);
		gridData = createGridData(SWT.FILL, true, 0, 0, 0, 0);
		userNumbersText.setLayoutData(gridData);
		setText(userNumbersText, true);
		userNumbersText.setVisible(false);
		userNumbersText.setText("5");
		userNumbersText.addFocusListener(numberTextFocusing);
		
		shell.setDefaultButton(openingStorageButton);
		addingUserButton.addSelectionListener(addingUserSelection);
		
		tableComposite = new Composite (shell, SWT.BORDER);
		gridData = createGridData(SWT.FILL, true, 0, 0, 0, 2);
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		tableComposite.setLayoutData(gridData);
		
		tableViewer = new TableViewer(tableComposite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns();
	    table = tableViewer.getTable();
	    setTable(table, false);
	    
	    tableViewer.setContentProvider(new UsersContentProvider());
	    tableViewer.addSelectionChangedListener(tableRowSelection);
	    // table.setSortDirection(SWT.UP);
	    
	    TableColumnLayout tableColumnLayout = new TableColumnLayout();
	    tableComposite.setLayout(tableColumnLayout);
	    	
	    ColumnWeightData columnWeightData = new ColumnWeightData(100);
	    columnWeightData.resizable = true;
	    columnWeightData.minimumWidth = 100;
	    tableColumnLayout.setColumnData(table.getColumn(0), columnWeightData);
	    tableColumnLayout.setColumnData(table.getColumn(1), columnWeightData);
	    tableColumnLayout.setColumnData(table.getColumn(2), columnWeightData);
	    tableColumnLayout.setColumnData(table.getColumn(3), columnWeightData);
	    tableColumnLayout.setColumnData(table.getColumn(4), columnWeightData);
	   	
	    downComposite = new Composite (shell, SWT.BORDER);
		gridData = createGridData(SWT.FILL, true, 0, 0, 0, 2);
		downComposite.setBackground(backColor);
		downComposite.setLayoutData(gridData);
		createGridLayout (downComposite, 2);
		
		deletingUserButton = new Button (downComposite, SWT.PUSH);
		gridData = createGridData (SWT.RIGHT, true, 0, 0, 500, 0);
		deletingUserButton.setLayoutData(gridData);
		setButton(deletingUserButton, "Удалить"); 
		
		deletingAllUsersButton = new Button (downComposite, SWT.PUSH);
		gridData = createGridData (SWT.RIGHT, false, 0, 0, 0, 0);
		deletingAllUsersButton.setLayoutData(gridData);
		setButton(deletingAllUsersButton, "Удалить всех"); 
		deletingAllUsersButton.addSelectionListener(deletingAllUsersSelection);
		
		openingStorageButton.addSelectionListener(isOpeningSelection);
		deletingUserButton.addSelectionListener(deletingUserSelection);
		
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
		buttonBackColor.dispose();
		buttonForeColor.dispose();
		font.dispose();
		display.dispose(); 	
	}
	
	/**
	 * Метод для установки поддержки редактирования для всех столбцов таблицы.
	 */
	private void setEditingSupportForColumns() {
		
		//Слушатель неверного ввода данных в таблицу:
		ErrorInputListener errorInputListener = new ErrorInputListener() {
			@Override
			public void createMessage(String message) {
				createMessageBox (SWT.ERROR, message);
			}
		};
		ageEditingSupport = new AgeEditingSupport (tableViewer, userEditingListener, errorInputListener);
		ageColumn.setEditingSupport(ageEditingSupport);
		
		nameEditingSupport = new NameEditingSupport(tableViewer, userEditingListener, errorInputListener);
		nameColumn.setEditingSupport(nameEditingSupport);
		
		surnameEditingSupport = new SurnameEditingSupport(tableViewer, userEditingListener, errorInputListener);
		surnameColumn.setEditingSupport(surnameEditingSupport);
		
		isActiveEditingSupport = new IsActiveEditingSupport(tableViewer, userEditingListener);
		isActiveColumn.setEditingSupport(isActiveEditingSupport);
	}
	
	/**
	 * Слушатель нажатия на столбец таблицы.
	 */
	private UserEditingListener userEditingListener = new UserEditingListener () {
		@Override
		public void changeUserName(User user, String name) {
			executeCommandAndRefreshViewer (new CommandUpdate (storage, user, name, UserData.FIRSTNAME));	
		}
		@Override
		public void changeUserSurname(User user, String surname) {
			executeCommandAndRefreshViewer (new CommandUpdate (storage, user, surname, UserData.LASTNAME));
		}
		@Override
		public void changeUserAge(User user, int age) {
			executeCommandAndRefreshViewer (new CommandUpdate (storage, user, age, UserData.AGE));
		}
		@Override
		public void changeUserIsActive(User user, boolean isActive) {
			executeCommandAndRefreshViewer (new CommandUpdate (storage, user, isActive, UserData.ISACTIVE));
		}
		
	};
	
	/**
	 * Метод для вызова метода execute() переданной команды и обновления объекта tableViewer класса TableViewer.
	 */
	private void executeCommandAndRefreshViewer(Command command) {
		try {
			commandsExecuter.execute(command);
			tableViewer.refresh();
		} catch (Exception e) {
			createMessageBox (SWT.ERROR, e.getMessage());
		}
	}
	
	/**
	 * Метод создаёт столбцы TableViewerColumn и устанавливает на них CellLabelProvider.
	 */
	private void createColumns() {
		TableViewerColumn viewerColumn = createTableViewerColumn ("Код", 0);
		viewerColumn.setLabelProvider(new CellLabelProvider() {
            @Override
            public void update(ViewerCell cell) {
                cell.setText(Integer.toString((((User) cell.getElement()).getId())));
            }
        });
		
		nameColumn = createTableViewerColumn ("Имя", 1);
		nameColumn.setLabelProvider(new CellLabelProvider() {
            @Override
            public void update(ViewerCell cell) {
                cell.setText(((User) cell.getElement()).getName());
            }
        });
		
		surnameColumn = createTableViewerColumn ("Фамилия", 2);
		surnameColumn.setLabelProvider(new CellLabelProvider() {
            @Override
            public void update(ViewerCell cell) {
                cell.setText(((User) cell.getElement()).getSurname());
            }
        });
		
		ageColumn = createTableViewerColumn ("Возраст", 3);
		ageColumn.setLabelProvider(new CellLabelProvider() {
            @Override
            public void update(ViewerCell cell) {
                cell.setText(Integer.toString((((User) cell.getElement()).getAge())));
            }
        });
		
		isActiveColumn = createTableViewerColumn ("Активен", 4);
		isActiveColumn.setLabelProvider(new CellLabelProvider() {
            @Override
            public void update(ViewerCell cell) {
                cell.setText(Boolean.toString((((User) cell.getElement()).isActive())));
            }
        });
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
	 * @param numColumns - количество столбцов
	 */
	private void createGridLayout(Composite composite, int numColumns) { 
		
		GridLayout g = new GridLayout();
		g.numColumns = numColumns; //Количество столбцов
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
	 * Метод для установки свойств компонента Combo.
	 *  * @param combo - объект класса Combo
	 */
	private void setCombo  (Combo combo) {
		combo.setBackground(backColor);
		combo.setForeground(lightForeColor);
		combo.setFont(font);
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
	 * Метод установки свойств для компонента Label
	 * @param label - компонент Label
	 */
	private void setLabel(Label label) {
		label.setBackground(backColor);
		label.setForeground(paleForeColor);
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
	 * Метод установки свойств для компонента Text.
	 * @param text - компонент Text
	 */
	private void setText (Text text) { 
		text.setBackground(backColor);
		text.setForeground(lightForeColor);
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
	 * Метод установки свойств для компонента Button.
	 * @param button - компонент Button
	 * @param isOnlyChoosingButtonColors - установить только цвета, соответствующие цветам последней нажатой кнопке / не устанавливать их
	 */
	private void setButton(Button button, boolean isOnlyChoosingButtonColors) { 
		if (isOnlyChoosingButtonColors) {
			button.setBackground(buttonBackColor);
			button.setForeground(buttonForeColor);
			return;
		} 
		button.setBackground(backColor);
		button.setForeground(paleForeColor);
	}	
	
	/**
	 * Метод создаёт столбец в таблице Table и устанавливает слушатель нажатия на данный столбец.
	 * @param title - заголовок столбца
	 * @param colNumber - номер столбца
	 * @return столбец TableViewerColumn
	 */
	private TableViewerColumn createTableViewerColumn(String title, int colNumber) {
		TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.CENTER);
        TableColumn column = viewerColumn.getColumn();
        column.setText(title);
        column.addSelectionListener(getSelectionAdapter(column, colNumber));
        //createMenuItem(contextMenu, column);
        return viewerColumn;
	}

	 /**
	  * Метод для возврата слушателя нажатия на столбец таблицы.
	 * @param column - столбец таблицы
	 * @param index - номер столбца
	 * @return слушатель SelectionAdapter
	 */
	private SelectionAdapter getSelectionAdapter(final TableColumn column, final int index) {
	        SelectionAdapter selectionAdapter = new SelectionAdapter() {
	            @Override
	            public void widgetSelected(SelectionEvent e) {
	                comparator.setColumn(index);
	                int dir = comparator.getDirection();
	                tableViewer.getTable().setSortDirection(dir);
	                tableViewer.refresh();
	            }
	        };
	        return selectionAdapter;
	    }
	 
	/**
	 * Метод для устновки свойств компонента Table.
	 * @param table - объект класса Table
	 * @param isOnlyColors - установить только цвета таблицы / установить не только цвета таблицы
	 */
	private void setTable(Table table, boolean isOnlyColors) {
		
		table.setBackground(backColor);
		table.setForeground(lightForeColor);
		if (!isOnlyColors) {
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
	private ErrorChecker createErrorChecker(boolean isOnlyUserNumbers) {
		ErrorChecker errorChecker = new ErrorChecker();
		if (isOnlyUserNumbers) {
			errorChecker.checkUserNumbers((userNumbersText.getText()));
			return errorChecker;
		} 
		errorChecker.checkName(nameText.getText());
		errorChecker.checkName(surnameText.getText());
		errorChecker.checkAge(ageText.getText());
		return errorChecker;
	}
	
	/**
	 * Метод устанавливает цвета всех компонентов в зависимости от текущего хранилища.
	 */
	private void setComponentsColors() {
		shellProperties = shellPropertiesFactory.getShellProperties(storage, backColor, isDarkColor);
		setShell(shell);
		setLabel(titleLabel); setLabel(lightThemeLabel); 
		setLabel(darkThemeLabel); setLabel(choosingLabel);
		setLabel(nameLabel); setLabel(surnameLabel);
		setLabel(ageLabel); setLabel(isActiveLabel); setLabel(userChoosingLabel);
		setText (nameText); setText (surnameText); setText (ageText); setText (userNumbersText);
		setCombo (combo); 
		setButton(openingStorageButton, false); setButton(addingUserButton, false);
		setButton(deletingUserButton, false);
		setButton(isActiveButton, false); setButton(darkThemeButton, false);
		setButton(lightThemeButton, false);  setButton (generatingButton, false);
		setButton (deletingAllUsersButton, false); setTable(table, true);		
		heightComposite.setBackground(backColor); themeComposite.setBackground(backColor);
		tableComposite.setBackground(backColor); genAndAddComposite.setBackground(backColor);
		downComposite.setBackground(backColor);
	}
	
	/**
	 * Слушатель нажатия кнопки выбора тёмной темы.
	 */
	SelectionAdapter darkThemeSelection = new SelectionAdapter () {
		@Override
		public void widgetSelected (SelectionEvent event) {
			if (darkThemeButton.getSelection() == true) {
				lightThemeButton.setSelection(false);
				if (isDarkColor == false) {
					isDarkColor = true;
					paleForeColor = new Color (display, 170, 250, 170);
					lightForeColor = new Color (display, 100, 250, 100);
					buttonBackColor = new Color (display, 200, 200, 200);
					buttonForeColor = new Color (display, 30, 50, 30);
					backColor = new Color (display, 83, 82, 82); 
					setComponentsColors();
				}
			} 
		}
	};
	
	/**
	 * Слушатель нажатия кнопки выбора светлой темы.
	 */
	SelectionAdapter lightThemeSelection = new SelectionAdapter () {
		@Override
		public void widgetSelected (SelectionEvent event) {
			if (lightThemeButton.getSelection() == true) {
				darkThemeButton.setSelection(false);
				if (isDarkColor == true) {
					isDarkColor = false;
					paleForeColor = new Color (display, 45, 5, 5); 
					lightForeColor = new Color (display, 115, 5, 5);
					buttonBackColor = new Color (display, 223, 220, 215);
					buttonForeColor = new Color (display, 65, 65, 65);
					backColor = new Color (display, 255, 252, 245); 
					setComponentsColors();
				}
			} 
		}
	};
	
	/**
	 * Слушатель изменения текста в списке Combo.
	 */
	ModifyListener comboModification = new ModifyListener () {
		@Override
		public void modifyText(ModifyEvent event) {
			shell.setDefaultButton(openingStorageButton);
		}
		
	};
	
	/**
	 * Слушатель установки курсора в текстовое поле userNumbersText.
	 */
	FocusListener numberTextFocusing = new FocusListener () {
		@Override
		public void focusGained(FocusEvent arg0) {
			shell.setDefaultButton(generatingButton);
		}
		@Override
		public void focusLost(FocusEvent arg0) {
		}
	};
	
	/**
	 * Метод для установки цветов на кнопках.
	 * @param b1 - объект класса Button
	 * @param b2 - объект класса Button
	 * @param b3 - объект класса Button
	 * @param b4 - объект класса Button
	 */
	private void setButtonsDefaultColor(Button b1, Button b2, Button b3, Button b4) {
		b1.setBackground(backColor);
		b2.setBackground(backColor);
		b3.setBackground(backColor);
		b4.setBackground(backColor);
		b1.setForeground(paleForeColor);
		b2.setForeground(paleForeColor);
		b3.setForeground(paleForeColor);
		b4.setForeground(paleForeColor);
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
						setButtonsDefaultColor(deletingUserButton, addingUserButton, generatingButton, deletingAllUsersButton);
						setButton (openingStorageButton, true);
						break;
					}
					if (buffer.equals("{Добавить}")) {
						setButtonsDefaultColor(deletingUserButton, openingStorageButton, generatingButton, deletingAllUsersButton);
						setButton (addingUserButton, true);
						break;
					}
					if (buffer.equals("{Удалить}")) {
						setButtonsDefaultColor(addingUserButton, openingStorageButton, generatingButton, deletingAllUsersButton);
						setButton (deletingUserButton, true);
						break;
					}
					if (buffer.equals("{Сгенерировать случайных пользователей}")) {
						setButtonsDefaultColor(deletingUserButton, addingUserButton, openingStorageButton, deletingAllUsersButton);
						setButton (generatingButton, true);
						break;
					}
					if (buffer.equals("{Удалить всех}")) {
						setButtonsDefaultColor(deletingUserButton, addingUserButton, openingStorageButton, generatingButton);
						setButton (deletingAllUsersButton, true);
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
			
			if (!(combo.getText().equals(defaultDatabaseName)) && !(combo.getText().equals(defaultFileName)) && !(combo.getText().equals("")))
				createMessageBox (SWT.ERROR, "Некорректное имя хранилища.");
				if (isConnection) {
					commandsExecuter.reset();
					try {
						storage.closeStorage();
					} catch (Exception e) {
						createMessageBox (SWT.ERROR, e.getMessage());
						return;
					}
				} else isConnection = true;
				
				setStorage();	
				clearTextFields();
				if (table.getItemCount() < 8) shell.pack();
				shell.setDefaultButton(addingUserButton);
			if (lightThemeButton.getSelection() == true) {
				shellProperties = shellPropertiesFactory.getShellProperties(storage, backColor, isDarkColor);
				setShell(shell);
			}
		}
	};
	
	/**
	 * Слушатель нажатия кнопки "Сгенерировать случайных пользователей"
	 */
	SelectionAdapter generatingSelection = new SelectionAdapter () {
		@Override
		public void widgetSelected (SelectionEvent event) {
			if (! ((userChoosingLabel.getVisible() == true) || (userNumbersText.getVisible() == true))) {
				userChoosingLabel.setVisible(true);
				userNumbersText.setVisible (true);
				return;
			}
			if (!isConnection) {
				createMessageBox(SWT.ERROR, "Выберите хранилище данных.");
				return;
			}
			ErrorChecker errorChecker = createErrorChecker (true);
			if (errorChecker.getMessageCode() != SWT.OK) {
				createMessageBox (errorChecker.getMessageCode(), errorChecker.getErrorMesssage());
				return;
			}
			int tableItemCount = table.getItemCount();
			int userNumbers = Integer.parseInt(userNumbersText.getText());
			
			try {
				commandsExecuter.execute (new CommandGenerate (storage, userNumbers));
			} catch (Exception e) {
				createMessageBox (SWT.ERROR, e.getMessage());
			}
			tableViewer.refresh();
			if ((tableItemCount + userNumbers) < 9) shell.pack();
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
			ErrorChecker errorChecker = createErrorChecker(false);
			
			if (errorChecker.getMessageCode() == SWT.OK) {
				
				User user = new User();
				user.setName(nameText.getText());
				user.setSurname(surnameText.getText()); 
				user.setAge(Integer.parseInt(ageText.getText()));
				user.setIsActive(isActiveButton.getSelection());
				
				try {
					commandsExecuter.execute(new CommandAdd (storage, user));
					tableViewer.refresh();
					clearTextFields();
					if ((table.getItemCount() + 1) < 9) shell.pack();
				} catch(Exception e) { 		
					createMessageBox (SWT.ERROR, e.getMessage());
				} 	
			} else createMessageBox (errorChecker.getMessageCode(), errorChecker.getErrorMesssage());
		}
	};
	
	/**
	 * Слушатель нажатия на строки таблицы.
	 */
	ISelectionChangedListener tableRowSelection = new ISelectionChangedListener() {
        @Override
        public void selectionChanged(SelectionChangedEvent event) {
            IStructuredSelection selection = tableViewer.getStructuredSelection();
            User user = (User) selection.getFirstElement();
            if (user != null) {
            	selectedId = user.getId();
            	nameText.setText(user.getName());
    			surnameText.setText(user.getSurname());
    			ageText.setText(Integer.toString(user.getAge()));
    			isActiveButton.setSelection(user.isActive());
    			shell.setDefaultButton(addingUserButton);
            }
        }
    };
	
	/**
	 * Слушатель нажатия "Control + z".
	 */
	KeyAdapter cancelPressingListener  = new KeyAdapter() {
		@Override
		public void keyPressed (KeyEvent key) {
			
			if ((int)key.character == 0x1a) { //Код комбинации "Control + z"
				if (commandsExecuter.getCommandsListSize() != 0) {
					try {
						commandsExecuter.undo();
					} catch (Exception e) {
						createMessageBox(SWT.ERROR, e.getMessage());
						return;
					}
					clearTextFields();
					tableViewer.refresh();
				} else return;
			}
		}
	};
	
	/**
	 * Слушатель нажатия "Control + y".
	 */
	KeyAdapter doubleCancelPressingListener  = new KeyAdapter() {
		@Override
		public void keyPressed (KeyEvent key) {
			
			if ((int)key.character == 0x19) { //Код комбинации "Control + Y"
				if (commandsExecuter.getCommandsListSize() != 0) {
					try {
						commandsExecuter.redo();
					} catch (Exception e) {
						createMessageBox(SWT.ERROR, e.getMessage());
						return;
					}
					clearTextFields();
					tableViewer.refresh();
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
			ErrorChecker errorChecker = createErrorChecker(false);
			if (errorChecker.getMessageCode() != SWT.OK) {
				createMessageBox(SWT.ICON_WARNING, "Пользователь не выбран.");
				return;
			}
			clearTextFields();
			
			try {
				List<User> users = new ArrayList<User>();
				users = storage.getUsersDataSet(false, false);
				User user = null;
				for (User temp : users) {
					if (temp.getId() == selectedId) user = temp;
				}
				commandsExecuter.execute(new CommandDelete (storage, user));
			
				titleLabel.setText("Добавление пользователя:");
			
				tableViewer.refresh();
				if (table.getItemCount() == 0) storage.updateStorageObject();
			} catch (Exception e) {
				createMessageBox(SWT.ERROR, e.getMessage());
			}
		}
	};
	
	/**
	 * Слушатель нажатия кнопки "Удалить всех".
	 */
	SelectionAdapter deletingAllUsersSelection = new SelectionAdapter() {
		@Override
		public void widgetSelected (SelectionEvent event) {
			if (!isConnection) {
				createMessageBox(SWT.ERROR, "Выберите хранилище данных.");
				return;
			}
			if (table.getItemCount() == 0) {
				createMessageBox(SWT.ICON_WARNING, "Нет данных для удаления.");
				return;
			}
			clearTextFields();
			try {
				commandsExecuter.execute(new CommandDeleteAll (storage));
				titleLabel.setText("Добавление пользователя:");
				tableViewer.refresh();
				storage.updateStorageObject();
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
			if (combo.getText().equals("")) combo.setText(defaultDatabaseName);
			storage.setStorage(); 
			shellProperties = shellPropertiesFactory.getShellProperties(storage);
			setShell (shell);
			storage.createStorageObject();
			ModelProvider modelProvider = new ModelProvider(storage);
			tableViewer.setInput(modelProvider);
			tableViewer.setComparator(comparator);
			setEditingSupportForColumns();
			tableViewer.refresh();
		} catch (Exception e) {
			createMessageBox(SWT.ERROR, e.getMessage());
			return;
		}
	}
}