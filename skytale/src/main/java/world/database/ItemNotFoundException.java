package world.database;

public class ItemNotFoundException extends Exception{

 public final String id;
 public final String tableName;

    public ItemNotFoundException(String tableName, String id ) {
        super(tableName + " has no item with id " + id);
        this.id = id;
        this.tableName = tableName;
    }


}
