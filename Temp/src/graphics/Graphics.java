package graphics;

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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;

import commands.Command;
import commands.CommandAdd;
import commands.CommandDelete;
import commands.CommandDeleteAll;
import commands.CommandGenerate;
import commands.CommandUpdate;
import commands.CommandsExecuter;
import editing.AgeEditingSupport;
import editing.InputValidationResultHandler;
import editing.IsActiveEditingSupport;
import editing.NameEditingSupport;
import editing.SurnameEditingSupport;
import errors.ErrorChecker;
import storages.Storage;
import storages.StorageFactory;
import user.UsersModelProvider;
import user.User;
import user.UserData;
import user.UserEditingListener;
import user.UsersContentProvider;

/**
 * Класс предназначен для создания интерфейса программы и реализации взаимодействия с пользователем через данный интерфейс.
 */
public class Graphics 
{
	private ShellPropertiesFactory m_shellPropertiesFactory;
	private ShellProperties m_shellProperties;
	private Display m_display; 
	private Shell m_shell; 
	private Color m_paleForeColor; 
	private Color m_lightForeColor;
	private Color m_buttonBackColor;
	private Color m_buttonForeColor;
	private Color m_backColor; 
	private Font m_font; 
	
	private TableViewer m_tableViewer;
	private TableViewerComparator m_comparator;
	
	private Button m_darkThemeButton;
	private Button m_lightThemeButton;
	private Composite m_heightComposite;
	private Composite m_genAndAddComposite;
	private Composite m_themeComposite;
	private Composite m_tableComposite;
	private Composite m_downComposite;
	private Combo m_combo;
	private Button m_openingStorageButton;
	private Label m_titleLabel;
	private Label m_lightThemeLabel;
	private Label m_darkThemeLabel;
	private Label m_choosingLabel;
	private Label m_nameLabel;
	private Label m_surnameLabel;
	private Label m_ageLabel;
	private Label m_isActiveLabel;
	private Label m_userChoosingLabel;
	private Text m_nameText;
	private Text m_surnameText;
	private Text m_ageText;
	private Text m_userNumbersText;
	private Button m_generatingButton;
	private Button m_addingUserButton;
	private Button m_isActiveButton;
	private Table m_table;
	private Button m_deletingUserButton;
	private Button m_deletingAllUsersButton;
	
	private final String m_defaultDatabaseName = "jdbc:h2:~/test";
	private final String m_defaultFileName = "file.txt";
	
	private AgeEditingSupport m_ageEditingSupport;
	private NameEditingSupport m_nameEditingSupport;
	private SurnameEditingSupport m_surnameEditingSupport;
	private IsActiveEditingSupport m_isActiveEditingSupport;
	
	private TableViewerColumn m_nameColumn;
	private TableViewerColumn m_surnameColumn;
	private TableViewerColumn m_ageColumn;
	private TableViewerColumn m_isActiveColumn;
	
	private StorageFactory m_storageFactory;
	private Storage m_storage;
	
	private boolean m_isDarkColor;
	private int m_selectedId;
	private boolean m_isConnection;
	
	private CommandsExecuter m_commandsExecuter;
	
	/**
	 * Конструктор класса Graphics.
	 */
	public Graphics() 
	{
		m_display = new Display();
		m_shell = new Shell (m_display);
		m_shellPropertiesFactory = new ShellPropertiesFactory();
		m_storageFactory = new StorageFactory();
		m_isConnection = false;
		m_commandsExecuter = new CommandsExecuter();
		m_isDarkColor = true;
		m_comparator = new TableViewerComparator();
	}
	
