package graphics;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import user.User;

/**
 * Класс предназначен для возможности сортировки данных пользователей по выбранному столбцу.
 */
public class TableViewerComparator extends ViewerComparator 
{
	private int m_propertyIndex;
	private static final int DESCENDING = 1; // нисходящее направление
	private int m_direction = DESCENDING;

	/**
	 * Конструктор класса TableViewerComparator.
	 */
	public TableViewerComparator() 
	{
		m_propertyIndex = 0;
		m_direction = DESCENDING;
	}

	/**
	 * Метод возвращает направление сортировки (нисходящее или восходящее).
	 */
	public int getDirection() 
	{
		return m_direction == 1 ? SWT.DOWN : SWT.UP;
	}

	/**
	 * Метод устанавливает номер выбранного для сортировки столбца.
	 * @param a_column - номер столбца
	 */
	public void setColumn(int a_column) 
	{
		if (a_column == m_propertyIndex) 
		{
			m_direction = 1 - m_direction;
		} else 
			{
				m_propertyIndex = a_column;
				m_direction = DESCENDING;
			}
	}

	/**
	 * Метод для сравнения данных, установленных в TableViewer.
	 * @param a_viewer - объект класса TableViewer
	 * @param a_e1 - первый объект таблицы для сравнения со вторым
	 * @param a_e2 - второй объект таблицы 
	 */
	@Override
	public int compare(Viewer a_viewer, Object a_e1, Object a_e2) 
	{
		User u1 = (User) a_e1;
		User u2 = (User) a_e2;
		int rc = 0;
		switch (m_propertyIndex) 
		{
			case 0:
			{
				if (u1.getId() == u2.getId()) 
					rc = 0; 
				else
					rc = ((u1.getId() > u2.getId()) ? 1 : -1);
				break;
			}
			case 1:
			{
				rc = u1.getName().compareTo(u2.getName());
				break;
			}
			case 2:
			{
				rc = u1.getSurname().compareTo(u2.getSurname());
				break;
			}
			case 3:
			{
				if (u1.getAge() == u2.getAge()) 
					rc = 0;
				else
					rc = ((u1.getAge() > u2.getAge()) ? 1 : -1);
				break;
			}
			case 4:
			{
				if (u1.isActive() == u2.isActive()) 
					rc = 0;
			    else
			    	rc = (u1.isActive() ? 1 : -1);
				break;
			}
			default: rc = 0;
		}
		if (m_direction == DESCENDING) 
			rc = -rc;
		return rc;
	}
}