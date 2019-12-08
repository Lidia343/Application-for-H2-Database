package storages;

/**
 * Данный абстрактный класс необходим для того, чтобы наследующие его классы имели один конструктор с одним параметром (для инициализации имени хранилища данных).
 */
public abstract class Storage
{
	protected String m_storageName;
	
	/**
	 * Конструктор класса Storage.
	 * @param a_storageName - имя хранилища
	 */
	public Storage (String a_storageName)
	{
		m_storageName = a_storageName;
	}
}