	/**
	 * Метод для создания и инициализации графических компонентов.
	 */
	public void createWindow() 
	{
		m_display.addFilter(SWT.KeyDown, m_cancelPressingListener);
		m_display.addFilter(SWT.KeyDown, m_doubleCancelPressingListener);
		
		m_paleForeColor = new Color (m_display, 170, 250, 170); 
		m_lightForeColor = new Color (m_display, 100, 250, 100);
		m_buttonBackColor = new Color (m_display, 200, 200, 200);
		m_buttonForeColor = new Color (m_display, 30, 50, 30);
		m_backColor = new Color (m_display, 83, 82, 82);   
		m_font = new Font(m_display, "Courier New", 13, SWT.NORMAL);
		
		m_shellProperties = m_shellPropertiesFactory.getShellProperties(m_storage, new Point (900, 550), m_backColor, m_isDarkColor);
		setShell(m_shell);
		createGridLayout(m_shell); 
		
		m_themeComposite = new Composite (m_shell, SWT.BORDER);
		GridData m_gridData = createGridData(SWT.FILL, true, 0, 0, 0, 2);
		m_themeComposite.setBackground(m_backColor);
		m_themeComposite.setLayoutData(m_gridData);
		createGridLayout (m_themeComposite, 4);
		
		m_darkThemeLabel = new Label (m_themeComposite, SWT.NONE);
		m_gridData = createGridData(SWT.RIGHT, false, 0, 0, 0, 0);
		m_darkThemeLabel.setLayoutData(m_gridData);
		setLabel (m_darkThemeLabel, "Тёмная тема:");
		
		m_darkThemeButton = new Button (m_themeComposite, SWT.RADIO);
		m_gridData = createGridData (SWT.LEFT, false, 0, 0, 0, 0);
		m_darkThemeButton.setLayoutData(m_gridData);
		setButton(m_darkThemeButton, ""); 
		m_darkThemeButton.setSelection(true);
		
		m_lightThemeLabel = new Label (m_themeComposite, SWT.NONE);
		m_gridData = createGridData(SWT.RIGHT, false, 0, 0, 0, 0);
		m_lightThemeLabel.setLayoutData(m_gridData);
		setLabel (m_lightThemeLabel, "Светлая тема:");
		
		m_lightThemeButton = new Button (m_themeComposite, SWT.RADIO);
		m_gridData = createGridData (SWT.LEFT, false, 0, 0, 0, 0);
		m_lightThemeButton.setLayoutData(m_gridData);
		setButton(m_lightThemeButton, ""); 
	
		m_darkThemeButton.addSelectionListener(m_darkThemeSelection);
		m_lightThemeButton.addSelectionListener(m_lightThemeSelection);
		
		//Композит для размещения choosingLabel с надписью "Имя хранилища", списка m_combo для временного хранения имён хранилищ и кнопки "Открыть" для открытия указанного хранилища:
		m_heightComposite = new Composite (m_shell, SWT.BORDER);
		m_gridData = createGridData(SWT.FILL, true, 0, 0, 0, 2);
		m_heightComposite.setBackground(m_backColor);
		m_heightComposite.setLayoutData(m_gridData);
		
		createGridLayout (m_heightComposite, 2);
		
		m_choosingLabel = new Label (m_heightComposite, SWT.NONE);
		m_gridData = createGridData(SWT.LEFT, false, 0, 0, 0, 0);
		m_choosingLabel.setLayoutData(m_gridData);
		setLabel(m_choosingLabel, "Имя хранилища:");
		
		m_combo = new Combo (m_heightComposite, SWT.DROP_DOWN);
		m_gridData = createGridData(SWT.FILL, true, 0, 0, 0, 0);
		m_combo.setLayoutData(m_gridData);
		setCombo (m_combo);
		m_combo.add(m_defaultDatabaseName);
		m_combo.add(m_defaultFileName);
		m_combo.addModifyListener(m_comboModification);
		
		m_openingStorageButton = new Button (m_heightComposite, SWT.PUSH);
		m_gridData = createGridData(SWT.RIGHT, false, 0, 0, 0, 2);
		int choosingButtonWidthHint = m_gridData.widthHint;
		m_openingStorageButton.setLayoutData(m_gridData);
		setButton(m_openingStorageButton, "Открыть"); 
		
		m_titleLabel = new Label(m_shell, SWT.NONE);
		m_gridData = createGridData(SWT.CENTER, false, 0, 0, 0, 2);
		m_titleLabel.setLayoutData(m_gridData);
		setLabel(m_titleLabel, "Добавление пользователя:");
		
		m_nameLabel = new Label(m_shell, SWT.NONE);
		m_gridData = createGridData(SWT.RIGHT, false, 0, 0, 0, 0);
		m_nameLabel.setLayoutData(m_gridData);
		setLabel(m_nameLabel, "Имя:");
		
		m_nameText = new Text (m_shell, SWT.BORDER);
		m_gridData = createGridData(SWT.FILL, true, 0, 0, 0, 0);
		m_nameText.setLayoutData(m_gridData);
		setText(m_nameText, true);
		
		m_surnameLabel = new Label (m_shell, SWT.NONE);
		m_gridData = createGridData(SWT.RIGHT, false, 0, 0, 0, 0);
		m_surnameLabel.setLayoutData(m_gridData);
		setLabel (m_surnameLabel, "Фамилия:");
		
		m_surnameText = new Text (m_shell, SWT.BORDER);
		m_gridData = createGridData(SWT.FILL, true, 0, 0, 0, 0);
		m_surnameText.setLayoutData(m_gridData);
		setText(m_surnameText, true);
		
		m_ageLabel = new Label (m_shell, SWT.NONE);
		m_gridData = createGridData(SWT.RIGHT, false, 0, 0, 0, 0);
		m_ageLabel.setLayoutData(m_gridData);
		setLabel (m_ageLabel, "Возраст:");
		
		m_ageText = new Text (m_shell, SWT.BORDER);
		m_gridData = createGridData(SWT.FILL, true, 0, 0, 0, 0);
		m_ageText.setLayoutData(m_gridData);
		setText(m_ageText, true);
		
		m_isActiveLabel = new Label (m_shell, SWT.NONE);
		m_gridData = createGridData(SWT.RIGHT, false, 0, 0, 0, 0);
		m_isActiveLabel.setLayoutData(m_gridData);
		setLabel (m_isActiveLabel, "Активен:");
		
		m_isActiveButton = new Button (m_shell, SWT.CHECK);
		m_gridData = createGridData(SWT.LEFT, false, 0, 0, 0, 0);
		m_isActiveButton.setLayoutData(m_gridData);
		setButton(m_isActiveButton, ""); 
		
		m_addingUserButton = new Button (m_shell, SWT.PUSH);
		m_gridData = createGridData (SWT.RIGHT, false, 120, 30, 50, 2);
		m_gridData.widthHint = choosingButtonWidthHint;
		m_addingUserButton.setLayoutData(m_gridData);
		setButton(m_addingUserButton, "Добавить"); 
		
		m_genAndAddComposite = new Composite (m_shell, SWT.NONE);
		m_gridData = createGridData(SWT.FILL, true, 0, 0, 0, 2);
		m_genAndAddComposite.setBackground(m_backColor);
		m_genAndAddComposite.setLayoutData(m_gridData);
		createGridLayout (m_genAndAddComposite, 3);
		
		m_generatingButton = new Button (m_genAndAddComposite, SWT.PUSH);
		m_gridData = createGridData (SWT.LEFT, false, 0, 0, 0, 0);
		m_generatingButton.setLayoutData(m_gridData);
		setButton (m_generatingButton, "Сгенерировать случайных пользователей");
		
		m_generatingButton.addSelectionListener(m_generatingSelection);
		
		m_userChoosingLabel = new Label (m_genAndAddComposite, SWT.NONE);
		m_gridData = createGridData(SWT.RIGHT, true, 0, 0, 0, 0);
		m_userChoosingLabel.setLayoutData(m_gridData);
		setLabel (m_userChoosingLabel, "Количество пользователей:");
		m_userChoosingLabel.setVisible(false);
		
		m_userNumbersText = new Text (m_genAndAddComposite, SWT.BORDER);
		m_gridData = createGridData(SWT.FILL, true, 0, 0, 0, 0);
		m_userNumbersText.setLayoutData(m_gridData);
		setText(m_userNumbersText, true);
		m_userNumbersText.setVisible(false);
		m_userNumbersText.setText("5");
		m_userNumbersText.addFocusListener(m_numberTextFocusing);
		
		m_shell.setDefaultButton(m_openingStorageButton);
		m_addingUserButton.addSelectionListener(m_addingUserSelection);
		
		m_tableComposite = new Composite (m_shell, SWT.BORDER);
		m_gridData = createGridData(SWT.FILL, true, 0, 0, 0, 2);
		m_gridData.verticalAlignment = SWT.FILL;
		m_gridData.grabExcessVerticalSpace = true;
		m_tableComposite.setLayoutData(m_gridData);
		
		m_tableViewer = new TableViewer(m_tableComposite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns();
		m_table = m_tableViewer.getTable();
	    setTable(m_table, false);
	    
	    m_tableViewer.setContentProvider(new UsersContentProvider());
	    m_tableViewer.addSelectionChangedListener(m_tableRowSelection);
	    
	    TableColumnLayout m_tableColumnLayout = new TableColumnLayout();
	    m_tableComposite.setLayout(m_tableColumnLayout);
	    	
	    ColumnWeightData m_columnWeightData = new ColumnWeightData(100);
	    m_columnWeightData.resizable = true;
	    m_columnWeightData.minimumWidth = 100;
	    m_tableColumnLayout.setColumnData(m_table.getColumn(0), m_columnWeightData);
	    m_tableColumnLayout.setColumnData(m_table.getColumn(1), m_columnWeightData);
	    m_tableColumnLayout.setColumnData(m_table.getColumn(2), m_columnWeightData);
	    m_tableColumnLayout.setColumnData(m_table.getColumn(3), m_columnWeightData);
	    m_tableColumnLayout.setColumnData(m_table.getColumn(4), m_columnWeightData);
	   	
	    m_downComposite = new Composite (m_shell, SWT.BORDER);
	    m_gridData = createGridData(SWT.FILL, true, 0, 0, 0, 2);
	    m_downComposite.setBackground(m_backColor);
	    m_downComposite.setLayoutData(m_gridData);
	    createGridLayout (m_downComposite, 2);
		
	    m_deletingUserButton = new Button (m_downComposite, SWT.PUSH);
	    m_gridData = createGridData (SWT.RIGHT, true, 0, 0, 500, 0);
	    m_deletingUserButton.setLayoutData(m_gridData);
		setButton(m_deletingUserButton, "Удалить"); 
		
		m_deletingAllUsersButton = new Button (m_downComposite, SWT.PUSH);
		m_gridData = createGridData (SWT.RIGHT, false, 0, 0, 0, 0);
		m_deletingAllUsersButton.setLayoutData(m_gridData);
		setButton(m_deletingAllUsersButton, "Удалить всех"); 
		m_deletingAllUsersButton.addSelectionListener(m_deletingAllUsersSelection);
		
		m_openingStorageButton.addSelectionListener(m_isOpeningSelection);
		m_deletingUserButton.addSelectionListener(m_deletingUserSelection);
		
		m_shell.pack(); //Установка оптимального размера окна под имеющиеся компоненты
		m_shell.open(); //Открытие окна
			
		/* Пока ресурсы, ассоциированные с m_shell, не освобождены,
		 * если m_display не используется, выполнение текущего потока приостанавливается:*/
		while (!m_shell.isDisposed()) 
		{ 
			if (!m_display.readAndDispatch()) m_display.sleep(); 
		}
		
		//Освобождение ресурсов:
		m_backColor.dispose();
		m_lightForeColor.dispose();
		m_paleForeColor.dispose();
		m_buttonBackColor.dispose();
		m_buttonForeColor.dispose();
		m_font.dispose();
		m_display.dispose(); 	
	}
	
	/**
	 * Метод для установки поддержки редактирования для всех столбцов таблицы.
	 */
	private void setEditingSupportForColumns() 
	{
		//Слушатель неверного ввода данных в таблицу:
		InputValidationResultHandler m_errorInputListener = new InputValidationResultHandler() 
		{
			@Override
			public void createMessage(String a_message) 
			{
				createMessageBox (SWT.ERROR, a_message);
			}
		};
		m_ageEditingSupport = new AgeEditingSupport (m_tableViewer, m_userEditingListener, m_errorInputListener);
		m_ageColumn.setEditingSupport(m_ageEditingSupport);
		
		m_nameEditingSupport = new NameEditingSupport(m_tableViewer, m_userEditingListener, m_errorInputListener);
		m_nameColumn.setEditingSupport(m_nameEditingSupport);
		
		m_surnameEditingSupport = new SurnameEditingSupport(m_tableViewer, m_userEditingListener, m_errorInputListener);
		m_surnameColumn.setEditingSupport(m_surnameEditingSupport);
		
		m_isActiveEditingSupport = new IsActiveEditingSupport(m_tableViewer, m_userEditingListener);
		m_isActiveColumn.setEditingSupport(m_isActiveEditingSupport);
	}
	
	/**
	 * Слушатель нажатия на столбец таблицы.
	 */
	private UserEditingListener m_userEditingListener = new UserEditingListener () 
	{
		@Override
		public void changeUserName(User a_user, String a_name) 
		{
			executeCommandAndRefreshViewer (new CommandUpdate (m_storage, a_user, a_name, UserData.FIRSTNAME));	
		}
		@Override
		public void changeUserSurname(User a_user, String a_surname) 
		{
			executeCommandAndRefreshViewer (new CommandUpdate (m_storage, a_user, a_surname, UserData.LASTNAME));
		}
		@Override
		public void changeUserAge(User a_user, int a_age) 
		{
			executeCommandAndRefreshViewer (new CommandUpdate (m_storage, a_user, a_age, UserData.AGE));
		}
		@Override
		public void changeUserIsActive(User a_user, boolean a_isActive) 
		{
			executeCommandAndRefreshViewer (new CommandUpdate (m_storage, a_user, a_isActive, UserData.ISACTIVE));
		}
		
	};
	
	/**
	 * Метод для вызова метода execute() переданной команды и обновления объекта a_tableViewer класса TableViewer.
	 */
	private void executeCommandAndRefreshViewer(Command a_command) 
	{
		try 
		{
			m_commandsExecuter.execute(a_command);
			m_tableViewer.refresh();
		} catch (Exception a_e) 
		{
			createMessageBox (SWT.ERROR, a_e.getMessage(), "Ошибка выполнения команды.");
		}
	}
	
	/**
	 * Метод создаёт столбцы TableViewerColumn и устанавливает на них CellLabelProvider.
	 */
	private void createColumns() 
	{
		TableViewerColumn m_viewerColumn = createTableViewerColumn ("Код", 0);
		m_viewerColumn.setLabelProvider(new CellLabelProvider() 
		{
            @Override
            public void update(ViewerCell a_cell) 
            {
                a_cell.setText(Integer.toString((((User) a_cell.getElement()).getId())));
            }
        });
		
		m_nameColumn = createTableViewerColumn ("Имя", 1);
		m_nameColumn.setLabelProvider(new CellLabelProvider() 
		{
            @Override
            public void update(ViewerCell a_cell) 
            {
            	a_cell.setText(((User) a_cell.getElement()).getName());
            }
        });
		
		m_surnameColumn = createTableViewerColumn ("Фамилия", 2);
		m_surnameColumn.setLabelProvider(new CellLabelProvider() 
		{
            @Override
            public void update(ViewerCell a_cell) 
            {
            	a_cell.setText(((User) a_cell.getElement()).getSurname());
            }
        });
		
		m_ageColumn = createTableViewerColumn ("Возраст", 3);
		m_ageColumn.setLabelProvider(new CellLabelProvider() 
		{
            @Override
            public void update(ViewerCell a_cell) 
            {
            	a_cell.setText(Integer.toString((((User) a_cell.getElement()).getAge())));
            }
        });
		
		m_isActiveColumn = createTableViewerColumn ("Активен", 4);
		m_isActiveColumn.setLabelProvider(new CellLabelProvider() 
		{
            @Override
            public void update(ViewerCell a_cell) 
            {
            	a_cell.setText(Boolean.toString((((User) a_cell.getElement()).isActive())));
            }
        });
	}
	
	/**
	 * Метод для установки свойств компонента a_shell.
	 * @param a_shell - объект класса Shell
	 */
	private void setShell (Shell a_shell) 
	{
		a_shell.setText (m_shellProperties.getTitle());
		a_shell.setImage(m_shellProperties.getImage());
		a_shell.setMinimumSize(m_shellProperties.getSize());
		a_shell.setBackground(m_shellProperties.getBackColor());
	}
	
	/**
	 * Создание таблицы из ячеек на a_shell для расположения в них графических компонентов.
	 * @param a_s - объект класса Shell
	 */
	private void createGridLayout(Shell a_s) 
	{
		GridLayout m_g = new GridLayout();
		m_g.numColumns = 2; //Количество столбцов
		m_g.marginWidth = 5; //Расстояние от левого и правого краев окна
		m_g.marginHeight = 5; //Расстояние от верхнего и нижненго краев окна
		m_g.horizontalSpacing = 5; //Расстояние между соседними ячейками по горизонтали
		m_g.verticalSpacing = 10; //Расстояние между соседними ячейками по вертикали
		a_s.setLayout(m_g); //Установка слоя на окно shell
	}
	
	/**
	 * Создание таблицы из ячеек на a_composite для расположения в них графических компонентов.
	 * @param a_composite - объект класса Composite
	 * @param a_numColumns - количество столбцов
	 */
	private void createGridLayout(Composite a_composite, int a_numColumns) 
	{
		GridLayout m_g = new GridLayout();
		m_g.numColumns = a_numColumns; //Количество столбцов
		m_g.marginWidth = 5; //Расстояние от левого и правого краев окна
		m_g.marginHeight = 5; //Расстояние от верхнего и нижненго краев окна
		m_g.horizontalSpacing = 5; //Расстояние между соседними ячейками по горизонтали
		m_g.verticalSpacing = 10; //Расстояние между соседними ячейками по вертикали
		a_composite.setLayout(m_g); //Установка слоя на окно shell
	}
	
	/**
	 * Метод для создания объекта класса GridData (для управления данными, связанными с различными компонентами).
	 * Обязательным является указание значений a_horAl и a_grab, остальные параметры можно передать как 0 (будут установлены значения по умолчанию).
	 * @param a_horAl - выравнивание по горизонтали
	 * @param a_grab - разрешение/запрет захвата свободного пространства по горизонтали при изменении размеров окна
	 * @param a_width - ширина компонента
	 * @param a_height - высота компонента
	 * @param a_horInd - отступ по горизонтали от начального положения (что задаётся способом выравнивания) компонента в ячейке
	 * @param a_horSpan - количество ячеек, занимаемое компонентом по горизонтали
	 * @return a_gridData - объект класса GridData
	 */
	private GridData createGridData(int a_horAl, boolean a_grab, int a_width, int a_height, int a_horInd, int a_horSpan) 
	{
		GridData m_gridData = new GridData();
		m_gridData.horizontalAlignment = a_horAl; 
		m_gridData.grabExcessHorizontalSpace = a_grab; 
		if (a_width != 0) m_gridData.widthHint = a_width; 
		if (a_height != 0) m_gridData.heightHint = a_height; 
		if (a_horInd != 0) m_gridData.horizontalIndent = a_horInd; 
		if (a_horSpan != 0) m_gridData.horizontalSpan = a_horSpan; 
		return m_gridData;
	}
	
	/**
	 * Метод для установки свойств компонента Combo.
	 *  * @param a_combo - объект класса Combo
	 */
	private void setCombo  (Combo a_combo) 
	{
		a_combo.setBackground(m_backColor);
		a_combo.setForeground(m_lightForeColor);
		a_combo.setFont(m_font);
	}
	
	/**
	 * Метод установки свойств для компонента Label
	 * @param a_label - компонент Label
	 * @param a_text - текст
	 */
	private void setLabel(Label a_label, String a_text) 
	{
		a_label.setText(a_text); 
		a_label.setBackground(m_backColor);
		a_label.setForeground(m_paleForeColor);
		a_label.setFont(m_font); 
	}
	
	/**
	 * Метод установки свойств для компонента Label
	 * @param a_label - компонент Label
	 */
	private void setLabel(Label a_label) 
	{
		a_label.setBackground(m_backColor);
		a_label.setForeground(m_paleForeColor);
	}
	
	/**
	 * Метод установки свойств для компонента Text.
	 * @param a_text - компонент Text
	 * @param a_editable - редактируемость
	 */
	private void setText (Text a_text, boolean a_editable) 
	{ 
		a_text.setEditable(a_editable); 
		a_text.setBackground(m_backColor);
		a_text.setForeground(m_lightForeColor);
		a_text.setFont(m_font);
	}
	
	/**
	 * Метод установки свойств для компонента Text.
	 * @param a_text - компонент Text
	 */
	private void setText (Text a_text) 
	{ 
		a_text.setBackground(m_backColor);
		a_text.setForeground(m_lightForeColor);
	}
	
	/**
	 * Метод установки свойств для компонента Button.
	 * @param a_button - компонент Button
	 * @param a_text - название кнопки
	 */
	private void setButton(Button a_button, String a_text) 
	{ 
		a_button.setText(a_text);
		a_button.setBackground(m_backColor);
		a_button.setForeground(m_paleForeColor);
		a_button.setFont(m_font);
		a_button.addSelectionListener(m_isPressingSelection);
	}	
	
	/**
	 * Метод установки свойств для компонента Button.
	 * @param a_button - компонент Button
	 * @param a_isOnlyChoosingButtonColors - установить только цвета, соответствующие цветам последней нажатой кнопке / не устанавливать их
	 */
	private void setButton(Button a_button, boolean a_isOnlyChoosingButtonColors) 
	{ 
		if (a_isOnlyChoosingButtonColors) 
		{
			a_button.setBackground(m_buttonBackColor);
			a_button.setForeground(m_buttonForeColor);
			return;
		} 
		a_button.setBackground(m_backColor);
		a_button.setForeground(m_paleForeColor);
	}	
	
	/**
	 * Метод создаёт столбец в таблице Table и устанавливает слушатель нажатия на данный столбец.
	 * @param a_title - заголовок столбца
	 * @param a_colNumber - номер столбца
	 * @return столбец TableViewerColumn
	 */
	private TableViewerColumn createTableViewerColumn(String a_title, int a_colNumber) 
	{
		TableViewerColumn m_viewerColumn = new TableViewerColumn(m_tableViewer, SWT.CENTER);
        TableColumn m_column = m_viewerColumn.getColumn();
        m_column.setText(a_title);
        m_column.addSelectionListener(getSelectionAdapter(m_column, a_colNumber));
        return m_viewerColumn;
	}

	 /**
	  * Метод для возврата слушателя нажатия на столбец таблицы.
	 * @param a_column - столбец таблицы
	 * @param a_index - номер столбца
	 * @return слушатель SelectionAdapter
	 */
	private SelectionAdapter getSelectionAdapter(final TableColumn a_column, final int a_index) 
	{
	        SelectionAdapter m_selectionAdapter = new SelectionAdapter() 
	        {
	            @Override
	            public void widgetSelected(SelectionEvent a_e) 
	            {
	            	m_comparator.setColumn(a_index);
	                int m_dir = m_comparator.getDirection();
	                m_tableViewer.getTable().setSortDirection(m_dir);
	                m_tableViewer.getTable().setSortColumn(a_column);
	                m_tableViewer.refresh();
	            }
	        };
	        return m_selectionAdapter;
	}
	 
	/**
	 * Метод для устновки свойств компонента Table.
	 * @param a_table - объект класса Table
	 * @param a_isOnlyColors - установить только цвета таблицы / установить не только цвета таблицы
	 */
	private void setTable(Table a_table, boolean a_isOnlyColors) 
	{
		a_table.setBackground(m_backColor);
		a_table.setForeground(m_lightForeColor);
		if (!a_isOnlyColors) 
		{
			GridData m_gridData = createGridData(SWT.FILL, true, 0, 0, 0, 2);
			a_table.setLayoutData(m_gridData);
			a_table.setBackground(m_backColor);
			a_table.setForeground(m_lightForeColor);
			a_table.setFont(m_font);
			a_table.setHeaderVisible(true);
			a_table.setLinesVisible(true);
		}
	}
	
	/**
	 * Метод для создания дочернего окна с сообщением пользователю (вид сообщения зависит от переданных параметров).
	 * @param a_messageCode - код сообщения (SWT.ERROR или SWT.ICON_INFORMATION)
	 * @param a_message - сообщение для пользователя
	 * @param a_defaultMessage - сообщение для пользователя по умолчанию (выводится, если a_message = null)
	 */
	private void createMessageBox (int a_messageCode, String a_message, String a_defaultMessage) 
	{
		if (a_message == null) a_message = a_defaultMessage;
		MessageBox m_messageBox = new MessageBox (m_shell, a_messageCode);
		m_messageBox.setText("Сообщение");
		m_messageBox.setMessage(a_message); 
		m_messageBox.open();
	}
	
	/**
	 * Метод для создания дочернего окна с сообщением пользователю (вид сообщения зависит от переданных параметров).
	 * @param a_messageCode - код сообщения (SWT.ERROR или SWT.ICON_INFORMATION)
	 * @param a_message - сообщение для пользователя
	 */
	private void createMessageBox (int a_messageCode, String a_message) 
	{
		MessageBox m_messageBox = new MessageBox (m_shell, a_messageCode);
		m_messageBox.setText("Сообщение");
		m_messageBox.setMessage(a_message); 
		m_messageBox.open();
	}
	
	/**
	 * Метод создаёт объект класса ErrorChecker, вызывает его методы и возвращает изменённый объект.
	 */
	private ErrorChecker createErrorChecker(boolean a_isOnlyUserNumbers) 
	{
		ErrorChecker m_errorChecker = new ErrorChecker();
		if (a_isOnlyUserNumbers) 
		{
			m_errorChecker.checkUserNumbers((m_userNumbersText.getText()));
			return m_errorChecker;
		} 
		m_errorChecker.checkName(m_nameText.getText());
		m_errorChecker.checkName(m_surnameText.getText());
		m_errorChecker.checkAge(m_ageText.getText());
		return m_errorChecker;
	}
	
	/**
	 * Метод устанавливает цвета всех компонентов в зависимости от текущего хранилища.
	 */
	private void setComponentsColors() 
	{
		m_shellProperties = m_shellPropertiesFactory.getShellProperties(m_storage, m_backColor, m_isDarkColor);
		setShell(m_shell);
		setLabel(m_titleLabel); 
		setLabel(m_lightThemeLabel); 
		setLabel(m_darkThemeLabel); 
		setLabel(m_choosingLabel);
		setLabel(m_nameLabel); 
		setLabel(m_surnameLabel);
		setLabel(m_ageLabel); 
		setLabel(m_isActiveLabel); 
		setLabel(m_userChoosingLabel);
		setText(m_nameText); 
		setText(m_surnameText); 
		setText(m_ageText); 
		setText(m_userNumbersText);
		setCombo(m_combo); 
		setButton(m_openingStorageButton, false); 
		setButton(m_addingUserButton, false);
		setButton(m_deletingUserButton, false);
		setButton(m_isActiveButton, false); 
		setButton(m_darkThemeButton, false);
		setButton(m_lightThemeButton, false);  
		setButton(m_generatingButton, false);
		setButton(m_deletingAllUsersButton, false); 
		setTable(m_table, true);		
		m_heightComposite.setBackground(m_backColor); 
		m_themeComposite.setBackground(m_backColor);
		m_tableComposite.setBackground(m_backColor); 
		m_genAndAddComposite.setBackground(m_backColor);
		m_downComposite.setBackground(m_backColor);
	}
	
	/**
	 * Слушатель нажатия кнопки выбора тёмной темы.
	 */
	SelectionAdapter m_darkThemeSelection = new SelectionAdapter() 
	{
		@Override
		public void widgetSelected (SelectionEvent a_event) 
		{
			if (m_darkThemeButton.getSelection() == true) 
			{
				m_lightThemeButton.setSelection(false);
				if (m_isDarkColor == false) 
				{
					m_isDarkColor = true;
					m_paleForeColor = new Color (m_display, 170, 250, 170);
					m_lightForeColor = new Color (m_display, 100, 250, 100);
					m_buttonBackColor = new Color (m_display, 200, 200, 200);
					m_buttonForeColor = new Color (m_display, 30, 50, 30);
					m_backColor = new Color (m_display, 83, 82, 82); 
					setComponentsColors();
				}
			} 
		}
	};
	
	/**
	 * Слушатель нажатия кнопки выбора светлой темы.
	 */
	SelectionAdapter m_lightThemeSelection = new SelectionAdapter() 
	{
		@Override
		public void widgetSelected (SelectionEvent a_event) 
		{
			if (m_lightThemeButton.getSelection() == true) 
			{
				m_darkThemeButton.setSelection(false);
				if (m_isDarkColor == true) 
				{
					m_isDarkColor = false;
					m_paleForeColor = new Color (m_display, 45, 5, 5); 
					m_lightForeColor = new Color (m_display, 115, 5, 5);
					m_buttonBackColor = new Color (m_display, 223, 220, 215);
					m_buttonForeColor = new Color (m_display, 65, 65, 65);
					m_backColor = new Color (m_display, 255, 252, 245); 
					setComponentsColors();
				}
			} 
		}
	};
	
	/**
	 * Слушатель изменения текста в списке m_combo.
	 */
	ModifyListener m_comboModification = new ModifyListener() 
	{
		@Override
		public void modifyText(ModifyEvent a_event) 
		{
			m_shell.setDefaultButton(m_openingStorageButton);
		}
		
	};
	
	/**
	 * Слушатель установки курсора в текстовое поле m_userNumbersText.
	 */
	FocusListener m_numberTextFocusing = new FocusListener() 
	{
		@Override
		public void focusGained(FocusEvent a_arg) 
		{
			m_shell.setDefaultButton(m_generatingButton);
		}
		@Override
		public void focusLost(FocusEvent a_arg) 
		{
		}
	};
	
	/**
	 * Метод для установки цветов на кнопках.
	 * @param a_b1 - объект класса Button
	 * @param a_b2 - объект класса Button
	 * @param a_b3 - объект класса Button
	 * @param a_b4 - объект класса Button
	 */
	private void setButtonsDefaultColor(Button a_b1, Button a_b2, Button a_b3, Button a_b4) 
	{
		a_b1.setBackground(m_backColor);
		a_b2.setBackground(m_backColor);
		a_b3.setBackground(m_backColor);
		a_b4.setBackground(m_backColor);
		a_b1.setForeground(m_paleForeColor);
		a_b2.setForeground(m_paleForeColor);
		a_b3.setForeground(m_paleForeColor);
		a_b4.setForeground(m_paleForeColor);
	}
	
	/**
	 * Слушатель нажатия на любую кнопку (изменяет её цвета).
	 */
	SelectionAdapter m_isPressingSelection = new SelectionAdapter() 
	{
		@Override
		public void widgetSelected(SelectionEvent a_event) 
		{
			String m_buffer = "";
			for (int m_i = 0; m_i < a_event.toString().length(); m_i++) 
			{
				if (a_event.toString().charAt(m_i) == ' ') m_buffer = ""; else
					m_buffer += Character.toString (a_event.toString().charAt(m_i));
					if (m_buffer.equals("{Открыть}")) 
					{
						setButtonsDefaultColor(m_deletingUserButton, m_addingUserButton, m_generatingButton, m_deletingAllUsersButton);
						setButton (m_openingStorageButton, true);
						break;
					}
					if (m_buffer.equals("{Добавить}")) 
					{
						setButtonsDefaultColor(m_deletingUserButton, m_openingStorageButton, m_generatingButton, m_deletingAllUsersButton);
						setButton (m_addingUserButton, true);
						break;
					}
					if (m_buffer.equals("{Удалить}")) 
					{
						setButtonsDefaultColor(m_addingUserButton, m_openingStorageButton, m_generatingButton, m_deletingAllUsersButton);
						setButton (m_deletingUserButton, true);
						break;
					}
					if (m_buffer.equals("{Сгенерировать случайных пользователей}")) 
					{
						setButtonsDefaultColor(m_deletingUserButton, m_addingUserButton, m_openingStorageButton, m_deletingAllUsersButton);
						setButton (m_generatingButton, true);
						break;
					}
					if (m_buffer.equals("{Удалить всех}")) 
					{
						setButtonsDefaultColor(m_deletingUserButton, m_addingUserButton, m_openingStorageButton, m_generatingButton);
						setButton (m_deletingAllUsersButton, true);
						break;
					}
			}
		}
	};
	
	/**
	 * Слушатель нажатия кнопки "Открыть".
	 */
	SelectionAdapter m_isOpeningSelection = new SelectionAdapter() 
	{
		@Override
		public void widgetSelected(SelectionEvent a_event) 
		{
			if (!(m_combo.getText().equals(m_defaultDatabaseName)) && !(m_combo.getText().equals(m_defaultFileName)) && !(m_combo.getText().equals("")))
				createMessageBox (SWT.ERROR, "Некорректное имя хранилища.");
			if (m_isConnection) 
			{
				m_commandsExecuter.reset();
				try 
				{
					m_storage.closeStorage();
				} catch (Exception a_e) 
				{
					createMessageBox (SWT.ERROR, a_e.getMessage(), "Ошибка выполнения закрытия хранилища.");
					return;
				}
			} else m_isConnection = true;	
			
			setStorage();	
			clearTextFields();
			if (m_table.getItemCount() < 8) m_shell.pack();
				m_shell.setDefaultButton(m_addingUserButton);
			if (m_lightThemeButton.getSelection() == true) 
			{
				m_shellProperties = m_shellPropertiesFactory.getShellProperties(m_storage, m_backColor, m_isDarkColor);
				setShell(m_shell);
			}
		}
	};
	
	/**
	 * Слушатель нажатия кнопки "Сгенерировать случайных пользователей"
	 */
	SelectionAdapter m_generatingSelection = new SelectionAdapter() 
	{
		@Override
		public void widgetSelected (SelectionEvent a_event) 
		{
			if (! ((m_userChoosingLabel.getVisible() == true) || (m_userNumbersText.getVisible() == true))) 
			{
				m_userChoosingLabel.setVisible(true);
				m_userNumbersText.setVisible (true);
				return;
			}
			if (!m_isConnection) 
			{
				createMessageBox(SWT.ERROR, "Выберите хранилище данных.");
				return;
			}
			ErrorChecker m_errorChecker = createErrorChecker (true);
			if (m_errorChecker.getMessageCode() != SWT.OK) 
			{
				createMessageBox (m_errorChecker.getMessageCode(), m_errorChecker.getErrorMesssage());
				return;
			}
			int m_tableItemCount = m_table.getItemCount();
			int m_userNumbers = Integer.parseInt(m_userNumbersText.getText());
			
			try 
			{
				m_commandsExecuter.execute (new CommandGenerate (m_storage, m_userNumbers));
			} catch (Exception a_e) 
			{
				createMessageBox (SWT.ERROR, a_e.getMessage(), "Ошибка выполнения команды генерации пользователей.");
			}
			m_tableViewer.refresh();
			if ((m_tableItemCount + m_userNumbers) < 9) m_shell.pack();
		}
	};
	
	/**
	 * Слушатель нажатия кнопки "Добавить".
	 */
	SelectionAdapter m_addingUserSelection = new SelectionAdapter() 
	{
		@Override
		public void widgetSelected(SelectionEvent a_event) 
		{
			if (!m_isConnection) 
			{
				createMessageBox(SWT.ERROR, "Выберите хранилище данных.");
				return;
			}
			m_titleLabel.setText("Добавление пользователя:");
			ErrorChecker m_errorChecker = createErrorChecker(false);
			
			if (m_errorChecker.getMessageCode() == SWT.OK) 
			{
				User m_user = new User();
				m_user.setName(m_nameText.getText());
				m_user.setSurname(m_surnameText.getText()); 
				m_user.setAge(Integer.parseInt(m_ageText.getText()));
				m_user.setIsActive(m_isActiveButton.getSelection());
				
				try 
				{
					m_commandsExecuter.execute(new CommandAdd (m_storage, m_user));
					m_tableViewer.refresh();
					clearTextFields();
					if ((m_table.getItemCount() + 1) < 9) m_shell.pack();
				} catch(Exception a_e) 
				{ 		
					createMessageBox (SWT.ERROR, a_e.getMessage(), "Ошибка выполнения команды добавления пользователя.");
				} 	
			} else createMessageBox (m_errorChecker.getMessageCode(), m_errorChecker.getErrorMesssage());
		}
	};
	
	/**
	 * Слушатель нажатия на строки таблицы.
	 */
	ISelectionChangedListener m_tableRowSelection = new ISelectionChangedListener() 
	{
        @Override
        public void selectionChanged(SelectionChangedEvent a_event) 
        {
            IStructuredSelection m_selection = m_tableViewer.getStructuredSelection();
            User m_user = (User) m_selection.getFirstElement();
            if (m_user != null) 
            {
            	m_selectedId = m_user.getId();
            	m_nameText.setText(m_user.getName());
            	m_surnameText.setText(m_user.getSurname());
    			m_ageText.setText(Integer.toString(m_user.getAge()));
    			m_isActiveButton.setSelection(m_user.isActive());
    			m_shell.setDefaultButton(m_addingUserButton);
            }
        }
    };
	
	/**
	 * Слушатель нажатия "Control + z".
	 */
    Listener m_cancelPressingListener  = new Listener() 
    {
		@Override
		public void handleEvent(Event a_event) 
		{
			if ((int)a_event.character == 0x1a) //Код комбинации "Control + z"
			{
				if (m_commandsExecuter.getCommandsListSize() != 0) 
				{
					try 
					{
						m_commandsExecuter.undo();
					} catch (Exception a_e) 
					{
						createMessageBox(SWT.ERROR, a_e.getMessage(), "Ошибка выполнения отмены команды.");
						return;
					}
					clearTextFields();
					m_tableViewer.refresh();
				} else return;
			}
		}
	};
	
	/**
	 * Слушатель нажатия "Control + y".
	 */
	Listener m_doubleCancelPressingListener = new Listener() 
	{
		@Override
		public void handleEvent(Event a_event)
		{
			if ((int)a_event.character == 0x19) //Код комбинации "Control + Y"
			{
				if (m_commandsExecuter.getCommandsListSize() != 0) 
				{
					try 
					{
						m_commandsExecuter.redo();
					} catch (Exception a_e) 
					{
						createMessageBox(SWT.ERROR, a_e.getMessage(), "Ошибка выполнения отмены отмены команды.");
						return;
					}
					clearTextFields();
					m_tableViewer.refresh();
				} else return;
			}
		}
	};
	
	/**
	 * Метод для удаления текста из полей.
	 */
	private void clearTextFields() 
	{
		m_nameText.setText("");
		m_surnameText.setText("");
		m_ageText.setText("");
		m_isActiveButton.setSelection(false);
	}

	/**
	 * Слушатель нажатия кнопки "Удалить".
	 */
	SelectionAdapter m_deletingUserSelection = new SelectionAdapter() 
	{
		@Override
		public void widgetSelected (SelectionEvent a_event) 
		{
			if (!m_isConnection) 
			{
				createMessageBox(SWT.ERROR, "Выберите хранилище данных.");
				return;
			}
			ErrorChecker m_errorChecker = createErrorChecker(false);
			if (m_errorChecker.getMessageCode() != SWT.OK) 
			{
				createMessageBox(SWT.ICON_WARNING, "Пользователь не выбран.");
				return;
			}
			clearTextFields();
			
			String m_potentialErrorMesaage = "";
			try 
			{
				List<User> m_users = new ArrayList<User>();
				m_potentialErrorMesaage = "Ошибка выполнения получения данных из хранилища.";
				m_users = m_storage.getUsersDataSet(false, false);
				User m_user = null;
				for (User m_temp : m_users) 
					if (m_temp.getId() == m_selectedId) m_user = m_temp;
				
				m_potentialErrorMesaage = "Ошибка выполнения команды удаления пользователя.";
				m_commandsExecuter.execute(new CommandDelete (m_storage, m_user));
				m_titleLabel.setText("Добавление пользователя:");
			
				m_tableViewer.refresh();
				m_potentialErrorMesaage = "Ошибка выполнения обновления хранилища.";
				if (m_table.getItemCount() == 0) m_storage.updateStorageObject();
			} catch (Exception a_e) 
			{
				createMessageBox(SWT.ERROR, a_e.getMessage(), m_potentialErrorMesaage);
			}
		}
	};
	
	/**
	 * Слушатель нажатия кнопки "Удалить всех".
	 */
	SelectionAdapter m_deletingAllUsersSelection = new SelectionAdapter() 
	{
		@Override
		public void widgetSelected (SelectionEvent a_event) 
		{
			if (!m_isConnection) 
			{
				createMessageBox(SWT.ERROR, "Выберите хранилище данных.");
				return;
			}
			if (m_table.getItemCount() == 0) 
			{
				createMessageBox(SWT.ICON_WARNING, "Нет данных для удаления.");
				return;
			}
			clearTextFields();
			String m_potentialErrorMessage = "Ошибка выполнения команды удаления пользователей.";
			try 
			{
				m_commandsExecuter.execute(new CommandDeleteAll (m_storage));
				m_titleLabel.setText("Добавление пользователя:");
				m_tableViewer.refresh();
				m_potentialErrorMessage = "Ошибка выполнения обновления хранилища.";
				m_storage.updateStorageObject();
			} catch (Exception a_e) 
			{
				createMessageBox(SWT.ERROR, a_e.getMessage(), m_potentialErrorMessage);
			}
		}
	};
	
	/**
	 * Метод для установки соединения с хранилищем.
	 */
	private void setStorage() 
	{
		m_storage = m_storageFactory.getStorage(m_combo.getText());
		String m_potentialErrorMessage = "";
		try 
		{
			if (m_combo.getText().equals("")) m_combo.setText(m_defaultFileName);
			m_potentialErrorMessage = "Ошибка выполнения установки соединения с хранилищем.";
			m_storage.setStorage(); 
			m_shellProperties = m_shellPropertiesFactory.getShellProperties(m_storage);
			setShell (m_shell);
			m_potentialErrorMessage = "Ошибка выполнения создания хранилища.";
			m_storage.createStorageObject();
			UsersModelProvider m_modelProvider = new UsersModelProvider(m_storage);
			m_tableViewer.setInput(m_modelProvider);
			m_tableViewer.setComparator(m_comparator);
			setEditingSupportForColumns();
			m_tableViewer.refresh();
		} catch (Exception a_e) 
		{
			createMessageBox(SWT.ERROR, a_e.getMessage(), m_potentialErrorMessage);
			return;
		}
	}
}