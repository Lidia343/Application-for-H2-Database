
public class StorageFactory {
	
	public Storage getStorage(String storageName) {
		Storage storage = null;
		if (storageName.startsWith("jdbc:h2:")) storage = new DataBase(storageName); else //"jdbc:h2:~/test"
			if (storageName.endsWith(".txt")) storage = new FileStorage(storageName);//"file.txt"
		return storage;
	}
}
