package world.skytale.database;

public class ItemNotFoundException extends Exception{

    public ItemNotFoundException(String tableName, String id )
    {
        super(tableName+" has no item with id "+id);
    }

}
