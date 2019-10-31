
public class StorageFactory {
	
	public Storage getStorage(String storageName) {
		Storage storage = null;
		if (storageName.equals("jdbc:h2:~/test")) storage = new DataBase(); 
		if (storageName.equals("file.txt")) storage = new FileStorage(); 
		return storage;
	}
}
